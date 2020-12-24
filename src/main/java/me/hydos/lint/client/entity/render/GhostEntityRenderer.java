package me.hydos.lint.client.entity.render;

import me.hydos.lint.Lint;import me.hydos.lint.client.entity.model.GhostEntityModel;
import me.hydos.lint.entity.aggressive.GhostEntity;
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
