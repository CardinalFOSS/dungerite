package com.github.foss.dungerite.item.items;

import com.github.foss.dungerite.item.ItemWithPath;
import com.github.foss.dungerite.item.ThrownItem;
import net.minecraft.client.item.TooltipContext;
import net.minecraft.item.ItemStack;
import net.minecraft.text.Text;
import net.minecraft.text.TranslatableText;
import net.minecraft.util.Formatting;
import net.minecraft.world.World;

import java.util.List;

public class DungCannonball extends ItemWithPath implements ThrownItem {
    public DungCannonball(Settings settings) {
        super(settings);
    }

    @Override
    public String getPath() {
        return "dung_cannonball";
    }

    @Override
    public void appendTooltip(
            ItemStack itemStack, World world, List<Text> tooltip, TooltipContext tooltipContext) {
        tooltip.add(
                new TranslatableText("item.dungerite.dung_cannonball.tooltip")
                        .formatted(Formatting.RED));
    }
}
