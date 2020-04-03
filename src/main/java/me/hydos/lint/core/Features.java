package me.hydos.lint.core;

import me.hydos.lint.dimensions.haykam.features.CommonMysticalTreeFeature;
import net.minecraft.state.property.Properties;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.gen.GenerationStep;
import net.minecraft.world.gen.decorator.CountExtraChanceDecoratorConfig;
import net.minecraft.world.gen.decorator.Decorator;
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
    }
}
