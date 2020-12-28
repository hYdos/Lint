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

package me.hydos.bossbar;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.font.TextRenderer;
import net.minecraft.client.gui.DrawableHelper;
import net.minecraft.client.util.math.MatrixStack;

@Environment(EnvType.CLIENT)
public class BossBarClientRenderer extends DrawableHelper {

	public static void render(MatrixStack stack, TextRenderer textRenderer, float tickDelta, boolean renderShadow) {
		ClientModernBossBar bossBar = ClientModernBossBar.getInstance();
		if (bossBar == null || bossBar.endX == 0) {
			return;
		}
		float scale = (float) MinecraftClient.getInstance().getWindow().getScaleFactor() / 4;
		int shadowOffsetX = 2;
		int shadowOffsetY = 2;
		stack.push();
		stack.scale(scale, scale, scale);
		stack.translate(240, 0, 0);
		drawCenteredText(stack, textRenderer, bossBar.title, 0, 16, 0xFFFFFFFF);
		if (renderShadow) {
			fill(stack, -140 + shadowOffsetX, 30 + shadowOffsetY, 140 + shadowOffsetX, 34 + shadowOffsetY, 0x80000000);
		}
		fill(stack, -140, 30, 140, 34, 0x991E1E1E);
		fill(stack, -140, 30, bossBar.endX, 34, 0xFFE52320);
		stack.pop();
	}

}
