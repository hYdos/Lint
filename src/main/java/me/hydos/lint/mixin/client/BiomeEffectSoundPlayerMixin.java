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

import it.unimi.dsi.fastutil.objects.Object2ObjectArrayMap;
import me.hydos.lint.sound.LintSoundManager;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.client.render.WorldRenderer;
import net.minecraft.client.sound.BiomeEffectSoundPlayer;
import net.minecraft.client.sound.SoundManager;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.source.BiomeAccess;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.Optional;

@Mixin(BiomeEffectSoundPlayer.class)
public class BiomeEffectSoundPlayerMixin {
	@Shadow
	private Biome activeBiome;

	@Shadow
	@Final
	private BiomeAccess biomeAccess;

	@Shadow
	@Final
	private ClientPlayerEntity player;

	@Shadow
	@Final
	private SoundManager soundManager;

	@Shadow
	private Object2ObjectArrayMap<Biome, BiomeEffectSoundPlayer.MusicLoop> soundLoops;

	@Inject(
			at = @At("HEAD"),
			method = "tick",
			cancellable = true)
	private void musicGood(CallbackInfo info) {
		MinecraftClient client = MinecraftClient.getInstance();
		WorldRenderer wr = client.worldRenderer;

		if (LintSoundManager.isPlayingRecordMusic(this.player, this.soundManager, wr)) {
			LintSoundManager.stopSounds(Optional.empty(), this.soundLoops);
			info.cancel();
		} else if (LintSoundManager.isCachedAsRecordPlaying()) {
			LintSoundManager.restartSounds(this.soundManager, this.activeBiome = biomeAccess.getBiomeForNoiseGen(this.player.getX(), this.player.getY(), this.player.getZ()), this.soundLoops);
			info.cancel();
		}

		if (this.activeBiome != null) {
			LintSoundManager.markPrev(this.activeBiome);
		}
	}

	@Inject(
			at = @At(
					value = "INVOKE",
					target = "Lit/unimi/dsi/fastutil/objects/Object2ObjectArrayMap;values()Lit/unimi/dsi/fastutil/objects/ObjectCollection;",
					remap = false,
					ordinal = 1),
			method = "tick")
	private void markNext(CallbackInfo info) {
		LintSoundManager.markNext(this.activeBiome);
	}

	@Redirect(
			at = @At(value = "INVOKE", target = "Lnet/minecraft/world/biome/source/BiomeAccess;getBiomeForNoiseGen(DDD)Lnet/minecraft/world/biome/Biome;"),
			method = "tick")
	private Biome injectBiomeSoundDummies(BiomeAccess access, double x, double y, double z) {
		return LintSoundManager.injectBiomeSoundDummies(this.player, access, x, y, z);
	}
}
