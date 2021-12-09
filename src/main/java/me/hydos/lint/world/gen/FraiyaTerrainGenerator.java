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

import me.hydos.lint.block.LintBlocks;
import me.hydos.lint.util.LossyDoubleCache;
import me.hydos.lint.util.math.DoubleGridOperator;
import me.hydos.lint.util.math.Vec2i;
import me.hydos.lint.world.gen.terrain.TerrainGenerator;
import net.minecraft.block.BlockState;
import net.minecraft.util.math.MathHelper;

import java.util.Random;

public class FraiyaTerrainGenerator implements TerrainGenerator {
	public static final int SEA_LEVEL = 63;

	public static final int ASH_START = 3320 * 3320;
	public static final int DENSE_ASH_START = 3360 * 3360;
	public static final int SHARDLANDS_FADE_START = 3400 * 3400;
	public static final int TERRAIN_CROB_DISTANCE = 3450 * 3450;
	public static final int SHARDLANDS_EDGE_START = 3460 * 3460;
	public static final int SHARDLANDS_START = 3500 * 3500;
	public static final int SHARDLANDS_ISLANDS_START = 3550 * 3550;
	public static final int SHARDLANDS_ISLANDS_FADE_END = 3590 * 3590;

	private static final int AVG_HEIGHT = 65;
	private static final int AVG_FLOAT_HEIGHT = 85;
	private static final int SCALE_SMOOTH_RADIUS = 9;

	private final int seed;
	private final OpenSimplexNoise continentNoise;
	private final OpenSimplexNoise mountainsNoise;
	private final OpenSimplexNoise hillsNoise;
	private final OpenSimplexNoise shardlandsModifierNoise;

	private final DoubleGridOperator continentOperator;

	public final Vec2i[] townAreas;

	public FraiyaTerrainGenerator(long seed, Random rand, Vec2i[] townAreas) {
		int protoSeed = (int) (seed >> 32);
		this.seed = protoSeed == 0 ? 1 : protoSeed; // 0 bad and worst in game
		this.townAreas = townAreas;

		this.continentNoise = new OpenSimplexNoise(rand);
		this.mountainsNoise = new OpenSimplexNoise(rand);
		this.hillsNoise = new OpenSimplexNoise(rand);
		this.shardlandsModifierNoise = new OpenSimplexNoise(rand);

		this.continentOperator = new LossyDoubleCache(1024, (x, z) -> AVG_HEIGHT + Math.min(30, 30 * this.continentNoise.sample(x * 0.001, z * 0.001)
				+ 18 * Math.max(0, (1.0 - 0.004 * manhattan(x, z, 0, 0))))); // make sure area around 0,0 is higher, but does not go higher than continent noise should go);
	}

	@Override
	public double sampleBaseHeight(int x, int z) {
		return this.continentOperator.get(x, z);
	}

	@Override
	public int getHeight(int x, int z) {
		int sqrDist = x * x + z * z;

		if (sqrDist < TERRAIN_CROB_DISTANCE) {
			return (int) this.getMainlandHeight(x, z);
		} else if (sqrDist > SHARDLANDS_ISLANDS_START) {
			return AVG_FLOAT_HEIGHT + (int) (18 * (1 + this.sampleShardlandsHills(x * 1.3, z * 1.3)));
		} else if (sqrDist > SHARDLANDS_START) {
			return 0;
		} else {
			double progress = (double) (sqrDist - TERRAIN_CROB_DISTANCE) / (double) (SHARDLANDS_START - TERRAIN_CROB_DISTANCE);
			int finalHeight = AVG_FLOAT_HEIGHT;
			double mainlandHeight = this.getMainlandHeight(x, z);

			return (int) MathHelper.lerp(progress, mainlandHeight, finalHeight);
		}
	}

	private double getMainlandHeight(int x, int z) {
		return this.continentOperator.get(x, z);
	}

	// ===================== SHARDLANDS ===================== //

	private static double manhattan(double x, double y, double x1, double y1) {
		double dx = Math.abs(x1 - x);
		double dy = Math.abs(y1 - y);
		return dx + dy;
	}

	public static double map(double value, double min, double max, double newmin, double newmax) {
		value -= min;
		value /= (max - min);
		return newmin + value * (newmax - newmin);
	}

	private double sampleShardlandsHills(double x, double z) {
		double sample1 = 0.67 * this.hillsNoise.sample(x * 0.0105, z * 0.0105); // period: ~95
		double sample2 = 0.33 * this.hillsNoise.sample(x * 0.025, z * 0.025); // period: 40
		return sample1 + sample2;
	}

	private double sampleShardlandsBase(int x, int z) {
		double bias = this.shardlandsModifierNoise.sample(1 + 0.001 * x, 0.001 * z) * 0.2;
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
	public int getLowerGenBound(int x, int z, int height) {
		int sqrDist = x * x + z * z;

		if (sqrDist < SHARDLANDS_FADE_START) {
			return 0;
		} else if (sqrDist > SHARDLANDS_START) {
			if (sqrDist < SHARDLANDS_ISLANDS_START) {
				return 256;
			} else {
				int lowerBound = height - (int) (22 * (-0.36 + this.sampleShardlandsBase(x * 3, z * 3)));

				if (sqrDist <= SHARDLANDS_ISLANDS_FADE_END) {
					lowerBound = (int) map(sqrDist, SHARDLANDS_ISLANDS_START, SHARDLANDS_ISLANDS_FADE_END, 200, lowerBound);
				}

				if (height - lowerBound == 1) {
					lowerBound--;
				}
				
				return lowerBound;
			}
		} else {
			int lowerBound = (int) map(sqrDist, SHARDLANDS_FADE_START, SHARDLANDS_START, 0, AVG_FLOAT_HEIGHT + 1);
			
			if (height - lowerBound == 1) {
				lowerBound--;
			}

			return lowerBound;
		}
	}

	@Override
	public BlockState getDefaultBlock(int x, int y, int z, int height, int lowerBound, double surfaceNoise) {
		final int dist = x * x + z * z;
		final boolean ash = surfaceNoise > 0 && (height - lowerBound) < 3;

		if (dist > FraiyaTerrainGenerator.SHARDLANDS_ISLANDS_START) {
			if (ash && y > lowerBound) {
				return LintBlocks.ASH.getDefaultState();
			} else {
				return LintBlocks.ASPHALT.getDefaultState();
			}
		} else {
			return LintBlocks.FUSED_STONE.getDefaultState();
		}
	}
}
