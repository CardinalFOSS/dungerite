package com.github.foss.dungerite.entity;

import com.github.foss.dungerite.entity.entities.DungCannonballEntity;
import com.github.foss.dungerite.entity.entities.DungEntity;
import com.github.foss.dungerite.entity.entities.SeaweedBombEntity;
import net.fabricmc.fabric.api.object.builder.v1.entity.FabricEntityTypeBuilder;
import net.minecraft.entity.EntityDimensions;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.SpawnGroup;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

import static com.github.foss.dungerite.Dungerite.MOD_ID;

public interface InitEntities {
    EntityType<DungEntity> DungEntityType =
        Registry.register( // registering throwable DUNG
                Registry.ENTITY_TYPE,
                new Identifier(MOD_ID, "dung"),
                FabricEntityTypeBuilder.<DungEntity>create(SpawnGroup.MISC, DungEntity::new)
                        .dimensions(
                                EntityDimensions.fixed(
                                        0.25f, 0.25f)) // dimensions in Minecraft units of the
                        // projectile
                        .trackRangeBlocks(4)
                        .trackedUpdateRate(
                                10) // necessary for all thrown projectiles (as it prevents it
                        // from breaking, lol)
                        .build());

    EntityType<SeaweedBombEntity> SeaweedBombEntityType =
            Registry.register(
                    Registry.ENTITY_TYPE,
                    new Identifier(MOD_ID, "seaweed_bomb"),
                    FabricEntityTypeBuilder.<SeaweedBombEntity>create(
                                    SpawnGroup.MISC, SeaweedBombEntity::new)
                            .dimensions(EntityDimensions.fixed(0.25f, 0.25f))
                            .trackRangeBlocks(4)
                            .trackedUpdateRate(10)
                            .build());

    EntityType<DungCannonballEntity> DungCannonballEntityType =
            Registry.register(
                    Registry.ENTITY_TYPE,
                    new Identifier(MOD_ID, "dung_bullet"),
                    FabricEntityTypeBuilder.<DungCannonballEntity>create(
                                    SpawnGroup.MISC, DungCannonballEntity::new)
                            .dimensions(
                                    EntityDimensions.fixed(
                                            0.5f, 0.5f)) // dimensions in Minecraft units of the
                            // projectile
                            .trackRangeBlocks(4)
                            .trackedUpdateRate(
                                    10) // necessary for all thrown projectiles (as it prevents it
                            // from breaking, lol)
                            .build());
}
