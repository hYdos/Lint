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
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.render.entity.LivingEntityRenderer;
import net.minecraft.client.render.entity.feature.ArmorFeatureRenderer;
import net.minecraft.client.render.entity.feature.HeadFeatureRenderer;
import net.minecraft.client.render.entity.feature.HeldItemFeatureRenderer;
import net.minecraft.client.render.entity.feature.StuckArrowsFeatureRenderer;
import net.minecraft.client.render.entity.model.BipedEntityModel;
import net.minecraft.client.render.entity.model.EntityModelLayers;
import net.minecraft.client.render.entity.model.PlayerEntityModel;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.item.CrossbowItem;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.util.Arm;
import net.minecraft.util.Hand;
import net.minecraft.util.Identifier;
import net.minecraft.util.UseAction;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.math.Vec3f;

public class NPCHumanEntityRenderer extends LivingEntityRenderer<NPCHumanEntity, PlayerEntityModel<NPCHumanEntity>> {
	public NPCHumanEntityRenderer(EntityRendererFactory.Context context) {
		super(context, new PlayerEntityModel<>(context.getPart(EntityModelLayers.PLAYER_SLIM), false), 0.5F);
		this.addFeature(new ArmorFeatureRenderer<>(this, new BipedEntityModel<>(context.getPart(EntityModelLayers.PLAYER_SLIM_INNER_ARMOR)), new BipedEntityModel<>(context.getPart(EntityModelLayers.PLAYER_SLIM_OUTER_ARMOR))));
		this.addFeature(new HeldItemFeatureRenderer<>(this));
		this.addFeature(new StuckArrowsFeatureRenderer<>(context, this));
		this.addFeature(new HeadFeatureRenderer<>(this, context.getModelLoader()));
	}

	// Code Stolen From Player Entity Renderer

	@Override
	public void render(NPCHumanEntity npcHuman, float f, float g, MatrixStack matrixStack, VertexConsumerProvider vertexConsumerProvider, int i) {
		this.setModelPose(npcHuman);
		super.render(npcHuman, f, g, matrixStack, vertexConsumerProvider, i);
	}

	@Override
	public Vec3d getPositionOffset(NPCHumanEntity npcHuman, float f) {
		return npcHuman.isInSneakingPose() ? new Vec3d(0.0D, -0.125D, 0.0D) : super.getPositionOffset(npcHuman, f);
	}

	private void setModelPose(NPCHumanEntity NPCHumanEntity) {
		PlayerEntityModel<NPCHumanEntity> playerEntityModel = this.getModel();

		playerEntityModel.setVisible(true);
		playerEntityModel.hat.visible = true;
		playerEntityModel.jacket.visible = true;
		playerEntityModel.leftPants.visible = true;
		playerEntityModel.rightPants.visible = true;
		playerEntityModel.leftSleeve.visible = true;
		playerEntityModel.rightSleeve.visible = true;
		playerEntityModel.sneaking = NPCHumanEntity.isInSneakingPose();
		BipedEntityModel.ArmPose armPose = getArmPose(NPCHumanEntity, Hand.MAIN_HAND);
		BipedEntityModel.ArmPose armPose2 = getArmPose(NPCHumanEntity, Hand.OFF_HAND);
		if (armPose.isTwoHanded()) {
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
		this.renderArm(matrices, vertexConsumers, light, player, this.model.rightArm, this.model.rightSleeve);
	}

	public void renderLeftArm(MatrixStack matrices, VertexConsumerProvider vertexConsumers, int light, NPCHumanEntity player) {
		this.renderArm(matrices, vertexConsumers, light, player, this.model.leftArm, this.model.leftSleeve);
	}

	private void renderArm(MatrixStack matrices, VertexConsumerProvider vertexConsumers, int light, NPCHumanEntity player, ModelPart arm, ModelPart sleeve) {
		PlayerEntityModel<NPCHumanEntity> playerEntityModel = this.getModel();
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

	protected void setupTransforms(NPCHumanEntity entity, MatrixStack matrixStack, float f, float g, float h) {
		float leaningPitch = entity.getLeaningPitch(h);
		float n;
		float k;
		if (entity.isFallFlying()) {
			super.setupTransforms(entity, matrixStack, f, g, h);
			n = (float) entity.getRoll() + h;
			k = MathHelper.clamp(n * n / 100.0F, 0.0F, 1.0F);
			if (!entity.isUsingRiptide()) {
				matrixStack.multiply(Vec3f.POSITIVE_X.getDegreesQuaternion(k * (-90.0F - entity.getPitch())));
			}

			Vec3d vec3d = entity.getRotationVec(h);
			Vec3d vec3d2 = entity.getVelocity();
			double d = vec3d2.method_37268();
			double e = vec3d.method_37268();
			if (d > 0.0D && e > 0.0D) {
				double l = (vec3d2.x * vec3d.x + vec3d2.z * vec3d.z) / Math.sqrt(d * e);
				double m = vec3d2.x * vec3d.z - vec3d2.z * vec3d.x;
				matrixStack.multiply(Vec3f.POSITIVE_Y.getRadialQuaternion((float) (Math.signum(m) * Math.acos(l))));
			}
		} else if (leaningPitch > 0.0F) {
			super.setupTransforms(entity, matrixStack, f, g, h);
			n = entity.isTouchingWater() ? -90.0F - entity.getPitch() : -90.0F;
			k = MathHelper.lerp(leaningPitch, 0.0F, n);
			matrixStack.multiply(Vec3f.POSITIVE_X.getDegreesQuaternion(k));
			if (entity.isInSwimmingPose()) {
				matrixStack.translate(0.0D, -1.0D, 0.30000001192092896D);
			}
		} else {
			super.setupTransforms(entity, matrixStack, f, g, h);
		}

	}
}
