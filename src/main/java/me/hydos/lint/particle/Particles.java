package me.hydos.lint.particle;

import me.hydos.lint.Lint;
import net.fabricmc.fabric.api.particle.v1.FabricParticleTypes;
import net.minecraft.particle.DefaultParticleType;
import net.minecraft.util.registry.Registry;

public class Particles {

	public static final DefaultParticleType FALLEN_MYSTICAL_LEAF = FabricParticleTypes.simple();

	public static void register() {
		Registry.register(Registry.PARTICLE_TYPE, Lint.id("fallen_mystical_leaf"), FALLEN_MYSTICAL_LEAF);
	}
}
