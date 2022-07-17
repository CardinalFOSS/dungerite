package com.github.foss.dungerite.block;

import com.github.foss.dungerite.Dungerite;
import com.github.foss.dungerite.block.blocks.DungBlock;
import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.minecraft.block.Material;
import net.minecraft.item.BlockItem;
import net.minecraft.item.ItemGroup;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

public interface InitBlocks {
    BlockWithPath[] blocks = {
        new DungBlock(FabricBlockSettings.of(Material.SOLID_ORGANIC).hardness(2.0F).requiresTool())
    };

    default void registerBlocks() {
        for (BlockWithPath blockWithPath : blocks) {
            Registry.register(
                    Registry.BLOCK,
                    new Identifier(Dungerite.MOD_ID, blockWithPath.getPath()),
                    blockWithPath);
            Registry.register(
                    Registry.ITEM,
                    new Identifier(Dungerite.MOD_ID, blockWithPath.getPath()),
                    new BlockItem(blockWithPath, new FabricItemSettings().group(ItemGroup.MISC)));
        }
    }
}
