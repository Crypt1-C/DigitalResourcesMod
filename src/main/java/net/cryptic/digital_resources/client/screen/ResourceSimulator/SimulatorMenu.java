package net.cryptic.digital_resources.client.screen.ResourceSimulator;

import net.cryptic.digital_resources.client.screen.slot.InputSlot;
import net.cryptic.digital_resources.client.screen.slot.ModuleInputSlot;
import net.cryptic.digital_resources.client.screen.slot.OutputSlot;
import net.cryptic.digital_resources.client.screen.slot.UpgradeSlot;
import net.cryptic.digital_resources.common.block.Simulator.ResourceSimulatorBlockEntity;
import net.cryptic.digital_resources.registry.BlockRegistry;
import net.cryptic.digital_resources.registry.MenuTypeRegistry;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.*;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.SlotItemHandler;
import org.apache.commons.lang3.text.WordUtils;

public class SimulatorMenu extends AbstractContainerMenu {

    public final ResourceSimulatorBlockEntity blockEntity;
    private final Level level;
    private ContainerData data;

    public SimulatorMenu(int pId, Inventory pInventory, FriendlyByteBuf pBuf) {
        this(pId,pInventory,pInventory.player.level.getBlockEntity(pBuf.readBlockPos()), new SimpleContainerData(3));
    }

    @SuppressWarnings("removal")
    public SimulatorMenu(int pId, Inventory pInventory, BlockEntity pEntity, ContainerData pData) {
        super(MenuTypeRegistry.RESOURCE_SIMULATOR_MENU.get(), pId);
        checkContainerSize(pInventory,5);
        blockEntity = (ResourceSimulatorBlockEntity) pEntity;
        this.level = pInventory.player.level;
        this.data = pData;

        addPlayerInventory(pInventory);
        addPlayerHotbar(pInventory);

        this.blockEntity.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY).ifPresent(handler -> {
            this.addSlot(new ModuleInputSlot(handler, 0, 24, 17));    //module slot
            this.addSlot(new UpgradeSlot(handler, 1, -13, 1));    // upgrade slot 1
            this.addSlot(new UpgradeSlot(handler, 2, -13, 26)); // upgrade slot 2
            this.addSlot(new OutputSlot(handler, 3, 196, 17));   //output slot
            this.addSlot(new InputSlot(handler, 4, 176, 17));   //input slot
        });

        addDataSlots(data);

    }



    public boolean isCrafting() {
        return data.get(0) > 0;
    }

    public String getResource() {
        if (blockEntity.getResource() != null) {
            return WordUtils.capitalize(blockEntity.getResource().toString().toLowerCase());
        } else return null;

    }

    public int getProgress() {
        return this.data.get(0);
    }

    public int getMaxProgress() {
        return this.data.get(1);
    }

    public int getCount() {
        return this.data.get(2);
    }

    public int getScaledProgress() {
        int progress = this.data.get(0);
        int maxProgress = this.data.get(1);  // Max Progress
        int progressArrowSize = 87; // This is the height in pixels of your arrow

        return maxProgress != 0 && progress != 0 ? ((progress * progressArrowSize) / maxProgress) : 0;
    }

    // CREDIT GOES TO: diesieben07 | https://github.com/diesieben07/SevenCommons
    // must assign a slot number to each of the slots used by the GUI.
    // For this container, we can see both the tile inventory's slots as well as the player inventory slots and the hotbar.
    // Each time we add a Slot to the container, it automatically increases the slotIndex, which means
    //  0 - 8 = hotbar slots (which will map to the InventoryPlayer slot numbers 0 - 8)
    //  9 - 35 = player inventory slots (which map to the InventoryPlayer slot numbers 9 - 35)
    //  36 - 44 = TileInventory slots, which map to our TileEntity slot numbers 0 - 8)
    private static final int HOTBAR_SLOT_COUNT = 9;
    private static final int PLAYER_INVENTORY_ROW_COUNT = 3;
    private static final int PLAYER_INVENTORY_COLUMN_COUNT = 9;
    private static final int PLAYER_INVENTORY_SLOT_COUNT = PLAYER_INVENTORY_COLUMN_COUNT * PLAYER_INVENTORY_ROW_COUNT;
    private static final int VANILLA_SLOT_COUNT = HOTBAR_SLOT_COUNT + PLAYER_INVENTORY_SLOT_COUNT;
    private static final int VANILLA_FIRST_SLOT_INDEX = 0;
    private static final int TE_INVENTORY_FIRST_SLOT_INDEX = VANILLA_FIRST_SLOT_INDEX + VANILLA_SLOT_COUNT;

    // THIS YOU HAVE TO DEFINE!
    private static final int TE_INVENTORY_SLOT_COUNT = 5;  // must be the number of slots you have!

    @Override
    public ItemStack quickMoveStack(Player playerIn, int index) {
        Slot sourceSlot = slots.get(index);
        if (sourceSlot == null || !sourceSlot.hasItem()) return ItemStack.EMPTY;  //EMPTY_ITEM
        ItemStack sourceStack = sourceSlot.getItem();
        ItemStack copyOfSourceStack = sourceStack.copy();

        // Check if the slot clicked is one of the vanilla container slots
        if (index < VANILLA_FIRST_SLOT_INDEX + VANILLA_SLOT_COUNT) {
            // This is a vanilla container slot so merge the stack into the tile inventory
            if (!moveItemStackTo(sourceStack, TE_INVENTORY_FIRST_SLOT_INDEX, TE_INVENTORY_FIRST_SLOT_INDEX + TE_INVENTORY_SLOT_COUNT, false)) {
                return ItemStack.EMPTY;  // EMPTY_ITEM
            }
        } else if (index < TE_INVENTORY_FIRST_SLOT_INDEX + TE_INVENTORY_SLOT_COUNT) {
            // This is a TE slot so merge the stack into the players inventory
            if (!moveItemStackTo(sourceStack, VANILLA_FIRST_SLOT_INDEX, VANILLA_FIRST_SLOT_INDEX + VANILLA_SLOT_COUNT, false)) {
                return ItemStack.EMPTY;
            }
        } else {
            System.out.println("Invalid slotIndex:" + index);
            return ItemStack.EMPTY;
        }
        // If stack size == 0 (the entire stack was moved) set slot contents to null
        if (sourceStack.getCount() == 0) {
            sourceSlot.set(ItemStack.EMPTY);
        } else {
            sourceSlot.setChanged();
        }
        sourceSlot.onTake(playerIn, sourceStack);
        return copyOfSourceStack;
    }

    @Override
    public boolean stillValid(Player pPlayer) {
        return stillValid(ContainerLevelAccess.create(level, blockEntity.getBlockPos()),pPlayer, BlockRegistry.SIMULATOR_BLOCK.get());
    }

    private void addPlayerInventory(Inventory playerInventory) {
        for (int i = 0; i < 3; ++i) {
            for (int l = 0; l < 9; ++l) {
                this.addSlot(new Slot(playerInventory, l + i * 9 + 9, 36 + l * 18, 153 + i * 18));
            }
        }
    }

    private void addPlayerHotbar(Inventory playerInventory) {
        for (int i = 0; i < 9; ++i) {
            this.addSlot(new Slot(playerInventory, i, 36 + i * 18, 211));
        }
    }

}
