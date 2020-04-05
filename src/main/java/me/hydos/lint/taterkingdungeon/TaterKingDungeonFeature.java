package me.hydos.lint.taterkingdungeon;

import com.mojang.datafixers.Dynamic;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.source.BiomeAccess;
import net.minecraft.world.gen.chunk.ChunkGenerator;
import net.minecraft.world.gen.feature.DefaultFeatureConfig;
import net.minecraft.world.gen.feature.StructureFeature;

import java.util.Random;
import java.util.function.Function;

public class TaterKingDungeonFeature extends StructureFeature<DefaultFeatureConfig> {

    public TaterKingDungeonFeature(Function<Dynamic<?>, ? extends DefaultFeatureConfig> configFactory) {
        super(configFactory);
    }

    @Override
    public boolean shouldStartAt(BiomeAccess biomeAccess, ChunkGenerator<?> chunkGenerator, Random random, int chunkZ, int i, Biome biome) {
        return true;
    }

    @Override
    public StructureStartFactory getStructureStartFactory() {
        return TaterKingDungeonStructureStart::new;
    }

    @Override
    public String getName() {
        return "King Tater Dungeon";
    }

    @Override
    public int getRadius() {
        return 2;
    }
}
