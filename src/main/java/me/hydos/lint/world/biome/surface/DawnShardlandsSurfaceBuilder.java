package me.hydos.lint.world.biome.surface;

import me.hydos.lint.block.Blocks;
import net.minecraft.block.BlockState;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.chunk.Chunk;
import net.minecraft.world.gen.surfacebuilder.SurfaceBuilder;
import net.minecraft.world.gen.surfacebuilder.TernarySurfaceConfig;

import java.util.Random;

public class DawnShardlandsSurfaceBuilder extends SurfaceBuilder<TernarySurfaceConfig> {
	public DawnShardlandsSurfaceBuilder() {
		super(TernarySurfaceConfig.CODEC);
	}

	@Override
	public void generate(Random random, Chunk chunk, Biome biome, int x, int z, int height, double noise, BlockState defaultBlock, BlockState defaultFluid, int seaLevel, long seed, TernarySurfaceConfig surfaceBlocks) {
		defaultBlock = Blocks.ASPHALT.getDefaultState();

		if (noise > 2.3f && noise < 3.0f) {
			SurfaceBuilder.DEFAULT.generate(random, chunk, biome, x, z, height, noise, defaultBlock, defaultFluid, seaLevel, seed, ALLOS_CONFIG);
		} else if (noise < -2.3f && noise > -3.0f) {
			SurfaceBuilder.DEFAULT.generate(random, chunk, biome, x, z, height, noise, defaultBlock, defaultFluid, seaLevel, seed, MANOS_CONFIG);
		} else {
			SurfaceBuilder.DEFAULT.generate(random, chunk, biome, x, z, height, noise, defaultBlock, defaultFluid, seaLevel, seed, surfaceBlocks);
		}
	}

	private static final TernarySurfaceConfig ALLOS_CONFIG = new TernarySurfaceConfig(Blocks.ALLOS_INFUSED_ASPHALT.getDefaultState(), Blocks.ASPHALT.getDefaultState(), Blocks.ASPHALT.getDefaultState());
	private static final TernarySurfaceConfig MANOS_CONFIG = new TernarySurfaceConfig(Blocks.MANOS_INFUSED_ASPHALT.getDefaultState(), Blocks.ASPHALT.getDefaultState(), Blocks.ASPHALT.getDefaultState());
}
