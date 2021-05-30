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

import me.hydos.lint.world.gen.FraiyaTerrainGenerator;
import me.hydos.lint.world.gen.OpenSimplexNoise;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.chunk.Chunk;
import net.minecraft.world.gen.surfacebuilder.SurfaceBuilder;
import net.minecraft.world.gen.surfacebuilder.TernarySurfaceConfig;

import java.util.Random;

public class OceanSurfaceBuilder extends SurfaceBuilder<TernarySurfaceConfig> {
    private static final OpenSimplexNoise NOISE = new OpenSimplexNoise(new Random(69));
    private static final OpenSimplexNoise NOISE_2 = new OpenSimplexNoise(new Random(420));
    private final TernarySurfaceConfig sand;
    private final TernarySurfaceConfig sand2;

    public OceanSurfaceBuilder(BlockState sand, BlockState sand2) {
        super(TernarySurfaceConfig.CODEC);
        this.sand = new TernarySurfaceConfig(sand, sand, sand);
        this.sand2 = new TernarySurfaceConfig(Blocks.AIR.getDefaultState(), sand2, sand2);
    }

    @Override
    public void generate(Random random, Chunk chunk, Biome biome, int x, int z, int height, double noise, BlockState defaultBlock, BlockState defaultFluid, int seaLevel, int i, long l, TernarySurfaceConfig surfaceConfig) {
        double limit = FraiyaTerrainGenerator.SEA_LEVEL + 1.2 + 1.5 * NOISE.sample(x * 0.022, z * 0.022);
        double limit2 = FraiyaTerrainGenerator.SEA_LEVEL + 1.2 + 1.5 * NOISE_2.sample(x * 0.022, z * 0.022);

        if (height < limit) {
            surfaceConfig = sand;
        } else if (height < limit2) {
            surfaceConfig = sand2;
        }

        SurfaceBuilder.DEFAULT.generate(random, chunk, biome, x, z, height, noise, defaultBlock, defaultFluid, seaLevel, i, l, surfaceConfig);
    }
}
