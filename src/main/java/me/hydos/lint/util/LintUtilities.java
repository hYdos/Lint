package me.hydos.lint.util;

import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.screen.slot.Slot;

public class LintUtilities {

	public static void addPlayerInventorySlots(PlayerInventory playerInventory, ScreenHandler screenHandler){
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
