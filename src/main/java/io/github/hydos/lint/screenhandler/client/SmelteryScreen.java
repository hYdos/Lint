package io.github.hydos.lint.screenhandler.client;

import net.minecraft.client.gui.screen.ingame.HandledScreen;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;

public class SmelteryScreen extends HandledScreen<ScreenHandler> {

	public static final Identifier GUI = new Identifier("lint", "textures/gui/container/smeltery_inventory.png");

	public SmelteryScreen(ScreenHandler handler, PlayerInventory inventory, Text title) {
		super(handler, inventory, title);
	}

	@Override
	protected void drawBackground(MatrixStack matrices, float delta, int mouseX, int mouseY) {
		this.client.getTextureManager().bindTexture(GUI);
		drawTexture(matrices, x, y - 32, 0, 0, this.backgroundWidth, this.backgroundHeight + 65);
		assert this.client.player != null;
	}
}
