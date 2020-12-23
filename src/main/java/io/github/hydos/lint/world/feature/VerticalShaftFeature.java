package io.github.hydos.lint.world.feature;

import java.util.Random;

import io.github.hydos.lint.util.OpenSimplexNoise;
import io.github.hydos.lint.util.Voronoi;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.StructureWorldAccess;
import net.minecraft.world.gen.chunk.ChunkGenerator;
import net.minecraft.world.gen.feature.DefaultFeatureConfig;
import net.minecraft.world.gen.feature.Feature;

public class VerticalShaftFeature extends Feature<DefaultFeatureConfig> {
	public VerticalShaftFeature() {
		super(DefaultFeatureConfig.CODEC);
	}

	@Override
	public boolean generate(StructureWorldAccess world, ChunkGenerator chunkGenerator, Random random, BlockPos start, DefaultFeatureConfig config) {
		BlockPos.Mutable pos = new BlockPos.Mutable();
		final int startX = start.getX() - 2;
		final int startY = start.getY();
		final int startZ = start.getZ() - 2;

		int depth = 0;

		pos.setX(startX);
		pos.setZ(startZ);

		// Break into caves
		for (int yo = 0; yo < MAX_DEPTH; ++yo) {
			int y = startY - yo;

			if (y < MIN_Y) { // can't go too deep
				return false;
			}

			pos.setY(y);

			if (world.getBlockState(pos) == CAVE_AIR) {
				depth = yo + 1;
				break;
			}
		}

		if (depth == 0) {
			return false;
		}

		final int[] offsets = new int[2];

		final double sampleX = (startX + Voronoi.random(startX, startZ, 127, 0xFF)) * 0.1;
		final double sampleZ = (startZ + Voronoi.random(startX, startZ, 255, 0xFF)) * 0.1;

		final int maxYO = depth - 1; // micro optimisation go brrrrrrrrrrr

		for (int yo = 0; yo < depth; ++yo) {
			final int y = startY - yo;
			pos.setY(y);
			getShift(offsets, sampleX, y, sampleZ);

			final int bound = yo == maxYO ? 3 : 5;
			final int offset = yo == maxYO ? 1 : 0; // keep in centre when changing bound.
			final int localStartX = startX + offsets[0] + offset;
			final int localStartZ = startZ + offsets[1] + offset;

			// Remove circle
			for (int xo = 0; xo <= bound; ++xo) {
				pos.setX(localStartX + xo);

				for (int zo = 0; zo <= bound; ++zo) {
					pos.setZ(localStartZ + zo);

					boolean meetsPredicate = (yo == maxYO) ? true : (xo != 0 && xo != bound) || (zo != 0 && zo != bound); // remove corners, making a circle-like shape

					if (meetsPredicate) {
						this.setBlockState(world, pos, CAVE_AIR);
					}
				}
			}
		}

		return true;
	}

	private static void getShift(int[] offsets, double x, int y, double z) {
		boolean reverseX = SHIFT_CUT.sample(x, y * 0.05) > 0;
		double noiseX = SHIFT.sample(x, y * 0.07);

		boolean reverseZ = SHIFT_CUT.sample(z, y * 0.05) > 0;
		double noiseZ = SHIFT.sample(z, y * 0.07);

		offsets[0] = (int) (3 * (reverseX ? 1.0 - noiseX : noiseX));
		offsets[1] = (int) (3 * (reverseZ ? 1.0 - noiseZ : noiseZ));
	}

	public static final int MAX_DEPTH = 40;
	public static final int MIN_Y = 22;

	private static final OpenSimplexNoise SHIFT = new OpenSimplexNoise(new Random(0));
	private static final OpenSimplexNoise SHIFT_CUT = new OpenSimplexNoise(new Random(1));
	private static final BlockState CAVE_AIR = Blocks.CAVE_AIR.getDefaultState();
}
