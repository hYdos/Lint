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
public class EasternRosellaEntity extends AbstractBirdEntity {

	private static final AnimationBuilder builder = new AnimationBuilder()
			.addAnimation("animation.eastern_rosella.fly", true)
			.addAnimation("animation.eastern_rosella.idle", true);

	public EasternRosellaEntity(EntityType<EasternRosellaEntity> type, World world) {
		super(Birds.EASTERN_ROSELLA, world, Birds.EASTERN_ROSELLA_DATA);
	}

	public <E extends IAnimatable> PlayState predicate(AnimationEvent<E> event) {
		event.getController().setAnimation(builder);
		return PlayState.CONTINUE;
	}

	@Nullable
	@Override
	public PassiveEntity createChild(ServerWorld world, PassiveEntity entity) {
		return new EasternRosellaEntity(Birds.EASTERN_ROSELLA, world);
	}
}
