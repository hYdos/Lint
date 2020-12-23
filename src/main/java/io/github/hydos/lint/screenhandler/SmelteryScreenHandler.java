package io.github.hydos.lint.screenhandler;

import io.github.hydos.lint.block.entity.SmelteryBlockEntity;
import io.github.hydos.lint.util.LintInventory;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.screen.slot.Slot;

public class SmelteryScreenHandler extends ScreenHandler {

	public LintInventory inventory;
	public SmelteryBlockEntity smelteryBlock;

	public SmelteryScreenHandler(int syncId, PlayerInventory playerInventory) {
		super(ScreenHandlers.SMELTERY, syncId);
	}

	public SmelteryScreenHandler(int syncId, PlayerInventory playerInventory, SmelteryBlockEntity smelteryBlock) {
		super(ScreenHandlers.SMELTERY, syncId);
		this.smelteryBlock = smelteryBlock;
		this.inventory = smelteryBlock.inventory;
		this.inventory.onOpen(playerInventory.player);

		this.addSlot(new Slot(inventory, 0, 10, 10));
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
