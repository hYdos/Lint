package io.github.hydos.lint.entity.aggressive;

import net.minecraft.entity.EntityType;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.mob.ZombieEntity;
import net.minecraft.sound.SoundEvent;
import net.minecraft.sound.SoundEvents;
import net.minecraft.world.World;

@SuppressWarnings({"EntityConstructor"})
public class GhostEntity extends ZombieEntity {

	public GhostEntity(EntityType<GhostEntity> type, World world) {
		super(type, world);
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
