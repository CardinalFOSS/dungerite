package com.github.foss.dungerite;

import com.github.foss.dungerite.block.BlockWithPath;
import com.github.foss.dungerite.block.InitBlocks;
import com.github.foss.dungerite.enchant.InitEnchants;
import com.github.foss.dungerite.entity.InitEntities;
import com.github.foss.dungerite.entity.entities.DungCannonballEntity;
import com.github.foss.dungerite.entity.entities.DungEntity;
import com.github.foss.dungerite.entity.entities.SeaweedBombEntity;
import com.github.foss.dungerite.item.InitItems;
import com.github.foss.dungerite.item.ItemWithPath;
import com.github.foss.dungerite.statuseffect.statuseffects.BingQiLinEffect;
import com.github.foss.dungerite.statuseffect.statuseffects.StinkyEffect;
import io.netty.buffer.Unpooled;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.client.itemgroup.FabricItemGroupBuilder;
import net.fabricmc.fabric.api.networking.v1.PlayerLookup;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.fabricmc.fabric.api.particle.v1.FabricParticleTypes;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.item.ArmorItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ToolItem;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.particle.DefaultParticleType;
import net.minecraft.sound.SoundEvent;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;

public class Dungerite implements ModInitializer, InitBlocks, InitItems, InitEnchants {
    public static final String MOD_ID = "dungerite";
    public static final int secsToTicks = 20;
    public static final Identifier PACKET_ID =
            new Identifier(MOD_ID, "spawn_packet"); // used for creating entity server-side
    public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

    // Double jump
    public static final Identifier C2S_DO_DOUBLE_JUMP = new Identifier(MOD_ID, "request_effects");
    public static final Identifier S2C_PLAY_EFFECTS_PACKET_ID =
            new Identifier(MOD_ID, "play_effects");

    // Sounds
    public static final Identifier FART_ID =
            new Identifier(MOD_ID + ":fart"); // used for DUNG_BLOCK
    public static final SoundEvent FART = new SoundEvent(FART_ID);
    public static final Identifier SPLAT_ID =
            new Identifier(MOD_ID + ":splat"); // used for DUNG on contact
    public static final SoundEvent SPLAT = new SoundEvent(SPLAT_ID);
    public static final Identifier BING_QI_LIN_ID = new Identifier(MOD_ID + ":bing_qi_lin");
    public static final SoundEvent BING_QI_LIN = new SoundEvent(BING_QI_LIN_ID);

    // Particle
    public static final DefaultParticleType FART_PARTICLE = FabricParticleTypes.simple();

    // Effects
    public static final StatusEffect STINKY_EFFECT = new StinkyEffect();
    public static final StatusEffect BING_QI_LIN_EFFECT = new BingQiLinEffect();

    // Entities
    public static final EntityType<DungEntity> DungEntityType = InitEntities.DungEntityType;
    public static final EntityType<SeaweedBombEntity> SeaweedBombEntityType =
            InitEntities.SeaweedBombEntityType;
    public static final EntityType<DungCannonballEntity> DungCannonballEntityType =
            InitEntities.DungCannonballEntityType;

    @Override
    public void onInitialize() {
        LOGGER.info("Loading Dungerite...");
        registerItems();
        registerBlocks();
        registerToGroup();
        registerEnchants();

        registerDispenserBehavior();

        // Sounds
        Registry.register(Registry.SOUND_EVENT, FART_ID, FART);
        Registry.register(Registry.SOUND_EVENT, SPLAT_ID, SPLAT);
        Registry.register(Registry.SOUND_EVENT, BING_QI_LIN_ID, BING_QI_LIN);

        // Particle
        Registry.register(
                Registry.PARTICLE_TYPE, new Identifier(MOD_ID, "fart_particle"), FART_PARTICLE);

        // Effects
        Registry.register(
                Registry.STATUS_EFFECT, new Identifier(MOD_ID, "stinky_effect"), STINKY_EFFECT);
        Registry.register(
                Registry.STATUS_EFFECT, new Identifier(MOD_ID, "bing_qi_lin_effect"), BING_QI_LIN_EFFECT
        );

        // Double jump
        ServerPlayNetworking.registerGlobalReceiver(
                C2S_DO_DOUBLE_JUMP,
                (server, player, handler, buf, responseSender) -> {
                    PacketByteBuf passedData = new PacketByteBuf(Unpooled.buffer());
                    passedData.writeUuid(buf.readUuid());

                    // not sure if this is correct
                    // looks up players on the server and determines if they're the player who
                    // executed double
                    // jump
                    // if not, send a packet requesting effects
                    server.execute(
                            () ->
                                    PlayerLookup.tracking(player)
                                            .forEach(
                                                    p -> {
                                                        if (p != player) {
                                                            ServerPlayNetworking.send(
                                                                    p,
                                                                    S2C_PLAY_EFFECTS_PACKET_ID,
                                                                    passedData);
                                                        }
                                                    }));
                });

        LOGGER.info("Dungerite loaded!");
    }

    private void registerToGroup() {
        List<ItemStack> stacks = new ArrayList<>();

        /* Special cases */
        stacks.add(new ItemStack(InitItems.DUNG_CANNON));

        for (BlockWithPath block : blocks) stacks.add(new ItemStack(block));
        for (ItemWithPath item : items) stacks.add(new ItemStack(item));
        for (ToolItem toolItem : toolItems) stacks.add(new ItemStack(toolItem));
        for (ArmorItem armorItem : armorItems) stacks.add(new ItemStack(armorItem));

        FabricItemGroupBuilder.create(new Identifier(MOD_ID, "dung"))
                .icon(() -> new ItemStack(items[0])) // Dung
                .appendItems(stack -> stack.addAll(stacks))
                .build();
    }
}
