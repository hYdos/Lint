package me.hydos.lint.entity.boss;

import net.minecraft.client.render.entity.EntityRenderDispatcher;
import net.minecraft.client.render.entity.MobEntityRenderer;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.Identifier;

public class KingTaterRenderer extends MobEntityRenderer<KingTater, KingTaterModel> {

    private static final Identifier SKIN = new Identifier("lint", "textures/entity/tater_king.png");

    public KingTaterRenderer(EntityRenderDispatcher renderManager) {
        super(renderManager, new KingTaterModel(), 1);
    }

    @Override
    public Identifier getTexture(KingTater entity) {
        return SKIN;
    }

    @Override
    protected void scale(KingTater entity, MatrixStack matrices, float tickDelta) {
        float scale = Math.max(entity.getScaledHealth(), 0.125F);
        matrices.scale(scale, scale, scale);
    }
}
