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

package me.hydos.lint.block;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;

public class DirtLikeBlock extends Block {
	public DirtLikeBlock(Settings settings) {
		super(settings);
	}

	@Override
	public boolean is(Block block) {
		return this == block || Blocks.DIRT == block;
	}

	public static boolean isLintGrass(BlockState state) {
		Block block = state.getBlock();
		return isLintGrass(block);
	}

	public static boolean isLintGrass(Block block) {
		return block == LintBlocks.CORRUPT_GRASS || block == LintBlocks.LIVELY_GRASS || block == LintBlocks.FROSTED_GRASS;
	}

	public static boolean isUntaintedGrass(BlockState state) {
		Block block = state.getBlock();
		return block == LintBlocks.LIVELY_GRASS || block == LintBlocks.FROSTED_GRASS;
	}
}
