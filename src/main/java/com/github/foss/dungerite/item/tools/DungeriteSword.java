package com.github.foss.dungerite.item.tools;

import net.minecraft.client.item.TooltipContext;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.item.HoeItem;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ToolMaterial;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;
import net.minecraft.world.World;

import java.util.List;

import static com.github.foss.dungerite.Dungerite.secsToTicks;

public class DungeriteSword extends HoeItem {
    public DungeriteSword(
            ToolMaterial material, int attackDamage, float attackSpeed, Settings settings) {
        super(material, attackDamage, attackSpeed, settings);
    }

    @Override
    public boolean postHit(ItemStack stack, LivingEntity target, LivingEntity attacker) {
        target.addStatusEffect(new StatusEffectInstance(StatusEffects.SPEED, 5 * secsToTicks, 1));
        target.addStatusEffect(
                new StatusEffectInstance(StatusEffects.STRENGTH, 5 * secsToTicks, 1));
        target.heal(3.0F);
        return super.postHit(stack, target, attacker);
    }

    @Override
    public void appendTooltip(ItemStack itemStack, World world, List<Text> tooltip, TooltipContext tooltipContext) {
        tooltip.add(
                Text.translatable("item.dungerite.dungerite_sword.tooltip").formatted(Formatting.RED));
    }
}
