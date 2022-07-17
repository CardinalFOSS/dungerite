package com.github.foss.dungerite.item;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;

public interface RangedWeapon {
    default int getAmmoIndex(PlayerEntity player, Item ammo) {
        for (int i = 0; i < player.getInventory().size(); i++) {
            Item item = player.getInventory().getStack(i).getItem();
            if (item == ammo) {
                return i;
            }
        }
        return -1;
    }
}
