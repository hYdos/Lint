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

package me.hydos.lint.client.entity.render.feature;

import me.hydos.lint.client.entity.model.TinyPotatoEntityModel;
import me.hydos.lint.client.entity.render.TinyPotatoEntityRenderer;
import me.hydos.lint.entity.Entities;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.render.OverlayTexture;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.feature.FeatureRenderer;
import net.minecraft.client.render.entity.feature.FeatureRendererContext;
import net.minecraft.client.render.entity.model.PlayerEntityModel;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.nbt.CompoundTag;

@Environment(EnvType.CLIENT)
public class LilTaterShoulderFeatureRenderer<T extends PlayerEntity> extends FeatureRenderer<T, PlayerEntityModel<T>> {

	private final TinyPotatoEntityModel model = new TinyPotatoEntityModel();

	public LilTaterShoulderFeatureRenderer(FeatureRendererContext<T, PlayerEntityModel<T>> context) {
		super(context);
	}

	public void render(MatrixStack matrixStack, VertexConsumerProvider vertexConsumerProvider, int i, T playerEntity, float f, float g, float h, float j, float k, float l) {
		this.renderShoulderParrot(matrixStack, vertexConsumerProvider, i, playerEntity, f, g, k, l, true);
		this.renderShoulderParrot(matrixStack, vertexConsumerProvider, i, playerEntity, f, g, k, l, false);
	}

	private void renderShoulderParrot(MatrixStack matrixStack, VertexConsumerProvider vertexConsumerProvider, int i, T playerEntity, float f, float g, float h, float j, boolean bl) {
		CompoundTag compoundTag = bl ? playerEntity.getShoulderEntityLeft() : playerEntity.getShoulderEntityRight();
		EntityType.get(compoundTag.getString("id")).filter((entityType) -> entityType == Entities.TINY_POTATO).ifPresent((entityType) -> {
			matrixStack.push();
			matrixStack.scale(0.6f, 0.6f, 0.6f);
			matrixStack.translate(bl ? 0.6D : -0.6D, playerEntity.isInSneakingPose() ? -1.2D : -1.5D, 0.0D);
			VertexConsumer vertexConsumer = vertexConsumerProvider.getBuffer(this.model.getLayer(TinyPotatoEntityRenderer.TINY_POTATO));
			this.model.renderOnShoulder(matrixStack, vertexConsumer, i, OverlayTexture.DEFAULT_UV);
			matrixStack.pop();
		});
	}
}
