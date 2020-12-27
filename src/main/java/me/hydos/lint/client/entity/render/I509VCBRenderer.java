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
import net.minecraft.client.render.entity.EntityRenderDispatcher;
import net.minecraft.client.render.entity.MobEntityRenderer;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.Box;
import net.minecraft.util.math.MathHelper;

public class I509VCBRenderer extends MobEntityRenderer<I509VCBEntity, I509VCBEntityModel> {

	public static final Identifier SKIN = Lint.id("textures/entity/i509vcb.png");

	public I509VCBRenderer(EntityRenderDispatcher dispatcher) {
		super(dispatcher, new I509VCBEntityModel(), 1);
	}

	@Override
	public Identifier getTexture(I509VCBEntity entity) {
		return SKIN;
	}

	@Override
	public void render(I509VCBEntity cone, float f, float g, MatrixStack matrixStack, VertexConsumerProvider vertexConsumerProvider, int i) {
		matrixStack.push();
		float l = MathHelper.sin(((float) cone.age + g) / 10.0F + 3) * 0.5f;
		matrixStack.translate(0.0D, l + 0.25F, 0.0D);
		for (PlayerEntity player : cone.world.getEntitiesByClass(PlayerEntity.class, new Box(cone.getX() - 10, cone.getY() - 10, cone.getZ() - 10, cone.getX() + 10, cone.getY() + 10, cone.getZ() + 10), LivingEntity::isAlive)) {
			float x = (float) (player.getX() - cone.getX());
			float y = (float) (player.getY() - cone.getY()) - l - 0.5f;
			float z = (float) (player.getZ() - cone.getZ());
			EnderDragonEntityRenderer.renderCrystalBeam(x, y, z, f, cone.age, matrixStack, vertexConsumerProvider, i);
		}
		super.render(cone, f, g, matrixStack, vertexConsumerProvider, i);
		matrixStack.pop();
	}
}
