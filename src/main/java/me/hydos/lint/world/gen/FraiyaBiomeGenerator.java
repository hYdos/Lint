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

package me.hydos.lint.world.gen;

import me.hydos.lint.util.math.Vec2i;
import me.hydos.lint.world.biome.Biomes;
import me.hydos.lint.world.feature.OldTownFeatureConstants;
import me.hydos.lint.world.feature.TownFeature;
import me.hydos.lint.world.gen.terrain.BiomeGenerator;
import me.hydos.lint.world.gen.terrain.TerrainGenerator;
import me.hydos.lint.world.layer.GenericBiomes;
import me.hydos.lint.world.layer.MountainBiomes;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.layer.ScaleLayer;
import net.minecraft.world.biome.layer.SmoothLayer;
import net.minecraft.world.biome.layer.type.InitLayer;
import net.minecraft.world.biome.layer.util.CachingLayerContext;
import net.minecraft.world.biome.layer.util.LayerFactory;
import net.minecraft.world.biome.layer.util.LayerSampleContext;
import net.minecraft.world.biome.layer.util.LayerSampler;
import net.minecraft.world.biome.source.BiomeLayerSampler;

import java.util.function.LongFunction;

public class FraiyaBiomeGenerator extends BiomeGenerator {
	public FraiyaBiomeGenerator(long seed, Registry<Biome> registry, TerrainGenerator generator) {
		super(generator, registry, seed);
	}

	public static <T extends LayerSampler, C extends LayerSampleContext<T>> LayerFactory<T> stackLayers(InitLayer biomes, LongFunction<C> contextProvider) {
		LayerFactory<T> result = biomes.create(contextProvider.apply(1L));
		for (int i = 0; i < 6; i++) {
			result = ScaleLayer.NORMAL.create(contextProvider.apply(1000 + i), result);
		}
		result = SmoothLayer.INSTANCE.create(contextProvider.apply(4L), result);
		return result;
	}

	@Override
	public Biome getBiomeForNoiseGen(int biomeX, int biomeZ) {
		// TODO new biome system, once terrain gen is finished
		int x = (biomeX << 2);
		int z = (biomeZ << 2);
		int dist = x * x + z * z;

		if (dist > FraiyaTerrainGenerator.SHARDLANDS_ISLANDS_START) {
			return this.biomeRegistry.getOrThrow(Biomes.DAWN_SHARDLANDS_KEY);
		} else if (dist > FraiyaTerrainGenerator.SHARDLANDS_EDGE_START) {
			return this.biomeRegistry.getOrThrow(Biomes.DAWN_SHARDLANDS_EDGE_KEY);
		}

		double baseHeight = this.terrainData.sampleBaseHeight(x, z);

		int frozenCentreZ = z + 6200;
		boolean frozen = frozenCentreZ * frozenCentreZ + x * x < 5000 * 5000;

		if (baseHeight < FraiyaTerrainGenerator.SEA_LEVEL + 2) {
			if (baseHeight > FraiyaTerrainGenerator.SEA_LEVEL - 2) {
				return this.biomeRegistry.get(frozen ? Biomes.ETHEREAL_WOODLAND_KEY : Biomes.OCEAN_KEY);
			} else {
				return this.biomeRegistry.getOrThrow(frozen ? Biomes.FROZEN_OCEAN_KEY : Biomes.OCEAN_KEY);
			}
		}

		if (frozen) {
			return this.biomeRegistry.get(Biomes.ETHEREAL_WOODLAND_KEY);
		}

		Biome result = this.biomeRegistry.get(Biomes.MYSTICAL_GROVE_KEY);

		for (Vec2i town : ((FraiyaTerrainGenerator) this.terrainData).townAreas) {
			if (town.squaredDist(x, z) < OldTownFeatureConstants.SUBURB_DIST) {
				// remove corrupt forest in town suburbs and centres. Yes indigo ridges can still apply.
				return this.biomeRegistry.getKey(result).get().equals(Biomes.CORRUPT_FOREST_KEY) ? this.biomeRegistry.get(Biomes.MYSTICAL_GROVE_KEY) : result;
			}
		}

		return result;
	}
}
