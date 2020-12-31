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

import io.netty.buffer.ByteBuf;
import me.hydos.lint.entity.passive.TinyPotatoEntity;
import me.hydos.lint.util.LintInventory;
import me.hydos.lint.util.LintUtilities;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.screen.slot.Slot;
import net.minecraft.world.World;

public class LilTaterInteractScreenHandler extends ScreenHandler {

	public final int taterId;

	public LilTaterInteractScreenHandler(int syncId, PlayerInventory playerInventory, ByteBuf buf) {
		super(ScreenHandlers.TATER_INVENTORY, syncId);
		this.taterId = buf.readInt();

		World world = playerInventory.player.world;

		TinyPotatoEntity tater = (TinyPotatoEntity) world.getEntityById(taterId);
		LintInventory inv = tater.inventory;

		//Tater Armour slots
		this.addSlot(new Slot(inv, 0, 8, 10));
		this.addSlot(new Slot(inv, 1, 8, 6));
		this.addSlot(new Slot(inv, 2, 8, 24));
		this.addSlot(new Slot(inv, 3, 8, 42));

		//Tater hotbar slots
		for (int i = 0; i != 9; i++) {
			this.addSlot(new Slot(inv, i + 4, 8 + i * 18, 65));
		}

		LintUtilities.addPlayerInventorySlots(playerInventory, this);
	}

	@Override
	public boolean canUse(PlayerEntity player) {
		return true;
	}

	@Override
	public ItemStack transferSlot(PlayerEntity player, int invSlot) {
		return ItemStack.EMPTY;
	}
}
