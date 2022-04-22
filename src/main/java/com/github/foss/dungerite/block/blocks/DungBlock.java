package com.github.foss.dungerite.block.blocks;

import com.github.foss.dungerite.Dungerite;
import com.github.foss.dungerite.block.BlockWithPath;
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

import static com.github.foss.dungerite.Dungerite.secsToTicks;

public class DungBlock extends BlockWithPath {
    public DungBlock(Settings settings) {
        super(settings);
    }

    @Override
    public ActionResult onUse(
            BlockState state,
            World world,
            BlockPos pos,
            PlayerEntity player,
            Hand hand,
            BlockHitResult hit) {
        if (!world.isClient) {
            world.playSound(null, pos, Dungerite.FART, SoundCategory.BLOCKS, 1f, 1f);

            player.addStatusEffect(
                    new StatusEffectInstance(StatusEffects.NAUSEA, 10 * secsToTicks, 0));
            player.addStatusEffect(
                    new StatusEffectInstance(StatusEffects.POISON, 10 * secsToTicks, 0));
            player.damage(DamageSource.MAGIC, 2);

            // sends a message telling the player they have become stinky
            player.sendMessage(new TranslatableText("block.dungerite.dung_block.onUse"), false);
        }
        return ActionResult.SUCCESS;
    }

    @Override
    public void appendTooltip(
            ItemStack itemStack,
            BlockView world,
            List<Text> tooltip,
            TooltipContext tooltipContext) {
        tooltip.add(
                new TranslatableText("block.dungerite.dung_block.tooltip")
                        .formatted(Formatting.RED));
    }

    @Override
    public String getPath() {
        return "dung_block";
    }
}
