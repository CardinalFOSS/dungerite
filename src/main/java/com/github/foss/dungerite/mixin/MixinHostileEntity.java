package com.github.foss.dungerite.mixin;

import com.github.foss.dungerite.Dungerite;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.ai.goal.FleeEntityGoal;
import net.minecraft.entity.mob.HostileEntity;
import net.minecraft.entity.mob.PathAwareEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(HostileEntity.class)
public abstract class MixinHostileEntity extends PathAwareEntity {
    private final FleeEntityGoal<PlayerEntity> fleePlayerGoal =
            new FleeEntityGoal<>(this, PlayerEntity.class, 6.0F, 1.0D, 1.2D);

    protected MixinHostileEntity(EntityType<? extends PathAwareEntity> entityType, World world) {
        super(entityType, world);
    }

    @Inject(method = "tickMovement", at = @At("HEAD"))
    public void run(CallbackInfo ci) {
        for (PlayerEntity player : this.world.getPlayers()) {
            // using for loop because closest player might select player without effects when in
            // proximity

            /* Probably laggy as hell */
            if (player.distanceTo(this) <= 6 && player.hasStatusEffect(Dungerite.STINKY_EFFECT))
                this.goalSelector.add(1, fleePlayerGoal);
            else this.goalSelector.remove(fleePlayerGoal);
        }
    }
}
