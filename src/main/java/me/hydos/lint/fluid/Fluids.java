package me.hydos.lint.fluid;

import me.hydos.lint.Lint;
import net.fabricmc.api.ModInitializer;
import net.minecraft.fluid.FlowableFluid;
import net.minecraft.util.registry.Registry;

public class Fluids implements ModInitializer {

	public static FlowableFluid STILL_MOLTEN_METAL = Registry.register(Registry.FLUID, Lint.id("molten_metal"), new MoltenMetalFluid.Still());
	public static FlowableFluid FLOWING_MOLTEN_METAL = Registry.register(Registry.FLUID, Lint.id("flowing_molten_metal"), new MoltenMetalFluid.Flowing());

	@Override
	public void onInitialize() {
	}
}
