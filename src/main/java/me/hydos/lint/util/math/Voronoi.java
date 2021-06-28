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

package me.hydos.lint.util.math;

import me.hydos.lint.world.gen.FraiyaTerrainGenerator;
import me.hydos.lint.world.gen.OpenSimplexNoise;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec2f;

public final class Voronoi {
	public static Vec2f sampleGrid(int x, int y, int seed) {
		float vx = x + (randomFloat(x, y, seed) + 0.5f) * 0.5f;
		float vy = y + (randomFloat(x, y, seed + 1) + 0.5f) * 0.5f;
		return new Vec2f(vx, vy);
	}

	public static Vec2f sampleBalanced(float x, float y, int seed) {
		final int baseX = MathHelper.floor(x);
		final int baseY = MathHelper.floor(y);
		float rx = 0;
		float ry = 0;
		float rdist = 1000;

		for (int xo = -1; xo <= 1; ++xo) {
			int gridX = baseX + xo;

			for (int yo = -1; yo <= 1; ++yo) {
				int gridY = baseY + yo;

				// ensure more evenly distributed
				float vx = gridX + (randomFloat(gridX, gridY, seed) + 0.5f) * 0.5f;
				float vy = gridY + (randomFloat(gridX, gridY, seed + 1) + 0.5f) * 0.5f;
				float vdist = squaredDist(x, y, vx, vy);

				if (vdist < rdist) {
					rx = vx;
					ry = vy;
					rdist = vdist;
				}
			}
		}

		return new Vec2f(rx, ry);
	}

	public static double sampleFloating(double x, double y, int seed, OpenSimplexNoise noise, double xyscale) {
		x *= xyscale;
		y *= xyscale;

		final int baseX = MathHelper.floor(x);
		final int baseY = MathHelper.floor(y);
		double rdist = 1000;

		for (int xo = -1; xo <= 1; ++xo) {
			int gridX = baseX + xo;
			double uGridX = Math.abs(gridX / xyscale);

			for (int yo = -1; yo <= 1; ++yo) {
				int gridY = baseY + yo;
				double uGridY = Math.abs(gridY / xyscale);
				double uDist = uGridY * uGridY + uGridX * uGridX;

				if (uDist < FraiyaTerrainGenerator.TERRAIN_CROB_DISTANCE) {
					// if a floating island point
					double noiseSample = noise.sample(gridX * 0.11, gridY * 0.11);
					if (noiseSample * 2 + 0.25 * random(gridX + 32, gridY, seed, 1) > 1.4) {
						double vx = gridX + randomFloat(gridX, gridY, seed);
						double vy = gridY + randomFloat(gridX, gridY, seed + 1);
						double vdist = squaredDist(x, y, vx, vy);

						if (vdist < rdist) {
							rdist = vdist;
						}
					}
				}
			}
		}

		return Math.max(0.0, 1.0 - rdist);
	}

	public static double sampleTerrace(double x, double y, int seed, DoublePreciseGridOperator values, double noiseScale) {
		final int baseX = MathHelper.floor(x);
		final int baseY = MathHelper.floor(y);
		double rdist = 1000;
		double sample = 0.0;

		for (int xo = -1; xo <= 1; ++xo) {
			int gridX = baseX + xo;

			for (int yo = -1; yo <= 1; ++yo) {
				int gridY = baseY + yo;

				double vx = gridX + randomFloat(gridX, gridY, seed);
				double vy = gridY + randomFloat(gridX, gridY, seed + 1);
				double vdist = squaredDist(x, y, vx, vy);

				if (vdist < rdist) {
					sample = values.get(vx * noiseScale, vy * noiseScale);
					rdist = vdist;
				}
			}
		}

		return sample;
	}

	public static int random(int x, int y, int seed, int mask) {
		seed *= 375462423 * seed + 672456235;
		seed += x;
		seed *= 375462423 * seed + 672456235;
		seed += y;
		seed *= 375462423 * seed + 672456235;
		return seed & mask;
	}

	private static float squaredDist(float x0, float y0, float x1, float y1) {
		float dx = Math.abs(x1 - x0);
		float dy = Math.abs(y1 - y0);
		return dx * dx + dy * dy;
	}

	private static double squaredDist(double x0, double y0, double x1, double y1) {
		double dx = Math.abs(x1 - x0);
		double dy = Math.abs(y1 - y0);
		return dx * dx + dy * dy;
	}

	private static float randomFloat(int x, int y, int seed) {
		return (float) random(x, y, seed, 0xFFFF) / (float) 0xFFFF;
	}
}
