package com.github.foss.dungerite.item.items;

import com.github.foss.dungerite.entity.entities.DungEntity;
import com.github.foss.dungerite.item.ItemWithPath;
import com.github.foss.dungerite.item.ThrownItem;
import net.minecraft.client.item.TooltipContext;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.sound.SoundEvents;
import net.minecraft.text.Text;
import net.minecraft.text.TranslatableText;
import net.minecraft.util.Formatting;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.world.World;

import java.util.List;

public class Dung extends ItemWithPath implements ThrownItem {
  public Dung(Settings settings) {
    super(settings);
  }

  @Override
  public String getPath() {
    return "dung";
  }

  public TypedActionResult<ItemStack> use(World world, PlayerEntity player, Hand hand) {
    onThrow(world, player, hand, SoundEvents.ENTITY_SNOWBALL_THROW, new DungEntity(world, player));
    return TypedActionResult.success(player.getStackInHand(hand));
  }

  @Override
  public void appendTooltip(
      ItemStack itemStack, World world, List<Text> tooltip, TooltipContext tooltipContext) {
    tooltip.add(new TranslatableText("item.dungerite.dung.tooltip").formatted(Formatting.RED));
  }
}
