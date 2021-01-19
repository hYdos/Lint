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

package me.hydos.lint.entity.goal;

import java.util.EnumSet;

import net.minecraft.entity.ai.goal.Goal;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.tag.FluidTags;

public class FlyOutOfWaterGoal extends Goal {
	private final MobEntity mob;
	private double targetX;
	private double targetY;
	private double targetZ;

	public FlyOutOfWaterGoal(MobEntity mob) {
		this.mob = mob;
		this.setControls(EnumSet.of(Goal.Control.JUMP));
		mob.getNavigation().setCanSwim(true);
	}

	@Override
	public boolean canStart() {
		if (this.mob.isTouchingWater() && this.mob.getFluidHeight(FluidTags.WATER) > this.mob.method_29241() || this.mob.isInLava()) {
			this.targetX = this.mob.getX() - 6.0 + 12.0 * this.mob.getRandom().nextDouble();
			this.targetZ = this.mob.getZ() - 6.0 + 12.0 * this.mob.getRandom().nextDouble();
			this.targetY = this.mob.getEntityWorld().getSeaLevel() + 5;
			return true;
		} else {
			return false;
		}
	}

	@Override
	public void start() {
		this.mob.getNavigation().startMovingTo(this.targetX, this.targetY, this.targetZ, this.mob.getAttributeBaseValue(EntityAttributes.GENERIC_FLYING_SPEED));
	}

	@Override
	public boolean shouldContinue() {
		return !this.mob.getNavigation().isIdle() && !this.mob.hasPassengers();
	}

	@Override
	public void stop() {
		this.mob.getNavigation().stop();
	}
}

