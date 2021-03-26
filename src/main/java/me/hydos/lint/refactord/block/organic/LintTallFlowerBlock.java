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

package me.hydos.lint.refactord.block.organic;

import me.hydos.lint.block.LintBlocks;
import me.hydos.lint.core.block.BlockBuilder.BlockConstructor;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.ShapeContext;
import net.minecraft.block.TallFlowerBlock;
import net.minecraft.block.enums.DoubleBlockHalf;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.util.shape.VoxelShapes;
import net.minecraft.world.BlockView;

public class LintTallFlowerBlock extends TallFlowerBlock {
	public static final VoxelShape BOTTOM_MODEL = VoxelShapes.cuboid(0.125, 0, 0.125, 0.875, 1, 0.875);
	public static final VoxelShape TOP_MODEL = VoxelShapes.cuboid(0.125, 0, 0.125, 0.875, 0.75, 0.875);

	public LintTallFlowerBlock(Settings settings) {
		super(settings);
	}

	@Override
	protected boolean canPlantOnTop(BlockState floor, BlockView world, BlockPos pos) {
		Block block = floor.getBlock();
		return block == LintBlocks.LIVELY_GRASS || block == LintBlocks.CORRUPT_GRASS;
	}

	@Override
	public VoxelShape getOutlineShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
		return state.get(HALF) == DoubleBlockHalf.LOWER ? BOTTOM_MODEL : TOP_MODEL;
	}

	public static final BlockConstructor<LintTallFlowerBlock> CONSTRUCTOR = LintTallFlowerBlock::new;
}
