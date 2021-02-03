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

import java.util.List;
import java.util.function.Supplier;
import java.util.stream.Stream;

import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.source.BiomeSource;

public abstract class TerrainBiomeSource extends BiomeSource {
	protected TerrainBiomeSource(List<Biome> biomes) {
		super(biomes);
	}

	protected TerrainBiomeSource(Stream<Supplier<Biome>> biomes) {
		super(biomes);
	}

	protected TerrainGenerator data;

	public void setTerrainData(TerrainGenerator data) {
		this.data = data;
	}
}
