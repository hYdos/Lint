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
public final class Fluid2DRenderer {
	private static final Map<Fluid, CachedFluid> FLUID_CACHE = new HashMap<>();

	@Nullable
	public static Fluid2DRenderer.CachedFluid from(Fluid fluid) {
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
