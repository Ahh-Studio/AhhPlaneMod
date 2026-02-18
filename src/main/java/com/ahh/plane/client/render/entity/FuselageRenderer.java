package com.ahh.plane.client.render.entity;

import com.ahh.plane.AhhPlaneMod;
import com.ahh.plane.client.render.entity.state.FuselageRenderState;
import com.ahh.plane.entity.Fuselage;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.renderer.RenderPipelines;
import net.minecraft.client.renderer.SubmitNodeCollector;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.rendertype.RenderSetup;
import net.minecraft.client.renderer.rendertype.RenderType;
import net.minecraft.client.renderer.state.CameraRenderState;
import net.minecraft.resources.Identifier;
import org.jspecify.annotations.NonNull;

public class FuselageRenderer extends EntityRenderer<Fuselage, FuselageRenderState> {
    public static final Identifier TEXTURE = Identifier.fromNamespaceAndPath(AhhPlaneMod.MOD_ID,  "textures/entity/null.png");
    private static final RenderType SOLID = RenderType.create(
            "solid_no_texture",
            RenderSetup.builder(RenderPipelines.SOLID_BLOCK)
                    .useLightmap()
                    .setOutline(RenderSetup.OutlineProperty.NONE)
                    .withTexture("Sampler0", TEXTURE)
                    .createRenderSetup()
    );

    public FuselageRenderer(EntityRendererProvider.Context context) {
        super(context);
    }

    @Override
    public void submit(FuselageRenderState entityRenderState, PoseStack poseStack, @NonNull SubmitNodeCollector submitNodeCollector, @NonNull CameraRenderState cameraRenderState) {
        poseStack.pushPose();
        renderBelly(entityRenderState, poseStack, submitNodeCollector);
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
    }

    private static void renderBelly(FuselageRenderState entityRenderState, PoseStack poseStack, SubmitNodeCollector submitNodeCollector) {
        submitNodeCollector.submitCustomGeometry( // bottom
                poseStack,
                SOLID,
                (pose, consumer) -> {
                    consumer.addVertex(pose, -2, 0, -5.5F).setColor(0xFFFFFFFF).setLight(entityRenderState.lightCoords).setNormal(pose, 0, -1, 0);
                    consumer.addVertex(pose, -2, 0, 5.5F).setColor(0xFFFFFFFF).setLight(entityRenderState.lightCoords).setNormal(pose, 0, -1, 0);
                    consumer.addVertex(pose, 2, 0, 5.5F).setColor(0xFFFFFFFF).setLight(entityRenderState.lightCoords).setNormal(pose, 0, -1, 0);
                    consumer.addVertex(pose, 2, 0, -5.5F).setColor(0xFFFFFFFF).setLight(entityRenderState.lightCoords).setNormal(pose, 0, -1, 0);
                }
        );
        submitNodeCollector.submitCustomGeometry( // up
                poseStack,
                SOLID,
                (pose, consumer) -> {
                    consumer.addVertex(pose, -2, 0.2F, -5.5F).setColor(0xFFFFFFFF).setLight(entityRenderState.lightCoords).setNormal(pose, 0, 1, 0);
                    consumer.addVertex(pose, -2, 0.2F, 5.5F).setColor(0xFFFFFFFF).setLight(entityRenderState.lightCoords).setNormal(pose, 0, 1, 0);
                    consumer.addVertex(pose, 2, 0.2F, 5.5F).setColor(0xFFFFFFFF).setLight(entityRenderState.lightCoords).setNormal(pose, 0, 1, 0);
                    consumer.addVertex(pose, 2, 0.2F, -5.5F).setColor(0xFFFFFFFF).setLight(entityRenderState.lightCoords).setNormal(pose, 0, 1, 0);
                }
        );
        submitNodeCollector.submitCustomGeometry( // left
                poseStack,
                SOLID,
                (pose, consumer) -> {
                    consumer.addVertex(pose, -2, 0F, -5.5F).setColor(0xFFFFFFFF).setLight(entityRenderState.lightCoords).setNormal(pose, -1, 0, 0);
                    consumer.addVertex(pose, -2, 0F, 5.5F).setColor(0xFFFFFFFF).setLight(entityRenderState.lightCoords).setNormal(pose, -1, 0, 0);
                    consumer.addVertex(pose, -2, 0.2F, 5.5F).setColor(0xFFFFFFFF).setLight(entityRenderState.lightCoords).setNormal(pose, -1, 0, 0);
                    consumer.addVertex(pose, -2, 0.2F, -5.5F).setColor(0xFFFFFFFF).setLight(entityRenderState.lightCoords).setNormal(pose, -1, 0, 0);
                }
        );
        submitNodeCollector.submitCustomGeometry( // right
                poseStack,
                SOLID,
                (pose, consumer) -> {
                    consumer.addVertex(pose, 2, 0F, -5.5F).setColor(0xFFFFFFFF).setLight(entityRenderState.lightCoords).setNormal(pose, 1, 0, 0);
                    consumer.addVertex(pose, 2, 0F, 5.5F).setColor(0xFFFFFFFF).setLight(entityRenderState.lightCoords).setNormal(pose, 1, 0, 0);
                    consumer.addVertex(pose, 2, 0.2F, 5.5F).setColor(0xFFFFFFFF).setLight(entityRenderState.lightCoords).setNormal(pose, 1, 0, 0);
                    consumer.addVertex(pose, 2, 0.2F, -5.5F).setColor(0xFFFFFFFF).setLight(entityRenderState.lightCoords).setNormal(pose, 1, 0, 0);
                }
        );
        submitNodeCollector.submitCustomGeometry( // forward
                poseStack,
                SOLID,
                (pose, consumer) -> {
                    consumer.addVertex(pose, -2, 0F, 5.5F).setColor(0xFFFFFFFF).setLight(entityRenderState.lightCoords).setNormal(pose, 0, 0, 1);
                    consumer.addVertex(pose, -2, 0.2F, 5.5F).setColor(0xFFFFFFFF).setLight(entityRenderState.lightCoords).setNormal(pose, 0, 0, 1);
                    consumer.addVertex(pose, 2, 0.2F, 5.5F).setColor(0xFFFFFFFF).setLight(entityRenderState.lightCoords).setNormal(pose, 0, 0, 1);
                    consumer.addVertex(pose, 2, 0F, 5.5F).setColor(0xFFFFFFFF).setLight(entityRenderState.lightCoords).setNormal(pose, 0, 0, 1);
                }
        );
        submitNodeCollector.submitCustomGeometry( // back
                poseStack,
                SOLID,
                (pose, consumer) -> {
                    consumer.addVertex(pose, -2, 0F, -5.5F).setColor(0xFFFFFFFF).setLight(entityRenderState.lightCoords).setNormal(pose, 0, 0, -1);
                    consumer.addVertex(pose, -2, 0.2F, -5.5F).setColor(0xFFFFFFFF).setLight(entityRenderState.lightCoords).setNormal(pose, 0, 0, -1);
                    consumer.addVertex(pose, 2, 0.2F, -5.5F).setColor(0xFFFFFFFF).setLight(entityRenderState.lightCoords).setNormal(pose, 0, 0, -1);
                    consumer.addVertex(pose, 2, 0F, -5.5F).setColor(0xFFFFFFFF).setLight(entityRenderState.lightCoords).setNormal(pose, 0, 0, -1);
                }
        );
    }
}
