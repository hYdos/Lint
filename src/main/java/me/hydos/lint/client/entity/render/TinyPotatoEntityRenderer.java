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

import me.hydos.lint.client.entity.model.TinyPotatoEntityModel;
import me.hydos.lint.entity.passive.TinyPotatoEntity;
import me.hydos.lint.entity.passive.TinyPotatoNpcEntity;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.EntityRenderDispatcher;
import net.minecraft.client.render.entity.MobEntityRenderer;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.Identifier;

public class TinyPotatoEntityRenderer extends MobEntityRenderer<TinyPotatoEntity, TinyPotatoEntityModel> {

	public static final Identifier TINY_POTATO = new Identifier("lint:textures/entity/tiny_potato.png");
	public static final Identifier IRRITATER = new Identifier("lint:textures/entity/irritated.png");

	public TinyPotatoEntityRenderer(EntityRenderDispatcher dispatcher) {
		super(dispatcher, new TinyPotatoEntityModel(), 0.4f);
	}

	@Override
	public void render(TinyPotatoEntity mobEntity, float f, float g, MatrixStack matrixStack, VertexConsumerProvider vertexConsumerProvider, int light) {
		matrixStack.push();
		matrixStack.scale(0.6f, 0.6f, 0.6f);
		super.render(mobEntity, f, g, matrixStack, vertexConsumerProvider, light);
		matrixStack.pop();
	}

	@Override
	public Identifier getTexture(TinyPotatoEntity entity) {
		return entity.isTamed() || entity instanceof TinyPotatoNpcEntity ? TINY_POTATO : IRRITATER;
	}
}
