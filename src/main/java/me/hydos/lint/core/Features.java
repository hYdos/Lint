package me.hydos.lint.core;

import me.hydos.lint.dimensions.haykam.features.CommonMysticalTreeFeature;
import me.hydos.lint.dimensions.haykam.features.EpicValoCloudFeature;
import me.hydos.lint.structurefeatures.TaterVillageFeature;
import net.minecraft.state.property.Properties;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.gen.GenerationStep;
import net.minecraft.world.gen.decorator.CountExtraChanceDecoratorConfig;
import net.minecraft.world.gen.decorator.Decorator;
import net.minecraft.world.gen.feature.*;
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

    StructureFeature<DefaultFeatureConfig> TATER_VILLAGE = (StructureFeature)register("igloo", new TaterVillageFeature(DefaultFeatureConfig::deserialize));


    Feature<DefaultFeatureConfig> EPIC_VALO_CLOUD_FEATURE_FEATURE = Registry.register(
            Registry.FEATURE,
            new Identifier("lint", "valo_cool_epic_e"),
            new EpicValoCloudFeature()
    );

    static void onInitialize(){
    }

    static <C extends FeatureConfig, F extends Feature<C>> F register(String name, F feature) {
        return (F) Registry.register(Registry.FEATURE, name, feature);
    }
}
