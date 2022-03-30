package com.github.foss.dungerite.statuseffect;

import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.effect.StatusEffectCategory;

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
