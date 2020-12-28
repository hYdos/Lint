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
import net.minecraft.world.gen.feature.*;
import net.minecraft.world.gen.feature.size.TwoLayersFeatureSize;
import net.minecraft.world.gen.foliage.BlobFoliagePlacer;
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

	public static final ConfiguredFeature<?, ?> CORRUPT_TREES = register("corrupt_trees", TREE.configure((
			new TreeFeatureConfig.Builder(
					new SimpleBlockStateProvider(LintBlocks.CORRUPT_LOG.getDefaultState()), new SimpleBlockStateProvider(LintBlocks.CORRUPT_LEAVES.getDefaultState()),
					new BlobFoliagePlacer(UniformIntDistribution.of(2), UniformIntDistribution.of(0), 3), new LintTrunkPlacer(4, 2, 0),
					new TwoLayersFeatureSize(1, 0, 1))).ignoreVines().build()).decorate(Decorator.COUNT_EXTRA.configure(new CountExtraDecoratorConfig(10, 0.1F, 1))));

	public static final ConfiguredFeature<?, ?> MYSTICAL_TREES = register("mystical_trees", TREE.configure((
			new TreeFeatureConfig.Builder(new SimpleBlockStateProvider(LintBlocks.MYSTICAL_LOG.getDefaultState()),
					new SimpleBlockStateProvider(LintBlocks.MYSTICAL_LEAVES.getDefaultState()),
					new BlobFoliagePlacer(UniformIntDistribution.of(2), UniformIntDistribution.of(0), 3),
					new LintTrunkPlacer(4, 2, 0), new TwoLayersFeatureSize(1, 0, 1))).ignoreVines().build()).decorate(Decorator.COUNT_EXTRA.configure(new CountExtraDecoratorConfig(3, 0.3F, 1))));

	public static final ConfiguredFeature<?, ?> THICK_MYSTICAL_TREES = register("thick_mystical_trees", TREE.configure((
			new TreeFeatureConfig.Builder(new SimpleBlockStateProvider(LintBlocks.MYSTICAL_LOG.getDefaultState()),
					new SimpleBlockStateProvider(LintBlocks.MYSTICAL_LEAVES.getDefaultState()),
					new BlobFoliagePlacer(UniformIntDistribution.of(2), UniformIntDistribution.of(0), 3),
					new LintTrunkPlacer(6, 4, 0), new TwoLayersFeatureSize(1, 0, 1))).ignoreVines().build()).decorate(Decorator.COUNT_EXTRA.configure(new CountExtraDecoratorConfig(290, 0.6F, 9))));

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
			.decorate(ConfiguredFeatures.Decorators.HEIGHTMAP_WORLD_SURFACE.spreadHorizontally().applyChance(32)));

	public static final ConfiguredFeature<?, ?> CONFIGURED_VERTICAL_SHAFT = register("vertical_shaft", VERTICAL_SHAFT.configure(DefaultFeatureConfig.INSTANCE)
			.decorate(ConfiguredFeatures.Decorators.HEIGHTMAP_WORLD_SURFACE.spreadHorizontally().applyChance(2)));

	public static final ConfiguredFeature<?, ?> CONFIGURED_FADING_ASH = register("fading_ash", FADING_ASH.configure(DefaultFeatureConfig.INSTANCE)
			.decorate(Decorator.NOPE.configure(DecoratorConfig.DEFAULT)));

	/**
	 * PATCHY FEATURES
	 **/
	public static final ConfiguredFeature<?, ?> MYSTICAL_FLOWERS = register("mystical_flowers", (ConfiguredFeature<? extends FeatureConfig, ? extends Feature<? extends FeatureConfig>>) Feature.RANDOM_PATCH.configure(Configs.MYSTICAL_DAISY_CONFIG).decorate(ConfiguredFeatures.Decorators.SQUARE_HEIGHTMAP_SPREAD_DOUBLE).repeat(3));
	public static final ConfiguredFeature<?, ?> MYSTICAL_STEMS = register("mystical_stems", (ConfiguredFeature<? extends FeatureConfig, ? extends Feature<? extends FeatureConfig>>) Feature.RANDOM_PATCH.configure(Configs.MYSTICAL_STEM_CONFIG).decorate(ConfiguredFeatures.Decorators.SQUARE_HEIGHTMAP_SPREAD_DOUBLE).repeat(3));
	public static final ConfiguredFeature<?, ?> CORRUPT_STEMS = register("corrupt_stems", (ConfiguredFeature<? extends FeatureConfig, ? extends Feature<? extends FeatureConfig>>) Feature.RANDOM_PATCH.configure(Configs.CORRUPT_STEM_CONFIG).decorate(ConfiguredFeatures.Decorators.SQUARE_HEIGHTMAP_SPREAD_DOUBLE).repeat(3));
	public static final ConfiguredFeature<?, ?> WILTED_FLOWERS = register("wilted_flowers", (ConfiguredFeature<? extends FeatureConfig, ? extends Feature<? extends FeatureConfig>>) Feature.RANDOM_PATCH.configure(Configs.WILTED_FLOWER_CONFIG).decorate(ConfiguredFeatures.Decorators.SQUARE_HEIGHTMAP_SPREAD_DOUBLE).repeat(3));

	public static final ConfiguredFeature<?, ?> CORRUPT_FALLEN_LEAVES = register("corrupt_fallen_leaves", (ConfiguredFeature<? extends FeatureConfig, ? extends Feature<? extends FeatureConfig>>) Feature.RANDOM_PATCH.configure(Configs.CORRUPT_FALLEN_LEAVES).decorate(ConfiguredFeatures.Decorators.SQUARE_HEIGHTMAP_SPREAD_DOUBLE).repeat(3));
	public static final ConfiguredFeature<?, ?> MYSTICAL_FALLEN_LEAVES = register("mystical_fallen_leaves", (ConfiguredFeature<? extends FeatureConfig, ? extends Feature<? extends FeatureConfig>>) Feature.RANDOM_PATCH.configure(Configs.MYSTICAL_FALLEN_LEAVES).decorate(ConfiguredFeatures.Decorators.SQUARE_HEIGHTMAP_SPREAD_DOUBLE).repeat(3));

	private static <C extends FeatureConfig, F extends Feature<C>> F register(String name, F feature) {
		return Registry.register(Registry.FEATURE, Lint.id(name), feature);
	}

	private static <FC extends FeatureConfig> ConfiguredFeature<?, ?> register(String id, ConfiguredFeature<FC, ?> configuredFeature) {
		return Registry.register(BuiltinRegistries.CONFIGURED_FEATURE, Lint.id(id), configuredFeature);
	}

	public static void register() {

	}

	public static final class Configs {
		public static final RandomPatchFeatureConfig MYSTICAL_DAISY_CONFIG;
		public static final RandomPatchFeatureConfig MYSTICAL_STEM_CONFIG;
		public static final RandomPatchFeatureConfig CORRUPT_STEM_CONFIG;
		public static final RandomPatchFeatureConfig WILTED_FLOWER_CONFIG;

		public static final RandomPatchFeatureConfig CORRUPT_FALLEN_LEAVES;
		public static final RandomPatchFeatureConfig MYSTICAL_FALLEN_LEAVES;

		static {
			MYSTICAL_DAISY_CONFIG = (new RandomPatchFeatureConfig.Builder(new SimpleBlockStateProvider(LintBlocks.MYSTICAL_DAISY.getDefaultState()), SimpleBlockPlacer.INSTANCE)).tries(16).build();
			MYSTICAL_STEM_CONFIG = (new RandomPatchFeatureConfig.Builder(new SimpleBlockStateProvider(LintBlocks.MYSTICAL_STEM.getDefaultState()), SimpleBlockPlacer.INSTANCE)).tries(16).build();
			CORRUPT_STEM_CONFIG = (new RandomPatchFeatureConfig.Builder(new SimpleBlockStateProvider(LintBlocks.CORRUPT_STEM.getDefaultState()), SimpleBlockPlacer.INSTANCE)).tries(16).build();
			WILTED_FLOWER_CONFIG = (new RandomPatchFeatureConfig.Builder(new SimpleBlockStateProvider(LintBlocks.WILTED_FLOWER.getDefaultState()), SimpleBlockPlacer.INSTANCE)).tries(16).build();

			CORRUPT_FALLEN_LEAVES = (new RandomPatchFeatureConfig.Builder(new SimpleBlockStateProvider(LintBlocks.CORRUPT_FALLEN_LEAVES.getDefaultState()), SimpleBlockPlacer.INSTANCE)).tries(6).build();
			MYSTICAL_FALLEN_LEAVES = (new RandomPatchFeatureConfig.Builder(new SimpleBlockStateProvider(LintBlocks.MYSTICAL_FALLEN_LEAVES.getDefaultState()), SimpleBlockPlacer.INSTANCE)).tries(6).build();
		}
	}
}
