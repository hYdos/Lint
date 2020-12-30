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

package me.hydos.lint.client.render.fluid;

import net.fabricmc.fabric.api.client.render.fluid.v1.FluidRenderHandler;
import net.fabricmc.fabric.api.client.render.fluid.v1.FluidRenderHandlerRegistry;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.texture.Sprite;
import net.minecraft.fluid.Fluid;
import net.minecraft.util.math.BlockPos;
import org.jetbrains.annotations.Nullable;

import java.util.HashMap;
import java.util.Map;

/**
 * Class used to render fluids in Screens
 * based off Shedaniel's SimpleFluidRenderer
 */
public final class LintFluidRenderer {
	private static final Map<Fluid, CachedFluid> FLUID_CACHE = new HashMap<>();

	@Nullable
	public static LintFluidRenderer.CachedFluid from(Fluid fluid) {
		return FLUID_CACHE.computeIfAbsent(fluid, CachedFluid::from);
	}

	public static final class CachedFluid {
		private final Sprite sprite;
		private final int color;

		public CachedFluid(Sprite sprite, int color) {
			this.sprite = sprite;
			this.color = color;
		}

		public static CachedFluid from(Fluid fluid) {
			FluidRenderHandler fluidRenderHandler = FluidRenderHandlerRegistry.INSTANCE.get(fluid);
			if (fluidRenderHandler == null) {
				return null;
			} else {
				Sprite[] sprites = fluidRenderHandler.getFluidSprites(MinecraftClient.getInstance().world, MinecraftClient.getInstance().world == null ? null : BlockPos.ORIGIN, fluid.getDefaultState());
				int color = -1;
				if (MinecraftClient.getInstance().world != null) {
					color = fluidRenderHandler.getFluidColor(MinecraftClient.getInstance().world, BlockPos.ORIGIN, fluid.getDefaultState());
				}

				return new CachedFluid(sprites[0], color);
			}
		}

		public Sprite getSprite() {
			return this.sprite;
		}

		public int getColor() {
			return this.color;
		}
	}
}
