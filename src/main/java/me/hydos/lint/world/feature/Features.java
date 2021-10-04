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

package me.hydos.lint.world.feature;

import me.hydos.lint.block.LintBlocks;
import me.hydos.lint.world.feature.trunkplacer.LintBendingTrunkPlacer;
import me.hydos.lint.world.feature.util.FeatureFactory;
import me.hydos.lint.world.feature.util.Placement;
import net.minecraft.block.BlockState;
import net.minecraft.util.math.intprovider.ConstantIntProvider;
import net.minecraft.util.math.intprovider.UniformIntProvider;
import net.minecraft.world.gen.decorator.CountExtraDecoratorConfig;
import net.minecraft.world.gen.feature.ConfiguredFeature;
import net.minecraft.world.gen.feature.Feature;
import net.minecraft.world.gen.feature.FeatureConfig;
import net.minecraft.world.gen.feature.FillLayerFeatureConfig;
import net.minecraft.world.gen.feature.TreeFeatureConfig;
import net.minecraft.world.gen.feature.size.TwoLayersFeatureSize;
import net.minecraft.world.gen.foliage.RandomSpreadFoliagePlacer;
import net.minecraft.world.gen.stateprovider.SimpleBlockStateProvider;

// TODO move features to this abstractified system because unneccesary abstractions are funi
// Vanilla-Based configured features can also go here (todo: move)
public class Features {
	private static SimpleBlockStateProvider provide(BlockState state) {
		return new SimpleBlockStateProvider(state);
	}

	public static ConfiguredFeature<?,?> GARGANTUAN_SPIKES = FeatureFactory.register(
			"gargantuan_spikes",
			new SpikesGenerator(),
			new FillLayerFeatureConfig(110, LintBlocks.ASPHALT.getDefaultState()));

	public static ConfiguredFeature<?,?> ASPHALT_ISLANDS = FeatureFactory.register(
			"asphalt_islands",
			new AsphaltIslandGenerator(),
			FeatureConfig.DEFAULT);

	public static ConfiguredFeature<TreeFeatureConfig,?> WITHERED_TREE = FeatureFactory.register(
			"withered_tree",
			Feature.TREE.configure(new TreeFeatureConfig.Builder(
						provide(LintBlocks.WITHERED_LOG.getDefaultState()),
						new LintBendingTrunkPlacer(5, 3, 0, 4, UniformIntProvider.create(2, 3)),
						provide(LintBlocks.WITHERED_LEAVES.getDefaultState()),
						provide(LintBlocks.WITHERED_SAPLING.getDefaultState()),
						new RandomSpreadFoliagePlacer(ConstantIntProvider.create(3), ConstantIntProvider.create(0), ConstantIntProvider.create(2), 50),
						new TwoLayersFeatureSize(1, 0, 1)
					).build()
					));

	public static class Worldgen {
		// DAWN SHARDLANDS FEATURES

		public static ConfiguredFeature<?,?> SHARDLANDS_SPIKES = Placement.CHANCE_SIMPLE.apply(
				"shardlands_spikes",
				GARGANTUAN_SPIKES,
				20);

		public static ConfiguredFeature<?,?> SHARDLANDS_SMOL_ISLANDS = Placement.CHANCE_RANGE.apply(
				"shardlands_small_islands",
				ASPHALT_ISLANDS,
				new int[] {7, 100, 135});

		public static ConfiguredFeature<?,?> SHARDLANDS_WITHERED_TREES = Placement.TREE_WORLD_SURFACE.apply(
				"shardlands_withered_trees",
				WITHERED_TREE,
				new CountExtraDecoratorConfig(0, 0.15f, 3));
	}
}
