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

package me.hydos.lint.entity.passive.bird;

import java.util.Comparator;
import java.util.EnumSet;
import java.util.List;

import org.jetbrains.annotations.Nullable;

import me.hydos.lint.entity.Birds;
import me.hydos.lint.entity.goal.FlyOutOfWaterGoal;
import me.hydos.lint.entity.passive.bird.SoaringMoveControl.Soaring;
import net.minecraft.block.Blocks;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.TargetPredicate;
import net.minecraft.entity.ai.goal.Goal;
import net.minecraft.entity.ai.pathing.BirdNavigation;
import net.minecraft.entity.ai.pathing.EntityNavigation;
import net.minecraft.entity.passive.CatEntity;
import net.minecraft.entity.passive.PassiveEntity;
import net.minecraft.entity.passive.SchoolingFishEntity;
import net.minecraft.predicate.entity.EntityPredicates;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.Heightmap;
import net.minecraft.world.World;
import software.bernie.geckolib3.core.IAnimatable;
import software.bernie.geckolib3.core.PlayState;
import software.bernie.geckolib3.core.builder.AnimationBuilder;
import software.bernie.geckolib3.core.event.predicate.AnimationEvent;

// TODO alternate between FISHING (phantom style) and NESTING (normal bird style) movements
@SuppressWarnings("EntityConstructor")
public class RedTailedTropicBirdEntity extends AbstractBirdEntity implements Soaring {
	private static final AnimationBuilder FLY_ANIMATION = new AnimationBuilder().addAnimation("animation.red_tailed_tropicbird.fly", true);
	private static final AnimationBuilder IDLE_ANIMATION = new AnimationBuilder().addAnimation("animation.red_tailed_tropicbird.idle", true);
	private Vec3d targetPosition = Vec3d.ZERO;
	private BlockPos circlingCentre = BlockPos.ORIGIN;
	private PhantomStyleHuntMovement movementType = PhantomStyleHuntMovement.HUNTING;

	public RedTailedTropicBirdEntity(EntityType<RedTailedTropicBirdEntity> entityType, World world) {
		super(entityType, world, Birds.RED_TAILED_TROPICBIRD_DATA);
		this.moveControl = new SoaringMoveControl(this);
	}

	@Override
	public boolean handleFallDamage(float fallDistance, float damageMultiplier) {
		return false;
	}

	@Override
	public Vec3d getTargetPosition() {
		return this.targetPosition;
	}

	protected void initGoals() {
		this.goalSelector.add(1, new RedTailedTropicBirdEntity.StartAttackGoal());
		this.goalSelector.add(2, new RedTailedTropicBirdEntity.SwoopMovementGoal()); // TODO make DIVE movement goal
		this.goalSelector.add(3, new FlyOutOfWaterGoal(this));
		this.goalSelector.add(4, new RedTailedTropicBirdEntity.CircleMovementGoal());
		this.targetSelector.add(1, new RedTailedTropicBirdEntity.FindTargetGoal());
	}

	public EntityNavigation createNavigation(World world) {
		BirdNavigation birdNavigation = new BirdNavigation(this, world);
		birdNavigation.setCanPathThroughDoors(false);
		birdNavigation.setCanSwim(true);
		birdNavigation.setCanEnterOpenDoors(true);
		return birdNavigation;
	}

	@Override
	public <E extends IAnimatable> PlayState predicate(AnimationEvent<E> event) {
		if (world.getBlockState(getBlockPos().down()) == Blocks.AIR.getDefaultState()) {
			event.getController().setAnimation(FLY_ANIMATION);
		} else {
			event.getController().setAnimation(IDLE_ANIMATION);
		}
		return PlayState.CONTINUE;
	}

	@Nullable
	@Override
	public PassiveEntity createChild(ServerWorld world, PassiveEntity entity) {
		return new RedTailedTropicBirdEntity(Birds.RED_TAILED_TROPICBIRD, world);
	}

	// REUSED AND TWEAKED PHANTOM CODE
	// 1) (bc the classes are package-private)
	// 2) I need to tweak it a bunch

	class FindTargetGoal extends Goal {
		private int delay;

		private FindTargetGoal() {
			this.delay = 20;
		}

		public boolean canStart() {
			if (this.delay > 0) {
				--this.delay;
				return false;
			} else {
				this.delay = 60;
				List<SchoolingFishEntity> list = RedTailedTropicBirdEntity.this.world.getEntitiesByClass(SchoolingFishEntity.class, RedTailedTropicBirdEntity.this.getBoundingBox().expand(18.0D, 64.0D, 18.0D), se -> true);
				if (!list.isEmpty()) {
					list.sort(Comparator.comparing(Entity::getY).reversed());
					for (SchoolingFishEntity fish : list) {
						if (RedTailedTropicBirdEntity.this.isTarget(fish, TargetPredicate.DEFAULT)) {
							RedTailedTropicBirdEntity.this.setTarget(fish);
							return true;
						}
					}
				}

				return false;
			}
		}

		public boolean shouldContinue() {
			LivingEntity livingEntity = RedTailedTropicBirdEntity.this.getTarget();
			return livingEntity != null && RedTailedTropicBirdEntity.this.isTarget(livingEntity, TargetPredicate.DEFAULT);
		}
	}

	class StartAttackGoal extends Goal {
		private int cooldown;

		private StartAttackGoal() {
		}

		public boolean canStart() {
			LivingEntity livingEntity = RedTailedTropicBirdEntity.this.getTarget();
			return livingEntity != null && RedTailedTropicBirdEntity.this.isTarget(RedTailedTropicBirdEntity.this.getTarget(), TargetPredicate.DEFAULT);
		}

		public void start() {
			this.cooldown = 10;
			RedTailedTropicBirdEntity.this.movementType = RedTailedTropicBirdEntity.PhantomStyleHuntMovement.HUNTING;
			this.startSwoop();
		}

		public void stop() {
			RedTailedTropicBirdEntity.this.circlingCentre = RedTailedTropicBirdEntity.this.world.getTopPosition(Heightmap.Type.WORLD_SURFACE, RedTailedTropicBirdEntity.this.circlingCentre).up(10 + RedTailedTropicBirdEntity.this.random.nextInt(10));
		}

		public void tick() {
			if (RedTailedTropicBirdEntity.this.movementType == RedTailedTropicBirdEntity.PhantomStyleHuntMovement.HUNTING) {
				--this.cooldown;
				if (this.cooldown <= 0) {
					RedTailedTropicBirdEntity.this.movementType = RedTailedTropicBirdEntity.PhantomStyleHuntMovement.ATTACKING;
					this.startSwoop();
					this.cooldown = (8 + RedTailedTropicBirdEntity.this.random.nextInt(4)) * 20;
 				}
			}

		}

		private void startSwoop() {
			RedTailedTropicBirdEntity.this.circlingCentre = RedTailedTropicBirdEntity.this.getTarget().getBlockPos().up(10 + RedTailedTropicBirdEntity.this.random.nextInt(10));
			if (RedTailedTropicBirdEntity.this.circlingCentre.getY() < RedTailedTropicBirdEntity.this.world.getSeaLevel()) {
				RedTailedTropicBirdEntity.this.circlingCentre = new BlockPos(RedTailedTropicBirdEntity.this.circlingCentre.getX(), RedTailedTropicBirdEntity.this.world.getSeaLevel() + 1, RedTailedTropicBirdEntity.this.circlingCentre.getZ());
			}

		}
	}

	class SwoopMovementGoal extends RedTailedTropicBirdEntity.MovementGoal {
		private SwoopMovementGoal() {
			super();
		}

		public boolean canStart() {
			return RedTailedTropicBirdEntity.this.getTarget() != null && RedTailedTropicBirdEntity.this.movementType == RedTailedTropicBirdEntity.PhantomStyleHuntMovement.ATTACKING;
		}

		public boolean shouldContinue() {
			LivingEntity livingEntity = RedTailedTropicBirdEntity.this.getTarget();
			if (livingEntity == null) {
				return false;
			} else if (!livingEntity.isAlive()) {
				return false;
			} else if (!this.canStart()) {
				return false;
			} else {
				if (RedTailedTropicBirdEntity.this.age % 20 == 0) {
					// I'll keep them scared of cats, but for a different reason
					List<CatEntity> list = RedTailedTropicBirdEntity.this.world.getEntitiesByClass(CatEntity.class, RedTailedTropicBirdEntity.this.getBoundingBox().expand(16.0D), EntityPredicates.VALID_ENTITY);

					return list.isEmpty();
				}

				return true;
			}
		}

		public void start() {
		}

		public void stop() {
			RedTailedTropicBirdEntity.this.setTarget(null);
			RedTailedTropicBirdEntity.this.movementType = RedTailedTropicBirdEntity.PhantomStyleHuntMovement.HUNTING;
		}

		public void tick() {
			LivingEntity livingEntity = RedTailedTropicBirdEntity.this.getTarget();
			RedTailedTropicBirdEntity.this.targetPosition = new Vec3d(livingEntity.getX(), livingEntity.getBodyY(0.5D), livingEntity.getZ());
			if (RedTailedTropicBirdEntity.this.getBoundingBox().expand(0.20000000298023224D).intersects(livingEntity.getBoundingBox())) {
				RedTailedTropicBirdEntity.this.tryAttack(livingEntity);
				RedTailedTropicBirdEntity.this.movementType = RedTailedTropicBirdEntity.PhantomStyleHuntMovement.HUNTING;
				if (!RedTailedTropicBirdEntity.this.isSilent()) {
					RedTailedTropicBirdEntity.this.world.syncWorldEvent(1039, RedTailedTropicBirdEntity.this.getBlockPos(), 0);
				}
			} else if (RedTailedTropicBirdEntity.this.horizontalCollision || RedTailedTropicBirdEntity.this.hurtTime > 0) {
				RedTailedTropicBirdEntity.this.movementType = RedTailedTropicBirdEntity.PhantomStyleHuntMovement.HUNTING;
			}

		}
	}

	class CircleMovementGoal extends RedTailedTropicBirdEntity.MovementGoal {
		private float angle;
		private float radius;
		private float yOffset;
		private float circlingDirection;

		private CircleMovementGoal() {
			super();
		}

		public boolean canStart() {
			return RedTailedTropicBirdEntity.this.getTarget() == null || RedTailedTropicBirdEntity.this.movementType == RedTailedTropicBirdEntity.PhantomStyleHuntMovement.HUNTING;
		}

		public void start() {
			this.radius = 5.0F + RedTailedTropicBirdEntity.this.random.nextFloat() * 10.0F;
			this.yOffset = -4.0F + RedTailedTropicBirdEntity.this.random.nextFloat() * 9.0F;
			this.circlingDirection = RedTailedTropicBirdEntity.this.random.nextBoolean() ? 1.0F : -1.0F;
			this.adjustDirection();
		}

		public void tick() {
			if (RedTailedTropicBirdEntity.this.random.nextInt(350) == 0) {
				this.yOffset = -4.0F + RedTailedTropicBirdEntity.this.random.nextFloat() * 9.0F;
			}

			if (RedTailedTropicBirdEntity.this.random.nextInt(250) == 0) {
				++this.radius;
				if (this.radius > 15.0F) {
					this.radius = 5.0F;
					this.circlingDirection = -this.circlingDirection;
				}
			}

			if (RedTailedTropicBirdEntity.this.random.nextInt(450) == 0) {
				this.angle = RedTailedTropicBirdEntity.this.random.nextFloat() * 2.0F * 3.1415927F;
				this.adjustDirection();
			}

			if (this.isNearTarget()) {
				this.adjustDirection();
			}

			if (RedTailedTropicBirdEntity.this.targetPosition.y < RedTailedTropicBirdEntity.this.getY() && !RedTailedTropicBirdEntity.this.world.isAir(RedTailedTropicBirdEntity.this.getBlockPos().down(1))) {
				this.yOffset = Math.max(1.0F, this.yOffset);
				this.adjustDirection();
			}

			if (RedTailedTropicBirdEntity.this.targetPosition.y > RedTailedTropicBirdEntity.this.getY() && !RedTailedTropicBirdEntity.this.world.isAir(RedTailedTropicBirdEntity.this.getBlockPos().up(1))) {
				this.yOffset = Math.min(-1.0F, this.yOffset);
				this.adjustDirection();
			}

		}

		private void adjustDirection() {
			if (BlockPos.ORIGIN.equals(RedTailedTropicBirdEntity.this.circlingCentre)) {
				RedTailedTropicBirdEntity.this.circlingCentre = RedTailedTropicBirdEntity.this.getBlockPos();
			}

			this.angle += this.circlingDirection * 15.0F * 0.017453292F;
			RedTailedTropicBirdEntity.this.targetPosition = Vec3d.of(RedTailedTropicBirdEntity.this.circlingCentre).add(this.radius * MathHelper.cos(this.angle), -4.0F + this.yOffset, this.radius * MathHelper.sin(this.angle));
		}
	}

	abstract class MovementGoal extends Goal {
		public MovementGoal() {
			this.setControls(EnumSet.of(Goal.Control.MOVE));
		}

		protected boolean isNearTarget() {
			return RedTailedTropicBirdEntity.this.targetPosition.squaredDistanceTo(RedTailedTropicBirdEntity.this.getX(), RedTailedTropicBirdEntity.this.getY(), RedTailedTropicBirdEntity.this.getZ()) < 4.0D;
		}
	}

	enum PhantomStyleHuntMovement {
		HUNTING,
		ATTACKING
	}
}
