package me.hydos.lint.block;

import net.minecraft.block.*;
import net.minecraft.item.ItemPlacementContext;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.util.shape.VoxelShapes;
import net.minecraft.world.BlockView;
import net.minecraft.world.WorldView;

public class LintTallFlowerBlock extends PlantBlock {


	public LintTallFlowerBlock(Settings settings) {
		super(settings.nonOpaque());
	}

	@Override
	protected boolean canPlantOnTop(BlockState floor, BlockView world, BlockPos pos) {
		Block block = floor.getBlock();
		return block == LintBlocks.MYSTICAL_GRASS || block == LintBlocks.CORRUPT_GRASS;
	}

	@Override
	public VoxelShape getOutlineShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
		return VoxelShapes.cuboid(0, 0, 0, 1, 2, 1);
	}

	@Override
	public boolean canPlaceAt(BlockState state, WorldView world, BlockPos pos) {
		BlockPos blockPos = pos.down();
		return this.canPlantOnTop(world.getBlockState(blockPos), world, blockPos) && world.getBlockState(blockPos.up()).getBlock() != Blocks.AIR;
	}

	@Override
	public boolean canReplace(BlockState state, ItemPlacementContext context) {
		return false;
	}
}
