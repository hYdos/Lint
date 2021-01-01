package me.hydos.lint.block;

import net.minecraft.block.BlockState;
import net.minecraft.block.ShapeContext;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.util.shape.VoxelShapes;
import net.minecraft.world.BlockView;

public class TaterbaneBlock extends LintGrassBlock {
	public TaterbaneBlock(StatusEffect effect, Settings settings) {
		super(effect, settings);
	}

	@Override
	public VoxelShape getOutlineShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
		return VoxelShapes.cuboid(2, 0, 2, 14, 6, 14);
	}

	@Override
	public OffsetType getOffsetType() {
		return OffsetType.NONE;
	}
}
