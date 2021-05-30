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
import me.hydos.lint.client.entity.model.KingTaterEntityModel;
import me.hydos.lint.entity.aggressive.KingTaterEntity;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.render.entity.MobEntityRenderer;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.Identifier;

public class KingTaterRenderer extends MobEntityRenderer<KingTaterEntity, KingTaterEntityModel> {

	private static final Identifier SKIN = Lint.id("textures/entity/tater_king.png");

	public KingTaterRenderer(EntityRendererFactory.Context context) {
		super(context, new KingTaterEntityModel(), 1);
	}

	@Override
	public Identifier getTexture(KingTaterEntity entity) {
		return SKIN;
	}

	@Override
	protected void scale(KingTaterEntity entity, MatrixStack matrices, float tickDelta) {
		float scale = Math.max(KingTaterEntity.getScaledHealth(entity.getHealth(), entity.getMaxHealth()), 0.125F);
		matrices.scale(scale, scale, scale);
	}
}
