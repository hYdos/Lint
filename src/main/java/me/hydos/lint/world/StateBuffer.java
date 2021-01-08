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

import it.unimi.dsi.fastutil.longs.Long2ObjectArrayMap;
import it.unimi.dsi.fastutil.longs.Long2ObjectMap;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.ChestBlock;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.ChestBlockEntity;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.ChunkPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.Heightmap;
import net.minecraft.world.WorldAccess;
import net.minecraft.world.gen.chunk.ChunkGenerator;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

public final class StateBuffer implements StructureWorld {
	private final Long2ObjectMap<Chunk> chunks = new Long2ObjectArrayMap<>();
	private final ChunkGenerator generator;

	public StateBuffer(ChunkGenerator generator) {
		this.generator = generator;
	}

	private Chunk getOrCreateChunk(BlockPos pos) {
		return this.chunks.computeIfAbsent(
				ChunkPos.toLong(pos.getX() >> 4, pos.getZ() >> 4),
				cpos -> new Chunk());
	}

	@Override
	public void setBlockState(BlockPos pos, BlockState state) {
		this.getOrCreateChunk(pos).states.put(pos, state);
	}

	@Override
	public void setChest(BlockPos pos, Random rand, Identifier lootTable) {
		Chunk chunk = this.getOrCreateChunk(pos);

		Direction direction = null;

		switch (rand.nextInt(4)) {
			case 0:
				direction = Direction.NORTH;
				break;
			case 1:
				direction = Direction.SOUTH;
				break;
			case 2:
				direction = Direction.EAST;
				break;
			case 3:
				direction = Direction.WEST;
				break;
			default:
				break;
		}

		chunk.states.put(pos, Blocks.CHEST.getDefaultState().with(ChestBlock.FACING, direction));
		chunk.chests.put(pos, lootTable);
	}

	@Override
	public int getSeaLevel() {
		return this.generator.getSeaLevel();
	}

	@Override
	public int getHeight(int x, int z) {
		return this.generator.getHeight(x, z, Heightmap.Type.OCEAN_FLOOR_WG);
	}

	public void pasteChunk(WorldAccess world, Random random, int chunkX, int chunkZ) {
		long chunkPos = ChunkPos.toLong(chunkX, chunkZ);

		if (this.chunks.containsKey(chunkPos)) {
			Chunk chunk = this.chunks.get(chunkPos);

			chunk.states.forEach((pos, state) -> {
				world.setBlockState(pos, state, 0b11);

				if (state.getBlock() == Blocks.CHEST && chunk.chests.containsKey(pos)) {
					BlockEntity entity = world.getBlockEntity(pos);
					((ChestBlockEntity) entity).setLootTable(chunk.chests.get(pos), random.nextLong());
				}
			});

			// free memory
			this.chunks.remove(chunkPos);
		}
	}

	private static class Chunk {
		private final Map<BlockPos, BlockState> states = new HashMap<>();
		private final Map<BlockPos, Identifier> chests = new HashMap<>();
	}
}
