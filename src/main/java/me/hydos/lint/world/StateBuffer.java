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

package me.hydos.lint.world;

import java.util.HashMap;
import java.util.Map;

import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.Heightmap;
import net.minecraft.world.gen.chunk.ChunkGenerator;

public class StateBuffer implements StructureWorld {
	public StateBuffer(ChunkGenerator generator) {
		this.generator = generator;
	}

	private final Map<BlockPos, BlockState> states = new HashMap<>();
	private final Map<BlockPos, Identifier> chests = new HashMap<>();
	private final ChunkGenerator generator;

	@Override
	public void setBlockState(BlockPos pos, BlockState state) {
		this.states.put(pos, state);
	}

	@Override
	public void setChest(BlockPos pos, Identifier lootTable) {
		this.states.put(pos, Blocks.CHEST.getDefaultState());
		this.chests.put(pos, lootTable);
	}

	@Override
	public int getSeaLevel() {
		return this.generator.getSeaLevel();
	}

	@Override
	public int getHeight(int x, int z) {
		return this.generator.getHeight(x, z, Heightmap.Type.OCEAN_FLOOR_WG);
	}
}
