package com.ahh.plane.client.render.entity;

import com.ahh.plane.AhhPlaneMod;
import com.ahh.plane.client.render.entity.model.FuselageModel;
import com.ahh.plane.client.render.entity.state.FuselageRenderState;
import com.ahh.plane.entity.Fuselage;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import net.minecraft.client.renderer.RenderPipelines;
import net.minecraft.client.renderer.SubmitNodeCollector;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.rendertype.RenderSetup;
import net.minecraft.client.renderer.rendertype.RenderType;
import net.minecraft.client.renderer.rendertype.RenderTypes;
import net.minecraft.client.renderer.state.CameraRenderState;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.resources.Identifier;
import net.minecraft.util.Util;
import org.joml.Quaternionfc;
import org.joml.Vector3f;
import org.jspecify.annotations.NonNull;

import java.util.function.BiFunction;

public class FuselageRenderer extends EntityRenderer<Fuselage, FuselageRenderState> {
    private static final float SQRT3 = ((int) (Math.sqrt(3) * 10000)) / 10000F;
    private static final float SIN30 = 0.5F;
    private static final float COS30 = (float) Math.sqrt(3) / 2;
    public static final Identifier TEXTURE = Identifier.fromNamespaceAndPath(AhhPlaneMod.MOD_ID,  "textures/entity/fuselage.png");
    private static final RenderType FUSELAGE_RENDER_TYPE = RenderTypes.entityTranslucent(TEXTURE);

    private final FuselageModel model;

    public FuselageRenderer(EntityRendererProvider.Context context) {
        super(context);
        this.model = new FuselageModel(context.bakeLayer(FuselageModel.LAYER));
    }

    @Override
    public void submit(FuselageRenderState entityRenderState, PoseStack poseStack, @NonNull SubmitNodeCollector submitNodeCollector, @NonNull CameraRenderState cameraRenderState) {
        poseStack.pushPose();
        // 旋转模型，使朝向正前方
        poseStack.mulPose(Axis.YP.rotationDegrees(180F - entityRenderState.yaw));
        poseStack.mulPose(Axis.XP.rotationDegrees(-entityRenderState.pitch));

        renderDodecagon(entityRenderState, poseStack, submitNodeCollector);
        submitNodeCollector.submitModel(this.model, entityRenderState, poseStack, FUSELAGE_RENDER_TYPE,
                entityRenderState.lightCoords, OverlayTexture.NO_OVERLAY, entityRenderState.outlineColor, null);
        poseStack.popPose();

        super.submit(entityRenderState, poseStack, submitNodeCollector, cameraRenderState);
    }

    @Override
    public FuselageRenderState createRenderState() {
        return new FuselageRenderState();
    }

    @Override
    public void extractRenderState(Fuselage entity, FuselageRenderState entityRenderState, float f) {
        super.extractRenderState(entity, entityRenderState, f);
        entityRenderState.yaw = entity.getYRot(f);
        entityRenderState.pitch = entity.getXRot(f);
        entityRenderState.landingGearDownPercentage = entity.landingGearDownPercentage;
    }

    private static void renderDodecagon(FuselageRenderState entityRenderState, PoseStack poseStack, SubmitNodeCollector submitNodeCollector) {
        submitNodeCollector.submitCustomGeometry(poseStack, FUSELAGE_RENDER_TYPE, (pose, consumer) -> {
            consumer.addVertex(pose, -0.625F, 5, 5.5F).setUv(0F/256F, 44F/256F).setUv1(0, 10).setColor(-1).setLight(entityRenderState.lightCoords).setNormal(pose, 0, 1, 0);
            consumer.addVertex(pose, 0.625F, 5, 5.5F).setUv(5F/256F, 44F/256F).setUv1(0, 10).setColor(-1).setLight(entityRenderState.lightCoords).setNormal(pose, 0, 1, 0);
            consumer.addVertex(pose, 0.625F, 5, -5.5F).setUv(5F/256F, 0F/256F).setUv1(0, 10).setColor(-1).setLight(entityRenderState.lightCoords).setNormal(pose, 0, 1, 0);
            consumer.addVertex(pose, -0.625F, 5, -5.5F).setUv(0F/256F, 0F/256F).setUv1(0, 10).setColor(-1).setLight(entityRenderState.lightCoords).setNormal(pose, 0, 1, 0);
        });
        submitNodeCollector.submitCustomGeometry(poseStack, FUSELAGE_RENDER_TYPE, (pose, consumer) -> {
            consumer.addVertex(pose, 0.625F, 5, 5.5F).setUv(5F/256F, 44F/256F).setUv1(0, 10).setColor(-1).setLight(entityRenderState.lightCoords).setNormal(pose, SIN30, COS30, 0);
            consumer.addVertex(pose, 0.625F * SQRT3 + 0.625F, 4.375F, 5.5F).setUv(10F/256F, 44F/256F).setUv1(0, 10).setColor(-1).setLight(entityRenderState.lightCoords).setNormal(pose, SIN30, COS30, 0);
            consumer.addVertex(pose, 0.625F * SQRT3 + 0.625F, 4.375F, -5.5F).setUv(10F/256F, 0F/256F).setUv1(0, 10).setColor(-1).setLight(entityRenderState.lightCoords).setNormal(pose, SIN30, COS30, 0);
            consumer.addVertex(pose, 0.625F, 5, -5.5F).setUv(5F/256F, 0F/256F).setUv1(0, 10).setColor(-1).setLight(entityRenderState.lightCoords).setNormal(pose, SIN30, COS30, 0);
        });
        submitNodeCollector.submitCustomGeometry(poseStack, FUSELAGE_RENDER_TYPE, (pose, consumer) -> {
            consumer.addVertex(pose, 0.625F * SQRT3 + 0.625F, 4.375F, 5.5F).setUv(10F/256F, 44F/256F).setUv1(0, 10).setColor(-1).setLight(entityRenderState.lightCoords).setNormal(pose, COS30, SIN30, 0);
            consumer.addVertex(pose, 0.625F * (SQRT3 + 1) + 0.625F, 4.375F - (0.625F * SQRT3), 5.5F).setUv(15F/256F, 44F/256F).setUv1(0, 10).setColor(-1).setLight(entityRenderState.lightCoords).setNormal(pose, COS30, SIN30, 0);
            consumer.addVertex(pose, 0.625F * (SQRT3 + 1) + 0.625F, 4.375F - (0.625F * SQRT3), -5.5F).setUv(15F/256F, 0F/256F).setUv1(0, 10).setColor(-1).setLight(entityRenderState.lightCoords).setNormal(pose, COS30, SIN30, 0);
            consumer.addVertex(pose, 0.625F * SQRT3 + 0.625F, 4.375F, -5.5F).setUv(10F/256F, 0F/256F).setUv1(0, 10).setColor(-1).setLight(entityRenderState.lightCoords).setNormal(pose, COS30, SIN30, 0);
        });
        submitNodeCollector.submitCustomGeometry(poseStack, FUSELAGE_RENDER_TYPE, (pose, consumer) -> {
            consumer.addVertex(pose, 0.625F * (SQRT3 + 1) + 0.625F, 4.375F - (0.625F * SQRT3), 5.5F).setUv(15F/256F, 44F/256F).setUv1(0, 10).setColor(-1).setLight(entityRenderState.lightCoords).setNormal(pose, 1, 0.2F, 0);
            consumer.addVertex(pose, 0.625F * (SQRT3 + 1) + 0.625F, 3.125F - (0.625F * SQRT3), 5.5F).setUv(20F/256F, 44F/256F).setUv1(0, 10).setColor(-1).setLight(entityRenderState.lightCoords).setNormal(pose, 1, 0.2F, 0);
            consumer.addVertex(pose, 0.625F * (SQRT3 + 1) + 0.625F, 3.125F - (0.625F * SQRT3), -5.5F).setUv(20F/256F, 0F/256F).setUv1(0, 10).setColor(-1).setLight(entityRenderState.lightCoords).setNormal(pose, 1, 0.2F, 0);
            consumer.addVertex(pose, 0.625F * (SQRT3 + 1) + 0.625F, 4.375F - (0.625F * SQRT3), -5.5F).setUv(15F/256F, 0F/256F).setUv1(0, 10).setColor(-1).setLight(entityRenderState.lightCoords).setNormal(pose, 1, 0.2F, 0);
        });
        submitNodeCollector.submitCustomGeometry(poseStack, FUSELAGE_RENDER_TYPE, (pose, consumer) -> {
            consumer.addVertex(pose, 0.625F * SQRT3 + 1.25F, 3.125F - (0.625F * SQRT3), 5.5F).setUv(20F/256F, 44F/256F).setUv1(0, 10).setColor(-1).setLight(entityRenderState.lightCoords).setNormal(pose, -COS30, SIN30, 0);
            consumer.addVertex(pose, 0.625F * SQRT3 + 0.625F, 3.125F - (1.25F * SQRT3), 5.5F).setUv(25F/256F, 44F/256F).setUv1(0, 10).setColor(-1).setLight(entityRenderState.lightCoords).setNormal(pose, -COS30, SIN30, 0);
            consumer.addVertex(pose, 0.625F * SQRT3 + 0.625F, 3.125F - (1.25F * SQRT3), -5.5F).setUv(25F/256F, 0F/256F).setUv1(0, 10).setColor(-1).setLight(entityRenderState.lightCoords).setNormal(pose, -COS30, SIN30, 0);
            consumer.addVertex(pose, 0.625F * SQRT3 + 1.25F, 3.125F - (0.625F * SQRT3), -5.5F).setUv(20F/256F, 0F/256F).setUv1(0, 10).setColor(-1).setLight(entityRenderState.lightCoords).setNormal(pose, -COS30, SIN30, 0);
        });
        submitNodeCollector.submitCustomGeometry(poseStack, FUSELAGE_RENDER_TYPE, (pose, consumer) -> {
            consumer.addVertex(pose, 0.625F * SQRT3 + 0.625F, 3.125F - (1.25F * SQRT3), 5.5F).setUv(25F/256F, 44F/256F).setUv1(0, 10).setColor(-1).setLight(entityRenderState.lightCoords).setNormal(pose, -SIN30, COS30, 0);
            consumer.addVertex(pose, 0.625F, 2.5F - 1.25F * SQRT3, 5.5F).setUv(30F/256F, 44F/256F).setUv1(0, 10).setColor(-1).setLight(entityRenderState.lightCoords).setNormal(pose, -SIN30, COS30, 0);
            consumer.addVertex(pose, 0.625F, 2.5F - 1.25F * SQRT3, -5.5F).setUv(30F/256F, 0F/256F).setUv1(0, 10).setColor(-1).setLight(entityRenderState.lightCoords).setNormal(pose, -SIN30, COS30, 0);
            consumer.addVertex(pose, 0.625F * SQRT3 + 0.625F, 3.125F - (1.25F * SQRT3), -5.5F).setUv(25F/256F, 0F/256F).setUv1(0, 10).setColor(-1).setLight(entityRenderState.lightCoords).setNormal(pose, -SIN30, COS30, 0);
        });
    }
}
