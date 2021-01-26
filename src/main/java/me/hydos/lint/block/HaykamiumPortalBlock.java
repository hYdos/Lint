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

import me.hydos.lint.mixinimpl.LintPortal;
import me.hydos.lint.util.TeleportUtils;
import me.hydos.lint.world.dimension.Dimensions;
import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.network.packet.s2c.play.StopSoundS2CPacket;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class HaykamiumPortalBlock extends Block {
	public HaykamiumPortalBlock(FabricBlockSettings settings) {
		super(settings);
	}

	@Override
	public void neighborUpdate(BlockState state, World world, BlockPos pos, Block block, BlockPos fromPos, boolean notify) {
		LintPortal.resolve(world, pos, fromPos, true); // TODO be more efficient
	}

	@Override
	public void onEntityCollision(BlockState state, World world, BlockPos pos, Entity entity) {
		if (!world.isClient()) {
			ServerWorld fraiya = ((ServerWorld) world).getServer().getWorld(Dimensions.FRAIYA_WORLD);
			if (fraiya == null) {
				return;
			}
			if (entity instanceof ServerPlayerEntity) {
				ServerPlayerEntity serverPlayerEntity = (ServerPlayerEntity) entity;
				serverPlayerEntity.networkHandler.sendPacket(new StopSoundS2CPacket());
			}
			if (entity instanceof LivingEntity) {
				TeleportUtils.teleport(((LivingEntity) entity), fraiya, new BlockPos(0, 80, 0));
			}
		}
	}
}
