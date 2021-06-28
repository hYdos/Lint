package me.hydos.lint.world.carver;

import net.minecraft.util.math.floatprovider.FloatProvider;
import net.minecraft.world.gen.YOffset;
import net.minecraft.world.gen.carver.CaveCarverConfig;
import net.minecraft.world.gen.heightprovider.HeightProvider;

public class LintCaveCarverConfig extends CaveCarverConfig {
	public LintCaveCarverConfig(HeightProvider y, FloatProvider yScale, YOffset lavaLevel, boolean aquifers, FloatProvider horizontalRadiusMultiplier, FloatProvider verticalRadiusMultiplier, FloatProvider floorLevel) {
		super(0.09F, y, yScale, lavaLevel, aquifers, horizontalRadiusMultiplier, verticalRadiusMultiplier, floorLevel);
	}
}
