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

import me.hydos.lint.entity.passive.human.NPCHumanEntity;
import net.minecraft.client.model.ModelPart;
import net.minecraft.client.render.OverlayTexture;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.EntityRenderDispatcher;
import net.minecraft.client.render.entity.LivingEntityRenderer;
import net.minecraft.client.render.entity.feature.ArmorFeatureRenderer;
import net.minecraft.client.render.entity.feature.HeadFeatureRenderer;
import net.minecraft.client.render.entity.feature.HeldItemFeatureRenderer;
import net.minecraft.client.render.entity.feature.StuckArrowsFeatureRenderer;
import net.minecraft.client.render.entity.model.BipedEntityModel;
import net.minecraft.client.render.entity.model.PlayerEntityModel;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.client.util.math.Vector3f;
import net.minecraft.entity.Entity;
import net.minecraft.item.CrossbowItem;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.util.Arm;
import net.minecraft.util.Hand;
import net.minecraft.util.Identifier;
import net.minecraft.util.UseAction;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;

public class NPCHumanEntityRenderer extends LivingEntityRenderer<NPCHumanEntity, PlayerEntityModel<NPCHumanEntity>>  {
	public NPCHumanEntityRenderer(EntityRenderDispatcher entityRenderDispatcher) {
		super(entityRenderDispatcher, new PlayerEntityModel<>(0.0F, false), 0.5F);
		this.addFeature(new ArmorFeatureRenderer<>(this, new BipedEntityModel<>(0.5F), new BipedEntityModel<>(1.0F)));
		this.addFeature(new HeldItemFeatureRenderer<>(this));
		this.addFeature(new StuckArrowsFeatureRenderer<>(this));
		this.addFeature(new HeadFeatureRenderer<>(this));
	}

	// Code Stolen From Player Entity Renderer
	private void setModelPose(NPCHumanEntity NPCHumanEntity) {
		PlayerEntityModel<NPCHumanEntity> playerEntityModel = (PlayerEntityModel)this.getModel();

		playerEntityModel.setVisible(true);
		playerEntityModel.helmet.visible = true;
		playerEntityModel.jacket.visible = true;
		playerEntityModel.leftPantLeg.visible = true;
		playerEntityModel.rightPantLeg.visible = true;
		playerEntityModel.leftSleeve.visible = true;
		playerEntityModel.rightSleeve.visible = true;
		playerEntityModel.sneaking = NPCHumanEntity.isInSneakingPose();
		BipedEntityModel.ArmPose armPose = getArmPose(NPCHumanEntity, Hand.MAIN_HAND);
		BipedEntityModel.ArmPose armPose2 = getArmPose(NPCHumanEntity, Hand.OFF_HAND);
		if (armPose.method_30156()) {
			armPose2 = NPCHumanEntity.getOffHandStack().isEmpty() ? BipedEntityModel.ArmPose.EMPTY : BipedEntityModel.ArmPose.ITEM;
		}

		if (NPCHumanEntity.getMainArm() == Arm.RIGHT) {
			playerEntityModel.rightArmPose = armPose;
			playerEntityModel.leftArmPose = armPose2;
		} else {
			playerEntityModel.rightArmPose = armPose2;
			playerEntityModel.leftArmPose = armPose;
		}
	}

	private static BipedEntityModel.ArmPose getArmPose(NPCHumanEntity NPCHumanEntity, Hand hand) {
		ItemStack itemStack = NPCHumanEntity.getStackInHand(hand);
		if (itemStack.isEmpty()) {
			return BipedEntityModel.ArmPose.EMPTY;
		} else {
			if (NPCHumanEntity.getActiveHand() == hand && NPCHumanEntity.getItemUseTimeLeft() > 0) {
				UseAction useAction = itemStack.getUseAction();
				if (useAction == UseAction.BLOCK) {
					return BipedEntityModel.ArmPose.BLOCK;
				}

				if (useAction == UseAction.BOW) {
					return BipedEntityModel.ArmPose.BOW_AND_ARROW;
				}

				if (useAction == UseAction.SPEAR) {
					return BipedEntityModel.ArmPose.THROW_SPEAR;
				}

				if (useAction == UseAction.CROSSBOW && hand == NPCHumanEntity.getActiveHand()) {
					return BipedEntityModel.ArmPose.CROSSBOW_CHARGE;
				}
			} else if (!NPCHumanEntity.handSwinging && itemStack.getItem() == Items.CROSSBOW && CrossbowItem.isCharged(itemStack)) {
				return BipedEntityModel.ArmPose.CROSSBOW_HOLD;
			}

			return BipedEntityModel.ArmPose.ITEM;
		}
	}

	public Identifier getTexture(NPCHumanEntity NPCHumanEntity) {
		return NPCHumanEntity.getSkinTexture();
	}

	protected void scale(NPCHumanEntity NPCHumanEntity, MatrixStack matrixStack, float f) {
		matrixStack.scale(0.9375F, 0.9375F, 0.9375F);
	}

	public void renderRightArm(MatrixStack matrices, VertexConsumerProvider vertexConsumers, int light, NPCHumanEntity player) {
		this.renderArm(matrices, vertexConsumers, light, player, this.model.rightArm, ((PlayerEntityModel)this.model).rightSleeve);
	}

	public void renderLeftArm(MatrixStack matrices, VertexConsumerProvider vertexConsumers, int light, NPCHumanEntity player) {
		this.renderArm(matrices, vertexConsumers, light, player, ((PlayerEntityModel)this.model).leftArm, ((PlayerEntityModel)this.model).leftSleeve);
	}

	private void renderArm(MatrixStack matrices, VertexConsumerProvider vertexConsumers, int light, NPCHumanEntity player, ModelPart arm, ModelPart sleeve) {
		PlayerEntityModel<NPCHumanEntity> playerEntityModel = (PlayerEntityModel)this.getModel();
		this.setModelPose(player);
		playerEntityModel.handSwingProgress = 0.0F;
		playerEntityModel.sneaking = false;
		playerEntityModel.leaningPitch = 0.0F;
		playerEntityModel.setAngles(player, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F);
		arm.pitch = 0.0F;
		arm.render(matrices, vertexConsumers.getBuffer(RenderLayer.getEntitySolid(player.getSkinTexture())), light, OverlayTexture.DEFAULT_UV);
		sleeve.pitch = 0.0F;
		sleeve.render(matrices, vertexConsumers.getBuffer(RenderLayer.getEntityTranslucent(player.getSkinTexture())), light, OverlayTexture.DEFAULT_UV);
	}

	protected void setupTransforms(NPCHumanEntity NPCHumanEntity, MatrixStack matrixStack, float f, float g, float h) {
		float leaningPitch = NPCHumanEntity.getLeaningPitch(h);
		float n;
		float k;
		if (NPCHumanEntity.isFallFlying()) {
			super.setupTransforms(NPCHumanEntity, matrixStack, f, g, h);
			n = (float)NPCHumanEntity.getRoll() + h;
			k = MathHelper.clamp(n * n / 100.0F, 0.0F, 1.0F);
			if (!NPCHumanEntity.isUsingRiptide()) {
				matrixStack.multiply(Vector3f.POSITIVE_X.getDegreesQuaternion(k * (-90.0F - NPCHumanEntity.pitch)));
			}

			Vec3d vec3d = NPCHumanEntity.getRotationVec(h);
			Vec3d vec3d2 = NPCHumanEntity.getVelocity();
			double d = Entity.squaredHorizontalLength(vec3d2);
			double e = Entity.squaredHorizontalLength(vec3d);
			if (d > 0.0D && e > 0.0D) {
				double l = (vec3d2.x * vec3d.x + vec3d2.z * vec3d.z) / Math.sqrt(d * e);
				double m = vec3d2.x * vec3d.z - vec3d2.z * vec3d.x;
				matrixStack.multiply(Vector3f.POSITIVE_Y.getRadialQuaternion((float)(Math.signum(m) * Math.acos(l))));
			}
		} else if (leaningPitch > 0.0F) {
			super.setupTransforms(NPCHumanEntity, matrixStack, f, g, h);
			n = NPCHumanEntity.isTouchingWater() ? -90.0F - NPCHumanEntity.pitch : -90.0F;
			k = MathHelper.lerp(leaningPitch, 0.0F, n);
			matrixStack.multiply(Vector3f.POSITIVE_X.getDegreesQuaternion(k));
			if (NPCHumanEntity.isInSwimmingPose()) {
				matrixStack.translate(0.0D, -1.0D, 0.30000001192092896D);
			}
		} else {
			super.setupTransforms(NPCHumanEntity, matrixStack, f, g, h);
		}

	}
}
