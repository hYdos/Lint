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

import net.fabricmc.fabric.api.transfer.v1.storage.StoragePreconditions;
import net.fabricmc.fabric.api.transfer.v1.transaction.TransactionContext;
import net.fabricmc.fabric.api.transfer.v1.transaction.base.SnapshotParticipant;

/**
 * A simple mana storage has bounded storage and insertion/extraction rates
 */
public class SimpleManaStorage extends SnapshotParticipant<Long> implements ManaStorage {

	private final ManaType type;
	private final IOBounds bounds;
	private final long capacity;
	private long stored;

	public SimpleManaStorage(ManaType type, IOBounds bounds, long capacity, long stored) {
		this.type = type;
		this.bounds = bounds;
		this.capacity = capacity;
		this.stored = stored;
	}

	@Override
	public ManaType getType() {
		return type;
	}

	@Override
	public long getAmount() {
		return stored;
	}

	@Override
	public long getCapacity() {
		return capacity;
	}

	@Override
	public long insert(long maxAmount, TransactionContext transaction) {
		StoragePreconditions.notNegative(maxAmount);

		long inserted = Math.min(bounds.input(), Math.min(maxAmount, capacity - stored));

		if (inserted > 0) {
			updateSnapshots(transaction);
			stored += inserted;
			return inserted;
		}

		return 0;
	}

	@Override
	public long extract(long maxAmount, TransactionContext transaction) {
		StoragePreconditions.notNegative(maxAmount);

		long extracted = Math.min(bounds.output(), Math.min(maxAmount, stored));

		if (extracted > 0) {
			updateSnapshots(transaction);
			stored -= extracted;
			return extracted;
		}

		return 0;
	}

	@Override
	protected Long createSnapshot() {
		return stored;
	}

	@Override
	protected void readSnapshot(Long snapshot) {
		stored = snapshot;
	}
}
