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

import me.hydos.lint.Lint;
import me.hydos.lint.block.LintBlocks;
import net.minecraft.structure.rule.BlockMatchRuleTest;
import net.minecraft.structure.rule.RuleTest;
import net.minecraft.util.registry.BuiltinRegistries;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.gen.UniformIntDistribution;
import net.minecraft.world.gen.decorator.CountExtraDecoratorConfig;
import net.minecraft.world.gen.decorator.Decorator;
import net.minecraft.world.gen.decorator.DecoratorConfig;
import net.minecraft.world.gen.feature.ConfiguredFeature;
import net.minecraft.world.gen.feature.ConfiguredFeatures;
import net.minecraft.world.gen.feature.DefaultFeatureConfig;
import net.minecraft.world.gen.feature.Feature;
import net.minecraft.world.gen.feature.FeatureConfig;
import net.minecraft.world.gen.feature.OreFeatureConfig;
import net.minecraft.world.gen.feature.RandomPatchFeatureConfig;
import net.minecraft.world.gen.feature.TreeFeatureConfig;
import net.minecraft.world.gen.feature.size.TwoLayersFeatureSize;
import net.minecraft.world.gen.foliage.BlobFoliagePlacer;
import net.minecraft.world.gen.placer.DoublePlantPlacer;
import net.minecraft.world.gen.placer.SimpleBlockPlacer;
import net.minecraft.world.gen.stateprovider.SimpleBlockStateProvider;

public class Features {
	/**
	 * UNCONFIGURED FEATURES
	 **/
	public static final Feature<TreeFeatureConfig> TREE = register("tree", new BetterTreeFeature(BetterTreeFeature.CODEC));
	public static final Feature<DefaultFeatureConfig> RETURN_PORTAL = register("portal", new PortalFeature());
	public static final Feature<DefaultFeatureConfig> VERTICAL_SHAFT = register("vertical_shaft", new VerticalShaftFeature());
	public static final Feature<DefaultFeatureConfig> FADING_ASH = register("fading_ash", new FadingAshFeature());
	public static final Feature<DefaultFeatureConfig> STRUCTURE = register("structure", new LintStructureFeature());
	public static final Feature<DefaultFeatureConfig> TOWN = register("town", new TownFeature());

	public static final ConfiguredFeature<TreeFeatureConfig, ?> CORRUPT_TREE = register("corrupt_tree", TREE.configure((
			new TreeFeatureConfig.Builder(
					new SimpleBlockStateProvider(LintBlocks.CORRUPT_LOG.getDefaultState()), new SimpleBlockStateProvider(LintBlocks.CORRUPT_LEAVES.getDefaultState()),
					new BlobFoliagePlacer(UniformIntDistribution.of(2), UniformIntDistribution.of(0), 3), new LintTrunkPlacer(4, 2, 0),
					new TwoLayersFeatureSize(1, 0, 1))).ignoreVines().build()));

	public static final ConfiguredFeature<?, ?> CORRUPT_TREES = register("corrupt_trees", CORRUPT_TREE.decorate(Decorator.COUNT_EXTRA.configure(new CountExtraDecoratorConfig(10, 0.1F, 1))));

	public static final ConfiguredFeature<TreeFeatureConfig, ?> MYSTICAL_TREE = register("mystical_tree", TREE.configure(new TreeFeatureConfig.Builder(new SimpleBlockStateProvider(LintBlocks.MYSTICAL_LOG.getDefaultState()),
			new SimpleBlockStateProvider(LintBlocks.MYSTICAL_LEAVES.getDefaultState()),
			new BlobFoliagePlacer(UniformIntDistribution.of(2), UniformIntDistribution.of(0), 3),
			new LintTrunkPlacer(4, 2, 0), new TwoLayersFeatureSize(1, 0, 1)).ignoreVines().build()));

	public static final ConfiguredFeature<?, ?> MYSTICAL_TREES = register("mystical_trees", MYSTICAL_TREE
			.decorate(Decorator.COUNT_EXTRA.configure(new CountExtraDecoratorConfig(3, 0.3F, 1))));

	public static final ConfiguredFeature<?, ?> THICK_MYSTICAL_TREES = register("thick_mystical_trees", TREE.configure((
			new TreeFeatureConfig.Builder(new SimpleBlockStateProvider(LintBlocks.MYSTICAL_LOG.getDefaultState()),
					new SimpleBlockStateProvider(LintBlocks.MYSTICAL_LEAVES.getDefaultState()),
					new BlobFoliagePlacer(UniformIntDistribution.of(2), UniformIntDistribution.of(0), 3),
					new LintTrunkPlacer(6, 4, 0), new TwoLayersFeatureSize(1, 0, 1))).ignoreVines().build()).decorate(Decorator.COUNT_EXTRA.configure(new CountExtraDecoratorConfig(190, 0.3F, 1))));

	/**
	 * ORES
	 */
	public static final RuleTest FUNNI_STONE = new BlockMatchRuleTest(LintBlocks.FUSED_STONE);

	public static final ConfiguredFeature<?, ?> TARSCAN_ORE = register("tarscan_ore", Feature.ORE.configure(
			new OreFeatureConfig(FUNNI_STONE,
					LintBlocks.TARSCAN_ORE.getDefaultState(),
					9)).rangeOf(64)).spreadHorizontally().repeat(20);

	public static final ConfiguredFeature<?, ?> SICIERON_ORE = register("sicieron_ore", Feature.ORE.configure(
			new OreFeatureConfig(FUNNI_STONE,
					LintBlocks.SICIERON_ORE.getDefaultState(),
					12)).rangeOf(40)).spreadHorizontally().repeat(8);

	public static final ConfiguredFeature<?, ?> JUREL_ORE = register("jurel_ore", Feature.ORE.configure(
			new OreFeatureConfig(FUNNI_STONE,
					LintBlocks.TARSCAN_ORE.getDefaultState(),
					9)).rangeOf(10)).spreadHorizontally().repeat(6);

	/**
	 * MISC
	 */
	public static final ConfiguredFeature<?, ?> CONFIGURED_RETURN_PORTAL = register("return_portal", RETURN_PORTAL.configure(DefaultFeatureConfig.INSTANCE)
			.decorate(ConfiguredFeatures.Decorators.HEIGHTMAP_WORLD_SURFACE.spreadHorizontally().applyChance(64)));

	public static final ConfiguredFeature<?, ?> CONFIGURED_VERTICAL_SHAFT = register("vertical_shaft", VERTICAL_SHAFT.configure(DefaultFeatureConfig.INSTANCE)
			.decorate(ConfiguredFeatures.Decorators.HEIGHTMAP_WORLD_SURFACE.spreadHorizontally().applyChance(2)));

	public static final ConfiguredFeature<?, ?> CONFIGURED_FADING_ASH = register("fading_ash", FADING_ASH.configure(DefaultFeatureConfig.INSTANCE)
			.decorate(Decorator.NOPE.configure(DecoratorConfig.DEFAULT)));

	public static final ConfiguredFeature<?, ?> CONFIGURED_STRUCTURE = register("structure", STRUCTURE.configure(DefaultFeatureConfig.INSTANCE));

	public static final ConfiguredFeature<?, ?> CONFIGURED_TOWN = register("town", TOWN.configure(DefaultFeatureConfig.INSTANCE)
			.decorate(ConfiguredFeatures.Decorators.HEIGHTMAP_WORLD_SURFACE.repeat(1)));

	/**
	 * PATCHY FEATURES
	 **/
	public static final ConfiguredFeature<?, ?> MYSTICAL_FLOWERS = register("mystical_flowers", Feature.RANDOM_PATCH.configure(Configs.MYSTICAL_DAISY_CONFIG)
			.decorate(ConfiguredFeatures.Decorators.SQUARE_HEIGHTMAP_SPREAD_DOUBLE).repeat(3));
	public static final ConfiguredFeature<?, ?> MYSTICAL_STEMS = register("mystical_stems", Feature.RANDOM_PATCH.configure(Configs.MYSTICAL_STEM_CONFIG)
			.decorate(ConfiguredFeatures.Decorators.SQUARE_HEIGHTMAP_SPREAD_DOUBLE).repeat(5));
	public static final ConfiguredFeature<?, ?> GENERIC_BLUE_FLOWERS = register("generic_blue_flowers", Feature.RANDOM_PATCH.configure(Configs.GENERIC_BLUE_FLOWERS_CONFIG)
			.decorate(ConfiguredFeatures.Decorators.SQUARE_HEIGHTMAP_SPREAD_DOUBLE).repeat(1));
	public static final ConfiguredFeature<?, ?> MYSTICAL_GRASS = register("mystical_grass", Feature.RANDOM_PATCH.configure(Configs.MYSTICAL_GRASS_CONFIG)
			.decorate(ConfiguredFeatures.Decorators.SQUARE_HEIGHTMAP_SPREAD_DOUBLE).repeat(3));
	public static final ConfiguredFeature<?, ?> CORRUPT_STEMS = register("corrupt_stems", Feature.RANDOM_PATCH.configure(Configs.CORRUPT_STEM_CONFIG)
			.decorate(ConfiguredFeatures.Decorators.SQUARE_HEIGHTMAP_SPREAD_DOUBLE).repeat(3));
	public static final ConfiguredFeature<?, ?> WILTED_FLOWERS = register("wilted_flowers", Feature.RANDOM_PATCH.configure(Configs.WILTED_FLOWER_CONFIG)
			.decorate(ConfiguredFeatures.Decorators.SQUARE_HEIGHTMAP_SPREAD_DOUBLE).repeat(3));

	public static final ConfiguredFeature<?, ?> CORRUPT_FALLEN_LEAVES = register("corrupt_fallen_leaves", Feature.RANDOM_PATCH.configure(Configs.CORRUPT_FALLEN_LEAVES)
			.decorate(ConfiguredFeatures.Decorators.SQUARE_HEIGHTMAP_SPREAD_DOUBLE).repeat(3));
	public static final ConfiguredFeature<?, ?> MYSTICAL_FALLEN_LEAVES = register("mystical_fallen_leaves", Feature.RANDOM_PATCH.configure(Configs.MYSTICAL_FALLEN_LEAVES)
			.decorate(ConfiguredFeatures.Decorators.SQUARE_HEIGHTMAP_SPREAD_DOUBLE).repeat(3));

	public static final ConfiguredFeature<?, ?> TATERBANES = register("taterbanes", Feature.RANDOM_PATCH.configure(
			new RandomPatchFeatureConfig.Builder(
					new SimpleBlockStateProvider(LintBlocks.TATERBANE.getDefaultState()),
					SimpleBlockPlacer.INSTANCE)
			.tries(1)
			.spreadX(1)
			.spreadY(1)
			.spreadZ(1)
			.build())
			.decorate(ConfiguredFeatures.Decorators.SQUARE_HEIGHTMAP.applyChance(9)));

	public static final ConfiguredFeature<?, ?> SPEARMINTS = register("spearmints", Feature.RANDOM_PATCH.configure(Configs.SPEARMINT_CONFIG)
			.decorate(ConfiguredFeatures.Decorators.SQUARE_HEIGHTMAP_SPREAD_DOUBLE).applyChance(6));
	public static final ConfiguredFeature<?, ?> WATERMINTS = register("watermints", Feature.RANDOM_PATCH.configure(Configs.WATERMINT_CONFIG)
			.decorate(ConfiguredFeatures.Decorators.SQUARE_HEIGHTMAP_SPREAD_DOUBLE).repeat(1));
	public static final ConfiguredFeature<?, ?> DILLS = register("dills", Feature.RANDOM_PATCH.configure(Configs.DILL_CONFIG)
			.decorate(ConfiguredFeatures.Decorators.SQUARE_HEIGHTMAP_SPREAD_DOUBLE).applyChance(6));
	public static final ConfiguredFeature<?, ?> KUREI = register("kurei", Feature.RANDOM_PATCH.configure(Configs.KUREI_CONFIG)
			.decorate(ConfiguredFeatures.Decorators.SQUARE_HEIGHTMAP_SPREAD_DOUBLE).applyChance(3));

	private static <C extends FeatureConfig, F extends Feature<C>> F register(String name, F feature) {
		return Registry.register(Registry.FEATURE, Lint.id(name), feature);
	}

	private static <FC extends FeatureConfig> ConfiguredFeature<FC, ?> register(String id, ConfiguredFeature<FC, ?> configuredFeature) {
		return Registry.register(BuiltinRegistries.CONFIGURED_FEATURE, Lint.id(id), configuredFeature);
	}

	public static void initialize() {

	}

	public static final class Configs {
		public static final RandomPatchFeatureConfig MYSTICAL_DAISY_CONFIG;
		public static final RandomPatchFeatureConfig MYSTICAL_STEM_CONFIG;
		public static final RandomPatchFeatureConfig GENERIC_BLUE_FLOWERS_CONFIG;
		public static final RandomPatchFeatureConfig MYSTICAL_GRASS_CONFIG;
		public static final RandomPatchFeatureConfig CORRUPT_STEM_CONFIG;
		public static final RandomPatchFeatureConfig WILTED_FLOWER_CONFIG;

		public static final RandomPatchFeatureConfig SPEARMINT_CONFIG;
		public static final RandomPatchFeatureConfig WATERMINT_CONFIG;
		public static final RandomPatchFeatureConfig DILL_CONFIG;
		public static final RandomPatchFeatureConfig KUREI_CONFIG;

		public static final RandomPatchFeatureConfig CORRUPT_FALLEN_LEAVES;
		public static final RandomPatchFeatureConfig MYSTICAL_FALLEN_LEAVES;

		static {
			MYSTICAL_DAISY_CONFIG = (new RandomPatchFeatureConfig.Builder(
					new SimpleBlockStateProvider(LintBlocks.MYSTICAL_DAISY.getDefaultState()),
					SimpleBlockPlacer.INSTANCE))
					.tries(16).build();
			MYSTICAL_STEM_CONFIG = (new RandomPatchFeatureConfig.Builder(
					new SimpleBlockStateProvider(LintBlocks.MYSTICAL_STEM.getDefaultState()),
					SimpleBlockPlacer.INSTANCE))
					.tries(16).build();
			GENERIC_BLUE_FLOWERS_CONFIG = (new RandomPatchFeatureConfig.Builder(
					new SimpleBlockStateProvider(LintBlocks.GENERIC_BLUE_FLOWER.getDefaultState()),
					new DoublePlantPlacer()))
					.tries(8).build();
			MYSTICAL_GRASS_CONFIG = (new RandomPatchFeatureConfig.Builder(
					new SimpleBlockStateProvider(LintBlocks.MYSTICAL_GRASS.getDefaultState()),
					SimpleBlockPlacer.INSTANCE))
					.tries(16).build();
			CORRUPT_STEM_CONFIG = (new RandomPatchFeatureConfig.Builder(
					new SimpleBlockStateProvider(LintBlocks.CORRUPT_STEM.getDefaultState()),
					SimpleBlockPlacer.INSTANCE))
					.tries(16).build();
			WILTED_FLOWER_CONFIG = (new RandomPatchFeatureConfig.Builder(
					new SimpleBlockStateProvider(LintBlocks.WILTED_FLOWER.getDefaultState()),
					SimpleBlockPlacer.INSTANCE))
					.tries(16).build();

			CORRUPT_FALLEN_LEAVES = (new RandomPatchFeatureConfig.Builder(
					new SimpleBlockStateProvider(LintBlocks.CORRUPT_FALLEN_LEAVES.getDefaultState()),
					SimpleBlockPlacer.INSTANCE))
					.tries(6).build();
			MYSTICAL_FALLEN_LEAVES = (new RandomPatchFeatureConfig.Builder(
					new SimpleBlockStateProvider(LintBlocks.MYSTICAL_FALLEN_LEAVES.getDefaultState()),
					SimpleBlockPlacer.INSTANCE))
					.tries(6).build();

			SPEARMINT_CONFIG = (new RandomPatchFeatureConfig.Builder(
					new SimpleBlockStateProvider(LintBlocks.SPEARMINT.getDefaultState()),
					SimpleBlockPlacer.INSTANCE))
					.tries(16).build();
			WATERMINT_CONFIG = (new RandomPatchFeatureConfig.Builder( // quite rare as it needs not only the dirt/grass but also to be next to water. Most water is by sand.
					new SimpleBlockStateProvider(LintBlocks.WATERMINT.getDefaultState()),
					SimpleBlockPlacer.INSTANCE))
					.tries(32).needsWater().build();
			DILL_CONFIG = (new RandomPatchFeatureConfig.Builder(
					new SimpleBlockStateProvider(LintBlocks.DILL.getDefaultState()),
					SimpleBlockPlacer.INSTANCE))
					.tries(16).build();
			KUREI_CONFIG = (new RandomPatchFeatureConfig.Builder(
					new SimpleBlockStateProvider(LintBlocks.KUREI.getDefaultState()),
					SimpleBlockPlacer.INSTANCE))
					.tries(16).build();
		}
	}
}
