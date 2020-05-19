package me.hydos.lint.entity.beetater;

import me.hydos.lint.containers.util.LintInventory;
import me.hydos.lint.core.Containers;
import me.hydos.lint.entity.tater.LilTaterEntity;
import net.fabricmc.fabric.api.container.ContainerProviderRegistry;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.ai.goal.*;
import net.minecraft.entity.ai.pathing.BirdNavigation;
import net.minecraft.entity.ai.pathing.EntityNavigation;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.passive.PassiveEntity;
import net.minecraft.entity.passive.TameableShoulderEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.Inventories;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.util.Hand;
import net.minecraft.world.World;

@SuppressWarnings("EntityConstructor")
public class BeeTaterEntity extends LilTaterEntity {

    public float size = 0;

    public BeeTaterEntity(EntityType<? extends BeeTaterEntity> type, World world) {
        super(type, world);
    }

    @Override
    public void fromTag(CompoundTag tag) {
        super.fromTag(tag);
    }

    @Override
    public CompoundTag toTag(CompoundTag tag) {
        return super.toTag(tag);
    }

    @Override
    protected void initAttributes() {
        super.initAttributes();
    }

    @Override
    public boolean tryAttack(Entity target) {
        return super.tryAttack(target);
    }

    @Override
    protected void initGoals() {
        super.initGoals();
    }

    @Override
    public PassiveEntity createChild(PassiveEntity mate) {
        return (PassiveEntity) getType().create(world);
    }

    public boolean interactMob(PlayerEntity player, Hand hand) {
        return super.interactMob(player, hand);
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
