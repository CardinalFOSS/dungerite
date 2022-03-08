package com.github.foss.dungerite;

import com.github.foss.dungerite.block.DungBlock;
import com.github.foss.dungerite.entity.entities.DungEntity;
import com.github.foss.dungerite.entity.entities.SeaweedBombEntity;
import com.github.foss.dungerite.item.items.Dung;
import com.github.foss.dungerite.item.items.SeaweedBomb;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.client.itemgroup.FabricItemGroupBuilder;
import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.fabricmc.fabric.api.object.builder.v1.entity.FabricEntityTypeBuilder;
import net.minecraft.block.Material;
import net.minecraft.entity.EntityDimensions;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.SpawnGroup;
import net.minecraft.item.BlockItem;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.sound.SoundEvent;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class Dungerite implements ModInitializer {
    public static final Identifier PACKET_ID = new Identifier(Dungerite.MOD_ID, "spawn_packet"); // used for creating entity server-side

    public static final String MOD_ID = "dungerite";
    public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

    // Blocks
    public static final DungBlock DUNG_BLOCK = new DungBlock(FabricBlockSettings.of(Material.SOLID_ORGANIC).hardness(2.0F).requiresTool());

    // Items
    public static final Dung DUNG = new Dung(new FabricItemSettings());
    public static final SeaweedBomb SEAWEED_BOMB = new SeaweedBomb(new FabricItemSettings());

    // Sounds
    public static final Identifier FART_ID = new Identifier(MOD_ID + ":fart"); // used for DUNG_BLOCK
    public static final SoundEvent FART = new SoundEvent(FART_ID);
    public static final Identifier SPLAT_ID = new Identifier(MOD_ID + ":splat"); // used for DUNG on contact
    public static final SoundEvent SPLAT = new SoundEvent(SPLAT_ID);

    // ItemGroup
    public static final ItemGroup DUNG_GROUP = FabricItemGroupBuilder.create(
                    new Identifier(MOD_ID, "dung"))
            .icon(() -> new ItemStack(DUNG))
            .appendItems(stacks -> {
                stacks.add(new ItemStack(DUNG));
                stacks.add(new ItemStack(DUNG_BLOCK));
                stacks.add(new ItemStack(SEAWEED_BOMB));
            })
            .build();

    // Entities
    public static final EntityType<DungEntity> DungEntityType = Registry.register( // registering throwable DUNG
            Registry.ENTITY_TYPE,
            new Identifier(MOD_ID, "dung"),
            FabricEntityTypeBuilder.<DungEntity>create(SpawnGroup.MISC, DungEntity::new)
                    .dimensions(EntityDimensions.fixed(0.25f, 0.25f)) // dimensions in Minecraft units of the projectile
                    .trackRangeBlocks(4).trackedUpdateRate(10) // necessary for all thrown projectiles (as it prevents it from breaking, lol)
                    .build()
    );

    public static final EntityType<SeaweedBombEntity> SeaweedBombEntityType = Registry.register(
            Registry.ENTITY_TYPE,
            new Identifier(MOD_ID, "seaweed_bomb"),
            FabricEntityTypeBuilder.<SeaweedBombEntity>create(SpawnGroup.MISC, SeaweedBombEntity::new)
                    .dimensions(EntityDimensions.fixed(0.25f, 0.25f))
                    .trackRangeBlocks(4).trackedUpdateRate(10)
                    .build()
    );

    @Override
    public void onInitialize() {
        LOGGER.info("Loading Dungerite...");

        // Blocks --> items
        Registry.register(Registry.BLOCK, new Identifier(MOD_ID, "dung_block"), DUNG_BLOCK);
        Registry.register(Registry.ITEM, new Identifier(MOD_ID, "dung_block"), new BlockItem(DUNG_BLOCK, new FabricItemSettings().group(ItemGroup.MISC)));

        // Items
        Registry.register(Registry.ITEM, new Identifier(MOD_ID, "dung"), DUNG);
        Registry.register(Registry.ITEM, new Identifier(MOD_ID, "seaweed_bomb"), SEAWEED_BOMB);

        // Sounds
        Registry.register(Registry.SOUND_EVENT, FART_ID, FART);
        Registry.register(Registry.SOUND_EVENT, SPLAT_ID, SPLAT);

        LOGGER.info("Dungerite loaded!");
    }
}
