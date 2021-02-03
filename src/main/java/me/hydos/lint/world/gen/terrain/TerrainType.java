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

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import me.hydos.lint.util.math.Vec2i;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.source.BiomeSource;

public final class TerrainType {
	public TerrainType(TerrainGeneratorFunction terrainGeneratorProvider, BiomeProviderFunction biomeSourceProvider) {
		this.terrainGeneratorProvider = terrainGeneratorProvider;
		this.biomeSourceProvider = biomeSourceProvider;
	}

	private final TerrainGeneratorFunction terrainGeneratorProvider;
	private final BiomeProviderFunction biomeSourceProvider;

	public TerrainGenerator createTerrainGenerator(long seed, Random rand, Vec2i[] keyLocations) {
		return this.terrainGeneratorProvider.apply(seed, rand, keyLocations);
	}

	public BiomeSource createBiomeSource(TerrainGenerator terrain, long seed, Registry<Biome> registry) {
		TerrainBiomeSource result = this.biomeSourceProvider.apply(seed, registry);
		result.setTerrainData(terrain);
		return result;
	}

	public static final Map<Identifier, TerrainType> REGISTRY = new HashMap<>();

	@FunctionalInterface
	public interface TerrainGeneratorFunction {
		TerrainGenerator apply(long seed, Random rand, Vec2i[] keyLocations);
	}

	@FunctionalInterface
	public interface BiomeProviderFunction {
		TerrainBiomeSource apply(long seed, Registry<Biome> registry);
	}
}
