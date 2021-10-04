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

package me.hydos.lint.world.dimension;

import me.hydos.lint.Lint;
import net.minecraft.util.registry.Registry;
import net.minecraft.util.registry.RegistryKey;
import net.minecraft.world.World;
import net.minecraft.world.dimension.DimensionOptions;
import net.minecraft.world.dimension.DimensionType;

public class LintDimensions {
	/**
	 * Dimension Registry Keys
	 */
	public static final RegistryKey<DimensionOptions> FRAIYA_DIM_OPTIONS = RegistryKey.of(Registry.DIMENSION_KEY, Lint.id("fraiya"));
	public static final RegistryKey<DimensionType> FRAIYA_DIM = RegistryKey.of(Registry.DIMENSION_TYPE_KEY, Lint.id("fraiya"));
	public static final RegistryKey<World> FRAIYA_WORLD = RegistryKey.of(Registry.WORLD_KEY, Lint.id("fraiya"));
}
