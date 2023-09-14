package net.cryptic.digital_resources.client.jei.category;

import com.mojang.blaze3d.systems.RenderSystem;
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
import net.minecraft.client.gui.GuiComponent;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.Style;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.minecraft.world.item.ItemStack;
import org.apache.commons.lang3.text.WordUtils;

public class ResourceSimulatingCategory implements IRecipeCategory<ResourceSimulatorRecipe> {

    public static final ResourceLocation UID = new ResourceLocation(Main.MOD_ID,"resource_simulating");
    public static final ResourceLocation TEXTURE = new ResourceLocation(Main.MOD_ID,"textures/gui/resource_simulator/gui_jei.png");

    private int ticks = 0;
    private long lastTickTime = 0;
    private final IDrawable bg;
    private final IDrawable icon;

    public ResourceSimulatingCategory(IGuiHelper guiHelper) {
        this.bg = guiHelper.createDrawable(TEXTURE,0,0,92,24);
        this.icon = guiHelper.createDrawableItemStack(new ItemStack(BlockRegistry.SIMULATOR_BLOCK.get()));
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
        builder.addSlot(RecipeIngredientRole.INPUT,4,4).addItemStack(recipe.getInput());
        builder.addSlot(RecipeIngredientRole.OUTPUT,72,4).addItemStack(recipe.getResultItem());
    }

    @Override
    public void draw(ResourceSimulatorRecipe recipe, IRecipeSlotsView recipeSlotsView, PoseStack stack, double mouseX, double mouseY) {
        Minecraft mc = Minecraft.getInstance();
        long time = mc.level.getGameTime();
        int speed = 100;

        int width = Mth.ceil(35f * (this.ticks % speed + mc.getDeltaFrameTime()) / speed);
        GuiComponent.blit(stack, 28, 9, 0, 25, width, 6, 256, 256);

        if (time != this.lastTickTime) {
            ++this.ticks;
            this.lastTickTime = time;
        }

    }
}
