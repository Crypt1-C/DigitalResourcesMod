package net.cryptic.digital_resources.client.screen.ResourceSimulator;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import net.cryptic.digital_resources.Main;
import net.cryptic.digital_resources.api.Classes.TickableText;
import net.cryptic.digital_resources.api.Classes.Util;
import net.minecraft.ChatFormatting;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.player.Inventory;

import java.util.ArrayList;
import java.util.Base64;
import java.util.List;

public class SimulatorScreen extends AbstractContainerScreen<SimulatorMenu> {

    public static final int WIDTH = 232;
    public static final int HEIGHT = 230;
    private static final ResourceLocation GUI = new ResourceLocation(Main.MOD_ID,"textures/gui/resource_simulator/gui.png");
    private static final ResourceLocation PLAYER_INV = new ResourceLocation(Main.MOD_ID,"textures/gui/default_gui.png");
    private List<TickableText> body = new ArrayList<>(7);


    public SimulatorScreen(SimulatorMenu pMenu, Inventory pPlayerInventory, Component pTitle) {
        super(pMenu, pPlayerInventory, pTitle);
        this.imageWidth = WIDTH;
        this.imageHeight = HEIGHT;
    }

    @Override
    protected void init() {
        super.init();
    }

    @Override
    protected void renderLabels(PoseStack pPoseStack, int pMouseX, int pMouseY) {
        int progress = this.menu.getProgress();
        int maxProgress = this.menu.getMaxProgress();

        if (menu.isCrafting()) {
            int percentage = Math.min(99, Mth.ceil(((float) progress / maxProgress) * 100F));
            this.font.drawShadow(pPoseStack, percentage + "%", 184, 123, 0xAA00FF);

            int xOff = 48;
            int yOff = 21;
            String msg = Component.literal("> ").getString();
            this.font.draw(pPoseStack, msg, xOff, yOff, 0xFFFFFF);

            xOff += this.font.width(msg);
            msg = menu.getResource() != null ? Base64.getEncoder().encodeToString(menu.getResource().getBytes()) : "";
            this.font.draw(pPoseStack, msg, xOff, yOff, 0xff005d);

            xOff += this.font.width(msg);
            this.font.draw(pPoseStack, " x" + this.menu.getCount(), xOff, yOff, 0xbb00ff);
        }

        int left = 29;
        int top = 51;
        int spacing = this.font.lineHeight + 3;
        int idx = 0;
        for (TickableText t : this.body) {
            t.render(this.font, pPoseStack, left, top + spacing * idx);
            if (t.causesNewLine()) {
                idx++;
                left = 29;
            } else {
                left += t.getWidth(this.font);
            }
        }
    }

    @Override
    protected void renderBg(PoseStack pPoseStack, float pPartialTick, int pMouseX, int pMouseY) {
        RenderSystem.setShader(GameRenderer::getPositionTexShader);
        RenderSystem.setShaderColor(1.0f,1.0f,1.0f,1.0f);
        RenderSystem.setShaderTexture(0,GUI);

        int x = this.getGuiLeft();
        int y = this.getGuiTop();

        this.blit(pPoseStack, x + 8, y, 0, 0, 216, 141, 256, 256);
        this.blit(pPoseStack, x - 14, y, 0, 141, 18, 18, 256, 256);
        this.blit(pPoseStack, x - 14, y + 25, 0, 159, 18, 18, 256, 256);

        blit(pPoseStack, x + 14, y + 135 - menu.getScaledProgress(), 18, 226 - menu.getScaledProgress(), 7, menu.getScaledProgress());

        RenderSystem.setShaderTexture(0, PLAYER_INV);
        this.blit(pPoseStack, x + 28, y + 145, 0, 0, 176, 90, 256, 256);
    }

    @Override
    protected void renderTooltip(PoseStack pPoseStack, int pX, int pY) {
        int progress = this.menu.getProgress();
        int maxProgress = this.menu.getMaxProgress();
        int rTime = Math.min(99, Mth.ceil(((float) progress / maxProgress) * 100f));

        if (this.isHovering(14,135-87,7,87, pX, pY)) {
            if (this.menu.isCrafting()) {
                this.renderTooltip(pPoseStack,Component.literal(rTime + "%").withStyle(ChatFormatting.DARK_PURPLE), pX, pY);
            } else {
                this.renderTooltip(pPoseStack,Component.literal("*"), pX, pY);
            }
        } else {
            super.renderTooltip(pPoseStack, pX, pY);
        }

    }

    @Override
    public void render(PoseStack pPoseStack, int pMouseX, int pMouseY, float pPartialTick) {
        renderBackground(pPoseStack);
        super.render(pPoseStack, pMouseX, pMouseY, pPartialTick);
        renderTooltip(pPoseStack, pMouseX, pMouseY);
    }


}
