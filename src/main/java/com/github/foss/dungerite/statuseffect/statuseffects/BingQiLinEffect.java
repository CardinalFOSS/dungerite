package com.github.foss.dungerite.statuseffect.statuseffects;

import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.effect.StatusEffectCategory;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.entity.mob.CreeperEntity;
import net.minecraft.entity.mob.HostileEntity;

public class BingQiLinEffect extends StatusEffect {
    private boolean end = false;
    public BingQiLinEffect() { super(StatusEffectCategory.BENEFICIAL, 0xA52A2A); }

    @Override
    public boolean canApplyUpdateEffect(int duration, int amplifier) {
        if (duration <= 1) {
            end = true;
        }
        return true;
    }

    @Override
    public void applyUpdateEffect(LivingEntity entity, int amplifier) {
        entity.addStatusEffect(new StatusEffectInstance(StatusEffects.WEAKNESS, 20, 255, false, false));
        entity.addStatusEffect(new StatusEffectInstance(StatusEffects.SLOWNESS, 20, 255, false, false));
        entity.addStatusEffect(new StatusEffectInstance(StatusEffects.JUMP_BOOST, 20, -7, false, false));

        if (entity instanceof HostileEntity hostileEntity) {
            hostileEntity.setAiDisabled(true);
            if (hostileEntity instanceof CreeperEntity creeper) creeper.setFuseSpeed(0);
            if (end) {
                hostileEntity.setAiDisabled(false);
                end = false;
                if (hostileEntity instanceof CreeperEntity creeper) creeper.setFuseSpeed(1);
            }
        }
    }
}
