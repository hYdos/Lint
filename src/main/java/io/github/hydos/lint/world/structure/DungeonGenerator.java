package io.github.hydos.lint.world.structure;

import com.google.common.collect.ImmutableList;
import com.mojang.datafixers.util.Pair;
import io.github.hydos.lint.Lint;
import net.minecraft.structure.pool.StructurePool;
import net.minecraft.structure.pool.StructurePools;
import net.minecraft.structure.processor.StructureProcessorLists;
import net.minecraft.util.Identifier;

public class DungeonGenerator {
    public static StructurePool structures;

    public static void init() {
        structures = StructurePools.register(
                new StructurePool(Lint.id("dungeon"), new Identifier("empty"),
                        ImmutableList.of(
                                Pair.of(Structures.createLegacySinglePoolElement("dungeon/dungeon_enterance", StructureProcessorLists.EMPTY), 50),
                                Pair.of(Structures.createLegacySinglePoolElement("dungeon/dungeon_prison_corner_connector", StructureProcessorLists.EMPTY), 50),
                                Pair.of(Structures.createLegacySinglePoolElement("dungeon/dungeon_staircase_split_connector", StructureProcessorLists.EMPTY), 50),
                                Pair.of(Structures.createLegacySinglePoolElement("dungeon/dungeon_staircase_split_connector_entrance", StructureProcessorLists.EMPTY), 50)),
                        StructurePool.Projection.RIGID));
    }
}
