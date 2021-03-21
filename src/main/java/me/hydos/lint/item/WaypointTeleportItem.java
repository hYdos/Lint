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

package me.hydos.lint.item;

import java.util.List;

import me.hydos.lint.block.LintBlocks;
import me.hydos.lint.block.ReturnHomeBlock;
import me.hydos.lint.world.dimension.Dimensions;
import net.minecraft.client.item.TooltipContext;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemUsageContext;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.text.LiteralText;
import net.minecraft.text.Text;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class WaypointTeleportItem extends Item {
	public WaypointTeleportItem(Settings settings) {
		super(settings);
	}

	@Override
	public void appendTooltip(ItemStack stack, World world, List<Text> tooltip, TooltipContext context) {
		CompoundTag tag = stack.getOrCreateTag();

		if (tag.contains("waypoint")) {
			int[] pos = tag.getIntArray("waypoint");
			tooltip.add(new LiteralText("Attuned with the shrine at " + pos[0] + ", " + pos[2]));
		}
	}

	@Override
	public boolean hasGlint(ItemStack stack) {
		CompoundTag tag = stack.getOrCreateTag();

		if (tag.contains("waypoint")) {
			return true;
		}

		return false;
	}

	@Override
	public TypedActionResult<ItemStack> use(World world, PlayerEntity user, Hand hand) {
		if (world.getRegistryKey() == Dimensions.FRAIYA_WORLD) {
			ItemStack stack = user.getStackInHand(hand);

			CompoundTag tag = stack.getOrCreateTag();

			if (tag.contains("nextTime")) {
				long cTime = world.getTime();
				long nextTime = tag.getLong("nextTime");

				if (cTime < nextTime) {
					user.sendMessage(new LiteralText("This portal attuner is on cooldown for " + (nextTime - cTime) / 20L + " seconds."), true);
					return TypedActionResult.consume(stack);
				}
			}
			if (tag.contains("waypoint")) {
				if (!world.isClient()) {
					int[] pos = tag.getIntArray("waypoint");

					user.teleport(pos[0] + 0.5, pos[1], pos[2] + 0.5);
				}

				tag.putLong("nextTime", world.getTime() + 20L * 60L * 5L);

				return TypedActionResult.consume(stack);
			}
		}

		return super.use(world, user, hand);
	}

	@Override
	public ActionResult useOnBlock(ItemUsageContext context) {
		BlockPos pos = context.getBlockPos();
		World world = context.getWorld();

		if (world.getBlockState(pos) == LintBlocks.RETURN_HOME.getDefaultState().with(ReturnHomeBlock.ACTIVATED, true)) {
			context.getStack().getOrCreateTag().putIntArray("waypoint", new int[] {pos.getX(), pos.getY() + 1, pos.getZ()});
			return ActionResult.SUCCESS;
		}

		return ActionResult.PASS;
	}
}
