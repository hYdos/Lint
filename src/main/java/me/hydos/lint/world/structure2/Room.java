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

package me.hydos.lint.world.structure2;

import me.hydos.lint.world.StructureWorld;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Box;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.Random;

public abstract class Room {
	protected final BlockPos startPos;
	private Box box;
	private boolean generated;

	protected Room(BlockPos startPos) {
		this.startPos = startPos;
	}

	public final void computeBounds(Random rand) {
		this.box = this.getBounds(rand);
	}

	public final void generate(StructureWorld world, Random rand) {
		this.generate(world, rand, this.box);
		this.generated = true;
	}

	@Nullable
	public final Box getBoundingBox() {
		return this.box;
	}

	public final BlockPos getStartPos() {
		return this.startPos;
	}

	public final boolean hasGenerated() {
		return this.generated;
	}

	protected abstract void generate(StructureWorld world, Random rand, final Box box);

	protected abstract Box getBounds(Random rand);

	protected abstract List<Room> computeNodes(Box box, Random rand);
}
