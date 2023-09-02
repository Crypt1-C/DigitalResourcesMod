package net.cryptic.digital_resources.registry;

import net.cryptic.digital_resources.Main;
import net.cryptic.digital_resources.api.Resources;
import net.cryptic.digital_resources.api.UpgradeTypes;
import net.cryptic.digital_resources.common.item.ModuleItem;
import net.cryptic.digital_resources.common.item.UpgradeItem;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class ItemRegistry {

    public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, Main.MOD_ID);

    public static final List<Item> MODULES = new ArrayList<>();
    public static final List<Item> UPGRADES = new ArrayList<>();

    private static void registerModules() {
        for (Resources resource : Resources.values()) {
            ITEMS.register(String.format("%s_module", resource.toString().toLowerCase()), () -> new ModuleItem(MODULES,new Item.Properties().tab(ItemTabRegistry.MODULE_TAB)));
        }
    }

    private static void registerUpgrades() {
        for (UpgradeTypes upgrade : UpgradeTypes.values()) {
            ITEMS.register(String.format("%s_upgrade", upgrade.toString().toLowerCase()), () -> new UpgradeItem(UPGRADES,new Item.Properties().tab(ItemTabRegistry.UPGRADE_TAB)));
        }
    }

    public static List<Item> getAllModules() {
        return MODULES;
    }
    
    public static List<Item> getAllUpgrades() {
        return UPGRADES;
    }

    public static Optional<RegistryObject<Item>> getModuleByResource(Resources pResource) {
        return ITEMS.getEntries().stream().filter(itemRegistryObject -> itemRegistryObject.getId().getPath().equals(pResource.name().toLowerCase()+"_module")).findFirst();
    }

    public static Optional<RegistryObject<Item>> getUpgradeByType(UpgradeTypes pType) {
        return ITEMS.getEntries().stream().filter(itemRegistryObject -> itemRegistryObject.getId().getPath().equals(pType.name().toLowerCase()+"_upgrade")).findFirst();
    }

    public static List<BlockItem> getAllBlockItems() {
        return ITEMS.getEntries().stream().map(RegistryObject::get).filter(item -> item instanceof BlockItem).map(item -> (BlockItem) item).filter(blockItem -> blockItem.getBlock() instanceof Block).collect(Collectors.toList());
    }

    public static void register(IEventBus eventBus) {
        registerModules();
        registerUpgrades();
        ITEMS.register(eventBus);
    }

}
