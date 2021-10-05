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

package lint.mana.events;

import lint.mana.ManaStorage;
import lint.mana.ManaType;
import me.hydos.lint.entity.passive.TinyPotatoEntity;
import net.fabricmc.fabric.api.entity.event.v1.ServerEntityCombatEvents;
import net.fabricmc.fabric.api.transfer.v1.transaction.Transaction;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.mob.Monster;
import net.minecraft.server.world.ServerWorld;

/**
 * Hostile mobs drop a small amount of mana when killed, which will be deposited into the player's mana reserves
 */
public class EntityDeathListener implements ServerEntityCombatEvents.AfterKilledOtherEntity {

	@Override
	public void afterKilledOtherEntity(ServerWorld world, Entity entity, LivingEntity killedEntity) {
		long insert;

		if (killedEntity instanceof Monster) {
			// +1 for every health point
			// TODO: We should probably hook a damage event instead
			insert = (long) killedEntity.getMaxHealth();

			// For Lint-specific monsters, triple points
			if (killedEntity.getClass().getName().contains("lint")) {
				insert *= 3;
			}

			// Cap it to 100 mana
			insert = Math.min(insert, 100);
		} else {
			// Passive mobs drain a tiny amount of mana. You monster :(
			if (killedEntity instanceof TinyPotatoEntity) {
				insert = -100;
			} else {
				insert = -1;
			}
		}

		if (insert > 0) {
			try (Transaction transaction = Transaction.openOuter()) {
				ManaStorage.of(entity, ManaType.GENERIC).insert(insert, transaction);
				transaction.commit();
			}
		} else if (insert < 0) {
			try (Transaction transaction = Transaction.openOuter()) {
				ManaStorage.of(entity, ManaType.GENERIC).extract(-insert, transaction);
				transaction.commit();
			}
		}
	}
}
