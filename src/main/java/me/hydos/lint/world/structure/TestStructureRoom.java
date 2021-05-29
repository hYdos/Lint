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

package me.hydos.lint.world.structure;

import com.google.common.collect.ImmutableList;
import me.hydos.lint.world.StructureWorld;
import me.hydos.lint.world.structure.TestStructureRoom.ExtraRoom;
import me.hydos.lint.world.structure2.LintStructure;
import me.hydos.lint.world.structure2.Room;
import net.minecraft.block.Blocks;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Box;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class TestStructureRoom extends Room {
	public static final LintStructure STRUCTURE = new LintStructure(new Identifier("lint:test"), 123L, TestStructureRoom::new, (generator, x, z) -> 200, biome -> true);

	public TestStructureRoom(BlockPos startPos) {
		super(startPos);
	}

	@Override
	protected void generate(StructureWorld world, Random rand, Box box) {
		BlockPos.Mutable pos = new BlockPos.Mutable();
		final int startX = this.startPos.getX();
		final int startY = this.startPos.getY();
		final int startZ = this.startPos.getZ();

		for (int xo = 0; xo < box.getXLength(); ++xo) {
			pos.setX(startX + xo);

			for (int yo = 0; yo < 4; ++yo) {
				pos.setY(startY + yo);

				for (int zo = 0; zo < box.getZLength(); ++zo) {
					pos.setZ(startZ + zo);

					world.setBlockState(pos, Blocks.ACACIA_LOG.getDefaultState());
				}
			}
		}
	}

	@Override
	protected Box getBounds(Random rand) {
		return new Box(this.startPos, this.startPos.add(4.0 + rand.nextInt(3), 4.0, 4.0 + rand.nextInt(3)));
	}

	@Override
	protected List<Room> computeNodes(Box box, Random rand) {
		List<Room> result = new ArrayList<>();

		result.add(new ExtraRoom(this.startPos.add(box.getXLength(), 0, box.getZLength() / 2)));
		result.add(new ExtraRoom(this.startPos.add(box.getXLength() / 2, 0, box.getZLength())));
		return result;
	}

	static class ExtraRoom extends Room {
		protected ExtraRoom(BlockPos startPos) {
			super(startPos);
		}

		@Override
		protected void generate(StructureWorld world, Random rand, Box box) {
			BlockPos.Mutable pos = new BlockPos.Mutable();
			final int startX = this.startPos.getX();
			final int startY = this.startPos.getY();
			final int startZ = this.startPos.getZ();

			for (int xo = 0; xo < 4; ++xo) {
				pos.setX(startX + xo);

				for (int yo = 0; yo < 4; ++yo) {
					pos.setY(startY + yo);

					for (int zo = 0; zo < 4; ++zo) {
						pos.setZ(startZ + zo);

						world.setBlockState(pos, Blocks.STONE.getDefaultState());
					}
				}
			}
		}

		@Override
		protected Box getBounds(Random rand) {
			return new Box(this.startPos, this.startPos.add(4.0, 4.0, 4.0));
		}

		@Override
		protected List<Room> computeNodes(Box box, Random rand) {
			return ImmutableList.of();
		}
	}
}
