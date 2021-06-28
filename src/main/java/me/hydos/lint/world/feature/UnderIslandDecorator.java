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

import me.hydos.lint.Lint;
import me.hydos.lint.world.gen.terrain.TerrainChunkGenerator;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.gen.decorator.Decorator;
import net.minecraft.world.gen.decorator.DecoratorContext;
import net.minecraft.world.gen.decorator.NopeDecoratorConfig;

import java.util.Random;
import java.util.stream.Stream;

public class UnderIslandDecorator extends Decorator<NopeDecoratorConfig> {
	public UnderIslandDecorator() {
		super(NopeDecoratorConfig.CODEC);
	}

	@Override
	public Stream<BlockPos> getPositions(DecoratorContext context, Random random, NopeDecoratorConfig config, BlockPos pos) {
		int x = pos.getX();
		int z = pos.getZ();

		try {
			int y = ((TerrainChunkGenerator) context.generator).getLowerGenBound(x, z) - 1;
			return y > 0 ? Stream.of(new BlockPos(x, y, z)) : Stream.of();
		} catch (ClassCastException e) {
			Lint.LOGGER.warn("Under Island Decorator can only be used with lint's chunk generators.");
			return Stream.of();
		}
	}
}
