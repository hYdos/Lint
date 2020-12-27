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

package me.hydos.lint.mixin;

import me.hydos.lint.sound.Sounds;
import me.hydos.lint.world.dimension.Dimensions;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.sound.MusicTracker;
import net.minecraft.client.sound.MusicType;
import net.minecraft.client.sound.SoundInstance;
import net.minecraft.sound.MusicSound;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(MusicTracker.class)
public abstract class SoundManagerMixin {

	@Shadow
	private @Nullable SoundInstance current;

	@Shadow
	@Final
	private MinecraftClient client;

	@Shadow
	public abstract void play(MusicSound type);

	@Shadow
	public abstract void stop();

	@Inject(method = "tick", at = @At("HEAD"), cancellable = true)
	private void tickButGood(CallbackInfo ci) {
		MusicSound musicSound = this.client.getMusicType();

		if (this.current == null) {
			if (musicSound == Sounds.KING_TATER_LOOP || musicSound == Sounds.I509_LOOP || musicSound == Sounds.LEX_MANOS_LOOP) {
				this.stop();
				this.play(musicSound);
			}
		} else {
			if (current.getId().getNamespace().equals("lint")) {
				ci.cancel();
			}
		}
	}

	@Inject(at = @At("HEAD"), method = "play", cancellable = true)
	private void onPlay(MusicSound type, CallbackInfo info) {
		if (type == MusicType.UNDERWATER || type == MusicType.GAME || type == MusicType.CREATIVE) {
			if (this.client.world.getDimension() == Dimensions.HAYKAM) {
				info.cancel();
			}
		}
	}
}
