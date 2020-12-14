package io.github.hydos.lint.world.feature;

import java.util.Random;

import io.github.hydos.lint.resource.block.LintBlocks;
import io.github.hydos.lint.util.OpenSimplexNoise;
import io.github.hydos.lint.util.Voronoi;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.StructureWorldAccess;

public class FloatingIslandModifier {
	public FloatingIslandModifier(long seed) {
		this.noise = new OpenSimplexNoise(new Random(seed + 3));
		// int seed
		int protoSeed = (int) (seed >> 32);
		this.seed = protoSeed == 0 ? 1 : protoSeed; // 0 bad and worst in game
	}

	private final OpenSimplexNoise noise;
	private final int seed;

	public void generate(StructureWorldAccess world, Random random, int startX, int startZ) {
		BlockPos.Mutable pos = new BlockPos.Mutable();

		for (int xo = 0; xo < 16; ++xo) {
			int x = xo + startX;
			pos.setX(x);

			for (int zo = 0; zo < 16; ++zo) {
				int z = zo + startZ;
				double floatingIsland = Voronoi.sampleFloating(x * 0.04, z * 0.04, this.seed, this.noise);

				if (floatingIsland > 0.0) {
					pos.setZ(z);
					double height = 150 + 10 * this.noise.sample(x * 0.02, z * 0.02);
					double depth = height - 25 * (0.5 + Math.abs(this.noise.sample(5 + x * 0.012, z * 0.012))) * floatingIsland;

					int iHeight = (int) height;
					int iDepth = (int) depth;

					if (iDepth < iHeight) {
						for (int y = iDepth; y < iHeight; ++y) {
							pos.setY(y);
							world.setBlockState(pos, STONE, 3);
						}
					}
				}
			}
		}
	}

	private static final BlockState STONE = Blocks.STONE.getDefaultState();
	private static final BlockState FUSED_STONE = LintBlocks.FUSED_STONE.getDefaultState();
}
