package io.github.hydos.lint.world.feature;

import me.hydos.lint.core.Blocks;
import me.hydos.lint.core.Lint;
import net.minecraft.util.registry.BuiltinRegistries;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.gen.UniformIntDistribution;
import net.minecraft.world.gen.decorator.CountExtraDecoratorConfig;
import net.minecraft.world.gen.decorator.Decorator;
import net.minecraft.world.gen.feature.ConfiguredFeature;
import net.minecraft.world.gen.feature.Feature;
import net.minecraft.world.gen.feature.FeatureConfig;
import net.minecraft.world.gen.feature.TreeFeatureConfig;
import net.minecraft.world.gen.feature.size.TwoLayersFeatureSize;
import net.minecraft.world.gen.foliage.BlobFoliagePlacer;
import net.minecraft.world.gen.stateprovider.SimpleBlockStateProvider;
import net.minecraft.world.gen.trunk.StraightTrunkPlacer;

public class Features {
    public static final Feature<TreeFeatureConfig> TREE = register("tree", new BetterTreeFeature(TreeFeatureConfig.CODEC));


    public static final ConfiguredFeature<?, ?> CORRUPT = register("corrupt_tree", TREE.configure((
            new TreeFeatureConfig.Builder(
                    new SimpleBlockStateProvider(Blocks.CORRUPT_LOG.getDefaultState()), new SimpleBlockStateProvider(Blocks.CORRUPT_LEAVES.getDefaultState()),
                    new BlobFoliagePlacer(UniformIntDistribution.of(2), UniformIntDistribution.of(0), 3), new StraightTrunkPlacer(4, 2, 0),
                    new TwoLayersFeatureSize(1, 0, 1))).ignoreVines().build()).decorate(Decorator.COUNT_EXTRA.configure(new CountExtraDecoratorConfig(10, 0.1F, 1))));

    public static final ConfiguredFeature<?, ?> MYSTICAL = register("mystical_tree", TREE.configure((
            new TreeFeatureConfig.Builder(new SimpleBlockStateProvider(Blocks.MYSTICAL_LOG.getDefaultState()),
                    new SimpleBlockStateProvider(Blocks.MYSTICAL_LEAVES.getDefaultState()),
                    new BlobFoliagePlacer(UniformIntDistribution.of(2), UniformIntDistribution.of(0), 3),
                    new StraightTrunkPlacer(4, 2, 0), new TwoLayersFeatureSize(1, 0, 1))).ignoreVines().build()).decorate(Decorator.COUNT_EXTRA.configure(new CountExtraDecoratorConfig(3, 0.3F, 1))));

    private static <C extends FeatureConfig, F extends Feature<C>> F register(String name, F feature) {
        return Registry.register(Registry.FEATURE, Lint.id(name), feature);
    }

    private static <FC extends FeatureConfig> ConfiguredFeature<?, ?> register(String id, ConfiguredFeature<FC, ?> configuredFeature) {
        return Registry.register(BuiltinRegistries.CONFIGURED_FEATURE, Lint.id(id), configuredFeature);
    }
}
