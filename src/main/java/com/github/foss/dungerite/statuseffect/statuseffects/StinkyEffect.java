package com.github.foss.dungerite.statuseffect.statuseffects;

import com.github.foss.dungerite.Dungerite;
import com.github.foss.dungerite.mixin.MixinHostileEntity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.TargetPredicate;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.effect.StatusEffectCategory;
import net.minecraft.entity.mob.HostileEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.math.Box;

import java.util.ArrayList;

public class StinkyEffect extends StatusEffect {
    // no method bodies because processing is done by hostile mob mixin
    public StinkyEffect() {
        super(StatusEffectCategory.BENEFICIAL, 0xA52A2A);
    }

    @Override
    public boolean canApplyUpdateEffect(int duration, int amplifier) {
        return true;
    }

    @Override
    public void applyUpdateEffect(LivingEntity entity, int amplifier) {}
}
