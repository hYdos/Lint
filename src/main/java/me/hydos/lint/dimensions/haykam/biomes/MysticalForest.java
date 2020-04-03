package me.hydos.lint.dimensions.haykam.biomes;

import me.hydos.lint.core.Blocks;
import me.hydos.lint.core.Entities;
import me.hydos.lint.core.Lint;
import net.minecraft.entity.EntityCategory;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.DefaultBiomeFeatures;
import net.minecraft.world.gen.feature.*;
import net.minecraft.world.gen.surfacebuilder.SurfaceBuilder;
import net.minecraft.world.gen.surfacebuilder.TernarySurfaceConfig;

import static net.minecraft.world.gen.surfacebuilder.SurfaceBuilder.*;

public class MysticalForest extends Biome {

    public static final TernarySurfaceConfig MYSTICAL_GRASS_CONFIG = new TernarySurfaceConfig(Blocks.LIVELY_GRASS.getDefaultState(), Blocks.RICH_DIRT.getDefaultState(), GRAVEL);

    public MysticalForest(){
        super(new Biome.Settings().configureSurfaceBuilder(SurfaceBuilder.DEFAULT, MYSTICAL_GRASS_CONFIG).precipitation(Precipitation.RAIN).category(Category.FOREST).depth(0.24F).scale(0.2F).temperature(0.6F).downfall(0.7F).waterColor(4159204).waterFogColor(329011).parent(null));

        DefaultBiomeFeatures.addLandCarvers(this);
        DefaultBiomeFeatures.addDefaultLakes(this);
        DefaultBiomeFeatures.addMineables(this);
        DefaultBiomeFeatures.addDefaultOres(this);
        DefaultBiomeFeatures.addDefaultDisks(this);
        DefaultBiomeFeatures.addDefaultVegetation(this);
        DefaultBiomeFeatures.addSprings(this);
        this.addSpawn(EntityCategory.CREATURE, new Biome.SpawnEntry(Entities.LIL_TATER, 1, 1, 1));

    }



}
