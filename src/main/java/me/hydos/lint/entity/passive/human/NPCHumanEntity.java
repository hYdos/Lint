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

package me.hydos.lint.entity.passive.human;

import me.hydos.lint.Lint;
import me.hydos.lint.entity.Entities;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.mob.PathAwareEntity;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.util.Identifier;
import net.minecraft.world.World;

public class NPCHumanEntity extends PathAwareEntity {
	public NPCHumanEntity(EntityType<? extends NPCHumanEntity> type, World world) {
		super(type, world);
		this.npc = NONE;
	}

	private NPCHumanEntity(World world, Identifier id) { // This has to be private to shut up the generic nonsense in entity types
		this(Entities.NPC_HUMAN, world);
		this.npc = id;
	}

	private Identifier npc;

	public static NPCHumanEntity createNew(World world, Identifier id) { // see the constructor for why
		return new NPCHumanEntity(world, id);
	}

	@Override
	public void readCustomDataFromTag(CompoundTag tag) {
		super.readCustomDataFromTag(tag);
		this.npc = new Identifier(tag.getString("npc"));
	}

	@Override
	public void writeCustomDataToTag(CompoundTag tag) {
		super.writeCustomDataToTag(tag);

		tag.putString("npc", this.npc.toString());
	}

	private static final Identifier NONE = Lint.id("none");

	public Identifier getSkinTexture() {
		return Lint.id("textures/npc/knavian_stablemaster");
	}
}
