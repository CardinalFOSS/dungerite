package com.github.foss.dungerite.item.weapons;

import com.github.foss.dungerite.Dungerite;
import com.github.foss.dungerite.entity.entities.DungCannonballEntity;
import com.github.foss.dungerite.item.InitItems;
import com.github.foss.dungerite.item.ItemWithPath;
import com.github.foss.dungerite.item.RangedWeapon;
import net.minecraft.client.item.TooltipContext;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.BowItem;
import net.minecraft.item.ItemStack;
import net.minecraft.sound.SoundCategory;
import net.minecraft.stat.Stats;
import net.minecraft.text.Text;
import net.minecraft.text.TranslatableText;
import net.minecraft.util.Formatting;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.util.UseAction;
import net.minecraft.world.World;

import java.util.List;
import java.util.function.Predicate;

public class DungCannon extends BowItem implements RangedWeapon {
  private static final ItemWithPath DUNG_CANNONBALL = InitItems.items[2];
  public static final Predicate<ItemStack> DUNG_CANNONBALL_PROJECTILE = (stack) -> stack.isOf(DUNG_CANNONBALL);
  public DungCannon(Settings settings) {
    super(settings);
  }

  public void onStoppedUsing(ItemStack stack, World world, LivingEntity user, int remainingUseTicks) {
    if (user instanceof PlayerEntity player) {
      int ammoIndex = getAmmoIndex(player, DUNG_CANNONBALL); // DungBullet
      if (ammoIndex != -1 || player.getAbilities().creativeMode) {
        ItemStack itemStack = ammoIndex != -1 ? player.getInventory().getStack(ammoIndex) : new ItemStack(DUNG_CANNONBALL);

        int i = this.getMaxUseTime(stack) - remainingUseTicks;
        float f = getPullProgress(i);
        if (!((double)f < 0.1)) {
          if (!world.isClient) {
            DungCannonballEntity dungCannonballEntity = new DungCannonballEntity(world, player);
            dungCannonballEntity.setVelocity(player, player.getPitch(), player.getYaw(), 0.0F, f * 5.0F, 0.5F);
            dungCannonballEntity.setNoGravity(true);
            dungCannonballEntity.setDamage(f * 15.0F + 0.5F);
            world.spawnEntity(dungCannonballEntity);
          }

          world.playSound(null, player.getX(), player.getY(), player.getZ(), Dungerite.SPLAT, SoundCategory.PLAYERS, 1.0F, 1.0F / (world.getRandom().nextFloat() * 0.4F + 1.2F) + f * 0.5F);
          itemStack.decrement(1);
          if (itemStack.isEmpty() && !player.isCreative()) {
            player.getInventory().removeOne(itemStack);
          }
        }

          player.incrementStat(Stats.USED.getOrCreateStat(this));
        }
      }
    }

  @Override
  public Predicate<ItemStack> getProjectiles() {
    return DUNG_CANNONBALL_PROJECTILE;
  }

  @Override
  public int getRange() {
    return 15;
  }

  @Override
  public UseAction getUseAction(ItemStack stack) {
    return UseAction.BOW;
  }

  public TypedActionResult<ItemStack> use(World world, PlayerEntity player, Hand hand) {
    ItemStack itemStack = player.getStackInHand(hand);
    int ammoIndex = getAmmoIndex(player, DUNG_CANNONBALL);

    if (!player.getAbilities().creativeMode && ammoIndex == -1) {
      return TypedActionResult.fail(itemStack);
    }
    else {
      player.setCurrentHand(hand);
      return TypedActionResult.success(itemStack);
    }
  }

  @Override
  public void appendTooltip(
          ItemStack itemStack, World world, List<Text> tooltip, TooltipContext tooltipContext) {
    tooltip.add(new TranslatableText("item.dungerite.dung_cannon.tooltip").formatted(Formatting.RED));
  }
}
