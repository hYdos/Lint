package io.github.hydos.lint.fluid;

import io.github.hydos.lint.block.LintBlocks;
import net.minecraft.block.BlockState;
import net.minecraft.fluid.Fluid;
import net.minecraft.fluid.FluidState;
import net.minecraft.item.Item;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.Properties;

public abstract class MoltenMetalFluid extends ModdedFluid {

	@Override
	public Fluid getFlowing() {
		return Fluids.FLOWING_MOLTEN_METAL;
	}

	@Override
	public Fluid getStill() {
		return Fluids.STILL_MOLTEN_METAL;
	}

	@Override
	public Item getBucketItem() {
		return null;
	}

	@Override
	protected BlockState toBlockState(FluidState state) {
		return LintBlocks.MOLTEN_METAL_FLUID.getDefaultState().with(Properties.LEVEL_15, method_15741(state));
	}

	public static class Flowing extends MoltenMetalFluid {

		@Override
		protected void appendProperties(StateManager.Builder<Fluid, FluidState> builder) {
			super.appendProperties(builder);
			builder.add(LEVEL);
		}

		@Override
		public int getLevel(FluidState fluidState) {
			return fluidState.get(LEVEL);
		}

		@Override
		public boolean isStill(FluidState fluidState) {
			return false;
		}
	}

	public static class Still extends MoltenMetalFluid {

		@Override
		public int getLevel(FluidState fluidState) {
			return 8;
		}

		@Override
		public boolean isStill(FluidState fluidState) {
			return true;
		}
	}
}
