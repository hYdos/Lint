package me.hydos.lint.world.feature;

import com.mojang.serialization.Codec;
import net.minecraft.structure.StructureManager;
import net.minecraft.structure.StructurePiece;
import net.minecraft.structure.StructureStart;
import net.minecraft.util.math.BlockBox;
import net.minecraft.util.math.ChunkPos;
import net.minecraft.util.registry.DynamicRegistryManager;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.source.BiomeSource;
import net.minecraft.world.gen.ChunkRandom;
import net.minecraft.world.gen.chunk.ChunkGenerator;
import net.minecraft.world.gen.chunk.StructureConfig;
import net.minecraft.world.gen.feature.JigsawFeature;
import net.minecraft.world.gen.feature.StructurePoolFeatureConfig;

public class DungeonFeature extends JigsawFeature {
	public DungeonFeature(Codec<StructurePoolFeatureConfig> codec) {
		super(codec, 0, true, true);
	}

	//TODO: make the entry the 1st object
	@Override
	public StructureStartFactory<StructurePoolFeatureConfig> getStructureStartFactory() {
		return (feature, chunkX, chunkZ, boundingBox, references, seed) -> new Start(this, chunkX, chunkZ, boundingBox, references, seed);
	}

	@Override
	public StructureStart<?> tryPlaceStart(DynamicRegistryManager dynamicRegistryManager, ChunkGenerator chunkGenerator, BiomeSource biomeSource, StructureManager structureManager, long worldSeed, ChunkPos chunkPos, Biome biome, int referenceCount, ChunkRandom chunkRandom, StructureConfig structureConfig, StructurePoolFeatureConfig featureConfig) {
		return super.tryPlaceStart(dynamicRegistryManager, chunkGenerator, biomeSource, structureManager, worldSeed, chunkPos, biome, referenceCount, chunkRandom, structureConfig, featureConfig);
	}

	public static class Start extends JigsawFeature.Start {
		public Start(DungeonFeature dungeonFeature, int chunkX, int chunkZ, BlockBox boundingBox, int references, long seed) {
			super(dungeonFeature, chunkX, chunkZ, boundingBox, references, seed);
		}

		@Override
		public void init(DynamicRegistryManager dynamicRegistryManager, ChunkGenerator chunkGenerator, StructureManager structureManager, int i, int j, Biome biome, StructurePoolFeatureConfig structurePoolFeatureConfig) {
			super.init(dynamicRegistryManager, chunkGenerator, structureManager, i, j, biome, structurePoolFeatureConfig);

			for (StructurePiece piece : this.children) {
				piece.translate(0, -16, 0);
			}
		}
	}
}
