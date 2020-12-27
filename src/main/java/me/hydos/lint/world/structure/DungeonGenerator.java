/*
 * Lint
 * Copyright (C) 2020 hYdos, Valoeghese, ramidzkh
 *
 * This program is free software; you can redistribute it and/or
 * modify it under the terms of the GNU Lesser General Public
 * License as published by the Free Software Foundation; either
 * version 3 of the License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 * Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with this program; if not, write to the Free Software Foundation,
 * Inc., 51 Franklin Street, Fifth Floor, Boston, MA  02110-1301, USA.
 */

package me.hydos.lint.world.structure;

import com.google.common.collect.ImmutableList;
import com.mojang.datafixers.util.Pair;
import me.hydos.lint.Lint;
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
                                Pair.of(Structures.createLegacySinglePoolElement("dungeon/boss/king_tater", StructureProcessorLists.EMPTY), 50),
                                Pair.of(Structures.createLegacySinglePoolElement("dungeon/pathway/carpet_intersection", StructureProcessorLists.EMPTY), 50),
                                Pair.of(Structures.createSinglePoolElement("dungeon/decoration/carpet_large_paintings", StructureProcessorLists.EMPTY), 50),
                                Pair.of(Structures.createLegacySinglePoolElement("dungeon/decoration/cell_room", StructureProcessorLists.EMPTY), 50),
                                Pair.of(Structures.createSinglePoolElement("dungeon/pathway/east_pathway", StructureProcessorLists.EMPTY), 50),
                                Pair.of(Structures.createSinglePoolElement("dungeon/pathway/east_west_corner", StructureProcessorLists.EMPTY), 50),
                                Pair.of(Structures.createSinglePoolElement("dungeon/pathway/hallway_north", StructureProcessorLists.EMPTY), 50),
                                Pair.of(Structures.createSinglePoolElement("dungeon/pathway/intermediate_sized_carpet", StructureProcessorLists.EMPTY), 50),
                                Pair.of(Structures.createSinglePoolElement("dungeon/pathway/intersection", StructureProcessorLists.EMPTY), 50),
                                Pair.of(Structures.createLegacySinglePoolElement("dungeon/pathway/large_carpet_stairs", StructureProcessorLists.EMPTY), 50),
                                Pair.of(Structures.createSinglePoolElement("dungeon/pathway/prison_corner_connector", StructureProcessorLists.EMPTY), 50),
                                Pair.of(Structures.createLegacySinglePoolElement("dungeon/pathway/staircase_split_connector_enterance", StructureProcessorLists.EMPTY), 50),
                                Pair.of(Structures.createSinglePoolElement("dungeon/pathway/stairs", StructureProcessorLists.EMPTY), 50),
                                Pair.of(Structures.createSinglePoolElement("dungeon/pathway/corner_north_east", StructureProcessorLists.EMPTY), 50)),
                        StructurePool.Projection.RIGID));
    }
}
