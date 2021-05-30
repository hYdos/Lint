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

package me.hydos.lint.entity.passive.bird;

import net.minecraft.entity.ai.control.MoveControl;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;

// Literally phantom move control
public class SoaringMoveControl extends MoveControl {
	private float targetSpeed = 0.1F;

	public SoaringMoveControl(MobEntity owner) {
		super(owner);
	}

	public void tick() {
		if (this.entity.horizontalCollision) {
			this.entity.setYaw(this.entity.getYaw() + 180.0F);
			this.targetSpeed = 0.1F;
		}

		Vec3d targetPosition = ((Soaring) this.entity).getTargetPosition();

		float f = (float) (targetPosition.x - this.entity.getX());
		float g = (float) (targetPosition.y - this.entity.getY());
		float h = (float) (targetPosition.z - this.entity.getZ());
		double d = MathHelper.sqrt(f * f + h * h);
		double e = 1.0D - (double) MathHelper.abs(g * 0.7F) / d;
		f = (float) ((double) f * e);
		h = (float) ((double) h * e);
		d = MathHelper.sqrt(f * f + h * h);
		double i = MathHelper.sqrt(f * f + h * h + g * g);
		float j = this.entity.getYaw();
		float k = (float) MathHelper.atan2(h, f);
		float l = MathHelper.wrapDegrees(this.entity.getYaw() + 90.0F);
		float m = MathHelper.wrapDegrees(k * 57.295776F);
		this.entity.setYaw(MathHelper.stepUnwrappedAngleTowards(l, m, 4.0F) - 90.0F);
		this.entity.bodyYaw = this.entity.getYaw();
		if (MathHelper.angleBetween(j, this.entity.getYaw()) < 3.0F) {
			this.targetSpeed = MathHelper.stepTowards(this.targetSpeed, 1.8F, 0.005F * (1.8F / this.targetSpeed));
		} else {
			this.targetSpeed = MathHelper.stepTowards(this.targetSpeed, 0.2F, 0.025F);
		}

		float n = (float) (-(MathHelper.atan2(-g, d) * 57.2957763671875D));
		this.entity.setPitch(n);
		float o = this.entity.getYaw() + 90.0F;
		double p = (double) (this.targetSpeed * MathHelper.cos(o * 0.017453292F)) * Math.abs((double) f / i);
		double q = (double) (this.targetSpeed * MathHelper.sin(o * 0.017453292F)) * Math.abs((double) h / i);
		double r = (double) (this.targetSpeed * MathHelper.sin(n * 0.017453292F)) * Math.abs((double) g / i);
		Vec3d vec3d = this.entity.getVelocity();
		this.entity.setVelocity(vec3d.add((new Vec3d(p, r, q)).subtract(vec3d).multiply(0.2D)));
	}

	public interface Soaring {
		Vec3d getTargetPosition();
	}
}
