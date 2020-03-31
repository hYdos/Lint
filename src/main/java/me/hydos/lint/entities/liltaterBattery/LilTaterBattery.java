package me.hydos.lint.entities.liltaterBattery;

import me.hydos.lint.Lint;
import net.minecraft.entity.Entity;
import net.minecraft.entity.ai.goal.AttackWithOwnerGoal;
import net.minecraft.entity.ai.goal.FollowOwnerGoal;
import net.minecraft.entity.ai.goal.LookAtEntityGoal;
import net.minecraft.entity.ai.goal.WanderAroundGoal;
import net.minecraft.entity.ai.pathing.BirdNavigation;
import net.minecraft.entity.ai.pathing.EntityNavigation;
import net.minecraft.entity.passive.PassiveEntity;
import net.minecraft.entity.passive.TameableEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.util.Hand;
import net.minecraft.world.World;

public class LilTaterBattery extends TameableEntity {

    public LilTaterBattery(World world){
        super(Lint.LIL_TATER, world);

    }

    @Override
    public boolean tryAttack(Entity target) {
        return super.tryAttack(target);
    }

    @Override
    protected void initGoals() {
        super.initGoals();
        this.goalSelector.add(4, new LookAtEntityGoal(this, LilTaterBattery.class, 10));
        this.goalSelector.add(1, new LookAtEntityGoal(this, PlayerEntity.class, 10));
        this.goalSelector.add(1, new FollowOwnerGoal(this, 1.0D, 10.0F, 2.0F, false));
        this.goalSelector.add(2, new AttackWithOwnerGoal(this));
        this.goalSelector.add(3, new WanderAroundGoal(this, 1));
    }

    @Override
    public PassiveEntity createChild(PassiveEntity mate) {
        return this;
    }

    public boolean interactMob(PlayerEntity player, Hand hand) {
        if(!this.world.isClient){
            this.setOwner(player);
            this.setTamed(true);
            this.world.addParticle(ParticleTypes.HEART, this.getX(), this.getY(), this.getZ(), 0, 4, 0);
            player.setStackInHand(hand, ItemStack.EMPTY);
        }
        return true;
    }

    @Override
    protected EntityNavigation createNavigation(World world) {
        return new BirdNavigation(this, world);
    }
}
