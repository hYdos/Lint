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
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Matrix4f;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.math.Vec3f;

// FIXME: fix night sky (the bottom half of the sky is lit)
// TODO: put these in shaders and just render the shader
public class LintSky {
	private static final Identifier ALPHA_LINT = Lint.id("textures/environment/alpha_lint.png");
	private static final Identifier BETA_LINT = Lint.id("textures/environment/beta_lint.png");
	private static final float PI = (float) Math.PI;
	private static final boolean DEBUG = true; // NOTE: change this for debugging

	public static void renderLintSky(MatrixStack matrices,
	                                 Matrix4f skyObjectMatrix,
	                                 float tickDelta,
	                                 Runnable runnable,
	                                 ClientWorld world,
	                                 MinecraftClient client,
	                                 VertexBuffer lightSkyBuffer,
	                                 VertexBuffer darkSkyBuffer,
	                                 VertexBuffer starsBuffer) {
		RenderSystem.disableTexture();
		Vec3d vec3d = world.method_23777(client.gameRenderer.getCamera().getPos(), tickDelta);
		float g = (float)vec3d.x;
		float h = (float)vec3d.y;
		float i = (float)vec3d.z;
		float skyAngle = world.getSkyAngle(tickDelta);
		BackgroundRenderer.setFogBlack();
		BufferBuilder bufferBuilder = Tessellator.getInstance().getBuffer();
		RenderSystem.depthMask(false);
		RenderSystem.setShaderColor(g, h, i, 1.0F);
		Shader shader = RenderSystem.getShader();
		lightSkyBuffer.setShader(matrices.peek().getModel(), skyObjectMatrix, shader);
		RenderSystem.enableBlend();
		RenderSystem.defaultBlendFunc();
		float[] fs = world.getSkyProperties().getFogColorOverride(world.getSkyAngle(tickDelta), tickDelta);
		float s;
		float size;
		float p;
		float q;
		float r = 0;
		if (fs != null) {
			RenderSystem.setShader(GameRenderer::getPositionColorShader);
			RenderSystem.disableTexture();
			RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
			matrices.push();
			matrices.multiply(Vec3f.POSITIVE_X.getDegreesQuaternion(90.0F));
			s = MathHelper.sin(world.getSkyAngleRadians(tickDelta)) < 0.0F ? 180.0F : 0.0F;
			matrices.multiply(Vec3f.POSITIVE_Z.getDegreesQuaternion(s));
			matrices.multiply(Vec3f.POSITIVE_Z.getDegreesQuaternion(90.0F));
			float k = fs[0];
			size = fs[1];
			float m = fs[2];
			Matrix4f matrix4f2 = matrices.peek().getModel();
			bufferBuilder.begin(VertexFormat.DrawMode.TRIANGLE_FAN, VertexFormats.POSITION_COLOR);
			bufferBuilder.vertex(matrix4f2, 0.0F, 100.0F, 0.0F).color(k, size, m, fs[3]).next();

			for(int o = 0; o <= 16; ++o) {
				p = (float)o * 6.2831855F / 16.0F;
				q = MathHelper.sin(p);
				r = MathHelper.cos(p);
				bufferBuilder.vertex(matrix4f2, q * 120.0F, r * 120.0F, -r * 40.0F * fs[3]).color(fs[0], fs[1], fs[2], 0.0F).next();
			}

			bufferBuilder.end();
			BufferRenderer.draw(bufferBuilder);
			matrices.pop();
		}

		RenderSystem.enableTexture();
		RenderSystem.blendFuncSeparate(GlStateManager.SrcFactor.SRC_ALPHA, GlStateManager.DstFactor.ONE, GlStateManager.SrcFactor.ONE, GlStateManager.DstFactor.ZERO);
		matrices.push();
		s = 1.0F - world.getRainGradient(tickDelta);
		RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, s);
		matrices.multiply(Vec3f.POSITIVE_Y.getDegreesQuaternion(-90.0F));
		matrices.multiply(Vec3f.POSITIVE_X.getDegreesQuaternion(skyAngle * 360.0F));
		size = 30.0F;
		RenderSystem.setShader(GameRenderer::getPositionTexShader);
		// render sun
		renderBinarySun(world, matrices, bufferBuilder, size, skyAngle);
		size = 10.0F;
		// render moon
		renderFraiyaMoons(world, matrices, bufferBuilder, skyObjectMatrix, skyAngle, r, size);
		RenderSystem.disableTexture();

		// render stars
		float ab = world.method_23787(tickDelta) * s;
		if (ab > 0.0F) {
			RenderSystem.setShaderColor(ab, ab, ab, ab);
			BackgroundRenderer.method_23792();
			starsBuffer.setShader(matrices.peek().getModel(), skyObjectMatrix, GameRenderer.getPositionShader());
			runnable.run();
		}

		RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
		RenderSystem.disableBlend();
		matrices.pop();
		RenderSystem.disableTexture();
		RenderSystem.setShaderColor(0.0F, 0.0F, 0.0F, 1.0F);
		assert client.player != null;
		double skyDarkness = client.player.getCameraPosVec(tickDelta).y - world.getLevelProperties().getSkyDarknessHeight(world);
		if (skyDarkness < 0.0D) {
			matrices.push();
			matrices.translate(0.0D, 12.0D, 0.0D);
			darkSkyBuffer.setShader(matrices.peek().getModel(), skyObjectMatrix, shader);
			matrices.pop();
		}

		if (world.getSkyProperties().isAlternateSkyColor()) {
			RenderSystem.setShaderColor(g * 0.2F + 0.04F, h * 0.2F + 0.04F, i * 0.6F + 0.1F, 1.0F);
		} else {
			RenderSystem.setShaderColor(g, h, i, 1.0F);
		}

		RenderSystem.enableTexture();
		RenderSystem.depthMask(true);
	}
	
	private static void renderFraiyaMoons(ClientWorld world, MatrixStack matrices, BufferBuilder bufferBuilder, Matrix4f skyObjectMatrix, float skyAngle, float r, float size) {
		float iOrbitRate = 0.0012f;
		float cOrbitRate = 0.0036f;

		if (DEBUG) {
			iOrbitRate *= 100.0f;
			cOrbitRate *= 100.0f;
		}

		float time = world.getTime();
		float ieseAngle = MathHelper.wrapDegrees(time * iOrbitRate + 10.0f + skyAngle);
		float cairAngle = MathHelper.wrapDegrees(time * cOrbitRate + skyAngle);

		//System.out.println();
		// Iese
		matrices.push();
		matrices.multiply(Vec3f.POSITIVE_X.getDegreesQuaternion(ieseAngle));
		skyObjectMatrix = matrices.peek().getModel();
		RenderSystem.blendFuncSeparate(skyAngle < 90 || skyAngle > 270 ? GlStateManager.SrcFactor.SRC_COLOR : GlStateManager.SrcFactor.SRC_ALPHA, GlStateManager.DstFactor.ONE, GlStateManager.SrcFactor.ONE, GlStateManager.DstFactor.ZERO);

		RenderSystem.setShaderColor(0.8F, 0.8F, 1.0F, r);
		RenderSystem.setShaderTexture(0, WorldRenderer.MOON_PHASES);
		int[] moonPhase = getMoonPhaseAndDirection(skyAngle, ieseAngle);

		float texRight = (float)(moonPhase[0]) / 4.0F;
		float texTop = (float) (moonPhase[1]) / 2.0F;
		float texLeft = (float) (moonPhase[0] + 1) / 4.0F;
		float texBottom = (float) (moonPhase[1] + 1) / 2.0F;
		bufferBuilder.begin(VertexFormat.DrawMode.QUADS, VertexFormats.POSITION_TEXTURE);
		bufferBuilder.vertex(skyObjectMatrix, -size, -100.0F, size).texture(texLeft, texBottom).next();
		bufferBuilder.vertex(skyObjectMatrix, size, -100.0F, size).texture(texRight, texBottom).next();
		bufferBuilder.vertex(skyObjectMatrix, size, -100.0F, -size).texture(texRight, texTop).next();
		bufferBuilder.vertex(skyObjectMatrix, -size, -100.0F, -size).texture(texLeft, texTop).next();
		bufferBuilder.end();
		BufferRenderer.draw(bufferBuilder);

		matrices.pop();

		// Cair
		matrices.push();

		matrices.multiply(Vec3f.POSITIVE_X.getDegreesQuaternion(cairAngle));
		skyObjectMatrix = matrices.peek().getModel();
		RenderSystem.blendFuncSeparate(skyAngle < 90 || skyAngle > 270 ? GlStateManager.SrcFactor.SRC_COLOR : GlStateManager.SrcFactor.SRC_ALPHA, GlStateManager.DstFactor.ONE, GlStateManager.SrcFactor.ONE, GlStateManager.DstFactor.ZERO);

		RenderSystem.setShaderColor(1.0F, 0.8F, 0.8F, r);
		size = 16.0F;
		moonPhase = getMoonPhaseAndDirection(skyAngle, cairAngle);

		texRight = (float)(moonPhase[0]) / 4.0F;
		texTop = (float) (moonPhase[1]) / 2.0F;
		texLeft = (float) (moonPhase[0] + 1) / 4.0F;
		texBottom = (float) (moonPhase[1] + 1) / 2.0F;
		bufferBuilder.begin(VertexFormat.DrawMode.QUADS, VertexFormats.POSITION_TEXTURE);
		bufferBuilder.vertex(skyObjectMatrix, -size, -100.0F, size).texture(texLeft, texBottom).next();
		bufferBuilder.vertex(skyObjectMatrix, size, -100.0F, size).texture(texRight, texBottom).next();
		bufferBuilder.vertex(skyObjectMatrix, size, -100.0F, -size).texture(texRight, texTop).next();
		bufferBuilder.vertex(skyObjectMatrix, -size, -100.0F, -size).texture(texLeft, texTop).next();
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

	public static void renderBinarySun(ClientWorld world, MatrixStack matrices, BufferBuilder bufferBuilder, float size, float skyAngle) {
		matrices.multiply(Vec3f.POSITIVE_X.getDegreesQuaternion(skyAngle));

		float[] data = new float[4];
		final float orbitPeriod = DEBUG ? 0.01f : 0.00008f;
		getRelativeAnglesAndDepths(data, world.getTime() * orbitPeriod);

		// ALPHA STAR
		renderAlphaLint(matrices, size, bufferBuilder, data);

		// BETA STAR
		// TODO hydos proper occlusion when big alpha star covers little beta star

		if (data[3] <= data[2] || Math.abs(data[0] - data[1]) > 0.07f) {
			renderBetaLint(matrices, size * 0.87f, bufferBuilder, data);
		}
	}

	private static void renderAlphaLint(MatrixStack matrices, final float size, BufferBuilder bufferBuilder, final float[] data) {
		matrices.push();

		matrices.multiply(Vec3f.POSITIVE_Z.getRadialQuaternion(data[0]));
		matrices.multiply(Vec3f.POSITIVE_X.getRadialQuaternion(data[0] / 2.0f));
		float vertY = data[2] / 2.0f;

		Matrix4f alphaMatrix = matrices.peek().getModel();

		RenderSystem.setShaderTexture(0, ALPHA_LINT);
		bufferBuilder.begin(VertexFormat.DrawMode.QUADS, VertexFormats.POSITION_TEXTURE);
		bufferBuilder.vertex(alphaMatrix, -size, vertY, -size).texture(0.0F, 0.0F).next();
		bufferBuilder.vertex(alphaMatrix, size, vertY, -size).texture(1.0F, 0.0F).next();
		bufferBuilder.vertex(alphaMatrix, size, vertY, size).texture(1.0F, 1.0F).next();
		bufferBuilder.vertex(alphaMatrix, -size, vertY, size).texture(0.0F, 1.0F).next();
		bufferBuilder.end();
		BufferRenderer.draw(bufferBuilder);

		matrices.pop();
	}

	private static void renderBetaLint(MatrixStack matrices, final float size, BufferBuilder bufferBuilder, final float[] data) {
		matrices.push();

		matrices.multiply(Vec3f.POSITIVE_Z.getRadialQuaternion(data[1]));
		matrices.multiply(Vec3f.POSITIVE_X.getRadialQuaternion(data[1] / 2.0f));
		float vertY = data[3] / 2.0f;

		Matrix4f betaMatrix = matrices.peek().getModel();

		RenderSystem.setShaderTexture(0, BETA_LINT);
		bufferBuilder.begin(VertexFormat.DrawMode.QUADS, VertexFormats.POSITION_TEXTURE);
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
		return fogColour.multiply(sunHeight * 0.82F + 0.18F, sunHeight * 0.82F + 0.18F, sunHeight * 0.78F + 0.22F);
	}
}
