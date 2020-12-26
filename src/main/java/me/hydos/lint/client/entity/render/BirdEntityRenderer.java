package me.hydos.lint.client.entity.render;

import me.hydos.lint.entity.passive.bird.AbstractBirdEntity;
import net.minecraft.client.render.entity.EntityRenderDispatcher;
import software.bernie.geckolib3.model.AnimatedGeoModel;
import software.bernie.geckolib3.renderer.geo.GeoEntityRenderer;

public class BirdEntityRenderer extends GeoEntityRenderer<AbstractBirdEntity> {

	public BirdEntityRenderer(EntityRenderDispatcher dispatcher, AnimatedGeoModel<AbstractBirdEntity> model) {
		super(dispatcher, model);
	}
}
