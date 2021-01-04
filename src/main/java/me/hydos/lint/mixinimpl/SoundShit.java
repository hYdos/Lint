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

import it.unimi.dsi.fastutil.objects.Object2ObjectArrayMap;
import me.hydos.lint.sound.NotMusicLoop;
import me.hydos.lint.sound.Sounds;
import net.minecraft.client.sound.BiomeEffectSoundPlayer;
import net.minecraft.client.sound.BiomeEffectSoundPlayer.MusicLoop;
import net.minecraft.client.sound.MovingSoundInstance;
import net.minecraft.client.sound.SoundManager;
import net.minecraft.sound.SoundEvent;
import net.minecraft.util.Identifier;
import net.minecraft.world.biome.Biome;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

public class SoundShit {
	private static Optional<SoundEvent> prev = Optional.empty();
	private static Optional<SoundEvent> next = Optional.empty();
	private static final Set<Identifier> BOSS_MUSIC = new HashSet<>();
	public static boolean magicBossMusicFlag = false;

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
		magicBossMusicFlag = true;
		soundLoops.values().removeIf(MovingSoundInstance::isDone);
		soundLoops.values().forEach(BiomeEffectSoundPlayer.MusicLoop::fadeOut);
		prev = next;
	}

	public static void markPrev(Biome biome) {
		prev = biome.getLoopSound();
	}

	public static void markNext(Biome biome) {
		next = biome.getLoopSound();
	}

	public static void doOtherShit(SoundEvent sound, SoundManager manager, Biome biome, Object2ObjectArrayMap<Biome, MusicLoop> soundLoops) {
		magicBossMusicFlag = false;
		biome.getLoopSound().ifPresent(soundEvent -> soundLoops.compute(biome, (biomex, musicLoop) -> {
			if (musicLoop == null) {
				musicLoop = new BiomeEffectSoundPlayer.MusicLoop(soundEvent);
				manager.play(musicLoop);
			}

			musicLoop.fadeIn();
			return musicLoop;
		}));
	}

	public static void markClear() {
		prev = Optional.empty();
		next = Optional.empty();
	}

	public static void doRandomLoopSwitcheroo(Biome activeBiome, SoundManager manager, Object2ObjectArrayMap<Biome, MusicLoop> soundLoops, Runnable setActiveBiomeToNull) {
		soundLoops.values().forEach(loop -> {
			if (loop instanceof NotMusicLoop && !manager.isPlaying(loop)) {
//				System.out.println(loop.getId());
				((NotMusicLoop)loop).setAsDone();
			}
		});

		if (activeBiome != null) {
			Optional<SoundEvent> event = activeBiome.getLoopSound();

			if (event.isPresent() && prev.isPresent()) {
				SoundEvent soundEvent = event.get();

				// if it's our sound with variants
				if (soundEvent.getId().equals(Sounds.MYSTICAL_FOREST.getId())) {

					// if the sound isn't changing anyway
					if (prev.get().getId().equals(event.get().getId())) {
						if (soundLoops.values().stream().noneMatch(loop -> loop.getSound().getIdentifier().toString().equals("lint:mystical_forest") || loop.getSound().getIdentifier().toString().equals("lint:ethereal_groves_of_fraiya"))) {
							// set to null bc funni hacks
							setActiveBiomeToNull.run();
							markClear();
						}
					}
				}
			}
		}
	}

	public static MusicLoop constructMusicLoop(SoundEvent event) {
		Identifier id = event.getId();

		if (id.equals(Sounds.MYSTICAL_FOREST.getId()) || id.equals(Sounds.ETHEREAL_GROVES_OF_FRAIYA.getId())) {
			return new NotMusicLoop(event);
		} else {
			return new MusicLoop(event);
		}
	}

	public static boolean recordIsPlaying() {
		return false;
	}
}
