package net.cryptic.digital_resources.registry;

import net.cryptic.digital_resources.Main;
import net.cryptic.digital_resources.common.recipe.ResourceSimulatorRecipe;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class RecipeRegistry {

    public static final DeferredRegister<RecipeSerializer<?>> SERIALIZERS = DeferredRegister.create(ForgeRegistries.RECIPE_SERIALIZERS, Main.MOD_ID);

    public static final RegistryObject<RecipeSerializer<ResourceSimulatorRecipe>> RESOURCE_SIMULATOR_SERIALIZER =
            SERIALIZERS.register("resource_simulating", () -> ResourceSimulatorRecipe.Serializer.INSTANCE);

    public static void register(IEventBus eventBus) {
        SERIALIZERS.register(eventBus);
    }

}
