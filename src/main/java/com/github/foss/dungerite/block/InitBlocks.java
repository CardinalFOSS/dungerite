package com.github.foss.dungerite.block;

import com.github.foss.dungerite.Dungerite;
import com.github.foss.dungerite.block.blocks.DungBlock;
import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.minecraft.block.Block;
import net.minecraft.block.Material;
import net.minecraft.item.BlockItem;
import net.minecraft.item.ItemGroup;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

public interface InitBlocks {
    Block[] blocks = {
        new DungBlock(FabricBlockSettings.of(Material.SOLID_ORGANIC).hardness(2.0F).requiresTool())
    };
    String[] paths = {"dung_block"};

    default void registerBlocks() {
        int i = 0;
        for (Block blockWithPath : blocks) {
            Registry.register(
                    Registry.BLOCK, new Identifier(Dungerite.MOD_ID, paths[i]), blockWithPath);
            Registry.register(
                    Registry.ITEM,
                    new Identifier(Dungerite.MOD_ID, paths[i]),
                    new BlockItem(blockWithPath, new FabricItemSettings().group(ItemGroup.MISC)));
        }
    }
}
