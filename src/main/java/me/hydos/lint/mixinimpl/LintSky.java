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
import net.minecraft.client.render.BackgroundRenderer;
import net.minecraft.client.render.BufferBuilder;
import net.minecraft.client.render.BufferRenderer;
import net.minecraft.client.render.Tessellator;
import net.minecraft.client.render.VertexFormat;
import net.minecraft.client.render.VertexFormats;
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
		float alpha;
		float size;
		float someMagicAngle;
		float p;
		float q;
		if (fs != null) {
			RenderSystem.disableTexture();
			RenderSystem.shadeModel(7425);
			matrices.push();
			matrices.multiply(Vector3f.POSITIVE_X.getDegreesQuaternion(90.0F));
			alpha = MathHelper.sin(world.getSkyAngleRadians(tickDelta)) < 0.0F ? 180.0F : 0.0F;
			matrices.multiply(Vector3f.POSITIVE_Z.getDegreesQuaternion(alpha));
			matrices.multiply(Vector3f.POSITIVE_Z.getDegreesQuaternion(90.0F));
			float j = fs[0];
			size = fs[1];
			float l = fs[2];
			Matrix4f matrix4f = matrices.peek().getModel();
			bufferBuilder.begin(6, VertexFormats.POSITION_COLOR);
			bufferBuilder.vertex(matrix4f, 0.0F, 100.0F, 0.0F).color(j, size, l, fs[3]).next();

			for (int n = 0; n <= 16; ++n) {
				someMagicAngle = (float) n * 6.2831855F / 16.0F;
				p = MathHelper.sin(someMagicAngle);
				q = MathHelper.cos(someMagicAngle);
				bufferBuilder.vertex(matrix4f, p * 120.0F, q * 120.0F, -q * 40.0F * fs[3]).color(fs[0], fs[1], fs[2], 0.0F).next();
			}

			bufferBuilder.end();
			BufferRenderer.draw(bufferBuilder);
			matrices.pop();
			RenderSystem.shadeModel(7424);
		}

		RenderSystem.enableTexture();

		// SOLAR AND LUNAR STUFF

		matrices.push();
		alpha = 1.0F - world.getRainGradient(tickDelta);
		RenderSystem.color4f(1.0F, 1.0F, 1.0F, alpha);

		float skyAngle = world.getSkyAngle(tickDelta) * 360.0F;
		Matrix4f skyObjectMatrix = matrices.peek().getModel();

		matrices.multiply(Vector3f.POSITIVE_Y.getDegreesQuaternion(-90.0F));
		renderFraiyaMoons(world, textureManager, matrices, bufferBuilder, skyObjectMatrix, skyAngle, alpha);

		// SUN
		size = 22.0F;

		RenderSystem.blendFuncSeparate(GlStateManager.SrcFactor.SRC_ALPHA, GlStateManager.DstFactor.ONE, GlStateManager.SrcFactor.ONE, GlStateManager.DstFactor.ZERO);
		renderBinarySun(world, textureManager, matrices, bufferBuilder, skyObjectMatrix, size, skyAngle);

		RenderSystem.disableTexture();

		// does this do rain or night? I am inclined to think the latter
		float greyAlpha = world.method_23787(tickDelta) * alpha;
		if (greyAlpha > 0.0F) {
			RenderSystem.color4f(greyAlpha, greyAlpha, greyAlpha, greyAlpha);
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

	private static void renderFraiyaMoons(ClientWorld world, TextureManager textureManager, MatrixStack matrices, BufferBuilder bufferBuilder, Matrix4f skyObjectMatrix, float skyAngle, float r) {
		final boolean debugTransit = false;
		float iOrbitRate = 0.0012f;
		float cOrbitRate = 0.0036f;

		if (debugTransit) {
			iOrbitRate *= 100.0f;
			cOrbitRate *= 100.0f;
		}

		float time = world.getTime();
		float ieseAngle = MathHelper.wrapDegrees(time * iOrbitRate + 10.0f + skyAngle);
		float cairAngle = MathHelper.wrapDegrees(time * cOrbitRate + skyAngle);

		//System.out.println();
		// Iese
		matrices.push();
		matrices.multiply(Vector3f.POSITIVE_X.getDegreesQuaternion(ieseAngle));
		skyObjectMatrix = matrices.peek().getModel();
		RenderSystem.blendFuncSeparate(skyAngle < 90 || skyAngle > 270 ? GlStateManager.SrcFactor.SRC_COLOR : GlStateManager.SrcFactor.SRC_ALPHA, GlStateManager.DstFactor.ONE, GlStateManager.SrcFactor.ONE, GlStateManager.DstFactor.ZERO);

		RenderSystem.color4f(0.8F, 0.8F, 1.0F, r);
		float size = 10.0F;
		textureManager.bindTexture(MOON_PHASES);
		int[] moonPhase = getMoonPhaseAndDirection(skyAngle, ieseAngle);

		float texRight = (float)(moonPhase[0] + 0) / 4.0F;
		float texTop = (float) (moonPhase[1] + 0) / 2.0F;
		float texLeft = (float) (moonPhase[0] + 1) / 4.0F;
		float texBottom = (float) (moonPhase[1] + 1) / 2.0F;
		bufferBuilder.begin(7, VertexFormats.POSITION_TEXTURE);
		bufferBuilder.vertex(skyObjectMatrix, -size, -100.0F, size).texture(texLeft, texBottom).next();
		bufferBuilder.vertex(skyObjectMatrix, size, -100.0F, size).texture(texLeft, texTop).next();
		bufferBuilder.vertex(skyObjectMatrix, size, -100.0F, -size).texture(texRight, texTop).next();
		bufferBuilder.vertex(skyObjectMatrix, -size, -100.0F, -size).texture(texRight, texBottom).next();
		bufferBuilder.end();
		BufferRenderer.draw(bufferBuilder);

		matrices.pop();

		// Cair
		matrices.push();

		matrices.multiply(Vector3f.POSITIVE_X.getDegreesQuaternion(cairAngle));
		skyObjectMatrix = matrices.peek().getModel();
		RenderSystem.blendFuncSeparate(skyAngle < 90 || skyAngle > 270 ? GlStateManager.SrcFactor.SRC_COLOR : GlStateManager.SrcFactor.SRC_ALPHA, GlStateManager.DstFactor.ONE, GlStateManager.SrcFactor.ONE, GlStateManager.DstFactor.ZERO);

		RenderSystem.color4f(1.0F, 0.8F, 0.8F, r);
		size = 16.0F;
		moonPhase = getMoonPhaseAndDirection(skyAngle, cairAngle);

		texRight = (float)(moonPhase[0] + 0) / 4.0F;
		texTop = (float) (moonPhase[1] + 0) / 2.0F;
		texLeft = (float) (moonPhase[0] + 1) / 4.0F;
		texBottom = (float) (moonPhase[1] + 1) / 2.0F;
		bufferBuilder.begin(7, VertexFormats.POSITION_TEXTURE);
		bufferBuilder.vertex(skyObjectMatrix, -size, -100.0F, size).texture(texLeft, texBottom).next();
		bufferBuilder.vertex(skyObjectMatrix, size, -100.0F, size).texture(texLeft, texTop).next();
		bufferBuilder.vertex(skyObjectMatrix, size, -100.0F, -size).texture(texRight, texTop).next();
		bufferBuilder.vertex(skyObjectMatrix, -size, -100.0F, -size).texture(texRight, texBottom).next();
		bufferBuilder.end();
		BufferRenderer.draw(bufferBuilder);

		matrices.pop();
	}

	private static int[] getMoonPhaseAndDirection(float skyAngle, float moonAngle) {
		float diff = MathHelper.wrapDegrees(skyAngle - moonAngle);
		float absDiff = MathHelper.abs(diff);
		int moonPhaseType = 0; // 0/4 = full/new moon. [Inverse] 1 = crescent... 3 = gibbous
		int moonPhaseInverse = 0; // 0 = facing +ve rotation, 1 = facing -ve rotation.

		if (absDiff >= 170) {
			moonPhaseInverse = 1;
		} else if (absDiff > 10) {
			// I could use basic maths and properly divide the area into 3rds but I'm being lazy
			if (absDiff < 62.5) {
				moonPhaseType = 1;
			} else if (absDiff < 117.5) {
				moonPhaseType = 2;
			} else {
				moonPhaseType = 3;
			}

			if (diff > 0) {
				moonPhaseInverse = 1;
				moonPhaseType = 4 - moonPhaseType;
			}
		}

		return new int[] {moonPhaseType, moonPhaseInverse};
	}

	private static void renderBinarySun(ClientWorld world, TextureManager textureManager, MatrixStack matrices, BufferBuilder bufferBuilder, Matrix4f skyObjectMatrix, float size, float skyAngle) {
		matrices.multiply(Vector3f.POSITIVE_X.getDegreesQuaternion(skyAngle));

		float[] data = new float[4];
		final boolean debugTransit = true;
		final float orbitPeriod = debugTransit ? 0.01f : 0.00008f;
		getRelativeAnglesAndDepths(data, world.getTime() * orbitPeriod);

		if (data[2] > data[3]) {
			// ALPHA STAR
			renderAlphaLint(matrices, size, textureManager, bufferBuilder, data);
			renderBetaLint(matrices, size * 0.87f, textureManager, bufferBuilder, data);			
		} else {
			renderBetaLint(matrices, size * 0.87f, textureManager, bufferBuilder, data);			
			renderAlphaLint(matrices, size, textureManager, bufferBuilder, data);
		}

		// BETA STAR
		// TODO hydos proper occlusion when big alpha star covers little beta star

		//if (data[3] <= data[2] || Math.abs(data[0] - data[1]) > 0.07f) {
		//}
	}

	private static void renderAlphaLint(MatrixStack matrices, final float size, TextureManager textureManager, BufferBuilder bufferBuilder, final float[] data) {
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
	}

	private static void renderBetaLint(MatrixStack matrices, final float size, TextureManager textureManager, BufferBuilder bufferBuilder, final float[] data) {
		matrices.push();

		matrices.multiply(Vector3f.POSITIVE_Z.getRadialQuaternion(data[1]));
		matrices.multiply(Vector3f.POSITIVE_X.getRadialQuaternion(data[1] / 2.0f));
		float vertY = data[3] / 2.0f;

		Matrix4f betaMatrix = matrices.peek().getModel();

		textureManager.bindTexture(BETA_LINT);
		bufferBuilder.begin(7, VertexFormats.POSITION_TEXTURE);
		bufferBuilder.vertex(betaMatrix, -size, vertY, -size).texture(0.0F, 0.0F).next();
		bufferBuilder.vertex(betaMatrix, size, vertY, -size).texture(1.0F, 0.0F).next();
		bufferBuilder.vertex(betaMatrix, size, vertY, size).texture(1.0F, 1.0F).next();
		bufferBuilder.vertex(betaMatrix, -size, vertY, size).texture(0.0F, 1.0F).next();
		bufferBuilder.end();
		BufferRenderer.draw(bufferBuilder);

		matrices.pop();

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

	public static Vec3d adjustFogColor(Vec3d fogColour, float sunHeight) {
		return fogColour.multiply((double)(sunHeight * 0.82F + 0.18F), (double)(sunHeight * 0.82F + 0.18F), (double)(sunHeight * 0.78F + 0.22F));
	}
}
