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
import net.fabricmc.fabric.api.transfer.v1.transaction.TransactionContext;
import net.minecraft.entity.player.PlayerEntity;

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
	 * @param type   The mana type to manipulate
	 * @return An aggregate mana storage
	 */
	static ManaStorage of(PlayerEntity player, ManaType type) {
		return new PlayerManaStorageImpl(player, type);
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
}
