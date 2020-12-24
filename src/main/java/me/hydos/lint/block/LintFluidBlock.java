package me.hydos.lint.block;

import net.minecraft.block.BlockRenderType;
import net.minecraft.block.BlockState;
import net.minecraft.block.FluidBlock;
import net.minecraft.fluid.FlowableFluid;

public class LintFluidBlock extends FluidBlock {

	public LintFluidBlock(FlowableFluid fluid, Settings settings) {
		super(fluid, settings);
	}

	@Override
	public BlockRenderType getRenderType(BlockState state) {
		return BlockRenderType.INVISIBLE;
	}
}
