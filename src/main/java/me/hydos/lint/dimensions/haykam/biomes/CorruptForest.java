package me.hydos.lint.dimensions.haykam.biomes;

import static me.hydos.lint.core.Blocks.CORRUPT_GRASS;
import static me.hydos.lint.core.Blocks.CORRUPT_LEAVES;
import static me.hydos.lint.core.Blocks.CORRUPT_LOG;
import static me.hydos.lint.core.Blocks.CORRUPT_SAND;
import static me.hydos.lint.core.Blocks.CORRUPT_STEM;
import static me.hydos.lint.core.Blocks.MYSTICAL_GRASS;
import static me.hydos.lint.core.Blocks.RICH_DIRT;
import static me.hydos.lint.core.Blocks.WHITE_SAND;
import static me.hydos.lint.core.Blocks.WILTED_FLOWER;
import static me.hydos.lint.core.Features.CORRUPT_TREE;
import static me.hydos.lint.core.Features.EPIC_VALO_CLOUD_FEATURE_FEATURE;
import static me.hydos.lint.taterkingdungeon.TutorialJigsaws.FEATURE;
import static net.minecraft.world.gen.surfacebuilder.SurfaceBuilder.GRAVEL;

import java.util.Random;

import me.hydos.lint.core.Blocks;
import me.hydos.lint.core.Entities;
import net.minecraft.block.BlockState;
import net.minecraft.entity.EntityCategory;
import net.minecraft.state.property.Properties;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.DefaultBiomeFeatures;
import net.minecraft.world.gen.GenerationStep;
import net.minecraft.world.gen.decorator.CountDecoratorConfig;
import net.minecraft.world.gen.decorator.CountExtraChanceDecoratorConfig;
import net.minecraft.world.gen.decorator.Decorator;
import net.minecraft.world.gen.decorator.NopeDecoratorConfig;
import net.minecraft.world.gen.feature.BranchedTreeFeatureConfig;
import net.minecraft.world.gen.feature.DefaultFeatureConfig;
import net.minecraft.world.gen.feature.Feature;
import net.minecraft.world.gen.feature.FeatureConfig;
import net.minecraft.world.gen.feature.RandomPatchFeatureConfig;
import net.minecraft.world.gen.foliage.BlobFoliagePlacer;
import net.minecraft.world.gen.placer.SimpleBlockPlacer;
import net.minecraft.world.gen.stateprovider.SimpleBlockStateProvider;
import net.minecraft.world.gen.stateprovider.WeightedBlockStateProvider;
import net.minecraft.world.gen.surfacebuilder.SurfaceBuilder;
import net.minecraft.world.gen.surfacebuilder.TernarySurfaceConfig;

public class CorruptForest extends Biome implements IBiomeHasLex {

	public static final TernarySurfaceConfig MYSTICAL_GRASS_CONFIG = new TernarySurfaceConfig(Blocks.LIVELY_GRASS.getDefaultState(), Blocks.RICH_DIRT.getDefaultState(), GRAVEL);

	public CorruptForest() {
		super(new Settings().configureSurfaceBuilder(SurfaceBuilder.DEFAULT, MYSTICAL_GRASS_CONFIG)
				.precipitation(Precipitation.RAIN)
				.category(Category.FOREST)
				.depth(0.24F)
				.scale(0.2F)
				.temperature(0.6F)
				.downfall(0.1F)
				.waterColor(0x631ee3)
				.waterFogColor(0x631ee3)
				.parent(null)

				);

		WeightedBlockStateProvider logProvider = new WeightedBlockStateProvider();
		logProvider.addState(
				CORRUPT_LOG.getDefaultState(),
				10
				);

		DefaultBiomeFeatures.addLandCarvers(this);
		DefaultBiomeFeatures.addDefaultLakes(this);
		DefaultBiomeFeatures.addMineables(this);
		DefaultBiomeFeatures.addDefaultOres(this);

		Random r = new Random();

		this.addFeature(
				GenerationStep.Feature.VEGETAL_DECORATION,
				CORRUPT_TREE.configure(new BranchedTreeFeatureConfig.Builder(
						logProvider,
						new SimpleBlockStateProvider(CORRUPT_LEAVES.getDefaultState().with(Properties.PERSISTENT, true)),
						new BlobFoliagePlacer(MathHelper.nextInt(r, 2, 3), MathHelper.nextInt(r, 1, 3)))
						.baseHeight(MathHelper.nextInt(r, 5, 7))
						.heightRandA(MathHelper.nextInt(r, 3, 6))
						.foliageHeight(MathHelper.nextInt(r, 3, 5))
						.noVines()
						.build()).createDecoratedFeature(Decorator.COUNT_EXTRA_HEIGHTMAP.configure(new CountExtraChanceDecoratorConfig(4, 0.25f, 1)))
				);

		this.addStructureFeature(FEATURE.configure(new DefaultFeatureConfig()));

		this.addSpawn(EntityCategory.CREATURE, new Biome.SpawnEntry(Entities.LIL_TATER, 12, 4, 4));

		this.addFeature(GenerationStep.Feature.VEGETAL_DECORATION, Feature.RANDOM_PATCH.configure(new RandomPatchFeatureConfig.Builder(
				new SimpleBlockStateProvider(
						MYSTICAL_GRASS.getDefaultState()
						),
				new SimpleBlockPlacer()).tries(32).build()
				).createDecoratedFeature(Decorator.COUNT_HEIGHTMAP_DOUBLE.configure(new CountDecoratorConfig(2))));

		this.addFeature(GenerationStep.Feature.VEGETAL_DECORATION, BiomeUtils.flower(WILTED_FLOWER.getDefaultState(), 8, 1));
		this.addFeature(GenerationStep.Feature.VEGETAL_DECORATION, BiomeUtils.flower(CORRUPT_STEM.getDefaultState(), 32, 4));

		this.addFeature(GenerationStep.Feature.TOP_LAYER_MODIFICATION, EPIC_VALO_CLOUD_FEATURE_FEATURE.configure(FeatureConfig.DEFAULT).createDecoratedFeature(Decorator.NOPE.configure(new NopeDecoratorConfig())));

		DefaultBiomeFeatures.addDefaultVegetation(this);
		DefaultBiomeFeatures.addSprings(this);
	}

	@Override
	public BlockState getGrass() {
		return CORRUPT_GRASS.getDefaultState();
	}

	@Override
	public BlockState getUnderDirt() {
		return RICH_DIRT.getDefaultState();
	}

	@Override
	public BlockState getSand() {
		return CORRUPT_SAND.getDefaultState();
	}

	@Override
	public BlockState getGravel() {
		return WHITE_SAND.getDefaultState();
	}
}
