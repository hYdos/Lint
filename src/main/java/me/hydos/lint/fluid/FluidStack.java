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

import net.minecraft.fluid.Fluid;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.util.Identifier;

/**
 * Used for storing fluids where {@link net.minecraft.fluid.FluidState} can't be used
 * @author hydos
 */
public class FluidStack {
	public float level;
	public Identifier fluid;

	public FluidStack(float level, Identifier fluid) {
		if(level > 8) {
			throw new RuntimeException("Level of fluid is beyond maximum value (8)");
		}
		this.level = level;
		this.fluid = fluid;
	}

	public CompoundTag toTag() {
		CompoundTag tag = new CompoundTag();
		tag.putFloat("level", level);
		tag.putString("fluid", fluid.toString());
		return tag;
	}

	public static FluidStack fromTag(CompoundTag tag) {
		return new FluidStack(tag.getFloat("level"), new Identifier(tag.getString("fluid")));
	}

	public static FluidStack of(LintFluids.FluidEntry entry) {
		return of(entry, 1);
	}

	public static FluidStack of(LintFluids.FluidEntry entry, float level) {
		return new FluidStack(level, LintFluids.getId(entry));
	}

	public Fluid get() {
		return LintFluids.MOLTEN_FLUID_MAP.get(fluid).getStill();
	}
}
