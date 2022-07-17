package com.github.foss.dungerite.enchant.enchants;

import com.github.foss.dungerite.Dungerite;
import com.github.foss.dungerite.enchant.EnchantsWithPath;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.enchantment.EnchantmentTarget;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.sound.SoundCategory;
import net.minecraft.util.math.BlockPos;

import java.util.Random;

import static com.github.foss.dungerite.Dungerite.secsToTicks;

public class BingQiLinEnchantment extends EnchantsWithPath {
    public BingQiLinEnchantment() {
        super(Rarity.RARE, EnchantmentTarget.WEAPON, new EquipmentSlot[] {EquipmentSlot.MAINHAND} );
    }

    @Override
    public int getMinPower(int level) {
        return 1;
    }

    @Override
    public int getMaxPower(int level) {
        return 3;
    }

    @Override
    public void onTargetDamaged(LivingEntity user, Entity target, int level) {
        if (target instanceof LivingEntity livingTarget) {
            if (new Random().nextInt(50) - level <= 0) { // 2% default, each level increases chances by 2%
                livingTarget.world.setBlockState(new BlockPos(livingTarget.getX(), livingTarget.getY() + 1, livingTarget.getZ()), Blocks.SNOW.getDefaultState());
                livingTarget.teleport(livingTarget.getX(), livingTarget.getBlockY() - 0.5, livingTarget.getZ());
                livingTarget.addStatusEffect(new StatusEffectInstance(Dungerite.BING_QI_LIN_EFFECT, 5 * secsToTicks, 0));
                target.world.playSound(null, target.getBlockPos(), Dungerite.BING_QI_LIN, SoundCategory.VOICE, 0.3F, 1F);
            }
        }
        super.onTargetDamaged(user, target, level);
    }

    @Override
    public String getPath() {
        return "bing_qi_lin_enchant";
    }
}
