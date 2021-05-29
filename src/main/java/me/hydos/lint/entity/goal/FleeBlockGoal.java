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

package me.hydos.lint.entity.goal;

import net.minecraft.block.Block;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.goal.Goal;
import net.minecraft.entity.ai.pathing.EntityNavigation;
import net.minecraft.entity.ai.pathing.Path;
import net.minecraft.entity.mob.PathAwareEntity;
import net.minecraft.predicate.entity.EntityPredicates;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;

import java.util.EnumSet;
import java.util.function.Predicate;

public class FleeBlockGoal extends Goal {
	protected final PathAwareEntity mob;
	protected final int fleeDistance;
	protected final EntityNavigation fleeingEntityNavigation;
	protected final Predicate<Block> blockToFleeFrom;
	protected final Predicate<LivingEntity> extraInclusionSelector;
	protected final Predicate<LivingEntity> inclusionSelector;
	private final double slowSpeed;
	private final double fastSpeed;
	protected BlockPos targetBlock;
	protected Path fleePath;

	public FleeBlockGoal(PathAwareEntity mob, Block fleeFromType, int distance, double slowSpeed, double fastSpeed) {
		this(mob, blk -> blk == fleeFromType, le -> true, distance, slowSpeed, fastSpeed, EntityPredicates.EXCEPT_CREATIVE_OR_SPECTATOR::test);
	}

	public FleeBlockGoal(PathAwareEntity mob, Predicate<Block> fleeFromType, int distance, double slowSpeed, double fastSpeed) {
		this(mob, fleeFromType, le -> true, distance, slowSpeed, fastSpeed, EntityPredicates.EXCEPT_CREATIVE_OR_SPECTATOR::test);
	}

	public FleeBlockGoal(PathAwareEntity mob, Predicate<Block> fleeFromType, Predicate<LivingEntity> extraInclusionSelector, int distance, double slowSpeed, double fastSpeed, Predicate<LivingEntity> inclusionSelector) {
		this.mob = mob;
		this.blockToFleeFrom = fleeFromType;
		this.extraInclusionSelector = extraInclusionSelector;
		this.fleeDistance = distance;
		this.slowSpeed = slowSpeed;
		this.fastSpeed = fastSpeed;
		this.inclusionSelector = inclusionSelector;
		this.fleeingEntityNavigation = mob.getNavigation();
		this.setControls(EnumSet.of(Goal.Control.MOVE));
	}

	public FleeBlockGoal(PathAwareEntity fleeingEntity, Predicate<Block> fleeFromType, int distance, double fleeSlowSpeed, double fleeFastSpeed, Predicate<LivingEntity> inclusionSelector) {
		this(fleeingEntity, fleeFromType, (livingEntity) -> {
			return true;
		}, distance, fleeSlowSpeed, fleeFastSpeed, inclusionSelector);
	}

	public boolean canStart() {
		World world = this.mob.getEntityWorld();
		BlockPos pos = this.mob.getBlockPos();
		BlockPos fleePosCache = null;
		BlockPos.Mutable pos2 = new BlockPos.Mutable();
		double sqdist = 10000;

		// get nearest block of predicate
		for (int xo = -this.fleeDistance; xo <= this.fleeDistance; ++xo) {
			pos2.setX(pos.getX() + xo);

			for (int zo = -this.fleeDistance; zo <= this.fleeDistance; ++zo) {
				pos2.setZ(pos.getZ() + zo);

				for (int yo = -1; yo <= 1; ++yo) {
					int y = pos.getY() + yo;

					if (y >= 0 && y <= world.getHeight()) {
						pos2.setY(y);

						if (this.blockToFleeFrom.test(world.getBlockState(pos2).getBlock())) {
							double newSqDist = pos2.getSquaredDistance(pos);

							if (newSqDist < sqdist) {
								fleePosCache = pos2.toImmutable();
								sqdist = newSqDist;
							}
						}
					}
				}
			}
		}

		if (fleePosCache == null) {
			return false;
		} else {
			Vec3d target = MoveToTargetPosGoal.findTargetAwayFrom(this.mob, this.fleeDistance + 2, 5, new Vec3d(fleePosCache.getX() + 0.5, fleePosCache.getY() + 0.5, fleePosCache.getZ() + 0.5));

			if (target == null) {
				return false;
			} else if (target.squaredDistanceTo(fleePosCache.getX(), fleePosCache.getY(), fleePosCache.getZ()) < target.squaredDistanceTo(pos.getX(), pos.getY(), pos.getZ())) {
				return false;
			} else {
				this.fleePath = this.fleeingEntityNavigation.findPathTo(target.x, target.y, target.z, 0);

				if (this.fleePath != null) {
					this.targetBlock = fleePosCache;
				}

				return this.fleePath != null;
			}
		}
	}

	public boolean shouldContinue() {
		return !this.fleeingEntityNavigation.isIdle();
	}

	public void start() {
		this.fleeingEntityNavigation.startMovingAlong(this.fleePath, this.slowSpeed);
	}

	public void stop() {
		this.targetBlock = null;
	}

	public void tick() {
		if (this.mob.getBlockPos().getSquaredDistance(this.targetBlock) < 49 * 49) {
			this.mob.getNavigation().setSpeed(this.fastSpeed);
		} else {
			this.mob.getNavigation().setSpeed(this.slowSpeed);
		}
	}
}
