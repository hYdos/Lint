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

package lint.mana.block;

import lint.mana.ManaStorage;
import lint.mana.ManaType;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.ShapeContext;
import net.minecraft.entity.Entity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;

public class ChargepadBlock extends Block {

	private static final VoxelShape SHAPE = createCuboidShape(0, 0, 0, 16, 2, 16);

	private final boolean chargeEntity;

	public ChargepadBlock(Settings settings, boolean chargeEntity) {
		super(settings);
		this.chargeEntity = chargeEntity;
	}

	@Override
	public VoxelShape getOutlineShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
		return super.getOutlineShape(state, world, pos, context);
	}

	@Override
	public void onSteppedOn(World world, BlockPos pos, BlockState state, Entity entity) {
		if (!world.isClient()) {
			ManaStorage entityStorage = ManaStorage.of(entity, ManaType.GENERIC);
			ManaStorage attachedStorage = ManaStorage.BLOCK.find(world, pos.down(), ManaType.GENERIC);

			if (attachedStorage != null) {
				if (chargeEntity) {
					ManaStorage.move(attachedStorage, entityStorage, 100, null);
				} else {
					ManaStorage.move(entityStorage, attachedStorage, 100, null);
				}
			}
		}
	}
}
