package me.hydos.lint.util;

import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.ChunkTicketType;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.ChunkPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.Heightmap;
import net.minecraft.world.dimension.DimensionType;

public class TeleportUtils {
	public static boolean teleport(LivingEntity entity, DimensionType dim, BlockPos pos) {
		// chunkpos
		ChunkPos cPos = new ChunkPos(pos);
		// get world for dimension
		ServerWorld world = entity.getServer().getWorld(dim);

		if (entity.world == world) {
			return false;
		}

		if (entity instanceof ServerPlayerEntity) {
			// teleport ticket
			world.getChunkManager().addTicket(ChunkTicketType.POST_TELEPORT, cPos, 1, entity.getEntityId());
			// stop riding
			entity.stopRiding();
			// haha load chunk
			world.getChunk(pos);
			world.getChunk(pos.add(0, 0, 16));
			world.getChunk(pos.add(16, 0, 0));
			world.getChunk(pos.add(16, 0, 16));
			// tp
			((ServerPlayerEntity) entity).teleport(world, pos.getX() + 0.5, world.getTopY(Heightmap.Type.MOTION_BLOCKING, pos.getX(), pos.getZ()) + 1, pos.getZ() + 0.5, entity.yaw, entity.pitch);
			// close containers
			((PlayerEntity) entity).openContainer(null);
			return true;
		} else {
			float yaw = MathHelper.wrapDegrees(entity.yaw);
			float pitch = MathHelper.wrapDegrees(entity.pitch);
			pitch = MathHelper.clamp(pitch, -90.0F, 90.0F);
			entity.detach();
			entity.dimension = world.dimension.getType();
			Entity eRef = entity;

			entity = (LivingEntity) entity.getType().create(world);
			if (entity == null) {
				return false;
			}

			entity.copyFrom(eRef);
			entity.refreshPositionAndAngles(pos.getX() + 0.5, world.getTopY(Heightmap.Type.MOTION_BLOCKING, pos.getX(), pos.getZ()) + 1, pos.getZ() + 0.5, yaw, pitch);
			entity.setHeadYaw(yaw);
			world.onDimensionChanged(entity);
			entity.removed = true;
			return true;
		}
	}
}
