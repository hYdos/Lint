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

import me.hydos.lint.world.biome.Biomes;
import net.minecraft.sound.SoundEvent;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.BiomeEffects;
import net.minecraft.world.biome.GenerationSettings;

public class DummyBiomes {
	private static final Biome createLoopSound(SoundEvent event) {
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
				.effects(new BiomeEffects.Builder()
						.waterColor(0x32e686)
						.waterFogColor(0x32e686)
						.fogColor(0xffffff)
						.loopSound(event)
						.skyColor(0x88dfea)
						.build())
				.build();
	}

	// Towns
	public static final Biome DUMMY_TOWN_THERIA = createLoopSound(Sounds.EYE_OF_GOLD);
	public static final Biome DUMMY_TOWN_AURIA = createLoopSound(Sounds.STEELBRANCH);
	public static final Biome DUMMY_TOWN_PAWERIA = createLoopSound(Sounds.PAWERIA_CARIAR_OF_ORDER);
	public static final Biome DUMMY_TOWN_HERIA = createLoopSound(Sounds.HERIA_AND_THE_TOWN_OF_HOPE);
 
	// Locations
	public static final Biome DUMMY_CAVERNS = createLoopSound(Sounds.CAVERNS);
	public static final Biome DUMMY_DUNGEON = createLoopSound(Sounds.DUNGEON);

	// Alternative Tunes
	public static final Biome DUMMY_MYSTICAL_FOREST_ALTER = createLoopSound(Sounds.ETHEREAL_GROVES);

	// Boss Music
	public static final Biome DUMMY_KING_TATER = createLoopSound(Sounds.KING_TATER);
	public static final Biome DUMMY_I509 = createLoopSound(Sounds.I509);

	// in the correct order to designate whose town is whose.
	public static final Biome[] TOWNS = new Biome[] {DUMMY_TOWN_PAWERIA, DUMMY_TOWN_HERIA, DUMMY_TOWN_AURIA, DUMMY_TOWN_THERIA};
}
