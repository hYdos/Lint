package me.hydos.lint.entities.boss;

import me.hydos.lint.entities.liltaterbattery.LilTaterBattery;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.mob.Monster;
import net.minecraft.entity.passive.PassiveEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Hand;
import net.minecraft.world.World;

import java.util.UUID;

@SuppressWarnings("EntityConstructor")
public class TaterMinion extends LilTaterBattery implements Monster {

    public TaterMinion(EntityType<? extends TaterMinion> type, World world) {
        super(type, world);
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
}
