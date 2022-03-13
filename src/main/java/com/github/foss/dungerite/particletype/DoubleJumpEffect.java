package com.github.foss.dungerite.particletype;

import com.github.foss.dungerite.Dungerite;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.sound.SoundCategory;
import net.minecraft.world.World;

import java.util.Random;

public class DoubleJumpEffect {
    private final static Random random = new Random();

    public static void play(PlayerEntity player) {
        play(player, player);
    }

    public static void play(PlayerEntity localPlayer, PlayerEntity effectPlayer) {
        World world = localPlayer.getEntityWorld();
        world.playSound(localPlayer, effectPlayer.getBlockPos(), Dungerite.FART, SoundCategory.PLAYERS, 0.4f, 1);

        for (int i = 0; i < 5; ++i) {
            double d = random.nextGaussian() * 0.02D;
            double e = random.nextGaussian() * 0.02D;
            double f = random.nextGaussian() * 0.02D;
            world.addParticle(Dungerite.FART_PARTICLE, effectPlayer.getParticleX(1.0D), effectPlayer.getY(), effectPlayer.getParticleZ(1.0D), d, e, f);
        }
    }
}
