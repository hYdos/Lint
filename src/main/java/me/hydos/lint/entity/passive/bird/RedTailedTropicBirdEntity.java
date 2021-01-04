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

import me.hydos.lint.entity.Birds;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.passive.PassiveEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;
import software.bernie.geckolib3.core.IAnimatable;
import software.bernie.geckolib3.core.PlayState;
import software.bernie.geckolib3.core.builder.AnimationBuilder;
import software.bernie.geckolib3.core.event.predicate.AnimationEvent;

@SuppressWarnings("EntityConstructor")
public class RedTailedTropicBirdEntity extends AbstractBirdEntity{

	private static final AnimationBuilder FLY_ANIMATION = new AnimationBuilder().addAnimation("animation.red_tailed_tropicbird.fly", true);
	private static final AnimationBuilder IDLE_ANIMATION = new AnimationBuilder().addAnimation("animation.red_tailed_tropicbird.idle", true);

	public RedTailedTropicBirdEntity(EntityType<RedTailedTropicBirdEntity> entityType, World world) {
		super(entityType, world, Birds.RED_TAILED_TROPICBIRD_DATA);
	}

	@Override
	public <E extends IAnimatable> PlayState predicate(AnimationEvent<E> event) {
		event.getController().setAnimation(IDLE_ANIMATION);
		return PlayState.CONTINUE;
	}

	@Nullable
	@Override
	public PassiveEntity createChild(ServerWorld world, PassiveEntity entity) {
		return new RedTailedTropicBirdEntity(Birds.RED_TAILED_TROPICBIRD, world);
	}
}
