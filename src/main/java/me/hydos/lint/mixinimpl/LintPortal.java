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
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.tag.BlockTags;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.explosion.Explosion;
import org.jetbrains.annotations.Nullable;

import java.util.function.BiPredicate;

public class LintPortal {
	public static final BlockState FRAME = Blocks.COAL_BLOCK.getDefaultState();

	public static void resolve(World world, BlockPos startPos, @Nullable BlockPos fromPos, boolean destroy) {
		boolean blowUp = world.getRegistryKey() != World.OVERWORLD;

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
				destroyPortalLattice(world, startPos, fromPos, 4);
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

					world.setBlockState(pos, HAYKAMIUM_PORTAL, 2);
				}
			}
		}
	}

	private static void destroyPortalLattice(World world, BlockPos startPos, BlockPos ignorePos, final int searchSize) {
		int[] data = new int[4];

		if (!findLattice(world, startPos, 3, searchSize, data, ($pos, state) -> state != HAYKAMIUM_PORTAL && !$pos.equals(ignorePos))) {
			if (!findLattice(world, startPos, 3, searchSize, data, ($pos, state) -> state != HAYKAMIUM_PORTAL)) {
				return;
			}
		}

		int widthP = data[0];
		int widthN = data[1];
		int breadthP = data[2];
		int breadthN = data[3];

		// System.out.println("E " + widthP + ", " + widthN + ", " + breadthP + ", " + breadthN);

		BlockPos.Mutable pos = new BlockPos.Mutable().set(startPos);
		final int startX = startPos.getX();
		final int startZ = startPos.getZ();
		final int rawId = Block.getRawIdFromState(HAYKAMIUM_PORTAL);

		for (int xo = -widthN; xo <= widthP; ++xo) {
			pos.setX(startX + xo);

			for (int zo = -breadthN; zo <= breadthP; ++zo) {
				pos.setZ(startZ + zo);

				world.syncWorldEvent(2001, pos, rawId);
				world.setBlockState(pos, AIR, 2);	
			}
		}
	}

	private static boolean findLattice(World world, BlockPos startPos, final int size, final int searchSize, int[] data, BiPredicate<BlockPos, BlockState> isBorder) {
		BlockPos.Mutable pos = new BlockPos.Mutable().set(startPos);
		final int startX = startPos.getX();
		final int startZ = startPos.getZ();

		int widthP = 0;
		int widthN = 0;
		int breadthP = 0;
		int breadthN = 0;

		for (int xo = 1; xo <= searchSize; ++xo) {
			if (xo == searchSize) {
				return false;
			}

			pos.setX(startX + xo);

			if (isBorder.test(pos, world.getBlockState(pos))) {
				break;
			}

			widthP++;
		}

		for (int xo = 1; xo <= searchSize; ++xo) {
			if (xo == searchSize) {
				return false;
			}

			pos.setX(startX - xo);

			if (isBorder.test(pos, world.getBlockState(pos))) {
				break;
			}

			widthN++;
		}

		if (size > -1) {
			if (1 + widthP + widthN != size) {
				return false;
			}
		}

		pos.setX(startX);

		for (int zo = 1; zo <= searchSize; ++zo) {
			if (zo == searchSize) {
				return false;
			}

			pos.setZ(startZ + zo);

			if (isBorder.test(pos, world.getBlockState(pos))) {
				break;
			}

			breadthP++;
		}

		for (int zo = 1; zo <= searchSize; ++zo) {
			if (zo == searchSize) {
				return false;
			}

			pos.setZ(startZ - zo);

			if (isBorder.test(pos, world.getBlockState(pos))) {
				break;
			}

			breadthN++;
		}

		if (size > -1) {
			if (1 + breadthP + breadthN != size) {
				return false;
			}
		}

		data[0] = widthP;
		data[1] = widthN;
		data[2] = breadthP;
		data[3] = breadthN;

		return true;
	}

	private static boolean test(World world, BlockPos firePos, final int searchSize, final int size, int[] data) {
		final int fireX = firePos.getX();
		final int fireZ = firePos.getZ();
		BlockPos.Mutable pos = new BlockPos.Mutable().set(firePos);

		// find encapsulation of portal.
		if (!findLattice(world, firePos, size, searchSize, data, ($pos, state) -> state == FRAME)) {
			return false;
		}

		int widthP = data[0]; // the amount of "gap" on +x
		int widthN = data[1]; // the amount of "gap" on -x
		int breadthP = data[2]; // the amount of "gap" on +z
		int breadthN = data[3]; // the amount of "gap" on -z

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
		boolean checkActive = false;

		checkAir: for (int xo = -widthN; xo <= widthP; ++xo) {
			pos.setX(fireX + xo);

			for (int zo = -breadthN; zo <= breadthP; ++zo) {
				pos.setZ(fireZ + zo);

				if (xo != 0 || zo != 0) {
					BlockState state = world.getBlockState(pos);

					if (!state.isAir()) {
						checkActive = true;
						break checkAir;
					}
				}
			}
		}

		// might be activated
		if (checkActive) {
			for (int xo = -widthN; xo <= widthP; ++xo) {
				pos.setX(fireX + xo);

				for (int zo = -breadthN; zo <= breadthP; ++zo) {
					pos.setZ(fireZ + zo);

					if (xo != 0 || zo != 0) {
						BlockState state = world.getBlockState(pos);

						if (state != HAYKAMIUM_PORTAL) {
							return false;
						}
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

		return true;
	}

	private static final BlockState AIR = Blocks.AIR.getDefaultState();
	private static final BlockState HAYKAMIUM_PORTAL = LintBlocks.HAYKAMIUM_PORTAL.getDefaultState();
}
