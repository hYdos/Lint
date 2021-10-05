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

package lint.mana.alchemy;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.block.entity.BlockEntityRenderer;
import net.minecraft.client.render.block.entity.BlockEntityRendererFactory;
import net.minecraft.client.render.model.json.ModelTransformation;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3f;

import java.util.List;

public class AlchemyPedestalBlockEntityRenderer implements BlockEntityRenderer<AlchemyPedestalBlockEntity> {

	public AlchemyPedestalBlockEntityRenderer(BlockEntityRendererFactory.Context context) {
	}

	@Override
	public void render(AlchemyPedestalBlockEntity entity, float tickDelta, MatrixStack matrices, VertexConsumerProvider vertexConsumers, int light, int overlay) {
		List<ItemStack> inventory = entity.getInventory();

		float[] angles = new float[inventory.size()];

		for (int i = 0; i < angles.length; i++) {
			angles[i] = i * 360F / inventory.size();
		}

		float time = ((int) entity.getWorld().getTime()) + tickDelta;

		for (int i = 0; i < inventory.size(); i++) {
			matrices.push();
			matrices.translate(0.5F, 1.25F, 0.5F);
			matrices.multiply(Vec3f.POSITIVE_Y.getDegreesQuaternion(angles[i] + time));
			matrices.translate(1.125F, 0F, 0.25F);
			matrices.multiply(Vec3f.POSITIVE_Y.getDegreesQuaternion(90F));
			matrices.translate(0D, 0.075 * MathHelper.sin((time + i * 10) / 5), 0F);
			ItemStack stack = inventory.get(i);

			if (!stack.isEmpty()) {
				MinecraftClient.getInstance().getItemRenderer().renderItem(stack, ModelTransformation.Mode.GROUND, light, overlay, matrices, vertexConsumers, 0);
			}

			matrices.pop();
		}
	}
}
