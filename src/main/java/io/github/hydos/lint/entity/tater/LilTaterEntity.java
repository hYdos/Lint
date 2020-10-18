package io.github.hydos.lint.entity.tater;

import io.github.hydos.lint.container.Containers;
import io.github.hydos.lint.container.util.LintInventory;
import net.fabricmc.fabric.api.container.ContainerProviderRegistry;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.goal.*;
import net.minecraft.entity.ai.pathing.BirdNavigation;
import net.minecraft.entity.ai.pathing.EntityNavigation;
import net.minecraft.entity.attribute.DefaultAttributeContainer;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.passive.PassiveEntity;
import net.minecraft.entity.passive.TameableShoulderEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.Inventories;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.world.World;

@SuppressWarnings("EntityConstructor")
public class LilTaterEntity extends TameableShoulderEntity {

    public final LintInventory inventory;

    public float size = 0;

    public LilTaterEntity(EntityType<? extends LilTaterEntity> type, World world) {
        super(type, world);
        inventory = new LintInventory(31);
    }

    public static DefaultAttributeContainer.Builder initAttributes() {
        return LivingEntity.createLivingAttributes()
                .add(EntityAttributes.GENERIC_MOVEMENT_SPEED, 0.6)
                .add(EntityAttributes.GENERIC_FOLLOW_RANGE, 6)
                .add(EntityAttributes.GENERIC_ATTACK_KNOCKBACK, 1)
                .add(EntityAttributes.GENERIC_FLYING_SPEED, 0.6);
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
        this.goalSelector.add(4, new LookAtEntityGoal(this, LilTaterEntity.class, 10));
        this.goalSelector.add(6, new WanderAroundGoal(this, 0.4));
    }

    public ActionResult interactMob(PlayerEntity player, Hand hand) {
        if (!this.world.isClient) {
            if (!isTamed() && player.getStackInHand(hand).getItem() == Items.POTATO) {
                this.setOwner(player);
                this.setTamed(true);
                this.world.addParticle(ParticleTypes.HEART, this.getX(), this.getY(), this.getZ(), 0, 4, 0);
                player.getStackInHand(hand).decrement(1);
            } else {
                if (getOwner() == player) {
                    //the person who clicked owns the tater
                    ContainerProviderRegistry.INSTANCE.openContainer(Containers.TATER_CONTAINER_ID, player, packetByteBuf -> packetByteBuf.writeInt(getEntityId()));
                } else {
                    return ActionResult.FAIL;
                }
            }
        }

        return ActionResult.SUCCESS;
    }

    @Override
    public void mobTick() {
        super.mobTick();
    }

    @Override
    protected EntityNavigation createNavigation(World world) {
        return new BirdNavigation(this, world);
    }

    @Override
    public PassiveEntity createChild(ServerWorld world, PassiveEntity entity) {
        return (PassiveEntity) getType().create(world);
    }
}
