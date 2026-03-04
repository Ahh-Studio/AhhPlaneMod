package com.ahh.plane.client;

import com.ahh.plane.client.render.entity.FuselageRenderer;
import com.ahh.plane.client.render.entity.model.FuselageModel;
import com.ahh.plane.entity.ModEntityTypes;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.rendering.v1.EntityModelLayerRegistry;
import net.minecraft.client.renderer.entity.EntityRenderers;

public class AhhPlaneClient implements ClientModInitializer {
    @Override
    public void onInitializeClient() {
        EntityRenderers.register(ModEntityTypes.FUSELAGE, FuselageRenderer::new);
        EntityModelLayerRegistry.registerModelLayer(FuselageModel.LAYER, FuselageModel::createLayer);
    }
}
