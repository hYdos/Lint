package io.github.hydos.lint.world.feature;

import io.github.hydos.lint.Lint;
import io.github.hydos.lint.resource.block.Blocks;
import net.minecraft.loot.function.LootFunction;
import net.minecraft.loot.function.LootFunctionType;
import net.minecraft.util.JsonSerializer;
import net.minecraft.util.registry.BuiltinRegistries;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.gen.UniformIntDistribution;
import net.minecraft.world.gen.decorator.CountExtraDecoratorConfig;
import net.minecraft.world.gen.decorator.Decorator;
import net.minecraft.world.gen.feature.*;
import net.minecraft.world.gen.feature.size.TwoLayersFeatureSize;
import net.minecraft.world.gen.foliage.BlobFoliagePlacer;
import net.minecraft.world.gen.placer.SimpleBlockPlacer;
import net.minecraft.world.gen.stateprovider.SimpleBlockStateProvider;
import net.minecraft.world.gen.trunk.StraightTrunkPlacer;

public class Features {
    /**
     * UNCONFIGURED FEATURES
     **/
    public static final Feature<TreeFeatureConfig> TREE = register("tree", new BetterTreeFeature(TreeFeatureConfig.CODEC));
    public static final Feature<DefaultFeatureConfig> RETURN_PORTAL = register("portal", new PortalFeature());
    public static final Feature<DefaultFeatureConfig> SPIRIT_SHRINE = register("spirit_shrine", new SpiritShrineFeature(DefaultFeatureConfig.CODEC));

    public static final ConfiguredFeature<?, ?> CORRUPT_TREES = register("corrupt_tree", TREE.configure((
            new TreeFeatureConfig.Builder(
                    new SimpleBlockStateProvider(Blocks.CORRUPT_LOG.getDefaultState()), new SimpleBlockStateProvider(Blocks.CORRUPT_LEAVES.getDefaultState()),
                    new BlobFoliagePlacer(UniformIntDistribution.of(2), UniformIntDistribution.of(0), 3), new StraightTrunkPlacer(4, 2, 0),
                    new TwoLayersFeatureSize(1, 0, 1))).ignoreVines().build()).decorate(Decorator.COUNT_EXTRA.configure(new CountExtraDecoratorConfig(10, 0.1F, 1))));

    public static final ConfiguredFeature<?, ?> MYSTICAL_TREES = register("mystical_tree", TREE.configure((
            new TreeFeatureConfig.Builder(new SimpleBlockStateProvider(Blocks.MYSTICAL_LOG.getDefaultState()),
                    new SimpleBlockStateProvider(Blocks.MYSTICAL_LEAVES.getDefaultState()),
                    new BlobFoliagePlacer(UniformIntDistribution.of(2), UniformIntDistribution.of(0), 3),
                    new StraightTrunkPlacer(4, 2, 0), new TwoLayersFeatureSize(1, 0, 1))).ignoreVines().build()).decorate(Decorator.COUNT_EXTRA.configure(new CountExtraDecoratorConfig(3, 0.3F, 1))));

    /**
     * MISC
     */
    public static final ConfiguredFeature<?, ?> CONFIGURED_RETURN_PORTAL = register("return_portal", RETURN_PORTAL.configure(DefaultFeatureConfig.INSTANCE));

    /**
     * PATCHY FEATURES
     **/
    public static final ConfiguredFeature<?, ?> MYSTICAL_FLOWERS = register("mystical_flowers", (ConfiguredFeature<? extends FeatureConfig, ? extends Feature<? extends FeatureConfig>>) Feature.RANDOM_PATCH.configure(Configs.MYSTICAL_DAISY_CONFIG).decorate(ConfiguredFeatures.Decorators.SQUARE_HEIGHTMAP_SPREAD_DOUBLE).repeat(3));
    public static final ConfiguredFeature<?, ?> MYSTICAL_STEMS = register("mystical_stems", (ConfiguredFeature<? extends FeatureConfig, ? extends Feature<? extends FeatureConfig>>) Feature.RANDOM_PATCH.configure(Configs.MYSTICAL_STEM_CONFIG).decorate(ConfiguredFeatures.Decorators.SQUARE_HEIGHTMAP_SPREAD_DOUBLE).repeat(3));
    public static final ConfiguredFeature<?, ?> CORRUPT_STEMS = register("corrupt_stems", (ConfiguredFeature<? extends FeatureConfig, ? extends Feature<? extends FeatureConfig>>) Feature.RANDOM_PATCH.configure(Configs.CORRUPT_STEM_CONFIG).decorate(ConfiguredFeatures.Decorators.SQUARE_HEIGHTMAP_SPREAD_DOUBLE).repeat(3));
    public static final ConfiguredFeature<?, ?> WILTED_FLOWERS = register("wilted_flowers", (ConfiguredFeature<? extends FeatureConfig, ? extends Feature<? extends FeatureConfig>>) Feature.RANDOM_PATCH.configure(Configs.WILTED_FLOWER_CONFIG).decorate(ConfiguredFeatures.Decorators.SQUARE_HEIGHTMAP_SPREAD_DOUBLE).repeat(3));

    public static final ConfiguredFeature<?, ?> CORRUPT_FALLEN_LEAVES = register("corrupt_fallen_leaves", (ConfiguredFeature<? extends FeatureConfig, ? extends Feature<? extends FeatureConfig>>) Feature.RANDOM_PATCH.configure(Configs.CORRUPT_FALLEN_LEAVES).decorate(ConfiguredFeatures.Decorators.SQUARE_HEIGHTMAP_SPREAD_DOUBLE).repeat(3));
    public static final ConfiguredFeature<?, ?> MYSTICAL_FALLEN_LEAVES = register("mystical_fallen_leaves", (ConfiguredFeature<? extends FeatureConfig, ? extends Feature<? extends FeatureConfig>>) Feature.RANDOM_PATCH.configure(Configs.MYSTICAL_FALLEN_LEAVES).decorate(ConfiguredFeatures.Decorators.SQUARE_HEIGHTMAP_SPREAD_DOUBLE).repeat(3));

    /**
     * SPIRIT SHRINE
     */
    public static final ConfiguredFeature<?, ?> CONFIGURED_SPIRIT_SHRINE = register("spirit_shrine", SPIRIT_SHRINE.configure(FeatureConfig.DEFAULT).applyChance(128));

    private static LootFunctionType register(String id, JsonSerializer<? extends LootFunction> jsonSerializer) {
        return Registry.register(Registry.LOOT_FUNCTION_TYPE, Lint.id(id), new LootFunctionType(jsonSerializer));
    }

    private static <C extends FeatureConfig, F extends Feature<C>> F register(String name, F feature) {
        return Registry.register(Registry.FEATURE, Lint.id(name), feature);
    }

    private static <FC extends FeatureConfig> ConfiguredFeature<?, ?> register(String id, ConfiguredFeature<FC, ?> configuredFeature) {
        return Registry.register(BuiltinRegistries.CONFIGURED_FEATURE, Lint.id(id), configuredFeature);
    }

    public static final class Configs {
        public static final RandomPatchFeatureConfig MYSTICAL_DAISY_CONFIG;
        public static final RandomPatchFeatureConfig MYSTICAL_STEM_CONFIG;
        public static final RandomPatchFeatureConfig CORRUPT_STEM_CONFIG;
        public static final RandomPatchFeatureConfig WILTED_FLOWER_CONFIG;

        public static final RandomPatchFeatureConfig CORRUPT_FALLEN_LEAVES;
        public static final RandomPatchFeatureConfig MYSTICAL_FALLEN_LEAVES;

        static {
            MYSTICAL_DAISY_CONFIG = (new RandomPatchFeatureConfig.Builder(new SimpleBlockStateProvider(Blocks.MYSTICAL_DAISY.getDefaultState()), SimpleBlockPlacer.INSTANCE)).tries(16).build();
            MYSTICAL_STEM_CONFIG = (new RandomPatchFeatureConfig.Builder(new SimpleBlockStateProvider(Blocks.MYSTICAL_STEM.getDefaultState()), SimpleBlockPlacer.INSTANCE)).tries(16).build();
            CORRUPT_STEM_CONFIG = (new RandomPatchFeatureConfig.Builder(new SimpleBlockStateProvider(Blocks.CORRUPT_STEM.getDefaultState()), SimpleBlockPlacer.INSTANCE)).tries(16).build();
            WILTED_FLOWER_CONFIG = (new RandomPatchFeatureConfig.Builder(new SimpleBlockStateProvider(Blocks.WILTED_FLOWER.getDefaultState()), SimpleBlockPlacer.INSTANCE)).tries(16).build();

            CORRUPT_FALLEN_LEAVES = (new RandomPatchFeatureConfig.Builder(new SimpleBlockStateProvider(Blocks.CORRUPT_FALLEN_LEAVES.getDefaultState()), SimpleBlockPlacer.INSTANCE)).tries(6).build();
            MYSTICAL_FALLEN_LEAVES = (new RandomPatchFeatureConfig.Builder(new SimpleBlockStateProvider(Blocks.MYSTICAL_FALLEN_LEAVES.getDefaultState()), SimpleBlockPlacer.INSTANCE)).tries(6).build();
        }
    }
}
