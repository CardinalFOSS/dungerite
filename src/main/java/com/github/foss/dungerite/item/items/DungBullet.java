package com.github.foss.dungerite.item.items;

import com.github.foss.dungerite.item.ThrownItem;
import net.minecraft.client.item.TooltipContext;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.text.Text;
import net.minecraft.text.TranslatableText;
import net.minecraft.util.Formatting;
import net.minecraft.world.World;

import java.util.List;

public class DungBullet extends Item implements ThrownItem {
  public DungBullet(Settings settings) {
    super(settings);
  }

  @Override
  public void appendTooltip(
          ItemStack itemStack, World world, List<Text> tooltip, TooltipContext tooltipContext) {
    tooltip.add(new TranslatableText("item.dungerite.dung.tooltip").formatted(Formatting.RED));
  }
}
