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

import me.hydos.lint.block.LintBlocks;
import net.minecraft.block.BlockState;
import net.minecraft.block.FlowerBlock;
import net.minecraft.block.ShapeContext;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.util.shape.VoxelShapes;
import net.minecraft.world.BlockView;
import org.jetbrains.annotations.Nullable;

public class LintFlowerBlock extends FlowerBlock implements PlaceablePlant {
	private final VoxelShape shape;

	public LintFlowerBlock(StatusEffect effect, Settings settings) {
		this(effect, settings, null);
	}

	public LintFlowerBlock(StatusEffect effect, Settings settings, @Nullable VoxelShape shape) {
		super(effect, 7, settings);
		this.shape = shape;
	}

	@Override
	public BlockState getGrowsOn() {
		return LintBlocks.LIVELY_GRASS.getDefaultState();
	}

	@Override
	protected boolean canPlantOnTop(BlockState floor, BlockView view, BlockPos pos) {
		return floor == this.getGrowsOn();
	}

	@Override
	public VoxelShape getOutlineShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
		return this.shape == null ? super.getOutlineShape(state, world, pos, context) : this.shape;
	}

	public static class Taterbane extends LintFlowerBlock {
		public Taterbane(Settings settings) {
			super(StatusEffects.NAUSEA, settings, VoxelShapes.cuboid(0.125, 0.0, 0.125, 0.875, 0.5, 0.875));
		}

		@Override
		public OffsetType getOffsetType() {
			return OffsetType.NONE;
		}
	}
}
