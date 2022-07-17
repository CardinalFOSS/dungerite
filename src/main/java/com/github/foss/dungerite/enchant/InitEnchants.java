package com.github.foss.dungerite.enchant;

import com.github.foss.dungerite.Dungerite;
import com.github.foss.dungerite.enchant.enchants.BingQiLinEnchantment;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

public interface InitEnchants {

    EnchantsWithPath[] enchants = {
            new BingQiLinEnchantment()
    };

    default void registerEnchants() {
        for (EnchantsWithPath enchantment : enchants) {
            Registry.register(Registry.ENCHANTMENT, new Identifier(Dungerite.MOD_ID, enchantment.getPath()), enchantment);
        }
    }
}