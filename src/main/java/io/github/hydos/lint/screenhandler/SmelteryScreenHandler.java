package io.github.hydos.lint.screenhandler;

import io.github.hydos.lint.block.entity.SmelteryBlockEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.screen.ScreenHandler;

public class SmelteryScreenHandler extends ScreenHandler {

	public SmelteryBlockEntity smelteryBlock;

	public SmelteryScreenHandler(int syncId, PlayerInventory playerInventory) {
		super(ScreenHandlers.SMELTERY, syncId);
	}

	public SmelteryScreenHandler(int syncId, PlayerInventory playerInventory, SmelteryBlockEntity smelteryBlock) {
		super(ScreenHandlers.SMELTERY, syncId);
		this.smelteryBlock = smelteryBlock;
	}

	@Override
	public boolean canUse(PlayerEntity player) {
		return true;
	}
}
