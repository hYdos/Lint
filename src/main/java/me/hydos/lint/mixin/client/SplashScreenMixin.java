package me.hydos.lint.mixin.client;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.font.TextRenderer;
import net.minecraft.client.gui.screen.SplashScreen;
import net.minecraft.client.util.Window;
import net.minecraft.client.util.math.MatrixStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(SplashScreen.class)
public class SplashScreenMixin {

	private static final String[] DISCLAIMER = {
			"This is a work of fiction. Names, characters, businesses, places,",
			"events, locales, and incidents are either the products of the",
			"author's imagination or used in a fictitious manner. Any",
			"resemblance to actual persons, living or dead, or actual events",
			"is purely coincidental."
	};

	@Inject(method = "init", at = @At("RETURN"))
	private static void init(MinecraftClient client, CallbackInfo callbackInfo) {
		// TODO: Initialize the default Minecraft font
	}

	@Inject(method = "render", at = @At("RETURN"))
	private void render(MatrixStack matrices, int mouseX, int mouseY, float delta, CallbackInfo callbackInfo) {
		MinecraftClient instance = MinecraftClient.getInstance();
		Window window = instance.getWindow();
		TextRenderer textRenderer = instance.textRenderer;

		int y = window.getScaledHeight() / 4 * 3;

		for (String line : DISCLAIMER) {
			int width = textRenderer.getWidth(line);
			textRenderer.drawWithShadow(matrices, line, (window.getScaledWidth() - width) / 2F, y, 0xFFFFFFFF);
			y += 10;
		}
	}
}
