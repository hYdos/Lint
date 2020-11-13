package io.github.hydos.lint.world.feature;

import java.util.Random;

import com.mojang.serialization.Codec;

import io.github.hydos.lint.Lint;
import net.minecraft.structure.Structure;
import net.minecraft.structure.StructureManager;
import net.minecraft.structure.StructurePlacementData;
import net.minecraft.structure.processor.BlockIgnoreStructureProcessor;
import net.minecraft.util.BlockRotation;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockBox;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.ChunkPos;
import net.minecraft.world.Heightmap;
import net.minecraft.world.StructureWorldAccess;
import net.minecraft.world.gen.chunk.ChunkGenerator;
import net.minecraft.world.gen.feature.DefaultFeatureConfig;
import net.minecraft.world.gen.feature.Feature;

public class SpiritShrineFeature extends Feature<DefaultFeatureConfig> {
    private static final Identifier SPIRIT_SHRINE = Lint.id("spirit_shrine");

    public SpiritShrineFeature(Codec<DefaultFeatureConfig> codec) {
        super(codec);
    }

    @Override
    public boolean generate(StructureWorldAccess world, ChunkGenerator chunkGenerator, Random random, BlockPos startPos, DefaultFeatureConfig config) {
        // Get structure
        StructureManager structureManager = world.toServerWorld().getServer().getStructureManager();
        Structure structure = structureManager.getStructureOrBlank(SPIRIT_SHRINE);
        
        BlockRotation rotation = BlockRotation.random(random);
        ChunkPos chunkPos = new ChunkPos(startPos);
        BlockBox box = new BlockBox(chunkPos.getStartX() - 16, 0, chunkPos.getStartZ() - 16, chunkPos.getEndX(), 256, chunkPos.getEndZ());

        StructurePlacementData placementData = new StructurePlacementData()
            .setRotation(rotation)
            .setBoundingBox(box)
            .setRandom(random)
            .addProcessor(BlockIgnoreStructureProcessor.IGNORE_AIR_AND_STRUCTURE_BLOCKS);

        BlockPos pos = new BlockPos(startPos.getX(), world.getTopY(Heightmap.Type.WORLD_SURFACE_WG, startPos.getX(), startPos.getZ()), startPos.getZ());
        structure.place(world, pos, pos, placementData, random, 4);
        return true;
    }
}