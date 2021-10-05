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

public abstract class DelegatingManaStorage implements ManaStorage {

	protected final ManaStorage delegate;

	protected DelegatingManaStorage(ManaStorage delegate) {
		this.delegate = delegate;
	}

	/**
	 * Wraps around the given mana storage and disables insertion
	 *
	 * @param storage Storage to wrap around
	 * @return A new mana storage with no insertion support
	 */
	public static ManaStorage noInsertion(ManaStorage storage) {
		return new DelegatingManaStorage(storage) {
			@Override
			public long insert(long maxAmount, TransactionContext transaction) {
				return 0;
			}
		};
	}

	/**
	 * Wraps around the given mana storage and disables extraction
	 *
	 * @param storage Storage to wrap around
	 * @return A new mana storage with no extraction support
	 */
	public static ManaStorage noExtraction(ManaStorage storage) {
		return new DelegatingManaStorage(storage) {
			@Override
			public long extract(long maxAmount, TransactionContext transaction) {
				return 0;
			}
		};
	}

	@Override
	public ManaType getType() {
		return delegate.getType();
	}

	@Override
	public long getAmount() {
		return delegate.getAmount();
	}

	@Override
	public long getCapacity() {
		return delegate.getCapacity();
	}

	@Override
	public long insert(long maxAmount, TransactionContext transaction) {
		return delegate.insert(maxAmount, transaction);
	}

	@Override
	public long extract(long maxAmount, TransactionContext transaction) {
		return delegate.extract(maxAmount, transaction);
	}
}
