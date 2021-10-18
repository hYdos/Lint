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

package me.hydos.lint.world.gen.tests;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.Random;

import me.hydos.lint.util.math.Vec2i;
import me.hydos.lint.world.gen.FraiyaTerrainGenerator;
import me.hydos.lint.world.gen.terrain.TerrainGenerator;
import net.minecraft.util.math.MathHelper;

public class CoreTests {
	public static void main(String[] args) {
		// load Mth.
		MathHelper.sin(0);

		for (Method m : CoreTests.class.getMethods()) {
			for (Annotation a : m.getAnnotations()) {
				if (a.annotationType().isAssignableFrom(UnitTest.class)) {
					try {
						if ((Boolean) m.invoke(null)) {
							System.out.println("Test " + m.getName() + " Passed.");
						} else {
							System.err.println("Test " + m.getName() + " Failed.");
						}
					} catch (Throwable t) {
						t.printStackTrace();
						System.err.println("Test " + m.getName() + " >>CRITICALLY FAILED<<");
					}
				}
			}
		}
	}

	@UnitTest
	public static boolean generateChunk() {
		Random random = new Random();
		TerrainGenerator classic = new FraiyaTerrainGenerator(random.nextLong(), random, new Vec2i[] {
				new Vec2i(1000, 0),
				new Vec2i(-1000, 0),
				new Vec2i(0, 1000),
				new Vec2i(0, -1000)
		});
		
		long start = System.nanoTime();

		for (int x = 0; x < 16; ++x) {
			for (int z = 0; z < 16; ++z) {
				classic.getHeight(x, z);
			}
		}

		long elapsed = System.nanoTime() - start;
		System.out.println("Took " + ((double) elapsed / 1000000000.0) + " seconds to get the heights for 1 chunk.");
		return elapsed < 500000000L; // 0.5 second max time!
	}
}
