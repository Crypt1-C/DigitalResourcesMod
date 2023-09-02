package net.cryptic.digital_resources.datagen;

import net.cryptic.digital_resources.Main;
import net.cryptic.digital_resources.registry.BlockRegistry;
import net.cryptic.digital_resources.registry.ItemRegistry;
import net.minecraft.data.CachedOutput;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.DataProvider;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.common.data.LanguageProvider;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import org.apache.commons.lang3.text.WordUtils;

import java.io.IOException;
import java.util.Objects;

public class LocalizationGenerator extends LanguageProvider {
    public LocalizationGenerator(DataGenerator gen, String locale) {
        super(gen, Main.MOD_ID, locale);
    }

    @Override
    protected void addTranslations() {
        BlockRegistry.BLOCKS.getEntries().stream()
                .map(RegistryObject::get)
                .map(ForgeRegistries.BLOCKS::getKey)
                .filter(Objects::nonNull)
                .map(ResourceLocation::getPath)
                .forEach(path -> add(String.format("block.digital_resources.%s", path), WordUtils.capitalize(path.replace("_", " "))));


        ItemRegistry.ITEMS.getEntries().stream()
                .map(RegistryObject::get)
                .map(ForgeRegistries.ITEMS::getKey)
                .filter(Objects::nonNull)
                .map(ResourceLocation::getPath)
                .forEach(path -> add(String.format("item.digital_resources.%s", path),WordUtils.capitalize(path.replace("_"," "))));

        add("itemGroup.module_tab", "Digital Resources Modules");
        add("itemGroup.upgrade_tab", "Digital Resources Upgrades");
        add("itemGroup.misc_tab", "Digital Resources Misc");

        add("digital_resources.jei.resource_simulator","Resource Simulating");
        add("digital_resources.jei.encoder","Resource Encoding");

        add("text.resource_simulator.working","Generating Resource:");
        add("text.resource_simulator.idle","root@DR:~$ Idle");

    }
}
