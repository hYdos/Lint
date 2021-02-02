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

import java.util.EnumSet;
import java.util.function.Predicate;

import net.minecraft.block.Block;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.goal.Goal;
import net.minecraft.entity.ai.pathing.EntityNavigation;
import net.minecraft.entity.ai.pathing.Path;
import net.minecraft.entity.mob.PathAwareEntity;
import net.minecraft.predicate.entity.EntityPredicates;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class SeekBlockGoal extends Goal {
	protected final PathAwareEntity mob;
	protected final int seekDistance;
	protected final EntityNavigation seekingNav;
	protected final Predicate<Block> blockToGoTo;
	protected final Predicate<LivingEntity> extraInclusionSelector;
	protected final Predicate<LivingEntity> inclusionSelector;
	private final double slowSpeed;
	private final double fastSpeed;
	protected BlockPos targetBlock;
	protected Path path;

	public SeekBlockGoal(PathAwareEntity mob, Block fleeFromType, int distance, double slowSpeed, double fastSpeed) {
		this(mob, blk -> blk == fleeFromType, le -> true, distance, slowSpeed, fastSpeed, EntityPredicates.EXCEPT_CREATIVE_OR_SPECTATOR::test);
	}

	public SeekBlockGoal(PathAwareEntity mob, Predicate<Block> fleeFromType, int distance, double slowSpeed, double fastSpeed) {
		this(mob, fleeFromType, le -> true, distance, slowSpeed, fastSpeed, EntityPredicates.EXCEPT_CREATIVE_OR_SPECTATOR::test);
	}

	public SeekBlockGoal(PathAwareEntity mob, Predicate<Block> fleeFromType, Predicate<LivingEntity> extraInclusionSelector, int distance, double slowSpeed, double fastSpeed, Predicate<LivingEntity> inclusionSelector) {
		this.mob = mob;
		this.blockToGoTo = fleeFromType;
		this.extraInclusionSelector = extraInclusionSelector;
		this.seekDistance = distance;
		this.slowSpeed = slowSpeed;
		this.fastSpeed = fastSpeed;
		this.inclusionSelector = inclusionSelector;
		this.seekingNav = mob.getNavigation();
		this.setControls(EnumSet.of(Goal.Control.MOVE));
	}

	public SeekBlockGoal(PathAwareEntity fleeingEntity, Predicate<Block> fleeFromType, int distance, double fleeSlowSpeed, double fleeFastSpeed, Predicate<LivingEntity> inclusionSelector) {
		this(fleeingEntity, fleeFromType, (livingEntity) -> {
			return true;
		}, distance, fleeSlowSpeed, fleeFastSpeed, inclusionSelector);
	}

	public boolean canStart() {
		World world = this.mob.getEntityWorld();
		BlockPos pos = this.mob.getBlockPos();
		BlockPos seekPosCache = null;
		BlockPos.Mutable pos2 = new BlockPos.Mutable();
		double sqdist = 10000;

		// get nearest block of predicate
		for (int xo = -this.seekDistance; xo <= this.seekDistance; ++xo) {
			pos2.setX(pos.getX() + xo);

			for (int zo = -this.seekDistance; zo <= this.seekDistance; ++zo) {
				pos2.setZ(pos.getZ() + zo);

				for (int yo = -1; yo <= 1; ++yo) {
					int y = pos.getY() + yo;

					if (y >= 0 && y <= world.getHeight()) {
						pos2.setY(y);

						if (this.blockToGoTo.test(world.getBlockState(pos2).getBlock())) {
							double newSqDist = pos2.getSquaredDistance(pos);

							if (newSqDist < sqdist) {
								seekPosCache = pos2.toImmutable();
								sqdist = newSqDist;
							}
						}
					}
				}
			}
		}

		if (seekPosCache == null) {
			return false;
		} else {
			this.path = this.seekingNav.findPathTo(seekPosCache.getX(), seekPosCache.getY(), seekPosCache.getZ(), 0);

			if (this.path != null) {
				this.targetBlock = seekPosCache;
			}

			return this.path != null;
		}
	}

	public boolean shouldContinue() {
		return !this.seekingNav.isIdle();
	}

	public void start() {
		this.seekingNav.startMovingAlong(this.path, this.slowSpeed);
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
