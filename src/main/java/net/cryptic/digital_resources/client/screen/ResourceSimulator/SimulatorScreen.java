package net.cryptic.digital_resources.client.screen.ResourceSimulator;

import com.mojang.blaze3d.platform.GlStateManager;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import net.cryptic.digital_resources.Main;
import net.cryptic.digital_resources.common.recipe.ResourceSimulatorRecipe;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.GuiComponent;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.client.renderer.RenderStateShard;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.client.resources.PaintingTextureManager;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TextColor;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.inventory.InventoryMenu;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraftforge.client.event.RenderTooltipEvent;

import java.awt.*;
import java.util.Locale;
import java.util.Random;

public class SimulatorScreen extends AbstractContainerScreen<SimulatorMenu> {

    int tick = 0;
    public static final ResourceLocation TEXTURE = new ResourceLocation(Main.MOD_ID,"textures/gui/resource_simulator/gui.png");
    public SimulatorScreen(SimulatorMenu pMenu, Inventory pPlayerInventory, Component pTitle) {
        super(pMenu, pPlayerInventory, pTitle);
    }

    @Override
    protected void init() {
        super.init();
    }

    @Override
    protected void renderBg(PoseStack pPoseStack, float pPartialTick, int pMouseX, int pMouseY) {
        RenderSystem.setShader(GameRenderer::getPositionTexShader);
        RenderSystem.setShaderColor(1.0f,1.0f,1.0f,1.0f);
        RenderSystem.setShaderTexture(0,TEXTURE);
        int x = (width - imageWidth) / 2;
        int y = (height - imageHeight) / 2;

        this.blit(pPoseStack,x,y,0,0,imageWidth,imageHeight);
        this.blit(pPoseStack,x-28,y,177,0,29,73);
        renderWidgets(pPoseStack, x, y);
    }

    private void renderWidgets(PoseStack pPoseStack, int x, int y) {
        if(menu.isCrafting()) {
            tick++;
            blit(pPoseStack, x + 153, y + 71 - menu.getScaledProgress(), 240, 62 - menu.getScaledProgress(), 16, menu.getScaledProgress());
            renderText(pPoseStack,x+33,y+12,Component.translatable("text.resource_simulator.working").getString(),"#71df2d");

            renderText(pPoseStack,x+32,y+36,"x"+menu.blockEntity.getCount(),"#ffffff");


            if (tick % 20*5 == 0) {
                renderText(pPoseStack, x + new Random().nextInt(34,140), y + new Random().nextInt(8,69),".","#71df2d");
                renderText(pPoseStack, x + new Random().nextInt(34,140), y + new Random().nextInt(8,69),"'","#71df2d");
                renderText(pPoseStack, x + new Random().nextInt(34,140), y + new Random().nextInt(8,69),"*","#71df2d");
                renderText(pPoseStack, x + new Random().nextInt(34,140), y + new Random().nextInt(8,69),"°","#71df2d");
            }

            if (menu.getProgress() >= 10) {
                renderText(pPoseStack,x+79,y+60, menu.getProgress()+"%","#ffffff");
            } else {
                renderText(pPoseStack,x+81,y+60, menu.getProgress()+"%","#ffffff");
            }
            if (menu.getResource() != null) {
                renderItem(pPoseStack,x+66,y+32, menu.getResource());
                renderText(pPoseStack,x+86,y+36, menu.getResource(),"#ffffff");
            }
        } else {
            tick = 0;
            renderText(pPoseStack,x+32,y+12,Component.translatable("text.resource_simulator.idle").getString(),"#7f0ff7");
            //renderText(pPoseStack,x+32,y+22,"To Begin Simulation _","#7f0ff7");
        }
    }

    private void renderText(PoseStack pPoseStack, int pX ,int pY,String text,String ColorHex) {
        Minecraft.getInstance().font.draw(pPoseStack,text,pX,pY, TextColor.parseColor(ColorHex).getValue());
    }

    private void renderItem(PoseStack pPoseStack, int pX, int pY, String resource) {
        ResourceLocation texture = new ResourceLocation(Main.MOD_ID,"textures/item/resources/"+resource.toLowerCase()+".png");

        RenderSystem.setShader(GameRenderer::getPositionTexShader);
        RenderSystem.setShaderColor(1.0f, 1.0f, 1.0f, 1.0f);
        RenderSystem.setShaderTexture(0, texture);

        blit(pPoseStack, pX, pY, 0,0, 0, 16, 16,16,16);
    }

    @Override
    public void render(PoseStack pPoseStack, int pMouseX, int pMouseY, float pPartialTick) {
        renderBackground(pPoseStack);
        super.render(pPoseStack, pMouseX, pMouseY, pPartialTick);
        renderTooltip(pPoseStack, pMouseX, pMouseY);
    }


}
