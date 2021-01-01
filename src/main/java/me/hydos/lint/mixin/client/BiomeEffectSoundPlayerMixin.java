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

import java.util.Optional;

import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import it.unimi.dsi.fastutil.objects.Object2ObjectArrayMap;
import me.hydos.lint.mixinimpl.SoundShit;
import me.hydos.lint.sound.NotMusicLoop;
import me.hydos.lint.sound.Sounds;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.client.sound.BiomeEffectSoundPlayer;
import net.minecraft.client.sound.BiomeEffectSoundPlayer.MusicLoop;
import net.minecraft.client.sound.SoundManager;
import net.minecraft.sound.SoundEvent;
import net.minecraft.util.Identifier;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.source.BiomeAccess;

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
	private void musicGood1(CallbackInfo info) {
		SoundEvent sound = MinecraftClient.getInstance().getMusicType().getSound();

		if (SoundShit.isBossMusic(sound.getId())) {
			SoundShit.doShit(sound, this.soundLoops);
			info.cancel();
		} else if (SoundShit.magicBossMusicFlag) {
			SoundShit.doOtherShit(sound, this.soundManager, this.activeBiome = biomeAccess.getBiome(this.player.getX(), this.player.getY(), this.player.getZ()), this.soundLoops);
			info.cancel();
		} else {
			SoundShit.doRandomLoopSwitcheroo(this.activeBiome, this.soundLoops, () -> {
				this.activeBiome = null;
			});
		}

		if (this.activeBiome != null) {
			SoundShit.markPrev(this.activeBiome);
		}
	}

	@Redirect(
			at = @At(value = "INVOKE", target = "Lnet/minecraft/world/biome/Biome;getLoopSound()Ljava/util/Optional;"),
			method = "tick")
	private Optional<SoundEvent> addVariants(Biome biome) {
		Optional<SoundEvent> event = biome.getLoopSound();

		if (event.isPresent()) {
			if (event.get().getId().equals(Sounds.MYSTICAL_FOREST.getId())) {
				if (this.player.getEntityWorld().getRandom().nextInt(3) == 0) {
					return Optional.of(Sounds.ETHEREAL_GROVES_OF_FRAIYA);
				}
			}
		}

		return biome.getEffects().getLoopSound();
	}

	@Redirect(
			at = @At(value = "NEW", target = "net/minecraft/client/sound/BiomeEffectSoundPlayer$MusicLoop"),
			method = "method_25459")
	private MusicLoop onMusicLoopConstruct(SoundEvent event) {
		Identifier id = event.getId();

		if (id.equals(Sounds.MYSTICAL_FOREST.getId()) || id.equals(Sounds.ETHEREAL_GROVES_OF_FRAIYA.getId())) {
			return new NotMusicLoop(event);
		} else {
			return new MusicLoop(event);
		}
	}

	@Inject(
			at = @At(
					value = "INVOKE",
					target = "Lit/unimi/dsi/fastutil/objects/Object2ObjectArrayMap;values()Lit/unimi/dsi/fastutil/objects/ObjectCollection;",
					ordinal = 1),
			method = "tick")
	private void musicGood2(CallbackInfo info) {
		SoundShit.markNext(this.activeBiome);
	}
}