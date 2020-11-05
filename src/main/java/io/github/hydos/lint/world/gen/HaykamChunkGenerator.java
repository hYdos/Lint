package io.github.hydos.lint.world.gen;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import io.github.hydos.lint.callback.ServerChunkManagerCallback;
import io.github.hydos.lint.world.biome.Biomes;
import io.github.hydos.lint.world.biome.HaykamBiomeSource;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.registry.Registry;
import net.minecraft.util.registry.RegistryLookupCodec;
import net.minecraft.world.BlockView;
import net.minecraft.world.ChunkRegion;
import net.minecraft.world.Heightmap;
import net.minecraft.world.WorldAccess;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.chunk.Chunk;
import net.minecraft.world.gen.StructureAccessor;
import net.minecraft.world.gen.chunk.ChunkGenerator;
import net.minecraft.world.gen.chunk.StructuresConfig;

import java.util.Objects;
import java.util.Random;

import static io.github.hydos.lint.resource.block.Blocks.*;

public class HaykamChunkGenerator extends ChunkGenerator {

    public static final Codec<HaykamChunkGenerator> CODEC = RecordCodecBuilder.create((instance) -> instance.group(
            Codec.LONG.fieldOf("seed").stable().forGetter((generator) -> generator.seed),
            RegistryLookupCodec.of(Registry.BIOME_KEY).forGetter(haykamChunkGenerator -> haykamChunkGenerator.biomeRegistry)
    ).apply(instance, instance.stable(HaykamChunkGenerator::new)));
    private final Random random = new Random();
    private final long seed;
    private final Registry<Biome> biomeRegistry;
    public OctaveHaykamNoiseSampler noise6;
    public OctaveHaykamNoiseSampler noise7;
    public OctaveHaykamNoiseSampler treeNoise;
    private OctaveHaykamNoiseSampler noise1;
    private OctaveHaykamNoiseSampler noise2;
    private OctaveHaykamNoiseSampler noise3;
    private OctaveHaykamNoiseSampler beachNoise;
    private OctaveHaykamNoiseSampler surfaceNoise;
    private double[] heightNoise;
    private double[] noiseArray1, noiseArray2, noiseArray3, noiseArray4, noiseArray5;
    private double[] sandSample = new double[256];
    private double[] gravelSample = new double[256];
    private double[] stoneNoise = new double[256];

    public HaykamChunkGenerator(Long seed, Registry<Biome> registry) {
        super(new HaykamBiomeSource(registry, seed), new StructuresConfig(false));
        this.seed = seed;
        this.biomeRegistry = registry;

        ServerChunkManagerCallback.EVENT.register(manager -> {
            Random rand = new Random(((ServerWorld) manager.getWorld()).getSeed());
            noise1 = new OctaveHaykamNoiseSampler(rand, 16);
            noise2 = new OctaveHaykamNoiseSampler(rand, 16);
            noise3 = new OctaveHaykamNoiseSampler(rand, 8);
            beachNoise = new OctaveHaykamNoiseSampler(rand, 4);
            surfaceNoise = new OctaveHaykamNoiseSampler(rand, 4);
            noise6 = new OctaveHaykamNoiseSampler(rand, 10);
            noise7 = new OctaveHaykamNoiseSampler(rand, 16);
            treeNoise = new OctaveHaykamNoiseSampler(rand, 8);
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

                boolean sandSampleAtPos = this.sandSample[(x + z * 16)] + random.nextDouble() * 0.2D > 0.0D;
                boolean gravelSampleAtPos = this.gravelSample[(x + z * 16)] + random.nextDouble() * 0.2D > 3.0D;
                int stoneSampleAtPos = (int) (this.stoneNoise[(x + z * 16)] / 3.0D + 3.0D + random.nextDouble() * 0.25D);
                int run = -1;
                BlockState topState = grass;
                BlockState underState = dirt;

                for (int y = 256; y >= 128; --y) {
                    pos.setY(y);
                    chunk.setBlockState(pos, Blocks.AIR.getDefaultState(), false);
                }
                for (int y = 127; y >= 0; --y) {
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
    public void populateNoise(WorldAccess world, StructureAccessor accessor, Chunk chunk) {
        int chunkX = chunk.getPos().x;
        int chunkZ = chunk.getPos().z;
        final double oneEighth = 0.125D;
        final double oneQuarter = 0.25D;
        this.heightNoise = this.generateOctaves(this.heightNoise, chunkX * 4, chunkZ * 4);

        BlockPos.Mutable posMutable = new BlockPos.Mutable();

        for (int xSubChunk = 0; xSubChunk < 4; ++xSubChunk) {
            for (int zSubChunk = 0; zSubChunk < 4; ++zSubChunk) {
                for (int ySubChunk = 0; ySubChunk < 16; ++ySubChunk) {

                    double sampleNWLow = this.heightNoise[(xSubChunk * 5 + zSubChunk) * 17 + ySubChunk];
                    double sampleSWLow = this.heightNoise[((xSubChunk) * 5 + zSubChunk + 1) * 17 + ySubChunk];
                    double sampleNELow = this.heightNoise[((xSubChunk + 1) * 5 + zSubChunk) * 17 + ySubChunk];
                    double sampleSELow = this.heightNoise[((xSubChunk + 1) * 5 + zSubChunk + 1) * 17 + ySubChunk];

                    double sampleNWHigh = (this.heightNoise[((xSubChunk) * 5 + zSubChunk) * 17 + ySubChunk + 1] - sampleNWLow) * oneEighth;
                    double sampleSWHigh = (this.heightNoise[((xSubChunk) * 5 + zSubChunk + 1) * 17 + ySubChunk + 1] - sampleSWLow) * oneEighth;
                    double sampleNEHigh = (this.heightNoise[((xSubChunk + 1) * 5 + zSubChunk) * 17 + ySubChunk + 1] - sampleNELow) * oneEighth;
                    double sampleSEHigh = (this.heightNoise[((xSubChunk + 1) * 5 + zSubChunk + 1) * 17 + ySubChunk + 1] - sampleSELow) * oneEighth;

                    for (int localY = 0; localY < 8; ++localY) {
                        int y = ySubChunk * 8 + localY;
                        posMutable.setY(y);

                        double sampleNWInitial = sampleNWLow;
                        double sampleSWInitial = sampleSWLow;
                        double sampleNAverage = (sampleNELow - sampleNWLow) * oneQuarter;
                        double sampleSAverage = (sampleSELow - sampleSWLow) * oneQuarter;

                        for (int localX = 0; localX < 4; ++localX) {
                            posMutable.setX(localX + xSubChunk * 4);

                            double sample = sampleNWInitial;
                            double progress = (sampleSWInitial - sampleNWInitial) * oneQuarter;

                            for (int localZ = 0; localZ < 4; ++localZ) {
                                posMutable.setZ(zSubChunk * 4 + localZ);

                                BlockState toSet = Blocks.AIR.getDefaultState();

                                if (y < this.getSeaLevel()) {
                                    toSet = Blocks.WATER.getDefaultState();
                                }
                                if (sample > 0.0D) {
                                    toSet = FUSED_STONE.getDefaultState();
                                }

                                chunk.setBlockState(posMutable, toSet, false);
                                sample += progress;
                            }

                            sampleNWInitial += sampleNAverage;
                            sampleSWInitial += sampleSAverage;
                        }

                        sampleNWLow += sampleNWHigh;
                        sampleSWLow += sampleSWHigh;
                        sampleNELow += sampleNEHigh;
                        sampleSELow += sampleSEHigh;
                    }
                }
            }
        }
    }

    @Override
    public int getHeight(int x, int z, Heightmap.Type heightmapType) {
        int chunkX = (x >> 4);
        int chunkZ = (z >> 4);
        final double oneEighth = 0.125D;
        final double oneQuarter = 0.25D;

        this.heightNoise = this.generateOctaves(this.heightNoise, chunkX * 4, chunkZ * 4);

        int xSubChunk = (x >> 2) & 0b11;
        int zSubChunk = (z >> 2) & 0b11;

        int maxGroundY = 0;

        final int actualLocalX = x - (chunkX << 4) - (xSubChunk << 2);
        final int actualLocalZ = z - (chunkZ << 4) - (zSubChunk << 2);

        for (int ySubChunk = 0; ySubChunk < 16; ++ySubChunk) {
            double sampleNWLow = this.heightNoise[(xSubChunk * 5 + zSubChunk) * 17 + ySubChunk];
            double sampleSWLow = this.heightNoise[((xSubChunk) * 5 + zSubChunk + 1) * 17 + ySubChunk];
            double sampleNELow = this.heightNoise[((xSubChunk + 1) * 5 + zSubChunk) * 17 + ySubChunk];
            double sampleSELow = this.heightNoise[((xSubChunk + 1) * 5 + zSubChunk + 1) * 17 + ySubChunk];

            double sampleNWHigh = (this.heightNoise[((xSubChunk) * 5 + zSubChunk) * 17 + ySubChunk + 1] - sampleNWLow) * oneEighth;
            double sampleSWHigh = (this.heightNoise[((xSubChunk) * 5 + zSubChunk + 1) * 17 + ySubChunk + 1] - sampleSWLow) * oneEighth;
            double sampleNEHigh = (this.heightNoise[((xSubChunk + 1) * 5 + zSubChunk) * 17 + ySubChunk + 1] - sampleNELow) * oneEighth;
            double sampleSEHigh = (this.heightNoise[((xSubChunk + 1) * 5 + zSubChunk + 1) * 17 + ySubChunk + 1] - sampleSELow) * oneEighth;

            for (int localY = 0; localY < 8; ++localY) {
                int y = ySubChunk * 8 + localY;

                double sampleNWInitial = sampleNWLow;
                double sampleSWInitial = sampleSWLow;
                double sampleNAverage = (sampleNELow - sampleNWLow) * oneQuarter;
                double sampleSAverage = (sampleSELow - sampleSWLow) * oneQuarter;

                xloop:
                for (int localX = 0; localX < 4; ++localX) {

                    double someValueToDoWithSettingStone = sampleNWInitial;
                    double someOffsetThing = (sampleSWInitial - sampleNWInitial) * oneQuarter;

                    for (int localZ = 0; localZ < 4; ++localZ) {

                        if (someValueToDoWithSettingStone > 0.0D) {
                            maxGroundY = y;
                        }

                        someValueToDoWithSettingStone += someOffsetThing;

                        if (actualLocalZ == localZ && actualLocalX == localX) {
                            break xloop;
                        }
                    }

                    sampleNWInitial += sampleNAverage;
                    sampleSWInitial += sampleSAverage;
                }

                sampleNWLow += sampleNWHigh;
                sampleSWLow += sampleSWHigh;
                sampleNELow += sampleNEHigh;
                sampleSELow += sampleSEHigh;
            }
        }

        return maxGroundY + 1;
    }

    @Override
    public BlockView getColumnSample(int x, int z) {
        return null;
    }


    private double[] generateOctaves(double[] oldArray, int x, int z) {
        if (oldArray == null) {
            oldArray = new double[5 * 17 * 5];
        }

        final double const1 = 684.412D;
        final double const2 = 684.412D;
        this.noiseArray4 = this.noise6.sample(this.noiseArray4, x, 0, z, 5, 1, 5, 1.0D, 0.0D, 1.0D);
        this.noiseArray5 = this.noise7.sample(this.noiseArray5, x, 0, z, 5, 1, 5, 100.0D, 0.0D, 100.0D);
        this.noiseArray3 = this.noise3.sample(this.noiseArray3, x, 0, z, 5, 17, 5, const1 / 80.0D, const2 / 160.0D, const1 / 80.0D);
        this.noiseArray1 = this.noise1.sample(this.noiseArray1, x, 0, z, 5, 17, 5, const1, const2, const1);
        this.noiseArray2 = this.noise2.sample(this.noiseArray2, x, 0, z, 5, 17, 5, const1, const2, const1);

        int index0 = 0;
        int index1 = 0;

        for (int localX = 0; localX < 5; ++localX) {
            for (int localZ = 0; localZ < 5; ++localZ) {
                Biome noiseBiome = this.biomeSource.getBiomeForNoiseGen(localX, 0, localZ);
                double double0 = (this.noiseArray4[index1] + 256.0D) / 512.0D;
                if (double0 > 1.0D) {
                    double0 = 1.0D;
                }

                double double2 = 0.0D;
                double clamp = this.noiseArray5[index1] / 8000.0D;
                if (clamp < 0.0D) {
                    clamp = -clamp;
                }

                clamp = clamp * 3.0D - 3.0D;
                if (clamp < 0.0D) {
                    clamp = clamp / 2.0D;
                    if (clamp < -1.0D) {
                        clamp = -1.0D;
                    }

                    clamp = clamp / 1.4D;
                    clamp = clamp / 2.0D;
                    double0 = 0.0D;
                } else {
                    if (clamp > 1.0D) {
                        clamp = 1.0D;
                    }

                    clamp = clamp / 6.0D;
                }

                double0 = double0 + 0.5D;
                clamp = clamp * (double) 17 / 16.0D;
                double double4 = (double) 17 / 2.0D + clamp * 4.0D;
                ++index1;

                for (int localY = 0; localY < 17; ++localY) {
                    double double1;
                    double double5 = ((double) localY - double4) * 12.0D / double0;
                    if (double5 < 0.0D) {
                        double5 *= 4.0D;
                    }

                    double sample0 = this.noiseArray1[index0] / 512.0D;
                    double sample1 = this.noiseArray2[index0] / 512.0D;
                    double sample2 = (this.noiseArray3[index0] / 10.0D + 1.0D) / 2.0D;
                    if (sample2 < 0.0D) {
                        double1 = sample0;
                    } else if (sample2 > 1.0D) {
                        double1 = sample1;
                    } else {
                        double1 = sample0 + (sample1 - sample0) * sample2;
                    }

                    double1 = double1 - double5;
                    if (localY > 17 - 4) {
                        double double6 = (float) (localY - (17 - 4)) / 3.0F;
                        double1 = double1 * (1.0D - double6) + -10.0D * double6;
                    }

                    if ((double) localY < double2) {
                        double double7 = (double2 - (double) localY) / 4.0D;
                        if (double7 < 0.0D) {
                            double7 = 0.0D;
                        }

                        if (double7 > 1.0D) {
                            double7 = 1.0D;
                        }

                        double1 = double1 * (1.0D - double7) + -10.0D * double7;
                    }

                    oldArray[index0] = double1 * noiseBiome.getScale() * 10000;
                    ++index0;
                }
            }
        }

        return oldArray;
    }
}
