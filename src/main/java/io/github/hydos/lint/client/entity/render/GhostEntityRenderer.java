package io.github.hydos.lint.client.entity.render;

import io.github.hydos.lint.Lint;
import io.github.hydos.lint.client.entity.model.GhostEntityModel;
import io.github.hydos.lint.entity.aggressive.GhostEntity;
import net.minecraft.client.render.entity.EntityRenderDispatcher;
import net.minecraft.client.render.entity.MobEntityRenderer;
import net.minecraft.util.Identifier;

public class GhostEntityRenderer extends MobEntityRenderer<GhostEntity, GhostEntityModel> {

	public static final Identifier TEXTURE = Lint.id("textures/entity/plain_ghost.png");

	public GhostEntityRenderer(EntityRenderDispatcher entityRenderDispatcher) {
		super(entityRenderDispatcher, new GhostEntityModel(), 0f);
	}

	@Override
	public Identifier getTexture(GhostEntity entity) {
		return TEXTURE;
	}
}
