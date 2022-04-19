package com.github.foss.dungerite.item;

import com.github.foss.dungerite.entity.entities.DungCannonballEntity;
import com.github.foss.dungerite.entity.entities.DungEntity;
import com.github.foss.dungerite.entity.entities.SeaweedBombEntity;
import com.github.foss.dungerite.item.items.Dung;
import com.github.foss.dungerite.item.items.DungCannonball;
import com.github.foss.dungerite.item.items.SeaweedBomb;
import com.github.foss.dungerite.item.weapons.DungCannon;
import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.minecraft.block.DispenserBlock;
import net.minecraft.block.dispenser.ProjectileDispenserBehavior;
import net.minecraft.entity.projectile.ProjectileEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Identifier;
import net.minecraft.util.Util;
import net.minecraft.util.math.Position;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.World;

import static com.github.foss.dungerite.Dungerite.MOD_ID;

public interface InitItems {
    ItemWithPath[] items = {
        new Dung(new FabricItemSettings()),
        new SeaweedBomb(new FabricItemSettings()),
        new DungCannonball(new FabricItemSettings())
    };

    /* Special cases */
    DungCannon DUNG_CANNON = new DungCannon(new FabricItemSettings());

    default void registerItems() {
        for (ItemWithPath item : items) {
            Registry.register(Registry.ITEM, new Identifier(MOD_ID, item.getPath()), item);
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
