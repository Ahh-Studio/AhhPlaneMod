package com.ahh.plane.client.render.entity.model;

import com.ahh.plane.AhhPlaneMod;
import com.ahh.plane.client.render.entity.state.FuselageRenderState;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.CubeListBuilder;
import net.minecraft.client.model.geom.builders.LayerDefinition;
import net.minecraft.client.model.geom.builders.MeshDefinition;
import net.minecraft.client.model.geom.builders.PartDefinition;
import net.minecraft.resources.Identifier;
import net.minecraft.util.Mth;

public class FuselageModel extends EntityModel<FuselageRenderState> {
    public static final ModelLayerLocation LAYER = new ModelLayerLocation(Identifier.fromNamespaceAndPath(AhhPlaneMod.MOD_ID, "fuselage"), "main");
    private final ModelPart gear1, gear2, gear3;

    public FuselageModel(ModelPart modelPart) {
        super(modelPart);
        this.gear1 = modelPart.getChild("gear1");
        this.gear2 = modelPart.getChild("gear2");
        this.gear3 = modelPart.getChild("gear3");
    }

    public static LayerDefinition createLayer() {
        MeshDefinition meshDefinition = new MeshDefinition();
        PartDefinition partDefinition = meshDefinition.getRoot();

        // Landing Gear
        partDefinition.addOrReplaceChild("gear1",
                CubeListBuilder.create()
                        .texOffs(0, 0).addBox(-6, -16, 13, 5, 8, 8)
                        .texOffs(26, 0).addBox(3, -16, 13, 5, 8, 8)
                        .texOffs(52, 0).addBox(-1, -14, 15, 4, 14, 4),
                PartPose.offset(-40, 16, 67));
        partDefinition.addOrReplaceChild("gear2",
                CubeListBuilder.create()
                        .texOffs(0, 16).addBox(-6, -16, 13, 5, 8, 8)
                        .texOffs(26, 16).addBox(3, -16, 13, 5, 8, 8)
                        .texOffs(52, 18).addBox(-1, -14, 15, 4, 14, 4),
                PartPose.offset(40, 16, 67));
        partDefinition.addOrReplaceChild("gear3",
                CubeListBuilder.create()
                        .texOffs(0, 32).addBox(-4.5F, -16, -1, 3, 5, 5)
                        .texOffs(16, 32).addBox(2.5F, -16, -1, 3, 5, 5)
                        .texOffs(32, 32).addBox(-1.5F, -15, 0, 4, 15, 3),
                PartPose.offset(-0.5F, 16, -31));


        return LayerDefinition.create(meshDefinition, 256, 256);
    }

    @Override
    public void setupAnim(FuselageRenderState state) {
        super.setupAnim(state);

        gear1.setRotation(0, 0, Mth.HALF_PI * (1.0F - state.landingGearDownPercentage));
        gear2.setRotation(0, 0, -Mth.HALF_PI * (1.0F - state.landingGearDownPercentage));
        gear3.setRotation(Mth.HALF_PI * (1.0F - state.landingGearDownPercentage), 0, 0);
    }
}
