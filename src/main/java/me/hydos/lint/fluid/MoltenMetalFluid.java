package me.hydos.lint.fluid;

import me.hydos.lint.block.Blocks;
import me.hydos.lint.fluid.base.ModdedFluid;
import net.minecraft.block.BlockState;
import net.minecraft.fluid.FlowableFluid;
import net.minecraft.fluid.Fluid;
import net.minecraft.fluid.FluidState;
import net.minecraft.item.Item;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.Properties;

public abstract class MoltenMetalFluid extends ModdedFluid {

	public Fluid flowing;
	public Fluid still;

	@Override
	public Fluid getFlowing() {
		return this.flowing;
	}

	@Override
	public Fluid getStill() {
		return this.still;
	}

	@Override
	public Item getBucketItem() {
		return null;
	}

	@Override
	protected BlockState toBlockState(FluidState state) {
		return Blocks.getFluid(still).with(Properties.LEVEL_15, method_15741(state));
	}

	public static class Flowing extends MoltenMetalFluid {

		public Flowing() {
			flowing = this;
		}

		public void setStill(FlowableFluid still){
			this.still = still;
		}

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

		public Still() {
			still = this;
		}

		public void setFlowing(FlowableFluid flowing){
			this.flowing = flowing;
		}

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
