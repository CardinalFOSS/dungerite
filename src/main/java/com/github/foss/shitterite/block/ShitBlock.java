package com.github.foss.shitterite.block;

import com.github.foss.shitterite.Shitterite;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.client.item.TooltipContext;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.sound.SoundCategory;
import net.minecraft.text.Text;
import net.minecraft.text.TranslatableText;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Formatting;
import net.minecraft.util.Hand;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;

import java.util.List;

public class ShitBlock extends Block {
    public ShitBlock(Settings settings) {
        super(settings);
    }

    @Override
    public ActionResult onUse(BlockState state, World world, BlockPos pos, PlayerEntity player, Hand hand, BlockHitResult hit) {
        if (!world.isClient) {
            world.playSound(
                    null,
                    pos,
                    Shitterite.FART,
                    SoundCategory.BLOCKS,
                    1f,
                    1f
            );

            player.addStatusEffect(new StatusEffectInstance(StatusEffects.NAUSEA, 10 * 20, 0));
            player.addStatusEffect(new StatusEffectInstance(StatusEffects.POISON, 10 * 20, 0));
            player.damage(DamageSource.MAGIC, 2);

            player.sendMessage( new TranslatableText("block.shitterite.shit_block.onUse"), false );
        }
        return ActionResult.SUCCESS;
    }
    @Override
    public void appendTooltip(ItemStack itemStack, BlockView world, List<Text> tooltip, TooltipContext tooltipContext) {
        tooltip.add( new TranslatableText("block.shitterite.shit_block.tooltip").formatted(Formatting.RED) );
    }
}
