package net.cryptic.digital_resources.common.recipe;

import com.google.gson.JsonObject;
import cpw.mods.util.Lazy;
import net.cryptic.digital_resources.Main;
import net.cryptic.digital_resources.registry.ItemRegistry;
import net.cryptic.digital_resources.registry.RecipeRegistry;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.util.GsonHelper;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.*;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.Nullable;

public class ResourceSimulatorRecipe implements Recipe<SimpleContainer> {

    private final ResourceLocation id;
    private final ItemStack input;
    private final Ingredient output;

    public ResourceSimulatorRecipe(ResourceLocation pId,Ingredient pOutput,ItemStack pInput) {
        this.id = pId;
        this.input = pInput;
        this.output = pOutput;
    }

    @Override
    public boolean matches(SimpleContainer pContainer, Level pLevel) {
        if (pLevel.isClientSide()) {
            return false;
        }

        return input.is(pContainer.getItem(0).getItem());
    }

    @Override
    public ItemStack assemble(SimpleContainer pContainer) {
        return output.getItems()[0];
    }

    @Override
    public boolean canCraftInDimensions(int pWidth, int pHeight) {
        return true;
    }

    @Override
    public ItemStack getResultItem() {
        return output.getItems()[0];
    }

    public ItemStack getInput() {
        return input.copy();
    }

    @Override
    public ResourceLocation getId() {
        return id;
    }

    @Override
    public RecipeSerializer<?> getSerializer() {
        return RecipeRegistry.RESOURCE_SIMULATOR_SERIALIZER.get();
    }

    @Override
    public RecipeType<?> getType() {
        return Type.INSTANCE;
    }

    public static class Type implements RecipeType<ResourceSimulatorRecipe> {
        private Type() { }
        public static final Type INSTANCE = new Type();
        public static String ID = "resource_simulating";
    }

    public static class Serializer implements RecipeSerializer<ResourceSimulatorRecipe> {
        public static final Serializer INSTANCE = new Serializer();
        public static final ResourceLocation ID = new ResourceLocation(Main.MOD_ID,"resource_simulating");

        @Override
        public ResourceSimulatorRecipe fromJson(ResourceLocation pRecipeId, JsonObject pSerializedRecipe) {
            ItemStack input = ShapedRecipe.itemStackFromJson(GsonHelper.getAsJsonObject(pSerializedRecipe, "input"));
            Ingredient output = Ingredient.fromJson(GsonHelper.getAsJsonObject(pSerializedRecipe,"output"));

            return new ResourceSimulatorRecipe(pRecipeId, output, input);
        }

        @Override
        public @Nullable ResourceSimulatorRecipe fromNetwork(ResourceLocation pRecipeId, FriendlyByteBuf pBuffer) {
            ItemStack input = pBuffer.readItem();
            Ingredient output = Ingredient.fromNetwork(pBuffer);

            return new ResourceSimulatorRecipe(pRecipeId, output, input);
        }

        @Override
        public void toNetwork(FriendlyByteBuf pBuffer, ResourceSimulatorRecipe pRecipe) {
            pBuffer.writeItemStack(pRecipe.getInput(),false);
            pBuffer.writeItemStack(pRecipe.getResultItem(),false);
        }


    }

}
