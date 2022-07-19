package com.github.foss.dungerite.item.weapons;

import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.item.ItemStack;
import net.minecraft.item.SwordItem;
import net.minecraft.item.ToolMaterial;

import static com.github.foss.dungerite.Dungerite.secsToTicks;

public class DungeriteHoe extends SwordItem {
    public DungeriteHoe(
            ToolMaterial toolMaterial, int attackDamage, float attackSpeed, Settings settings) {
        super(toolMaterial, attackDamage, attackSpeed, settings);
    }

    @Override
    public boolean postHit(ItemStack stack, LivingEntity target, LivingEntity attacker) {
        target.addStatusEffect(new StatusEffectInstance(StatusEffects.NAUSEA, 3 * secsToTicks, 0));
        target.addStatusEffect(
                new StatusEffectInstance(StatusEffects.MINING_FATIGUE, 3 * secsToTicks, 0));
        return super.postHit(stack, target, attacker);
    }
}
