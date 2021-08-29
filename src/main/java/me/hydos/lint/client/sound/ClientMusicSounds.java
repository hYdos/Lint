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

package me.hydos.lint.client.sound;

import me.hydos.lint.sound.Sounds;
import me.hydos.lint.world.biome.Biomes;
import net.minecraft.sound.MusicSound;
import net.minecraft.sound.SoundEvent;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.BiomeEffects;
import net.minecraft.world.biome.GenerationSettings;
import static me.hydos.lint.sound.BiomeMusicSounds.createAmbient;

public class ClientMusicSounds {
	static final Biome createLoop(SoundEvent event) {
		BiomeEffects.Builder effects = new BiomeEffects.Builder()
				.waterColor(0x32e686)
				.waterFogColor(0x32e686)
				.fogColor(0xffffff)
				.skyColor(0x88dfea);

		effects.loopSound(event);

		return new Biome.Builder()
				.precipitation(Biome.Precipitation.NONE)
				.category(Biome.Category.FOREST)
				.depth(0.125f)
				.scale(1)
				.temperature(0.8f)
				.downfall(0)
				.spawnSettings(Biomes.DEFAULT_SPAWN_SETTINGS.build())
				.generationSettings(new GenerationSettings.Builder()
						.surfaceBuilder(Biomes.MF_SB)
						.build())
				.effects(effects.build())
				.build();
	}

	// Towns
	public static final MusicSound TOWN_THERIA = createAmbient(Sounds.EYE_OF_GOLD);
	public static final MusicSound TOWN_AURIA = createAmbient(Sounds.STEELBRANCH);
	public static final MusicSound TOWN_PAWERIA = createAmbient(Sounds.PAWERIA_CARIAR_OF_ORDER);
	public static final MusicSound TOWN_HERIA = createAmbient(Sounds.TOWN_OF_HOPE);

	// Locations
	public static final MusicSound CAVERNS = createAmbient(Sounds.CAVERNS);
	public static final MusicSound DUNGEON = createAmbient(Sounds.DUNGEON);

	// in the correct order to designate whose town is whose.
	public static final MusicSound[] TOWNS = new MusicSound[]{TOWN_PAWERIA, TOWN_HERIA, TOWN_AURIA, TOWN_THERIA};
}
