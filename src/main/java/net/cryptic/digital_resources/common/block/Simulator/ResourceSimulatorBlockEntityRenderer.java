package net.cryptic.digital_resources.common.block.Simulator;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Quaternion;
import com.mojang.math.Vector3f;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.LightTexture;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.block.model.ItemTransforms;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.client.renderer.entity.ItemRenderer;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.client.resources.model.BakedModel;
import net.minecraft.core.BlockPos;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LightLayer;
import net.minecraft.world.level.block.state.BlockBehaviour;

import java.util.Objects;
import java.util.Vector;

public class ResourceSimulatorBlockEntityRenderer implements BlockEntityRenderer<ResourceSimulatorBlockEntity> {

    public ResourceSimulatorBlockEntityRenderer(BlockEntityRendererProvider.Context context) {

    }

    @Override
    public void render(ResourceSimulatorBlockEntity pBlockEntity, float pPartialTick, PoseStack pPoseStack, MultiBufferSource pBufferSource, int pPackedLight, int pPackedOverlay) {

        ItemRenderer itemRenderer = Minecraft.getInstance().getItemRenderer();

        ItemStack output = pBlockEntity.getRenderedStack();
        ItemStack upgrade1 = pBlockEntity.getRenderedUpgrade1();
        ItemStack upgrade2 = pBlockEntity.getRenderedUpgrade2();
        long time = System.currentTimeMillis() % 50000L;
        float speed = 0.05f;

        // render input module on top
        pPoseStack.pushPose();
        pPoseStack.translate(0.5f, 1.2f + Math.sin(time*0.005)*0.03, 0.5);  //x, y, z pos
        pPoseStack.scale(1f, 1f, 1f); // size
        //pPoseStack.mulPose(Vector3f.YN.rotationDegrees(pBlockEntity.getBlockState().getValue(ResourceSimulatorBlock.FACING).toYRot()));
        pPoseStack.mulPose(Vector3f.YN.rotationDegrees(time*speed));
        BakedModel input_model = itemRenderer.getModel(output, null, null, 0);
        itemRenderer.render(output, ItemTransforms.TransformType.GROUND, true, pPoseStack, pBufferSource, LightTexture.FULL_BRIGHT, OverlayTexture.NO_OVERLAY, input_model);
        pPoseStack.popPose();

    }
}
