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

public final class Vec2f {
	private final float x;
	private final float y;

	public Vec2f(Vec2f other) {
		this(other.x, other.y);
	}
	public Vec2f(float x, float y) {
		this.x = x;
		this.y = y;
	}

	public float getX() {
		return this.x;
	}

	public float getY() {
		return this.y;
	}

	public Vec2f add(float x, float y) {
		return new Vec2f(this.x + x, this.y + y);
	}

	public Vec2f add(Vec2f other) {
		return this.add(other.x, other.y);
	}

	public float squaredDist(Vec2f other) {
		return this.squaredDist(other.x, other.y);
	}

	public float squaredDist(float x, float y) {
		float dx = Math.abs(x - this.x);
		float dy = Math.abs(y - this.y);
		return dx * dx + dy * dy;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) {
			return true;
		} else if (o == null) {
			return false;
		} else if (o instanceof Vec2f) {
			Vec2f vec2f = (Vec2f) o;
			return vec2f.x == this.x && vec2f.y == this.y;
		} else {
			return false;
		}
	}

	@Override
	public int hashCode() {
		int result = 7;
		result = 29 * result + Float.hashCode(this.x);
		result = 29 * result + Float.hashCode(this.y);
		return result;
	}

	@Override
	public String toString() {
		return "Vec2f(" + this.x
				+ ", " + this.y
				+ ')';
	}
}