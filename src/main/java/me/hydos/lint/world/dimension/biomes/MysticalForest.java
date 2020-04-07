package me.hydos.lint.world.dimension.biomes;

import static me.hydos.lint.core.Blocks.LIVELY_GRASS;
import static me.hydos.lint.core.Blocks.MYSTICAL_GRASS;
import static me.hydos.lint.core.Blocks.MYSTICAL_LOG;
import static me.hydos.lint.core.Blocks.MYSTICAL_SAND;
import static me.hydos.lint.core.Blocks.MYSTICAL_STEM;
import static me.hydos.lint.core.Blocks.RICH_DIRT;
import static me.hydos.lint.core.Blocks.WHITE_SAND;
import static me.hydos.lint.core.Blocks.YELLOW_DAISY;
import static me.hydos.lint.core.Features.EPIC_VALO_CLOUD_FEATURE_FEATURE;
import static me.hydos.lint.core.Features.MYSTICAL_TREE;
import static me.hydos.lint.core.Features.PORTAL_FEATURE;
import static me.hydos.lint.world.dungeon.TutorialJigsaws.FEATURE;
import static net.minecraft.world.gen.surfacebuilder.SurfaceBuilder.GRAVEL;

import me.hydos.lint.core.Blocks;
import me.hydos.lint.core.Entities;
import net.minecraft.block.BlockState;
import net.minecraft.entity.EntityCategory;
import net.minecraft.state.property.Properties;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.DefaultBiomeFeatures;
import net.minecraft.world.gen.GenerationStep;
import net.minecraft.world.gen.decorator.ChanceDecoratorConfig;
import net.minecraft.world.gen.decorator.CountExtraChanceDecoratorConfig;
import net.minecraft.world.gen.decorator.Decorator;
import net.minecraft.world.gen.decorator.NopeDecoratorConfig;
import net.minecraft.world.gen.feature.BranchedTreeFeatureConfig;
import net.minecraft.world.gen.feature.DefaultFeatureConfig;
import net.minecraft.world.gen.feature.FeatureConfig;
import net.minecraft.world.gen.foliage.BlobFoliagePlacer;
import net.minecraft.world.gen.stateprovider.SimpleBlockStateProvider;
import net.minecraft.world.gen.stateprovider.WeightedBlockStateProvider;
import net.minecraft.world.gen.surfacebuilder.SurfaceBuilder;
import net.minecraft.world.gen.surfacebuilder.TernarySurfaceConfig;

public class MysticalForest extends Biome implements IBiomeHasLex {

	public static final TernarySurfaceConfig MYSTICAL_GRASS_CONFIG = new TernarySurfaceConfig(Blocks.LIVELY_GRASS.getDefaultState(), Blocks.RICH_DIRT.getDefaultState(), GRAVEL);

	public MysticalForest() {
		super(new Biome.Settings()
				.configureSurfaceBuilder(SurfaceBuilder.DEFAULT, MYSTICAL_GRASS_CONFIG)
				.precipitation(Precipitation.RAIN).category(Category.FOREST)
				.depth(0.24F)
				.scale(100F)
				.temperature(0.6F)
				.downfall(0.7F)
				.waterColor(4159204)
				.waterFogColor(329011)
				.parent(null));

		WeightedBlockStateProvider logProvider = new WeightedBlockStateProvider();
		logProvider.addState(
				MYSTICAL_LOG.getDefaultState(),
				10
				);

		DefaultBiomeFeatures.addLandCarvers(this);
		DefaultBiomeFeatures.addDefaultLakes(this);
		DefaultBiomeFeatures.addMineables(this);
		DefaultBiomeFeatures.addDefaultOres(this);

		this.addFeature(
				GenerationStep.Feature.VEGETAL_DECORATION,
				MYSTICAL_TREE.configure(new BranchedTreeFeatureConfig.Builder(
						logProvider,
						new SimpleBlockStateProvider(Blocks.MYSTICAL_LEAVES.getDefaultState().with(Properties.PERSISTENT, true)),
						new BlobFoliagePlacer(2, 2))
						.baseHeight(6)
						.heightRandA(3)
						.foliageHeight(4)
						.noVines()
						.build()).createDecoratedFeature(Decorator.COUNT_EXTRA_HEIGHTMAP.configure(new CountExtraChanceDecoratorConfig(4, 0.25f, 1)))
				);

		this.addSpawn(EntityCategory.CREATURE, new Biome.SpawnEntry(Entities.LIL_TATER, 12, 4, 4));

		this.addStructureFeature(FEATURE.configure(new DefaultFeatureConfig()));

		this.addFeature(GenerationStep.Feature.VEGETAL_DECORATION, BiomeUtils.randomPatch(MYSTICAL_GRASS.getDefaultState(), 2));
		this.addFeature(GenerationStep.Feature.VEGETAL_DECORATION, BiomeUtils.flower(YELLOW_DAISY.getDefaultState(), 64, 2));
		this.addFeature(GenerationStep.Feature.VEGETAL_DECORATION, BiomeUtils.flower(MYSTICAL_STEM.getDefaultState(), 64, 3));

		this.addFeature(GenerationStep.Feature.SURFACE_STRUCTURES, PORTAL_FEATURE
				.configure(FeatureConfig.DEFAULT)
				.createDecoratedFeature(Decorator.CHANCE_HEIGHTMAP.configure(new ChanceDecoratorConfig(40))));

		this.addFeature(GenerationStep.Feature.TOP_LAYER_MODIFICATION, EPIC_VALO_CLOUD_FEATURE_FEATURE
				.configure(FeatureConfig.DEFAULT)
				.createDecoratedFeature(Decorator.NOPE.configure(new NopeDecoratorConfig())));

		DefaultBiomeFeatures.addDefaultVegetation(this);
		DefaultBiomeFeatures.addSprings(this);
	}

	@Override
	public BlockState getGrass() {
		return LIVELY_GRASS.getDefaultState();
	}

	@Override
	public BlockState getUnderDirt() {
		return RICH_DIRT.getDefaultState();
	}

	@Override
	public BlockState getSand() {
		return MYSTICAL_SAND.getDefaultState();
	}

	@Override
	public BlockState getGravel() {
		return WHITE_SAND.getDefaultState();
	}
}
