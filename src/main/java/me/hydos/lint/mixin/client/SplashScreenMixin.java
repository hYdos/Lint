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

	}

	@Inject(method = "render", at = @At("RETURN"))
	private void render(MatrixStack matrices, int mouseX, int mouseY, float delta, CallbackInfo callbackInfo) {
		MinecraftClient instance = MinecraftClient.getInstance();
		Window window = instance.getWindow();
		TextRenderer textRenderer = instance.textRenderer;

		int y = 15;

		for (String line : DISCLAIMER) {
			int width = textRenderer.getWidth(line);
			textRenderer.drawWithShadow(matrices, line, (window.getScaledWidth() - width) / 2F, y, 0xFFFFFFFF);
			y += 10;
		}
	}
}
