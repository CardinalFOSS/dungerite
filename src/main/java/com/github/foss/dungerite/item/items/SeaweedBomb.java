package com.github.foss.dungerite.item.items;

import com.github.foss.dungerite.entity.entities.SeaweedBombEntity;
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

import static com.github.foss.dungerite.Dungerite.secsToTicks;

public class SeaweedBomb extends ItemWithPath implements ThrownItem {
    public SeaweedBomb(Settings settings) {
        super(settings);
    }

    @Override
    public String getPath() {
        return "seaweed_bomb";
    }

    public TypedActionResult<ItemStack> use(World world, PlayerEntity player, Hand hand) {
        // a cooldown to the item's right-click use, similar to Ender Pearls.
        player.getItemCooldownManager().set(this, 3 * secsToTicks);

        onThrow(
                world,
                player,
                hand,
                SoundEvents.ENTITY_ARROW_SHOOT,
                new SeaweedBombEntity(world, player));

        return TypedActionResult.success(player.getStackInHand(hand));
    }

    @Override
    public void appendTooltip(
            ItemStack itemStack, World world, List<Text> tooltip, TooltipContext tooltipContext) {
        tooltip.add(
                new TranslatableText("item.dungerite.seaweed_bomb.tooltip")
                        .formatted(Formatting.RED));
    }
}
