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

import java.util.Random;

import me.hydos.lint.refactord.block.LintBlocks2;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.SnowBlock;
import net.minecraft.block.SnowyBlock;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.tag.FluidTags;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.WorldView;
import net.minecraft.world.chunk.light.ChunkLightProvider;

public class LintSpreadableBlock extends SnowyBlock {
	public LintSpreadableBlock(Settings settings) {
		super(settings.ticksRandomly());
	}

	private static boolean canSurvive(BlockState state, WorldView worldView, BlockPos pos) {
		BlockPos upPos = pos.up();
		BlockState blockState = worldView.getBlockState(upPos);
		if (blockState.isOf(Blocks.SNOW) && (Integer)blockState.get(SnowBlock.LAYERS) == 1) {
			return true;
		} else if (blockState.getFluidState().getLevel() == 8) {
			return false;
		} else {
			int i = ChunkLightProvider.getRealisticOpacity(worldView, state, pos, blockState, upPos, Direction.UP, blockState.getOpacity(worldView, upPos));
			return i < worldView.getMaxLightLevel();
		}
	}

	private static boolean canSpread(BlockState state, WorldView worldView, BlockPos pos) {
		BlockPos blockPos = pos.up();
		return canSurvive(state, worldView, pos) && !worldView.getFluidState(blockPos).isIn(FluidTags.WATER);
	}

	@Override
	public void randomTick(BlockState state, ServerWorld world, BlockPos pos, Random random) {
		if (!canSurvive(state, world, pos)) {
			world.setBlockState(pos, LintBlocks2.RICH_DIRT.getDefaultState());
		} else {
			if (world.getLightLevel(pos.up()) >= 9) {
				BlockState blockState = this.getDefaultState();

				for(int i = 0; i < 4; ++i) {
					BlockPos setPos = pos.add(random.nextInt(3) - 1, random.nextInt(5) - 3, random.nextInt(3) - 1);
					if (world.getBlockState(setPos).isOf(LintBlocks2.RICH_DIRT) && canSpread(blockState, world, setPos)) {
						world.setBlockState(setPos, (BlockState)blockState.with(SNOWY, world.getBlockState(setPos.up()).isOf(Blocks.SNOW)));
					}
				}
			}

		}
	}
}
