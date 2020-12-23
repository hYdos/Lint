package io.github.hydos.lint.util;

import net.fabricmc.fabric.api.dimension.v1.FabricDimensions;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.ChunkTicketType;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.ChunkPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.Heightmap;
import net.minecraft.world.TeleportTarget;

public class TeleportUtils {
	public static void teleport(LivingEntity entity, ServerWorld world, BlockPos pos) {
		if (entity instanceof ServerPlayerEntity) {
			world.getChunkManager().addTicket(ChunkTicketType.POST_TELEPORT, new ChunkPos(pos), 1, entity.getEntityId());
			entity.stopRiding();
			world.getChunk(pos);
			world.getChunk(pos.add(0, 0, 16));
			world.getChunk(pos.add(16, 0, 0));
			world.getChunk(pos.add(16, 0, 16));
			((PlayerEntity) entity).openHandledScreen(null);
		}
		FabricDimensions.teleport(entity, world, new TeleportTarget(new Vec3d(pos.getX() + 0.5, world.getTopY(Heightmap.Type.MOTION_BLOCKING, pos.getX(), pos.getZ()) + 1, pos.getZ() + 0.5), new Vec3d(0, 0, 0), entity.yaw, entity.pitch));
	}
}