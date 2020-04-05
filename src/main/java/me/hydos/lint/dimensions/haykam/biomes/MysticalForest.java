package me.hydos.lint.dimensions.haykam.biomes;

import me.hydos.lint.core.Blocks;
import me.hydos.lint.core.Entities;
import net.minecraft.block.BlockState;
import net.minecraft.entity.EntityCategory;
import net.minecraft.state.property.Properties;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.DefaultBiomeFeatures;
import net.minecraft.world.gen.GenerationStep;
import net.minecraft.world.gen.decorator.ChanceDecoratorConfig;
import net.minecraft.world.gen.decorator.CountDecoratorConfig;
import net.minecraft.world.gen.decorator.CountExtraChanceDecoratorConfig;
import net.minecraft.world.gen.decorator.Decorator;
import net.minecraft.world.gen.feature.*;
import net.minecraft.world.gen.foliage.BlobFoliagePlacer;
import net.minecraft.world.gen.placer.SimpleBlockPlacer;
import net.minecraft.world.gen.stateprovider.SimpleBlockStateProvider;
import net.minecraft.world.gen.stateprovider.WeightedBlockStateProvider;
import net.minecraft.world.gen.surfacebuilder.SurfaceBuilder;
import net.minecraft.world.gen.surfacebuilder.TernarySurfaceConfig;

import java.util.Random;

import static me.hydos.lint.core.Blocks.*;
import static me.hydos.lint.core.Features.*;
import static me.hydos.lint.taterkingdungeon.TutorialJigsaws.FEATURE;
import static net.minecraft.world.gen.surfacebuilder.SurfaceBuilder.GRAVEL;

public class MysticalForest extends Biome implements IBiomeHasLex{

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
        Random r = new Random();

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
                        new BlobFoliagePlacer(MathHelper.nextInt(r, 2, 3), MathHelper.nextInt(r, 1, 3)))
                        .baseHeight(MathHelper.nextInt(r, 5, 7))
                        .heightRandA(MathHelper.nextInt(r, 3, 6))
                        .foliageHeight(MathHelper.nextInt(r, 3, 5))
                        .noVines()
                        .build()).createDecoratedFeature(Decorator.COUNT_EXTRA_HEIGHTMAP.configure(new CountExtraChanceDecoratorConfig(4, 0.25f, 1)))
        );

        this.addSpawn(EntityCategory.CREATURE, new Biome.SpawnEntry(Entities.LIL_TATER, 12, 4, 4));

        this.addFeature(GenerationStep.Feature.RAW_GENERATION, FEATURE.configure(new DefaultFeatureConfig()).createDecoratedFeature(Decorator.CHANCE_HEIGHTMAP_DOUBLE.configure(new ChanceDecoratorConfig(40))));
        this.addStructureFeature(FEATURE.configure(new DefaultFeatureConfig()));

        this.addFeature(GenerationStep.Feature.VEGETAL_DECORATION, Feature.RANDOM_PATCH.configure(new RandomPatchFeatureConfig.Builder(
                new SimpleBlockStateProvider(
                        MYSTICAL_GRASS.getDefaultState()
                ),
                new SimpleBlockPlacer()).tries(32).build()
        ).createDecoratedFeature(Decorator.COUNT_HEIGHTMAP_DOUBLE.configure(new CountDecoratorConfig(2))));

//        this.addFeature(GenerationStep.Feature.TOP_LAYER_MODIFICATION, EPIC_VALO_CLOUD_FEATURE_FEATURE.configure(FeatureConfig.DEFAULT).createDecoratedFeature(Decorator.NOPE.configure(new NopeDecoratorConfig())));

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
