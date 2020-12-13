package io.github.hydos.lint.util;

import net.minecraft.util.math.MathHelper;

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

	public static double sampleFloating(double x, double y, int seed, OpenSimplexNoise noise) {
		final int baseX = MathHelper.floor(x);
		final int baseY = MathHelper.floor(y);
		double rdist = 1000;

		for (int xo = -1; xo <= 1; ++xo) {
			int gridX = baseX + xo;

			for (int yo = -1; yo <= 1; ++yo) {
				int gridY = baseY + yo;

				// if a floating island point
				if (noise.sample(gridX * 0.2, gridY * 0.2) * 2 + random(gridX + 32, gridY, seed, 1) > 1.0) {
					double vx = gridX + randomFloat(gridX, gridY, seed);
					double vy = gridY + randomFloat(gridX, gridY, seed + 1);
					double vdist = squaredDist(x, y, vx, vy);

					if (vdist < rdist) {
						rdist = vdist;
					}
				}
			}
		}

		return Math.max(0.0, 1.0 - rdist);
	}

	private static int random(int x, int y, int seed, int mask) {
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
