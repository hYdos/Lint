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
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.tag.BlockTags;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.explosion.Explosion;

public class LintPortal {
	public static void resolve(World world, BlockPos firePos) {
		boolean blowUp = false;
		if (world.getRegistryKey() != World.OVERWORLD) {
			blowUp = true;
		}

		final int size = 3;
		final int searchSize = 4;

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
				return;
			}

			pos.setX(fireX + xo);

			if (world.getBlockState(pos) == FRAME) {
				break;
			}

			widthP++;
		}

		for (int xo = 1; xo <= searchSize; ++xo) {
			if (xo == searchSize) {
				return;
			}

			pos.setX(fireX - xo);

			if (world.getBlockState(pos) == FRAME) {
				break;
			}

			widthN++;
		}

		if (1 + widthP + widthN != size) {
			return;
		}

		pos.setX(fireX);

		for (int zo = 1; zo <= searchSize; ++zo) {
			if (zo == searchSize) {
				return;
			}

			pos.setZ(fireZ + zo);

			if (world.getBlockState(pos) == FRAME) {
				break;
			}

			breadthP++;
		}

		for (int zo = 1; zo <= searchSize; ++zo) {
			if (zo == searchSize) {
				return;
			}

			pos.setZ(fireZ - zo);

			if (world.getBlockState(pos) == FRAME) {
				break;
			}

			breadthN++;
		}

		if (1 + breadthP + breadthN != size) {
			return;
		}

		// Check Edges
		pos.setZ(fireZ + breadthP + 1);

		for (int xo = -widthN; xo <= widthP; ++xo) {
			pos.setX(fireX + xo);

			if (world.getBlockState(pos) != FRAME) {
				return;
			}
		}

		pos.setZ(fireZ - breadthN - 1);

		for (int xo = -widthN; xo <= widthP; ++xo) {
			pos.setX(fireX + xo);

			if (world.getBlockState(pos) != FRAME) {
				return;
			}
		}

		pos.setX(fireX + widthP + 1);

		for (int zo = -breadthN; zo <= breadthP; ++zo) {
			pos.setZ(fireZ + zo);

			if (world.getBlockState(pos) != FRAME) {
				return;
			}
		}

		pos.setX(fireX - widthN - 1);

		for (int zo = -breadthN; zo <= breadthP; ++zo) {
			pos.setZ(fireZ + zo);

			if (world.getBlockState(pos) != FRAME) {
				return;
			}
		}

		// check air space for portal
		for (int xo = -widthN; xo <= widthP; ++xo) {
			pos.setX(fireX + xo);

			for (int zo = -breadthN; zo <= breadthP; ++zo) {
				pos.setZ(fireZ + zo);

				if (xo != 0 || zo != 0) {
					if (!world.getBlockState(pos).isAir()) {
						return;
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
					return;
				}
			}
		}

		pos.setY(fireY);

		// place portal
		if(blowUp){
			if(!world.isClient()){
				world.createExplosion(null, fireX, fireY, fireZ, 10, Explosion.DestructionType.DESTROY);
			}
			return;
		}
		for (int xo = -widthN; xo <= widthP; ++xo) {
			pos.setX(fireX + xo);

			for (int zo = -breadthN; zo <= breadthP; ++zo) {
				pos.setZ(fireZ + zo);

				world.setBlockState(pos, LintBlocks.HAYKAMIUM_PORTAL.getDefaultState());
			}
		}
	}

	public static final BlockState FRAME = Blocks.COAL_BLOCK.getDefaultState();
}
