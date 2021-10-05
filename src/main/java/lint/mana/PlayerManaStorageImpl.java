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

package lint.mana;

import net.fabricmc.fabric.api.transfer.v1.transaction.TransactionContext;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;

record PlayerManaStorageImpl(PlayerEntity player, ManaType type) implements ManaStorage {

	@Override
	public ManaType getType() {
		return type;
	}

	@Override
	public long getAmount() {
		long amount = 0;
		PlayerInventory inventory = player.getInventory();

		for (int slot = 0; slot < inventory.size(); slot++) {
			ManaStorage storage = ITEM.find(inventory.getStack(slot), type);

			if (storage != null) {
				amount += storage.getAmount();
			}
		}

		return amount;
	}

	@Override
	public long getCapacity() {
		long capacity = 0;
		PlayerInventory inventory = player.getInventory();

		for (int slot = 0; slot < inventory.size(); slot++) {
			ManaStorage storage = ITEM.find(inventory.getStack(slot), type);

			if (storage != null) {
				capacity += storage.getCapacity();
			}
		}

		return capacity;
	}

	// TODO: Someone could make like 20 storages and evenly split between them without capped throughput. Do we care?
	@Override
	public long insert(long maxAmount, TransactionContext transaction) {
		long inserted = 0;
		PlayerInventory inventory = player.getInventory();

		for (int slot = 0; slot < inventory.size(); slot++) {
			ManaStorage storage = ITEM.find(inventory.getStack(slot), type);

			if (storage != null) {
				long insert = storage.insert(maxAmount, transaction);
				inserted += insert;
				maxAmount -= insert;
			}
		}

		return inserted;
	}

	@Override
	public long extract(long maxAmount, TransactionContext transaction) {
		long extracted = 0;
		PlayerInventory inventory = player.getInventory();

		for (int slot = 0; slot < inventory.size(); slot++) {
			ManaStorage storage = ITEM.find(inventory.getStack(slot), type);

			if (storage != null) {
				long extract = storage.extract(maxAmount, transaction);
				extracted += extract;
				maxAmount -= extract;
			}
		}

		return extracted;
	}
}
