package net.cryptic.digital_resources.registry;

import com.mojang.blaze3d.platform.NativeImage;
import com.mojang.blaze3d.platform.TextureUtil;
import net.cryptic.digital_resources.Main;
import net.cryptic.digital_resources.api.Resources;
import net.cryptic.digital_resources.api.UpgradeTypes;
import net.cryptic.digital_resources.common.item.DataShardItem;
import net.cryptic.digital_resources.common.item.ModuleItem;
import net.cryptic.digital_resources.common.item.UpgradeItem;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.components.AbstractWidget;
import net.minecraft.client.renderer.texture.AbstractTexture;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.packs.resources.ResourceManager;
import net.minecraft.util.Mth;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import mezz.jei.api.helpers.IColorHelper;

import javax.annotation.Nonnull;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class ItemRegistry {

    public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, Main.MOD_ID);
    public static final DeferredRegister<Item> DATA_SHARDS_REGISTRY = DeferredRegister.create(ForgeRegistries.ITEMS, Main.MOD_ID);

    public static final List<Item> MODULES = new ArrayList<>();
    public static final List<Item> UPGRADES = new ArrayList<>();
    public static final List<DataShardItem> DATA_SHARDS = new ArrayList<>();

    public static final RegistryObject<Item> NULLA = ITEMS.register("nulla",() -> new Item(new Item.Properties().tab(ItemTabRegistry.MISC_TAB)));
    public static final RegistryObject<Item> RAW_NULLA = ITEMS.register("raw_nulla",() -> new Item(new Item.Properties().tab(ItemTabRegistry.MISC_TAB)));

    private static void registerModules() {
        for (Resources resource : Resources.values()) {
            ITEMS.register(String.format("%s_module", resource.toString().toLowerCase()), () -> new ModuleItem(MODULES,new Item.Properties().tab(ItemTabRegistry.MODULE_TAB)));
        }
    }

    private static void registerDataShards() {
        for (Resources resource : Resources.values()) {
            DATA_SHARDS_REGISTRY.register(String.format("%s_data_shard", resource.toString().toLowerCase()), () -> new DataShardItem(DATA_SHARDS, new Item.Properties().tab(ItemTabRegistry.DATA_SHARDS_TAB), resource.getColor()));
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

    public static List<DataShardItem> getAllDataShards() {
        return DATA_SHARDS_REGISTRY.getEntries().stream().map(RegistryObject::get).map(item -> (DataShardItem) item).collect(Collectors.toList());
    }

    public static RegistryObject<Item> getModuleByResource(Resources pResource) {
        return ITEMS.getEntries().stream().filter(itemRegistryObject -> itemRegistryObject.getId().getPath().equals(pResource.name().toLowerCase()+"_module")).findFirst().get();
    }

    public static RegistryObject<Item> getDataShardByResource(Resources pResource) {
        return DATA_SHARDS_REGISTRY.getEntries().stream().filter(itemRegistryObject -> itemRegistryObject.getId().getPath().equals(pResource.name().toLowerCase()+"_data_shard")).findFirst().get();
    }

    public static RegistryObject<Item> getUpgradeByType(UpgradeTypes pType) {
        return ITEMS.getEntries().stream().filter(itemRegistryObject -> itemRegistryObject.getId().getPath().equals(pType.name().toLowerCase()+"_upgrade")).findFirst().get();
    }

    public static List<BlockItem> getAllBlockItems() {
        return ITEMS.getEntries().stream().map(RegistryObject::get).filter(item -> item instanceof BlockItem).map(item -> (BlockItem) item).filter(blockItem -> blockItem.getBlock() instanceof Block).collect(Collectors.toList());
    }

    public static void register(IEventBus eventBus) {
        registerModules();
        registerUpgrades();
        registerDataShards();
        DATA_SHARDS_REGISTRY.register(eventBus);
        ITEMS.register(eventBus);
    }

}
