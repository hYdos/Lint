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

package me.hydos.lint.mixinimpl;

import me.hydos.lint.block.LintBlocks;
import me.hydos.lint.util.GridDirection;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.tag.BlockTags;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.explosion.Explosion;

public class LintPortal {
	public static final BlockState FRAME = Blocks.COAL_BLOCK.getDefaultState();

	public static void resolve(World world, BlockPos startPos, boolean destroy) {
		boolean blowUp = false;
		if (world.getRegistryKey() != World.OVERWORLD) {
			blowUp = true;
		}

		final int size = 3;
		final int searchSize = 4;

		final int startX = startPos.getX();
		final int startZ = startPos.getZ();
		final int startY = startPos.getY();

		int[] data = new int[4];
		boolean portal = test(world, startPos, searchSize, size, data);
		BlockPos.Mutable pos = new BlockPos.Mutable().set(startPos);
		pos.setY(startY);

		// place portal
		if (destroy) {
			if (!portal) {
				recursivelyBreakPortal(world, startPos, LintBlocks.HAYKAMIUM_PORTAL.getDefaultState(), 0);
			}
		} else if (portal) {
			int widthP = data[0];
			int widthN = data[1];
			int breadthP = data[2];
			int breadthN = data[3];

			if (blowUp) {
				if (!world.isClient()) {
					world.createExplosion(null, startX, startY, startZ, 10, Explosion.DestructionType.DESTROY);
				}
				return;
			}
			for (int xo = -widthN; xo <= widthP; ++xo) {
				pos.setX(startX + xo);

				for (int zo = -breadthN; zo <= breadthP; ++zo) {
					pos.setZ(startZ + zo);

					world.setBlockState(pos, LintBlocks.HAYKAMIUM_PORTAL.getDefaultState(), 2);
				}
			}
		}
	}

	private static void recursivelyBreakPortal(World world, BlockPos currentPos, BlockState state, int i) {
		world.syncWorldEvent(2001, currentPos, Block.getRawIdFromState(state));
		world.setBlockState(currentPos, AIR, 2);

		BlockPos[] positions = new BlockPos[4];
		BlockState[] states = new BlockState[4];

		for (int j = 0; j < 4; ++j) {
			GridDirection direction = GridDirection.values()[j];
			BlockPos pos = currentPos.offset(direction.direction);
			BlockState nextState = world.getBlockState(pos);

			if (nextState.getBlock() == LintBlocks.HAYKAMIUM_PORTAL) {
				positions[j] = pos;
				states[j] = nextState;
			}
		}

		for (int j = 0; j < 4; ++j) {
			BlockPos pos = positions[j];

			if (pos != null) {
				if (i < 4) {
					recursivelyBreakPortal(world, pos, states[j], i + 1);
				}
			}
		}
	}

	private static boolean test(World world, BlockPos firePos, final int searchSize, final int size, int[] data) {
		final int fireX = firePos.getX();
		final int fireZ = firePos.getZ();
		BlockPos.Mutable pos = new BlockPos.Mutable().set(firePos);
		int widthP = 0; // the amount of "gap" on +x
		int widthN = 0; // the amount of "gap" on -x
		int breadthP = 0; // the amount of "gap" on +z
		int breadthN = 0; // the amount of "gap" on -z

		// find encapsulation of portal.
		for (int xo = 1; xo <= searchSize; ++xo) {
			if (xo == searchSize) {
				return false;
			}

			pos.setX(fireX + xo);

			if (world.getBlockState(pos) == FRAME) {
				break;
			}

			widthP++;
		}

		for (int xo = 1; xo <= searchSize; ++xo) {
			if (xo == searchSize) {
				return false;
			}

			pos.setX(fireX - xo);

			if (world.getBlockState(pos) == FRAME) {
				break;
			}

			widthN++;
		}

		if (1 + widthP + widthN != size) {
			return false;
		}

		pos.setX(fireX);

		for (int zo = 1; zo <= searchSize; ++zo) {
			if (zo == searchSize) {
				return false;
			}

			pos.setZ(fireZ + zo);

			if (world.getBlockState(pos) == FRAME) {
				break;
			}

			breadthP++;
		}

		for (int zo = 1; zo <= searchSize; ++zo) {
			if (zo == searchSize) {
				return false;
			}

			pos.setZ(fireZ - zo);

			if (world.getBlockState(pos) == FRAME) {
				break;
			}

			breadthN++;
		}

		if (1 + breadthP + breadthN != size) {
			return false;
		}

		// Check Edges
		pos.setZ(fireZ + breadthP + 1);

		for (int xo = -widthN; xo <= widthP; ++xo) {
			pos.setX(fireX + xo);

			if (world.getBlockState(pos) != FRAME) {
				return false;
			}
		}

		pos.setZ(fireZ - breadthN - 1);

		for (int xo = -widthN; xo <= widthP; ++xo) {
			pos.setX(fireX + xo);

			if (world.getBlockState(pos) != FRAME) {
				return false;
			}
		}

		pos.setX(fireX + widthP + 1);

		for (int zo = -breadthN; zo <= breadthP; ++zo) {
			pos.setZ(fireZ + zo);

			if (world.getBlockState(pos) != FRAME) {
				return false;
			}
		}

		pos.setX(fireX - widthN - 1);

		for (int zo = -breadthN; zo <= breadthP; ++zo) {
			pos.setZ(fireZ + zo);

			if (world.getBlockState(pos) != FRAME) {
				return false;
			}
		}

		// check air space for portal
		for (int xo = -widthN; xo <= widthP; ++xo) {
			pos.setX(fireX + xo);

			for (int zo = -breadthN; zo <= breadthP; ++zo) {
				pos.setZ(fireZ + zo);

				if (xo != 0 || zo != 0) {
					if (!world.getBlockState(pos).isAir()) {
						return false;
					}
				}
			}
		}

		final int fireY = pos.getY();
		pos.setY(fireY - 1);

		// test floor is logs
		for (int xo = -widthN; xo <= widthP; ++xo) {
			pos.setX(fireX + xo);

			for (int zo = -breadthN; zo <= breadthP; ++zo) {
				pos.setZ(fireZ + zo);

				if (!BlockTags.LOGS.contains(world.getBlockState(pos).getBlock())) {
					return false;
				}
			}
		}

		data[0] = widthP;
		data[1] = widthN;
		data[2] = breadthP;
		data[3] = breadthN;
		return true;
	}

	private static final BlockState AIR = Blocks.AIR.getDefaultState();
}
