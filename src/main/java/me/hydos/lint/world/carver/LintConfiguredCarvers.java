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

import me.hydos.lint.Lint;
import net.minecraft.util.math.floatprovider.ConstantFloatProvider;
import net.minecraft.util.registry.BuiltinRegistries;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.gen.YOffset;
import net.minecraft.world.gen.carver.ConfiguredCarver;
import net.minecraft.world.gen.heightprovider.BiasedToBottomHeightProvider;

public interface LintConfiguredCarvers {
	ConfiguredCarver<LintCaveCarverConfig> CAVE = Registry.register(BuiltinRegistries.CONFIGURED_CARVER, Lint.id("nebulaes_bane"), LintCaveCarver.CAVE.configure(new LintCaveCarverConfig(BiasedToBottomHeightProvider.create(YOffset.fixed(0), YOffset.fixed(127), 8), ConstantFloatProvider.create(0.5F), YOffset.aboveBottom(10), false, ConstantFloatProvider.create(0.5F), ConstantFloatProvider.create(0.5F), ConstantFloatProvider.create(-0.7F))));
}
