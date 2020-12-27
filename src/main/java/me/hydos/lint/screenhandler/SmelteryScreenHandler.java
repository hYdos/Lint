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

package me.hydos.lint.screenhandler;

import me.hydos.lint.util.LintInventory;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.screen.slot.Slot;

public class SmelteryScreenHandler extends ScreenHandler {

	public LintInventory inventory;

	public SmelteryScreenHandler(int syncId, PlayerInventory playerInventory) {
		this(syncId, playerInventory, new LintInventory(9));
	}

	public SmelteryScreenHandler(int syncId, PlayerInventory playerInventory, LintInventory inventory) {
		super(ScreenHandlers.SMELTERY, syncId);
		this.inventory = inventory;

		this.addSlot(new Slot(inventory, 0, 10, 10));
		this.addSlot(new Slot(inventory, 1, 10, 10));
		this.addSlot(new Slot(inventory, 2, 10, 10));
		this.addSlot(new Slot(inventory, 3, 10, 10));
		this.addSlot(new Slot(inventory, 4, 10, 10));
		this.addSlot(new Slot(inventory, 5, 10, 10));
		this.addSlot(new Slot(inventory, 6, 10, 10));
		this.addSlot(new Slot(inventory, 7, 10, 10));
		this.addSlot(new Slot(inventory, 8, 10, 10));

		this.inventory.onOpen(playerInventory.player);
	}

	@Override
	public boolean canUse(PlayerEntity player) {
		return this.inventory.canPlayerUse(player);
	}

	// Add quick move support
	@Override
	public ItemStack transferSlot(PlayerEntity player, int invSlot) {
		ItemStack newStack = ItemStack.EMPTY;
		Slot slot = this.slots.get(invSlot);
		if (slot != null && slot.hasStack()) {
			ItemStack originalStack = slot.getStack();
			newStack = originalStack.copy();
			if (invSlot < this.inventory.size()) {
				if (!this.insertItem(originalStack, this.inventory.size(), this.slots.size(), true)) {
					return ItemStack.EMPTY;
				}
			} else if (!this.insertItem(originalStack, 0, this.inventory.size(), false)) {
				return ItemStack.EMPTY;
			}

			if (originalStack.isEmpty()) {
				slot.setStack(ItemStack.EMPTY);
			} else {
				slot.markDirty();
			}
		}
		return newStack;
	}
}
