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

package me.hydos.lint.npc.ai;

import java.util.function.BooleanSupplier;
import java.util.function.Function;

import org.jetbrains.annotations.Nullable;

import me.hydos.lint.entity.goal.SeekBlockGoal;
import me.hydos.lint.entity.passive.human.NPCHumanEntity;
import net.minecraft.entity.ai.goal.FleeEntityGoal;
import net.minecraft.entity.ai.goal.Goal;
import net.minecraft.entity.ai.goal.WanderAroundGoal;
import net.minecraft.entity.mob.HostileEntity;
import net.minecraft.world.LightType;

public class Task {
	private Task(Task parent, Predicate predicate) {
		this.goalProvider = parent.goalProvider;
		this.predicate = predicate;
	}
	
	public Task(Function<NPCHumanEntity, Goal> goalProvider) {
		this.goalProvider = goalProvider;
	}

	private final Function<NPCHumanEntity, Goal> goalProvider;
	private Predicate predicate;

	Task withPredicate(@Nullable Predicate predicate) {
		return predicate == null ? this : new Task(this, predicate);
	}

	public Goal createGoal(NPCHumanEntity entity) {
		return this.goalProvider.apply(entity);
	}

	Predicate getPredicate() {
		return this.predicate;
	}

	public static final Task FLEE_HOSTILE_ENEMIES = new Task(npc -> new FleeEntityGoal<>(npc, HostileEntity.class, 4.0f, 2.5f, 3.0f));
	public static final Task SEEK_LIGHT = new Task(npc -> new SeekBlockGoal(npc, b -> b.getDefaultState().getLuminance() > 8 && npc.getEntityWorld().getLightLevel(LightType.BLOCK, npc.getBlockPos()) < 10, 16, 2.0f, 2.5f));
	public static final Task SAUNTER_AROUND = new Task(npc -> new WanderAroundGoal(npc, 1.5f, 32));
	public static final Task WALK_AROUND = new Task(npc -> new WanderAroundGoal(npc, 2.0f, 64));

	public enum Predicate {
		NIGHT(e -> () -> e.getEntityWorld().isNight()),
		DAY(e -> () -> e.getEntityWorld().isDay());

		private Predicate(Function<NPCHumanEntity, BooleanSupplier> conditionProvider) {
			this.provider = conditionProvider;
		}

		public final Function<NPCHumanEntity, BooleanSupplier> provider;
	}
}
