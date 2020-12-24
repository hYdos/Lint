package me.hydos.lint.entity.aggressive;

import net.minecraft.entity.EntityType;
import net.minecraft.entity.ai.goal.*;
import net.minecraft.entity.ai.pathing.BirdNavigation;
import net.minecraft.entity.ai.pathing.EntityNavigation;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.mob.HostileEntity;
import net.minecraft.entity.mob.ZombifiedPiglinEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.sound.SoundEvent;
import net.minecraft.sound.SoundEvents;
import net.minecraft.world.World;

@SuppressWarnings({"EntityConstructor"})
public class GhostEntity extends HostileEntity {

	public GhostEntity(EntityType<GhostEntity> type, World world) {
		super(type, world);
		this.noClip = true;
	}

	@Override
	protected void initGoals() {
		this.goalSelector.add(2, new AttackGoal(this));
		this.goalSelector.add(7, new WanderAroundFarGoal(this, 1.0D));
		this.goalSelector.add(8, new LookAtEntityGoal(this, PlayerEntity.class, 8.0F));
		this.goalSelector.add(8, new LookAroundGoal(this));
		this.targetSelector.add(1, (new RevengeGoal(this)).setGroupRevenge(ZombifiedPiglinEntity.class));
		this.targetSelector.add(2, new FollowTargetGoal<>(this, PlayerEntity.class, true));
	}

	@Override
	public EntityNavigation createNavigation(World world) {
		BirdNavigation flyingNav = new BirdNavigation(this, world);
		flyingNav.setCanPathThroughDoors(true);
		flyingNav.setCanSwim(true);
		flyingNav.setSpeed(100);
		flyingNav.setCanEnterOpenDoors(true);
		return flyingNav;
	}

	@Override
	protected SoundEvent getHurtSound(DamageSource source) {
		return SoundEvents.ENTITY_PHANTOM_HURT;
	}

	@Override
	protected SoundEvent getAmbientSound() {
		return SoundEvents.ENTITY_PHANTOM_AMBIENT;
	}
}
