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

import java.util.Arrays;
import java.util.function.Supplier;

import com.google.common.base.Suppliers;
import com.google.common.collect.ImmutableList;

import me.hydos.lint.Lint;
import me.hydos.lint.block.LintBlocks;
import me.hydos.lint.world.feature.modifier.Tree;
import me.hydos.lint.world.feature.util.FeatureFactory;
import net.minecraft.structure.rule.BlockMatchRuleTest;
import net.minecraft.structure.rule.RuleTest;
import net.minecraft.util.registry.BuiltinRegistries;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.gen.UniformIntDistribution;
import net.minecraft.world.gen.decorator.CountExtraDecoratorConfig;
import net.minecraft.world.gen.decorator.Decorator;
import net.minecraft.world.gen.decorator.DecoratorConfig;
import net.minecraft.world.gen.decorator.NopeDecoratorConfig;
import net.minecraft.world.gen.feature.ConfiguredFeature;
import net.minecraft.world.gen.feature.ConfiguredFeatures;
import net.minecraft.world.gen.feature.DefaultFeatureConfig;
import net.minecraft.world.gen.feature.Feature;
import net.minecraft.world.gen.feature.FeatureConfig;
import net.minecraft.world.gen.feature.OreFeatureConfig;
import net.minecraft.world.gen.feature.RandomFeatureConfig;
import net.minecraft.world.gen.feature.RandomPatchFeatureConfig;
import net.minecraft.world.gen.feature.SimpleRandomFeatureConfig;
import net.minecraft.world.gen.feature.SingleStateFeatureConfig;
import net.minecraft.world.gen.feature.TreeFeatureConfig;
import net.minecraft.world.gen.feature.size.TwoLayersFeatureSize;
import net.minecraft.world.gen.foliage.BlobFoliagePlacer;
import net.minecraft.world.gen.foliage.SpruceFoliagePlacer;
import net.minecraft.world.gen.placer.DoublePlantPlacer;
import net.minecraft.world.gen.placer.SimpleBlockPlacer;
import net.minecraft.world.gen.stateprovider.SimpleBlockStateProvider;

public class FeaturesOld {
	/**
	 * UNCONFIGURED FEATURES
	 **/
	public static final Feature<TreeFeatureConfig> CANOPY_TREE = register("canopy_tree", new CanopyTreeFeature());
	public static final PortalFeature RETURN_PORTAL = register("portal", new PortalFeature());
	public static final Feature<DefaultFeatureConfig> VERTICAL_SHAFT = register("vertical_shaft", new VerticalShaftFeature());
	public static final Feature<DefaultFeatureConfig> FADING_ASH = register("fading_ash", new FadingAshFeature());
	public static final Feature<DefaultFeatureConfig> TOWN = register("town", new TownFeature());
	public static final Feature<SingleStateFeatureConfig> HANGING_BLOCK = register("hanging_block", new HangingBlockFeature());

	/**
	 * UNCONFIGURED DECORATORS
	 */
	public static final Decorator<NopeDecoratorConfig> UNDER_ISLAND = register("under_island", new UnderIslandDecorator());

	public static final ConfiguredFeature<TreeFeatureConfig, ?> CORRUPT_TREE = FeatureFactory.register("corrupt_tree", Tree.WORLD_MODIFIER, new TreeFeatureConfig.Builder(
			new SimpleBlockStateProvider(LintBlocks.CORRUPT_LOG.getDefaultState()), new SimpleBlockStateProvider(LintBlocks.CORRUPT_LEAVES.getDefaultState()),
			new BlobFoliagePlacer(UniformIntDistribution.of(2), UniformIntDistribution.of(0), 3), new LintTrunkPlacer(4, 2, 0),
			new TwoLayersFeatureSize(1, 0, 1)).ignoreVines().build());

	public static final ConfiguredFeature<?, ?> CORRUPT_TREES = register("corrupt_trees", CORRUPT_TREE.decorate(Decorator.COUNT_EXTRA.configure(new CountExtraDecoratorConfig(10, 0.1F, 1))));

	private static final TreeFeatureConfig BASED_CONFIG = new TreeFeatureConfig.Builder(new SimpleBlockStateProvider(LintBlocks.MYSTICAL_LOG.getDefaultState()),
			new SimpleBlockStateProvider(LintBlocks.MYSTICAL_LEAVES.getDefaultState()),
			new BlobFoliagePlacer(UniformIntDistribution.of(2), UniformIntDistribution.of(0), 3),
			new LintTrunkPlacer(4, 2, 0), new TwoLayersFeatureSize(1, 0, 1)).ignoreVines().build();

	public static final ConfiguredFeature<TreeFeatureConfig, ?> MYSTICAL_TREE = FeatureFactory.register("mystical_tree", Tree.WORLD_MODIFIER, BASED_CONFIG);

	public static final ConfiguredFeature<TreeFeatureConfig, ?> TALL_MYSTICAL_TREE = FeatureFactory.register("tall_mystical_tree", Tree.WORLD_MODIFIER,
			new TreeFeatureConfig.Builder(new SimpleBlockStateProvider(LintBlocks.MYSTICAL_LOG.getDefaultState()),
					new SimpleBlockStateProvider(LintBlocks.MYSTICAL_LEAVES.getDefaultState()),
					new BlobFoliagePlacer(UniformIntDistribution.of(2), UniformIntDistribution.of(0), 3),
					new LintTrunkPlacer(6, 4, 0), new TwoLayersFeatureSize(1, 0, 1)).ignoreVines().build());

	public static final ConfiguredFeature<TreeFeatureConfig, ?> FROZEN_TREE = FeatureFactory.register("frozen_tree", Tree.WORLD_MODIFIER, new TreeFeatureConfig.Builder(new SimpleBlockStateProvider(LintBlocks.MYSTICAL_LOG.getDefaultState()),
			new SimpleBlockStateProvider(LintBlocks.FROZEN_LEAVES.getDefaultState()),
			new SpruceFoliagePlacer(UniformIntDistribution.of(2, 1), UniformIntDistribution.of(0, 2), UniformIntDistribution.of(1, 1)),
			new LintTrunkPlacer(4, 2, 1), new TwoLayersFeatureSize(2, 0, 1)).ignoreVines().build());

	public static final ConfiguredFeature<?, ?> MYSTICAL_TREES = register("mystical_trees", MYSTICAL_TREE
			.decorate(Decorator.COUNT_EXTRA.configure(new CountExtraDecoratorConfig(3, 0.3F, 1))));

	public static final ConfiguredFeature<?, ?> FROZEN_TREES = register("frozen_trees", FROZEN_TREE
			.decorate(Decorator.COUNT_EXTRA.configure(new CountExtraDecoratorConfig(4, 0.3F, 1))));

	public static final ConfiguredFeature<TreeFeatureConfig, ?> CANOPY_TREE_CONFIGURED = register("canopy_tree", CANOPY_TREE.configure(BASED_CONFIG));

	public static final ConfiguredFeature<?, ?> THICK_MYSTICAL_TREES = register("thick_mystical_trees", Feature.RANDOM_SELECTOR.configure(
			new RandomFeatureConfig(ImmutableList.of(CANOPY_TREE_CONFIGURED.withChance(0.15f), MYSTICAL_TREE.withChance(0.33f)), TALL_MYSTICAL_TREE))
			.decorate(ConfiguredFeatures.Decorators.SQUARE_HEIGHTMAP).decorate(Decorator.COUNT_EXTRA.configure(new CountExtraDecoratorConfig(18, 0.3F, 1))));

	public static final ConfiguredFeature<?, ?> MYSTICAL_ROCKS = register("mystical_rocks", Feature.RANDOM_SELECTOR.configure(
			new RandomFeatureConfig(
					ImmutableList.of(
							Feature.FOREST_ROCK.configure(new SingleStateFeatureConfig(LintBlocks.MAGNETITE_DEPOSIT.getDefaultState())).withChance(0.06f)
							),
					Feature.FOREST_ROCK.configure(new SingleStateFeatureConfig(LintBlocks.FUSED_COBBLESTONE.getDefaultState()))
					))
			.decorate(ConfiguredFeatures.Decorators.SQUARE_HEIGHTMAP).repeatRandomly(2)
			);

	/**
	 * Hanging Crystals
	 */
	public static final ConfiguredFeature<?, ?> FLOATING_ISLAND_ALLOS_CRYSTAL = register("floating_island_allos_crystal", HANGING_BLOCK
			.configure(new SingleStateFeatureConfig(LintBlocks.ALLOS_CRYSTAL.getDefaultState()))
			.rangeOf(60).spreadHorizontally().repeatRandomly(3));

	@SuppressWarnings("unchecked")
	public static final ConfiguredFeature<?, ?> DAWN_SHARDLANDS_SHARDS = register("dawn_shardlands_shards", Feature.SIMPLE_RANDOM_SELECTOR.configure(
			new SimpleRandomFeatureConfig(Arrays.asList(ImmutableList.of(
					HANGING_BLOCK.configure(new SingleStateFeatureConfig(LintBlocks.ALLOS_CRYSTAL.getDefaultState())),
					HANGING_BLOCK.configure(new SingleStateFeatureConfig(LintBlocks.MANOS_CRYSTAL.getDefaultState())))
					.stream().map(Suppliers::ofInstance).toArray(Supplier[]::new))
					))
			.decorate(UNDER_ISLAND.configure(new NopeDecoratorConfig()).spreadHorizontally().repeat(UniformIntDistribution.of(3, 5)).applyChance(20)));

	/**
	 * ORES
	 */
	public static final RuleTest FUNNI_STONE = new BlockMatchRuleTest(LintBlocks.FUSED_STONE);

	public static final ConfiguredFeature<?, ?> TARSCAN_ORE = register("tarscan_ore", Feature.ORE.configure(
			new OreFeatureConfig(FUNNI_STONE,
					LintBlocks.TARSCAN_ORE.getDefaultState(),
					9)).rangeOf(64).spreadHorizontally().repeat(20));

	public static final ConfiguredFeature<?, ?> SICIERON_ORE = register("sicieron_ore", Feature.ORE.configure(
			new OreFeatureConfig(FUNNI_STONE,
					LintBlocks.SICIERON_ORE.getDefaultState(),
					12)).rangeOf(40).spreadHorizontally().repeat(8));

	public static final ConfiguredFeature<?, ?> JUREL_ORE = register("jurel_ore", Feature.ORE.configure(
			new OreFeatureConfig(FUNNI_STONE,
					LintBlocks.TARSCAN_ORE.getDefaultState(),
					9)).rangeOf(10).spreadHorizontally().repeat(6));

	/**
	 * MISC
	 */
	public static final ConfiguredFeature<?, ?> CONFIGURED_RETURN_PORTAL = register("return_portal", RETURN_PORTAL.configure(DefaultFeatureConfig.INSTANCE)
			.decorate(ConfiguredFeatures.Decorators.HEIGHTMAP_WORLD_SURFACE.spreadHorizontally().applyChance(64)));

	public static final ConfiguredFeature<?, ?> CONFIGURED_VERTICAL_SHAFT = register("vertical_shaft", VERTICAL_SHAFT.configure(DefaultFeatureConfig.INSTANCE)
			.decorate(ConfiguredFeatures.Decorators.HEIGHTMAP_WORLD_SURFACE.spreadHorizontally().applyChance(2)));

	public static final ConfiguredFeature<?, ?> CONFIGURED_FADING_ASH = register("fading_ash", FADING_ASH.configure(DefaultFeatureConfig.INSTANCE)
			.decorate(Decorator.NOPE.configure(DecoratorConfig.DEFAULT)));

	public static final ConfiguredFeature<?, ?> CONFIGURED_TOWN = register("town", TOWN.configure(DefaultFeatureConfig.INSTANCE)
			.decorate(ConfiguredFeatures.Decorators.HEIGHTMAP_WORLD_SURFACE.repeat(1)));

	/**
	 * PATCHY FEATURES
	 **/
	private static ConfiguredFeature<?, ?> registerPatch(String id, RandomPatchFeatureConfig config, int count) {
		return register(id, Feature.RANDOM_PATCH.configure(config).decorate(ConfiguredFeatures.Decorators.SQUARE_HEIGHTMAP_SPREAD_DOUBLE).repeat(count));
	}

	private static ConfiguredFeature<?, ?> registerChancePatch(String id, RandomPatchFeatureConfig config, int chance) {
		return register(id, Feature.RANDOM_PATCH.configure(config).decorate(ConfiguredFeatures.Decorators.SQUARE_HEIGHTMAP_SPREAD_DOUBLE).applyChance(chance));
	}

	public static final ConfiguredFeature<?, ?> MYSTICAL_FLOWERS = registerPatch("mystical_flowers", Configs.MYSTICAL_DAISY_CONFIG, 3);
	public static final ConfiguredFeature<?, ?> MYSTICAL_STEMS = registerPatch("mystical_stems", Configs.MYSTICAL_STEM_CONFIG, 5);
	public static final ConfiguredFeature<?, ?> TUSSOCKS = registerPatch("tussocks", Configs.TUSSOCK_CONFIG, 2);
	public static final ConfiguredFeature<?, ?> RED_TUSSOCKS = registerPatch("red_tussocks", Configs.RED_TUSSOCK_CONFIG, 2);
	public static final ConfiguredFeature<?, ?> GENERIC_BLUE_FLOWERS = registerPatch("generic_blue_flowers", Configs.GENERIC_BLUE_FLOWERS_CONFIG, 1);
	public static final ConfiguredFeature<?, ?> MYSTICAL_GRASS = registerPatch("mystical_grass",Configs.MYSTICAL_GRASS_CONFIG, 3);
	public static final ConfiguredFeature<?, ?> LESS_MYSTICAL_STEMS = registerChancePatch("less_mystical_stems", Configs.BUNCHED_STEMS_CONFIG, 2);
	public static final ConfiguredFeature<?, ?> CORRUPT_STEMS = registerPatch("corrupt_stems", Configs.CORRUPT_STEM_CONFIG, 3);
	public static final ConfiguredFeature<?, ?> WILTED_FLOWERS = registerPatch("wilted_flowers", Configs.WILTED_FLOWER_CONFIG, 3);
	public static final ConfiguredFeature<?, ?> CORRUPT_FALLEN_LEAVES = registerPatch("corrupt_fallen_leaves", Configs.CORRUPT_FALLEN_LEAVES, 3);
	public static final ConfiguredFeature<?, ?> MYSTICAL_FALLEN_LEAVES = registerPatch("mystical_fallen_leaves", Configs.MYSTICAL_FALLEN_LEAVES, 3);

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

	public static final ConfiguredFeature<?, ?> SPEARMINTS = registerChancePatch("spearmints", Configs.SPEARMINT_CONFIG, 6);	
	public static final ConfiguredFeature<?, ?> WATERMINTS = registerPatch("watermints", Configs.WATERMINT_CONFIG, 1);
	public static final ConfiguredFeature<?, ?> DILLS = registerChancePatch("dills", Configs.DILL_CONFIG, 6);
	public static final ConfiguredFeature<?, ?> KUREI = registerChancePatch("kurei", Configs.KUREI_CONFIG, 3);

	private static <C extends FeatureConfig, F extends Feature<C>> F register(String name, F feature) {
		return Registry.register(Registry.FEATURE, Lint.id(name), feature);
	}

	private static <T extends DecoratorConfig> Decorator<T> register(String name, Decorator<T> decorator) {
		return Registry.register(Registry.DECORATOR, Lint.id(name), decorator);
	}

	private static <FC extends FeatureConfig> ConfiguredFeature<FC, ?> register(String id, ConfiguredFeature<FC, ?> configuredFeature) {
		return Registry.register(BuiltinRegistries.CONFIGURED_FEATURE, Lint.id(id), configuredFeature);
	}

	public static void initialize() {

	}

	public static final class Configs {
		public static final RandomPatchFeatureConfig MYSTICAL_DAISY_CONFIG = new RandomPatchFeatureConfig.Builder(
				new SimpleBlockStateProvider(LintBlocks.MYSTICAL_DAISY.getDefaultState()),
				SimpleBlockPlacer.INSTANCE).tries(16).build();

		public static final RandomPatchFeatureConfig MYSTICAL_STEM_CONFIG = new RandomPatchFeatureConfig.Builder(
				new SimpleBlockStateProvider(LintBlocks.MYSTICAL_STEM.getDefaultState()),
				SimpleBlockPlacer.INSTANCE)
				.tries(16).build();

		public static final RandomPatchFeatureConfig BUNCHED_STEMS_CONFIG = new RandomPatchFeatureConfig.Builder(
				new SimpleBlockStateProvider(LintBlocks.MYSTICAL_STEM.getDefaultState()),
				SimpleBlockPlacer.INSTANCE)
				.spreadX(3)
				.spreadZ(3)
				.tries(16).build();

		public static final RandomPatchFeatureConfig TUSSOCK_CONFIG = new RandomPatchFeatureConfig.Builder(
				new SimpleBlockStateProvider(LintBlocks.TUSSOCK.getDefaultState()),
				SimpleBlockPlacer.INSTANCE)
				.tries(32).build();

		public static final RandomPatchFeatureConfig RED_TUSSOCK_CONFIG = new RandomPatchFeatureConfig.Builder(
				new SimpleBlockStateProvider(LintBlocks.RED_TUSSOCK.getDefaultState()),
				SimpleBlockPlacer.INSTANCE)
				.tries(32).build();

		public static final RandomPatchFeatureConfig GENERIC_BLUE_FLOWERS_CONFIG = new RandomPatchFeatureConfig.Builder(
				new SimpleBlockStateProvider(LintBlocks.THAISA.getDefaultState()),
				new DoublePlantPlacer())
				.tries(8).build();

		public static final RandomPatchFeatureConfig MYSTICAL_GRASS_CONFIG = new RandomPatchFeatureConfig.Builder(
				new SimpleBlockStateProvider(LintBlocks.MYSTICAL_GRASS_PLANT.getDefaultState()),
				SimpleBlockPlacer.INSTANCE)
				.tries(16).build();
		public static final RandomPatchFeatureConfig CORRUPT_STEM_CONFIG = new RandomPatchFeatureConfig.Builder(
				new SimpleBlockStateProvider(LintBlocks.CORRUPT_STEM.getDefaultState()),
				SimpleBlockPlacer.INSTANCE)
				.tries(16).build();
		public static final RandomPatchFeatureConfig WILTED_FLOWER_CONFIG = new RandomPatchFeatureConfig.Builder(
				new SimpleBlockStateProvider(LintBlocks.WILTED_FLOWER.getDefaultState()),
				SimpleBlockPlacer.INSTANCE)
				.tries(16).build();

		public static final RandomPatchFeatureConfig SPEARMINT_CONFIG = new RandomPatchFeatureConfig.Builder(
				new SimpleBlockStateProvider(LintBlocks.SPEARMINT.getDefaultState()),
				SimpleBlockPlacer.INSTANCE)
				.tries(16).build();
		public static final RandomPatchFeatureConfig WATERMINT_CONFIG = new RandomPatchFeatureConfig.Builder( // quite rare as it needs not only the dirt/grass but also to be next to water. Most water is by sand.
				new SimpleBlockStateProvider(LintBlocks.WATERMINT.getDefaultState()),
				SimpleBlockPlacer.INSTANCE)
				.tries(32).needsWater().build();
		public static final RandomPatchFeatureConfig DILL_CONFIG = new RandomPatchFeatureConfig.Builder(
				new SimpleBlockStateProvider(LintBlocks.DILL.getDefaultState()),
				SimpleBlockPlacer.INSTANCE)
				.tries(16).build();
		public static final RandomPatchFeatureConfig KUREI_CONFIG = new RandomPatchFeatureConfig.Builder(
				new SimpleBlockStateProvider(LintBlocks.KUREI.getDefaultState()),
				SimpleBlockPlacer.INSTANCE)
				.tries(16).build();

		public static final RandomPatchFeatureConfig CORRUPT_FALLEN_LEAVES = new RandomPatchFeatureConfig.Builder(
				new SimpleBlockStateProvider(LintBlocks.CORRUPT_FALLEN_LEAVES.getDefaultState()),
				SimpleBlockPlacer.INSTANCE)
				.tries(6).build();
		public static final RandomPatchFeatureConfig MYSTICAL_FALLEN_LEAVES = new RandomPatchFeatureConfig.Builder(
				new SimpleBlockStateProvider(LintBlocks.MYSTICAL_FALLEN_LEAVES.getDefaultState()),
				SimpleBlockPlacer.INSTANCE)
				.tries(6).build();
	}
}
