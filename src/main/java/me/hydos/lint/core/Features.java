package me.hydos.lint.core;

import me.hydos.lint.world.dimension.features.CommonCorruptTreeFeature;
import me.hydos.lint.world.dimension.features.CommonMysticalTreeFeature;
import me.hydos.lint.world.dimension.features.EpicValoCloudFeature;
import me.hydos.lint.world.dimension.features.PortalFeature;
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

	Feature<DefaultFeatureConfig> EPIC_VALO_CLOUD_FEATURE_FEATURE = Registry.register(
			Registry.FEATURE,
			new Identifier("lint", "epic_valo_cloud_feature"),
			new EpicValoCloudFeature()
	);

	Feature<DefaultFeatureConfig> PORTAL_FEATURE = Registry.register(
			Registry.FEATURE,
			new Identifier("lint", "portal"),
			new PortalFeature()
	);
}
