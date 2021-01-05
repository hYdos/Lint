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

package me.hydos.lint.entity.passive;

import me.hydos.lint.network.Networking;
import net.fabricmc.fabric.api.networking.v1.PacketByteBufs;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.text.LiteralText;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.world.World;

@SuppressWarnings("EntityConstructor")
public class TinyPotatoNpcEntity extends TinyPotatoEntity {

	public TinyPotatoNpcEntity(EntityType<? extends TinyPotatoEntity> type, World world) {
		super(type, world);
	}

	@Override
	public ActionResult interactMob(PlayerEntity player, Hand hand) {
		if (!world.isClient()) {
			PacketByteBuf buf = PacketByteBufs.create()
					.writeString("Mr. Potato")
					.writeText(new LiteralText("Hi i am Mr. Potato! i sell crabs and corbs"));
			buf.writeInt(getEntityId());
			ServerPlayNetworking.send(
					(ServerPlayerEntity) player,
					Networking.OPEN_NPC_INTERACTION_WINDOW,
					buf
			);
		}
		return ActionResult.SUCCESS;
	}
}
