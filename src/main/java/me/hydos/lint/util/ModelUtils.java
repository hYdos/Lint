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

package me.hydos.lint.util;

import net.minecraft.client.model.ModelPart;

import java.util.List;
import java.util.Map;

/**
 * @author TehcJS
 * Utilities for Model creation
 */
// TODO: allow the user to omit the "textureWidth" and "textureHeight" arguments in ModelUtils.cuboid methods
public final class ModelUtils {
	private ModelUtils() {
	}

	public static ModelPart.Cuboid cuboid(int u, int v, float x, float y, float z, float sizeX, float sizeY, float sizeZ, float extra, boolean mirror, int textureWidth, int textureHeight) {
		return new ModelPart.Cuboid(u, v, x, y, z, sizeX, sizeY, sizeZ, extra, extra, extra, mirror, textureWidth, textureHeight);
	}

	public static ModelPart.Cuboid cuboid(float x, float y, float z, int sizeX, int sizeY, int sizeZ, float extra, int u, int v, int textureWidth, int textureHeight) {
		return ModelUtils.cuboid(u, v, x, y, z, sizeX, sizeY, sizeZ, extra, false, textureWidth, textureHeight);
	}

	public static ModelPart part(float pivotX, float pivotY, float pivotZ, List<ModelPart.Cuboid> cuboids, Map<String, ModelPart> children) {
		ModelPart model = new ModelPart(cuboids, children);
		model.setPivot(pivotX, pivotY, pivotZ);
		return model;
	}
}
