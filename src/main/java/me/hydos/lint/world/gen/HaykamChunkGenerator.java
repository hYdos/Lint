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

import java.util.List;
import java.util.Random;
import java.util.function.Supplier;
import java.util.stream.IntStream;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;

import me.hydos.lint.block.Blocks;
import me.hydos.lint.util.callback.ServerChunkManagerCallback;
import me.hydos.lint.world.biome.HaykamBiomeSource;
import me.hydos.lint.world.feature.FloatingIslandModifier;
import net.minecraft.block.BlockState;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.crash.CrashException;
import net.minecraft.util.crash.CrashReport;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.ChunkPos;
import net.minecraft.util.math.noise.OctaveSimplexNoiseSampler;
import net.minecraft.util.registry.Registry;
import net.minecraft.util.registry.RegistryLookupCodec;
import net.minecraft.world.BlockView;
import net.minecraft.world.ChunkRegion;
import net.minecraft.world.Heightmap;
import net.minecraft.world.WorldAccess;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.chunk.Chunk;
import net.minecraft.world.gen.ChunkRandom;
import net.minecraft.world.gen.GenerationStep;
import net.minecraft.world.gen.StructureAccessor;
import net.minecraft.world.gen.chunk.ChunkGenerator;
import net.minecraft.world.gen.chunk.StructuresConfig;
import net.minecraft.world.gen.chunk.VerticalBlockSample;
import net.minecraft.world.gen.feature.ConfiguredFeature;

public class HaykamChunkGenerator extends ChunkGenerator {

	public static final Codec<HaykamChunkGenerator> CODEC = RecordCodecBuilder.create((instance) -> instance.group(
			Codec.LONG.fieldOf("seed").stable().forGetter((generator) -> generator.seed),
			RegistryLookupCodec.of(Registry.BIOME_KEY).forGetter(haykamChunkGenerator -> haykamChunkGenerator.biomeRegistry)
			).apply(instance, instance.stable(HaykamChunkGenerator::new)));
	private final ChunkRandom random = new ChunkRandom();
	private final long seed;
	private final Registry<Biome> biomeRegistry;

	private HaykamTerrainGenerator terrain;

	private FloatingIslandModifier floatingIslands;
	private OctaveSimplexNoiseSampler surfaceNoise;

	public HaykamChunkGenerator(Long seed, Registry<Biome> registry) {
		super(new HaykamBiomeSource(registry, seed), new StructuresConfig(false));
		this.seed = seed;
		this.biomeRegistry = registry;

		ServerChunkManagerCallback.EVENT.register(manager -> {
			long worldSeed = ((ServerWorld) manager.getWorld()).getSeed();
			Random rand = new Random(seed);
			this.terrain = new HaykamTerrainGenerator(worldSeed, rand);
			((HaykamBiomeSource) this.biomeSource).setTerrainData(this.terrain);
			this.floatingIslands = new FloatingIslandModifier(worldSeed);
			this.surfaceNoise = new OctaveSimplexNoiseSampler(this.random, IntStream.rangeClosed(-3, 0));
		});
	}

	@Override
	protected Codec<? extends ChunkGenerator> getCodec() {
		return CODEC;
	}

	@Override
	public ChunkGenerator withSeed(long seed) {
		return new HaykamChunkGenerator(seed, biomeRegistry);
	}

	@Override
	public void populateNoise(WorldAccess world, StructureAccessor accessor, Chunk chunk) {
		final int startX = ((chunk.getPos().x) << 4);
		final int startZ = ((chunk.getPos().z) << 4);
		final int seaLevel = this.getSeaLevel();

		BlockPos.Mutable pos = new BlockPos.Mutable();

		for (int xo = 0; xo < 16; ++xo) {
			final int x = xo + startX;
			pos.setX(xo);

			for (int zo = 0; zo < 16; ++zo) {
				final int z = zo + startZ;
				final int dist = x * x + z * z;
				pos.setZ(zo);

				int height = this.terrain.getHeight(x, z);
				int lowerBound = this.terrain.getLowerGenBound(x, z, height);
				boolean ash = this.surfaceNoise.sample(x * 0.09, z * 0.09, true) > 0 && (height - lowerBound) < 3;
 
				if (height - lowerBound == 1) {
					lowerBound--;
				}

				for (int y = lowerBound; y < world.getHeight(); ++y) {
					pos.setY(y);

					if (y < height) {
						BlockState state;

						if (dist > HaykamTerrainGenerator.SHARDLANDS_ISLANDS_START) {
							if (ash && y > lowerBound) {
								state = Blocks.ASH.getDefaultState();
							} else {
								state = Blocks.ASPHALT.getDefaultState();
							}
						} else {
							state = Blocks.FUSED_STONE.getDefaultState();
						}

						chunk.setBlockState(pos, state, false);
					} else if (y < seaLevel) {
						chunk.setBlockState(pos, Blocks.WATER.getDefaultState(), false);
					} else {
						chunk.setBlockState(pos, Blocks.AIR.getDefaultState(), false);
					}
				}
			}
		}
	}

	@Override
	public int getHeight(int x, int z, Heightmap.Type heightmapType) {
		int height = this.terrain.getHeight(x, z);
		return heightmapType.getBlockPredicate().test(Blocks.WATER.getDefaultState()) ? Math.max(height, this.getSeaLevel()) : height;
	}

	@Override
	public int getSeaLevel() {
		return HaykamTerrainGenerator.SEA_LEVEL;
	}

	@Override
	public void buildSurface(ChunkRegion region, Chunk chunk) {
		ChunkPos chunkPos = chunk.getPos();
		int chunkX = chunkPos.x;
		int chunkZ = chunkPos.z;

		ChunkRandom rand = new ChunkRandom();
		rand.setTerrainSeed(chunkX, chunkZ);

		int startX = chunkPos.getStartX();
		int startZ = chunkPos.getStartZ();
		BlockPos.Mutable mutable = new BlockPos.Mutable();

		for(int xo = 0; xo < 16; ++xo) {
			int x = startX + xo;

			for(int zo = 0; zo < 16; ++zo) {
				int z = startZ + zo;
				int height = chunk.sampleHeightmap(Heightmap.Type.WORLD_SURFACE_WG, xo, zo) + 1;
				double noise = this.surfaceNoise.sample((double)x * 0.0625D, (double)z * 0.0625D, 0.0625D, (double)xo * 0.0625D) * 15.0D;

				region.getBiome(mutable.set(startX + xo, height, startZ + zo)).buildSurface(rand, chunk, x, z, height, noise,
						Blocks.FUSED_STONE.getDefaultState(), Blocks.WATER.getDefaultState(), this.getSeaLevel(), region.getSeed());

				// bedrock
				if (x * x + z * z < HaykamTerrainGenerator.SHARDLANDS_FADE_START) {
					mutable.setY(0);
					chunk.setBlockState(mutable, Blocks.BEDROCK.getDefaultState(), false);
				}
			}
		}

	}

	@Override
	public BlockView getColumnSample(int x, int z) {
		BlockState[] column = new BlockState[256];
		final int seaLevel = this.getSeaLevel();
		final int height = this.terrain.getHeight(x, z);

		for (int y = 0; y < 256; ++y) {
			if (y < height) {
				column[y] = Blocks.FUSED_STONE.getDefaultState();
			} else if (y < seaLevel) {
				column[y] = Blocks.WATER.getDefaultState();
			} else {
				column[y] = Blocks.AIR.getDefaultState();
			}
		}

		return new VerticalBlockSample(column);
	}

	@Override
	public void generateFeatures(ChunkRegion region, StructureAccessor accessor) {
		super.generateFeatures(region, accessor);

		int centreChunkX = region.getCenterChunkX();
		int centreChunkZ = region.getCenterChunkZ();
		int startX = centreChunkX * 16;
		int startZ = centreChunkZ * 16;

		// generate floating islands
		ChunkRandom genRand = new ChunkRandom();
		genRand.setTerrainSeed(centreChunkX, centreChunkZ);

		if (this.floatingIslands.generate(region, genRand, startX, startZ)) { // Only run vegetal decoration if floating island is in chunk
			Biome biome = this.populationSource.getBiomeForNoiseGen((centreChunkX << 2) + 2, 2, (centreChunkZ << 2) + 2);
			BlockPos startPos = new BlockPos(startX, 0, startZ);
			ChunkRandom rand = new ChunkRandom();
			rand.setPopulationSeed(region.getSeed() + 1, startX, startZ);

			List<List<Supplier<ConfiguredFeature<?, ?>>>> list = biome.getGenerationSettings().getFeatures();
			this.postVegetalPlacement(list, region, startPos, rand);
		}
	}

	private <T extends List<R>, R extends Supplier<ConfiguredFeature<?, ?>>> void postVegetalPlacement(List<T> list, ChunkRegion region, BlockPos pos, ChunkRandom random) {
		int n = GenerationStep.Feature.VEGETAL_DECORATION.ordinal();

		if (list.size() > n) {
			for (R supplier : list.get(n)) {
				try {
					ConfiguredFeature<?, ?> configured = supplier.get();

					try {
						configured.generate(region, this, random, pos);
					} catch (Exception var22) {
						CrashReport report = CrashReport.create(var22, "Feature placement");
						// noinspection Convert2MethodRef - Java 8 compiler bug
						report.addElement("Feature").add("Id", Registry.FEATURE.getId(configured.feature)).add("Config", configured.config).add("Description", () -> configured.feature.toString());
						throw new CrashException(report);
					}
				} catch (ClassCastException ignored) {
					// no
				}
			}
		}
	}
}
