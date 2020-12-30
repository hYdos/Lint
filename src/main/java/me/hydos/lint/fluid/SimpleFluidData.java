package me.hydos.lint.fluid;

import net.minecraft.fluid.Fluid;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.util.Identifier;

/**
 * Used for storing fluids where {@link net.minecraft.fluid.FluidState} is an overcomplicated system
 */
public class SimpleFluidData {
	public float level;
	public Identifier fluid;

	public SimpleFluidData(float level, Identifier fluid) {
		this.level = level;
		this.fluid = fluid;
	}

	public CompoundTag toTag() {
		CompoundTag tag = new CompoundTag();
		tag.putFloat("level", level);
		tag.putString("fluid", fluid.toString());
		return tag;
	}

	public static SimpleFluidData fromTag(CompoundTag tag) {
		return new SimpleFluidData(tag.getFloat("level"), new Identifier(tag.getString("fluid")));
	}

	public static SimpleFluidData of(LintFluids.FluidEntry entry) {
		return of(entry, 1);
	}

	public static SimpleFluidData of(LintFluids.FluidEntry entry, float level) {
		return new SimpleFluidData(level, LintFluids.getId(entry));
	}

	public Fluid get() {
		return LintFluids.MOLTEN_FLUID_MAP.get(fluid).getStill();
	}
}
