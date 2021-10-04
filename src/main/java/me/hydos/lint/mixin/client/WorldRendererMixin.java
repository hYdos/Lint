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

import me.hydos.lint.mixinimpl.LintSky;
import me.hydos.lint.world.dimension.LintDimensions;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gl.VertexBuffer;
import net.minecraft.client.render.WorldRenderer;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.util.math.Matrix4f;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(WorldRenderer.class)
public class WorldRendererMixin {
	@Shadow
	private VertexBuffer starsBuffer;
	@Shadow
	private VertexBuffer lightSkyBuffer;
	@Shadow
	private VertexBuffer darkSkyBuffer;

	@Shadow
	@Final
	private MinecraftClient client;

	@Shadow
	private ClientWorld world;

	@Inject(at = @At(value = "INVOKE", target = "Ljava/lang/Runnable;run()V", shift = At.Shift.AFTER), method = "renderSky", cancellable = true)
	private void renderLintSky(MatrixStack matrices, Matrix4f skyObjectMatrix, float tickDelta, Runnable runnable, CallbackInfo info) {
		if (this.world.getRegistryKey().equals(LintDimensions.FRAIYA_WORLD)) {
			LintSky.renderLintSky(matrices, skyObjectMatrix, tickDelta, runnable, world, client, lightSkyBuffer, darkSkyBuffer, starsBuffer);
			info.cancel();
		}
	}
}
