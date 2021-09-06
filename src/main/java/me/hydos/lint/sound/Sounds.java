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
import net.minecraft.sound.SoundEvent;
import net.minecraft.util.registry.Registry;

// Todo only have boss music use the loop override system

public class Sounds {
	/**
	 * Boss Music
	 */
	public static final SoundEvent KING_TATER = new SoundEvent(Lint.id("music.king_tater_boss_theme"));
	public static final SoundEvent I509 = new SoundEvent(Lint.id("music.i509_boss_theme"));
	public static final SoundEvent LEX_MANOS = new SoundEvent(Lint.id("music.lex_manos_boss_theme"));

	/**
	 * Music Discs
	 */
	public static final SoundEvent GRIMACE_OBOE = new SoundEvent(Lint.id("music.grimace"));
	public static final SoundEvent GRIMACE_VIOLINS = new SoundEvent(Lint.id("music.grimace_violins"));
	public static final SoundEvent MANOS_TOUCH = new SoundEvent(Lint.id("music.manos_touch"));
	public static final SoundEvent KING_TATER_DISC = new SoundEvent(Lint.id("music.king_tater_disc"));
	/**
	 * Biome Music Discs that are separate to the normal biome music events.
	 */
	public static final SoundEvent MYSTICAL_FOREST_DISC = new SoundEvent(Lint.id("music.mystical_forest_disc"));
	public static final SoundEvent CORRUPT_FOREST_DISC = new SoundEvent(Lint.id("music.corrupt_forest_disc"));

	/**
	 * Biome Music
	 */
	public static final SoundEvent MYSTICAL_FOREST = new SoundEvent(Lint.id("music.mystical_forest"));
	public static final SoundEvent CORRUPT_FOREST = new SoundEvent(Lint.id("music.corrupt_forest"));
	public static final SoundEvent DAWN_SHARDLANDS = new SoundEvent(Lint.id("music.dawn_shardlands"));
	public static final SoundEvent FROZEN_FOREST = new SoundEvent(Lint.id("music.frozen_forest"));
	public static final SoundEvent OCEAN = new SoundEvent(Lint.id("music.ocean"));
	public static final SoundEvent VOYAGE = new SoundEvent(Lint.id("music.voyage"));

	/**
	 * Other Ambient Music.
	 */
	public static final SoundEvent DUNGEON = new SoundEvent(Lint.id("music.clandestine"));
	public static final SoundEvent CAVERNS = new SoundEvent(Lint.id("music.caverns"));
	public static final SoundEvent TOWN_OF_HOPE = new SoundEvent(Lint.id("music.town_of_hope"));
	public static final SoundEvent PAWERIA_CARIAR_OF_ORDER = new SoundEvent(Lint.id("music.paweria_cariar_of_order"));
	public static final SoundEvent STEELBRANCH = new SoundEvent(Lint.id("music.steelbranch"));
	public static final SoundEvent EYE_OF_GOLD = new SoundEvent(Lint.id("music.eye_of_gold"));

	/**
	 * Misc Sounds
	 */
	public static final SoundEvent ADVANCEMENT = new SoundEvent(Lint.id("player.advancement"));
	public static final SoundEvent SHRINE_ACTIVATE = new SoundEvent(Lint.id("player.shrine_activate"));

	/**
	 * Entity Sounds
	 */
	public static final SoundEvent EASTERN_ROSELLA_IDLE = new SoundEvent(Lint.id("eastern_rosella.idle"));
	public static final SoundEvent NIGHTCLAW_IDLE = new SoundEvent(Lint.id("nightclaw.idle"));
	public static final SoundEvent CRAB_IDLE = new SoundEvent(Lint.id("crab.idle"));

	public static void initialize() {
		// boss music
		register(KING_TATER);
		register(I509);
		register(LEX_MANOS);

		// music discs
		register(GRIMACE_OBOE);
		register(GRIMACE_VIOLINS);
		register(MANOS_TOUCH);
		register(KING_TATER_DISC);
		register(MYSTICAL_FOREST_DISC);
		register(CORRUPT_FOREST_DISC);
		register(FROZEN_FOREST);

		// ambient music
		register(MYSTICAL_FOREST);
		register(CORRUPT_FOREST);
		register(OCEAN);
		register(VOYAGE);
		register(DAWN_SHARDLANDS);

		register(DUNGEON);
		register(CAVERNS);

		register(TOWN_OF_HOPE);
		register(PAWERIA_CARIAR_OF_ORDER);
		register(STEELBRANCH);
		register(EYE_OF_GOLD);

		// progression related short tunes
		register(ADVANCEMENT);
		register(SHRINE_ACTIVATE);

		// sounds
		register(EASTERN_ROSELLA_IDLE);
		register(NIGHTCLAW_IDLE);
		register(CRAB_IDLE);
	}

	private static void register(SoundEvent event) {
		Registry.register(Registry.SOUND_EVENT, event.id, event);
	}
}
