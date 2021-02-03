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

import java.util.stream.Collectors;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;

import me.hydos.lint.Lint;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;
import net.minecraft.util.registry.RegistryLookupCodec;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.source.BiomeSource;

public final class TerrainBiomeSource extends BiomeSource {
	public static final Codec<TerrainBiomeSource> CODEC = RecordCodecBuilder.create(instance -> instance.group(
			RegistryLookupCodec.of(Registry.BIOME_KEY).forGetter(source -> source.biomeRegistry),
			Identifier.CODEC.fieldOf("terrainType").orElse(Lint.id("fraiya")).stable().forGetter(generator -> generator.terrainType),
			Codec.LONG.fieldOf("seed").stable().forGetter(source -> source.seed))
			.apply(instance, instance.stable(TerrainBiomeSource::new)));

	private final Registry<Biome> biomeRegistry;
	private final long seed;
	private Identifier terrainType;
	private BiomeGenerator biomeGenerator;

	public TerrainBiomeSource(Registry<Biome> biomeRegistry, Identifier terrainType, long seed) {
		super(biomeRegistry.stream().collect(Collectors.toList()));
		this.seed = seed;
		this.biomeRegistry = biomeRegistry;
		this.terrainType = terrainType;
	}

	public void createBiomeGenerator(TerrainGenerator terrainData) {
		this.biomeGenerator = TerrainType.REGISTRY.get(terrainType).createBiomeGenerator(terrainData, seed, this.biomeRegistry);
	}

	@Override
	public Biome getBiomeForNoiseGen(int biomeX, int biomeY, int biomeZ) {
		return this.biomeGenerator.getBiomeForNoiseGen(biomeX, biomeZ);
	}

	@Override
	protected Codec<? extends BiomeSource> getCodec() {
		return CODEC;
	}

	@Override
	public BiomeSource withSeed(long seed) {
		return new TerrainBiomeSource(this.biomeRegistry, this.terrainType, seed);
	}
}