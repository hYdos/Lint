package me.hydos.lint.entities.liltaterbattery;

import me.hydos.lint.Lint;
import net.minecraft.entity.Entity;
import net.minecraft.entity.ai.goal.*;
import net.minecraft.entity.ai.pathing.BirdNavigation;
import net.minecraft.entity.ai.pathing.EntityNavigation;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.passive.PassiveEntity;
import net.minecraft.entity.passive.TameableEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.util.Hand;
import net.minecraft.world.World;

public class LilTaterBattery extends TameableEntity {

    public float size = 0;

    public LilTaterBattery(World world) {
        super(Lint.LIL_TATER, world);
    }

    @Override
    public void fromTag(CompoundTag tag) {
        super.fromTag(tag);
        size = 1;
        System.out.println(size);
    }

    @Override
    public CompoundTag toTag(CompoundTag tag) {
        if(tag.getFloat("size") == 0){
            tag.putFloat("size", super.getRandom().nextFloat() + 1.5f);
        }
        return super.toTag(tag);
    }

    @Override
    protected void initAttributes() {
        super.initAttributes();

        this.getAttributes().get(EntityAttributes.MOVEMENT_SPEED).setBaseValue(0.6);
        this.getAttributes().register(EntityAttributes.FLYING_SPEED).setBaseValue(0.6);
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
        this.goalSelector.add(1, new FollowOwnerGoal(this, 0.5D, 1.0F, 3.0F, false));
        this.goalSelector.add(2, new AttackWithOwnerGoal(this));
        this.goalSelector.add(3, new WanderAroundGoal(this, 0.4));
        this.goalSelector.add(2, new SwimGoal(this));
    }

    @Override
    public PassiveEntity createChild(PassiveEntity mate) {
        return new LilTaterBattery(world);
    }

    public boolean interactMob(PlayerEntity player, Hand hand) {
        if (!this.world.isClient) {
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
