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
import me.hydos.lint.refactord.block.LintBlocks2;
import me.hydos.lint.util.GridDirection;
import me.hydos.lint.util.math.Vec2i;
import me.hydos.lint.world.gen.terrain.TerrainChunkGenerator;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.DoorBlock;
import net.minecraft.block.StairsBlock;
import net.minecraft.block.WallTorchBlock;
import net.minecraft.block.enums.DoubleBlockHalf;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.ChunkPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.Heightmap;
import net.minecraft.world.StructureWorldAccess;
import net.minecraft.world.gen.chunk.ChunkGenerator;
import net.minecraft.world.gen.feature.DefaultFeatureConfig;
import net.minecraft.world.gen.feature.Feature;

// N: Theria
// E: Paweria
// S: Heria
// W: Auria
// TODO pre-generate all towns in order to properly place persistent NPCs

public class TownFeature extends Feature<DefaultFeatureConfig> {
	public static int DENSE_DIST = 80 * 80;
	public static int SUBURB_DIST = 127 * 127;
	public static int RURAL_DIST = 180 * 180;
	private static final int OUTSKIRTS_DIST = 320 * 320;

	private static final TownBlocks HERIA = new TownBlocks()
			.floor(LintBlocks2.MYSTICAL_PLANKS.getDefaultState())
			.walls(LintBlocks2.MYSTICAL_PLANKS.getDefaultState(), LintBlocks.MYSTICAL_LOG.getDefaultState())
			.roof(Blocks.STONE_BRICKS.getDefaultState(), Blocks.STONE_BRICK_STAIRS.getDefaultState(), Blocks.STONE_BRICK_SLAB.getDefaultState());

	private static final TownBlocks AURIA = new TownBlocks()
			.floor(Blocks.SMOOTH_STONE.getDefaultState())
			.walls(Blocks.BRICKS.getDefaultState(), LintBlocks.CORRUPT_LOG.getDefaultState())
			.roof(Blocks.STONE_BRICKS.getDefaultState(), Blocks.STONE_BRICK_STAIRS.getDefaultState(), Blocks.STONE_BRICK_SLAB.getDefaultState());

	private static final TownBlocks PAWERIA_CENTRE = new TownBlocks()
			.floor(LintBlocks2.MYSTICAL_PLANKS.getDefaultState())
			.walls(LintBlocks2.CORRUPT_PLANKS.getDefaultState(), LintBlocks.STRIPPED_CORRUPT_LOG.getDefaultState())
			.roof(Blocks.STONE_BRICKS.getDefaultState(), Blocks.STONE_BRICK_STAIRS.getDefaultState(), Blocks.STONE_BRICK_SLAB.getDefaultState());

	private static final TownBlocks THERIA = new TownBlocks()
			.floor(LintBlocks2.MYSTICAL_PLANKS.getDefaultState())
			.walls(Blocks.COBBLESTONE.getDefaultState(), LintBlocks.MYSTICAL_LOG.getDefaultState())
			.roof(Blocks.STONE_BRICKS.getDefaultState(), Blocks.STONE_BRICK_STAIRS.getDefaultState(), Blocks.STONE_BRICK_SLAB.getDefaultState());

	private static final TownBlocks[] BLOCK_SETS = new TownBlocks[] {PAWERIA_CENTRE, HERIA, AURIA, THERIA};

	public TownFeature() {
		super(DefaultFeatureConfig.CODEC);
	}

	@Override
	public boolean generate(StructureWorldAccess world, ChunkGenerator chunkGenerator, Random random, BlockPos pos, DefaultFeatureConfig config) {
		if (chunkGenerator instanceof TerrainChunkGenerator) {
			int mindist = Integer.MAX_VALUE;
			Vec2i closest = null;
			int x = pos.getX();
			int z = pos.getZ();
			int blockSet = 0;
			int i = 0;

			for (Vec2i loc : ((TerrainChunkGenerator) chunkGenerator).getTownCentres()) {
				int dist = loc.squaredDist(x, z);

				if (mindist > dist) {
					mindist = dist;
					closest = loc;
					blockSet = i;
				}
				
				++i;
			}

			TownBlocks blocks = BLOCK_SETS[blockSet];

			if (mindist < DENSE_DIST) {
				if (new ChunkPos(pos).equals(closest.chunkPos())) {
					Features.RETURN_PORTAL.generate(world, random, pos, true);
				} else {
					this.generateHouse(world, pos.add(random.nextInt(3), 0, random.nextInt(3)), random, false, blocks);
				}
			} else if (mindist < SUBURB_DIST) {
				if (random.nextInt(4) == 0) {
					this.generateHouse(world, pos.add(random.nextInt(16), 0, random.nextInt(16)), random, false, blocks == PAWERIA_CENTRE ? HERIA : blocks);
				}
			} else if (mindist < RURAL_DIST) {
				if (random.nextInt(10) == 0) {
					this.generateHouse(world, pos.add(random.nextInt(16), 0, random.nextInt(16)), random, false, blocks == PAWERIA_CENTRE ? HERIA : blocks);
				}
			} else if (mindist < OUTSKIRTS_DIST) {
				if (random.nextInt(27) == 0) {
					this.generateHouse(world, pos.add(random.nextInt(16), 0, random.nextInt(16)), random, true, blocks == PAWERIA_CENTRE ? HERIA : blocks);
				}
			}
		}

		return true;
	}

	private void generateHouse(StructureWorldAccess world, BlockPos start, Random random, boolean outskirts, TownBlocks blocks) {
		int width = (random.nextInt(3) + 4) * 2 - 1;
		int breadth = (random.nextInt(3) + 4) * 2 - 1;
		int floor = world.getChunk(start).sampleHeightmap(Heightmap.Type.WORLD_SURFACE_WG, start.getX(), start.getZ());
		int startX = start.getX() - (width / 2);
		int startZ = start.getZ() - (breadth / 2);
		final int seaLevel = world.getSeaLevel();
		final int houseHeight = random.nextInt(3) + 4;
		final GridDirection door = GridDirection.random(random);

		BlockPos.Mutable pos = new BlockPos.Mutable();

		// platform
		if (floor < seaLevel) {
			if (outskirts) { // no underwater outskirt stuff
				return;
			}

			final int w2 = width + 5;
			final int b2 = breadth + 5;

			for (int xo = -5; xo < w2; ++xo) {
				final int x = startX + xo;
				pos.setX(x);

				for (int zo = -5; zo < b2; ++zo) {
					final int z = startZ + zo;
					pos.setZ(z);

					boolean xedg = (xo == -5 || xo == width + 4);
					boolean zedg = (zo == -5 || zo == breadth + 4);
					boolean corner = xedg && zedg;
					int height = world.getChunk(pos).sampleHeightmap(Heightmap.Type.OCEAN_FLOOR_WG, x, z);

					if (height < seaLevel - 1) {
						if (corner) { // corners
							for (int y = height; y < floor; ++y) {
								pos.setY(y);
								this.setBlockState(world, pos, blocks.log);
							}
						}

						pos.setY(floor);
						this.setBlockState(world, pos, blocks.floor);
					}
				}
			}
		}

		// house
		for (int xo = 0; xo < width; ++xo) {
			final int x = startX + xo;
			pos.setX(x);

			for (int zo = 0; zo < breadth; ++zo) {
				final int z = startZ + zo;
				pos.setZ(z);

				int height = world.getChunk(pos).sampleHeightmap(Heightmap.Type.OCEAN_FLOOR_WG, x, z);
				boolean xedg = (xo == 0 || xo == width - 1);
				boolean zedg = (zo == 0 || zo == breadth - 1);
				boolean corner = xedg && zedg;
				boolean edge = xedg || zedg;

				boolean innerXedg = (xo == 1 || xo == width - 2);
				boolean innerZedg = (zo == 1 || zo == breadth - 2);
				boolean innerEdge = (innerXedg || innerZedg) && !edge;

				// air go brrrrr
				for (int yo = 1; yo < houseHeight; ++yo) {
					pos.setY(floor + yo);
					this.setBlockState(world, pos, Blocks.AIR.getDefaultState());
				}

				// platform
				if (height < seaLevel) {
					if (corner) { // corners
						for (int y = height; y < floor; ++y) {
							pos.setY(y);
							this.setBlockState(world, pos, blocks.log);
						}
					}
				} else if (height < floor && !edge) {
					for (int y = height; y < floor; ++y) {
						pos.setY(y);
						this.setBlockState(world, pos, LintBlocks.RICH_DIRT.getDefaultState());
					}
				}

				// floor
				if (!edge || height < seaLevel) {
					pos.setY(floor);
					this.setBlockState(world, pos, blocks.floor);
				}

				// roof
				if (!edge) {
					int dxo = xo - (width / 2); // directional xo. negative = lower xo, positive = higher xo.
					int dzo = zo - (breadth / 2);
					int distX = (width / 2) - MathHelper.abs(dxo);
					int distZ = (breadth / 2) - MathHelper.abs(dzo);
					int dist = Math.min(distX, distZ);

					if (dist > 0) {
						for (int yo = 0; yo < dist; ++yo) {
							pos.setY(floor + houseHeight + yo);

							if (yo == dist - 1) {
								if (distX == distZ || (dxo == 0 && dzo == 0)) {
									this.setBlockState(world, pos, blocks.roof_slab);
								} else {
									Direction direction = null;

									if (distX > distZ) {
										if (dzo < 0) {
											direction = Direction.SOUTH; // face opposite direction
										} else {
											direction = Direction.NORTH;
										}
									} else if (dxo < 0) {
										direction = Direction.EAST; // face opposite direction
									} else {
										direction = Direction.WEST;
									}

									this.setBlockState(world, pos, blocks.roof_stair.with(StairsBlock.FACING, direction));
								}
							} else {
								this.setBlockState(world, pos, blocks.roof);
							}
						}
					}
				} else if (!corner) {
					pos.setY(floor + houseHeight - 1);
					this.setBlockState(world, pos, blocks.roof);
				}

				// pillars
				if (corner) {
					final int pillarHeight = houseHeight + 1;

					for (int yo = 0; yo < pillarHeight; ++yo) {
						pos.setY(floor + yo);
						this.setBlockState(world, pos, blocks.log);
					}
				}

				// walls
				if (innerEdge) {
					for (int yo = 1; yo < houseHeight; ++yo) {
						pos.setY(floor + yo);
						this.setBlockState(world, pos, blocks.walls);
					}
				}
			}
		}

		// door
		int x = startX;
		int z = startZ;

		if (door.horizontal) {
			z += breadth / 2; // avg(0, breadth)
			x += door.mirror ? 1 : width - 2;
		} else {
			x += width / 2; // avg(0, width)
			z += door.mirror ? 1 : breadth - 2;
		}

		int y = floor + 1;
		pos.set(x, y, z);

		BlockState state = LintBlocks.MYSTICAL_DOOR.getDefaultState().with(DoorBlock.FACING, door.reverse().direction);
		this.setBlockState(world, pos, state);
		pos.setY(y + 1);
		this.setBlockState(world, pos, state.with(DoorBlock.HALF, DoubleBlockHalf.UPPER));
		state = Blocks.WALL_TORCH.getDefaultState().with(WallTorchBlock.FACING, door.direction);

		// torches
		if (door.horizontal) {
			z += 1;
			x += door.mirror ? -1 : 1;
			pos.set(x, y + 1, z);
			this.setBlockState(world, pos, state);
			z -= 2;
			pos.setZ(z);
			this.setBlockState(world, pos, state);
		} else {
			x += 1;
			z += door.mirror ? -1 : 1;
			pos.set(x, y + 1, z);
			this.setBlockState(world, pos, state);
			x -= 2;
			pos.setX(x);
			this.setBlockState(world, pos, state);
		}

		// glowstone
		x = startX + width / 2;
		z = startZ + breadth / 2;
		y = floor + houseHeight - 1;
		pos.set(x, y, z);
		this.setBlockState(world, pos, Blocks.GLOWSTONE.getDefaultState());
	}

	private static class TownBlocks {
		BlockState floor, walls, roof, roof_stair, roof_slab, log;
		
		public TownBlocks floor(BlockState floor) {
			this.floor = floor;
			return this;
		}
		
		public TownBlocks walls(BlockState walls, BlockState log) {
			this.walls = walls;
			this.log = log;
			return this;
		}
		
		public TownBlocks roof(BlockState normal, BlockState stair, BlockState slab) {
			this.roof = normal;
			this.roof_stair = stair;
			this.roof_slab = slab;
			return this;
		}
	}
}
