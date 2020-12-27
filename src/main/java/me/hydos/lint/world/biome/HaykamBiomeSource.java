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

package me.hydos.lint.world.biome;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import me.hydos.lint.world.gen.HaykamTerrainGenerator;
import me.hydos.lint.world.layer.GenericBiomes;
import me.hydos.lint.world.layer.MountainBiomes;
import net.minecraft.util.registry.Registry;
import net.minecraft.util.registry.RegistryLookupCodec;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.layer.ScaleLayer;
import net.minecraft.world.biome.layer.SmoothLayer;
import net.minecraft.world.biome.layer.type.InitLayer;
import net.minecraft.world.biome.layer.util.CachingLayerContext;
import net.minecraft.world.biome.layer.util.LayerFactory;
import net.minecraft.world.biome.layer.util.LayerSampleContext;
import net.minecraft.world.biome.layer.util.LayerSampler;
import net.minecraft.world.biome.source.BiomeLayerSampler;
import net.minecraft.world.biome.source.BiomeSource;

import java.util.function.LongFunction;
import java.util.stream.Collectors;

public class HaykamBiomeSource extends BiomeSource {
	public static final Codec<HaykamBiomeSource> CODEC = RecordCodecBuilder.create(instance -> instance.group(
			RegistryLookupCodec.of(Registry.BIOME_KEY).forGetter(source -> source.biomeRegistry),
			Codec.LONG.fieldOf("seed").stable().forGetter(source -> source.seed))
			.apply(instance, instance.stable(HaykamBiomeSource::new)));

	private final Registry<Biome> biomeRegistry;
	private final long seed;

	private final BiomeLayerSampler genericSampler;
	private final BiomeLayerSampler mountainSampler;
	private final BiomeLayerSampler beachSampler;

	private TerrainData data;

	public HaykamBiomeSource(Registry<Biome> biomeRegistry, long seed) {
		super(biomeRegistry.stream().collect(Collectors.toList()));
		this.seed = seed;
		this.biomeRegistry = biomeRegistry;

		this.genericSampler = createBiomeLayerSampler(new GenericBiomes(biomeRegistry, false), seed);
		this.mountainSampler = createBiomeLayerSampler(new MountainBiomes(biomeRegistry), seed);
		this.beachSampler = createBiomeLayerSampler(new GenericBiomes(biomeRegistry, true), seed);
	}

	public static BiomeLayerSampler createBiomeLayerSampler(InitLayer biomes, long seed) {
		LongFunction<CachingLayerContext> contextProvider = salt -> new CachingLayerContext(25, seed, salt);
		return new BiomeLayerSampler(stackLayers(biomes, contextProvider));
	}

	public static <T extends LayerSampler, C extends LayerSampleContext<T>> LayerFactory<T> stackLayers(InitLayer biomes, LongFunction<C> contextProvider) {
		LayerFactory<T> result = biomes.create(contextProvider.apply(1L));
		for (int i = 0; i < 6; i++) {
			result = ScaleLayer.NORMAL.create(contextProvider.apply(1000 + i), result);
		}
		result = SmoothLayer.INSTANCE.create(contextProvider.apply(4L), result);
		return result;
	}

	public void setTerrainData(TerrainData data) {
		this.data = data;
	}

	@Override
	public Biome getBiomeForNoiseGen(int biomeX, int biomeY, int biomeZ) {
		int x = (biomeX << 2);
		int z = (biomeZ << 2);
		int dist = x * x + z * z;

		if (dist > HaykamTerrainGenerator.SHARDLANDS_START) {
			return this.biomeRegistry.getOrThrow(Biomes.DAWN_SHARDLANDS_KEY);
		}

		double baseHeight = this.data.sampleBaseHeight(x, z);
		//final int limit = HaykamTerrainGenerator.SEA_LEVEL + 2;

		if (baseHeight < HaykamTerrainGenerator.SEA_LEVEL + 2) {
			/*for (GridDirection direction : GridDirection.values()) {
				baseHeight = this.data.sampleBaseHeight(x + direction.xOff * 32, z + direction.zOff * 32);

				if (baseHeight < limit) {*/
			if (baseHeight > HaykamTerrainGenerator.SEA_LEVEL - 2) {
				return this.beachSampler.sample(this.biomeRegistry, biomeX, biomeZ);
			} else {
				return this.biomeRegistry.getOrThrow(Biomes.OCEAN_KEY);
			}
			/*}
			}*/
		}

		double scale = this.data.sampleTerrainScale(x, z);
		return (scale > 40.0 ? this.mountainSampler : this.genericSampler).sample(this.biomeRegistry, biomeX, biomeZ);
	}

	@Override
	protected Codec<? extends BiomeSource> getCodec() {
		return CODEC;
	}

	@Override
	public BiomeSource withSeed(long seed) {
		return new HaykamBiomeSource(biomeRegistry, seed);
	}
}