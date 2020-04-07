package me.hydos.lint.dimensions.haykam.biomes;

import net.minecraft.block.BlockState;
import net.minecraft.world.gen.decorator.CountDecoratorConfig;
import net.minecraft.world.gen.decorator.Decorator;
import net.minecraft.world.gen.feature.ConfiguredFeature;
import net.minecraft.world.gen.feature.Feature;
import net.minecraft.world.gen.feature.RandomPatchFeatureConfig;
import net.minecraft.world.gen.placer.SimpleBlockPlacer;
import net.minecraft.world.gen.stateprovider.SimpleBlockStateProvider;

public final class BiomeUtils {
	public static ConfiguredFeature<?, ?> randomPatch(BlockState state, int count) {
		return Feature.RANDOM_PATCH.configure(new RandomPatchFeatureConfig.Builder(
				new SimpleBlockStateProvider(
						state
						),
				new SimpleBlockPlacer()).tries(32).build()
				).createDecoratedFeature(Decorator.COUNT_HEIGHTMAP_DOUBLE.configure(new CountDecoratorConfig(count)));
	}
	
	public static ConfiguredFeature<?, ?> flower(BlockState state, int tries, int count) {
		return Feature.FLOWER.configure(new RandomPatchFeatureConfig.Builder(
				new SimpleBlockStateProvider(
						state
						),
				new SimpleBlockPlacer()).tries(tries).build()
				).createDecoratedFeature(Decorator.COUNT_HEIGHTMAP_32.configure(new CountDecoratorConfig(count)));
	}
}
