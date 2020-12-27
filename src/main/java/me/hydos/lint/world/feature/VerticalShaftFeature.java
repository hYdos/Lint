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

package me.hydos.lint.world.feature;

import me.hydos.lint.util.math.Voronoi;
import me.hydos.lint.world.gen.OpenSimplexNoise;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.StructureWorldAccess;
import net.minecraft.world.gen.chunk.ChunkGenerator;
import net.minecraft.world.gen.feature.DefaultFeatureConfig;
import net.minecraft.world.gen.feature.Feature;

import java.util.Random;

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
		pos.setY(startY - 1);

		// no water
		for (int xo = -WATER_SEARCH_RADIUS; xo <= WATER_SEARCH_RADIUS; ++xo) {
			pos.setX(startX + xo);

			for (int zo = -WATER_SEARCH_RADIUS; zo <= WATER_SEARCH_RADIUS; ++zo) {
				pos.setZ(startZ + zo);

				if (world.getBlockState(pos).getBlock() == Blocks.WATER) {
					return false;
				}
			}
		}

		pos.setY(startY);
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
				depth = yo + 2;
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

					boolean meetsPredicate = yo == maxYO || (xo != 0 && xo != bound) || (zo != 0 && zo != bound); // remove corners, making a circle-like shape

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

		reverseX &= Math.abs(noiseX) < 0.7;
		reverseZ &= Math.abs(noiseZ) < 0.7;

		offsets[0] = (int) (3 * (reverseX ? 1.0 - noiseX : noiseX));
		offsets[1] = (int) (3 * (reverseZ ? 1.0 - noiseZ : noiseZ));
	}

	public static final int WATER_SEARCH_RADIUS = 4;
	public static final int MAX_DEPTH = 40;
	public static final int MIN_Y = 22;

	private static final OpenSimplexNoise SHIFT = new OpenSimplexNoise(new Random(0));
	private static final OpenSimplexNoise SHIFT_CUT = new OpenSimplexNoise(new Random(1));
	private static final BlockState CAVE_AIR = Blocks.CAVE_AIR.getDefaultState();
}
