package net.cryptic.digital_resources.datagen;

import net.cryptic.digital_resources.Main;
import net.cryptic.digital_resources.api.Resources;
import net.cryptic.digital_resources.api.UpgradeTypes;
import net.cryptic.digital_resources.registry.ItemRegistry;
import net.minecraft.data.DataGenerator;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraftforge.client.model.generators.ItemModelBuilder;
import net.minecraftforge.client.model.generators.ItemModelProvider;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.registries.RegistryObject;

public class ItemModelGenerator extends ItemModelProvider {
    public ItemModelGenerator(DataGenerator generator, ExistingFileHelper existingFileHelper) {
        super(generator, Main.MOD_ID, existingFileHelper);
    }

    @Override
    protected void registerModels() {

        ItemRegistry.getAllBlockItems().forEach(this::blockItem);

        normalItem(ItemRegistry.getModuleByResource(Resources.BLANK));
        normalItem(ItemRegistry.getDataShardByResource(Resources.BLANK));
        normalItem(ItemRegistry.getUpgradeByType(UpgradeTypes.BLANK));
        normalItem(ItemRegistry.NULLA);
        normalItem(ItemRegistry.RAW_NULLA);

        for (Resources resource: Resources.values()) {
            if (resource != Resources.BLANK) {
                moduleItem(ItemRegistry.getModuleByResource(resource));
                dataShardItem(ItemRegistry.getDataShardByResource(resource));
            }
        }

        for (UpgradeTypes type: UpgradeTypes.values()) {
            if (type != UpgradeTypes.BLANK) {
                upgradeItem(ItemRegistry.getUpgradeByType(type));
            }
        }
    }

    private ItemModelBuilder moduleItem(RegistryObject<Item> pItem) {
        String item_name = pItem.getId().getPath().replace("_module","");
        return withExistingParent(pItem.getId().getPath(), mcLoc("item/generated"))
                .texture("layer0", modLoc("item/blank_module"))
                .texture("layer1",modLoc(String.format("item/resources/%s",item_name)));
    }

    private ItemModelBuilder dataShardItem(RegistryObject<Item> pItem) {
        String item_name = pItem.getId().getPath().replace("_data_shard","");
        return withExistingParent(pItem.getId().getPath(), mcLoc("item/generated"))
                .texture("layer0", modLoc("item/blank_data_shard"));
    }

    private ItemModelBuilder upgradeItem(RegistryObject<Item> pItem) {
        String item_name = pItem.getId().getPath().replace("_upgrade","");
        return withExistingParent(pItem.getId().getPath(), mcLoc("item/generated"))
                .texture("layer0", modLoc("item/blank_upgrade"))
                .texture("layer1",modLoc(String.format("item/upgrades/%s",item_name)));
    }

    private ItemModelBuilder normalItem(RegistryObject<Item> pItem) {
        return withExistingParent(pItem.getId().getPath(), mcLoc("item/generated"))
                .texture("layer0", modLoc(String.format("item/%s",pItem.getId().getPath())));
    }

    private ItemModelBuilder blockItem(Item pItem) {
        String name = pItem.toString();
        return name.contains("resource") ? withExistingParent(name, modLoc("block/" + name + "/" + name)) : withExistingParent(name, modLoc("block/" + name)) ;
    }

    private ItemModelBuilder handheldItem(RegistryObject<Item> pItem) {
        return withExistingParent(pItem.getId().getPath(),
                new ResourceLocation("item/handheld")).texture("layer0",
                new ResourceLocation(Main.MOD_ID,"item/"+pItem.getId().getPath()));
    }

}
