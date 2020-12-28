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

import me.hydos.lint.util.LossyDoubleCache;
import me.hydos.lint.util.LossyIntCache;
import me.hydos.lint.util.math.DoubleGridOperator;
import me.hydos.lint.util.math.IntGridOperator;
import me.hydos.lint.util.math.Voronoi;
import me.hydos.lint.world.biome.TerrainData;
import net.minecraft.util.math.MathHelper;

import java.util.Random;

public class HaykamTerrainGenerator implements TerrainData {
	public static final int SEA_LEVEL = 63;

	public static final int ASH_START = 2320 * 2320;
	public static final int DENSE_ASH_START = 2360 * 2360;
	public static final int SHARDLANDS_FADE_START = 2400 * 2400;
	public static final int TERRAIN_CROB_DISTANCE = 2450 * 2450;
	public static final int SHARDLANDS_EDGE_START = 2460 * 2460;
	public static final int SHARDLANDS_START = 2500 * 2500;
	public static final int SHARDLANDS_ISLANDS_START = 2550 * 2550;
	public static final int SHARDLANDS_ISLANDS_FADE_END = 2590 * 2590;

	private static final int AVG_HEIGHT = 65;
	private static final int AVG_FLOAT_HEIGHT = 85;
	private static final int SCALE_SMOOTH_RADIUS = 9;

	private final int seed;
	private final OpenSimplexNoise continentNoise;
	private final OpenSimplexNoise mountainsNoise;
	private final OpenSimplexNoise hillsNoise;
	private final OpenSimplexNoise scaleNoise;
	private final OpenSimplexNoise cliffsNoise;
	private final OpenSimplexNoise riverNoise;
	private final OpenSimplexNoise terrainDeterminerNoise;
	private final DoubleGridOperator continentOperator;
	private final DoubleGridOperator typeScaleOperator;

	//private static final double TERRACE_RESCALE = 1 / 0.6;
	private final DoubleGridOperator terrainScaleOperator;
	private final IntGridOperator baseHeightOperator;
	private final IntGridOperator heightOperator;
	private final IntGridOperator terraceModOperator;

	HaykamTerrainGenerator(long seed, Random rand) {
		int protoSeed = (int) (seed >> 32);
		this.seed = protoSeed == 0 ? 1 : protoSeed; // 0 bad and worst in game

		this.continentNoise = new OpenSimplexNoise(rand);
		this.mountainsNoise = new OpenSimplexNoise(rand);
		this.hillsNoise = new OpenSimplexNoise(rand);
		this.scaleNoise = new OpenSimplexNoise(rand);
		this.cliffsNoise = new OpenSimplexNoise(rand);
		this.riverNoise = new OpenSimplexNoise(rand);
		this.terrainDeterminerNoise = new OpenSimplexNoise(rand);

		this.continentOperator = new LossyDoubleCache(1024, (x, z) -> Math.min(30, 30 * this.continentNoise.sample(x * 0.001, z * 0.001)
				+ 18 * Math.max(0, (1.0 - 0.004 * manhattan(x, z, 0, 0))))); // make sure area around 0,0 is higher, but does not go higher than continent noise should go);
		this.typeScaleOperator = new LossyDoubleCache(1024, (x, z) -> {
			double continent = Math.max(0, this.continentOperator.get(x, z)); // min 0
			double scale = (0.5 * this.scaleNoise.sample(x * 0.003, z * 0.003)) + 1.0; // 0 - 1
			scale = 30 * scale + continent; // continent 0-30, scaleNoise 0-30. Overall, 0-60.

			// accelerate scale at higher regions
			if (scale > 40) {
				scale = map(scale, 40, 60, 40, 80);
			}

			return scale; // should be range 0-80 on return
		});
		this.terrainScaleOperator = new LossyDoubleCache(1024, (x, z) -> this.addMountainPlateaus(x, z, this.addTerrainCrobber(x, z, this.typeScaleOperator.get(x, z))));
		this.baseHeightOperator = new LossyIntCache(512, (x, z) -> {
			double continent = 3 + 1.2 * this.continentOperator.get(x, z);

			double typeScale = 0.0;
			double heightScale = 0.0;
			int count = 0;

			for (int xo = -SCALE_SMOOTH_RADIUS; xo <= SCALE_SMOOTH_RADIUS; ++xo) {
				int sx = x + xo; // shifted/sample x

				for (int zo = -SCALE_SMOOTH_RADIUS; zo <= SCALE_SMOOTH_RADIUS; ++zo) {
					int sz = z + zo; // shifted/sample z
					typeScale += this.typeScaleOperator.get(sx, sz);
					heightScale += this.terrainScaleOperator.get(sx, sz);
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
		});

		this.heightOperator = new LossyIntCache(512, (x, z) -> {
			int sqrDist = x * x + z * z;

			if (sqrDist < TERRAIN_CROB_DISTANCE) {
				int baseHeight = this.sampleBaseHeight(x, z);
				int height = riverMod(x, z, terraceMod(x, z, baseHeight, baseHeight));
				return height;
			} else if (sqrDist > SHARDLANDS_ISLANDS_START) {
				return AVG_FLOAT_HEIGHT + (int) (18 * (1 + this.sampleHillsNoise(x * 1.3, z * 1.3)));
			} else if (sqrDist > SHARDLANDS_START) {
				return 0;
			} else {
				double progress = (double) (sqrDist - TERRAIN_CROB_DISTANCE) / (double) (SHARDLANDS_START - TERRAIN_CROB_DISTANCE);
				int finalHeight = AVG_FLOAT_HEIGHT;

				int baseHeight = this.sampleBaseHeight(x, z);
				int height = riverMod(x, z, terraceMod(x, z, baseHeight, baseHeight));
				return (int) MathHelper.lerp(progress, height, finalHeight);
			}
		});

		this.terraceModOperator = new LossyIntCache(512, (x, z) -> {
			double terraceRescaleConstant = 1.0 / 0.52;
			int heightMod = (int) (28 * Voronoi.sampleTerrace(x * 0.034, z * 0.034, this.seed, (sx, sz) -> {
				double cliffsNoise = 1 + this.cliffsNoise.sample(sx, sz);
				double limiter = Math.max(0, terraceRescaleConstant * (this.terrainDeterminerNoise.sample(sx * 0.23, sz * 0.23) - 0.48));
				limiter = Math.max(limiter, 0.2);
				return limiter * cliffsNoise;
			}, 0.3));
			return 3 * (heightMod / 3);
		});
	}

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

	public int getHeight(int x, int z) {
		return this.heightOperator.get(x, z);
	}

	private int riverMod(int x, int z, int height) {
		double riverNoise = this.riverNoise.sample(x * 0.003, z * 0.003);

		if (riverNoise > -0.12 && riverNoise < 0.12) {
			int riverHeight = SEA_LEVEL - 3 - (int) (3 * this.sampleHillsNoise(x, z));

			// 1 / 0.12 = 8.333...
			height = (int) MathHelper.lerp(MathHelper.perlinFade((Math.abs(riverNoise) * 8.333333)), riverHeight, height);
		}

		return height;
	}

	@Override
	public int sampleTerraceMod(int x, int z) {
		return this.terraceModOperator.get(x, z);
	}

	private int terraceMod(int x, int z, int height, int baseHeight) {
		if (this.sampleTypeScale(x, z) < 23.0 && baseHeight > SEA_LEVEL + 2) {
			return height + this.terraceModOperator.get(x, z);
		}

		return height;
	}

	private double addTerrainCrobber(int x, int z, double scale) {
		if (x * x + z * z > TERRAIN_CROB_DISTANCE) {
			return 0;
		}

		return scale;
	}

	private double addMountainPlateaus(int x, int z, double scale) {
		if (scale > 30 && this.terrainDeterminerNoise.sample(x * 0.0041, z * 0.0041) > 0.325) { // approx 240 blocks period
			scale -= 35;
			scale = Math.max(0, scale);
		}

		return scale;
	}

	private double sampleHillsNoise(double x, double z) {
		double sample1 = 0.67 * this.hillsNoise.sample(x * 0.0105, z * 0.0105); // period: ~95
		double sample2 = 0.33 * this.hillsNoise.sample(x * 0.025, z * 0.025); // period: 40
		return sample1 + sample2;
	}

	private double sampleMountainsNoise(int x, int z) {
		double bias = this.terrainDeterminerNoise.sample(1 + 0.001 * x, 0.001 * z) * 0.2;
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
	public double sampleTypeScale(int x, int z) {
		return this.typeScaleOperator.get(x, z);
	}

	@Override
	public double sampleTerrainScale(int x, int z) {
		return this.terrainScaleOperator.get(x, z);
	}

	@Override
	public int sampleBaseHeight(int x, int z) {
		return this.baseHeightOperator.get(x, z);
	}

	public int getLowerGenBound(int x, int z, int height) {
		int sqrDist = x * x + z * z;

		if (sqrDist < SHARDLANDS_FADE_START) {
			return 0;
		} else if (sqrDist > SHARDLANDS_START) {
			if (sqrDist < SHARDLANDS_ISLANDS_START) {
				return 256;
			} else {
				int lowerBound = height - (int) (22 * (-0.36 + this.sampleMountainsNoise(x * 3, z * 3)));

				if (sqrDist > SHARDLANDS_ISLANDS_FADE_END) {
					return lowerBound;
				} else {
					return (int) map(sqrDist, SHARDLANDS_ISLANDS_START, SHARDLANDS_ISLANDS_FADE_END, 200, lowerBound);
				}
			}
		} else {
			return (int) map(sqrDist, SHARDLANDS_FADE_START, SHARDLANDS_START, 0, AVG_FLOAT_HEIGHT + 1);
		}
	}
}
