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
import me.hydos.lint.block.LintBlocks;
import net.minecraft.util.math.floatprovider.ConstantFloatProvider;
import net.minecraft.util.math.floatprovider.UniformFloatProvider;
import net.minecraft.util.registry.BuiltinRegistries;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.gen.YOffset;
import net.minecraft.world.gen.carver.CarverDebugConfig;
import net.minecraft.world.gen.carver.ConfiguredCarver;
import net.minecraft.world.gen.heightprovider.BiasedToBottomHeightProvider;

// FIXME: cave carvers
public interface LintConfiguredCarvers {
	ConfiguredCarver<LintCaveCarverConfig> NEBULAES_BANE = Registry.register(BuiltinRegistries.CONFIGURED_CARVER,
			Lint.id("nebulaes_bane"),
			LintCaveCarver.INSTANCE.configure(
					new LintCaveCarverConfig(
							0.09F,
							BiasedToBottomHeightProvider.create(YOffset.fixed(0), YOffset.fixed(127), 8),
							ConstantFloatProvider.create(0.5F),
							YOffset.aboveBottom(10),
							false,
							CarverDebugConfig.create(false, LintBlocks.HAYKAMIUM_PORTAL.getDefaultState()),
							UniformFloatProvider.create(2.4F, 8.0F),
							UniformFloatProvider.create(3.6F, 12.0F),
							UniformFloatProvider.create(-1.0F, -0.4F))));
}
