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

package me.hydos.lint.sound;

import me.hydos.lint.Lint;
import me.hydos.lint.mixinimpl.LintSoundEvent;
import net.minecraft.client.sound.MusicType;
import net.minecraft.sound.MusicSound;
import net.minecraft.sound.SoundEvent;
import net.minecraft.util.registry.Registry;

public class Sounds {

	/**
	 * Boss Music
	 */
	public static final SoundEvent KING_TATER = new SoundEvent(Lint.id("music.king_tater_boss_theme"));
	public static final SoundEvent I509 = new SoundEvent(Lint.id("music.i509_boss_theme"));
	public static final SoundEvent LEX_MANOS = new SoundEvent(Lint.id("music.lex_manos_boss_theme"));

	/**
	 * Misc Music
	 */
	public static final SoundEvent DUNGEON = new SoundEvent(Lint.id("music.clandestine"));
	public static final SoundEvent GRIMACE = new SoundEvent(Lint.id("music.grimace"));

	/**
	 * Biome Music
	 */
	public static final SoundEvent MYSTICAL_FOREST = new SoundEvent(Lint.id("music.mystical_forest"));
	public static final SoundEvent CORRUPT_FOREST = new SoundEvent(Lint.id("music.corrupt_forest"));
	public static final SoundEvent OCEAN = new SoundEvent(Lint.id("music.ocean"));
	public static final SoundEvent DAWN_SHARDLANDS = new SoundEvent(Lint.id("music.dawn_shardlands"));
	public static final SoundEvent ETHEREAL_GROVES_OF_FRAIYA = new SoundEvent(Lint.id("music.ethereal_groves_of_fraiya"));

	/**
	 * Misc Sounds
	 */
	public static final SoundEvent ADVANCEMENT = new SoundEvent(Lint.id("player.advancement"));

	/**
	 * Entity Sounds
	 */
	public static final SoundEvent EASTERN_ROSELLA_IDLE = new SoundEvent(Lint.id("eastern_rosella.idle"));
	public static final SoundEvent CRAB_IDLE = new SoundEvent(Lint.id("crab.idle"));

	/**
	 * Boss Music Loops
	 */
	public static final MusicSound KING_TATER_LOOP = MusicType.createIngameMusic(KING_TATER);
	public static final MusicSound I509_LOOP = MusicType.createIngameMusic(I509);
	public static final MusicSound LEX_MANOS_LOOP = MusicType.createIngameMusic(LEX_MANOS);

	public static void initialize() {
		register(KING_TATER);
		register(I509);
		register(LEX_MANOS);
		register(MYSTICAL_FOREST);
		register(CORRUPT_FOREST);
		register(OCEAN);
		register(DAWN_SHARDLANDS);
		register(ETHEREAL_GROVES_OF_FRAIYA);

		register(ADVANCEMENT);

		register(EASTERN_ROSELLA_IDLE);
	}

	private static void register(SoundEvent event) {
		Registry.register(Registry.SOUND_EVENT, ((LintSoundEvent) event).getCommonId(), event);
	}
}
