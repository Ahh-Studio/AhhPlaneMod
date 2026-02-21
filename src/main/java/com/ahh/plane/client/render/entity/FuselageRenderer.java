package com.ahh.plane.client.render.entity;

import com.ahh.plane.AhhPlaneMod;
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
import net.minecraft.resources.Identifier;
import org.joml.Quaternionfc;
import org.joml.Vector3f;
import org.jspecify.annotations.NonNull;

public class FuselageRenderer extends EntityRenderer<Fuselage, FuselageRenderState> {
    private static final float SQRT3 = (float) Math.sqrt(3);
    public static final Identifier TEXTURE = Identifier.fromNamespaceAndPath(AhhPlaneMod.MOD_ID,  "textures/entity/fuselage.png");
    private static final RenderType FUSELAGE_RENDER_TYPE = RenderTypes.entityTranslucent(TEXTURE);

    public FuselageRenderer(EntityRendererProvider.Context context) {
        super(context);
    }

    @Override
    public void submit(FuselageRenderState entityRenderState, PoseStack poseStack, @NonNull SubmitNodeCollector submitNodeCollector, @NonNull CameraRenderState cameraRenderState) {
        poseStack.pushPose();
        poseStack.mulPose(Axis.YP.rotationDegrees(entityRenderState.yaw));
        poseStack.mulPose(Axis.XP.rotationDegrees(entityRenderState.pitch));
        renderDodecagon(entityRenderState, poseStack, submitNodeCollector);
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
        entityRenderState.yaw = entity.getYRot();
        entityRenderState.pitch = entity.getXRot();
    }

    private static void renderDodecagon(FuselageRenderState entityRenderState, PoseStack poseStack, SubmitNodeCollector submitNodeCollector) {
        submitNodeCollector.submitCustomGeometry(poseStack, FUSELAGE_RENDER_TYPE, (pose, consumer) -> {
            consumer.addVertex(pose, -0.625F, 4, 5.5F).setUv(0F/256F, 47F/256F).setUv1(0, 10).setColor(-1).setLight(entityRenderState.lightCoords).setNormal(pose, 0, 1, 0);
            consumer.addVertex(pose, 0.625F, 4, 5.5F).setUv(5F/256F, 47F/256F).setUv1(0, 10).setColor(-1).setLight(entityRenderState.lightCoords).setNormal(pose, 0, 1, 0);
            consumer.addVertex(pose, 0.625F, 4, -5.5F).setUv(5F/256F, 0F/256F).setUv1(0, 10).setColor(-1).setLight(entityRenderState.lightCoords).setNormal(pose, 0, 1, 0);
            consumer.addVertex(pose, -0.625F, 4, -5.5F).setUv(0F/256F, 0F/256F).setUv1(0, 10).setColor(-1).setLight(entityRenderState.lightCoords).setNormal(pose, 0, 1, 0);
        });
        submitNodeCollector.submitCustomGeometry(poseStack, FUSELAGE_RENDER_TYPE, (pose, consumer) -> {
            consumer.addVertex(pose, 0.625F, 4, 5.5F).setUv(5F/256F, 47F/256F).setUv1(0, 10).setColor(-1).setLight(entityRenderState.lightCoords).setNormal(pose, 0, 1, 0);
            consumer.addVertex(pose, 0.625F * SQRT3 + 0.625F, 3.375F, 5.5F).setUv(10F/256F, 47F/256F).setUv1(0, 10).setColor(-1).setLight(entityRenderState.lightCoords).setNormal(pose, 0, 1, 0);
            consumer.addVertex(pose, 0.625F * SQRT3 + 0.625F, 3.375F, -5.5F).setUv(10F/256F, 0F/256F).setUv1(0, 10).setColor(-1).setLight(entityRenderState.lightCoords).setNormal(pose, 0, 1, 0);
            consumer.addVertex(pose, 0.625F, 4, -5.5F).setUv(5F/256F, 0F/256F).setUv1(0, 10).setColor(-1).setLight(entityRenderState.lightCoords).setNormal(pose, 0, 1, 0);
        });
    }
}
