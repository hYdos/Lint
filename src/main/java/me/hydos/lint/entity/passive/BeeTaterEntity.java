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

package me.hydos.lint.entity.passive;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.ai.pathing.BirdNavigation;
import net.minecraft.entity.ai.pathing.EntityNavigation;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.world.World;

@SuppressWarnings("EntityConstructor")
public class BeeTaterEntity extends TinyPotatoEntity {

	public float size = 0;

	public BeeTaterEntity(EntityType<? extends BeeTaterEntity> type, World world) {
		super(type, world);
	}

	@Override
	public void readNbt(NbtCompound tag) {
		super.readNbt(tag);
	}

	@Override
	public NbtCompound writeNbt(NbtCompound tag) {
		return super.writeNbt(tag);
	}

	@Override
	public boolean tryAttack(Entity target) {
		return super.tryAttack(target);
	}

	public ActionResult interactMob(PlayerEntity player, Hand hand) {
		return super.interactMob(player, hand);
	}

	@Override
	protected EntityNavigation createNavigation(World world) {
		return new BirdNavigation(this, world);
	}
}
