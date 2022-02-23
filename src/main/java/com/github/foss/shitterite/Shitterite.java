package com.github.foss.shitterite;

import com.github.foss.shitterite.block.ShitBlock;
import com.github.foss.shitterite.entity.ShitEntity;
import com.github.foss.shitterite.item.Shit;
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


public class Shitterite implements ModInitializer {
    public static final Identifier PACKET_ID = new Identifier(Shitterite.MOD_ID, "spawn_packet"); // used for creating entity server-side

    public static final String MOD_ID = "shitterite";
    public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

    public static final ShitBlock SHIT_BLOCK = new ShitBlock(FabricBlockSettings.of(Material.SOLID_ORGANIC).hardness(2.0F).requiresTool());
    public static final Shit SHIT = new Shit(new FabricItemSettings().group(ItemGroup.MISC));

    public static final Identifier FART_ID = new Identifier(MOD_ID + ":fart"); // used for SHIT_BLOCK
    public static final SoundEvent FART = new SoundEvent(FART_ID);
    public static final Identifier SPLAT_ID = new Identifier(MOD_ID + ":splat"); // used for SHIT on contact
    public static final SoundEvent SPLAT = new SoundEvent(SPLAT_ID);

    public static final ItemGroup SHIT_GROUP = FabricItemGroupBuilder.create(
                    new Identifier(MOD_ID, "shit"))
            .icon(() -> new ItemStack(SHIT))
            .appendItems(stacks -> {
                stacks.add(new ItemStack(SHIT));
                stacks.add(new ItemStack(SHIT_BLOCK));
            })
            .build();

    public static final EntityType<ShitEntity> ShitEntityType = Registry.register( // registering throwable shit
            Registry.ENTITY_TYPE,
            new Identifier(MOD_ID, "shit"),
            FabricEntityTypeBuilder.<ShitEntity>create(SpawnGroup.MISC, ShitEntity::new)
                    .dimensions(EntityDimensions.fixed(0.25F, 0.25F)) // dimensions in Minecraft units of the projectile
                    .trackRangeBlocks(4).trackedUpdateRate(10) // necessary for all thrown projectiles (as it prevents it from breaking, lol)
                    .build()
    );

    @Override
    public void onInitialize() {
        LOGGER.info("Loading Shitterite...");

        Registry.register(Registry.BLOCK, new Identifier(MOD_ID, "shit_block"), SHIT_BLOCK);
        Registry.register(Registry.ITEM, new Identifier(MOD_ID, "shit_block"), new BlockItem(SHIT_BLOCK, new FabricItemSettings().group(ItemGroup.MISC)));

        Registry.register(Registry.ITEM, new Identifier(MOD_ID, "shit"), SHIT);

        Registry.register(Registry.SOUND_EVENT, FART_ID, FART);
        Registry.register(Registry.SOUND_EVENT, SPLAT_ID, SPLAT);

        LOGGER.info("Shitterite loaded!");
    }
}
