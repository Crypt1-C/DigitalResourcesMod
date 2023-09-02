package net.cryptic.digital_resources.registry;

import net.cryptic.digital_resources.api.Resources;
import net.cryptic.digital_resources.api.UpgradeTypes;
import net.cryptic.digital_resources.registry.BlockRegistry;
import net.cryptic.digital_resources.registry.ItemRegistry;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import org.jetbrains.annotations.NotNull;

public class ItemTabRegistry {
    public static final CreativeModeTab MODULE_TAB = new CreativeModeTab("module_tab") {
        @Override
        public @NotNull ItemStack makeIcon() {
            if (ItemRegistry.getModuleByResource(Resources.EMPTY).isPresent()) {
                return new ItemStack(ItemRegistry.getModuleByResource(Resources.EMPTY).get().get());
            } else {
                return new ItemStack(Items.AIR);
            }
        }
    };

    public static final CreativeModeTab UPGRADE_TAB = new CreativeModeTab("upgrade_tab") {
        @Override
        public @NotNull ItemStack makeIcon() {
            if (ItemRegistry.getUpgradeByType(UpgradeTypes.EMPTY).isPresent()) {
                return new ItemStack(ItemRegistry.getUpgradeByType(UpgradeTypes.EMPTY).get().get());
            } else {
                return new ItemStack(Items.AIR);
            }
        }
    };

    public static final CreativeModeTab MISC_TAB = new CreativeModeTab("misc_tab") {
        @Override
        public @NotNull ItemStack makeIcon() {
            return new ItemStack(BlockRegistry.SIMULATOR_BLOCK.get());
        }
    };


}
