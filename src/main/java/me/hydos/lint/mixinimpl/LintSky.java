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

package me.hydos.lint.mixinimpl;

import com.mojang.blaze3d.platform.GlStateManager;
import com.mojang.blaze3d.systems.RenderSystem;
import me.hydos.lint.Lint;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gl.VertexBuffer;
import net.minecraft.client.render.*;
import net.minecraft.client.texture.TextureManager;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.client.util.math.Vector3f;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Matrix4f;
import net.minecraft.util.math.Vec3d;

public class LintSky {
	private static final Identifier MOON_PHASES = new Identifier("textures/environment/moon_phases.png");
	private static final Identifier ALPHA_LINT = Lint.id("textures/environment/alpha_lint.png");
	private static final Identifier BETA_LINT = Lint.id("textures/environment/beta_lint.png");
	private static final float PI = (float) Math.PI;

	public static void renderLintSky(MatrixStack matrices, TextureManager textureManager,
									 VertexBuffer lightSkyBuffer, VertexBuffer darkSkyBuffer, VertexBuffer starsBuffer,
									 VertexFormat skyVertexFormat, MinecraftClient client, ClientWorld world, float tickDelta) {
		RenderSystem.disableTexture();
		Vec3d vec3d = world.method_23777(client.gameRenderer.getCamera().getBlockPos(), tickDelta);
		float f = (float) vec3d.x;
		float g = (float) vec3d.y;
		float h = (float) vec3d.z;
		BackgroundRenderer.setFogBlack();
		BufferBuilder bufferBuilder = Tessellator.getInstance().getBuffer();
		RenderSystem.depthMask(false);
		RenderSystem.enableFog();
		RenderSystem.color3f(f, g, h);
		lightSkyBuffer.bind();
		skyVertexFormat.startDrawing(0L);
		lightSkyBuffer.draw(matrices.peek().getModel(), 7);
		VertexBuffer.unbind();
		skyVertexFormat.endDrawing();
		RenderSystem.disableFog();
		RenderSystem.disableAlphaTest();
		RenderSystem.enableBlend();
		RenderSystem.defaultBlendFunc();
		float[] fs = world.getSkyProperties().getFogColorOverride(world.getSkyAngle(tickDelta), tickDelta);
		float r;
		float size;
		float o;
		float p;
		float q;
		if (fs != null) {
			RenderSystem.disableTexture();
			RenderSystem.shadeModel(7425);
			matrices.push();
			matrices.multiply(Vector3f.POSITIVE_X.getDegreesQuaternion(90.0F));
			r = MathHelper.sin(world.getSkyAngleRadians(tickDelta)) < 0.0F ? 180.0F : 0.0F;
			matrices.multiply(Vector3f.POSITIVE_Z.getDegreesQuaternion(r));
			matrices.multiply(Vector3f.POSITIVE_Z.getDegreesQuaternion(90.0F));
			float j = fs[0];
			size = fs[1];
			float l = fs[2];
			Matrix4f matrix4f = matrices.peek().getModel();
			bufferBuilder.begin(6, VertexFormats.POSITION_COLOR);
			bufferBuilder.vertex(matrix4f, 0.0F, 100.0F, 0.0F).color(j, size, l, fs[3]).next();

			for (int n = 0; n <= 16; ++n) {
				o = (float) n * 6.2831855F / 16.0F;
				p = MathHelper.sin(o);
				q = MathHelper.cos(o);
				bufferBuilder.vertex(matrix4f, p * 120.0F, q * 120.0F, -q * 40.0F * fs[3]).color(fs[0], fs[1], fs[2], 0.0F).next();
			}

			bufferBuilder.end();
			BufferRenderer.draw(bufferBuilder);
			matrices.pop();
			RenderSystem.shadeModel(7424);
		}

		RenderSystem.enableTexture();
		RenderSystem.blendFuncSeparate(GlStateManager.SrcFactor.SRC_ALPHA, GlStateManager.DstFactor.ONE, GlStateManager.SrcFactor.ONE, GlStateManager.DstFactor.ZERO);
		matrices.push();
		r = 1.0F - world.getRainGradient(tickDelta);
		RenderSystem.color4f(1.0F, 1.0F, 1.0F, r);

		// SUN
		size = 22.0F;
		Matrix4f skyObjectMatrix = matrices.peek().getModel();
		renderBinarySun(world, textureManager, matrices, bufferBuilder, skyObjectMatrix, size, world.getSkyAngle(tickDelta) * 360.0F);

		RenderSystem.color4f(0.8F, 0.8F, 1.0F, r);
		size = 20.0F;
		textureManager.bindTexture(MOON_PHASES);
		int moonPhase = world.getMoonPhase();
		int moonPhaseType = moonPhase % 4;
		int moonPhaseRotation = moonPhase / 4 % 2;
		float w = (float) (moonPhaseType + 0) / 4.0F;
		o = (float) (moonPhaseRotation + 0) / 2.0F;
		p = (float) (moonPhaseType + 1) / 4.0F;
		q = (float) (moonPhaseRotation + 1) / 2.0F;
		bufferBuilder.begin(7, VertexFormats.POSITION_TEXTURE);
		bufferBuilder.vertex(skyObjectMatrix, -size, -100.0F, size).texture(p, q).next();
		bufferBuilder.vertex(skyObjectMatrix, size, -100.0F, size).texture(w, q).next();
		bufferBuilder.vertex(skyObjectMatrix, size, -100.0F, -size).texture(w, o).next();
		bufferBuilder.vertex(skyObjectMatrix, -size, -100.0F, -size).texture(p, o).next();
		bufferBuilder.end();
		BufferRenderer.draw(bufferBuilder);

		RenderSystem.disableTexture();
		float aa = world.method_23787(tickDelta) * r;
		if (aa > 0.0F) {
			RenderSystem.color4f(aa, aa, aa, aa);
			starsBuffer.bind();
			skyVertexFormat.startDrawing(0L);
			starsBuffer.draw(matrices.peek().getModel(), 7);
			VertexBuffer.unbind();
			skyVertexFormat.endDrawing();
		}

		RenderSystem.color4f(1.0F, 1.0F, 1.0F, 1.0F);
		RenderSystem.disableBlend();
		RenderSystem.enableAlphaTest();
		RenderSystem.enableFog();
		matrices.pop();
		RenderSystem.disableTexture();
		RenderSystem.color3f(0.0F, 0.0F, 0.0F);
		double d = client.player.getCameraPosVec(tickDelta).y - world.getLevelProperties().getSkyDarknessHeight();

		if (d < 0.0D) {
			matrices.push();
			matrices.translate(0.0D, 12.0D, 0.0D);
			darkSkyBuffer.bind();
			skyVertexFormat.startDrawing(0L);
			darkSkyBuffer.draw(matrices.peek().getModel(), 7);
			VertexBuffer.unbind();
			skyVertexFormat.endDrawing();
			matrices.pop();
		}

		if (world.getSkyProperties().isAlternateSkyColor()) {
			RenderSystem.color3f(f * 0.2F + 0.04F, g * 0.2F + 0.04F, h * 0.6F + 0.1F);
		} else {
			RenderSystem.color3f(f, g, h);
		}

		RenderSystem.enableTexture();
		RenderSystem.depthMask(true);
		RenderSystem.disableFog();
	}

	private static void renderBinarySun(ClientWorld world, TextureManager textureManager, MatrixStack matrices, BufferBuilder bufferBuilder, Matrix4f skyObjectMatrix, float size, float skyAngle) {
		matrices.multiply(Vector3f.POSITIVE_Y.getDegreesQuaternion(-90.0F));
		matrices.multiply(Vector3f.POSITIVE_X.getDegreesQuaternion(skyAngle));

		float[] data = new float[4];
		final boolean debugTransit = false;
		final float orbitPeriod = debugTransit ? 0.01f : 0.00008f;
		getRelativeAnglesAndDepths(data, world.getTime() * orbitPeriod);

		// ALPHA STAR
		matrices.push();

		matrices.multiply(Vector3f.POSITIVE_Z.getRadialQuaternion(data[0]));
		matrices.multiply(Vector3f.POSITIVE_X.getRadialQuaternion(data[0] / 2.0f));
		float vertY = data[2] / 2.0f;

		Matrix4f alphaMatrix = matrices.peek().getModel();

		textureManager.bindTexture(ALPHA_LINT);
		bufferBuilder.begin(7, VertexFormats.POSITION_TEXTURE);
		bufferBuilder.vertex(alphaMatrix, -size, vertY, -size).texture(0.0F, 0.0F).next();
		bufferBuilder.vertex(alphaMatrix, size, vertY, -size).texture(1.0F, 0.0F).next();
		bufferBuilder.vertex(alphaMatrix, size, vertY, size).texture(1.0F, 1.0F).next();
		bufferBuilder.vertex(alphaMatrix, -size, vertY, size).texture(0.0F, 1.0F).next();
		bufferBuilder.end();
		BufferRenderer.draw(bufferBuilder);

		matrices.pop();

		// BETA STAR
		// TODO hydos proper occlusion when big alpha star covers little beta star
		if (data[3] <= data[2] || Math.abs(data[0] - data[1]) > 0.09f) {
			matrices.push();
			size *= 0.87;

			matrices.multiply(Vector3f.POSITIVE_Z.getRadialQuaternion(data[1]));
			matrices.multiply(Vector3f.POSITIVE_X.getRadialQuaternion(data[1] / 2.0f));
			vertY = data[3] / 2.0f;

			textureManager.bindTexture(BETA_LINT);
			bufferBuilder.begin(7, VertexFormats.POSITION_TEXTURE);
			bufferBuilder.vertex(skyObjectMatrix, -size, vertY, -size).texture(0.0F, 0.0F).next();
			bufferBuilder.vertex(skyObjectMatrix, size, vertY, -size).texture(1.0F, 0.0F).next();
			bufferBuilder.vertex(skyObjectMatrix, size, vertY, size).texture(1.0F, 1.0F).next();
			bufferBuilder.vertex(skyObjectMatrix, -size, vertY, size).texture(0.0F, 1.0F).next();
			bufferBuilder.end();
			BufferRenderer.draw(bufferBuilder);

			matrices.pop();
		}
	}

	private static void getRelativeAnglesAndDepths(float[] result, float t) {
		t = t % (2 * PI);
		t = PI * MathHelper.sin((t - PI) * 0.5f);
		float cosT = MathHelper.cos(t);

		// alpha pos

		float ax = cosT - 0.7f;
		float ay = MathHelper.sin(t);

		// beta pos

		float bx = 1.25f * (-cosT + 0.6f);
		float by = -ay;

		// observer pos

		float ox = -6f;
		float oy = 0;

		// get angles
		float day = ay - oy;
		float dax = ax - ox;
		float dby = by - oy;
		float dbx = bx - ox;

		// == Angles ==
		result[0] = (float) MathHelper.atan2(day, dax); // atan(dy/dx)
		result[1] = (float) MathHelper.atan2(dby, dbx); // atan(dy/dx)

		// == Depths ==
		result[2] = (day * day + dax * dax) * 10.0f;
		result[3] = (dby * dby + dbx * dbx) * 10.0f;
	}
}
