package com.github.foss.shitterite.mixin;

import com.github.foss.shitterite.Shitterite;
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

    @Shadow public abstract void sendMessage(Text message, boolean actionBar);

    private MixinPlayerEntity(EntityType<? extends LivingEntity> entityType, World world) {
        super(entityType, world);
        throw new AssertionError(); // no clue what this means
    }

    //
    @Inject(method = "wakeUp(ZZ)V", at = @At("TAIL"))
    private void shitPants(CallbackInfo ci) {
        // System.out.println("Woke up!");
        if (new Random().nextInt(4) != 0) return;
        int shits = new Random().nextInt(4) + 1;
        world.spawnEntity(new ItemEntity(
                world,
                this.getBlockX(),
                this.getBlockY(),
                this.getBlockZ(),
                new ItemStack(Shitterite.SHIT, shits))
        );
        this.sendMessage( new TranslatableText("mixin.shitterite.shitPants"), false );
    }

    // random method that is activated every tick (0.05 seconds)
    @Inject(method = "travel", at = @At("TAIL"))
    private void shitting(Vec3d movementInput, CallbackInfo ci) {
        if (!this.isSneaking() || new Random().nextInt(100000) != 1) return; // REVERT
        world.spawnEntity(new ItemEntity(
                world,
                this.getBlockX(),
                this.getBlockY(),
                this.getBlockZ(),
                new ItemStack(Shitterite.SHIT, 1))
        );
        this.sendMessage( new TranslatableText("mixin.shitterite.shitPants"), false );
    }
}
