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

package me.hydos.lint.multiblock;

import java.util.ArrayList;
import java.util.Collection;

import org.jetbrains.annotations.Nullable;

import me.hydos.lint.util.GridDirection;
import net.minecraft.block.Block;
import net.minecraft.tag.Tag;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Box;
import net.minecraft.util.math.Vec3i;
import net.minecraft.world.WorldAccess;

/**
 * A type of multiblock. Specifies the tag and shape.
 */
public final class MultiblockType {
	public MultiblockType(Tag<Block> block) {
		this.block = block;
	}

	public final Tag<Block> block;
	private final Collection<Vec3i> shape = new ArrayList<>();
	private BlockPos.Mutable min; // it's a mutable vec3i that's all I need
	private BlockPos.Mutable max;

	/**
	 * Adds a position relative to the controller.
	 * @param xo the relative-rotated x offset
	 * @param yo the y offset
	 * @param zo the relative-rotated z offset
	 * @return this
	 */
	public MultiblockType addPosition(int xo, int yo, int zo) {
		if (this.shape.isEmpty()) {
			this.min = new BlockPos.Mutable().set(xo, yo, zo);
			this.max = new BlockPos.Mutable().set(xo, yo, zo);
		}

		this.shape.add(new Vec3i(xo, yo, zo));
			if (xo < this.min.getX()) {
				this.min.setX(xo);
			} else if (xo > this.max.getX()) {
				this.max.setX(xo);
			}

			if (yo < this.min.getY()) {
				this.min.setY(yo);
			} else if (yo > this.max.getY()) {
				this.max.setY(yo);
			}

			if (zo < this.min.getZ()) {
				this.min.setZ(zo);
			} else if (zo > this.max.getZ()) {
				this.max.setZ(zo);
			}
		return this;
	}

	@Nullable
	public Multiblock find(WorldAccess world, BlockPos controllerPos) {
		BlockPos.Mutable pos = new BlockPos.Mutable();
		final int startX = controllerPos.getX();
		final int startY = controllerPos.getY();
		final int startZ = controllerPos.getZ();

		for (GridDirection direction : GridDirection.values()) {
			shapeSearch: {
				for (Vec3i offset : this.shape) {
					if (direction.horizontal) {
						pos.set(startX + direction.off * offset.getX(), startY + offset.getY(), startZ + direction.off * offset.getZ());
					} else {
						pos.set(startX + direction.off * offset.getZ(), startY + offset.getY(), startZ + direction.off * offset.getX());
					}
	
					if (!world.getBlockState(pos).isIn(this.block)) {
						break shapeSearch;
					}
				}
				
				BlockPos min;
				BlockPos max;

				if (direction.horizontal) {
					min = new BlockPos(startX + direction.off * this.min.getX(), startY + this.min.getY(), startZ + direction.off * this.min.getZ());
					max = new BlockPos(startX + direction.off * this.max.getX(), startY + this.max.getY(), startZ + direction.off * this.max.getZ());
				} else {
					min = new BlockPos(startX + direction.off * this.min.getZ(), startY + this.min.getY(), startZ + direction.off * this.min.getX());
					max = new BlockPos(startX + direction.off * this.max.getZ(), startY + this.max.getY(), startZ + direction.off * this.max.getX());
				}

				Box box = new Box(min, max);
				return new Multiblock(box, controllerPos, new BlockPos(box.getCenter()));
			}
		}

		return null;
	}
}
