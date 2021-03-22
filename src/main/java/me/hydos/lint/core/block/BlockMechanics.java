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

package me.hydos.lint.core.block;

import org.jetbrains.annotations.Nullable;

import me.hydos.lint.core.block.BlockBuilder.BlockConstructor;
import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

/**
 * Class for constructing blocks with particular special pproperties.
 */
public class BlockMechanics implements BlockConstructor<Block> {
	@Nullable
	private OnUse onUse;

	public BlockMechanics onUse(OnUse onUse) {
		this.onUse = onUse;
		return this;
	}

	@Override
	public Block create(FabricBlockSettings settings) {
		return new MechanicalBlock(settings, this.onUse);
	}

	// Block Class and Functional Interfaces

	private static class MechanicalBlock extends Block {
		public MechanicalBlock(Settings settings, @Nullable OnUse onUse) {
			super(settings);
			this.onUse = onUse == null ? super::onUse : onUse;
		}

		private OnUse onUse;

		@Override
		public ActionResult onUse(BlockState state, World world, BlockPos pos, PlayerEntity player, Hand hand, BlockHitResult hit) {
			return this.onUse.onUse(state, world, pos, player, hand, hit);
		}
	}

	@FunctionalInterface
	public interface OnUse {
		ActionResult onUse(BlockState state, World world, BlockPos pos, PlayerEntity player, Hand hand, BlockHitResult hit);
	}
}
