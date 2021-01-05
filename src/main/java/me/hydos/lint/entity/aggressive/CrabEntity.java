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

import me.hydos.lint.sound.Sounds;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.goal.LookAroundGoal;
import net.minecraft.entity.ai.goal.LookAtEntityGoal;
import net.minecraft.entity.ai.goal.RevengeGoal;
import net.minecraft.entity.attribute.DefaultAttributeContainer;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.mob.PathAwareEntity;
import net.minecraft.sound.SoundEvent;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;
import software.bernie.geckolib3.core.IAnimatable;
import software.bernie.geckolib3.core.PlayState;
import software.bernie.geckolib3.core.builder.AnimationBuilder;
import software.bernie.geckolib3.core.controller.AnimationController;
import software.bernie.geckolib3.core.event.predicate.AnimationEvent;
import software.bernie.geckolib3.core.manager.AnimationData;
import software.bernie.geckolib3.core.manager.AnimationFactory;

@SuppressWarnings("EntityConstructor")
public class CrabEntity extends PathAwareEntity implements IAnimatable {

	private static final AnimationBuilder IDLE_ANIMATION = new AnimationBuilder().addAnimation("animation.crab.idle", true);
	private static final AnimationBuilder CRAB_ANIMATION = new AnimationBuilder().addAnimation("animation.crab.rave", true);

	private final AnimationFactory factory = new AnimationFactory(this);

	private boolean raving;
	private BlockPos songPosition;

	public CrabEntity(EntityType<? extends PathAwareEntity> entityType, World world) {
		super(entityType, world);
	}

	public static DefaultAttributeContainer.Builder createCrobAttributes() {
		return createMobAttributes().add(EntityAttributes.GENERIC_ATTACK_DAMAGE, 1d).add(EntityAttributes.GENERIC_MOVEMENT_SPEED, 0.3d);
	}

	@Override
	public void registerControllers(AnimationData data) {
		data.addAnimationController(new AnimationController<>(this, "controller", 0, this::predicate));
	}

	@Override
	protected void initGoals() {
		goalSelector.add(3, new LookAtEntityGoal(this, LivingEntity.class, 10));
		goalSelector.add(2, new RevengeGoal(this).setGroupRevenge(CrabEntity.class));
		goalSelector.add(1, new LookAroundGoal(this));
	}

	@Override
	public void tickMovement() {
		super.tickMovement();
		setTarget(world.getClosestPlayer(this, 10));
	}

	@Nullable
	@Override
	protected SoundEvent getAmbientSound() {
		return Sounds.CRAB_IDLE;
	}

	@Environment(EnvType.CLIENT)
	public void setNearbySongPlaying(BlockPos songPosition, boolean playing) {
		this.songPosition = songPosition;
		this.raving = playing;
	}

	private <P extends IAnimatable> PlayState predicate(AnimationEvent<P> event) {
		if (raving && songPosition != null && getPos().squaredDistanceTo(Vec3d.ofCenter(songPosition)) < 144) {
			event.getController().setAnimation(CRAB_ANIMATION);
		} else {
			event.getController().setAnimation(IDLE_ANIMATION);
		}

		return PlayState.CONTINUE;
	}

	@Override
	public AnimationFactory getFactory() {
		return factory;
	}
}
