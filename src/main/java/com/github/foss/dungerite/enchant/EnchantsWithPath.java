package com.github.foss.dungerite.enchant;

import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentTarget;
import net.minecraft.entity.EquipmentSlot;

public abstract class EnchantsWithPath extends Enchantment {
    protected EnchantsWithPath(Rarity weight, EnchantmentTarget type, EquipmentSlot[] slotTypes) {
        super(weight, type, slotTypes);
    }

    public abstract String getPath();
}
