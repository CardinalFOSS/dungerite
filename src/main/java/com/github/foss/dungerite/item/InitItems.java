package com.github.foss.dungerite.item;

import com.github.foss.dungerite.entity.entities.DungCannonballEntity;
import com.github.foss.dungerite.entity.entities.DungEntity;
import com.github.foss.dungerite.entity.entities.SeaweedBombEntity;
import com.github.foss.dungerite.item.armor.DungeriteArmorMaterial;
import com.github.foss.dungerite.item.items.Dung;
import com.github.foss.dungerite.item.items.DungCannonball;
import com.github.foss.dungerite.item.items.SeaweedBomb;
import com.github.foss.dungerite.item.tools.DungeriteAxe;
import com.github.foss.dungerite.item.tools.DungeritePickaxe;
import com.github.foss.dungerite.item.tools.DungeriteShovel;
import com.github.foss.dungerite.item.tools.DungeriteSword;
import com.github.foss.dungerite.item.weapons.DungCannon;
import com.github.foss.dungerite.item.weapons.DungeriteHoe;
import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.minecraft.block.DispenserBlock;
import net.minecraft.block.dispenser.ProjectileDispenserBehavior;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.projectile.ProjectileEntity;
import net.minecraft.item.*;
import net.minecraft.util.Identifier;
import net.minecraft.util.Util;
import net.minecraft.util.math.Position;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.World;

import static com.github.foss.dungerite.Dungerite.MOD_ID;

public interface InitItems {
    /* Variables */
    ItemWithPath[] items = {
        new Dung(new FabricItemSettings().maxCount(16)),
        new SeaweedBomb(new FabricItemSettings()),
        new DungCannonball(new FabricItemSettings()),
    };

    DungCannon DUNG_CANNON = new DungCannon(new FabricItemSettings().maxCount(1));
    ArmorMaterial dungeriteArmorMaterial = new DungeriteArmorMaterial();

    ArmorItem[] armorItems = {
            new ArmorItem(dungeriteArmorMaterial, EquipmentSlot.HEAD, new Item.Settings().group(ItemGroup.COMBAT)),
            new ArmorItem(dungeriteArmorMaterial, EquipmentSlot.CHEST, new Item.Settings().group(ItemGroup.COMBAT)),
            new ArmorItem(dungeriteArmorMaterial, EquipmentSlot.LEGS, new Item.Settings().group(ItemGroup.COMBAT)),
            new ArmorItem(dungeriteArmorMaterial, EquipmentSlot.FEET, new Item.Settings().group(ItemGroup.COMBAT)),
    };

    String[] armorItemPaths = {
            "dungerite_helmet",
            "dungerite_chestplate",
            "dungerite_leggings",
            "dungerite_boots"
    };
    ToolItem[] toolItems = {
            new DungeriteSword(
                    DungeriteToolMaterial.INSTANCE, -5, -2.8F, new Item.Settings().group(ItemGroup.COMBAT)),
            new DungeritePickaxe(
                    DungeriteToolMaterial.INSTANCE, 1, -1.8F, new Item.Settings().group(ItemGroup.TOOLS)),
            new DungeriteAxe(
                    DungeriteToolMaterial.INSTANCE, 1, -3.5F, new Item.Settings().group(ItemGroup.TOOLS)),
            new DungeriteShovel(
                    DungeriteToolMaterial.INSTANCE, 0, -1.8F, new Item.Settings().group(ItemGroup.TOOLS)),
            new DungeriteHoe(
                    DungeriteToolMaterial.INSTANCE, 5, -1.8F, new Item.Settings().group(ItemGroup.TOOLS)),
    };
    String[] toolItemPaths = {
            "dungerite_sword",
            "dungerite_pickaxe",
            "dungerite_axe",
            "dungerite_shovel",
            "dungerite_hoe"
    };


    default void registerItems() {
        int toolIndex = 0;
        int armorIndex = 0;

        for (ItemWithPath item : items)
            Registry.register(Registry.ITEM, new Identifier(MOD_ID, item.getPath()), item);
        for (ToolItem toolItem : toolItems) {
            Registry.register(Registry.ITEM, new Identifier(MOD_ID, toolItemPaths[toolIndex]), toolItem);
            toolIndex++;
        }
        for (ArmorItem armorItem : armorItems) {
            Registry.register(Registry.ITEM, new Identifier(MOD_ID, armorItemPaths[armorIndex]), armorItem);
            armorIndex++;
        }
        /* Special cases */
        Registry.register(Registry.ITEM, new Identifier(MOD_ID, "dung_cannon"), DUNG_CANNON);
    }

    default void registerDispenserBehavior() {
        DispenserBlock.registerBehavior(
                items[0],
                new ProjectileDispenserBehavior() {
                    @Override
                    protected ProjectileEntity createProjectile(
                            World world, Position position, ItemStack stack) {
                        return Util.make(
                                new DungEntity(
                                        world, position.getX(), position.getY(), position.getZ()),
                                (entity) -> entity.setItem(stack));
                    }
                });

        DispenserBlock.registerBehavior(
                items[1],
                new ProjectileDispenserBehavior() {
                    @Override
                    protected ProjectileEntity createProjectile(
                            World world, Position position, ItemStack stack) {
                        return Util.make(
                                new SeaweedBombEntity(
                                        world, position.getX(), position.getY(), position.getZ()),
                                (entity) -> entity.setItem(stack));
                    }
                });

        DispenserBlock.registerBehavior(
                items[2],
                new ProjectileDispenserBehavior() {
                    @Override
                    protected ProjectileEntity createProjectile(
                            World world, Position position, ItemStack stack) {
                        return Util.make(
                                new DungCannonballEntity(
                                        world, position.getX(), position.getY(), position.getZ()),
                                (entity) -> entity.setItem(stack));
                    }
                });
    }
}
