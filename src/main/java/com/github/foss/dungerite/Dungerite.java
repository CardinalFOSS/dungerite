package com.github.foss.dungerite;

import com.github.foss.dungerite.block.DungBlock;
import com.github.foss.dungerite.entity.entities.DungEntity;
import com.github.foss.dungerite.entity.entities.SeaweedBombEntity;
import com.github.foss.dungerite.item.items.Dung;
import com.github.foss.dungerite.item.items.SeaweedBomb;
import com.github.foss.dungerite.statuseffect.StinkyEffect;
import io.netty.buffer.Unpooled;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.client.itemgroup.FabricItemGroupBuilder;
import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.fabricmc.fabric.api.networking.v1.PlayerLookup;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.fabricmc.fabric.api.object.builder.v1.entity.FabricEntityTypeBuilder;
import net.fabricmc.fabric.api.particle.v1.FabricParticleTypes;
import net.minecraft.block.DispenserBlock;
import net.minecraft.block.Material;
import net.minecraft.block.dispenser.ProjectileDispenserBehavior;
import net.minecraft.entity.EntityDimensions;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.SpawnGroup;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.projectile.ProjectileEntity;
import net.minecraft.item.BlockItem;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.particle.DefaultParticleType;
import net.minecraft.sound.SoundEvent;
import net.minecraft.util.Identifier;
import net.minecraft.util.Util;
import net.minecraft.util.math.Position;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.World;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class Dungerite implements ModInitializer {
    public static final String MOD_ID = "dungerite";
    public static final Identifier PACKET_ID = new Identifier(Dungerite.MOD_ID, "spawn_packet"); // used for creating entity server-side
    public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

    // Double jump
    public static final Identifier C2S_DO_DOUBLE_JUMP = new Identifier(MOD_ID, "request_effects");
    public static final Identifier S2C_PLAY_EFFECTS_PACKET_ID = new Identifier(MOD_ID, "play_effects");

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

    // Particle
    public static final DefaultParticleType FART_PARTICLE = FabricParticleTypes.simple();

    // Effects
    public static final StatusEffect STINKY_EFFECT = new StinkyEffect();

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

        // Particle
        Registry.register(Registry.PARTICLE_TYPE, new Identifier(MOD_ID, "fart_particle"), FART_PARTICLE);

        // Effects
        Registry.register(Registry.STATUS_EFFECT, new Identifier(MOD_ID, "stinky_effect"), STINKY_EFFECT);

        // Registering in dispenser --> shootable
        DispenserBlock.registerBehavior(DUNG, new ProjectileDispenserBehavior() {
            @Override
            protected ProjectileEntity createProjectile(World world, Position position, ItemStack stack) {
                return Util.make(new DungEntity(world, position.getX(), position.getY(), position.getZ()), (entity) -> entity.setItem(stack));
            }
        });

        DispenserBlock.registerBehavior(SEAWEED_BOMB, new ProjectileDispenserBehavior() {
            @Override
            protected ProjectileEntity createProjectile(World world, Position position, ItemStack stack) {
                return Util.make(new SeaweedBombEntity(world, position.getX(), position.getY(), position.getZ()), (entity) -> entity.setItem(stack));
            }
        });

        // Double jump
        ServerPlayNetworking.registerGlobalReceiver(C2S_DO_DOUBLE_JUMP,
            (server, player, handler, buf, responseSender) -> {
                PacketByteBuf passedData = new PacketByteBuf(Unpooled.buffer());
                passedData.writeUuid(buf.readUuid());

                server.execute(() -> PlayerLookup.tracking(player).forEach(p -> {
                    if (p != player) {
                        ServerPlayNetworking.send(p, S2C_PLAY_EFFECTS_PACKET_ID, passedData);
                    }
                }));
            });

        LOGGER.info("Dungerite loaded!");
    }
}
