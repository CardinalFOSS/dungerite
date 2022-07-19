package com.github.foss.dungerite.item;

import net.minecraft.item.ToolMaterial;
import net.minecraft.recipe.Ingredient;

public class DungeriteToolMaterial implements ToolMaterial {

    public static final DungeriteToolMaterial INSTANCE = new DungeriteToolMaterial();

    @Override
    public int getDurability() {
        return 2501;
    }

    @Override
    public float getMiningSpeedMultiplier() {
        return 12.0F;
    }

    @Override
    public float getAttackDamage() {
        return 5.0F;
    }

    @Override
    public int getMiningLevel() {
        return 5;
    }

    @Override
    public int getEnchantability() {
        return 25;
    }

    @Override
    public Ingredient getRepairIngredient() {
        return Ingredient.ofItems(InitItems.items[0]); // Dung
    }
}
