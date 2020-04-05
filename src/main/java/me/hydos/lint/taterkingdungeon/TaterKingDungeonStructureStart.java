package me.hydos.lint.taterkingdungeon;

import net.minecraft.structure.StructureManager;
import net.minecraft.structure.StructureStart;
import net.minecraft.util.math.BlockBox;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.gen.chunk.ChunkGenerator;
import net.minecraft.world.gen.feature.StructureFeature;

public class TaterKingDungeonStructureStart extends StructureStart {

    public TaterKingDungeonStructureStart(StructureFeature<?> feature, int chunkX, int chunkZ, BlockBox box, int references, long l) {
        super(feature, chunkX, chunkZ, box, references, l);
    }

    @Override
    public void initialize(ChunkGenerator<?> chunkGenerator, StructureManager structureManager, int x, int z, Biome biome) {

    }
}
