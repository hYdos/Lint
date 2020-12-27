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

import java.util.Arrays;
import java.util.Random;

public class OctaveHaykamNoiseSampler {

	private final HaykamNoiseSampler[] generators;
	private final int octaves;

	public OctaveHaykamNoiseSampler(Random rand, int octaveCount) {
		this.octaves = octaveCount;
		this.generators = new HaykamNoiseSampler[octaveCount];
		for (int i = 0; i < octaveCount; ++i) {
			this.generators[i] = new HaykamNoiseSampler(rand);
		}
	}

	public double sample(double x, double y) {
		double double6 = 0.0;
		double double8 = 1.0;
		for (int i = 0; i < this.octaves; ++i) {
			double6 += this.generators[i].sample(x * double8, y * double8) / double8;
			double8 /= 2.0;
		}
		return double6;
	}

	public double[] sample(double[] arrayToReuse, double startX, double startY, double startZ, int xWidth, int yHeight, int zWidth, double xScale, double yScale, double zScale) {
		if (arrayToReuse == null) {
			arrayToReuse = new double[xWidth * yHeight * zWidth];
		} else {
			Arrays.fill(arrayToReuse, 0.0);
		}

		double scale = 1.0;

		for (int j = 0; j < this.octaves; ++j) {
			this.generators[j].sample(arrayToReuse, startX, startY, startZ, xWidth, yHeight, zWidth, xScale * scale, yScale * scale, zScale * scale, scale);
			scale /= 2.0;
		}

		return arrayToReuse;
	}
}
