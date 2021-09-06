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

import net.minecraft.sound.MusicSound;
import net.minecraft.sound.SoundEvent;

/**
 * Collection of Music Sounds that are attributed to specific biomes. These are done commonly, because they must be constructed as part of the biome.
 */
public class BiomeMusicSounds {
	// significantly more often than vanilla due to the more atmospheric purpose of the music, but never an entire loop because that's dumb
	// this is very approximately 3-6.5 minutes
	public static MusicSound createAmbient(SoundEvent event) {
		return new MusicSound(event, 3500, 8000, false);
	}

	public static final MusicSound MYSTICAL_FOREST = createAmbient(Sounds.MYSTICAL_FOREST);
	public static final MusicSound FROZEN_FOREST = createAmbient(Sounds.FROZEN_FOREST);
	public static final MusicSound CORRUPT_FOREST = createAmbient(Sounds.CORRUPT_FOREST);
	public static final MusicSound OCEAN = createAmbient(Sounds.OCEAN);
	public static final MusicSound DAWN_SHARDLANDS = createAmbient(Sounds.DAWN_SHARDLANDS);
}
