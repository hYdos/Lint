package me.hydos.lint.world.biome.surface;

import java.util.Random;

import me.hydos.lint.block.Blocks;
import me.hydos.lint.world.gen.HaykamTerrainGenerator;
import me.hydos.lint.world.gen.OpenSimplexNoise;
import net.minecraft.block.BlockState;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.chunk.Chunk;
import net.minecraft.world.gen.surfacebuilder.SurfaceBuilder;
import net.minecraft.world.gen.surfacebuilder.TernarySurfaceConfig;

public class OceanSurfaceBuilder extends SurfaceBuilder<TernarySurfaceConfig> {
	public OceanSurfaceBuilder(BlockState sand, BlockState sand2) {
		super(TernarySurfaceConfig.CODEC);
		this.sand = new TernarySurfaceConfig(sand, sand, sand);
		this.sand2 = new TernarySurfaceConfig(Blocks.AIR.getDefaultState(), sand2, sand2);
	}

	private final TernarySurfaceConfig sand;
	private final TernarySurfaceConfig sand2;

	@Override
	public void generate(Random random, Chunk chunk, Biome biome, int x, int z, int height, double noise, BlockState defaultBlock, BlockState defaultFluid, int seaLevel, long seed, TernarySurfaceConfig surfaceBlocks) {
		double limit = HaykamTerrainGenerator.SEA_LEVEL + 1.2 + 1.5 * NOISE.sample(x * 0.022, z * 0.022);
		double limit2 = HaykamTerrainGenerator.SEA_LEVEL + 1.2 + 1.5 * NOISE_2.sample(x * 0.022, z * 0.022);

		if (height < limit) {
			SurfaceBuilder.DEFAULT.generate(random, chunk, biome, x, z, height, noise, defaultBlock, defaultFluid, seaLevel, seed, this.sand);
		} else if (height < limit2) {
			SurfaceBuilder.DEFAULT.generate(random, chunk, biome, x, z, height, noise, defaultBlock, defaultFluid, seaLevel, seed, this.sand2);
		} else {
			SurfaceBuilder.DEFAULT.generate(random, chunk, biome, x, z, height, noise, defaultBlock, defaultFluid, seaLevel, seed, surfaceBlocks);
		}
	}

	private static final OpenSimplexNoise NOISE = new OpenSimplexNoise(new Random(69));
	private static final OpenSimplexNoise NOISE_2 = new OpenSimplexNoise(new Random(420));
}
