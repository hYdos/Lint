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

import org.jetbrains.annotations.Nullable;

import me.hydos.lint.entity.Birds;
import me.hydos.lint.entity.passive.TinyPotatoEntity;
import me.hydos.lint.entity.passive.bird.AbstractBirdEntity;
import me.hydos.lint.entity.passive.bird.EasternRosellaEntity;
import me.hydos.lint.sound.Sounds;
import net.minecraft.block.Blocks;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.Flutterer;
import net.minecraft.entity.ai.control.FlightMoveControl;
import net.minecraft.entity.ai.goal.AttackGoal;
import net.minecraft.entity.ai.goal.EscapeDangerGoal;
import net.minecraft.entity.ai.goal.FleeEntityGoal;
import net.minecraft.entity.ai.goal.FlyOntoTreeGoal;
import net.minecraft.entity.ai.goal.FollowTargetGoal;
import net.minecraft.entity.ai.goal.PounceAtTargetGoal;
import net.minecraft.entity.ai.goal.SitGoal;
import net.minecraft.entity.ai.goal.SwimGoal;
import net.minecraft.entity.ai.pathing.BirdNavigation;
import net.minecraft.entity.ai.pathing.EntityNavigation;
import net.minecraft.entity.passive.CatEntity;
import net.minecraft.entity.passive.OcelotEntity;
import net.minecraft.entity.passive.PassiveEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundEvent;
import net.minecraft.world.World;
import software.bernie.geckolib3.core.IAnimatable;
import software.bernie.geckolib3.core.PlayState;
import software.bernie.geckolib3.core.builder.AnimationBuilder;
import software.bernie.geckolib3.core.event.predicate.AnimationEvent;

@SuppressWarnings("EntityConstructor")
public class NightclawEntity extends AbstractBirdEntity implements Flutterer {

	private static final AnimationBuilder FLY_ANIMATION = new AnimationBuilder().addAnimation("animation.nightclaw.fly", true);
	private static final AnimationBuilder IDLE_ANIMATION = new AnimationBuilder().addAnimation("animation.nightclaw.idle", true);

	public NightclawEntity(EntityType<NightclawEntity> type, World world) {
		super(Birds.NIGHTCLAW, world, Birds.NIGHTCLAW_DATA);
		this.moveControl = new FlightMoveControl(this, 10, false);
	}

	@Override
	protected void initGoals() {
		this.goalSelector.add(0, new EscapeDangerGoal(this, 1.25D));
		this.goalSelector.add(0, new SwimGoal(this));
		this.goalSelector.add(1, new FleeEntityGoal<>(this, CatEntity.class, 6.0F, 1.0D, 1.2D));
		this.goalSelector.add(1, new FleeEntityGoal<>(this, OcelotEntity.class, 6.0F, 1.0D, 1.2D));
		this.goalSelector.add(3, new SitGoal(this));
		this.goalSelector.add(4, new FlyOntoTreeGoal(this, 1.0D));
		this.goalSelector.add(4, new PounceAtTargetGoal(this, 0.2F));
		this.goalSelector.add(5, new AttackGoal(this));

		this.targetSelector.add(2, new FollowTargetGoal<>(this, PlayerEntity.class, false));
		this.targetSelector.add(4, new FollowTargetGoal<>(this, EasternRosellaEntity.class, false));
		this.targetSelector.add(4, new FollowTargetGoal<>(this, TinyPotatoEntity.class, false));
	}

	@Nullable
	@Override
	protected SoundEvent getAmbientSound() {
		return Sounds.NIGHTCLAW_IDLE;
	}

	public EntityNavigation createNavigation(World world) {
		BirdNavigation birdNavigation = new BirdNavigation(this, world);
		birdNavigation.setCanPathThroughDoors(false);
		birdNavigation.setCanSwim(true);
		birdNavigation.setCanEnterOpenDoors(true);
		return birdNavigation;
	}

	@Override
	public boolean handleFallDamage(float fallDistance, float damageMultiplier) {
		return false;
	}

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
		return new NightclawEntity(Birds.NIGHTCLAW, world);
	}
}
