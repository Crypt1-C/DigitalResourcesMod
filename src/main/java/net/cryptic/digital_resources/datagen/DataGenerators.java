package net.cryptic.digital_resources.datagen;

import net.minecraft.data.DataGenerator;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.data.event.GatherDataEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.MOD)
public class DataGenerators {

    @SubscribeEvent
    public static void gatherData(GatherDataEvent event) {
        DataGenerator generator = event.getGenerator();
        ExistingFileHelper existingFileHelper = event.getExistingFileHelper();
        generator.addProvider(event.includeClient(), new ItemModelGenerator(generator, existingFileHelper));
        generator.addProvider(event.includeClient(), new LocalizationGenerator(generator, "en_us"));
        generator.addProvider(event.includeServer(), new LootTableGenerator(generator));
        generator.addProvider(event.includeServer(), new BlockTagGenerator(generator, existingFileHelper));
        //generator.addProvider(event.includeServer(), new RecipeGenerator(generator));
    }
}