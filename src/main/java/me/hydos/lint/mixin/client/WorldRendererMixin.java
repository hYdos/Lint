package me.hydos.lint.mixin.client;

import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import me.hydos.lint.mixinimpl.LintSky;
import me.hydos.lint.world.dimension.Dimensions;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gl.VertexBuffer;
import net.minecraft.client.render.VertexFormat;
import net.minecraft.client.render.WorldRenderer;
import net.minecraft.client.texture.TextureManager;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.client.world.ClientWorld;

@Mixin(WorldRenderer.class)
public class WorldRendererMixin {
	@Shadow
	@Final
	private VertexFormat skyVertexFormat;

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
	@Final
	private TextureManager textureManager;

	@Shadow
	private ClientWorld world;

	@Inject(at = @At("HEAD"), method = "renderSky", cancellable = true)
	private void renderLintSky(MatrixStack matrices, float tickDelta, CallbackInfo info) {
		if (this.world.getDimension() == Dimensions.HAYKAM) {
			LintSky.renderLintSky(matrices, this.textureManager,
					this.lightSkyBuffer, this.darkSkyBuffer, this.starsBuffer,
					this.skyVertexFormat, this.client, this.world, tickDelta);
			info.cancel();
		}
	}
}
