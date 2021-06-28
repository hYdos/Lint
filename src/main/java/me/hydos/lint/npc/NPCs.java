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

package me.hydos.lint.npc;

import me.hydos.lint.Lint;
import me.hydos.lint.npc.ai.NPCPathing;
import me.hydos.lint.npc.ai.Task;

/**
 * Lint NPCS.
 */
public final class NPCs {
	private static NPCPathing createCitizenAI() {
		return new NPCPathing()
				.survival(Task.FLEE_HOSTILE_ENEMIES)
				.leisure(Task.SAUNTER_AROUND)
				.leisure(Task.WALK_AROUND)
				.when(Task.Predicate.NIGHT, pathing -> pathing
						//.strongHabit(Task.SEEK_HOME)
						.habit(Task.SEEK_LIGHT));
	}

	public static final NPC TEST = new NPC(new NPC.Settings()
			.name("Bob")
			.texture(Lint.id("knavian_stablemaster"))
			.pathing(createCitizenAI()));

	public static void initialise() {
		NPCRegistry.register(Lint.id("test"), TEST);
	}
}
