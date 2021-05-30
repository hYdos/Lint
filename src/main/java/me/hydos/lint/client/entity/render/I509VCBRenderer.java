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

package me.hydos.lint.client.entity.render;

import me.hydos.lint.Lint;
import me.hydos.lint.client.entity.model.I509VCBEntityModel;
import me.hydos.lint.entity.aggressive.I509VCBEntity;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.EnderDragonEntityRenderer;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.render.entity.MobEntityRenderer;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.LivingEntity;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.MathHelper;

public class I509VCBRenderer extends MobEntityRenderer<I509VCBEntity, I509VCBEntityModel> {
	public static final Identifier SKIN = Lint.id("textures/entity/i509vcb.png");

	public I509VCBRenderer(EntityRendererFactory.Context context) {
		super(context, new I509VCBEntityModel(), 1);
	}

	@Override
	public Identifier getTexture(I509VCBEntity entity) {
		return SKIN;
	}

	@Override
	public void render(I509VCBEntity cone, float yaw, float tickDelta, MatrixStack matrixStack, VertexConsumerProvider vertexConsumerProvider, int i) {
		matrixStack.push();
		float l = MathHelper.sin(((float) cone.age + tickDelta) / 8.0F + 3) * 0.125F;
		matrixStack.translate(0.0D, l + 0.60F, 0.0D);

		if (cone.getTarget() != null) {
			LivingEntity target = cone.getTarget();

			float x = (float) (cone.getX() - target.getX());
			float y = (float) (cone.getY() - target.getY());// - l - 0.90F;
			float z = (float) (cone.getZ() - target.getZ());
			EnderDragonEntityRenderer.renderCrystalBeam(x, y, z, tickDelta, cone.age, matrixStack, vertexConsumerProvider, i);
		}

		super.render(cone, yaw, tickDelta, matrixStack, vertexConsumerProvider, i);
		matrixStack.pop();
	}
}
