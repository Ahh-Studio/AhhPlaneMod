package com.ahh.plane.entity;

import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.InterpolationHandler;
import net.minecraft.world.entity.MoverType;
import net.minecraft.world.entity.vehicle.VehicleEntity;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.storage.ValueInput;
import net.minecraft.world.level.storage.ValueOutput;
import org.jspecify.annotations.NonNull;

public class Fuselage extends VehicleEntity {
    private final InterpolationHandler interpolation = new InterpolationHandler(this, 3);
    public float landingGearDownPercentage = 0.0F;

    public Fuselage(EntityType<?> entityType, Level level) {
        super(entityType, level);
        this.blocksBuilding = true;
    }

    @Override
    public void tick() {
        super.tick();
        this.interpolation.interpolate();
        this.handlePortal();
        this.applyGravity();
        this.move(MoverType.SELF, this.getDeltaMovement());
        if (this.onGround()) {
            this.setDeltaMovement(this.getDeltaMovement().multiply(1, -0.5, 1));
        }
        this.setDeltaMovement(this.getDeltaMovement().scale(0.95));
        this.applyEffectsFromBlocks();
        this.updateInWaterStateAndDoFluidPushing();
    }

    @Override
    protected @NonNull Item getDropItem() {
        return Items.IRON_BLOCK;
    }

    @Override
    protected void readAdditionalSaveData(ValueInput valueInput) {

    }

    @Override
    protected void addAdditionalSaveData(ValueOutput valueOutput) {

    }

    @Override
    protected double getDefaultGravity() {
        return 0.1;
    }

    @Override
    public InterpolationHandler getInterpolation() {
        return this.interpolation;
    }


}
