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
import net.minecraft.client.renderer.rendertype.RenderTypes;
import net.minecraft.client.renderer.state.CameraRenderState;
import net.minecraft.resources.Identifier;
import net.minecraft.util.ARGB;

public class FuselageRenderer extends EntityRenderer<Fuselage, FuselageRenderState> {
    private static final RenderType SOLID = RenderType.create(
            "solid_no_texture",
            RenderSetup.builder(RenderPipelines.DEBUG_QUADS)
                    .useLightmap()
                    .setOutline(RenderSetup.OutlineProperty.NONE)
                    .createRenderSetup()
    );

    public FuselageRenderer(EntityRendererProvider.Context context) {
        super(context);
    }

    @Override
    public void submit(FuselageRenderState entityRenderState, PoseStack poseStack, SubmitNodeCollector submitNodeCollector, CameraRenderState cameraRenderState) {
        poseStack.pushPose();
        submitNodeCollector.submitCustomGeometry(
                poseStack,
                SOLID,
                (pose, consumer) -> {
                    consumer.addVertex(pose, -2, 0, -5.5F)
                            .setColor(0xFFFFFFFF)
                            .setLight(entityRenderState.lightCoords)
                            .setNormal(pose, 0, 1, 0);
                    consumer.addVertex(pose, -2, 0, 5.5F)
                            .setColor(0xFFFFFFFF)
                            .setLight(entityRenderState.lightCoords)
                            .setNormal(pose, 0, 1, 0);
                    consumer.addVertex(pose, 2, 0, 5.5F)
                            .setColor(0xFFFFFFFF)
                            .setLight(entityRenderState.lightCoords)
                            .setNormal(pose, 0, 1, 0);
                    consumer.addVertex(pose, 2, 0, -5.5F)
                            .setColor(0xFFFFFFFF)
                            .setLight(entityRenderState.lightCoords)
                            .setNormal(pose, 0, 1, 0);
                }
        );
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
}
