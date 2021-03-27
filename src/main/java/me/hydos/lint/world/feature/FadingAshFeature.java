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

import java.util.Random;

import me.hydos.lint.block.LintBlocks;
import me.hydos.lint.refactord.block.LintBlocks2;
import me.hydos.lint.world.gen.FraiyaTerrainGenerator;
import net.minecraft.block.BlockState;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3i;
import net.minecraft.world.Heightmap;
import net.minecraft.world.StructureWorldAccess;
import net.minecraft.world.chunk.Chunk;
import net.minecraft.world.gen.chunk.ChunkGenerator;
import net.minecraft.world.gen.feature.DefaultFeatureConfig;
import net.minecraft.world.gen.feature.Feature;

public class FadingAshFeature extends Feature<DefaultFeatureConfig> {
	private static final BlockState ASH = LintBlocks.ASH.getDefaultState();
	private static final BlockState ASPHALT = LintBlocks2.ASPHALT.getDefaultState();

	public FadingAshFeature() {
		super(DefaultFeatureConfig.CODEC);
	}

	@Override
	public boolean generate(StructureWorldAccess world, ChunkGenerator chunkGenerator, Random random, BlockPos start, DefaultFeatureConfig config) {
		double dist = start.getSquaredDistance(Vec3i.ZERO);

		if (dist > FraiyaTerrainGenerator.ASH_START) {
			final int bound = dist > FraiyaTerrainGenerator.DENSE_ASH_START ? 7 : 14;

			BlockPos.Mutable pos = new BlockPos.Mutable();
			Chunk chunk = world.getChunk(start);

			for (int xo = 0; xo < 16; ++xo) {
				pos.setX(start.getX() + xo);

				for (int zo = 0; zo < 16; ++zo) {
					pos.setZ(start.getZ() + zo);

					if (random.nextInt(bound) == 0) {
						pos.setY(chunk.sampleHeightmap(Heightmap.Type.OCEAN_FLOOR_WG, xo, zo));
						this.setBlockState(world, pos, random.nextInt(3) == 0 ? ASPHALT : ASH);
					}
				}
			}
		}

		return true;
	}
}
