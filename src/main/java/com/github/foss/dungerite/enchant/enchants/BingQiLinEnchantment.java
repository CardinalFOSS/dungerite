package com.github.foss.dungerite.enchant.enchants;

import com.github.foss.dungerite.Dungerite;
import net.minecraft.block.Blocks;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentTarget;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.sound.SoundCategory;
import net.minecraft.util.math.BlockPos;

import java.util.Random;

import static com.github.foss.dungerite.Dungerite.secsToTicks;

public class BingQiLinEnchantment extends Enchantment {
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
            if (new Random().nextInt(1) - level <= 0) { // 2% default, each level increases chances by 2%
                if (!livingTarget.getEquippedStack(EquipmentSlot.HEAD).isEmpty()) {
                    ItemStack headItem = livingTarget.getEquippedStack(EquipmentSlot.HEAD);
                    if (livingTarget instanceof PlayerEntity player) {
                        player.giveItemStack(headItem);
                    }
                }
                livingTarget.equipStack(EquipmentSlot.HEAD, Items.SNOW_BLOCK.getDefaultStack());
                livingTarget.world.setBlockState(new BlockPos(livingTarget.getX(), livingTarget.getY() + 1, livingTarget.getZ()), Blocks.SNOW.getDefaultState());
                livingTarget.teleport(livingTarget.getX(), livingTarget.getBlockY() - 0.1, livingTarget.getZ());
                livingTarget.addStatusEffect(new StatusEffectInstance(Dungerite.BING_QI_LIN_EFFECT, 5 * secsToTicks, 0));
                target.world.playSound(null, target.getBlockPos(), Dungerite.BING_QI_LIN, SoundCategory.VOICE, 0.3F, 1F);
            }
        }
        super.onTargetDamaged(user, target, level);
    }
}
