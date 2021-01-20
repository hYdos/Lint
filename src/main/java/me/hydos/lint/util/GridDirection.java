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

package me.hydos.lint.util;

import java.util.Random;

import net.minecraft.util.math.Direction;

public enum GridDirection {
	UP(0, 0, 1, false, false, Direction.SOUTH),
	RIGHT(1, 1, 0, true, false, Direction.EAST),
	DOWN(2, 0, -1, false, true, Direction.NORTH),
	LEFT(3, -1, 0, true, true, Direction.WEST);

	public static final GridDirection[] BY_ID = new GridDirection[4];

	static {
		for (GridDirection d : GridDirection.values()) {
			BY_ID[d.id] = d;
		}
	}

	public final int id;
	public final int xOff, zOff;
	public final boolean horizontal;
	public final boolean mirror;
	public final int off;
	public final Direction direction;

	GridDirection(int id, int xOff, int zOff, boolean horizontal, boolean mirror, Direction direction) {
		this.id = id;
		this.xOff = xOff;
		this.zOff = zOff;
		this.horizontal = horizontal;
		this.mirror = mirror;
		this.off = mirror ? -1 : 1;
		this.direction = direction;
	}

	public GridDirection reverse() {
		switch (this) {
			case UP:
				return DOWN;
			case DOWN:
				return UP;
			case LEFT:
				return RIGHT;
			case RIGHT:
				return LEFT;
			default:
				return null;
		}
	}

	public static GridDirection random(Random rand) {
		return values()[rand.nextInt(4)];
	}
}