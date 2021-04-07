/*
 * Lint
 * Copyright (C) 2020 hYdos, Valoeghese, ramidzkh
 *
 * This program is free software; you can redistribute it and/or
 * modify it under the terms of the GNU Lesser General Public
 * License as published by the Free Software Foundation; either
 * version 3 of the License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 * Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with this program; if not, write to the Free Software Foundation,
 * Inc., 51 Franklin Street, Fifth Floor, Boston, MA  02110-1301, USA.
 */

package me.hydos.lint.fluid;

import me.hydos.lint.block.LintBlocks;
import me.hydos.lint.fluid.base.LintFluid;
import net.minecraft.block.BlockState;
import net.minecraft.fluid.FlowableFluid;
import net.minecraft.fluid.Fluid;
import net.minecraft.fluid.FluidState;
import net.minecraft.item.Item;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.Properties;

public abstract class MoltenMetalFluid extends LintFluid {

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
		return LintBlocks.getFluid(still).with(Properties.LEVEL_15, method_15741(state));
	}

	public static class Flowing extends MoltenMetalFluid {

		public Flowing() {
			flowing = this;
		}

		public void setStill(FlowableFluid still) {
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

		public void setFlowing(FlowableFluid flowing) {
			this.flowing = flowing;
		}

		@Override
		public int getLevel(FluidState fluidState) {
			return fluidState.get(LEVEL);
		}

		@Override
		public boolean isStill(FluidState fluidState) {
			return true;
		}

		@Override
		protected void appendProperties(StateManager.Builder<Fluid, FluidState> builder) {
			super.appendProperties(builder);
			builder.add(LEVEL);
		}
	}
}
