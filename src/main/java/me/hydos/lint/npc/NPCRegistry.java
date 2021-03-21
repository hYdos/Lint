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

import java.util.HashMap;
import java.util.Map;

import org.jetbrains.annotations.Nullable;

import me.hydos.lint.npc.quest.Quest;
import net.minecraft.util.Identifier;

public class NPCRegistry {
	private static final Map<Identifier, NPC> MAP = new HashMap<>();
	private static final Map<Identifier, Quest> QUEST_MAP = new HashMap<>();

	@Nullable
	public static NPC getById(Identifier id) {
		return MAP.get(id);
	}

	@Nullable
	public static Quest getQuestById(Identifier id) {
		return QUEST_MAP.get(id);
	}

	public static <T extends NPC> T register(Identifier id, T npc) {
		MAP.put(id, npc);
		return npc;
	}

	public static <T extends Quest> T register(Identifier id, T quest) {
		QUEST_MAP.put(id, quest);
		return quest;
	}
}
