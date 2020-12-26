package me.hydos.lint.client.entity.render;

import me.hydos.lint.Lint;
import me.hydos.lint.client.entity.model.KingTaterEntityModel;
import me.hydos.lint.entity.aggressive.KingTaterEntity;
import net.minecraft.client.render.entity.EntityRenderDispatcher;
import net.minecraft.client.render.entity.MobEntityRenderer;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.Identifier;

public class KingTaterRenderer extends MobEntityRenderer<KingTaterEntity, KingTaterEntityModel> {

    private static final Identifier SKIN = Lint.id("textures/entity/tater_king.png");

    public KingTaterRenderer(EntityRenderDispatcher renderManager) {
        super(renderManager, new KingTaterEntityModel(), 1);
    }

    @Override
    public Identifier getTexture(KingTaterEntity entity) {
        return SKIN;
    }

    @Override
    protected void scale(KingTaterEntity entity, MatrixStack matrices, float tickDelta) {
        float scale = Math.max(KingTaterEntity.getScaledHealth(entity.getHealth(), entity.getMaxHealth()), 0.125F);
        matrices.scale(scale, scale, scale);
    }
}
