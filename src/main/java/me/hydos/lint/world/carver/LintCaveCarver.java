package me.hydos.lint.world.carver;

import com.mojang.serialization.Codec;
import me.hydos.lint.Lint;
import me.hydos.lint.block.Blocks;
import net.minecraft.block.BlockState;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockPos.Mutable;
import net.minecraft.util.math.Direction;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.chunk.Chunk;
import net.minecraft.world.gen.ProbabilityConfig;
import net.minecraft.world.gen.carver.Carver;
import net.minecraft.world.gen.carver.CaveCarver;
import org.apache.commons.lang3.mutable.MutableBoolean;

import java.util.BitSet;
import java.util.Random;
import java.util.function.Function;

public class LintCaveCarver extends CaveCarver {
	public LintCaveCarver(Codec<ProbabilityConfig> codec, int i) {
		super(codec, i);
	}

	@Override
	protected boolean canCarveBlock(BlockState state, BlockState stateAbove) {
		return state.isOf(Blocks.FUSED_STONE) || super.canCarveBlock(state, stateAbove);
	}

	protected float getTunnelSystemWidth(Random random) {
		return 7.0f + random.nextFloat() + random.nextFloat();
	}

	@Override
	protected boolean carveAtPoint(Chunk chunk, Function<BlockPos, Biome> posToBiome, BitSet carvingMask, Random random,
			Mutable pos1, Mutable mutable2, Mutable pos3, int seaLevel, int mainChunkX, int mainChunkZ, int x,
			int z, int relativeX, int y, int relativeZ, MutableBoolean grassCheckerThing) {
		int i = relativeX | relativeZ << 4 | y << 8;
		if (carvingMask.get(i)) {
			return false;
		} else {
			carvingMask.set(i);
			pos1.set(x, y, z);
			BlockState state = chunk.getBlockState(pos1);
			BlockState upState = chunk.getBlockState(mutable2.set(pos1, Direction.UP));
			if (state.isOf(Blocks.CORRUPT_GRASS) || state.isOf(Blocks.LIVELY_GRASS)) {
				grassCheckerThing.setTrue();
			}

			if (!this.canCarveBlock(state, upState)) {
				return false;
			} else {
				if (y < 11) {
					chunk.setBlockState(pos1, LAVA.getBlockState(), false);
				} else {
					chunk.setBlockState(pos1, CAVE_AIR, false);
					if (grassCheckerThing.isTrue()) {
						pos3.set(pos1, Direction.DOWN);
						if (chunk.getBlockState(pos3).isOf(Blocks.RICH_DIRT)) {
							chunk.setBlockState(pos3, posToBiome.apply(pos1).getGenerationSettings().getSurfaceConfig().getTopMaterial(), false);
						}
					}
				}

				return true;
			}
		}
	}

	public static final Carver<ProbabilityConfig> INSTANCE = Registry.register(Registry.CARVER, Lint.id("cave"), new LintCaveCarver(ProbabilityConfig.CODEC, 256));
}
