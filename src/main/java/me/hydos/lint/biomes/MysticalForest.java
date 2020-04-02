package me.hydos.lint.biomes;

import me.hydos.lint.Lint;
import me.hydos.lint.registers.BlockRegister;
import net.minecraft.entity.EntityCategory;
import net.minecraft.entity.EntityType;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.DefaultBiomeFeatures;
import net.minecraft.world.gen.feature.Feature;
import net.minecraft.world.gen.feature.FeatureConfig;
import net.minecraft.world.gen.feature.MineshaftFeature;
import net.minecraft.world.gen.feature.MineshaftFeatureConfig;
import net.minecraft.world.gen.surfacebuilder.SurfaceBuilder;
import net.minecraft.world.gen.surfacebuilder.TernarySurfaceConfig;

import static net.minecraft.world.gen.surfacebuilder.SurfaceBuilder.*;

public class MysticalForest extends Biome {

    public static final TernarySurfaceConfig MYSTICAL_GRASS_CONFIG = new TernarySurfaceConfig(BlockRegister.getBlock("lively_grass").getDefaultState(), BlockRegister.getBlock("rich_dirt").getDefaultState(), GRAVEL);

    public MysticalForest(){
        super(new Biome.Settings().configureSurfaceBuilder(SurfaceBuilder.DEFAULT, MYSTICAL_GRASS_CONFIG).precipitation(Biome.Precipitation.RAIN).category(Biome.Category.PLAINS).depth(0.24F).scale(0.2F).temperature(0.6F).downfall(0.7F).waterColor(4159204).waterFogColor(329011).parent(null));

        this.addStructureFeature(Feature.MINESHAFT.configure(new MineshaftFeatureConfig(0.004D, MineshaftFeature.Type.NORMAL)));
        this.addStructureFeature(Feature.STRONGHOLD.configure(FeatureConfig.DEFAULT));
        DefaultBiomeFeatures.addLandCarvers(this);
        DefaultBiomeFeatures.addDefaultStructures(this);
        DefaultBiomeFeatures.addDefaultLakes(this);
        DefaultBiomeFeatures.addDungeons(this);
        DefaultBiomeFeatures.addExtraMountainTrees(this);
        DefaultBiomeFeatures.addDefaultFlowers(this);
        DefaultBiomeFeatures.addDefaultGrass(this);
        DefaultBiomeFeatures.addMineables(this);
        DefaultBiomeFeatures.addDefaultOres(this);
        DefaultBiomeFeatures.addDefaultDisks(this);
        DefaultBiomeFeatures.addDefaultVegetation(this);
        DefaultBiomeFeatures.addSprings(this);
        DefaultBiomeFeatures.addFrozenTopLayer(this);
        this.addSpawn(EntityCategory.CREATURE, new Biome.SpawnEntry(EntityType.SHEEP, 12, 4, 4));
        this.addSpawn(EntityCategory.CREATURE, new Biome.SpawnEntry(Lint.LIL_TATER, 10, 2, 8));

    }

}
