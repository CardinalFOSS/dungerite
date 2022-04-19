package com.github.foss.dungerite.entity;

import com.github.foss.dungerite.Dungerite;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.entity.projectile.thrown.ThrownItemEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.network.Packet;
import net.minecraft.particle.ItemStackParticleEffect;
import net.minecraft.particle.ParticleEffect;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.sound.SoundEvent;
import net.minecraft.util.hit.EntityHitResult;
import net.minecraft.util.hit.HitResult;
import net.minecraft.world.World;

public abstract class ProjEntity extends ThrownItemEntity implements EntityWithPath {

    protected static final int secsToTicks = 20;

    public ProjEntity(EntityType<? extends ThrownItemEntity> entityType, World world) {
        super(entityType, world);
    }

    public ProjEntity(EntityType<? extends ThrownItemEntity> entityType, LivingEntity livingEntity, World world) {
        super(entityType, livingEntity, world);
    }

    public ProjEntity(EntityType<? extends ThrownItemEntity> entityType, double x, double y, double z, World world) {
        super(entityType, x, y, z, world);
    }

    @Environment(EnvType.CLIENT)
    protected ParticleEffect getParticleParameters() {
        ItemStack itemStack = this.getItem();
        return itemStack.isEmpty() ? ParticleTypes.ASH : new ItemStackParticleEffect(ParticleTypes.ITEM, itemStack);
    } // ? is an if statement, basically: if empty then ASH, else new ItemStackParticleEffect...

    // guessing this is internal code
    @Environment(EnvType.CLIENT)
    public void handleStatus(byte status) {
        if (status == 3) {
            ParticleEffect particleEffect = this.getParticleParameters();

            for (int i = 0; i < 8; i++) {
                this.world.addParticle(particleEffect, this.getX(), this.getY(), this.getZ(), 0.0D, 0.0D, 0.0D);
            }
        }
    }

    protected void onEntityHit(EntityHitResult entityHitResult, float damage, SoundEvent soundEvent, StatusEffectInstance[] effects) { // Listener
        super.onEntityHit(entityHitResult);
        Entity entity = entityHitResult.getEntity();

        // checks for null
        soundEvent = soundEvent != null ? soundEvent : Dungerite.SPLAT;
        effects = effects != null ? effects : new StatusEffectInstance[]{new StatusEffectInstance(StatusEffects.WATER_BREATHING, 0, 0)};

        if (entity instanceof LivingEntity livingEntity) {
            livingEntity.damage(DamageSource.thrownProjectile(this, this.getOwner()), damage);

            for (StatusEffectInstance effect : effects)
                livingEntity.addStatusEffect(effect);

            livingEntity.playSound(soundEvent, 2F, 1F);
        }
    }

    protected void onCollision(HitResult hitResult) {
        super.onCollision(hitResult);
        if (!this.world.isClient) {
            this.world.sendEntityStatus(this, (byte) 3); // no clue wut dis is, from Wiki
            this.kill();
        }
    }

    @Override
    public Packet createSpawnPacket() {
        return EntitySpawnPacket.create(this, Dungerite.PACKET_ID);
    }
}
