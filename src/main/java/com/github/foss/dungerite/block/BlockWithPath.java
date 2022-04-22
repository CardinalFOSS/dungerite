package com.github.foss.dungerite.block;

import net.minecraft.block.Block;

public abstract class BlockWithPath extends Block {
    public BlockWithPath(Settings settings) {
        super(settings);
    }

    public abstract String getPath();
}
