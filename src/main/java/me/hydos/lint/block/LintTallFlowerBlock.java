package me.hydos.lint.block;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.ShapeContext;
import net.minecraft.block.TallFlowerBlock;
import net.minecraft.block.enums.DoubleBlockHalf;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.util.shape.VoxelShapes;
import net.minecraft.world.BlockView;

public class LintTallFlowerBlock extends TallFlowerBlock {
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
		return state.get(HALF) == DoubleBlockHalf.LOWER ? BOTTOM_MODEL : TOP_MODEL;
	}

	public static final VoxelShape BOTTOM_MODEL = VoxelShapes.cuboid(0.125, 0, 0.125, 0.875, 1, 0.875);
	public static final VoxelShape TOP_MODEL = VoxelShapes.cuboid(0.125, 0, 0.125, 0.875, 0.75, 0.875);
}
