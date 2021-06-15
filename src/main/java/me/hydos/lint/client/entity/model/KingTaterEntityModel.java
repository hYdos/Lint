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
import me.hydos.lint.entity.aggressive.KingTaterEntity;
import me.hydos.lint.util.ModelUtils;
import net.minecraft.client.model.ModelPart;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.entity.model.CompositeEntityModel;
import net.minecraft.client.util.math.MatrixStack;

import java.util.List;
import java.util.Map;

import static me.hydos.lint.util.ModelUtils.cuboid;
import static me.hydos.lint.util.ModelUtils.part;

public class KingTaterEntityModel extends CompositeEntityModel<KingTaterEntity> {
	private static final ModelPart root;
	private static final int textureWidth = 96;
	private static final int textureHeight = 82;
	
	static {
		root = new ModelPart(List.of(), Map.of(
				"model", part(0.0F, 24.0F, 0.0F, List.of(
						cuboid(-12.0F, -32.0F, -12.0F, 24, 32, 24, 0.0F, 0, 0, textureWidth, textureHeight)
				), Map.of(
						"crown", part(0.0F, 0.0F, 0.0F, List.of(
								cuboid(-12.0F, -34.0F, -12.0F, 24, 2, 24, 0.0F, 0, 56, textureWidth, textureHeight)
						), Map.of(
								"things", part(0.0F, 0.0F, 0.0F, List.of(), Map.of(
										"front", part(0.0F, 0.0F, 0.0F, List.of(
												cuboid(10.5F, -35.0F, -12.0F, 1, 1, 0, 0.0F, 8, 77, textureWidth, textureHeight),
												cuboid(8.5F, -35.0F, -12.0F, 1, 1, 0, 0.0F, 8, 77, textureWidth, textureHeight),
												cuboid(6.5F, -35.0F, -12.0F, 1, 1, 0, 0.0F, 8, 77, textureWidth, textureHeight),
												cuboid(4.5F, -35.0F, -12.0F, 1, 1, 0, 0.0F, 8, 77, textureWidth, textureHeight),
												cuboid(2.5F, -35.0F, -12.0F, 1, 1, 0, 0.0F, 8, 77, textureWidth, textureHeight),
												cuboid(0.5F, -35.0F, -12.0F, 1, 1, 0, 0.0F, 8, 77, textureWidth, textureHeight),
												cuboid(-1.5F, -35.0F, -12.0F, 1, 1, 0, 0.0F, 8, 77, textureWidth, textureHeight),
												cuboid(-3.5F, -35.0F, -12.0F, 1, 1, 0, 0.0F, 8, 77, textureWidth, textureHeight),
												cuboid(-5.5F, -35.0F, -12.0F, 1, 1, 0, 0.0F, 8, 77, textureWidth, textureHeight),
												cuboid(-7.5F, -35.0F, -12.0F, 1, 1, 0, 0.0F, 8, 77, textureWidth, textureHeight),
												cuboid(-9.5F, -35.0F, -12.0F, 1, 1, 0, 0.0F, 8, 77, textureWidth, textureHeight),
												cuboid(-11.5F, -35.0F, -12.0F, 1, 1, 0, 0.0F, 8, 77, textureWidth, textureHeight)
										), Map.of()),
										"back", part(0.0F, 0.0F, 1.0F, List.of(
												cuboid(10.5F, -35.0F, 11.0F, 1, 1, 0, 0.0F, 8, 77, textureWidth, textureHeight),
												cuboid(8.5F, -35.0F, 11.0F, 1, 1, 0, 0.0F, 8, 77, textureWidth, textureHeight),
												cuboid(6.5F, -35.0F, 11.0F, 1, 1, 0, 0.0F, 8, 77, textureWidth, textureHeight),
												cuboid(4.5F, -35.0F, 11.0F, 1, 1, 0, 0.0F, 8, 77, textureWidth, textureHeight),
												cuboid(2.5F, -35.0F, 11.0F, 1, 1, 0, 0.0F, 8, 77, textureWidth, textureHeight),
												cuboid(0.5F, -35.0F, 11.0F, 1, 1, 0, 0.0F, 8, 77, textureWidth, textureHeight),
												cuboid(-1.5F, -35.0F, 11.0F, 1, 1, 0, 0.0F, 8, 77, textureWidth, textureHeight),
												cuboid(-3.5F, -35.0F, 11.0F, 1, 1, 0, 0.0F, 8, 77, textureWidth, textureHeight),
												cuboid(-5.5F, -35.0F, 11.0F, 1, 1, 0, 0.0F, 8, 77, textureWidth, textureHeight),
												cuboid(-7.5F, -35.0F, 11.0F, 1, 1, 0, 0.0F, 8, 77, textureWidth, textureHeight),
												cuboid(-9.5F, -35.0F, 11.0F, 1, 1, 0, 0.0F, 8, 77, textureWidth, textureHeight),
												cuboid(-11.5F, -35.0F, 11.0F, 1, 1, 0, 0.0F, 8, 77, textureWidth, textureHeight)
										), Map.of()),
										"right", part(0.0F, 0.0F, 0.0F, List.of(
												cuboid(12.0F, -35.0F, -11.5F, 0, 1, 1, 0.0F, 31, 68, textureWidth, textureHeight),
												cuboid(12.0F, -35.0F, -9.5F, 0, 1, 1, 0.0F, 31, 68, textureWidth, textureHeight),
												cuboid(12.0F, -35.0F, -7.5F, 0, 1, 1, 0.0F, 31, 68, textureWidth, textureHeight),
												cuboid(12.0F, -35.0F, -5.5F, 0, 1, 1, 0.0F, 31, 68, textureWidth, textureHeight),
												cuboid(12.0F, -35.0F, -3.5F, 0, 1, 1, 0.0F, 31, 68, textureWidth, textureHeight),
												cuboid(12.0F, -35.0F, -1.5F, 0, 1, 1, 0.0F, 31, 68, textureWidth, textureHeight),
												cuboid(12.0F, -35.0F, 0.5F, 0, 1, 1, 0.0F, 31, 68, textureWidth, textureHeight),
												cuboid(12.0F, -35.0F, 2.5F, 0, 1, 1, 0.0F, 31, 68, textureWidth, textureHeight),
												cuboid(12.0F, -35.0F, 4.5F, 0, 1, 1, 0.0F, 31, 68, textureWidth, textureHeight),
												cuboid(12.0F, -35.0F, 6.5F, 0, 1, 1, 0.0F, 31, 68, textureWidth, textureHeight),
												cuboid(12.0F, -35.0F, 8.5F, 0, 1, 1, 0.0F, 31, 68, textureWidth, textureHeight),
												cuboid(12.0F, -35.0F, 10.5F, 0, 1, 1, 0.0F, 31, 68, textureWidth, textureHeight)
										), Map.of()),
										"left", part(0.0F, 0.0F, 0.0F, List.of(
												cuboid(-12.0F, -35.0F, -11.5F, 0, 1, 1, 0.0F, 31, 68, textureWidth, textureHeight),
												cuboid(-12.0F, -35.0F, -9.5F, 0, 1, 1, 0.0F, 31, 68, textureWidth, textureHeight),
												cuboid(-12.0F, -35.0F, -7.5F, 0, 1, 1, 0.0F, 31, 68, textureWidth, textureHeight),
												cuboid(-12.0F, -35.0F, -5.5F, 0, 1, 1, 0.0F, 31, 68, textureWidth, textureHeight),
												cuboid(-12.0F, -35.0F, -3.5F, 0, 1, 1, 0.0F, 31, 68, textureWidth, textureHeight),
												cuboid(-12.0F, -35.0F, -1.5F, 0, 1, 1, 0.0F, 31, 68, textureWidth, textureHeight),
												cuboid(-12.0F, -35.0F, 0.5F, 0, 1, 1, 0.0F, 31, 68, textureWidth, textureHeight),
												cuboid(-12.0F, -35.0F, 2.5F, 0, 1, 1, 0.0F, 31, 68, textureWidth, textureHeight),
												cuboid(-12.0F, -35.0F, 4.5F, 0, 1, 1, 0.0F, 31, 68, textureWidth, textureHeight),
												cuboid(-12.0F, -35.0F, 6.5F, 0, 1, 1, 0.0F, 31, 68, textureWidth, textureHeight),
												cuboid(-12.0F, -35.0F, 8.5F, 0, 1, 1, 0.0F, 31, 68, textureWidth, textureHeight),
												cuboid(-12.0F, -35.0F, 8.5F, 0, 1, 1, 0.0F, 31, 68, textureWidth, textureHeight),
												cuboid(-12.0F, -35.0F, 10.5F, 0, 1, 1, 0.0F, 31, 68, textureWidth, textureHeight)
										), Map.of())
								))
						))
				))
		));
	}

	@Override
	public void render(MatrixStack matrices, VertexConsumer vertexConsumer, int light, int overlay, float red, float green, float blue, float alpha) {
		root.render(matrices, vertexConsumer, light, overlay, red, green, blue, alpha);
	}

	@Override
	public Iterable<ModelPart> getParts() {
		return ImmutableList.of(root);
	}

	@Override
	public void setAngles(KingTaterEntity entity, float limbAngle, float limbDistance, float customAngle, float headYaw, float headPitch) {
	}
}