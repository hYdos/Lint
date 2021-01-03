package me.hydos.lint.client.screen;

import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.network.ClientPlayNetworkHandler;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.text.LiteralText;
import net.minecraft.text.Text;

public class NpcInteractionScreen extends Screen {

	public Text text;

	public NpcInteractionScreen(PacketByteBuf byteBuf) {
		super(new LiteralText(byteBuf.readString()));
		text = byteBuf.readText();
	}

	@Override
	public void render(MatrixStack matrices, int mouseX, int mouseY, float delta) {
		super.render(matrices, mouseX, mouseY, delta);
		drawTexture(matrices, 0, 0, 0, 0, width, 50);
		client.textRenderer.draw(matrices, text, 10, height / 1.4f, 0xFF444444);
	}
}
