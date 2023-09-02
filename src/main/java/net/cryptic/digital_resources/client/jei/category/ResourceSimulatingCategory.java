package net.cryptic.digital_resources.client.jei.category;

import com.mojang.blaze3d.vertex.PoseStack;
import mezz.jei.api.constants.VanillaTypes;
import mezz.jei.api.gui.IRecipeLayoutDrawable;
import mezz.jei.api.gui.builder.IRecipeLayoutBuilder;
import mezz.jei.api.gui.drawable.IDrawable;
import mezz.jei.api.gui.drawable.IDrawableAnimated;
import mezz.jei.api.gui.drawable.IDrawableStatic;
import mezz.jei.api.gui.ingredient.IRecipeSlotsView;
import mezz.jei.api.helpers.IGuiHelper;
import mezz.jei.api.recipe.IFocusGroup;
import mezz.jei.api.recipe.RecipeIngredientRole;
import mezz.jei.api.recipe.RecipeType;
import mezz.jei.api.recipe.category.IRecipeCategory;
import net.cryptic.digital_resources.Main;
import net.cryptic.digital_resources.client.jei.JEIPlugin;
import net.cryptic.digital_resources.common.block.Simulator.ResourceSimulatorBlock;
import net.cryptic.digital_resources.common.block.Simulator.ResourceSimulatorBlockEntity;
import net.cryptic.digital_resources.common.recipe.ResourceSimulatorRecipe;
import net.cryptic.digital_resources.registry.BlockRegistry;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.Style;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import org.apache.commons.lang3.text.WordUtils;

public class ResourceSimulatingCategory implements IRecipeCategory<ResourceSimulatorRecipe> {

    public static final ResourceLocation UID = new ResourceLocation(Main.MOD_ID,"resource_simulating");
    public static final ResourceLocation TEXTURE = new ResourceLocation(Main.MOD_ID,"textures/gui/resource_simulator/gui_jei.png");

    //public static final IDrawableStatic PROGRESS_BAR_TEXTURE =


    private final IDrawable bg;
    private final IDrawable icon;
    private final IDrawable progress;

    public ResourceSimulatingCategory(IGuiHelper guiHelper) {
        this.bg = guiHelper.createDrawable(TEXTURE,0,0,175,82);
        this.icon = guiHelper.createDrawableItemStack(new ItemStack(BlockRegistry.SIMULATOR_BLOCK.get()));
        this.progress = guiHelper.createDrawable(TEXTURE,240,0,16, 63);
    }

    protected void drawStringCentered(PoseStack poseStack, Font fontRenderer, Component text, int x, int y) {
        fontRenderer.draw(poseStack, text, (x - fontRenderer.width(text) / 2.0f), y, 0);
    }

    @Override
    public RecipeType<ResourceSimulatorRecipe> getRecipeType() {
        return JEIPlugin.RESOURCE_SIMULATING;
    }

    @Override
    public Component getTitle() {
        return Component.translatable(Main.MOD_ID + ".jei.resource_simulator");
    }

    @Override
    public IDrawable getBackground() {
        return this.bg;
    }

    @Override
    public IDrawable getIcon() {
        return this.icon;
    }

    @Override
    public void setRecipe(IRecipeLayoutBuilder builder, ResourceSimulatorRecipe recipe, IFocusGroup focuses) {
        builder.addSlot(RecipeIngredientRole.INPUT,8,7).addItemStack(recipe.getInput());
        builder.addSlot(RecipeIngredientRole.OUTPUT,8,57).addItemStack(recipe.getResultItem());
    }

    @Override
    public void draw(ResourceSimulatorRecipe recipe, IRecipeSlotsView recipeSlotsView, PoseStack stack, double mouseX, double mouseY) {
        Minecraft.getInstance().font.draw(stack,Component.translatable("text.resource_simulator.working"),32,12,0x71df2d);
        Minecraft.getInstance().font.draw(stack,WordUtils.capitalize(recipe.getInput().getItem().toString().replace("_module","")),76,24,0xffffff);
    }
}
