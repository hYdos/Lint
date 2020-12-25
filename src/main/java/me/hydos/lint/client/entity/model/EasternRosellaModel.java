package me.hydos.lint.client.entity.model;

import me.hydos.lint.Lint;
import me.hydos.lint.entity.passive.bird.AbstractBirdEntity;
import net.minecraft.util.Identifier;
import software.bernie.geckolib3.model.AnimatedGeoModel;

public class EasternRosellaModel extends AnimatedGeoModel<AbstractBirdEntity> {

	@Override
	public Identifier getModelLocation(AbstractBirdEntity abstractBirdEntity) {
		return Lint.id("geo/birds/eastern_rosella.geo.json");
	}

	@Override
	public Identifier getTextureLocation(AbstractBirdEntity abstractBirdEntity) {
		return Lint.id("textures/entity/birds/eastern_rosella.png");
	}

	@Override
	public Identifier getAnimationFileLocation(AbstractBirdEntity abstractBirdEntity) {
		return Lint.id("animations/birds/eastern_rosella.animation.json");
	}
}
