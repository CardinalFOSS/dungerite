package com.github.foss.dungerite.mixin.player;

import com.github.foss.dungerite.Dungerite;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.ItemEntity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.text.Text;
import net.minecraft.text.TranslatableText;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.Random;

@Mixin(PlayerEntity.class)
public abstract class MixinPlayerEntity extends LivingEntity {

    private MixinPlayerEntity(EntityType<? extends LivingEntity> entityType, World world) {
        super(entityType, world);
        throw new AssertionError(); // no clue what this means
    }

    @Shadow
    public abstract void sendMessage(Text message, boolean actionBar);

    // method that randomly makes player poop after sleep
    @Inject(method = "wakeUp(ZZ)V", at = @At("TAIL"))
    private void poopPants(CallbackInfo ci) {
        // System.out.println("Woke up!");
        if (new Random().nextInt(4) != 0) return;
        int dungs = new Random().nextInt(4) + 1;
        world.spawnEntity(new ItemEntity(
                world,
                this.getBlockX(),
                this.getBlockY(),
                this.getBlockZ(),
                new ItemStack(Dungerite.DUNG, dungs))
        );
        this.sendMessage(new TranslatableText("mixin.dungerite.poopPants"), false);
    }

    // random method that is activated every tick (0.05 seconds)
    @Inject(method = "travel", at = @At("TAIL"))
    private void pooping(Vec3d movementInput, CallbackInfo ci) {
        if (!this.isSneaking() || new Random().nextInt(100000) != 1) return; // REVERT
        world.spawnEntity(new ItemEntity(
                world,
                this.getBlockX(),
                this.getBlockY(),
                this.getBlockZ(),
                new ItemStack(Dungerite.DUNG, 1))
        );
        this.sendMessage(new TranslatableText("mixin.dungerite.poopPants"), false);
    }
}
