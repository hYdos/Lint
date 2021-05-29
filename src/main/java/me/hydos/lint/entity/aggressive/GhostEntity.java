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

package me.hydos.lint.entity.aggressive;

import java.util.EnumSet;
import me.hydos.lint.entity.aggressive.GhostEntity.RetreatToCavernsGoal;
import me.hydos.lint.entity.goal.FleeBlockGoal;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.goal.FollowTargetGoal;
import net.minecraft.entity.ai.goal.Goal;
import net.minecraft.entity.ai.goal.LookAtEntityGoal;
import net.minecraft.entity.ai.goal.RevengeGoal;
import net.minecraft.entity.ai.pathing.BirdNavigation;
import net.minecraft.entity.ai.pathing.EntityNavigation;
import net.minecraft.entity.ai.pathing.Path;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.entity.mob.VexEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.raid.RaiderEntity;
import net.minecraft.sound.SoundEvent;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.Heightmap;
import net.minecraft.world.World;
import software.bernie.geckolib3.core.IAnimatable;
import software.bernie.geckolib3.core.PlayState;
import software.bernie.geckolib3.core.builder.AnimationBuilder;
import software.bernie.geckolib3.core.controller.AnimationController;
import software.bernie.geckolib3.core.event.predicate.AnimationEvent;
import software.bernie.geckolib3.core.manager.AnimationData;
import software.bernie.geckolib3.core.manager.AnimationFactory;

@SuppressWarnings("EntityConstructor")
public class GhostEntity extends VexEntity implements IAnimatable {
	private static final AnimationBuilder IDLE_ANIMATION = new AnimationBuilder().addAnimation("animation.ghost.idle", true);

	private final AnimationFactory factory = new AnimationFactory(this);
	private boolean cavern = false;

	public GhostEntity(EntityType<GhostEntity> type, World world) {
		super(type, world);
	}

	@Override
	public void refreshPositionAndAngles(double x, double y, double z, float yaw, float pitch) {
		super.refreshPositionAndAngles(x, y, z, yaw, pitch);
		this.cavern = this.isInCaverns(y);
	}

	private boolean isInCaverns(double y) {
		BlockPos pos = this.getBlockPos();

		return y < Math.max(this.world.getSeaLevel(), this.world.getChunk(pos).sampleHeightmap(Heightmap.Type.MOTION_BLOCKING, pos.getX(), pos.getZ()));
	}

	@Override
	public void tick() {
		this.noClip = true;
		super.tick();
		this.noClip = false;
		this.setNoGravity(true);
	}

	@Override
	protected void initGoals() {
		this.goalSelector.add(0, new RetreatToCavernsGoal());
		this.goalSelector.add(1, new FleeBlockGoal(this, b -> b.getDefaultState().getLuminance() > 8, 9, 1.0f, 2.0f));
		this.goalSelector.add(4, new me.hydos.lint.entity.aggressive.GhostEntity.ChargeTargetGoal());
		this.goalSelector.add(8, new me.hydos.lint.entity.aggressive.GhostEntity.LookAtTargetGoal());
		this.goalSelector.add(9, new LookAtEntityGoal(this, PlayerEntity.class, 3.0F, 1.0F));
		this.goalSelector.add(10, new LookAtEntityGoal(this, MobEntity.class, 8.0F));
		this.targetSelector.add(1, (new RevengeGoal(this, RaiderEntity.class)).setGroupRevenge());
		this.targetSelector.add(3, new FollowTargetGoal<>(this, PlayerEntity.class, true));
	}

	@Override
	public EntityNavigation createNavigation(World world) {
		BirdNavigation flyingNav = new BirdNavigation(this, world);
		flyingNav.setCanPathThroughDoors(true);
		flyingNav.setCanSwim(true);
		flyingNav.setSpeed(100);
		flyingNav.setCanEnterOpenDoors(true);
		return flyingNav;
	}

	@Override
	protected SoundEvent getHurtSound(DamageSource source) {
		return SoundEvents.ENTITY_PHANTOM_HURT;
	}

	@Override
	protected SoundEvent getAmbientSound() {
		return SoundEvents.ENTITY_PHANTOM_AMBIENT;
	}

	@Override
	public void registerControllers(AnimationData data) {
		data.addAnimationController(new AnimationController<>(this, "controller", 0, this::predicate));
	}

	@Override
	public void tickMovement() {
		if (this.isAffectedByDaylight()) { //TODO: make not burn in corrupt forest
			this.setOnFireFor(8);
		}
		super.tickMovement();
	}

	private <P extends IAnimatable> PlayState predicate(AnimationEvent<P> event) {
		event.getController().setAnimation(IDLE_ANIMATION);
		return PlayState.CONTINUE;
	}

	@Override
	public AnimationFactory getFactory() {
		return factory;
	}

	class LookAtTargetGoal extends Goal {
		public LookAtTargetGoal() {
			this.setControls(EnumSet.of(Goal.Control.MOVE));
		}

		public boolean canStart() {
			return !GhostEntity.this.getMoveControl().isMoving() && GhostEntity.this.random.nextInt(7) == 0;
		}

		public boolean shouldContinue() {
			return false;
		}

		public void tick() {
			BlockPos blockPos = GhostEntity.this.getBounds();
			if (blockPos == null) {
				blockPos = GhostEntity.this.getBlockPos();
			}

			for (int i = 0; i < 3; ++i) {
				BlockPos blockPos2 = blockPos.add(GhostEntity.this.random.nextInt(15) - 7, GhostEntity.this.random.nextInt(11) - 5, GhostEntity.this.random.nextInt(15) - 7);
				if (GhostEntity.this.world.isAir(blockPos2)) {
					GhostEntity.this.moveControl.moveTo((double) blockPos2.getX() + 0.5D, (double) blockPos2.getY() + 0.5D, (double) blockPos2.getZ() + 0.5D, 0.25D);
					if (GhostEntity.this.getTarget() == null) {
						GhostEntity.this.getLookControl().lookAt((double) blockPos2.getX() + 0.5D, (double) blockPos2.getY() + 0.5D, (double) blockPos2.getZ() + 0.5D, 180.0F, 20.0F);
					}
					break;
				}
			}

		}
	}

	class ChargeTargetGoal extends Goal {
		public ChargeTargetGoal() {
			this.setControls(EnumSet.of(Goal.Control.MOVE));
		}

		public boolean canStart() {
			if (GhostEntity.this.getTarget() != null && !GhostEntity.this.getMoveControl().isMoving() && GhostEntity.this.random.nextInt(7) == 0) {
				boolean b0 = GhostEntity.this.squaredDistanceTo(GhostEntity.this.getTarget()) > 4.0D;
				b0 &= GhostEntity.this.world.getLightLevel(GhostEntity.this.getTarget().getBlockPos()) < 9;
				return b0;
			} else {
				return false;
			}
		}

		public boolean shouldContinue() {
			if (GhostEntity.this.getTarget() != null) {
				boolean b0 = GhostEntity.this.getMoveControl().isMoving() && GhostEntity.this.isCharging() && GhostEntity.this.getTarget() != null && GhostEntity.this.getTarget().isAlive();
				b0 &= GhostEntity.this.world.getLightLevel(GhostEntity.this.getTarget().getBlockPos()) < 9;
				return b0;
			}
			return false;
		}

		public void start() {
			LivingEntity livingEntity = GhostEntity.this.getTarget();
			Vec3d vec3d = livingEntity.getCameraPosVec(1.0F);
			GhostEntity.this.moveControl.moveTo(vec3d.x, vec3d.y, vec3d.z, 1.0D);
			GhostEntity.this.setCharging(true);
			GhostEntity.this.playSound(SoundEvents.ENTITY_VEX_CHARGE, 1.0F, 1.0F);
		}

		public void stop() {
			GhostEntity.this.setCharging(false);
		}

		public void tick() {
			LivingEntity livingEntity = GhostEntity.this.getTarget();

			if (GhostEntity.this.getBoundingBox().intersects(livingEntity.getBoundingBox())) {
				GhostEntity.this.tryAttack(livingEntity);
				GhostEntity.this.setCharging(false);
			} else {
				double d = GhostEntity.this.squaredDistanceTo(livingEntity);
				if (d < 9.0D) {
					Vec3d vec3d = livingEntity.getCameraPosVec(1.0F);
					GhostEntity.this.moveControl.moveTo(vec3d.x, vec3d.y, vec3d.z, 1.0D);
				}
			}
		}
	}

	class RetreatToCavernsGoal extends Goal {
		RetreatToCavernsGoal() {
			this.nav = GhostEntity.this.getNavigation();
		}

		private final EntityNavigation nav;
		private Path path;

		@Override
		public boolean canStart() {
			if (GhostEntity.this.cavern && !GhostEntity.this.isInCaverns(GhostEntity.this.getY())) {
				this.path = this.nav.findPathTo(GhostEntity.this.getX() + random.nextInt(9) - 4, Math.max(0, GhostEntity.this.getY() - 20), GhostEntity.this.getZ() + random.nextInt(9) - 4, 0);
				return true;
			} else {
				return false;
			}
		}
		
		@Override
		public void start() {
			this.nav.startMovingAlong(this.path, 1.5f);
		}

		public boolean shouldContinue() {
			return !this.nav.isIdle();
		}
	}
}
