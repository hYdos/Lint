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

package me.hydos.lint.entity.passive;

import me.hydos.lint.Lint;
import me.hydos.lint.block.LintBlocks;
import me.hydos.lint.entity.passive.bird.EasternRosellaEntity;
import me.hydos.lint.util.LintInventory;
import net.fabricmc.fabric.api.container.ContainerProviderRegistry;
import net.minecraft.block.BlockState;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.SpawnReason;
import net.minecraft.entity.ai.goal.*;
import net.minecraft.entity.attribute.DefaultAttributeContainer;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.passive.PassiveEntity;
import net.minecraft.entity.passive.TameableShoulderEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.Inventories;
import net.minecraft.item.Items;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.recipe.Ingredient;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.WorldAccess;

import java.util.Random;

@SuppressWarnings("EntityConstructor")
public class TinyPotatoEntity extends TameableShoulderEntity {

	private static final Ingredient FOOD = Ingredient.ofItems(Items.POTATO, Items.BAKED_POTATO);

	public final LintInventory inventory;

	public TinyPotatoEntity(EntityType<? extends TinyPotatoEntity> type, World world) {
		super(type, world);
		inventory = new LintInventory(31);
	}

	public static DefaultAttributeContainer.Builder createAttributes() {
		return LivingEntity.createLivingAttributes()
				.add(EntityAttributes.GENERIC_MOVEMENT_SPEED, 0.6)
				.add(EntityAttributes.GENERIC_FOLLOW_RANGE, 6)
				.add(EntityAttributes.GENERIC_ATTACK_KNOCKBACK, 1)
				.add(EntityAttributes.GENERIC_FLYING_SPEED, 0.6);
	}

	public static boolean canSpawn(EntityType<TinyPotatoEntity> entity, WorldAccess world, SpawnReason spawnReason, BlockPos pos, Random random) {
		if (spawnReason == SpawnReason.NATURAL) {
			//TODO: create a mob entity cap manager and check the passive cap before spawning them.
			if (random.nextInt(10) > 4) {
				BlockState blockState = world.getBlockState(pos.down());
				return (blockState.isOf(LintBlocks.LIVELY_GRASS) && world.getBaseLightLevel(pos, 0) > 8);
			}
		}

		return true;
	}

	@Override
	public void readCustomDataFromTag(CompoundTag tag) {
		super.readCustomDataFromTag(tag);
		Inventories.fromTag(tag, inventory.getRawList());
	}

	@Override
	public void writeCustomDataToTag(CompoundTag tag) {
		super.writeCustomDataToTag(tag);
		Inventories.toTag(tag, inventory.getRawList());
	}

	@Override
	protected void initGoals() {
		this.goalSelector.add(0, new EscapeDangerGoal(this, 1.25D));
		this.goalSelector.add(0, new SwimGoal(this));
		this.goalSelector.add(1, new FleeEntityGoal<>(this, EasternRosellaEntity.class, 8.0F, 1.0D, 1.2D));
		this.goalSelector.add(2, new LookAtEntityGoal(this, PlayerEntity.class, 8.0F));
		this.goalSelector.add(3, new SitGoal(this));
		this.goalSelector.add(3, new TemptGoal(this, 1.1D, FOOD, false));
		this.goalSelector.add(3, new FollowOwnerGoal(this, 1.0D, 5.0F, 1.0F, true));
		this.goalSelector.add(3, new WanderAroundGoal(this, 1.0D));
		this.goalSelector.add(4, new SitOnOwnerShoulderGoal(this));
		this.goalSelector.add(4, new FollowMobGoal(this, 1.0D, 3.0F, 7.0F));
		this.goalSelector.add(5, new LookAtEntityGoal(this, TinyPotatoEntity.class, 8.0F));
	}

	public ActionResult interactMob(PlayerEntity player, Hand hand) {
		if (!this.world.isClient()) {
			if (!isTamed() && FOOD.test(player.getStackInHand(hand))) {
				this.setOwner(player);
				this.setTamed(true);
				this.world.addParticle(ParticleTypes.HEART, this.getX(), this.getY(), this.getZ(), 0, 4, 0);
				player.getStackInHand(hand).decrement(1);
			} else {
				if (getOwner() == player) {
					//the person who clicked owns the tater
					ContainerProviderRegistry.INSTANCE.openContainer(Lint.id("tater_inv"), player, packetByteBuf -> packetByteBuf.writeInt(getEntityId()));
				} else {
					return ActionResult.FAIL;
				}
			}
		}
		return ActionResult.SUCCESS;
	}

	@Override
	public PassiveEntity createChild(ServerWorld world, PassiveEntity entity) {
		return (PassiveEntity) getType().create(world);
	}
}
