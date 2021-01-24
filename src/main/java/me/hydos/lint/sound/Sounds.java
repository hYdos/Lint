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
	public static final SoundEvent HERIA_AND_THE_TOWN_OF_HOPE = new SoundEvent(Lint.id("music.heria_and_the_town_of_hope"));
	public static final SoundEvent PAWERIA_CARIAR_OF_ORDER = new SoundEvent(Lint.id("music.paweria_cariar_of_order"));
	public static final SoundEvent STEELBRANCH = new SoundEvent(Lint.id("music.steelbranch"));

	/**
	 * Biome Music
	 */
	public static final SoundEvent MYSTICAL_FOREST = new SoundEvent(Lint.id("music.mystical_forest"));
	public static final SoundEvent CORRUPT_FOREST = new SoundEvent(Lint.id("music.corrupt_forest"));
	public static final SoundEvent OCEAN = new SoundEvent(Lint.id("music.ocean"));
	public static final SoundEvent DAWN_SHARDLANDS = new SoundEvent(Lint.id("music.dawn_shardlands"));
	public static final SoundEvent ETHEREAL_GROVES_OF_FRAIYA = new SoundEvent(Lint.id("music.ethereal_groves_of_fraiya"));
	public static final SoundEvent CAVERNS = new SoundEvent(Lint.id("music.caverns"));

	/**
	 * Misc Sounds
	 */
	public static final SoundEvent ADVANCEMENT = new SoundEvent(Lint.id("player.advancement"));
	public static final SoundEvent ACTIVATE_SHRINE = new SoundEvent(Lint.id("player.activate_shrine"));

	/**
	 * Entity Sounds
	 */
	public static final SoundEvent EASTERN_ROSELLA_IDLE = new SoundEvent(Lint.id("eastern_rosella.idle"));
	public static final SoundEvent CRAB_IDLE = new SoundEvent(Lint.id("crab.idle"));

	private static final MusicSound createBossMusic(SoundEvent event) {
		return new MusicSound(event, 0, 0, true);
	}

	/**
	 * Boss Music Loops
	 */
	public static final MusicSound KING_TATER_LOOP = createBossMusic(KING_TATER);
	public static final MusicSound I509_LOOP = createBossMusic(I509);
	public static final MusicSound LEX_MANOS_LOOP = createBossMusic(LEX_MANOS);

	public static void initialize() {
		register(KING_TATER);
		register(I509);
		register(LEX_MANOS);
		register(MYSTICAL_FOREST);
		register(CORRUPT_FOREST);
		register(OCEAN);
		register(DAWN_SHARDLANDS);
		register(ETHEREAL_GROVES_OF_FRAIYA);
		register(CAVERNS);
		register(HERIA_AND_THE_TOWN_OF_HOPE);
		register(PAWERIA_CARIAR_OF_ORDER);
		register(STEELBRANCH);

		register(ADVANCEMENT);
		register(ACTIVATE_SHRINE);

		register(EASTERN_ROSELLA_IDLE);
	}

	private static void register(SoundEvent event) {
		Registry.register(Registry.SOUND_EVENT, ((LintSoundEvent) event).getCommonId(), event);
	}
}
