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
                .forEach(path -> add(String.format("item.digital_resources.%s", path), WordUtils.capitalize(path.replace("_"," "))));

        ItemRegistry.DATA_SHARDS_REGISTRY.getEntries().stream()
                .map(RegistryObject::get)
                .map(ForgeRegistries.ITEMS::getKey)
                .filter(Objects::nonNull)
                .map(ResourceLocation::getPath)
                .forEach(path -> add(String.format("item.digital_resources.%s", path), WordUtils.capitalize(path.replace("_"," "))));

        add("itemGroup.module_tab", "Digital Resources Modules");
        add("itemGroup.upgrade_tab", "Digital Resources Upgrades");
        add("itemGroup.misc_tab", "Digital Resources Misc");
        add("itemGroup.data_shards_tab", "Digital Resources Data Shards");

        add("item.tooltip.shift", "Press [Shift] For More Info");

        add("item.data_shard.tooltip", "Contains Resource Data For: ");
        add("item.module.tooltip", "Contains Resource Data For: ");

        add("item.upgrade.speed.tooltip", "Upgrades Machine Speed By x2");
        add("item.upgrade.stack.tooltip", "Upgrades Machine Output By x4");

        add("digital_resources.jei.resource_simulator","Resource Simulating");
        add("digital_resources.jei.encoder","Resource Encoding");

        add("resource_simulator.gui.working","Simulating: ");

        add("resource_simulator.run.0", "~$ Launching runtime ");
        add("resource_simulator.run.1", "~$ Iteration started");
        add("resource_simulator.run.2", "~$ Loading Resource from Module");
        add("resource_simulator.run.3", "~$ Assessing Resource Value");
        add("resource_simulator.run.4", "~$ Discovering Composition");
        add("resource_simulator.run.5", "~$ Materializing Data");
        add("resource_simulator.run.6", "~$ Processing results...");

    }
}
