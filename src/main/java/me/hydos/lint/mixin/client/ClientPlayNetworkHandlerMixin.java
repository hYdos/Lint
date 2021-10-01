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

package me.hydos.lint.mixin.client;

import io.netty.buffer.Unpooled;
import me.hydos.lint.client.screen.TaterDownloadingTerrainScreen;
import me.hydos.lint.network.Networking;
import me.hydos.lint.world.dimension.Dimensions;
import net.fabricmc.fabric.api.network.ClientSidePacketRegistry;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.screen.DownloadingTerrainScreen;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.network.ClientPlayNetworkHandler;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.network.packet.s2c.play.GameJoinS2CPacket;
import net.minecraft.network.packet.s2c.play.PlayerRespawnS2CPacket;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ClientPlayNetworkHandler.class)
public class ClientPlayNetworkHandlerMixin {
	@Redirect(method = "onPlayerRespawn", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/MinecraftClient;openScreen(Lnet/minecraft/client/gui/screen/Screen;)V"))
	private void openDownloadingTerrainScreen(MinecraftClient client, Screen screen, PlayerRespawnS2CPacket packet) {
		if (screen instanceof DownloadingTerrainScreen && packet.getDimension().equals(Dimensions.FRAIYA_WORLD)) {
			client.setScreen(new TaterDownloadingTerrainScreen());
		} else {
			client.setScreen(new DownloadingTerrainScreen());
		}
	}

	@SuppressWarnings("deprecation")
	@Inject(at = @At("RETURN"), method = "onGameJoin")
	private void onOnGameJoin(GameJoinS2CPacket packet, CallbackInfo info) {
		PacketByteBuf data = new PacketByteBuf(Unpooled.buffer());
		try {
			ClientSidePacketRegistry.INSTANCE.sendToServer(Networking.GIB_INFO_PLS, data);
		} catch (Exception e) {
			e.printStackTrace();//java.lang.IllegalStateException: Cannot send packet to server while not in game!
		}
		System.out.println("sent data yes ok");
	}
}
