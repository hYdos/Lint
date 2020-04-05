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

public class TaterKingDungeonStructureStart extends StructureStart {

    private static final Identifier BASE_POOL = new Identifier("lint:dungeon_pool");

    public TaterKingDungeonStructureStart(StructureFeature<?> feature, int chunkX, int chunkZ, BlockBox box, int references, long seed) {
        super(feature, chunkX, chunkZ, box, references, seed);
    }

    @Override
    public void initialize(ChunkGenerator<?> chunkGenerator, StructureManager structureManager, int x, int z, Biome biome) {
        StructurePoolBasedGenerator.addPieces(BASE_POOL, 15, BasicPiece::new, chunkGenerator, structureManager, new BlockPos(x * 16, 150, z * 16), children, random);
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
