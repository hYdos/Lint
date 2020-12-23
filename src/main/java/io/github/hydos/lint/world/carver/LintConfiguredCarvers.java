package io.github.hydos.lint.world.carver;

import net.minecraft.world.gen.ProbabilityConfig;
import net.minecraft.world.gen.carver.ConfiguredCarver;

public interface LintConfiguredCarvers {
	ConfiguredCarver<ProbabilityConfig> CAVE = LintCaveCarver.INSTANCE.configure(new ProbabilityConfig(0.09F));
}
