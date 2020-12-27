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

import me.hydos.lint.block.LintBlocks;
import me.hydos.lint.util.math.Voronoi;
import me.hydos.lint.world.gen.HaykamTerrainGenerator;
import me.hydos.lint.world.gen.OpenSimplexNoise;
import net.minecraft.block.BlockState;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.Heightmap;
import net.minecraft.world.StructureWorldAccess;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.chunk.Chunk;
import net.minecraft.world.gen.surfacebuilder.SurfaceConfig;

import java.util.Random;

public class FloatingIslandModifier {
	public FloatingIslandModifier(long seed) {
		this.noise = new OpenSimplexNoise(new Random(seed + 3));
		// int seed
		int protoSeed = (int) (seed >> 32);
		this.seed = protoSeed == 0 ? 1 : protoSeed; // 0 bad and worst in game
	}

	private final OpenSimplexNoise noise;
	private final int seed;

	public boolean generate(StructureWorldAccess world, Random random, int startX, int startZ) {
		BlockPos.Mutable pos = new BlockPos.Mutable();
		Chunk chunk = world.getChunk(new BlockPos(startX, 0, startZ));
		Heightmap wsWG = chunk.getHeightmap(Heightmap.Type.WORLD_SURFACE_WG);
		Heightmap ofWG = chunk.getHeightmap(Heightmap.Type.OCEAN_FLOOR_WG);
		Heightmap mb = chunk.getHeightmap(Heightmap.Type.MOTION_BLOCKING);
		boolean flag = false;

		for (int xo = 0; xo < 16; ++xo) {
			int x = xo + startX;
			int absx = MathHelper.abs(x);
			pos.setX(x);

			for (int zo = 0; zo < 16; ++zo) {
				int z = zo + startZ;
				int absz = MathHelper.abs(z);
				int dist = absx * absx + absz * absz;

				if (dist > HaykamTerrainGenerator.SHARDLANDS_START) {
					return false; // prevent unneccessary checks since it will all succeed second thing
				}

				double floatingIsland = Voronoi.sampleFloating(x, z , this.seed, this.noise, 0.04);
				double floatingNoise = noise.sample(x * 0.04 * 0.11, z * 0.04 * 0.11);
				floatingIsland = smoothstep(floatingIsland);

				// give a nicer shape
				if (floatingNoise > 0.7) {
					floatingIsland += 0.5 * (floatingNoise - 0.7); // start at 0
				}

				if (floatingIsland > 0.0) {
					pos.setZ(z);
					double height = 150 + 10 * this.noise.sample(x * 0.02, z * 0.02);
					double depth = height - 25 * (0.5 + Math.abs(this.noise.sample(5 + x * 0.012, z * 0.012))) * floatingIsland;

					int iHeight = (int) height;
					int finalY = iHeight - 1;
					int fillY = iHeight - (random.nextBoolean() ? 4 : 5);
					int iDepth = (int) depth;

					if (iDepth < iHeight) {
						flag = true;
						Biome biome = world.getBiome(pos.up(64));// just in case 3d biomes in 1.17
						SurfaceConfig surface = biome.getGenerationSettings().getSurfaceConfig();
						BlockState filler = surface.getUnderMaterial();
						BlockState top = surface.getTopMaterial();

						for (int y = iDepth; y < iHeight; ++y) {
							pos.setY(y);

							if (y == finalY) {
								world.setBlockState(pos, top, 3);
								wsWG.trackUpdate(xo, y, zo, top); // the top block will never be air so always updates
								ofWG.trackUpdate(xo, y, zo, top); // I don't think any top blocks fail the check here either
								mb.trackUpdate(xo, y, zo, top);
							} else if (y > fillY) {
								world.setBlockState(pos, filler, 3);
							} else {
								world.setBlockState(pos, FUSED_STONE, 3);
							}
						}
					}
				}
			}
		}

		return flag;
	}

	private static double smoothstep(double x) {
		x -= 0.5;
		return 0.5 * (1 + x + 4 * (x * x * x));
	}
	private static final BlockState FUSED_STONE = LintBlocks.FUSED_STONE.getDefaultState();
}
