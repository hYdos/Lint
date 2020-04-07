package me.hydos.lint.entities.boss;

import me.hydos.lint.entities.tater.LilTaterEntity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.goal.*;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.mob.Monster;
import net.minecraft.entity.passive.PassiveEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Hand;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;

import java.util.EnumSet;
import java.util.UUID;

@SuppressWarnings("EntityConstructor")
public class TaterMinion extends LilTaterEntity implements Monster {

    public TaterMinion(EntityType<? extends TaterMinion> type, World world, LivingEntity target) {
        super(type, world);
        setTarget(target);
    }

    @Override
    protected void initGoals() {
        goalSelector.add(1, new SwimGoal(this));
        goalSelector.add(2, new MeleeAttackGoal(this, 1D, false));
    }

    @Override
    protected void initAttributes() {
        super.initAttributes();

        getAttributeInstance(EntityAttributes.MAX_HEALTH).setBaseValue(10.0D);
        getAttributes().register(EntityAttributes.ATTACK_DAMAGE).setBaseValue(1.5D);
    }

    @Override
    public void mobTick() {
        super.mobTick();
        if(getTarget() != null){
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
    public boolean interactMob(PlayerEntity player, Hand hand) {
        return false;
    }

    @Override
    public PassiveEntity createChild(PassiveEntity mate) {
        return null;
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
                if (livingEntity.squaredDistanceTo(TaterMinion.this) < (d * d)) {
                    double e = livingEntity.getX() - getX();
                    double f = livingEntity.getZ() - getZ();
                    yaw = -((float) MathHelper.atan2(e, f)) * 57.295776F;
                    bodyYaw = yaw;
                }
            }
        }
    }
}
