package com.github.foss.dungerite.item.tools;

import com.github.foss.dungerite.block.InitBlocks;
import com.github.foss.dungerite.item.InitItems;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.*;
import net.minecraft.util.ActionResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class DungeriteAxe extends AxeItem {
    public DungeriteAxe(ToolMaterial material, float attackDamage, float attackSpeed, Settings settings) {
        super(material, attackDamage, attackSpeed, settings);
    }

    // Turns obsidian to poop
    @Override
    public ActionResult useOnBlock(ItemUsageContext context) {
        World world = context.getWorld();
        BlockPos blockPos = context.getBlockPos();
        BlockState blockState = world.getBlockState(blockPos);

        if (blockState.getBlock().equals(Blocks.OBSIDIAN))
            world.setBlockState(blockPos, InitBlocks.blocks[0].getDefaultState()); // Dung Block
        return super.useOnBlock(context);
    }

    @Override
    public boolean postHit(ItemStack stack, LivingEntity target, LivingEntity attacker) {
        if (target instanceof PlayerEntity player && !target.getOffHandStack().isEmpty()){
            if (target.getOffHandStack().isOf(Items.SHIELD)) {
                ItemStack shield = target.getOffHandStack().copy();
                player.equipStack(EquipmentSlot.OFFHAND, InitItems.items[0].getDefaultStack()); // Dung
                player.giveItemStack(shield);
            }
        }
        return super.postHit(stack, target, attacker);
    }
}
