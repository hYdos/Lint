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

import me.hydos.lint.world.feature.util.WorldModifier;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.StructureWorldAccess;
import net.minecraft.world.gen.feature.DefaultFeatureConfig;

public class AsphaltIslandGenerator implements WorldModifier<DefaultFeatureConfig> {
	@Override
	public boolean place(GenerationSettings<DefaultFeatureConfig> settings) {
		// Literally copy paste from End Isladn Generator
		// With block changed
		StructureWorldAccess world = settings.world;
		Random random = settings.random;
		BlockPos blockPos = settings.origin;
		float f = (float)(random.nextInt(3) + 4);

		for(int i = 0; f > 0.5F; --i) {
			for(int j = MathHelper.floor(-f); j <= MathHelper.ceil(f); ++j) {
				for(int k = MathHelper.floor(-f); k <= MathHelper.ceil(f); ++k) {
					if ((float)(j * j + k * k) <= (f + 1.0F) * (f + 1.0F)) {
						world.setBlockState(blockPos.add(j, i, k), Blocks.END_STONE.getDefaultState(), Block.NOTIFY_ALL | Block.FORCE_STATE);
					}
				}
			}

			f = (float)((double)f - ((double)random.nextInt(2) + 0.5D));
		}

		return true;
	}

	@Override
	public String id() {
		return "asphalt_island";
	}
}
