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

import me.hydos.lint.entity.passive.TinyPotatoEntity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.goal.Goal;
import net.minecraft.entity.ai.goal.MeleeAttackGoal;
import net.minecraft.entity.ai.goal.SwimGoal;
import net.minecraft.entity.attribute.DefaultAttributeContainer;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.mob.Monster;
import net.minecraft.entity.passive.PassiveEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

import java.util.EnumSet;
import java.util.UUID;

@SuppressWarnings("EntityConstructor")
public class TaterMinionEntity extends TinyPotatoEntity implements Monster {

    public TaterMinionEntity(EntityType<? extends TaterMinionEntity> type, World world, LivingEntity target) {
        super(type, world);
        setTarget(target);
    }

    public static DefaultAttributeContainer.Builder createAttributes() {
        return TinyPotatoEntity.createAttributes()
                .add(EntityAttributes.GENERIC_MAX_HEALTH, 10)
                .add(EntityAttributes.GENERIC_ATTACK_DAMAGE, 1.5);
    }

    @Override
    protected void initGoals() {
        goalSelector.add(1, new SwimGoal(this));
        goalSelector.add(2, new MeleeAttackGoal(this, 1D, false));
    }

    @Override
    public void mobTick() {
        super.mobTick();
        if (getTarget() != null) {
            if (getTarget().removed) {
                remove();
            }
        }
    }

    @Override
    public boolean isBreedingItem(ItemStack stack) {
        return false;
    }

    @Override
    public ActionResult interactMob(PlayerEntity player, Hand hand) {
        return ActionResult.FAIL;
    }

    @Override
    public UUID getOwnerUuid() {
        return null;
    }

    @Override
    public void setOwnerUuid(UUID uuid) {
    }

    @Override
    public LivingEntity getOwner() {
        return null;
    }

    @Override
    public void setOwner(PlayerEntity player) {
    }

    @Override
    public boolean isOwner(LivingEntity entity) {
        return false;
    }

    @Override
    public boolean canAttackWithOwner(LivingEntity target, LivingEntity owner) {
        return false;
    }

    @Override
    public @Nullable PassiveEntity createChild(ServerWorld world, PassiveEntity entity) {
        return null;
    }

    private class LookAtTargetGoal extends Goal {

        public LookAtTargetGoal() {
            this.setControls(EnumSet.of(Goal.Control.MOVE));
        }

        public boolean canStart() {
            return !getMoveControl().isMoving() && random.nextInt(7) == 0;
        }

        public boolean shouldContinue() {
            return false;
        }

        public void tick() {
            if (getTarget() == null) {
                Vec3d vec3d = getVelocity();
                yaw = -((float) MathHelper.atan2(vec3d.x, vec3d.z)) * 57.295776F;
                bodyYaw = yaw;
            } else {
                LivingEntity livingEntity = getTarget();
                double d = 64.0D;
                if (livingEntity.squaredDistanceTo(TaterMinionEntity.this) < (d * d)) {
                    double e = livingEntity.getX() - getX();
                    double f = livingEntity.getZ() - getZ();
                    yaw = -((float) MathHelper.atan2(e, f)) * 57.295776F;
                    bodyYaw = yaw;
                }
            }
        }
    }
}
