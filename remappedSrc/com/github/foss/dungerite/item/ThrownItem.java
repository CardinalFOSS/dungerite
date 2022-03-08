package com.github.foss.dungerite.item;

import com.github.foss.dungerite.entity.ProjEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvent;
import net.minecraft.stat.Stats;
import net.minecraft.util.Hand;
import net.minecraft.world.World;

public interface ThrownItem {
    default void onThrow(
            World world,
            PlayerEntity player,
            Hand hand,
            SoundEvent soundEvent,
            ProjEntity projEntity) {

        world.playSound(
                null,
                player.getBlockPos(),
                soundEvent,
                SoundCategory.NEUTRAL,
                0.5F,
                1F
        );

        if (!world.isClient) {
            projEntity.setItem(player.getStackInHand(hand));
            projEntity.setVelocity(player, player.getPitch(), player.getYaw(), 0.0F, 1.5F, 0F);
            world.spawnEntity(projEntity);
        }

        // adds amount of item thrown as a stat
        player.incrementStat(Stats.USED.getOrCreateStat(player.getStackInHand(hand).getItem()));
        if (!player.isCreative())
            player.getStackInHand(hand).decrement(1);
    }
}
