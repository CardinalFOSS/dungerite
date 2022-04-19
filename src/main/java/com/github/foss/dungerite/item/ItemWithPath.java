package com.github.foss.dungerite.item;

import net.minecraft.item.Item;

public abstract class ItemWithPath extends Item {
  public ItemWithPath(Settings settings) {
    super(settings);
  }

  public abstract String getPath();
}
