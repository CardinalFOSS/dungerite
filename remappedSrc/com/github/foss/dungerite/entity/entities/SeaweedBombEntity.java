package com.github.foss.dungerite.entity.entities;

import com.github.foss.dungerite.Dungerite;
import com.github.foss.dungerite.entity.ProjEntity;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.entity.projectile.thrown.ThrownItemEntity;
import net.minecraft.item.Item;
import net.minecraft.network.Packet;
import net.minecraft.particle.ParticleEffect;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.hit.EntityHitResult;
import net.minecraft.util.hit.HitResult;
import net.minecraft.world.World;
import net.minecraft.world.explosion.Explosion;

public class SeaweedBombEntity extends ProjEntity {
    public SeaweedBombEntity(EntityType<? extends ThrownItemEntity> entityType, World world) {
        super(entityType, world);
    }

    public SeaweedBombEntity(World world, LivingEntity owner) {
        super(Dungerite.SeaweedBombEntityType, owner, world);
    }

    public SeaweedBombEntity(World world, double x, double y, double z) {
        super(Dungerite.SeaweedBombEntityType, x, y, z, world);
    }

    @Override
    protected Item getDefaultItem() {
        return null;
    }

    @Environment(EnvType.CLIENT)
    protected ParticleEffect getParticleParameters() {
        return super.getParticleParameters();
    } // ? is an if statement, basically: if empty then ASH, else new ItemStackParticleEffect...

    // guessing this is internal code
    @Environment(EnvType.CLIENT)
    public void handleStatus(byte status) {
        super.handleStatus(status);
    }

    protected void onEntityHit(EntityHitResult entityHitResult) { // Listener
        super.onEntityHit(
                entityHitResult,
                2.0f,
                SoundEvents.ENTITY_CHICKEN_EGG,
                new StatusEffectInstance[] {
                        new StatusEffectInstance(StatusEffects.SLOWNESS, 3 * secsToTicks, 0),
                });
    }

    protected void onCollision(HitResult hitResult) {
        super.onCollision(hitResult);
        if (!this.world.isClient)
            this.world.createExplosion(this, this.getX(), this.getY(), this.getZ(), 2.0f, Explosion.DestructionType.BREAK);
    }

    @Override
    public Packet createSpawnPacket() {
        return super.createSpawnPacket();
    }
}
