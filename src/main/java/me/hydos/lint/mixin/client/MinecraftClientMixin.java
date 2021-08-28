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

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import me.hydos.lint.client.sound.LintSoundManager;
import me.hydos.lint.client.sound.SecurityProblemCauser;
import me.hydos.lint.world.dimension.Dimensions;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.client.sound.MusicType;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.entity.Entity;
import net.minecraft.sound.MusicSound;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;

@Mixin(MinecraftClient.class)
public class MinecraftClientMixin {
	@Shadow
	private ClientPlayerEntity player;

	@Shadow
	public ClientWorld world;

	@Shadow
	public Entity cameraEntity;

	@Inject(at = @At("HEAD"), method = "disconnect(Lnet/minecraft/client/gui/screen/Screen;)V")
	private void disconnect(Screen screen, CallbackInfo info) {
		synchronized (SecurityProblemCauser.lock) {
			SecurityProblemCauser.townLocs = null;
		}
	}

	@Inject(at = @At("HEAD"), cancellable = true, method = "getMusicType")
	private void addLintMusicOverrides(CallbackInfoReturnable<MusicSound> info) {
		if (this.world != null && this.world.getRegistryKey() == Dimensions.FRAIYA_WORLD) {
			BlockPos playerPos = this.player.getBlockPos();
			Vec3d playerPosD = this.player.getPos(); // pos double

			info.setReturnValue(LintSoundManager.injectSpecialMusic(this.world, playerPos, playerPosD.x, playerPosD.y, playerPosD.z)
					.orElse(this.world.getBiomeAccess().getBiomeForNoiseGen(playerPos).getMusic()
							.orElse(MusicType.GAME)));
		}
	}
}
