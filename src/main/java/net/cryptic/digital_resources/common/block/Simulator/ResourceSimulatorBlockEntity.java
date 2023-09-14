package net.cryptic.digital_resources.common.block.Simulator;

import net.cryptic.digital_resources.api.Resources;
import net.cryptic.digital_resources.api.UpgradeTypes;
import net.cryptic.digital_resources.client.screen.ResourceSimulator.SimulatorMenu;
import net.cryptic.digital_resources.common.recipe.ResourceSimulatorRecipe;
import net.cryptic.digital_resources.registry.BlockEntityRegistry;
import net.cryptic.digital_resources.registry.ItemRegistry;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.Containers;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.ContainerData;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.ItemStackHandler;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Optional;

public class ResourceSimulatorBlockEntity extends BlockEntity implements MenuProvider {

    private final ItemStackHandler itemHandler = new ItemStackHandler(5) {
        @Override
        protected void onContentsChanged(int slot) {
            super.onContentsChanged(slot);
        }
    };

    private LazyOptional<IItemHandler> lazyItemHandler = LazyOptional.empty();

    protected final ContainerData data;
    private int progress = 0;
    private int maxProgress = 20 * 60 * 5; // 5 minutes
    private int speed = 1;
    private int count = 1;
    private int ticks = 1;


    public ResourceSimulatorBlockEntity(BlockPos pPos, BlockState pBlockState) {
        super(BlockEntityRegistry.RESOURCE_SIMULATOR.get(), pPos, pBlockState);
        this.data = new ContainerData() {
            @Override
            public int get(int pIndex) {
                return switch (pIndex) {
                    case 0 -> ResourceSimulatorBlockEntity.this.progress;
                    case 1 -> ResourceSimulatorBlockEntity.this.maxProgress;
                    case 2 -> ResourceSimulatorBlockEntity.this.count;
                    default -> 0;
                };
            }

            @Override
            public void set(int pIndex, int pValue) {
                switch (pIndex) {
                    case 0 -> ResourceSimulatorBlockEntity.this.progress = pValue;
                    case 1 -> ResourceSimulatorBlockEntity.this.maxProgress = pValue;
                    case 2 -> ResourceSimulatorBlockEntity.this.count = pValue;
                }
            }

            @Override
            public int getCount() {
                return 3;
            }
        };
    }

    @Override
    public Component getDisplayName() {
        return Component.empty();
    }

    @Nullable
    @Override
    public AbstractContainerMenu createMenu(int pContainerId, Inventory pPlayerInventory, Player pPlayer) {
        return new SimulatorMenu(pContainerId, pPlayerInventory,this,this.data);
    }

    @SuppressWarnings("removal")
    @Override
    public @NotNull <T> LazyOptional<T> getCapability(@NotNull Capability<T> cap, @Nullable Direction side) {
        if (cap == CapabilityItemHandler.ITEM_HANDLER_CAPABILITY) {
            return lazyItemHandler.cast();
        }
        return super.getCapability(cap, side);
    }

    @Override
    public void onLoad() {
        super.onLoad();
        lazyItemHandler = LazyOptional.of(() -> itemHandler);
    }

    @Override
    public void invalidateCaps() {
        super.invalidateCaps();
        lazyItemHandler.invalidate();
    }

    @Override
    protected void saveAdditional(CompoundTag pTag) {
        pTag.put("resource_simulator.inventory",itemHandler.serializeNBT());
        pTag.putInt("resource_simulator.progress",progress);
        super.saveAdditional(pTag);
    }

    @Override
    public void load(CompoundTag pTag) {
        super.load(pTag);
        itemHandler.deserializeNBT(pTag.getCompound("resource_simulator.inventory"));
        progress = pTag.getInt("resource_simulator.progress");
    }

    public void drops() {
        SimpleContainer inventory = new SimpleContainer(itemHandler.getSlots());
        for (int i = 0; i < itemHandler.getSlots(); i++) {
            inventory.setItem(i, itemHandler.getStackInSlot(i));
        }
        assert this.level != null;
        Containers.dropContents(this.level, this.worldPosition, inventory);
    }

    public static <E extends BlockEntity> void tick(Level level, BlockPos blockPos, BlockState blockState, ResourceSimulatorBlockEntity pEntity) {

        if (hasRecipe(pEntity)) {
            level.setBlock(blockPos,blockState.setValue(ResourceSimulatorBlock.WORKING,true),3);
            pEntity.progress += pEntity.speed;
            pEntity.ticks++;
            setChanged(level, blockPos, blockState);

            if (pEntity.progress >= pEntity.maxProgress) {
                craftItem(pEntity);
            }

            if (pEntity.ticks % (20*7) == 0) {
                System.out.println(pEntity.ticks);
                level.playLocalSound(blockPos.getX(),blockPos.getY(),blockPos.getZ(), SoundEvents.BEACON_AMBIENT, SoundSource.BLOCKS,1.0f,0.5f,false);
                pEntity.ticks = 0;
            }

        } else {
            level.setBlock(blockPos,blockState.setValue(ResourceSimulatorBlock.WORKING,false),3);
            pEntity.resetProgress();
            setChanged(level, blockPos, blockState);
        }
    }

    private static boolean hasRecipe(ResourceSimulatorBlockEntity pEntity) {
        Level level = pEntity.level;
        SimpleContainer inventory = new SimpleContainer(pEntity.itemHandler.getSlots());

        inventory.setItem(0, pEntity.itemHandler.getStackInSlot(0));
        inventory.setItem(3, pEntity.itemHandler.getStackInSlot(3));

        Optional<ResourceSimulatorRecipe> recipe = level.getRecipeManager().getRecipeFor(ResourceSimulatorRecipe.Type.INSTANCE,inventory,level);

        if (pEntity.itemHandler.getStackInSlot(1).getItem() == ItemRegistry.getUpgradeByType(UpgradeTypes.SPEED).get()) {
            pEntity.speed = pEntity.itemHandler.getStackInSlot(1).getCount() * 4;
        } else if (pEntity.itemHandler.getStackInSlot(2).getItem() == ItemRegistry.getUpgradeByType(UpgradeTypes.SPEED).get()) {
            pEntity.speed = pEntity.itemHandler.getStackInSlot(2).getCount() * 4;
        } else {
            pEntity.speed = 1;
        }

        if ((pEntity.itemHandler.getStackInSlot(1).getItem() == ItemRegistry.getUpgradeByType(UpgradeTypes.STACK).get())) {
            pEntity.count = pEntity.itemHandler.getStackInSlot(1).getCount() * 4;
        } else if ((pEntity.itemHandler.getStackInSlot(2).getItem() == ItemRegistry.getUpgradeByType(UpgradeTypes.STACK).get())) {
            pEntity.count = pEntity.itemHandler.getStackInSlot(2).getCount() * 4;
        } else {
            pEntity.count = 1;
        }

        return recipe.isPresent() && canInsertAmmountIntoOutputSlot(inventory) && canInsertIntoOutputSlot(inventory, recipe.get().getResultItem()) && hasNullMaterial(pEntity);
    }

    private static boolean canInsertIntoOutputSlot(SimpleContainer inventory, ItemStack itemStack) {
        return (inventory.getItem(3).getItem() == itemStack.getItem() || inventory.getItem(3).isEmpty());
    }

    private static boolean canInsertAmmountIntoOutputSlot(SimpleContainer inventory) {
        return inventory.getItem(3).getMaxStackSize() > inventory.getItem(3).getCount();
    }

    private static boolean hasNullMaterial(ResourceSimulatorBlockEntity pEntity) {
        return pEntity.itemHandler.getStackInSlot(4).is(ItemRegistry.NULLA.get());
    }

    private static void craftItem(ResourceSimulatorBlockEntity pEntity) {
        Level level = pEntity.level;
        SimpleContainer inventory = new SimpleContainer(pEntity.itemHandler.getSlots());
        for (int i = 0; i < pEntity.itemHandler.getSlots(); i++) {
            inventory.setItem(i,pEntity.itemHandler.getStackInSlot(i));
        }
        Optional<ResourceSimulatorRecipe> recipe = level.getRecipeManager().getRecipeFor(ResourceSimulatorRecipe.Type.INSTANCE,inventory,level);

        if (hasRecipe(pEntity)) {
            pEntity.itemHandler.extractItem(4, pEntity.count, false);
            pEntity.itemHandler.setStackInSlot(3, new ItemStack(recipe.get().getResultItem().getItem(), pEntity.itemHandler.getStackInSlot(3).getCount() + pEntity.count));
            pEntity.resetProgress();
        }
    }

    public Resources getResource(){
        if (ItemRegistry.getAllModules().contains(this.itemHandler.getStackInSlot(0).getItem())) {
            return Resources.valueOf(this.itemHandler.getStackInSlot(0).getItem().toString().replace("_module","").toUpperCase());
        } else return null;
    }

    public ItemStack getRenderedStack() {
        if (!itemHandler.getStackInSlot(0).isEmpty()) {
            ItemStack stack = itemHandler.getStackInSlot(0);
            return stack;
        } else {
            return new ItemStack(Items.AIR);
        }
    }


    public int getProgress() {
        return this.progress;
    }

    private void resetProgress() {
        this.progress = 0;
    }

}
