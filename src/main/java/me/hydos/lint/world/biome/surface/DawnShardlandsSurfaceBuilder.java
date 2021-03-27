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

package me.hydos.lint.world.biome.surface;

import java.util.Random;

import me.hydos.lint.block.LintBlocks;
import me.hydos.lint.refactord.block.LintBlocks2;
import net.minecraft.block.BlockState;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.chunk.Chunk;
import net.minecraft.world.gen.surfacebuilder.SurfaceBuilder;
import net.minecraft.world.gen.surfacebuilder.TernarySurfaceConfig;

public class DawnShardlandsSurfaceBuilder extends SurfaceBuilder<TernarySurfaceConfig> {
	private static final TernarySurfaceConfig ALLOS_CONFIG = new TernarySurfaceConfig(LintBlocks.ALLOS_INFUSED_ASPHALT.getDefaultState(), LintBlocks2.ASPHALT.getDefaultState(), LintBlocks2.ASPHALT.getDefaultState());
	private static final TernarySurfaceConfig MANOS_CONFIG = new TernarySurfaceConfig(LintBlocks.MANOS_INFUSED_ASPHALT.getDefaultState(), LintBlocks2.ASPHALT.getDefaultState(), LintBlocks2.ASPHALT.getDefaultState());

	public DawnShardlandsSurfaceBuilder() {
		super(TernarySurfaceConfig.CODEC);
	}

	@Override
	public void generate(Random random, Chunk chunk, Biome biome, int x, int z, int height, double noise, BlockState defaultBlock, BlockState defaultFluid, int seaLevel, long seed, TernarySurfaceConfig surfaceBlocks) {
		defaultBlock = LintBlocks2.ASPHALT.getDefaultState();

		if (noise > 2.3f && noise < 3.0f) {
			SurfaceBuilder.DEFAULT.generate(random, chunk, biome, x, z, height, noise, defaultBlock, defaultFluid, seaLevel, seed, ALLOS_CONFIG);
		} else if (noise < -2.3f && noise > -3.0f) {
			SurfaceBuilder.DEFAULT.generate(random, chunk, biome, x, z, height, noise, defaultBlock, defaultFluid, seaLevel, seed, MANOS_CONFIG);
		} else {
			SurfaceBuilder.DEFAULT.generate(random, chunk, biome, x, z, height, noise, defaultBlock, defaultFluid, seaLevel, seed, surfaceBlocks);
		}
	}
}
