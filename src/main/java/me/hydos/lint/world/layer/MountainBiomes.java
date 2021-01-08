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

package me.hydos.lint.world.layer;

import me.hydos.lint.world.biome.Biomes;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.registry.Registry;
import net.minecraft.util.registry.RegistryKey;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.layer.type.InitLayer;
import net.minecraft.world.biome.layer.util.LayerRandomnessSource;

public class MountainBiomes implements InitLayer {
	@SuppressWarnings("rawtypes")
	private static final RegistryKey[] BIOMES = {Biomes.MYSTICAL_GROVE_KEY, Biomes.MYSTICAL_GROVE_KEY, Biomes.MYSTICAL_FOREST_KEY};
	@SuppressWarnings("rawtypes")
	private static final RegistryKey[] CORRUPT_BIOMES = {Biomes.CORRUPT_FOREST_KEY, Biomes.INDIGO_RIDGES_KEY};

	private final Registry<Biome> biomeRegistry;

	public MountainBiomes(Registry<Biome> biomeRegistry) {
		this.biomeRegistry = biomeRegistry;
	}

	@Override
	public int sample(LayerRandomnessSource randomPawn, int x, int y) {
		boolean corrupt = false;
		int absx = MathHelper.abs(x);
		int absy = MathHelper.abs(y);
		int absval = absx + absy;

		if (absval > 1 && randomPawn.nextInt(3) == 0) {
			corrupt = true;
		}

		return id(corrupt ?
				CORRUPT_BIOMES[randomPawn.nextInt(CORRUPT_BIOMES.length)] :
				BIOMES[randomPawn.nextInt(BIOMES.length)]);
	}

	@SuppressWarnings({"rawtypes", "unchecked"})
	private int id(RegistryKey key) {
		return biomeRegistry.getRawId(biomeRegistry.get(key));
	}
}