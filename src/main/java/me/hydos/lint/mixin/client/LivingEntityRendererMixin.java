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

import me.hydos.lint.Lint;
import net.minecraft.client.render.entity.LivingEntityRenderer;
import net.minecraft.client.render.entity.model.EntityModel;
import net.minecraft.entity.LivingEntity;
import net.minecraft.util.Identifier;
import org.spongepowered.asm.mixin.Mixin;

@Mixin(LivingEntityRenderer.class)
public class LivingEntityRendererMixin<T extends LivingEntity, M extends EntityModel<T>> {

	private static final Identifier MODEL = Lint.id("models/misc/transmutation");

//	@Redirect(method = "render", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/render/entity/model/EntityModel;render(Lnet/minecraft/client/util/math/MatrixStack;Lnet/minecraft/client/render/VertexConsumer;IIFFFF)V"))
//	private void render(M entityModel, MatrixStack matrices, VertexConsumer vertices, int light, int overlay, float red, float green, float blue, float alpha,
//						T livingEntity, float yaw, float tickDelta, MatrixStack matrixStack, VertexConsumerProvider vertexConsumerProvider, int $light) {
//		if (livingEntity.hasStatusEffect(LintStatusEffects.TRANSMUTATION)) {
//			BakedModel model = Myron.getModel(MODEL);
//
//			if (model != null) {
//				VertexConsumer consumer = vertexConsumerProvider.getBuffer(RenderLayer.getSolid());
//
//				matrices.push();
//				matrices.scale(1, -1, 1);
//				MatrixStack.Entry entry = matrices.peek();
//
//				model.getQuads(null, null, livingEntity.world.random).forEach(quad -> {
//					consumer.quad(entry, quad, 1F, 1F, 1F, light, overlay);
//				});
//
//				matrices.pop();
//				return;
//			}
//		}
//
//		entityModel.render(matrices, vertices, light, overlay, red, green, blue, alpha);
//	}
}
