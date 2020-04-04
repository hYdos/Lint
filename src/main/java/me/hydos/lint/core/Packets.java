package me.hydos.lint.core;

import net.fabricmc.fabric.api.dimension.v1.FabricDimensions;
import net.fabricmc.fabric.api.network.ServerSidePacketRegistry;
import net.minecraft.server.world.ChunkTicketType;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.ChunkPos;
import net.minecraft.world.chunk.ChunkStatus;

import static me.hydos.lint.core.Dimensions.HAYKAM;

public interface Packets {

    Identifier PLAY_DIMENSION_CHANGE_PACKET_ID = new Identifier("lint", "change_dimension");

    static void onInitialize() {
        ServerSidePacketRegistry.INSTANCE.register(PLAY_DIMENSION_CHANGE_PACKET_ID, (packetContext, packetByteBuf) -> {
            String dimension = packetByteBuf.readString(65535);
            if (dimension.equalsIgnoreCase("HAYKAM")) {
                packetContext.getTaskQueue().execute(() -> {
                    ServerWorld world = packetContext.getPlayer().getServer().getWorld(HAYKAM);
                    world.getChunkManager().addTicket(ChunkTicketType.POST_TELEPORT, new ChunkPos(0, 0), 8, packetContext.getPlayer().getEntityId());
                    world.getChunk(0, 0, ChunkStatus.HEIGHTMAPS);
                    FabricDimensions.teleport(packetContext.getPlayer(), HAYKAM);
                    packetContext.getPlayer().openContainer(null);
                });
            }
        });
    }
}
