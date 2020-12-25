package me.hydos.lint.entity.passive.bird;

import me.hydos.lint.entity.Birds;
import me.hydos.lint.sound.Sounds;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.Flutterer;
import net.minecraft.entity.ai.control.FlightMoveControl;
import net.minecraft.entity.ai.goal.*;
import net.minecraft.entity.ai.pathing.BirdNavigation;
import net.minecraft.entity.ai.pathing.EntityNavigation;
import net.minecraft.entity.attribute.DefaultAttributeContainer;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.entity.passive.PassiveEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundEvent;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;
import org.lwjgl.system.CallbackI;
import software.bernie.geckolib3.core.IAnimatable;
import software.bernie.geckolib3.core.PlayState;
import software.bernie.geckolib3.core.builder.AnimationBuilder;
import software.bernie.geckolib3.core.event.predicate.AnimationEvent;

@SuppressWarnings("EntityConstructor")
public class EasternRosellaEntity extends AbstractBirdEntity implements Flutterer  {

	private static final AnimationBuilder FLY_ANIMATION = new AnimationBuilder().addAnimation("animation.eastern_rosella.fly", true);
	private static final AnimationBuilder IDLE_ANiMATION = new AnimationBuilder().addAnimation("animation.eastern_rosella.idle", true);

	public EasternRosellaEntity(EntityType<EasternRosellaEntity> type, World world) {
		super(Birds.EASTERN_ROSELLA, world, Birds.EASTERN_ROSELLA_DATA);
		this.moveControl = new FlightMoveControl(this, 10, false);
	}

	public static DefaultAttributeContainer.Builder createBirdAttributes() {
		return createMobAttributes()
				.add(EntityAttributes.GENERIC_FLYING_SPEED, 0.4000000059604645D)
				.add(EntityAttributes.GENERIC_MOVEMENT_SPEED, 0.20000000298023224D);
	}

	@Override
	protected void initGoals() {
		this.goalSelector.add(0, new EscapeDangerGoal(this, 1.25D));
		this.goalSelector.add(0, new SwimGoal(this));
		this.goalSelector.add(1, new LookAtEntityGoal(this, PlayerEntity.class, 8.0F));
		this.goalSelector.add(2, new SitGoal(this));
		this.goalSelector.add(2, new FollowOwnerGoal(this, 1.0D, 5.0F, 1.0F, true));
		this.goalSelector.add(2, new FlyOntoTreeGoal(this, 1.0D));
	}

	@Nullable
	@Override
	protected SoundEvent getAmbientSound() {
		return Sounds.EASTERN_ROSELLA_IDLE;
	}

	public EntityNavigation createNavigation(World world) {
		BirdNavigation birdNavigation = new BirdNavigation(this, world);
		birdNavigation.setCanPathThroughDoors(false);
		birdNavigation.setCanSwim(true);
		birdNavigation.setCanEnterOpenDoors(true);
		return birdNavigation;
	}

	@Override
	public boolean handleFallDamage(float fallDistance, float damageMultiplier) {
		return false;
	}

	public <E extends IAnimatable> PlayState predicate(AnimationEvent<E> event) {
//		if (flying) {
//			event.getController().setAnimation(FLY_ANIMATION);
//		} else {
		event.getController().setAnimation(IDLE_ANiMATION);
//		}
		return PlayState.CONTINUE;
	}

	@Nullable
	@Override
	public PassiveEntity createChild(ServerWorld world, PassiveEntity entity) {
		return new EasternRosellaEntity(Birds.EASTERN_ROSELLA, world);
	}
}
