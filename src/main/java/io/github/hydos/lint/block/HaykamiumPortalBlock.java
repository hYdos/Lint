package io.github.hydos.lint.block;

import io.github.hydos.lint.world.dimension.Dimensions;
import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.entity.Entity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class HaykamiumPortalBlock extends Block {
	public HaykamiumPortalBlock(FabricBlockSettings settings) {
		super(settings);
	}

	@Override
	public void onEntityCollision(BlockState state, World world, BlockPos pos, Entity entity) {
		if(!world.isClient()){
			entity.moveToWorld(world.getServer().getWorld(Dimensions.HAYKAM_WORLD));
		}
	}
}
