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

package me.hydos.lint.block.organic;

import me.hydos.lint.particle.Particles;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.block.BlockState;
import net.minecraft.block.LeavesBlock;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.World;

import java.util.Random;

public class LintLeavesBlock extends LeavesBlock {
	public LintLeavesBlock(Settings settings) {
		super(settings);
	}

	@Environment(EnvType.CLIENT)
	@Override
	public void randomDisplayTick(BlockState state, World world, BlockPos pos, Random random) {
		super.randomDisplayTick(state, world, pos, random);
		if (random.nextInt(15) == 1) {
			double x = (double) pos.getX() + random.nextDouble();
			double y = (double) pos.getY() + random.nextInt(1);
			double z = (double) pos.getZ() + random.nextDouble();
			if (world.isAir(pos.offset(Direction.DOWN))) {
				world.addParticle(Particles.FALLEN_MYSTICAL_LEAF, x, y, z, 0.0D, -0.2D, 0.0D);
			}
		}
	}
}
