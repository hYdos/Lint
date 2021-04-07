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

package me.hydos.lint.util;

import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.screen.slot.Slot;

public class LintUtilities {

    public static void addPlayerInventorySlots(PlayerInventory playerInventory, ScreenHandler screenHandler) {
        // Hotbar
        for (int i = 0; i < 9; i++) {
            screenHandler.addSlot(new Slot(playerInventory, i, (18 * i) + 8, 142));
        }
        // Player Inventory
        int padding = 9;
        for (int j = 0; j != 3; j++) {
            for (int i = 0; i != 9; i++) {
                screenHandler.addSlot(new Slot(playerInventory, i + padding, 8 + i * 18, 84 + j * 18));
            }
            padding += 9;
        }
    }

}
