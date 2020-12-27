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

package me.hydos.lint.client.screen;

import me.hydos.lint.Lint;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.gui.DrawableHelper;
import net.minecraft.client.gui.screen.DownloadingTerrainScreen;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.text.Text;
import net.minecraft.text.TranslatableText;
import net.minecraft.util.Identifier;

import java.util.Random;

@Environment(EnvType.CLIENT)
public class TaterDownloadingTerrainScreen extends DownloadingTerrainScreen {
	private static final Text TEXT = new TranslatableText("multiplayer.downloadingTerrain");
	private static final Identifier TATER_TEXTURE = Lint.id("textures/gui/tater_backgrounds.png");
	private static final int SIZE = 32;

	private final long seed = new Random().nextLong();
	private float progress = 0;

	public void render(MatrixStack matrices, int mouseX, int mouseY, float delta) {
		Random random = new Random(this.seed);

		this.progress += 0.01;
		if (this.progress > 1) {
			this.progress = 1;
		}

		this.client.getTextureManager().bindTexture(TATER_TEXTURE);
		for (int x = 0; x < this.width; x += SIZE) {
			for (int y = 0; y < this.height; y += SIZE) {
				boolean tater = this.progress > random.nextFloat();
				DrawableHelper.drawTexture(matrices, x, y, tater ? 0 : SIZE, 0, SIZE, SIZE, 256, 256);
			}
		}

		DrawableHelper.drawCenteredText(matrices, this.textRenderer, TEXT, this.width / 2, this.height / 2 - 50, 0xFFFFFF);
	}
}
