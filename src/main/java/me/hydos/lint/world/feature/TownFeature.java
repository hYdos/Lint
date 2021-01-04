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

import java.util.Random;

import me.hydos.lint.block.LintBlocks;
import net.minecraft.block.Blocks;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.Heightmap;
import net.minecraft.world.StructureWorldAccess;
import net.minecraft.world.gen.chunk.ChunkGenerator;
import net.minecraft.world.gen.feature.DefaultFeatureConfig;
import net.minecraft.world.gen.feature.Feature;

public class TownFeature extends Feature<DefaultFeatureConfig> {
	public TownFeature() {
		super(DefaultFeatureConfig.CODEC);
	}

	@Override
	public boolean generate(StructureWorldAccess world, ChunkGenerator chunkGenerator, Random random, BlockPos pos, DefaultFeatureConfig config) {
		this.generateHouse(world, pos, random);
		return true;
	}

	private void generateHouse(StructureWorldAccess world, BlockPos start, Random random) {
		int width = random.nextInt(5) + 7;
		int breadth = random.nextInt(5) + 7;
		int floor = start.getY();
		int startX = start.getX() - (width/2);
		int startZ = start.getZ() - (breadth/2);
		final int seaLevel = world.getSeaLevel();
		final int houseHeight = random.nextInt(3) + 4;

		BlockPos.Mutable pos = new BlockPos.Mutable();

		for (int xo = 0; xo <= width; ++xo) {
			final int x = startX + xo;
			pos.setX(x);

			for (int zo = 0; zo <= breadth; ++zo) {
				final int z = startZ + zo;
				pos.setZ(z);

				int height = world.getChunk(pos).sampleHeightmap(Heightmap.Type.OCEAN_FLOOR_WG, x, z);
				boolean xedg = (xo == 0 || xo == width - 1);
				boolean zedg = (zo == 0 || zo == breadth - 1);
				boolean corner = xedg && zedg;
				boolean edge = xedg || zedg;

				boolean innerXedg = (xo == 1 || xo == width - 2);
				boolean innerZedg = (zo == 1 || zo == breadth - 2);
				boolean innerEdge = innerXedg || innerZedg;

				// platform
				if (height < seaLevel) {
					if (corner) { // corners
						for (int y = height; y < floor; ++y) {
							pos.setY(y);
							this.setBlockState(world, pos, LintBlocks.MYSTICAL_LOG.getDefaultState());
						}
					}
				} else if (height < floor && !edge) {
					for (int y = height; y < floor; ++y) {
						pos.setY(y);
						this.setBlockState(world, pos, LintBlocks.RICH_DIRT.getDefaultState());
					}
				}

				// floor and roof
				if (!edge || height < seaLevel) {
					pos.setY(floor);
					this.setBlockState(world, pos, LintBlocks.MYSTICAL_PLANKS.getDefaultState());
					int distX = (width / 2) - MathHelper.abs(xo - (width / 2));
					int distZ = (breadth / 2) - MathHelper.abs(zo - (breadth / 2));
					int dist = Math.max(distX, distZ);

					if (dist > 0) {
						for (int yo = 0; yo < dist; ++yo) {
							pos.setY(floor + houseHeight + yo);
							// TODO directional stair placing by checking the non-abs distX vs non-abs distZ
							this.setBlockState(world, pos, Blocks.STONE_BRICKS.getDefaultState());
						}
					}
				}

				/*
				// pillars
				if (corner) {
					final int pillarHeight = houseHeight + 1;

					for (int yo = 0; yo < pillarHeight; ++yo) {
						pos.setY(floor + yo);
						this.setBlockState(world, pos, LintBlocks.MYSTICAL_LOG.getDefaultState());
					}
				}

				// walls
				if (innerEdge) {
					for (int yo = 1; yo < houseHeight; ++yo) {
						pos.setY(floor + yo);
						this.setBlockState(world, pos, LintBlocks.MYSTICAL_PLANKS.getDefaultState());
					}
				}*/
			}
		}
	}
}
