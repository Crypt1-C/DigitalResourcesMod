package net.cryptic.digital_resources.datagen;

import net.cryptic.digital_resources.Main;
import net.cryptic.digital_resources.registry.BlockRegistry;
import net.cryptic.digital_resources.registry.ItemRegistry;
import net.minecraft.advancements.critereon.ItemPredicate;
import net.minecraft.client.RecipeBookCategories;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.recipes.*;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.Ingredient;

import javax.annotation.Nonnull;
import java.util.List;
import java.util.function.Consumer;

public class RecipeGenerator extends RecipeProvider {
    public RecipeGenerator(DataGenerator pGenerator) {
        super(pGenerator);
    }

    @Override
    protected void buildCraftingRecipes(@Nonnull Consumer<FinishedRecipe> pFinishedRecipeConsumer) {

        nineBlockStorageRecipes(pFinishedRecipeConsumer, ItemRegistry.NULLA.get(), BlockRegistry.NULLA_BLOCK.get(), "raw_nulla_block", RecipeBookCategories.CRAFTING_BUILDING_BLOCKS.name(), "raw_nulla", RecipeBookCategories.CRAFTING_MISC.name());
        nineBlockStorageRecipes(pFinishedRecipeConsumer, ItemRegistry.RAW_NULLA.get(), BlockRegistry.RAW_NULLA_BLOCK.get(), "nulla_block", RecipeBookCategories.CRAFTING_BUILDING_BLOCKS.name(), "nulla", RecipeBookCategories.CRAFTING_MISC.name());

        oreSmelting(pFinishedRecipeConsumer, List.of(BlockRegistry.NULLA_ORE.get()), ItemRegistry.NULLA.get(),0.8f,250, RecipeBookCategories.FURNACE_BLOCKS.name());
        oreBlasting(pFinishedRecipeConsumer, List.of(BlockRegistry.NULLA_ORE.get()), ItemRegistry.NULLA.get(),1.0f,100, RecipeBookCategories.BLAST_FURNACE_BLOCKS.name());

        oreSmelting(pFinishedRecipeConsumer, List.of(ItemRegistry.RAW_NULLA.get()), ItemRegistry.NULLA.get(),1.7f,300, RecipeBookCategories.FURNACE_BLOCKS.name());
        oreBlasting(pFinishedRecipeConsumer, List.of(ItemRegistry.RAW_NULLA.get()), ItemRegistry.NULLA.get(),2.4f,150, RecipeBookCategories.BLAST_FURNACE_BLOCKS.name());
    }
}
