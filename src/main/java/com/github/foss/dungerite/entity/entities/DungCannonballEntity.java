package com.github.foss.dungerite.entity.entities;

import com.github.foss.dungerite.Dungerite;
import com.github.foss.dungerite.entity.ProjEntity;
import com.github.foss.dungerite.item.InitItems;
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

import static com.github.foss.dungerite.Dungerite.secsToTicks;


public class DungCannonballEntity extends ProjEntity {
    private float damage = 0.0F;
    private float explosionPower = 0.0F;
    private int tick = 0;

    public float getDamage() {
        return damage;
    }

    public void setDamage(float newDamage) {
        this.damage = newDamage;
    }

    public float getExplosionPower() {
        return explosionPower;
    }

    public void setExplosionPower(float newExplosionPower) {
        this.explosionPower = newExplosionPower;
    }

    public DungCannonballEntity(EntityType<? extends ThrownItemEntity> entityType, World world) {
        super(entityType, world);
    }

    public DungCannonballEntity(World world, LivingEntity owner) {
        super(Dungerite.DungCannonballEntityType, owner, world);
    }

    public DungCannonballEntity(World world, double x, double y, double z) {
        super(Dungerite.DungCannonballEntityType, x, y, z, world);
    }


    @Override
    protected void onEntityHit(EntityHitResult entityHitResult) {
        Entity entity = entityHitResult.getEntity();

        // checks for null
        final SoundEvent soundEvent = Dungerite.SPLAT;
        final StatusEffectInstance[] effects = new StatusEffectInstance[]{
                new StatusEffectInstance(StatusEffects.NAUSEA, 3 * secsToTicks, 0),
                new StatusEffectInstance(StatusEffects.SLOWNESS, 5 * secsToTicks, 0)
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
                    this, this.getX(), this.getY(), this.getZ(), explosionPower * 2.0F + 0.5F, Explosion.DestructionType.DESTROY);
    }

    @Override
    protected Item getDefaultItem() {
        return InitItems.items[2]; // DungBullet
    }

    @Override
    public String getPath() {
        return "dung_cannonball";
    }

    // has no gravity, meaning it might remain forever, so set a tick value for it to cease
    @Override
    public void tick() {
        super.tick();

        final int lifeSpanInSecs = 5;
        tick++;
        if (tick >= lifeSpanInSecs * secsToTicks) {
            if (!this.world.isClient)
                this.world.createExplosion(
                        this, this.getX(), this.getY(), this.getZ(), explosionPower * 2.0F + 0.5F, Explosion.DestructionType.DESTROY);
            this.world.sendEntityStatus(this, (byte) 3);
            this.kill();
        }
    }
}
