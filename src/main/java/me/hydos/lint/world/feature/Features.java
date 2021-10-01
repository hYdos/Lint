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

package me.hydos.lint.world.feature;

import me.hydos.lint.block.LintBlocks;
import me.hydos.lint.world.feature.util.FeatureFactory;
import me.hydos.lint.world.feature.util.Placement;
import net.minecraft.world.gen.feature.ConfiguredFeature;
import net.minecraft.world.gen.feature.FillLayerFeatureConfig;

// TODO move features to this abstractified system because unneccesary abstractions are funi
public class Features {
	public static ConfiguredFeature<?,?> SHARDLANDS_SPIKES = Placement.CHANCE_SIMPLE.apply(
			"shardlands_spikes",
			FeatureFactory.register("gargantuan_spikes", new SpikesGenerator(), new FillLayerFeatureConfig(110, LintBlocks.ASPHALT.getDefaultState())),
			12);
}
