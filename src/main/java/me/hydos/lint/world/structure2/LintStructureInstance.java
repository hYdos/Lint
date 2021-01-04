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

package me.hydos.lint.world.structure2;

import me.hydos.lint.world.StateBuffer;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Box;
import net.minecraft.world.gen.ChunkRandom;
import net.minecraft.world.gen.chunk.ChunkGenerator;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * @author Valoeghese
 * Internal class used for handling structure generation.
 */
public final class LintStructureInstance {
	public LintStructureInstance(LintConfiguredStructure configuredStructure, ChunkGenerator generator, int x, int z) {
		this.stateBuffer = new StateBuffer(generator);

		LintStructure structure = configuredStructure.structure;
		this.id = structure.id;

		BlockPos pos = new BlockPos(x, structure.getYStart(generator, x, z), z);
		ChunkRandom rand = new ChunkRandom();
		rand.setPopulationSeed(structure.getDecorationSeed(), x, z);

		this.rooms = new ArrayList<>();
		Room start = structure.getStartRoom(pos);
		start.computeBounds(rand);
		this.computeRooms(start, rand, 0, configuredStructure.getMaxIterDepth());		
	}

	public final Identifier id;
	private final List<Room> rooms;
	private final StateBuffer stateBuffer;

	private void computeRooms(Room startRoom, ChunkRandom random, int iter, int maxIterDepth) {
		boolean iterateDeeper = iter < maxIterDepth;

		List<Room> nodes = startRoom.computeNodes(startRoom.getBoundingBox(), random);

		roomIterator: for (Room room : nodes) {
			room.computeBounds(random);
			Box box = room.getBoundingBox();

			// stop rooms intersecting each other
			for (Room existing : this.rooms) {
				if (box.intersects(existing.getBoundingBox())) {
					continue roomIterator;
				}
			}

			this.rooms.add(room);

			if (iterateDeeper) {
				computeRooms(room, random, iter + 1, maxIterDepth);
			}
		}
	}

	public void prepareChunk(Box chunkBox, Random rand) {
		this.rooms.forEach(room -> {
			if (!room.hasGenerated()) {
				if (room.getBoundingBox().intersects(chunkBox)) {
					room.generate(this.stateBuffer, rand);
				}
			}
		});
	}

	public StateBuffer getStateBuffer() {
		return this.stateBuffer;
	}
}
