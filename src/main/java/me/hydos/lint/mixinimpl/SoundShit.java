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

package me.hydos.lint.mixinimpl;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import it.unimi.dsi.fastutil.objects.Object2ObjectArrayMap;
import net.minecraft.client.sound.BiomeEffectSoundPlayer;
import net.minecraft.client.sound.MovingSoundInstance;
import net.minecraft.client.sound.BiomeEffectSoundPlayer.MusicLoop;
import net.minecraft.sound.SoundEvent;
import net.minecraft.util.Identifier;
import net.minecraft.world.biome.Biome;

public class SoundShit {
	public static Optional<SoundEvent> prev = Optional.empty();
	public static Optional<SoundEvent> next = Optional.empty();
	private static final Set<Identifier> BOSS_MUSIC = new HashSet<>();

	public static boolean isBossMusic(Identifier id) {
		return BOSS_MUSIC.contains(id);
	}

	public static SoundEvent registerBossMusic(SoundEvent event) {
		BOSS_MUSIC.add(event.getId());
		return event;
	}

	public static void checkFade(CallbackInfo info) {
		if (prev.isPresent() && next.isPresent()) {
			if (prev.get().getId().equals(next.get().getId())) {
				info.cancel();
			}
		}
	}

	public static void doShit(SoundEvent event, Object2ObjectArrayMap<Biome, MusicLoop> soundLoops) {
		next = Optional.of(event);
		soundLoops.values().removeIf(MovingSoundInstance::isDone);
		soundLoops.values().forEach(BiomeEffectSoundPlayer.MusicLoop::fadeOut);
		prev = next;
	}
}