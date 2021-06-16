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

import com.mojang.datafixers.kinds.App;
import com.mojang.serialization.Codec;
import com.mojang.serialization.DynamicOps;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import me.hydos.lint.Lint;
import me.hydos.lint.block.LintBlocks;
import me.hydos.lint.util.callback.ServerChunkManagerCallback;
import me.hydos.lint.util.math.Vec2i;
import me.hydos.lint.world.feature.Features;
import me.hydos.lint.world.feature.FloatingIslandModifier;
import me.hydos.lint.world.gen.FraiyaTerrainGenerator;
import me.hydos.lint.world.structure2.StructureChunkGenerator;
import me.hydos.lint.world.structure2.StructureManager;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.Identifier;
import net.minecraft.util.crash.CrashException;
import net.minecraft.util.crash.CrashReport;
import net.minecraft.util.dynamic.RegistryLookupCodec;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.ChunkPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.noise.OctaveSimplexNoiseSampler;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.*;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.source.BiomeSource;
import net.minecraft.world.chunk.Chunk;
import net.minecraft.world.gen.ChunkRandom;
import net.minecraft.world.gen.GenerationStep;
import net.minecraft.world.gen.StructureAccessor;
import net.minecraft.world.gen.chunk.*;
import net.minecraft.world.gen.feature.ConfiguredFeature;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executor;
import java.util.function.Consumer;
import java.util.function.Supplier;
import java.util.stream.IntStream;

// FIXME: port
public class TerrainChunkGenerator extends ChunkGenerator implements StructureChunkGenerator {
	
	public static List<Consumer<StructureManager>> onStructureSetup = new ArrayList<>();
	public static final Codec<TerrainChunkGenerator> CODEC = RecordCodecBuilder.create((instance) -> instance.group(
			Codec.LONG.fieldOf("seed").stable().forGetter((generator) -> generator.seed),
			Identifier.CODEC.fieldOf("terrainType").orElse(Lint.id("fraiya")).stable().forGetter(generator -> generator.terrainType),
			RegistryLookupCodec.of(Registry.BIOME_KEY).forGetter(haykamChunkGenerator -> haykamChunkGenerator.biomeRegistry)
	).apply(instance, instance.stable(TerrainChunkGenerator::new)));
	private final ChunkRandom random = new ChunkRandom();
	private final long seed;
	private final Registry<Biome> biomeRegistry;
	private final List<Vec2i> villageCentres = new ArrayList<>();
	private TerrainGenerator terrain;
	private FloatingIslandModifier floatingIslands;
	private OctaveSimplexNoiseSampler surfaceNoise;
	private StructureManager structureManager;
	private final Identifier terrainType;
	
	public TerrainChunkGenerator(long seed, Identifier terrainType, Registry<Biome> registry) {
		super(new TerrainBiomeSource(registry, terrainType, seed), new StructuresConfig(false));
		this.seed = seed;
		this.biomeRegistry = registry;
		this.terrainType = terrainType;
		
		ServerChunkManagerCallback.EVENT.register(manager -> {
			this.structureManager = new StructureManager(this);
			onStructureSetup.forEach(c -> c.accept(this.structureManager));
			
			long worldSeed = ((ServerWorld) manager.getWorld()).getSeed();
			
			Random rand = new Random(worldSeed + 1);
			double angleRadians = (rand.nextDouble() * Math.PI / 4) - Math.PI / 8;
			final double rightAngle = Math.PI / 2;
			this.villageCentres.add(fromRTheta(1000, angleRadians)); // east, paweria
			this.villageCentres.add(fromRTheta(1000, angleRadians + rightAngle)); // south, heria
			this.villageCentres.add(fromRTheta(1000, angleRadians + 2 * rightAngle)); // west, auria
			this.villageCentres.add(fromRTheta(1000, angleRadians + 3 * rightAngle)); // north, theria
			
			rand.setSeed(worldSeed);
			TerrainType tt = TerrainType.REGISTRY.get(terrainType);
			this.terrain = tt.createTerrainGenerator(worldSeed, rand, this.getTownCentres());
			((TerrainBiomeSource) this.biomeSource).createBiomeGenerator(this.terrain);
			
			this.floatingIslands = tt.floatingIslands ? new FloatingIslandModifier(worldSeed) : null;
			this.surfaceNoise = new OctaveSimplexNoiseSampler(this.random, IntStream.rangeClosed(-3, 0));
		});
	}
	
	public static void onStructureSetup(Consumer<StructureManager> callback) {
		onStructureSetup.add(callback);
	}
	
	private static Vec2i fromRTheta(double r, double theta) { // 0 = +x, pi/2 = +z, etc
		return new Vec2i(
				MathHelper.floor(Math.cos(theta) * r),
				MathHelper.floor(Math.sin(theta) * r));
	}
	
	@Override
	protected Codec<? extends ChunkGenerator> getCodec() {
		return CODEC;
	}
	
	@Override
	public ChunkGenerator withSeed(long seed) {
		return new TerrainChunkGenerator(seed, this.terrainType, this.biomeRegistry);
	}
	
	@Override
	public CompletableFuture<Chunk> populateNoise(Executor executor, StructureAccessor accessor, Chunk chunk) {
		return CompletableFuture.supplyAsync(() -> {
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
					
					for (int y = lowerBound; y < chunk.getHeight(); ++y) {
						pos.setY(y);
						
						if (y < height) {
							BlockState state;
							
							// TODO move this logic to the surface builder, or some other property
							if (dist > FraiyaTerrainGenerator.SHARDLANDS_ISLANDS_START) {
								if (ash && y > lowerBound) {
									state = LintBlocks.ASH.getDefaultState();
								} else {
									state = LintBlocks.ASPHALT.getDefaultState();
								}
							} else {
								state = LintBlocks.FUSED_STONE.getDefaultState();
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
			
			return chunk;
		});
	}

//    @Override
//    public void populateNoise(WorldAccess world, StructureAccessor accessor, Chunk chunk) {
//        final int startX = ((chunk.getPos().x) << 4);
//        final int startZ = ((chunk.getPos().z) << 4);
//        final int seaLevel = this.getSeaLevel();
//
//        BlockPos.Mutable pos = new BlockPos.Mutable();
//
//        for (int xo = 0; xo < 16; ++xo) {
//            final int x = xo + startX;
//            pos.setX(xo);
//
//            for (int zo = 0; zo < 16; ++zo) {
//                final int z = zo + startZ;
//                final int dist = x * x + z * z;
//                pos.setZ(zo);
//
//                int height = this.terrain.getHeight(x, z);
//                int lowerBound = this.terrain.getLowerGenBound(x, z, height);
//                boolean ash = this.surfaceNoise.sample(x * 0.09, z * 0.09, true) > 0 && (height - lowerBound) < 3;
//
//                if (height - lowerBound == 1) {
//                    lowerBound--;
//                }
//
//                for (int y = lowerBound; y < world.getHeight(); ++y) {
//                    pos.setY(y);
//
//                    if (y < height) {
//                        BlockState state;
//
//                        // TODO move this logic to the surface builder, or some other property
//                        if (dist > FraiyaTerrainGenerator.SHARDLANDS_ISLANDS_START) {
//                            if (ash && y > lowerBound) {
//                                state = LintBlocks.ASH.getDefaultState();
//                            } else {
//                                state = LintBlocks.ASPHALT.getDefaultState();
//                            }
//                        } else {
//                            state = LintBlocks.FUSED_STONE.getDefaultState();
//                        }
//
//                        chunk.setBlockState(pos, state, false);
//                    } else if (y < seaLevel) {
//                        chunk.setBlockState(pos, Blocks.WATER.getDefaultState(), false);
//                    } else {
//                        chunk.setBlockState(pos, Blocks.AIR.getDefaultState(), false);
//                    }
//                }
//            }
//        }
//    }
	
	public int getLowerGenBound(int x, int z) {
		int height = this.terrain.getHeight(x, z);
		return this.terrain.getLowerGenBound(x, z, height);
	}
	
	@Override
	public int getHeight(int x, int z, Heightmap.Type heightmapType, HeightLimitView world) {
		int height = this.terrain.getHeight(x, z);
		return heightmapType.getBlockPredicate().test(Blocks.WATER.getDefaultState()) ? Math.max(height, this.getSeaLevel()) : height;
	}
	
	@Override
	public VerticalBlockSample getColumnSample(int x, int z, HeightLimitView world) {
		BlockState[] column = new BlockState[256];
		final int seaLevel = this.getSeaLevel();
		final int height = this.terrain.getHeight(x, z);
		
		for (int y = 0; y < 256; ++y) {
			if (y < height) {
				column[y] = LintBlocks.FUSED_STONE.getDefaultState();
			} else if (y < seaLevel) {
				column[y] = Blocks.WATER.getDefaultState();
			} else {
				column[y] = Blocks.AIR.getDefaultState();
			}
		}
		
		return new VerticalBlockSample(seaLevel, column);
	}
	
	@Override
	public int getSeaLevel() {
		return FraiyaTerrainGenerator.SEA_LEVEL;
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
		
		for (int xo = 0; xo < 16; ++xo) {
			int x = startX + xo;
			
			for (int zo = 0; zo < 16; ++zo) {
				int z = startZ + zo;
				int height = chunk.sampleHeightmap(Heightmap.Type.WORLD_SURFACE_WG, xo, zo) + 1;
				double noise = this.surfaceNoise.sample((double) x * 0.0625D, (double) z * 0.0625D, 0.0625D, (double) xo * 0.0625D) * 15.0D;
				
				region.getBiome(mutable.set(startX + xo, height, startZ + zo)).buildSurface(rand, chunk, x, z, height, noise,
						LintBlocks.FUSED_STONE.getDefaultState(), Blocks.WATER.getDefaultState(), this.getSeaLevel(),  this.withSeed(this., region.getSeed());
				
				// bedrock
				if (x * x + z * z < FraiyaTerrainGenerator.SHARDLANDS_FADE_START) {
					mutable.setY(0);
					chunk.setBlockState(mutable, Blocks.BEDROCK.getDefaultState(), false);
				}
			}
		}
		
	}
	
	@Override
	public StructureManager getStructureManager() {
		return this.structureManager;
	}
	
	public Vec2i[] getTownCentres() {
		return this.villageCentres.toArray(new Vec2i[4]);
	}
	
	@Override
	public void generateFeatures(ChunkRegion region, StructureAccessor accessor) {
		// prepare structures
		this.structureManager.prepareChunkForPopulation(region, this.seed, region.getCenterChunkX(), region.getCenterChunkZ());
		
		// generate features
		super.generateFeatures(region, accessor);
		
		int centreChunkX = region.getCenterChunkX();
		int centreChunkZ = region.getCenterChunkZ();
		int startX = centreChunkX * 16;
		int startZ = centreChunkZ * 16;
		
		// generate floating islands
		ChunkRandom genRand = new ChunkRandom();
		genRand.setTerrainSeed(centreChunkX, centreChunkZ);
		
		// TODO turn this into "terrain decorators" like tree decorators but off brand
		if (this.floatingIslands != null) {
			if (this.floatingIslands.generate(region, genRand, startX, startZ)) { // Only run vegetal decoration if floating island is in chunk
				Biome biome = this.populationSource.getBiomeForNoiseGen((centreChunkX << 2) + 2, 2, (centreChunkZ << 2) + 2);
				BlockPos startPos = new BlockPos(startX, 0, startZ);
				ChunkRandom rand = new ChunkRandom();
				rand.setPopulationSeed(region.getSeed() + 1, startX, startZ);
				
				List<List<Supplier<ConfiguredFeature<?, ?>>>> list = biome.getGenerationSettings().getFeatures();
				this.postVegetalPlacement(list, region, startPos, rand);
			}
		}
	}
	
	private void postVegetalPlacement(List<List<Supplier<ConfiguredFeature<?, ?>>>> list, ChunkRegion region, BlockPos pos, ChunkRandom random) {
		int n = GenerationStep.Feature.VEGETAL_DECORATION.ordinal();
		
		if (list.size() > n) {
			List<Supplier<ConfiguredFeature<?, ?>>> toGenerate = new ArrayList<Supplier<ConfiguredFeature<?, ?>>>(list.get(n));
			toGenerate.add(0, () -> Features.FLOATING_ISLAND_ALLOS_CRYSTAL);
			
			for (Supplier<ConfiguredFeature<?, ?>> supplier : toGenerate) {
				try {
					ConfiguredFeature<?, ?> configured = supplier.get();
					
					try {
						configured.generate(region, this, random, pos);
					} catch (Exception var22) {
						CrashReport report = CrashReport.create(var22, "Feature placement");
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
