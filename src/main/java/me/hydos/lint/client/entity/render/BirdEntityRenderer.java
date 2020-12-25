package me.hydos.lint.client.entity.render;

import net.minecraft.client.render.entity.EntityRenderDispatcher;
import software.bernie.geckolib3.model.AnimatedGeoModel;
import software.bernie.geckolib3.renderer.geo.GeoEntityRenderer;

public class BirdEntityRenderer extends GeoEntityRenderer {

	public BirdEntityRenderer(EntityRenderDispatcher dispatcher, AnimatedGeoModel<?> model) {
		super(dispatcher, model);
	}
}
