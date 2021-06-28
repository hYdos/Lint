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

import net.minecraft.entity.ai.goal.Goal;

import java.util.EnumSet;
import java.util.function.BooleanSupplier;

public class PredicatedGoal extends Goal {
	public PredicatedGoal(Goal goal, BooleanSupplier predicate) {
		this(goal, predicate, predicate);
	}

	public PredicatedGoal(Goal goal, BooleanSupplier canStart, BooleanSupplier shouldContinue) {
		this.goal = goal;
		this.canStart = canStart;
		this.shouldContinue = shouldContinue;
	}

	private final Goal goal;
	private final BooleanSupplier canStart;
	private final BooleanSupplier shouldContinue;

	@Override
	public boolean canStart() {
		return this.goal.canStart() && this.canStart.getAsBoolean();
	}

	@Override
	public void start() {
		this.goal.start();
	}

	@Override
	public void stop() {
		this.goal.stop();
	}

	@Override
	public boolean canStop() {
		return this.goal.canStop();
	}

	@Override
	public boolean shouldContinue() {
		return this.goal.shouldContinue() && this.shouldContinue.getAsBoolean();
	}

	@Override
	public void tick() {
		this.goal.tick();
	}

	@Override
	public EnumSet<Control> getControls() {
		return this.goal.getControls();
	}

	@Override
	public void setControls(EnumSet<Control> controls) {
		this.goal.setControls(controls);
	}
}
