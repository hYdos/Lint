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

package lint.mana.item;

import lint.mana.IOBounds;
import lint.mana.ManaStorage;
import lint.mana.ManaType;
import lint.mana.SimpleManaStorage;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

/**
 * Simple portable crystal which can store up to 100000 generic mana, with input and output rates of 100 per operation
 */
public class PortableCrystal extends Item {

	public PortableCrystal(Settings settings) {
		super(settings);
		registerMana();
	}

	protected void registerMana() {
		ManaStorage.ITEM.registerForItems((itemStack, context) -> {
			if (itemStack.getItem() instanceof PortableCrystal crystal && context == ManaType.GENERIC) {
				return new SimpleManaStorage(ManaType.GENERIC, new IOBounds(100, 100), 100000, crystal.getMana(itemStack)) {
					@Override
					protected void onFinalCommit() {
						crystal.setMana(itemStack, getAmount());
					}
				};
			}

			return null;
		}, this);
	}

	public long getMana(ItemStack itemStack) {
		if (itemStack.hasNbt()) {
			return itemStack.getNbt().getLong("Mana");
		} else {
			return 0;
		}
	}

	public void setMana(ItemStack itemStack, long amount) {
		if (amount == 0) {
			itemStack.setNbt(null);
		} else {
			itemStack.getOrCreateNbt().putLong("Mana", amount);
		}
	}
}
