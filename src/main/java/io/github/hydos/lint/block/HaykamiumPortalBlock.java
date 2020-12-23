package io.github.hydos.lint.block;

import io.github.hydos.lint.world.dimension.Dimensions;
import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.entity.Entity;
import net.minecraft.network.packet.s2c.play.StopSoundS2CPacket;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class HaykamiumPortalBlock extends Block {
	public HaykamiumPortalBlock(FabricBlockSettings settings) {
		super(settings);
	}

	@Override
	public void onEntityCollision(BlockState state, World world, BlockPos pos, Entity entity) {
		if(!world.isClient()){
			ServerWorld haykamWorld = ((ServerWorld)world).getServer().getWorld(Dimensions.HAYKAM_WORLD);
			if (haykamWorld == null) {
				return;
			}
			entity.moveToWorld(haykamWorld);
			if(entity instanceof ServerPlayerEntity){
				ServerPlayerEntity serverPlayerEntity = (ServerPlayerEntity) entity;
				serverPlayerEntity.networkHandler.sendPacket(new StopSoundS2CPacket());
			}
		}
	}
}
