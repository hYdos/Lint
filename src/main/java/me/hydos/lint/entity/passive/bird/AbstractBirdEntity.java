package me.hydos.lint.entity.passive.bird;

import net.minecraft.entity.EntityType;
import net.minecraft.entity.passive.TameableShoulderEntity;
import net.minecraft.sound.SoundEvent;
import net.minecraft.world.World;
import software.bernie.geckolib3.core.IAnimatable;
import software.bernie.geckolib3.core.PlayState;
import software.bernie.geckolib3.core.controller.AnimationController;
import software.bernie.geckolib3.core.event.predicate.AnimationEvent;
import software.bernie.geckolib3.core.manager.AnimationData;
import software.bernie.geckolib3.core.manager.AnimationFactory;

@SuppressWarnings("EntityConstructor")
public abstract class AbstractBirdEntity extends TameableShoulderEntity implements IAnimatable {

	private final AnimationFactory factory = new AnimationFactory(this);
	private final BirdData birdData;

	protected AbstractBirdEntity(EntityType<? extends TameableShoulderEntity> entityType, World world, BirdData birdData) {
		super(entityType, world);
		this.birdData = birdData;
	}

	@Override
	public void registerControllers(AnimationData data) {
		data.addAnimationController(new AnimationController<>(this, "controller", 0, this::predicate));
	}

	public abstract <E extends IAnimatable> PlayState predicate(AnimationEvent<E> event);

	@Override
	public AnimationFactory getFactory() {
		return factory;
	}

	public static class BirdData {

		public SoundEvent sound;
		public String name;
		public String description;
		//TODO: give birds more information

		public BirdData(SoundEvent sound, String name, String description) {
			this.sound = sound;
			this.name = name;
			this.description = description;
		}
	}
}
