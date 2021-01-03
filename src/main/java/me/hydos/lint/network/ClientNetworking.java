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

package me.hydos.lint.network;

import me.hydos.lint.bossbar.ClientModernBossBar;
import me.hydos.lint.bossbar.ModernBossBar;
import me.hydos.lint.client.screen.NpcInteractionScreen;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.minecraft.client.MinecraftClient;
import net.minecraft.text.Text;

public class ClientNetworking {

	public static void register() {
		ClientPlayNetworking.registerGlobalReceiver(Networking.SEND_BOSSBAR_INFO, (minecraftClient, clientPlayNetworkHandler, buf, packetSender) -> {
			ModernBossBar.PacketType type = buf.readEnumConstant(ModernBossBar.PacketType.class);
			switch (type) {
				case NEW:
					Text title = buf.readText();
					int colour = buf.readInt();
					int endX = buf.readInt();
					new ClientModernBossBar(title, colour, endX);
					break;
				case SET_VALUE:
					ClientModernBossBar.getInstance().endX = buf.readInt();
					break;
				case SET_TITLE:
					ClientModernBossBar.getInstance().title = buf.readText();
			}
		});

		ClientPlayNetworking.registerGlobalReceiver(Networking.OPEN_NPC_INTERACTION_WINDOW, (minecraftClient, clientPlayNetworkHandler, packetByteBuf, packetSender) -> {
			MinecraftClient.getInstance().openScreen(new NpcInteractionScreen(packetByteBuf));
		});
	}
}
