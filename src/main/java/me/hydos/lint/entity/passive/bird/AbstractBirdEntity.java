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

import net.minecraft.entity.EntityType;
import net.minecraft.entity.attribute.DefaultAttributeContainer;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.passive.TameableShoulderEntity;
import net.minecraft.sound.SoundEvent;
import net.minecraft.world.World;
import software.bernie.geckolib3.core.IAnimatable;
import software.bernie.geckolib3.core.PlayState;
import software.bernie.geckolib3.core.controller.AnimationController;
import software.bernie.geckolib3.core.event.predicate.AnimationEvent;
import software.bernie.geckolib3.core.manager.AnimationData;
import software.bernie.geckolib3.core.manager.AnimationFactory;

@SuppressWarnings("EntityConstructor")
public abstract class AbstractBirdEntity extends TameableShoulderEntity implements IAnimatable {

	private final AnimationFactory factory = new AnimationFactory(this);
	private final BirdData birdData;

	protected AbstractBirdEntity(EntityType<? extends TameableShoulderEntity> entityType, World world, BirdData birdData) {
		super(entityType, world);
		this.birdData = birdData;
	}

	public static DefaultAttributeContainer.Builder createBirdAttributes(double flySpeed, double health) {
		return createBirdAttributes(flySpeed, health, 0.4D);
	}

	public static DefaultAttributeContainer.Builder createBirdAttributes(double flySpeed, double health, double atk) {
		return createMobAttributes()
				.add(EntityAttributes.GENERIC_FLYING_SPEED, flySpeed)
				.add(EntityAttributes.GENERIC_MOVEMENT_SPEED, 0.2D)
				.add(EntityAttributes.GENERIC_ATTACK_DAMAGE, atk)
				.add(EntityAttributes.GENERIC_MAX_HEALTH, health);
	}

	public BirdData getBirdData() {
		return birdData;
	}

	@Override
	public void registerControllers(AnimationData data) {
		data.addAnimationController(new AnimationController<>(this, "controller", 0, this::predicate));
	}

	public abstract <E extends IAnimatable> PlayState predicate(AnimationEvent<E> event);

	@Override
	public AnimationFactory getFactory() {
		return factory;
	}

	public static class BirdData {

		public SoundEvent sound;
		public String name;
		public String description;
		//TODO: give birds more information

		public BirdData(SoundEvent sound, String name, String description) {
			this.sound = sound;
			this.name = name;
			this.description = description;
		}
	}
}
