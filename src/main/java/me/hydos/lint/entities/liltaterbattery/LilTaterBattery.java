package me.hydos.lint.entities.liltaterbattery;

import me.hydos.lint.core.Lint;
import me.hydos.lint.containers.LintInventory;
import net.fabricmc.fabric.api.container.ContainerProviderRegistry;
import net.minecraft.entity.Entity;
import net.minecraft.entity.ai.goal.*;
import net.minecraft.entity.ai.pathing.BirdNavigation;
import net.minecraft.entity.ai.pathing.EntityNavigation;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.passive.PassiveEntity;
import net.minecraft.entity.passive.TameableShoulderEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.Inventories;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.util.Hand;
import net.minecraft.util.Identifier;
import net.minecraft.world.World;

public class LilTaterBattery extends TameableShoulderEntity {

    public LintInventory inventory;

    public float size = 0;

    public LilTaterBattery(World world) {
        super(Lint.LIL_TATER, world);
        inventory = new LintInventory(31){

        };
    }

    @Override
    public void fromTag(CompoundTag tag) {
        super.fromTag(tag);
        Inventories.fromTag(tag, inventory.getRawList());
        size = 1;
    }

    @Override
    public CompoundTag toTag(CompoundTag tag) {
        Inventories.toTag(tag, inventory.getRawList());
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
        this.goalSelector.add(0, new SwimGoal(this));
        this.goalSelector.add(1, new AttackWithOwnerGoal(this));
        this.goalSelector.add(2, new FollowOwnerGoal(this, 0.5D, 1.0F, 3.0F, false));
        this.goalSelector.add(2, new SitOnOwnerShoulderGoal(this));
        this.goalSelector.add(3, new LookAtEntityGoal(this, PlayerEntity.class, 10));
        this.goalSelector.add(4, new LookAtEntityGoal(this, LilTaterBattery.class, 10));
        this.goalSelector.add(6, new WanderAroundGoal(this, 0.4));
    }

    @Override
    public PassiveEntity createChild(PassiveEntity mate) {
        return new LilTaterBattery(world);
    }

    public boolean interactMob(PlayerEntity player, Hand hand) {
        if (!this.world.isClient) {
            if(!isTamed()){
                this.setOwner(player);
                this.setTamed(true);
                this.world.addParticle(ParticleTypes.HEART, this.getX(), this.getY(), this.getZ(), 0, 4, 0);
                player.setStackInHand(hand, ItemStack.EMPTY);
            }else{
                ContainerProviderRegistry.INSTANCE.openContainer(new Identifier("lint", "liltater"), player, packetByteBuf -> packetByteBuf.writeInt(getEntityId()));
            }

        }
        return true;
    }

    @Override
    public void mobTick() {
        super.mobTick();
    }

    @Override
    protected EntityNavigation createNavigation(World world) {
        return new BirdNavigation(this, world);
    }
}
