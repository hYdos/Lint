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

public final class TerrainType {
	public TerrainType(TerrainGeneratorFunction terrainGeneratorProvider, BiomeGeneratorFunction biomeGeneratorProvider) {
		this.terrainGeneratorProvider = terrainGeneratorProvider;
		this.biomeGeneratorProvider = biomeGeneratorProvider;
	}

	private final TerrainGeneratorFunction terrainGeneratorProvider;
	private final BiomeGeneratorFunction biomeGeneratorProvider;

	public TerrainGenerator createTerrainGenerator(long seed, Random rand, Vec2i[] keyLocations) {
		return this.terrainGeneratorProvider.apply(seed, rand, keyLocations);
	}

	public BiomeGenerator createBiomeGenerator(TerrainGenerator terrain, long seed, Registry<Biome> registry) {
		BiomeGenerator result = this.biomeGeneratorProvider.apply(terrain, registry, seed);
		return result;
	}

	public static final Map<Identifier, TerrainType> REGISTRY = new HashMap<>();

	@FunctionalInterface
	public interface TerrainGeneratorFunction {
		TerrainGenerator apply(long seed, Random rand, Vec2i[] keyLocations);
	}

	@FunctionalInterface
	public interface BiomeGeneratorFunction {
		BiomeGenerator apply(TerrainGenerator generator, Registry<Biome> registry, long seed);
	}
}
