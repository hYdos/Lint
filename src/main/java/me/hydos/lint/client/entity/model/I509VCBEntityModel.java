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

import me.hydos.lint.entity.aggressive.I509VCBEntity;
import net.minecraft.client.model.ModelPart;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.entity.model.EntityModel;
import net.minecraft.client.util.math.MatrixStack;

import java.util.List;
import java.util.Map;

import static me.hydos.lint.util.ModelUtils.cuboid;

public class I509VCBEntityModel extends EntityModel<I509VCBEntity> {
	private static final ModelPart cone;
	private static final int textureWidth = 64;
	private static final int textureHeight = 64;

	// TODO: use ModelUtils.part to set pivot
	static {
		cone = new ModelPart(List.of(
				cuboid(0, 0, -8.0F, -1.0F, -8.0F, 16.0F, 1.0F, 16.0F, 0.0F, false, textureWidth, textureHeight),
				cuboid(0, 17, -7.0F, -5.0F, -7.0F, 14.0F, 4.0F, 14.0F, 0.0F, false, textureWidth, textureHeight),
				cuboid(0, 36, -6.0F, -9.0F, -6.0F, 12.0F, 4.0F, 12.0F, 0.0F, false, textureWidth, textureHeight),
				cuboid(4, 6, -5.0F, -13.0F, -5.0F, 10.0F, 4.0F, 10.0F, 0.0F, false, textureWidth, textureHeight),
				cuboid(32, 50, -4.0F, -18.0F, -4.0F, 8.0F, 6.0F, 8.0F, 0.0F, false, textureWidth, textureHeight),
				cuboid(0, 0, -3.0F, -15.0F, -3.0F, 6.0F, 3.0F, 6.0F, 0.0F, false, textureWidth, textureHeight),
				cuboid(40, 0, -3.0F, -22.0F, -3.0F, 6.0F, 6.0F, 6.0F, 0.0F, false, textureWidth, textureHeight),
				cuboid(24, 8, -2.0F, -27.0F, -2.0F, 4.0F, 7.0F, 4.0F, 0.0F, false, textureWidth, textureHeight),
				cuboid(56, 11, -1.0F, -31.0F, -1.0F, 2.0F, 7.0F, 2.0F, 0.0F, false, textureWidth, textureHeight)
		), Map.of());
		cone.setPivot(0.0F, 24.0F, 0.0F);
	}

	@Override
	public void setAngles(I509VCBEntity entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
		cone.roll = (float) (Math.PI);
	}

	@Override
	public void render(MatrixStack matrices, VertexConsumer vertices, int light, int overlay, float red, float green, float blue, float alpha) {
		matrices.translate(0, -1.5, 0);
		cone.render(matrices, vertices, light, overlay, red, green, blue, alpha);
	}
}