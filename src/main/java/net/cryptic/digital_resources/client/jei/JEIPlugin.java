package net.cryptic.digital_resources.client.jei;

import mezz.jei.api.IModPlugin;
import mezz.jei.api.JeiPlugin;
import mezz.jei.api.recipe.RecipeType;
import mezz.jei.api.registration.IRecipeCatalystRegistration;
import mezz.jei.api.registration.IRecipeCategoryRegistration;
import mezz.jei.api.registration.IRecipeRegistration;
import net.cryptic.digital_resources.Main;
import net.cryptic.digital_resources.client.jei.category.ResourceSimulatingCategory;
import net.cryptic.digital_resources.common.recipe.ResourceSimulatorRecipe;
import net.cryptic.digital_resources.registry.BlockRegistry;
import net.minecraft.client.Minecraft;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.RecipeManager;

import java.util.List;
import java.util.Objects;

@JeiPlugin
public class JEIPlugin implements IModPlugin {

    public static RecipeType<ResourceSimulatorRecipe> RESOURCE_SIMULATING = new RecipeType<>(ResourceSimulatingCategory.UID,ResourceSimulatorRecipe.class);

    @Override
    public ResourceLocation getPluginUid() {
        return new ResourceLocation(Main.MOD_ID,"jei_plugin");
    }

    @Override
    public void registerCategories(IRecipeCategoryRegistration registration) {
        registration.addRecipeCategories(new ResourceSimulatingCategory(registration.getJeiHelpers().getGuiHelper()));
    }

    @Override
    public void registerRecipeCatalysts(IRecipeCatalystRegistration registration) {
        registration.addRecipeCatalyst(new ItemStack(BlockRegistry.SIMULATOR_BLOCK.get()), RESOURCE_SIMULATING);
    }

    @Override
    public void registerRecipes(IRecipeRegistration registration) {
        RecipeManager rm = Objects.requireNonNull(Minecraft.getInstance().level).getRecipeManager();
        List<ResourceSimulatorRecipe> recipesSimulating = rm.getAllRecipesFor(ResourceSimulatorRecipe.Type.INSTANCE);
        registration.addRecipes(RESOURCE_SIMULATING, recipesSimulating);
    }
}
