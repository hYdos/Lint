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

package me.hydos.lint.world.carver;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.util.math.floatprovider.FloatProvider;
import net.minecraft.world.gen.YOffset;
import net.minecraft.world.gen.carver.CaveCarverConfig;
import net.minecraft.world.gen.heightprovider.HeightProvider;

public class LintCaveCarverConfig extends CaveCarverConfig {
	public static final Codec<LintCaveCarverConfig> CAVE_CODEC = RecordCodecBuilder.create(instance -> instance.group(
			HeightProvider.CODEC.fieldOf("y").forGetter(config -> config.y),
			FloatProvider.VALUE_CODEC.fieldOf("yScale").forGetter(config -> config.yScale),
			YOffset.OFFSET_CODEC.fieldOf("lava_level").forGetter(config -> config.lavaLevel),
			Codec.BOOL.fieldOf("aquifers_enabled").forGetter(config -> config.aquifers),
			FloatProvider.VALUE_CODEC.fieldOf("horizontal_radius_multiplier").forGetter(config -> config.horizontalRadiusMultiplier),
			FloatProvider.VALUE_CODEC.fieldOf("vertical_radius_multiplier").forGetter(config -> config.verticalRadiusMultiplier),
			FloatProvider.VALUE_CODEC.fieldOf("floor_level").forGetter(config -> config.floorLevel)
	).apply(instance, LintCaveCarverConfig::new));

	public LintCaveCarverConfig(HeightProvider y, FloatProvider yScale, YOffset lavaLevel, boolean aquifers, FloatProvider horizontalRadiusMultiplier, FloatProvider verticalRadiusMultiplier, FloatProvider floorLevel) {
		super(0.09F, y, yScale, lavaLevel, aquifers, horizontalRadiusMultiplier, verticalRadiusMultiplier, floorLevel);
	}
}
