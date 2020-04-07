package me.hydos.lint.entity.tater;

import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.EntityRenderDispatcher;
import net.minecraft.client.render.entity.MobEntityRenderer;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.Identifier;

public class LilTaterEntityRenderer extends MobEntityRenderer<LilTaterEntity, LilTaterEntityModel> {

    public static final Identifier FRIENDLY_TATER = new Identifier("lint:textures/entity/lil_tater.png");
    public static final Identifier IRRITATER = new Identifier("lint:textures/entity/lil_irritated.png");

    public LilTaterEntityRenderer(EntityRenderDispatcher dispatcher) {
        super(dispatcher, new LilTaterEntityModel(), 0.4f);
    }

    @Override
    public void render(LilTaterEntity mobEntity, float f, float g, MatrixStack matrixStack, VertexConsumerProvider vertexConsumerProvider, int i) {
        matrixStack.push();
        matrixStack.scale(1, 1, 1);
        super.render(mobEntity, f, g, matrixStack, vertexConsumerProvider, i);
        matrixStack.pop();
    }

    @Override
    public Identifier getTexture(LilTaterEntity entity) {
        return entity.isTamed() ? FRIENDLY_TATER : IRRITATER;
    }
}
