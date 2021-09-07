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

package me.hydos.lint.world.gen.terrain;

import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;

public interface TerrainGenerator {
	double sampleTypeScale(int x, int z);

	double sampleTerrainScale(int x, int z);

	int sampleBaseHeight(int x, int z);

	int sampleTerraceMod(int x, int z);

	int getHeight(int x, int z);

	/**
	 * Get the lower bound of the terrain gen.
	 * @param x the block x position.
	 * @param z the block z position.
	 * @param height the height of the terrain at that position.
	 * @return the lowest position at which a block will be placed in the base terrain.
	 */
	default int getLowerGenBound(int x, int z, int height) {
		return 0;
	}

	default BlockState getDefaultBlock(int x, int y, int z, int height, int lowerBound, double surfaceNoise) {
		return Blocks.STONE.getDefaultState();
	}

	default BlockState getDefaultFluid(int x, int y, int z, int height, int lowerBound, double surfaceNoise) {
		return Blocks.WATER.getDefaultState();
	}
}
