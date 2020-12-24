package me.hydos.lint.block.entity;

import io.github.hydos.lint.screenhandler.SmelteryScreenHandler;
import io.github.hydos.lint.util.LintInventory;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.Inventories;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.screen.NamedScreenHandlerFactory;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.text.LiteralText;
import net.minecraft.text.Text;
import org.jetbrains.annotations.Nullable;

public class SmelteryBlockEntity extends BlockEntity implements NamedScreenHandlerFactory {

	public LintInventory inventory = new LintInventory(9);

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

	@Override
	public CompoundTag toTag(CompoundTag tag) {
		Inventories.toTag(tag, inventory.getRawList());
		return super.toTag(tag);
	}

	@Override
	public void fromTag(BlockState state, CompoundTag tag) {
		inventory = new LintInventory(9);
		Inventories.fromTag(tag, inventory.getRawList());
		super.fromTag(state, tag);
	}
}
