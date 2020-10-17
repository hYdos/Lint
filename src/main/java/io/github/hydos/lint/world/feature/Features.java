package io.github.hydos.lint.world.feature;

import io.github.hydos.lint.Lint;
import io.github.hydos.lint.block.Blocks;
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
    /**         TREES          **/
    public static final Feature<TreeFeatureConfig> TREE = register("tree", new BetterTreeFeature(TreeFeatureConfig.CODEC));

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

    /**      PATCHY FEATURES      **/

    public static final ConfiguredFeature<?, ?> MYSTICAL_FLOWERS = register("mystical_flowers", (ConfiguredFeature)Feature.RANDOM_PATCH.configure(Configs.GRASS_CONFIG).decorate(ConfiguredFeatures.Decorators.SQUARE_HEIGHTMAP_SPREAD_DOUBLE).repeat(3));


    private static <C extends FeatureConfig, F extends Feature<C>> F register(String name, F feature) {
        return Registry.register(Registry.FEATURE, Lint.id(name), feature);
    }

    private static <FC extends FeatureConfig> ConfiguredFeature<?, ?> register(String id, ConfiguredFeature<FC, ?> configuredFeature) {
        return Registry.register(BuiltinRegistries.CONFIGURED_FEATURE, Lint.id(id), configuredFeature);
    }

    public static final class Configs {
        public static final RandomPatchFeatureConfig GRASS_CONFIG;

        static {
            GRASS_CONFIG = (new RandomPatchFeatureConfig.Builder(new SimpleBlockStateProvider(Blocks.MYSTICAL_DAISY.getDefaultState()), SimpleBlockPlacer.INSTANCE)).tries(16).build();
        }
    }
}
