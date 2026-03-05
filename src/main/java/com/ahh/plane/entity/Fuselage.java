package com.ahh.plane.entity;

import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.InterpolationHandler;
import net.minecraft.world.entity.MoverType;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.vehicle.VehicleEntity;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraft.world.level.storage.ValueInput;
import net.minecraft.world.level.storage.ValueOutput;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import org.jspecify.annotations.NonNull;

public class Fuselage extends VehicleEntity {
    private final InterpolationHandler interpolation = new InterpolationHandler(this, 3);
    public float landingGearDownPercentage = 1.0F;

    public Fuselage(EntityType<?> entityType, Level level) {
        super(entityType, level);
        this.blocksBuilding = true;
    }

    @Override
    public void animateHurt(float f) {
        this.setHurtDir(-this.getHurtDir());
        this.setHurtTime(10);
        this.setDamage(this.getDamage() * 11.0F);
    }

    @Override
    public void tick() {
        if (this.getHurtTime() > 0) {
            this.setHurtTime(this.getHurtTime() - 1);
        }

        if (this.getDamage() > 0.0F) {
            this.setDamage(this.getDamage() - 1.0F);
        }

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
    public boolean isPickable() {
        return !this.isRemoved();
    }

    @Override
    public boolean canBeCollidedWith(Entity entity) {
        return true;
    }

    @Override
    public boolean isPushable() {
        return true;
    }

    @Override
    public boolean canCollideWith(Entity entity) {
        return (entity.canBeCollidedWith(this) || entity.isPushable()) && !entity.isPassengerOfSameVehicle(this);
    }

    @Override
    public boolean hurtServer(ServerLevel serverLevel, DamageSource damageSource, float f) {
        if (this.isRemoved()) {
            return true;
        } else if (this.isInvulnerableToBase(damageSource)) {
            return false;
        } else {
            this.setHurtDir(-this.getHurtDir());
            this.setHurtTime(10);
            this.markHurt();
            this.setDamage(this.getDamage() + f * 10.0F);
            this.gameEvent(GameEvent.ENTITY_DAMAGE, damageSource.getEntity());
            boolean bl = damageSource.getEntity() instanceof Player player && player.getAbilities().instabuild;
            if ((bl || !(this.getDamage() > 400.0F)) && !this.shouldSourceDestroy(damageSource)) {
                if (bl) {
                    this.discard();
                }
            } else {
                this.destroy(serverLevel, damageSource);
            }

            return true;
        }
    }

    @Override
    protected @NonNull AABB makeBoundingBox(Vec3 vec3) {
        double d = vec3.x, e = vec3.y, f = vec3.z;
        return new AABB(d - 2.5, e, f - 8, d + 2.5, e + 5, f + 8);
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
