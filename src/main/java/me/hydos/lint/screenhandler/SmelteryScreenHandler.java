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

import me.hydos.lint.block.entity.SmelteryBlockEntity;
import me.hydos.lint.fluid.LintFluids;
import me.hydos.lint.fluid.SimpleFluidData;
import me.hydos.lint.util.LintInventory;
import me.hydos.lint.util.LintUtilities;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.Inventory;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.screen.slot.Slot;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class SmelteryScreenHandler extends ScreenHandler {

	public LintInventory inventory;
	public BlockPos smelteryBlockEntity;
	public World world;

	public SmelteryScreenHandler(int syncId, PlayerInventory playerInventory) {
		this(syncId, playerInventory, new LintInventory(9), ((BlockHitResult) playerInventory.player.raycast(5, 1, false)).getBlockPos(), playerInventory.player.world);
	}

	public SmelteryScreenHandler(int syncId, PlayerInventory playerInventory, LintInventory inventory, BlockPos smelteryBlockEntity, World world) {
		super(ScreenHandlers.SMELTERY, syncId);
		this.inventory = inventory;
		this.smelteryBlockEntity = smelteryBlockEntity;
		this.world = world;

		int slotIndex = 0;
		for (int y = 0; y < 3; y++) {
			for (int i = 0; i < 3; i++) {
				this.addSlot(new FuelInputSlot(inventory, slotIndex, (18 * i) + 116, 17 + (18 * y)));
				slotIndex++;
			}
		}

		LintUtilities.addPlayerInventorySlots(playerInventory, this);
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

	private class FuelInputSlot extends Slot {

		public FuelInputSlot(Inventory inventory, int index, int x, int y) {
			super(inventory, index, x, y);
		}

		public SmelteryBlockEntity getBlockEntity() {
			return (SmelteryBlockEntity) SmelteryScreenHandler.this.world.getBlockEntity(SmelteryScreenHandler.this.smelteryBlockEntity);
		}

		@Override
		public void markDirty() {
			super.markDirty();
			Item item = inventory.getStack(id).getItem();
			if (item != Items.AIR) {
				// Check the molten fluid registries for the item.
				for (LintFluids.FluidEntry entry : LintFluids.MOLTEN_FLUID_MAP.values()) {
					if (entry.getNuggetItem() == item) {
						System.out.println("Nugget item of " + LintFluids.getId(entry));
						setStack(ItemStack.EMPTY);
						getBlockEntity().fluidData.add(SimpleFluidData.of(entry, (1f / 9) / 9));
					}

					if (entry.getIngotItem() == item) {
						System.out.println("Ingot item of " + LintFluids.getId(entry));
						setStack(ItemStack.EMPTY);
						getBlockEntity().fluidData.add(SimpleFluidData.of(entry, 1f / 9));
					}

					if (entry.getBlockItem() == item) {
						System.out.println("Block item of " + LintFluids.getId(entry));
						setStack(ItemStack.EMPTY);
						getBlockEntity().fluidData.add(SimpleFluidData.of(entry, 1));
					}
				}
			}
		}
	}
}
