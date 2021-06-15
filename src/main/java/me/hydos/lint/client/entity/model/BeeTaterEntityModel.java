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

import me.hydos.lint.entity.passive.BeeTaterEntity;
import me.hydos.lint.util.ModelUtils;
import net.minecraft.client.model.ModelPart;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.entity.model.EntityModel;
import net.minecraft.client.util.math.MatrixStack;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import static me.hydos.lint.util.ModelUtils.cuboid;

public class BeeTaterEntityModel extends EntityModel<BeeTaterEntity> {

	private static final ModelPart body;
	private static final ModelPart left_wing;
	private static final ModelPart right_wing;
	private static final ModelPart leg1;
	private static final ModelPart leg2;
	private static final ModelPart leg3;
	private static final int textureWidth = 64;
	private static final int textureHeight = 64;
	
	// TODO: use ModelUtils.part to set pivot
	static {
		body = new ModelPart(List.of(
				cuboid(0, 0, -4.0F, -2.0F, -8.0F, 7.0F, 7.0F, 10.0F, 0.0F, false, textureWidth, textureHeight),
				cuboid(3, 3, 2.0F, -2.0F, -11.0F, 0.0F, 2.0F, 3.0F, 0.0F, false, textureWidth, textureHeight),
				cuboid(2, 0, -3.0F, -2.0F, -11.0F, 0.0F, 2.0F, 3.0F, 0.0F, false, textureWidth, textureHeight),
				cuboid(3, 1, -0.5F, 1.0F, 2.0F, 0.0F, 1.0F, 2.0F, 0.0F, false, textureWidth, textureHeight)
		), Map.of());
		body.setPivot(0.0F, 0.0F, 0.0F);
		
		left_wing = new ModelPart(List.of(
				cuboid(9, 24, 3.0F, 0.0F, 2.0F, 7.0F, 0.0F, 6.0F, 0.0F, false, textureWidth, textureHeight)
		), Map.of());
		left_wing.setPivot(-2.0F, -2.0F, -8.0F);
		
		right_wing = new ModelPart(List.of(
				cuboid(9, 24, -13.0F, 0.0F, 2.0F, 7.0F, 0.0F, 6.0F, 0.0F, true, textureWidth, textureHeight)
		), Map.of());
		right_wing.setPivot(3.0F, -2.0F, -8.0F);
		
		leg1 = new ModelPart(List.of(
				cuboid(28, 1, -2.0F, -19.0F, -5.0F, 3.0F, 2.0F, 0.0F, 0.0F, false, textureWidth, textureHeight)
		), Map.of());
		leg1.setPivot(0.0F, 24.0F, 0.0F);
		
		leg2 = new ModelPart(List.of(
				cuboid(27, 3, -3.0F, -19.0F, -3.0F, 5.0F, 2.0F, 0.0F, 0.0F, false, textureWidth, textureHeight)
		), Map.of());
		leg2.setPivot(0.0F, 24.0F, 0.0F);
		
		leg3 = new ModelPart(List.of(
				cuboid(27, 5, -3.0F, -19.0F, -1.0F, 5.0F, 2.0F, 0.0F, 0.0F, false, textureWidth, textureHeight)
		), Map.of());
		leg3.setPivot(0.0F, 24.0F, 0.0F);
	}

	@Override
	public void setAngles(BeeTaterEntity entity, float limbAngle, float limbDistance, float customAngle, float headYaw, float headPitch) {
	}


	@Override
	public void render(MatrixStack matrixStack, VertexConsumer buffer, int packedLight, int packedOverlay, float red, float green, float blue, float alpha) {
		body.render(matrixStack, buffer, packedLight, packedOverlay);
		left_wing.render(matrixStack, buffer, packedLight, packedOverlay);
		right_wing.render(matrixStack, buffer, packedLight, packedOverlay);
		leg1.render(matrixStack, buffer, packedLight, packedOverlay);
		leg2.render(matrixStack, buffer, packedLight, packedOverlay);
		leg3.render(matrixStack, buffer, packedLight, packedOverlay);
	}


	public void setRotationAngle(ModelPart modelRenderer, float x, float y, float z) {
		modelRenderer.pitch = x;
		modelRenderer.yaw = y;
		modelRenderer.roll = z;
	}
}