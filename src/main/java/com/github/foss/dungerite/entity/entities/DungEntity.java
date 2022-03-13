package com.github.foss.dungerite.entity.entities;

import com.github.foss.dungerite.Dungerite;
import com.github.foss.dungerite.entity.ProjEntity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.entity.projectile.thrown.ThrownItemEntity;
import net.minecraft.item.Item;
import net.minecraft.util.hit.EntityHitResult;
import net.minecraft.world.World;

public class DungEntity extends ProjEntity {
    public DungEntity(EntityType<? extends ThrownItemEntity> entityType, World world) {
        super(entityType, world);
    }

    public DungEntity(World world, LivingEntity owner) {
        super(Dungerite.DungEntityType, owner, world);
    }

    public DungEntity(World world, double x, double y, double z) {
        super(Dungerite.DungEntityType, x, y, z, world);
    }

    @Override
    protected Item getDefaultItem() {
        return Dungerite.DUNG;
    }

    protected void onEntityHit(EntityHitResult entityHitResult) {
        super.onEntityHit(
                entityHitResult,
                1.0f,
                Dungerite.SPLAT,
                new StatusEffectInstance[]{
                        new StatusEffectInstance(StatusEffects.NAUSEA, 3 * secsToTicks, 0),
                        new StatusEffectInstance(StatusEffects.POISON, 5 * secsToTicks, 1),
                        new StatusEffectInstance(StatusEffects.HUNGER, 10 * secsToTicks, 2),
                });
    }
}
