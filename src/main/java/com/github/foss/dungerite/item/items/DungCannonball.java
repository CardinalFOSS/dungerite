package com.github.foss.dungerite.item.items;

import com.github.foss.dungerite.item.ItemWithPath;
import com.github.foss.dungerite.item.ThrownItem;

public class DungCannonball extends ItemWithPath implements ThrownItem {
    public DungCannonball(Settings settings) {
        super(settings);
    }

    @Override
    public String getPath() {
        return "dung_cannonball";
    }
}
