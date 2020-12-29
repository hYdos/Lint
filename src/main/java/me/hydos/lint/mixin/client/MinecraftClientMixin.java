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

import me.hydos.lint.entity.Entities;
import me.hydos.lint.mixinimpl.SoundShit;
import me.hydos.lint.sound.Sounds;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.entity.Entity;
import net.minecraft.sound.MusicSound;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.Optional;

@Mixin(MinecraftClient.class)
public class MinecraftClientMixin {

	@Shadow
	public ClientWorld world;

	@Shadow
	public Entity cameraEntity;

	@Inject(method = "getMusicType", at = @At("HEAD"), cancellable = true)
	private void getMusicType(CallbackInfoReturnable<MusicSound> callbackInfoReturnable) {
		ClientWorld world = this.world;
		Entity entity = this.cameraEntity;

		if (world != null && entity != null && !world.getEntitiesByType(Entities.KING_TATER, entity.getBoundingBox().expand(40), $ -> true).isEmpty()) {
			callbackInfoReturnable.setReturnValue(Sounds.KING_TATER_LOOP);
		}
		if (world != null && entity != null && !world.getEntitiesByType(Entities.I5, entity.getBoundingBox().expand(40), $ -> true).isEmpty()) {
			callbackInfoReturnable.setReturnValue(Sounds.I509_LOOP);
		}
		//		if (world != null && entity != null && !world.getEntitiesByType(Entities.LEX_MANOS, entity.getBoundingBox().expand(40), $ -> true).isEmpty()) {
		//			callbackInfoReturnable.setReturnValue(Sounds.KING_TATER_LOOP);
		//		}
	}

	@Inject(at = @At("HEAD"), method = "disconnect(Lnet/minecraft/client/gui/screen/Screen;)V")
	private void disconnect(Screen screen, CallbackInfo info) {
		SoundShit.next = Optional.empty();
		SoundShit.prev = Optional.empty();
	}
}
