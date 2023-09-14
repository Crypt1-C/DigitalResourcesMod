package net.cryptic.digital_resources.registry;

import net.cryptic.digital_resources.api.Resources;
import net.cryptic.digital_resources.api.UpgradeTypes;
import net.cryptic.digital_resources.registry.BlockRegistry;
import net.cryptic.digital_resources.registry.ItemRegistry;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import org.jetbrains.annotations.NotNull;

import javax.print.DocFlavor;

public class ItemTabRegistry {
    public static final CreativeModeTab MODULE_TAB = new CreativeModeTab("module_tab") {
        @Override
        public @NotNull ItemStack makeIcon() {
           return new ItemStack(ItemRegistry.getModuleByResource(Resources.BLANK).get());
        }
    };

    public static final CreativeModeTab DATA_SHARDS_TAB = new CreativeModeTab("data_shards_tab") {
        @Override
        public @NotNull ItemStack makeIcon() {
            return new ItemStack(ItemRegistry.getDataShardByResource(Resources.BLANK).get());
        }
    };

    public static final CreativeModeTab UPGRADE_TAB = new CreativeModeTab("upgrade_tab") {
        @Override
        public @NotNull ItemStack makeIcon() {
            return new ItemStack(ItemRegistry.getUpgradeByType(UpgradeTypes.BLANK).get());
        }
    };

    public static final CreativeModeTab MISC_TAB = new CreativeModeTab("misc_tab") {
        @Override
        public @NotNull ItemStack makeIcon() {
            return new ItemStack(ItemRegistry.NULLA.get());
        }
    };


}
