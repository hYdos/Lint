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

import me.hydos.lint.item.LintItems;
import me.hydos.lint.sound.Sounds;
import me.hydos.lint.util.TeleportUtils;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.network.packet.s2c.play.PlaySoundS2CPacket;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundCategory;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.BooleanProperty;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Box;
import net.minecraft.world.World;

public class ReturnHomeBlock extends Block {

	public static final BooleanProperty ACTIVATED = BooleanProperty.of("activated");

	public ReturnHomeBlock(Settings settings) {
		super(settings);

		this.setDefaultState(this.stateManager.getDefaultState().with(ACTIVATED, false));
	}

	protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
		builder.add(ACTIVATED);
	}

	@Override
	public ActionResult onUse(BlockState state, World world, BlockPos pos, PlayerEntity player, Hand hand, BlockHitResult hit) {
		if (state.get(ACTIVATED)) {			
			if (world instanceof ServerWorld) {
				if (player.getStackInHand(hand).getItem() == LintItems.ATTUNER) {
					return ActionResult.PASS;
				}

				world.getEntitiesByClass(LivingEntity.class, new Box(pos).expand(5.0), le -> true).forEach(le -> TeleportUtils.teleport(le, world.getServer().getWorld(World.OVERWORLD), le.getBlockPos()));
			}

			return ActionResult.SUCCESS;
		} else if (player.getStackInHand(hand).getItem() == LintItems.TATER_ESSENCE) {
			if (!world.isClient()) {
				((ServerPlayerEntity) player).networkHandler.sendPacket(new PlaySoundS2CPacket(Sounds.SHRINE_ACTIVATE, SoundCategory.MASTER, pos.getX(), pos.getY(), pos.getZ(), 1f, 1f));
				world.setBlockState(pos, LintBlocks.RETURN_HOME.getDefaultState().with(ReturnHomeBlock.ACTIVATED, true));
				player.getStackInHand(hand).decrement(1);
				return ActionResult.SUCCESS;
			}
		}
		return ActionResult.PASS;
	}
}
