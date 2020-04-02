package me.hydos.lint.core;

import me.hydos.lint.dimensions.haykam.features.CommonMysticalTreeFeature;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.gen.GenerationStep;
import net.minecraft.world.gen.feature.BranchedTreeFeatureConfig;
import net.minecraft.world.gen.feature.Feature;
import net.minecraft.world.gen.foliage.BlobFoliagePlacer;
import net.minecraft.world.gen.stateprovider.SimpleBlockStateProvider;
import net.minecraft.world.gen.stateprovider.WeightedBlockStateProvider;

public interface Features {

    Feature<BranchedTreeFeatureConfig> MYSTICAL_TREE = Registry.register(
            Registry.FEATURE,
            new Identifier("lint", "mystical_tree"),
            new CommonMysticalTreeFeature(BranchedTreeFeatureConfig::deserialize)
    );

    static void onInitialize(){
        WeightedBlockStateProvider logProvider = new WeightedBlockStateProvider();
        logProvider.addState(
                Blocks.RICH_DIRT.getDefaultState(),
                10
        );

        Registry.BIOME.forEach(biome -> biome.addFeature(
                GenerationStep.Feature.RAW_GENERATION,
                MYSTICAL_TREE.configure(new BranchedTreeFeatureConfig.Builder(
                        logProvider,
                        new SimpleBlockStateProvider(Blocks.RICH_DIRT.getDefaultState()),
                        new BlobFoliagePlacer(2, 0))
                        .baseHeight(6)
                        .heightRandA(2)
                        .foliageHeight(3)
                        .noVines()
                        .build())
        ));

    }

}
