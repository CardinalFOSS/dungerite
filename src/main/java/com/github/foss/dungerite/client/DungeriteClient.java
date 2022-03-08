package com.github.foss.dungerite.client;

import com.github.foss.dungerite.Dungerite;
import com.github.foss.dungerite.entity.EntitySpawnPacket;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.fabricmc.fabric.impl.client.rendering.EntityRendererRegistryImpl;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.render.entity.FlyingItemEntityRenderer;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.registry.Registry;

import java.util.UUID;

import static com.github.foss.dungerite.Dungerite.PACKET_ID;

@Environment(EnvType.CLIENT)
public class DungeriteClient implements ClientModInitializer {


    @Override
    public void onInitializeClient() {
        // EntityRendererRegistry.INSTANCE.register(Dungerite.DungEntityType, FlyingItemEntityRenderer::new);
        // net.fabricmc.fabric.api.client.rendering.v1.EntityRendererRegistry.register(Dungerite.DungEntityType, FlyingItemEntityRenderer::new);
        // EntityRendererRegistry.INSTANCE.register(Dungerite.DungEntityType, (context) ->
        //        new FlyingItemEntityRenderer(context));

        EntityRendererRegistryImpl.register(Dungerite.DungEntityType, FlyingItemEntityRenderer::new); // registering entity in database for client
        EntityRendererRegistryImpl.register(Dungerite.SeaweedBombEntityType, FlyingItemEntityRenderer::new); // registering entity in database for client
        receiveEntityPacket();
    }

    public void receiveEntityPacket() {
        //ClientSidePacketRegistry.INSTANCE.register(PacketID, (ctx, byteBuf) -> {
        ClientPlayNetworking.registerGlobalReceiver(PACKET_ID, (client, handler, byteBuf, responseSender) -> {
            EntityType<?> et = Registry.ENTITY_TYPE.get(byteBuf.readVarInt());
            UUID uuid = byteBuf.readUuid();
            int entityId = byteBuf.readVarInt();
            Vec3d pos = EntitySpawnPacket.PacketBufUtil.readVec3d(byteBuf);
            float pitch = EntitySpawnPacket.PacketBufUtil.readAngle(byteBuf);
            float yaw = EntitySpawnPacket.PacketBufUtil.readAngle(byteBuf);
            client.execute(() -> {
                if (MinecraftClient.getInstance().world == null)
                    throw new IllegalStateException("Tried to spawn entity in a null world!");
                Entity e = et.create(MinecraftClient.getInstance().world);
                if (e == null)
                    throw new IllegalStateException("Failed to create instance of entity \"" + Registry.ENTITY_TYPE.getId(et) + "\"!");
                e.updateTrackedPosition(pos);
                e.setPos(pos.x, pos.y, pos.z);
                e.setPitch(pitch);
                e.setYaw(yaw);
                e.setId(entityId);
                e.setUuid(uuid);
                MinecraftClient.getInstance().world.addEntity(entityId, e);
            });
        });
    }

}
