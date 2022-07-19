package com.github.foss.dungerite.item.tools;

import net.minecraft.block.BlockState;
import net.minecraft.client.item.TooltipContext;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.item.ItemStack;
import net.minecraft.item.PickaxeItem;
import net.minecraft.item.ToolMaterial;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import java.util.List;

import static com.github.foss.dungerite.Dungerite.secsToTicks;

public class DungeritePickaxe extends PickaxeItem {
    public DungeritePickaxe(
            ToolMaterial material, int attackDamage, float attackSpeed, Settings settings) {
        super(material, attackDamage, attackSpeed, settings);
    }

    @Override
    public boolean postMine(
            ItemStack stack, World world, BlockState state, BlockPos pos, LivingEntity miner) {
        miner.heal(0.1F);
        miner.addStatusEffect(new StatusEffectInstance(StatusEffects.HASTE, 3 * secsToTicks, 0));
        return super.postMine(stack, world, state, pos, miner);
    }

    @Override
    public void appendTooltip(ItemStack itemStack, World world, List<Text> tooltip, TooltipContext tooltipContext) {
        tooltip.add(
                Text.translatable("item.dungerite.dungerite_pickaxe.tooltip").formatted(Formatting.RED));
    }
}
