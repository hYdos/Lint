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

import net.minecraft.util.math.BlockPos;
import net.minecraft.world.StructureWorldAccess;
import net.minecraft.world.gen.feature.Feature;
import net.minecraft.world.gen.feature.SingleStateFeatureConfig;
import net.minecraft.world.gen.feature.util.FeatureContext;

public class HangingBlockFeature extends Feature<SingleStateFeatureConfig> {
	public HangingBlockFeature() {
		super(SingleStateFeatureConfig.CODEC);
	}

	@Override
	public boolean generate(FeatureContext<SingleStateFeatureConfig> context) {
		StructureWorldAccess world = context.getWorld();
		BlockPos start = context.getOrigin();
		SingleStateFeatureConfig config = context.getConfig();

		BlockPos.Mutable pos = new BlockPos.Mutable().set(start);
		BlockPos.Mutable next = new BlockPos.Mutable().set(start);
		final int startY = pos.getY();

		for (int i = 1; i < 6; ++i) {
			next.setY(startY + i);

			if (world.isAir(pos) && world.getBlockState(next).isFullCube(world, next)) {
				this.setBlockState(world, pos, config.state);
				return true;
			}

			pos.set(next);
		}

		return false;
	}
}
