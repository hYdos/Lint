package me.hydos.lint.entity.passive.bird;

import me.hydos.lint.entity.Birds;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.passive.PassiveEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;
import software.bernie.geckolib3.core.IAnimatable;
import software.bernie.geckolib3.core.PlayState;
import software.bernie.geckolib3.core.builder.AnimationBuilder;
import software.bernie.geckolib3.core.event.predicate.AnimationEvent;

@SuppressWarnings("EntityConstructor")
public class RedTailedTropicBirdEntity extends AbstractBirdEntity{

	private static final AnimationBuilder FLY_ANIMATION = new AnimationBuilder().addAnimation("animation.red_tailed_tropicbird.fly", true);
	private static final AnimationBuilder IDLE_ANIMATION = new AnimationBuilder().addAnimation("animation.red_tailed_tropicbird.idle", true);

	public RedTailedTropicBirdEntity(EntityType<RedTailedTropicBirdEntity> entityType, World world) {
		super(entityType, world, Birds.RED_TAILED_TROPICBIRD_DATA);
	}

	@Override
	public <E extends IAnimatable> PlayState predicate(AnimationEvent<E> event) {
		event.getController().setAnimation(IDLE_ANIMATION);
		return PlayState.CONTINUE;
	}

	@Nullable
	@Override
	public PassiveEntity createChild(ServerWorld world, PassiveEntity entity) {
		return new RedTailedTropicBirdEntity(Birds.RED_TAILED_TROPICBIRD, world);
	}
}
