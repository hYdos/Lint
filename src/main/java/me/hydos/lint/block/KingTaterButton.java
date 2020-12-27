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

import me.hydos.lint.entity.Entities;
import me.hydos.lint.entity.aggressive.KingTaterEntity;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class KingTaterButton extends Block {

	public KingTaterButton(Settings settings) {
		super(settings);
	}

	@Override
	public ActionResult onUse(BlockState state, World world, BlockPos pos, PlayerEntity player, Hand hand, BlockHitResult hit) {
		if (state != LintBlocks.GREEN_BUTTON.getDefaultState()) {
			world.setBlockState(pos, LintBlocks.GREEN_BUTTON.getDefaultState());
			KingTaterEntity kingTater = new KingTaterEntity(Entities.KING_TATER, world);
			kingTater.refreshPositionAndAngles(pos, 0, 0);
			world.spawnEntity(kingTater);
			return ActionResult.SUCCESS;
		} else {
			return ActionResult.FAIL;
		}
	}
}
