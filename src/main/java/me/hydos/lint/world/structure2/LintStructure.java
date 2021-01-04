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

import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.gen.chunk.ChunkGenerator;

import java.util.function.Function;
import java.util.function.Predicate;

/**
 * @author Valoeghese
 * Class for structures that use a StateBuffer.
 */
public final class LintStructure {
	public LintStructure(Identifier id, long decorationSeed, Function<BlockPos, Room> startRoom, StartHeightProvider getYStart, Predicate<Biome> canStartIn) {
		this.id = id;
		this.decorationSeed = decorationSeed;
		this.startRoom = startRoom;
		this.getYStart = getYStart;
		this.canStartIn = canStartIn;
	}

	public final Identifier id;
	private final Function<BlockPos, Room> startRoom;
	private final StartHeightProvider getYStart;
	private final Predicate<Biome> canStartIn;
	private final long decorationSeed;

	public Room getStartRoom(BlockPos pos) {
		return this.startRoom.apply(pos);
	}

	public int getYStart(ChunkGenerator generator, int x, int z) {
		return this.getYStart.findStartHeight(generator, x, z);
	}

	public boolean canStartIn(Biome biome) {
		return this.canStartIn.test(biome);
	}

	public long getDecorationSeed() {
		return this.decorationSeed;
	}

	@FunctionalInterface
	public static interface StartHeightProvider {
		int findStartHeight(ChunkGenerator generator, int x, int z);
	}
}
