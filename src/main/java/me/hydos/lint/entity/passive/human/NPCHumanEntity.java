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
import me.hydos.lint.npc.NPC;
import me.hydos.lint.npc.NPCRegistry;
import net.minecraft.client.util.DefaultSkinHelper;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.mob.PathAwareEntity;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.Packet;
import net.minecraft.text.LiteralText;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;
import net.minecraft.world.World;

public class NPCHumanEntity extends PathAwareEntity {
	public NPCHumanEntity(EntityType<? extends NPCHumanEntity> type, World world) {
		super(type, world);
		this.npc = MISSINGNO;
	}

	private NPCHumanEntity(World world, Identifier id) { // This has to be private to shut up the generic nonsense in entity types
		super(Entities.NPC_HUMAN, world);
		this.npc = id;
	}

	private Identifier npc;

	@Override
	public Text getCustomName() {
		NPC npc = NPCRegistry.getById(this.npc);
		return new LiteralText(npc == null ? "Unregistered NPC" : npc.getName());
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

	@Override
	public Packet<?> createSpawnPacket() {
		return super.createSpawnPacket();
	}

	public Identifier getSkinTexture() {
		NPC npc = NPCRegistry.getById(this.npc);
		return npc == null ? DefaultSkinHelper.getTexture() : npc.getTextureLocation();
	}

	public static NPCHumanEntity create(World world, Identifier id) { // see the constructor for why
		return new NPCHumanEntity(world, id);
	}

	private static final Identifier MISSINGNO = Lint.id("missingno");
}
