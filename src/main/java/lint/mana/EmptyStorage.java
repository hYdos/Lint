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

record EmptyStorage(ManaType type) implements ManaStorage {

	static final ManaStorage GENERIC_NONE = new EmptyStorage(ManaType.GENERIC);
	static final ManaStorage GENERIC_MANOS = new EmptyStorage(ManaType.MANOS);
	static final ManaStorage GENERIC_ALLOS = new EmptyStorage(ManaType.ALLOS);

	@Override
	public ManaType getType() {
		return type;
	}

	@Override
	public long getAmount() {
		return 0;
	}

	@Override
	public long getCapacity() {
		return 0;
	}

	@Override
	public long insert(long maxAmount, TransactionContext transaction) {
		return 0;
	}

	@Override
	public long extract(long maxAmount, TransactionContext transaction) {
		return 0;
	}
}
