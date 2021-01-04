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

package me.hydos.lint.util.math;

import net.minecraft.util.math.MathHelper;

public final class Vec2i {
	private final int x;
	private final int y;

	public Vec2i(int x, int y) {
		this.x = x;
		this.y = y;
	}

	public int getX() {
		return this.x;
	}

	public int getY() {
		return this.y;
	}

	public Vec2i add(int x, int y) {
		return new Vec2i(this.x + x, this.y + y);
	}

	public Vec2i add(Vec2i other) {
		return this.add(other.x, other.y);
	}

	public int squaredDist(int x, int y) {
		int dx = MathHelper.abs(this.x - x);
		int dy = MathHelper.abs(this.y - y);
		return dx * dx + dy * dy;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) {
			return true;
		} else if (o == null) {
			return false;
		} else if (o instanceof Vec2i) {
			Vec2i vec2i = (Vec2i) o;
			return vec2i.x == this.x && vec2i.y == this.y;
		} else {
			return false;
		}
	}

	@Override
	public int hashCode() {
		int result = 7;
		result = 29 * result + Integer.hashCode(this.x);
		result = 29 * result + Integer.hashCode(this.y);
		return result;
	}

	@Override
	public String toString() {
		return "Vec2i(" + this.x
				+ ", " + this.y
				+ ')';
	}
}