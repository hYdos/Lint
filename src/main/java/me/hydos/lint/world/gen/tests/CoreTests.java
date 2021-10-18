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
import net.minecraft.util.Unit;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.biome.Biome;

public class CoreTests {
	public static void main(String[] args) {
		// load Mth.
		MathHelper.sin(0);

		for (Method m : CoreTests.class.getMethods()) {
			for (Annotation a : m.getAnnotations()) {
				if (a.annotationType().isAssignableFrom(UnitTest.class)) {
					UnitTest test = m.getAnnotation(UnitTest.class);
					int passes = 0;
					int tries = test.times();

					System.out.println("Executing Task: " + m.getName());
					System.out.println("================" + "=".repeat(m.getName().length()));

					try {
						for (int i = 0; i < tries; ++i) {
							if ((Boolean) m.invoke(null)) {
								passes++;
							}
						}

						if (passes == tries) {
							System.out.println("Test " + m.getName() + " passed.");
						} else {
							System.err.println("Test " + m.getName() + " failed (" + passes + "/" + tries + " attempts passed).");
						}
					} catch (Throwable t) {
						t.printStackTrace();
						System.err.println("Test " + m.getName() + " >>CRITICALLY FAILED<<");
					}

					// formatting lol
					System.out.println();
				}
			}
		}
	}

	private static int offset = 0;

	@UnitTest(times = 15)
	public static boolean generateChunk() {
		Random random = new Random(12345); // unit test, set seed
		TerrainGenerator classic = new FraiyaTerrainGenerator(random.nextLong(), random, new Vec2i[] {
				new Vec2i(1000, 0),
				new Vec2i(-1000, 0),
				new Vec2i(0, 1000),
				new Vec2i(0, -1000)
		});
		
		long start = System.nanoTime();

		for (int chunkX = -1 + offset; chunkX <= 1 + offset; ++chunkX) {
			int startX = chunkX << 4;

			for (int chunkZ = -1; chunkZ <= 1; ++chunkZ) {
				int startZ = chunkZ << 4;

				for (int x = 0; x < 16; ++x) {
					for (int z = 0; z < 16; ++z) {
						classic.getHeight(x + startX, z + startZ);
					}
				}
			}
		}

		long elapsed = System.nanoTime() - start;
		boolean success = elapsed < 40000000L;
		boolean warn = elapsed > 20000000L;
		System.out.println((success ? (warn ? "[?] " : "[✓] ") : "[!] " + "Took ") + ((double) elapsed / 1000000000.0) + " seconds to get the heights for 9 chunks (in a 3x3 region).");
		offset += 3;
		return success; // 0.05 second max time!
	}
}
