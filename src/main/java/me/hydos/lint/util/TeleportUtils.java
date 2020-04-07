package me.hydos.lint.util;

import net.fabricmc.fabric.api.dimension.v1.FabricDimensions;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.server.world.ChunkTicketType;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.ChunkPos;
import net.minecraft.world.chunk.ChunkStatus;
import net.minecraft.world.dimension.DimensionType;

public class TeleportUtils {
	public static void teleport(LivingEntity entity, DimensionType dim, BlockPos pos) {
		// chunkpos
		ChunkPos cPos = new ChunkPos(pos);
		// get world for dimension
		ServerWorld world = entity.getServer().getWorld(dim);
		// teleport ticket
		world.getChunkManager().addTicket(ChunkTicketType.POST_TELEPORT, cPos, 8, entity.getEntityId());
		// get chunk heightmaps
		world.getChunk(cPos.x, cPos.z, ChunkStatus.HEIGHTMAPS);
		// change dimension
		FabricDimensions.teleport(entity, dim);
		// close containers
		if (entity instanceof PlayerEntity) {
			((PlayerEntity) entity).openContainer(null);
		}
	}
}
