package com.github.foss.shitterite.entity;

import com.github.foss.shitterite.Shitterite;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.entity.projectile.thrown.ThrownItemEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.network.Packet;
import net.minecraft.particle.ItemStackParticleEffect;
import net.minecraft.particle.ParticleEffect;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.util.hit.EntityHitResult;
import net.minecraft.util.hit.HitResult;
import net.minecraft.world.World;

public class ShitEntity extends ThrownItemEntity {
    public ShitEntity(EntityType<? extends ThrownItemEntity> entityType, World world) {
        super(entityType, world);
    }

    public ShitEntity(World world, LivingEntity owner) {
        super(Shitterite.ShitEntityType, owner, world);
    }

    public ShitEntity(World world, double x, double y, double z) {
        super(Shitterite.ShitEntityType, x, y, z, world);
    }

    @Override
    protected Item getDefaultItem() {
        return Shitterite.SHIT;
    }

    @Environment(EnvType.CLIENT)
    private ParticleEffect getParticleParameters() {
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

    protected void onEntityHit(EntityHitResult entityHitResult) { // Listener
        super.onEntityHit(entityHitResult);
        Entity entity = entityHitResult.getEntity();

        if (entity instanceof LivingEntity livingEntity) {
            livingEntity.damage(DamageSource.thrownProjectile(this, this.getOwner()), 1.0F);
//            livingEntity.takeKnockback(1.0D, 1.0D, 1.0D);
            livingEntity.addStatusEffect((new StatusEffectInstance(StatusEffects.HUNGER, 10 * 20, 0)));
            livingEntity.addStatusEffect((new StatusEffectInstance(StatusEffects.POISON, 3 * 20, 1)));
            livingEntity.addStatusEffect((new StatusEffectInstance(StatusEffects.NAUSEA, 5 * 20, 0)));
            livingEntity.playSound(Shitterite.SPLAT, 2F, 1F);
        }
    }

    protected void onCollision(HitResult hitResult) {
        super.onCollision(hitResult);
        if (!this.world.isClient) {
            this.world.sendEntityStatus(this, (byte)3); // no clue wut dis is, from Wiki
            this.kill();
        }
    }

    @Override
    public Packet createSpawnPacket() {
        return EntitySpawnPacket.create(this, Shitterite.PACKET_ID);
    }
}
