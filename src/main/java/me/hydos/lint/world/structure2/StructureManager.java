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

import java.util.ArrayList;
import java.util.List;

import me.hydos.lint.util.FIFOCache;
import me.hydos.lint.util.math.Maths;
import net.minecraft.util.math.Box;
import net.minecraft.util.math.ChunkPos;
import net.minecraft.world.gen.ChunkRandom;
import net.minecraft.world.gen.chunk.ChunkGenerator;

public final class StructureManager {
	public StructureManager(ChunkGenerator generator) {
		this.generator = generator;
	}

	private final ChunkGenerator generator;
	private final ChunkRandom random = new ChunkRandom();
	private final FIFOCache<LintStructureInstance> activeInstances = new FIFOCache<>(new LintStructureInstance[16]);

	public void prepareChunkForPopulation(long worldSeed, int chunkX, int chunkZ) {
		long seed = worldSeed + 1;
		int blockStartX = (chunkX << 4);
		int blockStartZ = (chunkZ << 4);

		Box chunkBox = new Box(blockStartX, 0, blockStartZ, blockStartX + 16, this.generator.getWorldHeight(), blockStartZ + 16);

		for (LintConfiguredStructure structure : this.structures) {
			final int gridS = structure.getGridSize();
			final int startX = chunkX / gridS;
			final int startZ = chunkZ / gridS;
			final int prepareDist = structure.getPrepareChunkDistance();

			for (int xo = -1; xo <= 1; ++xo) {
				final int gridX = startX + xo;

				for (int zo = -1; zo <= 1; ++zo) {
					final int gridZ = startZ + zo;

					this.random.setPopulationSeed(seed, gridX, gridZ);

					// structure start chunk coords
					final int sChunkX = (gridX * gridS) + this.random.nextInt(gridS);
					final int sChunkZ = (gridZ * gridS) + this.random.nextInt(gridS);

					// if should make sure the structure is prepared and maybe generate
					if (Maths.manhattan(chunkX, chunkZ, sChunkX, sChunkZ) <= prepareDist) {
						long position = ChunkPos.toLong(sChunkX, sChunkZ);
						LintStructureInstance structureInstance = this.activeInstances.getOrAdd(
								position,
								t -> t.id == structure.structure.id,
								pos -> new LintStructureInstance(
										structure,
										this.generator,
										(sChunkX << 4) + this.random.nextInt(16),
										(sChunkZ << 4) + this.random.nextInt(16)));
						this.random.setPopulationSeed(seed, chunkX, chunkZ);
						structureInstance.prepareChunk(chunkBox, this.random);
					}
				}
			}

			++seed;
		}
	}

	/**
	 * Add a structure to this lint structure generator.
	 * @param structure the structure to generate.
	 * @param gridSize the grid size of the structure.
	 * @param prepareChunkDistance the manhatttan distance in chunks away to prepare the structure for generation.
	 * @param maxIterDepth the maximum iterations of room appendage for the structure.
	 */
	public void addStructure(LintStructure structure, int gridSize, int prepareChunkDistance, int maxIterDepth) {
		this.structures.add(new LintConfiguredStructure(structure, maxIterDepth, prepareChunkDistance, gridSize));
	}

	private final List<LintConfiguredStructure> structures = new ArrayList<>();
}
