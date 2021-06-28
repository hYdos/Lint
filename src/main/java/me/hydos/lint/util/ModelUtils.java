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
