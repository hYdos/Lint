package me.hydos.lint.taterkingdungeon;

import com.mojang.datafixers.Dynamic;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.source.BiomeAccess;
import net.minecraft.world.gen.chunk.ChunkGenerator;
import net.minecraft.world.gen.feature.DefaultFeatureConfig;
import net.minecraft.world.gen.feature.StructureFeature;

import java.util.Random;
import java.util.function.Function;

public class KingTaterDungeonFeature extends StructureFeature<DefaultFeatureConfig> {

    public KingTaterDungeonFeature(Function<Dynamic<?>, ? extends DefaultFeatureConfig> config) {
        super(config);
    }

    @Override
    public boolean shouldStartAt(BiomeAccess biomeAccess, ChunkGenerator<?> chunkGenerator, Random random, int i, int j, Biome biome) {
        return true;
    }

    @Override
    public StructureStartFactory getStructureStartFactory() {
        return KingTaterDungeonStructureStart::new;
    }

    // used for structure feature location
    @Override
    public String getName() {
        return "TutorialJigsaw";
    }

    // radius seems to be the max size of a piece inside a chunk
    // I assume it is used for random rotation and placement
    @Override
    public int getRadius() {
        return 17;
    }
}
