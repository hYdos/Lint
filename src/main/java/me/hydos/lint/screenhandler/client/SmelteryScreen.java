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

package me.hydos.lint.screenhandler.client;

import me.hydos.lint.Lint;
import me.hydos.lint.client.render.fluid.LintFluidRenderer;
import me.hydos.lint.fluid.LintFluids;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.screen.ingame.HandledScreen;
import net.minecraft.client.render.BufferBuilder;
import net.minecraft.client.render.Tessellator;
import net.minecraft.client.render.VertexFormats;
import net.minecraft.client.texture.Sprite;
import net.minecraft.client.texture.SpriteAtlasTexture;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.fluid.Fluid;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.Matrix4f;

import java.awt.*;

public class SmelteryScreen extends HandledScreen<ScreenHandler> {

	public static final Identifier GUI = new Identifier("lint", "textures/gui/container/smeltery_inventory.png");
	private static final Identifier IRON_FLUID = Lint.id("iron");

	public SmelteryScreen(ScreenHandler handler, PlayerInventory inventory, Text title) {
		super(handler, inventory, title);
	}

	@Override
	public void render(MatrixStack matrices, int mouseX, int mouseY, float delta) {
		super.render(matrices, mouseX, mouseY, delta);
		drawMouseoverTooltip(matrices, mouseX, mouseY);
	}

	@Override
	protected void drawBackground(MatrixStack matrices, float delta, int mouseX, int mouseY) {
		this.client.getTextureManager().bindTexture(GUI);
		drawTexture(matrices, x, y - 32, 0, 0, this.backgroundWidth, this.backgroundHeight + 65);
		renderFluid(matrices, LintFluids.MOLTEN_FLUID_MAP.get(IRON_FLUID).getStill(), new Rectangle(new Point(x + 8, y + 49), new Dimension(72, 20)));
	}

	public void renderFluid(MatrixStack matrices, Fluid fluid, Rectangle bounds) {
		LintFluidRenderer.CachedFluid renderingData = LintFluidRenderer.from(fluid);
		if (renderingData != null) {
			Sprite sprite = renderingData.getSprite();
			int color = renderingData.getColor();
			int a = 255;
			int r = color >> 16 & 255;
			int g = color >> 8 & 255;
			int b = color & 255;
			MinecraftClient.getInstance().getTextureManager().bindTexture(SpriteAtlasTexture.BLOCK_ATLAS_TEXTURE);
			Tessellator tessellator = Tessellator.getInstance();
			BufferBuilder bb = tessellator.getBuffer();
			Matrix4f matrix = matrices.peek().getModel();
			bb.begin(7, VertexFormats.POSITION_TEXTURE_COLOR);
			bb.vertex(matrix, (float) bounds.getMaxX(), (float) bounds.y, this.getFluidZ()).texture(sprite.getMaxU(), sprite.getMinV()).color(r, g, b, a).next();
			bb.vertex(matrix, (float) bounds.x, (float) bounds.y, this.getFluidZ()).texture(sprite.getMinU(), sprite.getMinV()).color(r, g, b, a).next();
			bb.vertex(matrix, (float) bounds.x, (float) bounds.getMaxY(), this.getFluidZ()).texture(sprite.getMinU(), sprite.getMaxV()).color(r, g, b, a).next();
			bb.vertex(matrix, (float) bounds.getMaxX(), (float) bounds.getMaxY(), this.getFluidZ()).texture(sprite.getMaxU(), sprite.getMaxV()).color(r, g, b, a).next();
			tessellator.draw();
		}
	}

	public void renderAdvancedFluidTm(MatrixStack matrices, Fluid fluid, Rectangle bounds) {
		//TODO: this will work by rendering many small fluids and putting them together thus removing the stretching artifact i hope. this will be interesting also someone remind me to finish overworld -> lint and lint -> overworld portal thank
	}

	private float getFluidZ() {
		return 1f;
	}
}
