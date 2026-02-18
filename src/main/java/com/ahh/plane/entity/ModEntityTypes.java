package com.ahh.plane.entity;

import com.ahh.plane.AhhPlaneMod;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.Identifier;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;

public class ModEntityTypes {
    public static final EntityType<Fuselage> FUSELAGE =  register(
            "fuselage",
            EntityType.Builder.of(Fuselage::new, MobCategory.MISC)
                    .noLootTable()
                    .sized(11.0F, 4F)
                    .eyeHeight(3.0F)
                    .clientTrackingRange(10)
    );


    public static void init() {

    }

    public static <T extends Entity> EntityType<T> register(String id, EntityType.Builder<T> entityType) {
        return Registry.register(BuiltInRegistries.ENTITY_TYPE,
                Identifier.fromNamespaceAndPath(AhhPlaneMod.MOD_ID, id),
                entityType.build(ResourceKey.create(Registries.ENTITY_TYPE, Identifier.fromNamespaceAndPath(AhhPlaneMod.MOD_ID, id))));
    }
}
