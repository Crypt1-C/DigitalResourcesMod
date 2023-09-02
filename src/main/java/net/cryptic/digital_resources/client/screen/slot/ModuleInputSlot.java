package net.cryptic.digital_resources.client.screen.slot;

import net.cryptic.digital_resources.registry.ItemRegistry;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.SlotItemHandler;
import org.jetbrains.annotations.NotNull;

public class ModuleInputSlot extends SlotItemHandler {
    public ModuleInputSlot(IItemHandler itemHandler, int index, int xPosition, int yPosition) {
        super(itemHandler, index, xPosition, yPosition);
    }

    @Override
    public boolean mayPlace(@NotNull ItemStack stack) {
        return ItemRegistry.getAllModules().contains(stack.getItem());
    }
}
