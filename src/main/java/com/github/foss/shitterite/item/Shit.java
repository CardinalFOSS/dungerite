package com.github.foss.shitterite.item;

import com.github.foss.shitterite.entity.ShitEntity;
import net.minecraft.client.item.TooltipContext;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.stat.Stats;
import net.minecraft.text.Text;
import net.minecraft.text.TranslatableText;
import net.minecraft.util.Formatting;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.world.World;

import java.util.List;

public class Shit extends Item {
    public Shit(Settings settings) {
        super(settings);
    }

    public TypedActionResult<ItemStack> use(World world, PlayerEntity player, Hand hand) {
        ItemStack itemStack = player.getStackInHand(hand);

        world.playSound(
                null,
                player.getBlockPos(),
                SoundEvents.ENTITY_SNOWBALL_THROW,
                SoundCategory.NEUTRAL,
                0.5F,
                1F
        );


        /*
		player.getItemCooldownManager().set(this, 5);
		Optionally, you can add a cooldown to your item's right-click use, similar to Ender Pearls.
		*/

        if (!world.isClient) {
            ShitEntity shitEntity = new ShitEntity(world, player);
            shitEntity.setItem(itemStack);
            shitEntity.setVelocity(player, player.getPitch(), player.getYaw(), 0.0F, 1.5F, 0F);
            /*
            shitEntity.setProperties(user, user.getPitch(), user.getYaw(), 0.0F, 1.5F, 1.0F);
            In 1.17,we will use setProperties instead of setVelocity.

            setProperties isn't even a method???
            */
            world.spawnEntity(shitEntity);
        }
        player.incrementStat(Stats.USED.getOrCreateStat(this)); // adds amount of shit thrown as a stat

        player.getStackInHand(hand).decrement(1);
        return TypedActionResult.success(player.getStackInHand(hand));
    }

    @Override
    public void appendTooltip(ItemStack itemStack, World world, List<Text> tooltip, TooltipContext tooltipContext) {
        tooltip.add( new TranslatableText("item.shitterite.shit.tooltip").formatted(Formatting.RED) );
    }
}
