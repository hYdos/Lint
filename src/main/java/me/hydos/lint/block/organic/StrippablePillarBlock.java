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

package me.hydos.lint.block.organic;

import net.fabricmc.fabric.api.tool.attribute.v1.FabricToolTags;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.PillarBlock;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class StrippablePillarBlock extends PillarBlock {
	public StrippablePillarBlock(Settings settings, Block stripper) {
		super(settings);

		this.stripper = stripper;
	} 

	private final Block stripper;

	@Override
	public ActionResult onUse(BlockState state, World world, BlockPos pos, PlayerEntity player, Hand hand, BlockHitResult hit) {
		// Mojang's code originally from axe item
		// Adapted to be on the block side bc then I don't have to mixin
		// We don't need to check the block anymore so just check the item in 2 lines
		ItemStack stack = player.getEquippedStack(hand == Hand.MAIN_HAND ? EquipmentSlot.MAINHAND : EquipmentSlot.OFFHAND);

		if (FabricToolTags.AXES.contains(stack.getItem())) {
			world.playSound(player, pos, SoundEvents.ITEM_AXE_STRIP, SoundCategory.BLOCKS, 1.0F, 1.0F);

			if (!world.isClient()) {
				world.setBlockState(pos, this.stripper.getDefaultState().with(PillarBlock.AXIS, state.get(PillarBlock.AXIS)), 11);

				if (player != null) {
					stack.damage(1, player, p -> p.sendToolBreakStatus(hand));
				}
			}

			return ActionResult.success(world.isClient);
		} else {
			return ActionResult.PASS;
		}
	}
}
