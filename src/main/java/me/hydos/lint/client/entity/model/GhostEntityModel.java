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

import me.hydos.lint.Lint;
import me.hydos.lint.entity.aggressive.GhostEntity;
import net.minecraft.client.model.ModelPart;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.entity.model.EntityModel;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.Identifier;
import software.bernie.geckolib3.model.AnimatedGeoModel;

public class GhostEntityModel extends AnimatedGeoModel<GhostEntity> {
	@Override
	public Identifier getModelLocation(GhostEntity object) {
		return Lint.id("geo/ghost.geo.json");
	}

	@Override
	public Identifier getTextureLocation(GhostEntity object) {
		return Lint.id("textures/entity/ghost.png");
	}

	@Override
	public Identifier getAnimationFileLocation(GhostEntity animatable) {
		return Lint.id("animations/ghost.animation.json");
	}
}
