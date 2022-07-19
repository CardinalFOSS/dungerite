package com.github.foss.dungerite.enchant;

import com.github.foss.dungerite.Dungerite;
import com.github.foss.dungerite.enchant.enchants.BingQiLinEnchantment;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

public interface InitEnchants {

    Enchantment[] enchants = {new BingQiLinEnchantment()};
    String[] paths = {"bing_qi_lin_enchant"};

    default void registerEnchants() {
        int i = 0;
        for (Enchantment enchantment : enchants) {
            Registry.register(
                    Registry.ENCHANTMENT, new Identifier(Dungerite.MOD_ID, paths[i]), enchantment);
        }
    }
}
