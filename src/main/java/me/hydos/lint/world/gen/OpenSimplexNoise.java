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

import java.util.Random;

/**
 * 2D OpenSimplexNoise
 */
public class OpenSimplexNoise extends RawOpenSimplexNoise {
	private final double xOffset, yOffset;

	public OpenSimplexNoise(Random rand) {
		super(rand.nextLong());

		this.xOffset = rand.nextDouble();
		this.yOffset = rand.nextDouble();
	}

	public double sample(double x) {
		return super.sample(x + this.xOffset, 0.0);
	}

	@Override
	public double sample(double x, double y) {
		return super.sample(x + this.xOffset, y + this.yOffset);
	}
}

/**
 * OpenSimplex Noise in Java.
 * (Using implementation by Kurt Spencer, without the 3D and 4D impl)<br/>
 * <br/>
 * v1.1 (October 5, 2014)<br/>
 * - Added 2D and 4D implementations.<br/>
 * - Proper gradient sets for all dimensions, from a
 * dimensionally-generalizable scheme with an actual
 * rhyme and reason behind it.<br/>
 * - Removed default permutation array in favor of
 * default seed.<br/>
 * - Changed seed-based constructor to be independent
 * of any particular randomization library, so results
 * will be the same when ported to other languages.<br/>
 */
class RawOpenSimplexNoise {

	private static final double STRETCH_CONSTANT_2D = -0.211324865405187;    //(1/Math.sqrt(2+1)-1)/2;
	private static final double SQUISH_CONSTANT_2D = 0.366025403784439;      //(Math.sqrt(2+1)-1)/2;

	private static final double NORM_CONSTANT_2D = 47;
	//Gradients for 2D. They approximate the directions to the
	//vertices of an octagon from the center.
	private static final byte[] gradients2D = new byte[]{
			5, 2, 2, 5,
			-5, 2, -2, 5,
			5, -2, 2, -5,
			-5, -2, -2, -5,
	};
	//Gradients for 3D. They approximate the directions to the
	//vertices of a rhombicuboctahedron from the center, skewed so
	//that the triangular and square facets can be inscribed inside
	//circles of the same radius.
	private static final byte[] gradients3D = new byte[]{
			-11, 4, 4, -4, 11, 4, -4, 4, 11,
			11, 4, 4, 4, 11, 4, 4, 4, 11,
			-11, -4, 4, -4, -11, 4, -4, -4, 11,
			11, -4, 4, 4, -11, 4, 4, -4, 11,
			-11, 4, -4, -4, 11, -4, -4, 4, -11,
			11, 4, -4, 4, 11, -4, 4, 4, -11,
			-11, -4, -4, -4, -11, -4, -4, -4, -11,
			11, -4, -4, 4, -11, -4, 4, -4, -11,
	};
	private final short[] perm;

	//Initializes the class using a permutation array generated from a 64-bit seed.
	//Generates a proper permutation (i.e. doesn't merely perform N successive pair swaps on a base array)
	//Uses a simple 64-bit LCG.
	public RawOpenSimplexNoise(long seed) {
		perm = new short[256];
		short[] source = new short[256];
		for (short i = 0; i < 256; i++)
			source[i] = i;
		seed = seed * 6364136223846793005L + 1442695040888963407L;
		seed = seed * 6364136223846793005L + 1442695040888963407L;
		seed = seed * 6364136223846793005L + 1442695040888963407L;
		for (int i = 255; i >= 0; i--) {
			seed = seed * 6364136223846793005L + 1442695040888963407L;
			int r = (int) ((seed + 31) % (i + 1));
			if (r < 0)
				r += (i + 1);
			perm[i] = source[r];
			source[r] = source[i];
		}
	}

	private static int fastFloor(double x) {
		int xi = (int) x;
		return x < xi ? xi - 1 : xi;
	}

	//2D OpenSimplex Noise.
	public double sample(double x, double y) {
		//Place input coordinates onto grid.
		double stretchOffset = (x + y) * STRETCH_CONSTANT_2D;
		double xs = x + stretchOffset;
		double ys = y + stretchOffset;

		//Floor to get grid coordinates of rhombus (stretched square) super-cell origin.
		int xsb = fastFloor(xs);
		int ysb = fastFloor(ys);

		//Skew out to get actual coordinates of rhombus origin. We'll need these later.
		double squishOffset = (xsb + ysb) * SQUISH_CONSTANT_2D;
		double xb = xsb + squishOffset;
		double yb = ysb + squishOffset;

		//Compute grid coordinates relative to rhombus origin.
		double xins = xs - xsb;
		double yins = ys - ysb;

		//Sum those together to get a value that determines which region we're in.
		double inSum = xins + yins;

		//Positions relative to origin point.
		double dx0 = x - xb;
		double dy0 = y - yb;

		//We'll be defining these inside the next block and using them afterwards.
		double dx_ext, dy_ext;
		int xsv_ext, ysv_ext;

		double value = 0;

		//Contribution (1,0)
		double dx1 = dx0 - 1 - SQUISH_CONSTANT_2D;
		double dy1 = dy0 - 0 - SQUISH_CONSTANT_2D;
		double attn1 = 2 - dx1 * dx1 - dy1 * dy1;
		if (attn1 > 0) {
			attn1 *= attn1;
			value += attn1 * attn1 * extrapolate(xsb + 1, ysb, dx1, dy1);
		}

		//Contribution (0,1)
		double dx2 = dx0 - 0 - SQUISH_CONSTANT_2D;
		double dy2 = dy0 - 1 - SQUISH_CONSTANT_2D;
		double attn2 = 2 - dx2 * dx2 - dy2 * dy2;
		if (attn2 > 0) {
			attn2 *= attn2;
			value += attn2 * attn2 * extrapolate(xsb, ysb + 1, dx2, dy2);
		}

		if (inSum <= 1) { //We're inside the triangle (2-Simplex) at (0,0)
			double zins = 1 - inSum;
			if (zins > xins || zins > yins) { //(0,0) is one of the closest two triangular vertices
				if (xins > yins) {
					xsv_ext = xsb + 1;
					ysv_ext = ysb - 1;
					dx_ext = dx0 - 1;
					dy_ext = dy0 + 1;
				} else {
					xsv_ext = xsb - 1;
					ysv_ext = ysb + 1;
					dx_ext = dx0 + 1;
					dy_ext = dy0 - 1;
				}
			} else { //(1,0) and (0,1) are the closest two vertices.
				xsv_ext = xsb + 1;
				ysv_ext = ysb + 1;
				dx_ext = dx0 - 1 - 2 * SQUISH_CONSTANT_2D;
				dy_ext = dy0 - 1 - 2 * SQUISH_CONSTANT_2D;
			}
		} else { //We're inside the triangle (2-Simplex) at (1,1)
			double zins = 2 - inSum;
			if (zins < xins || zins < yins) { //(0,0) is one of the closest two triangular vertices
				if (xins > yins) {
					xsv_ext = xsb + 2;
					ysv_ext = ysb;
					dx_ext = dx0 - 2 - 2 * SQUISH_CONSTANT_2D;
					dy_ext = dy0 + 0 - 2 * SQUISH_CONSTANT_2D;
				} else {
					xsv_ext = xsb;
					ysv_ext = ysb + 2;
					dx_ext = dx0 + 0 - 2 * SQUISH_CONSTANT_2D;
					dy_ext = dy0 - 2 - 2 * SQUISH_CONSTANT_2D;
				}
			} else { //(1,0) and (0,1) are the closest two vertices.
				dx_ext = dx0;
				dy_ext = dy0;
				xsv_ext = xsb;
				ysv_ext = ysb;
			}
			xsb += 1;
			ysb += 1;
			dx0 = dx0 - 1 - 2 * SQUISH_CONSTANT_2D;
			dy0 = dy0 - 1 - 2 * SQUISH_CONSTANT_2D;
		}

		//Contribution (0,0) or (1,1)
		double attn0 = 2 - dx0 * dx0 - dy0 * dy0;
		if (attn0 > 0) {
			attn0 *= attn0;
			value += attn0 * attn0 * extrapolate(xsb, ysb, dx0, dy0);
		}

		//Extra Vertex
		double attn_ext = 2 - dx_ext * dx_ext - dy_ext * dy_ext;
		if (attn_ext > 0) {
			attn_ext *= attn_ext;
			value += attn_ext * attn_ext * extrapolate(xsv_ext, ysv_ext, dx_ext, dy_ext);
		}

		return value / NORM_CONSTANT_2D;
	}

	private double extrapolate(int xsb, int ysb, double dx, double dy) {
		int index = perm[(perm[xsb & 0xFF] + ysb) & 0xFF] & 0x0E;
		return gradients2D[index] * dx
				+ gradients2D[index + 1] * dy;
	}
}