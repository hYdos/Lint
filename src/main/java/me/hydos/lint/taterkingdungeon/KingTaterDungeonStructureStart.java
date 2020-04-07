package me.hydos.lint.taterkingdungeon;

import com.google.common.collect.ImmutableList;
import com.mojang.datafixers.util.Pair;

import net.minecraft.structure.StructureManager;
import net.minecraft.structure.StructureStart;
import net.minecraft.structure.pool.SinglePoolElement;
import net.minecraft.structure.pool.StructurePool;
import net.minecraft.structure.pool.StructurePoolBasedGenerator;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockBox;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.gen.chunk.ChunkGenerator;
import net.minecraft.world.gen.feature.StructureFeature;

public class KingTaterDungeonStructureStart extends StructureStart {
	private static final Identifier BASE_POOL = new Identifier("lint:dungeon_pool");

	KingTaterDungeonStructureStart(StructureFeature<?> feature, int x, int z, BlockBox box, int int_3, long seed) {
		super(feature, x, z, box, int_3, seed);
	}

	@Override
	public void initialize(ChunkGenerator<?> chunkGenerator, StructureManager structureManager, int x, int z, Biome biome) {
		// start pool, size, piece (only 1 piece needed)
		StructurePoolBasedGenerator.addPieces(BASE_POOL, 4, KingTaterDungeonPiece::new, chunkGenerator, structureManager, new BlockPos(x * 16, 150, z * 16), children, random);
		setBoundingBoxFromChildren();
	}

	static {
		StructurePoolBasedGenerator.REGISTRY.add(
				new StructurePool(
						BASE_POOL,
						new Identifier("empty"),
						ImmutableList.of(
								Pair.of(new SinglePoolElement("lint:dungeon_enterance"), 1),
								Pair.of(new SinglePoolElement("lint:dungeon_short_walkway"), 1),
								Pair.of(new SinglePoolElement("lint:dungeon_long_walkway"), 1),
								Pair.of(new SinglePoolElement("lint:dungeon_boss_room"), 1)
								),
						StructurePool.Projection.RIGID
						)
				);
	}
}
