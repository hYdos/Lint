package me.hydos.lint.entity.aggressive;

import me.hydos.lint.sound.Sounds;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.goal.LookAroundGoal;
import net.minecraft.entity.ai.goal.LookAtEntityGoal;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.sound.SoundEvent;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;
import software.bernie.geckolib3.core.IAnimatable;
import software.bernie.geckolib3.core.PlayState;
import software.bernie.geckolib3.core.builder.AnimationBuilder;
import software.bernie.geckolib3.core.controller.AnimationController;
import software.bernie.geckolib3.core.event.predicate.AnimationEvent;
import software.bernie.geckolib3.core.manager.AnimationData;
import software.bernie.geckolib3.core.manager.AnimationFactory;

@SuppressWarnings("EntityConstructor")
public class CrabEntity extends MobEntity implements IAnimatable {

	private static final AnimationBuilder IDLE_ANIMATION = new AnimationBuilder().addAnimation("animation.crab.idle", true);

	private final AnimationFactory factory = new AnimationFactory(this);

	public CrabEntity(EntityType<? extends MobEntity> entityType, World world) {
		super(entityType, world);
	}

	@Override
	public void registerControllers(AnimationData data) {
		data.addAnimationController(new AnimationController<>(this, "controller", 0, this::predicate));
	}

	@Override
	protected void initGoals() {
		goalSelector.add(3, new LookAtEntityGoal(this, LivingEntity.class, 10));
		goalSelector.add(1, new LookAroundGoal(this));
	}

	@Nullable
	@Override
	protected SoundEvent getAmbientSound() {
		return Sounds.CRAB_IDLE;
	}

	private <P extends IAnimatable> PlayState predicate(AnimationEvent<P> event) {
		event.getController().setAnimation(IDLE_ANIMATION);
		return PlayState.CONTINUE;
	}

	@Override
	public AnimationFactory getFactory() {
		return factory;
	}
}
