package me.hydos.lint.core;

import me.hydos.lint.dimensions.haykam.features.CommonMysticalTreeFeature;
import net.minecraft.state.property.Properties;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.gen.GenerationStep;
import net.minecraft.world.gen.feature.BranchedTreeFeatureConfig;
import net.minecraft.world.gen.feature.Feature;
import net.minecraft.world.gen.foliage.BlobFoliagePlacer;
import net.minecraft.world.gen.stateprovider.SimpleBlockStateProvider;
import net.minecraft.world.gen.stateprovider.WeightedBlockStateProvider;

import java.util.Random;

public interface Features {

    Random r = new Random();

    Feature<BranchedTreeFeatureConfig> MYSTICAL_TREE = Registry.register(
            Registry.FEATURE,
            new Identifier("lint", "mystical_tree"),
            new CommonMysticalTreeFeature(BranchedTreeFeatureConfig::deserialize)
    );

    static void onInitialize(){
        WeightedBlockStateProvider logProvider = new WeightedBlockStateProvider();
        logProvider.addState(
                Blocks.MYSTICAL_LOG.getDefaultState(),
                10
        );

        Registry.BIOME.forEach(biome -> biome.addFeature(
                GenerationStep.Feature.RAW_GENERATION,
                MYSTICAL_TREE.configure(new BranchedTreeFeatureConfig.Builder(
                        logProvider,
                        new SimpleBlockStateProvider(Blocks.MYSTICAL_LEAVES.getDefaultState().with(Properties.PERSISTENT, true)),
                        new BlobFoliagePlacer(MathHelper.nextInt(r, 2, 3), MathHelper.nextInt(r, 1, 3)))
                        .baseHeight(MathHelper.nextInt(r, 5, 7))
                        .heightRandA(MathHelper.nextInt(r, 3, 6))
                        .foliageHeight(MathHelper.nextInt(r, 3, 5))
                        .noVines()
                        .build())
        ));

    }
}
