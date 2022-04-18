package com.github.foss.dungerite.entity.entities;

import com.github.foss.dungerite.Dungerite;
import com.github.foss.dungerite.entity.ProjEntity;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.entity.projectile.thrown.ThrownItemEntity;
import net.minecraft.item.Item;
import net.minecraft.sound.SoundEvent;
import net.minecraft.util.hit.EntityHitResult;
import net.minecraft.util.hit.HitResult;
import net.minecraft.world.World;
import net.minecraft.world.explosion.Explosion;


public class DungBulletEntity extends ProjEntity {
    private float damage = 0.0F;

    public float getDamage() {
        return damage;
    }

    public void setDamage(float newDamage) {
        this.damage = newDamage;
    }


    public DungBulletEntity(EntityType<? extends ThrownItemEntity> entityType, World world) {
        super(entityType, world);
    }

    public DungBulletEntity(LivingEntity livingEntity, World world) {
        super(Dungerite.DungBulletEntityType, livingEntity, world);
    }

    public DungBulletEntity(double x, double y, double z, World world) {
        super(Dungerite.DungBulletEntityType, x, y, z, world);
    }

    @Override
    protected void onEntityHit(EntityHitResult entityHitResult) {
        Entity entity = entityHitResult.getEntity();

        // checks for null
        final SoundEvent soundEvent = Dungerite.SPLAT;
        final StatusEffectInstance[] effects = new StatusEffectInstance[]{
                new StatusEffectInstance(StatusEffects.HUNGER, 10*20, 0),
                new StatusEffectInstance(StatusEffects.SLOWNESS, 3*20, 0)
        };

        if (entity instanceof LivingEntity livingEntity) {
            livingEntity.damage(DamageSource.thrownProjectile(this, this.getOwner()), damage);

            for (StatusEffectInstance effect : effects)
                livingEntity.addStatusEffect(effect);

            livingEntity.playSound(soundEvent, 2F, 1F);
        }
    }
    
    @Override
    protected void onCollision(HitResult hitResult) {
        super.onCollision(hitResult);
        if (!this.world.isClient)
            this.world.createExplosion(
                    this, this.getX(), this.getY(), this.getZ(), 2.0f, Explosion.DestructionType.BREAK);
    }

    @Override
    protected Item getDefaultItem() {
        return Dungerite.DUNG_BULLET;
    }
}
