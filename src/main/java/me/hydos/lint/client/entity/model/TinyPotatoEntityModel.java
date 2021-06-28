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

package me.hydos.lint.client.entity.model;

import com.google.common.collect.ImmutableList;
import me.hydos.lint.entity.passive.TinyPotatoEntity;
import net.minecraft.client.model.ModelPart;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.entity.model.CompositeEntityModel;
import net.minecraft.client.util.math.MatrixStack;

import java.util.List;
import java.util.Map;

import static me.hydos.lint.util.ModelUtils.cuboid;

public class TinyPotatoEntityModel extends CompositeEntityModel<TinyPotatoEntity> {
	private static final ModelPart model;
	private static final int textureWidth = 32;
	private static final int textureHeight = 32;

	static {
		model = new ModelPart(List.of(
				cuboid(0, 0, -4.0F, -12.0F, -4.0F, 8.0F, 12.0F, 8.0F, 0.0F, false, textureWidth, textureHeight)
		), Map.of());
		model.setPivot(0.0F, 24.0F, 0.0F);
	}

	@Override
	public void setAngles(TinyPotatoEntity entity, float limbAngle, float limbDistance, float customAngle, float headYaw, float headPitch) {
		model.yaw = headYaw / 100;
		model.pitch = headPitch / 100;
	}

	@Override
	public Iterable<ModelPart> getParts() {
		return ImmutableList.of(model);
	}

	public void renderOnShoulder(MatrixStack matrixStack, VertexConsumer vertexConsumer, int i, int j) {
		this.getParts().forEach((modelPart) -> modelPart.render(matrixStack, vertexConsumer, i, j));
	}
}