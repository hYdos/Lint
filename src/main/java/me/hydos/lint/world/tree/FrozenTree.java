package me.hydos.lint.world.tree;

import me.hydos.lint.world.feature.FeaturesOld;
import net.minecraft.block.sapling.SaplingGenerator;
import net.minecraft.world.gen.feature.ConfiguredFeature;
import net.minecraft.world.gen.feature.TreeFeatureConfig;

import java.util.Random;

public class FrozenTree extends SaplingGenerator {
	@Override
	protected ConfiguredFeature<TreeFeatureConfig, ?> createTreeFeature(Random random, boolean bees) {
		return FeaturesOld.FROZEN_TREE;
	}
}
