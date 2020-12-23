package io.github.hydos.lint.block.entity;

import io.github.hydos.lint.screenhandler.SmelteryScreenHandler;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.screen.NamedScreenHandlerFactory;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.text.LiteralText;
import net.minecraft.text.Text;
import org.jetbrains.annotations.Nullable;

public class SmelteryBlockEntity extends BlockEntity implements NamedScreenHandlerFactory {

	public SmelteryBlockEntity() {
		super(BlockEntities.SMELTERY);
	}

	@Override
	public Text getDisplayName() {
		return new LiteralText("Lint Smeltery");
	}

	@Nullable
	@Override
	public ScreenHandler createMenu(int syncId, PlayerInventory inv, PlayerEntity player) {
		return new SmelteryScreenHandler(syncId, inv, this);
	}
}
