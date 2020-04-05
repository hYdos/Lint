package me.hydos.lint.core;

import me.hydos.lint.taterkingdungeon.KingTaterDungeonFeatures;
import me.hydos.lint.dimensions.haykam.features.CommonCorruptTreeFeature;
import me.hydos.lint.dimensions.haykam.features.CommonMysticalTreeFeature;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.gen.feature.*;

public interface Features {

    Feature<BranchedTreeFeatureConfig> MYSTICAL_TREE = Registry.register(
            Registry.FEATURE,
            new Identifier("lint", "mystical_tree"),
            new CommonMysticalTreeFeature(BranchedTreeFeatureConfig::deserialize)
    );

    Feature<BranchedTreeFeatureConfig> CORRUPT_TREE = Registry.register(
            Registry.FEATURE,
            new Identifier("lint", "corrupt_tree"),
            new CommonCorruptTreeFeature(BranchedTreeFeatureConfig::deserialize)
    );

    static void onInitialize(){
        KingTaterDungeonFeatures.onInitialize();
    }

}
