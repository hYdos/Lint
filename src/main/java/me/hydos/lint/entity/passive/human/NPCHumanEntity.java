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
import me.hydos.lint.entity.goal.SeekBlockGoal;
import me.hydos.lint.npc.NPC;
import me.hydos.lint.npc.NPCRegistry;
import net.minecraft.client.util.DefaultSkinHelper;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.data.DataTracker;
import net.minecraft.entity.data.TrackedData;
import net.minecraft.entity.data.TrackedDataHandlerRegistry;
import net.minecraft.entity.mob.PathAwareEntity;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.text.LiteralText;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;
import net.minecraft.world.World;

public class NPCHumanEntity extends PathAwareEntity {
	public NPCHumanEntity(EntityType<? extends NPCHumanEntity> type, World world) {
		super(type, world);
	}

	private NPCHumanEntity(World world, Identifier id) { // This has to be private to shut up the generic nonsense in entity types
		super(Entities.NPC_HUMAN, world);
		this.dataTracker.set(NPC_ID, id.toString());
	}

	@Override
	protected void initGoals() {
		this.goalSelector.add(1, new PredicatedGoal(new SeekBlockGoal(this, b -> b.getDefaultState().getLuminance() > 8, 9, 1.0f, 2.0f), () -> this.world.isNight()));
	}

	@Override
	public Text getCustomName() {
		// Corrective stuff for making summoning one without data into a missingno
		String npcId = this.dataTracker.get(NPC_ID);

		if (npcId == null || npcId.isEmpty()) {
			this.dataTracker.set(NPC_ID, MISSINGNO.toString());
		}

		NPC npc = NPCRegistry.getById(new Identifier(npcId));
		return new LiteralText(npc == null ? "Unregistered NPC" : npc.getName());
	}

	@Override
	public void readCustomDataFromTag(CompoundTag tag) {
		super.readCustomDataFromTag(tag);
		this.dataTracker.set(NPC_ID, tag.getString("npc"));
	}

	@Override
	public void writeCustomDataToTag(CompoundTag tag) {
		super.writeCustomDataToTag(tag);

		tag.putString("npc", this.dataTracker.get(NPC_ID));
	}

	@Override
	protected void initDataTracker() {
		super.initDataTracker();
		this.dataTracker.startTracking(NPC_ID, MISSINGNO.toString());
	}

	public Identifier getSkinTexture() {
		NPC npc = NPCRegistry.getById(new Identifier(this.dataTracker.get(NPC_ID)));
		return npc == null ? DefaultSkinHelper.getTexture() : npc.getTextureLocation();
	}

	public static NPCHumanEntity create(World world, Identifier id) { // see the constructor for why
		return new NPCHumanEntity(world, id);
	}

	private static final Identifier MISSINGNO = Lint.id("missingno");
	private static final TrackedData<String> NPC_ID = DataTracker.registerData(NPCHumanEntity.class, TrackedDataHandlerRegistry.STRING);
}
