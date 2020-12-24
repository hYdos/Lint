package me.hydos.lint.entity.passive;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.ai.pathing.BirdNavigation;
import net.minecraft.entity.ai.pathing.EntityNavigation;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.world.World;

@SuppressWarnings("EntityConstructor")
public class BeeTaterEntity extends TinyPotatoEntity {

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
    public boolean tryAttack(Entity target) {
        return super.tryAttack(target);
    }

	public ActionResult interactMob(PlayerEntity player, Hand hand) {
        return super.interactMob(player, hand);
    }

	@Override
    protected EntityNavigation createNavigation(World world) {
        return new BirdNavigation(this, world);
    }
}
