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

import java.awt.Dimension;
import java.awt.Point;
import java.awt.Rectangle;

import me.hydos.lint.block.entity.SmelteryBlockEntity;
import me.hydos.lint.client.render.fluid.LintFluidRenderer;
import me.hydos.lint.fluid.FluidStack;
import me.hydos.lint.screenhandler.SmelteryScreenHandler;
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
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Matrix4f;

public class SmelteryScreen extends HandledScreen<ScreenHandler> {

	public static final Identifier GUI = new Identifier("lint", "textures/gui/screen/smeltery_inventory.png");

	public SmelteryScreen(ScreenHandler handler, PlayerInventory inventory, Text title) {
		super(handler, inventory, title);
	}

	@Override
	public void render(MatrixStack matrices, int mouseX, int mouseY, float delta) {
		super.render(matrices, mouseX, mouseY, delta);
		BlockPos smelteryPos = ((SmelteryScreenHandler) getScreenHandler()).smelteryPos;
		SmelteryBlockEntity smeltery = (SmelteryBlockEntity) MinecraftClient.getInstance().world.getBlockEntity(smelteryPos);
		if (smeltery.multiblock != null) {
			for (int i = 0; i < smeltery.getFluidData().size(); i++) {
				FluidStack layerFluid = smeltery.getFluidData().get(i);
				if (layerFluid != null) {
					int j = (int) (layerFluid.level * (6.5f / smeltery.multiblock.volume));
					int magicVariable = (int) (layerFluid.level * 5); //TODO: figure out magic variable for different volumes
					renderFluid(matrices, layerFluid.get(), new Rectangle(new Point(x + 8, (height / 2) - magicVariable - (j * i)), new Dimension(72, j)));
				}
			}
			drawMouseoverTooltip(matrices, mouseX, mouseY);
		}
	}

	@Override
	protected void drawBackground(MatrixStack matrices, float delta, int mouseX, int mouseY) {
		this.client.getTextureManager().bindTexture(GUI);
		drawTexture(matrices, x, y - 32, 0, 0, this.backgroundWidth, this.backgroundHeight + 65);
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
			bb.vertex(matrix, (float) bounds.getMaxX(), (float) bounds.y, 1f).texture(sprite.getMaxU(), sprite.getMinV()).color(r, g, b, a).next();
			bb.vertex(matrix, (float) bounds.x, (float) bounds.y, 1f).texture(sprite.getMinU(), sprite.getMinV()).color(r, g, b, a).next();
			bb.vertex(matrix, (float) bounds.x, (float) bounds.getMaxY(), 1f).texture(sprite.getMinU(), sprite.getMaxV()).color(r, g, b, a).next();
			bb.vertex(matrix, (float) bounds.getMaxX(), (float) bounds.getMaxY(), 1f).texture(sprite.getMaxU(), sprite.getMaxV()).color(r, g, b, a).next();
			tessellator.draw();
		}
	}
}
