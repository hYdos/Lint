package me.hydos.lint.structurefeatures;

import net.minecraft.structure.StructureManager;
import net.minecraft.structure.StructureStart;
import net.minecraft.util.BlockRotation;
import net.minecraft.util.math.BlockBox;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.gen.chunk.ChunkGenerator;
import net.minecraft.world.gen.feature.DefaultFeatureConfig;
import net.minecraft.world.gen.feature.StructureFeature;

import static me.hydos.lint.core.Features.TATER_VILLAGE;

public class TaterVillageStructureStart extends StructureStart {

    public TaterVillageStructureStart(StructureFeature<?> feature, int chunkX, int chunkZ, BlockBox box, int references, long l) {
        super(feature, chunkX, chunkZ, box, references, l);
    }

    @Override
    public void initialize(ChunkGenerator<?> chunkGenerator, StructureManager structureManager, int chunkX, int chunkZ, Biome biome) {
        DefaultFeatureConfig defaultFeatureConfig = chunkGenerator.getStructureConfig(biome, TATER_VILLAGE);
        int x = chunkX * 16;
        int z = chunkZ * 16;
        BlockPos startingPos = new BlockPos(x, 0, z);
        BlockRotation rotation = BlockRotation.values()[this.random.nextInt(BlockRotation.values().length)];
        MyGenerator.addParts(structureManager, startingPos, rotation, this.children, this.random, defaultFeatureConfig);
        this.setBoundingBoxFromChildren();
    }
}
