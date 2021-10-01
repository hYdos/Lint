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

import me.hydos.lint.world.feature.util.WorldModifier;
import net.minecraft.block.Block;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.StructureWorldAccess;
import net.minecraft.world.gen.feature.FillLayerFeatureConfig;

public class SpikesGenerator implements WorldModifier<FillLayerFeatureConfig>{
	@Override
	public boolean place(GenerationSettings<FillLayerFeatureConfig> settings) {
		// todo maybe these only generate in the void areas between islands in shardlands. place them there. That would be done in the placement.

		// LOL GET OVERUSE-OF-FINAL-ED
		// rip to the eyes of whomever reads this code

		final int startX = settings.origin.getX();
		final int startZ = settings.origin.getZ();
		final int halfBaseHeight = settings.config.height / 2;

		// we want it to generate pointing out a bit
		// the +0.5 is to make it target the centre of a block to try not imbalance pointing left or right because d2i floors positives and ceils negatives
		// clamp due to limitations in worldgen. might gen after chunk is done or something in the future in order to avoid this idk
		final double endX = startX + MathHelper.clamp(settings.random.nextInt(settings.config.height) - halfBaseHeight + 0.5, -24, 24);
		final double endZ = startZ + MathHelper.clamp(settings.random.nextInt(settings.config.height) - halfBaseHeight + 0.5, -24, 24);

		// chonky
		// max thickness is 10 because of cringe gen restrictions.
		final int height = settings.random.nextInt(halfBaseHeight) + settings.config.height;
		final int thiccness = Math.min(10, settings.random.nextInt(Math.max(3,settings.config.height / 10)) + (settings.config.height / 8) + 2);

		// ok we lied earlier here's the *real* starting points
		final int sx_0 = startX - thiccness; // start x 0 (where 1 = higher (positive increment) and 0 = lower (negative). it's a bit but in variable name you see)
		final int sz_0 = startZ - thiccness;

		final int sx_1 = startX + thiccness;
		final int sz_1 = startZ + thiccness;

		BlockPos.Mutable pos = new BlockPos.Mutable();
		StructureWorldAccess world = settings.world;

		for (int y = 0; y <= height; ++y) {
			pos.setY(y);

			// compute starting positions for given height. y/height gives the proportion of the way done we are since y is range 0 to height.
			final double prog = (double)y / (double)height; // progress

			final int x_0 = (int) MathHelper.lerp(prog, sx_0, endX);
			final int z_0 = (int) MathHelper.lerp(prog, sz_0, endZ);
			final int x_1 = (int) MathHelper.lerp(prog, sx_1, endX);
			final int z_1 = (int) MathHelper.lerp(prog, sz_1, endZ);

			for (int x = x_0; x <= x_1; ++x) {
				pos.setX(x);
				
				for (int z = z_0; z <= z_1; ++z) {
					pos.setZ(z);

					world.setBlockState(pos, settings.config.state, Block.NOTIFY_ALL | Block.FORCE_STATE);
				}				
			}
		}

		return true;
	}

	@Override
	public String id() {
		return "gargantuan_spikes";
	}
}
