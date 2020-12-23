package io.github.hydos.lint.world.gen;

import static io.github.hydos.lint.block.LintBlocks.*;

import java.util.Iterator;
import java.util.List;
import java.util.Objects;
import java.util.Random;
import java.util.function.Supplier;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;

import io.github.hydos.lint.block.LintBlocks;
import io.github.hydos.lint.callback.ServerChunkManagerCallback;
import io.github.hydos.lint.util.DoubleGridOperator;
import io.github.hydos.lint.util.LossyDoubleCache;
import io.github.hydos.lint.util.OpenSimplexNoise;
import io.github.hydos.lint.world.biome.Biomes;
import io.github.hydos.lint.world.biome.HaykamBiomeSource;
import io.github.hydos.lint.world.feature.FloatingIslandModifier;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.crash.CrashException;
import net.minecraft.util.crash.CrashReport;
import net.minecraft.util.math.BlockPos;
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
	private final Random random = new Random();
	private final long seed;
	private final Registry<Biome> biomeRegistry;

	private OpenSimplexNoise continentNoise;
	private OpenSimplexNoise mountainsNoise;
	private OpenSimplexNoise hillsNoise;
	private OpenSimplexNoise scaleNoise;
	private OpenSimplexNoise cliffsNoise;
	private OpenSimplexNoise riverNoise;
	private OpenSimplexNoise terrainDeterminerNoise;

	private DoubleGridOperator continentOperator;
	private DoubleGridOperator scaleOperator;

	private OctaveHaykamNoiseSampler beachNoise;
	private OctaveHaykamNoiseSampler surfaceNoise;
	private double[] sandSample = new double[256];
	private double[] gravelSample = new double[256];
	private double[] stoneNoise = new double[256];
	private FloatingIslandModifier floatingIslands;

	public HaykamChunkGenerator(Long seed, Registry<Biome> registry) {
		super(new HaykamBiomeSource(registry, seed), new StructuresConfig(false));
		this.seed = seed;
		this.biomeRegistry = registry;

		ServerChunkManagerCallback.EVENT.register(manager -> {
			long worldSeed = ((ServerWorld) manager.getWorld()).getSeed();
			Random rand = new Random(seed);
			this.continentNoise = new OpenSimplexNoise(rand);
			this.mountainsNoise = new OpenSimplexNoise(rand);
			this.hillsNoise = new OpenSimplexNoise(rand);
			this.scaleNoise = new OpenSimplexNoise(rand);
			this.cliffsNoise = new OpenSimplexNoise(rand);
			this.riverNoise = new OpenSimplexNoise(rand);
			this.terrainDeterminerNoise = new OpenSimplexNoise(rand);

			this.beachNoise = new OctaveHaykamNoiseSampler(rand, 4);
			this.surfaceNoise = new OctaveHaykamNoiseSampler(rand, 4);
			this.floatingIslands = new FloatingIslandModifier(worldSeed);

			this.continentOperator = new LossyDoubleCache(512, (x, z) -> Math.min(30, 30 * this.continentNoise.sample(x * 0.001, z * 0.001)
					+ 18 * Math.max(0, (1.0 - 0.004 * manhattan(x, z, 0, 0))))); // make sure area around 0,0 is higher, but does not go higher than continent noise should go);
			this.scaleOperator = new LossyDoubleCache(512, (x, z) -> {
				double continent = Math.max(0, this.continentOperator.get(x, z)); // min 0
				double scale = (0.5 * this.scaleNoise.sample(x * 0.003, z * 0.003)) + 1.0; // 0 - 1
				scale = 30 * scale + continent; // continent 0-30, scaleNoise 0-30. Overall, 0-60.

				return scale; // should be range 0-60 on return
			});
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
				pos.setZ(zo);

				int height = getHeight(x, z);

				for (int y = 0; y < world.getHeight(); ++y) {
					pos.setY(y);

					if (y < height) {
						chunk.setBlockState(pos, LintBlocks.FUSED_STONE.getDefaultState(), false);
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
		int height = getHeight(x, z);
		return heightmapType.getBlockPredicate().test(Blocks.WATER.getDefaultState()) ? Math.max(height, this.getSeaLevel()) : height;
	}

	private int getHeight(int x, int z) {
		return riverMod(x, z, this.getBaseHeight(x, z));
	}

	private int riverMod(int x, int z, int height) {
		double riverNoise = this.riverNoise.sample(x * 0.003, z * 0.003);

		/*if (riverNoise > -0.12 && riverNoise < 0.12) {
			int riverHeight = this.getSeaLevel() - 3 - (int) (3 * this.sampleHillsNoise(x, z));

			// 1 / 0.12 = 8.333...
			height = (int) MathHelper.lerp(MathHelper.perlinFade((Math.abs(riverNoise) * 8.333333)), riverHeight, height);
		}*/

		return height;
	}

	private int getBaseHeight(int x, int z) {
		double continent = 3 + 1.2 * this.continentOperator.get(x, z);

		double typeScale = 0.0;
		double heightScale = 0.0;
		int count = 0;

		for (int xo = -SCALE_SMOOTH_RADIUS; xo <= SCALE_SMOOTH_RADIUS; ++xo) {
			int sx = x + xo; // shifted/sample x

			for (int zo = -SCALE_SMOOTH_RADIUS; zo <= SCALE_SMOOTH_RADIUS; ++zo) {
				int sz = z + zo; // shifted/sample z
				double current = this.scaleOperator.get(sx, sz);
				typeScale += current;

				current = applyScaleModifiers(sx, sz, current);

				heightScale += current;
				++count;
			}
		}

		typeScale /= count;
		heightScale /= count;

		if (typeScale > 40) {
			double mountains = this.sampleMountainsNoise(x, z);

			if (mountains < 0) {
				heightScale /= 2;
			}

			return AVG_HEIGHT + (int) (continent + heightScale * mountains);
		} else if (typeScale < 35) {
			double hills = this.sampleHillsNoise(x, z);

			if (hills < 0) {
				heightScale /= 2;
			}

			return AVG_HEIGHT + (int) (continent + heightScale * hills);
		} else { // fade region from mountains to hills
			double mountainsScale = (typeScale - 35) * 0.2 * heightScale; // 35 -> 0. 40 -> full scale.
			double hillsScale = (40 - typeScale) * 0.2 * heightScale; // 40 -> 0. 35 -> full scale.

			double mountains = this.sampleMountainsNoise(x, z);
			double hills = this.sampleHillsNoise(x, z);

			if (mountains < 0) {
				mountainsScale /= 2;
			}	

			if (hills < 0) {
				hillsScale /= 2;
			}

			mountains = mountainsScale * mountains;
			hills = hillsScale * hills;

			return AVG_HEIGHT + (int) (continent + mountains + hills);
		}
	}

	private double applyScaleModifiers(int x, int z, double scale) {
		if (scale > 30 && this.terrainDeterminerNoise.sample(x * 0.0041, z * 0.0041) > 0.325) { // approx 240 blocks period
			scale -= 35;
			scale = Math.max(0, scale);
		}

		return scale;
	}

	private double sampleHillsNoise(int x, int z) {
		double sample1 = 0.67 * this.hillsNoise.sample(x * 0.0105, z * 0.0105); // period: ~95
		double sample2 = 0.33 * this.hillsNoise.sample(x * 0.025, z * 0.025); // period: 40
		return sample1 + sample2;
	}

	private double sampleMountainsNoise(int x, int z) {
		double bias = this.terrainDeterminerNoise.sample(1 + 0.001 * x, 0.001 * z) * 0.25;
		double sample1;

		if (bias > 0) {
			sample1 = this.mountainsNoise.sample(0.004 * x * (1.0 + bias), 0.004 * z);
		} else {
			sample1 = this.mountainsNoise.sample(0.004 * x, 0.004 * z * (1.0 - bias));
		}

		sample1 = 0.75 - 1.5 * Math.abs(sample1); // ridged +/-0.75
		double sample2 = 0.25 - 0.5 * Math.abs(this.mountainsNoise.sample(0.0076 * x, 1 + 0.0076 * z)); // ridged +/- 0.25
		return sample1 + sample2;
	}

	@Override
	public void buildSurface(ChunkRegion region, Chunk chunk) {
		random.setSeed(this.seed);

		BlockPos.Mutable pos = new BlockPos.Mutable();

		int chunkX = chunk.getPos().x;
		int chunkZ = chunk.getPos().z;

		final int seaLevel = this.getSeaLevel();
		final double oneSixteenth = 0.03125D;

		this.sandSample = this.beachNoise.sample(this.sandSample, chunkX * 16, chunkZ * 16, 0.0D, 16, 16, 1, oneSixteenth, oneSixteenth, 1.0D);
		this.gravelSample = this.beachNoise.sample(this.gravelSample, chunkZ * 16, 109.0134D, chunkX * 16, 16, 1, 16, oneSixteenth, 1.0D, oneSixteenth);
		this.stoneNoise = this.surfaceNoise.sample(this.stoneNoise, chunkX * 16, chunkZ * 16, 0.0D, 16, 16, 1, oneSixteenth * 2.0D, oneSixteenth * 2.0D, oneSixteenth * 2.0D);

		int chunkStartX = chunk.getPos().getStartX();
		int chunkStartZ = chunk.getPos().getStartZ();
		BlockPos.Mutable biomePos = new BlockPos.Mutable();
		for (int x = 0; x < 16; x++) {
			pos.setX(x);
			biomePos.setX(x + chunkStartX);
			for (int z = 0; z < 16; z++) {
				pos.setZ(z);
				biomePos.setZ(z + chunkStartZ);

				Biome biome = region.getBiome(biomePos);
				BlockState grass = LIVELY_GRASS.getDefaultState();
				BlockState dirt = RICH_DIRT.getDefaultState();
				BlockState sand = MYSTICAL_SAND.getDefaultState();
				BlockState gravel = WHITE_SAND.getDefaultState();

				if (Objects.equals(biomeRegistry.getId(biome), Biomes.CORRUPT_FOREST_KEY.getValue())) {
					grass = CORRUPT_GRASS.getDefaultState();
					dirt = RICH_DIRT.getDefaultState();
					sand = CORRUPT_SAND.getDefaultState();
					gravel = WHITE_SAND.getDefaultState();
				}

				boolean sandSampleAtPos = this.sandSample[(x * 16 + z)] + random.nextDouble() * 0.2D > 0.0D;
				boolean gravelSampleAtPos = this.gravelSample[(x + z * 16)] + random.nextDouble() * 0.2D > 3.0D;
				int stoneSampleAtPos = (int) (this.stoneNoise[(x + z * 16)] / 3.0D + 3.0D + random.nextDouble() * 0.25D);
				int run = -1;
				BlockState topState = grass;
				BlockState underState = dirt;

				for (int y = 255; y >= 0; --y) {
					pos.setY(y);
					if (y <= random.nextInt(6) - 1) {
						chunk.setBlockState(new BlockPos(x, y, z), Blocks.BEDROCK.getDefaultState(), false);
					} else if (y == 0){
						chunk.setBlockState(new BlockPos(x, y, z), Blocks.BEDROCK.getDefaultState(), false);
					} else {
						Block currentBlock = chunk.getBlockState(pos).getBlock();

						if (currentBlock == Blocks.AIR) {
							run = -1;
						} else if (currentBlock == FUSED_STONE) {
							if (run == -1) {
								if (stoneSampleAtPos <= 0) {
									topState = Blocks.AIR.getDefaultState();
									underState = FUSED_STONE.getDefaultState();
								} else if ((y >= seaLevel - 4) && (y <= seaLevel + 1)) {
									topState = grass;
									underState = dirt;

									if (gravelSampleAtPos) {
										topState = Blocks.AIR.getDefaultState();
										underState = gravel;
									}

									if (sandSampleAtPos) {
										topState = sand;
										underState = sand;
									}
								}

								if ((y < seaLevel) && topState.isAir()) {
									topState = Blocks.WATER.getDefaultState();
								}

								run = stoneSampleAtPos;
								if (y >= seaLevel - 1) {
									chunk.setBlockState(new BlockPos(x, y, z), topState, false);
								} else {
									chunk.setBlockState(new BlockPos(x, y, z), underState, false);
								}
							} else if (run > 0) {
								run--;
								chunk.setBlockState(new BlockPos(x, y, z), underState, false);
							}
						}
					}
				}
			}
		}
	}

	@Override
	public BlockView getColumnSample(int x, int z) {
		BlockState[] column = new BlockState[256];
		final int seaLevel = this.getSeaLevel();
		final int height = this.getHeight(x, z);

		for (int y = 0; y < 256; ++y) {
			if (y < height) {
				column[y] = LintBlocks.FUSED_STONE.getDefaultState();
			} else if (y < seaLevel) {
				column[y] = Blocks.WATER.getDefaultState();
			} else {
				column[y] = Blocks.AIR.getDefaultState();
			}
		}

		return new VerticalBlockSample(column);
	}

	private static double manhattan(double x, double y, double x1, double y1) {
		double dx = Math.abs(x1 - x);
		double dy = Math.abs(y1 - y);
		return dx + dy;
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
		this.floatingIslands.generate(region, genRand, startX, startZ);

		Biome biome = this.populationSource.getBiomeForNoiseGen((centreChunkX << 2) + 2, 2, (centreChunkZ << 2) + 2);
		BlockPos startPos = new BlockPos(startX, 0, startZ);
		ChunkRandom rand = new ChunkRandom();
		rand.setPopulationSeed(region.getSeed() + 1, startX, startZ);

		List<List<Supplier<ConfiguredFeature<?, ?>>>> list = biome.getGenerationSettings().getFeatures();
		this.postVegetalPlacement(list, region, startPos, rand);
	}

	private <T extends List<R>, R extends Supplier<ConfiguredFeature<?, ?>>> void postVegetalPlacement(List<T> list, ChunkRegion region, BlockPos pos, ChunkRandom random) {
		// get iterator
		Iterator<R> var23 = ((List<R>)list.get(GenerationStep.Feature.VEGETAL_DECORATION.ordinal())).iterator();

		// loop over stuff
		while (var23.hasNext()) {
			R supplier = var23.next();
			try {
				ConfiguredFeature<?, ?> configured = (ConfiguredFeature<?, ?>) supplier.get();

				try {
					configured.generate(region, this, random, pos);
				} catch (Exception var22) {
					CrashReport report = CrashReport.create(var22, "Feature placement");
					report.addElement("Feature").add("Id", (Object)Registry.FEATURE.getId(configured.feature)).add("Config", (Object)configured.config).add("Description", () -> {
						return configured.feature.toString();
					});
					throw new CrashException(report);
				}
			} catch (ClassCastException e) {
				// no
			}
		}
	}

	private static final int AVG_HEIGHT = 65;
	private static final int SCALE_SMOOTH_RADIUS = 15;
}
