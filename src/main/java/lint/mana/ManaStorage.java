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

import me.hydos.lint.Lint;
import net.fabricmc.fabric.api.lookup.v1.block.BlockApiLookup;
import net.fabricmc.fabric.api.lookup.v1.item.ItemApiLookup;
import net.fabricmc.fabric.api.transfer.v1.storage.StoragePreconditions;
import net.fabricmc.fabric.api.transfer.v1.transaction.Transaction;
import net.fabricmc.fabric.api.transfer.v1.transaction.TransactionContext;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import org.jetbrains.annotations.Nullable;

/**
 * Access to mana reserved either in world, as items on in players
 */
public interface ManaStorage {

	/**
	 * Access to a block's mana storage, using the mana type as the context and filter
	 */
	BlockApiLookup<ManaStorage, ManaType> BLOCK = BlockApiLookup.get(Lint.id("mana"), ManaStorage.class, ManaType.class);

	/**
	 * Access to an item's mana storage, using the mana type as the context and filter
	 */
	ItemApiLookup<ManaStorage, ManaType> ITEM = ItemApiLookup.get(Lint.id("mana"), ManaStorage.class, ManaType.class);

	/**
	 * Returns an empty mana storage of the given type
	 *
	 * @param type The type of the empty storage
	 * @return A storage incapable of insertion nor extraction, and has no capacity
	 */
	static ManaStorage empty(ManaType type) {
		return switch (type) {
			case GENERIC -> EmptyStorage.GENERIC_NONE;
			case MANOS -> EmptyStorage.GENERIC_MANOS;
			case ALLOS -> EmptyStorage.GENERIC_ALLOS;
		};
	}

	/**
	 * A mana storage of a player, such as items in their inventory capable of mana storage
	 *
	 * @param player The player
	 * @param type   The mana type to access
	 * @return An aggregate mana storage
	 */
	static ManaStorage of(PlayerEntity player, ManaType type) {
		return new PlayerManaStorageImpl(player, type);
	}

	/**
	 * A mana storage of an entity
	 *
	 * @param entity The entity
	 * @param type   The mana type to access
	 * @return An aggregate mana storage. May return an {@link #empty(ManaType)} instance if the entity is incapable of
	 * holding mana
	 * @see #of(PlayerEntity, ManaType)
	 */
	static ManaStorage of(Entity entity, ManaType type) {
		if (entity instanceof PlayerEntity player) {
			return of(player, type);
		} else {
			return empty(type);
		}
	}

	/**
	 * @return The mana type which this storage supports
	 */
	ManaType getType();

	/**
	 * @return The mana currently stored
	 */
	long getAmount();

	/**
	 * @return The maximum mana which can be stored
	 */
	long getCapacity();

	/**
	 * Try to insert up to a given amount of mana into the storage
	 *
	 * @param maxAmount   The amount to try inserting
	 * @param transaction The transaction to operate under
	 * @return How much mana was successfully inserted
	 */
	long insert(long maxAmount, TransactionContext transaction);

	/**
	 * Try to extract up to a given amount of mana from the storage
	 *
	 * @param maxAmount   The amount to try extracting
	 * @param transaction The transaction to operate under
	 * @return How much mana was successfully extracted
	 */
	long extract(long maxAmount, TransactionContext transaction);

	/**
	 * Move mana between two mana storages, and return the amount that was successfully moved
	 *
	 * @param from        The source storage
	 * @param to          The target storage
	 * @param maxAmount   The maximum amount that may be moved
	 * @param transaction The transaction this transfer is part of, or {@code null} if a transaction should be opened
	 *                    just for this transfer
	 * @return The amount of mana that was successfully moved
	 */
	static long move(ManaStorage from, ManaStorage to, long maxAmount, @Nullable TransactionContext transaction) {
		if (from.getType() != to.getType()) {
			return 0;
		}

		StoragePreconditions.notNegative(maxAmount);

		// Simulate extraction first
		long maxExtracted;

		try (Transaction extractionTestTransaction = Transaction.openNested(transaction)) {
			maxExtracted = from.extract(maxAmount, extractionTestTransaction);
		}

		try (Transaction moveTransaction = Transaction.openNested(transaction)) {
			// Then insert what can be extracted
			long accepted = to.insert(maxExtracted, moveTransaction);

			// Extract for real
			if (from.extract(accepted, moveTransaction) == accepted) {
				// Commit if the amounts match
				moveTransaction.commit();
				return accepted;
			}
		}

		return 0;
	}
}
