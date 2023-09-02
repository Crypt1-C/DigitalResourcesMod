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

        normalItem(ItemRegistry.getModuleByResource(Resources.EMPTY).get());
        normalItem(ItemRegistry.getUpgradeByType(UpgradeTypes.EMPTY).get());

        ItemRegistry.getAllBlockItems().forEach(this::blockItem);

        for (Resources resource: Resources.values()) {
            if (resource != Resources.EMPTY) {
                moduleItem(ItemRegistry.getModuleByResource(resource).get());
            }
        }

        for (UpgradeTypes type: UpgradeTypes.values()) {
            if (type != UpgradeTypes.EMPTY) {
                upgradeItem(ItemRegistry.getUpgradeByType(type).get());
            }
        }
    }

    private ItemModelBuilder moduleItem(RegistryObject<Item> pItem) {
        String item_name = pItem.getId().getPath().replace("_module","");
        System.out.println(item_name);
        return withExistingParent(pItem.getId().getPath(), mcLoc("item/generated"))
                .texture("layer0", modLoc("item/empty_module"))
                .texture("layer1",modLoc(String.format("item/resources/%s",item_name)));
    }

    private ItemModelBuilder upgradeItem(RegistryObject<Item> pItem) {
        String item_name = pItem.getId().getPath().replace("_upgrade","");
        System.out.println(item_name);
        return withExistingParent(pItem.getId().getPath(), mcLoc("item/generated"))
                .texture("layer0", modLoc("item/empty_upgrade"))
                .texture("layer1",modLoc(String.format("item/upgrades/%s",item_name)));
    }

    private ItemModelBuilder normalItem(RegistryObject<Item> pItem) {
        return withExistingParent(pItem.getId().getPath(), mcLoc("item/generated"))
                .texture("layer0", modLoc(String.format("item/%s",pItem.getId().getPath())));
    }

    private ItemModelBuilder blockItem(Item pItem) {
        String name = pItem.toString();
        return withExistingParent(name, modLoc("block/" + name + "/" + name));
    }

    private ItemModelBuilder handheldItem(RegistryObject<Item> pItem) {
        return withExistingParent(pItem.getId().getPath(),
                new ResourceLocation("item/handheld")).texture("layer0",
                new ResourceLocation(Main.MOD_ID,"item/"+pItem.getId().getPath()));
    }

}
