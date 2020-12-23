package io.github.hydos.lint.client.entity.render;

import io.github.hydos.lint.Lint;
import io.github.hydos.lint.client.entity.model.BeeTaterEntityModel;
import io.github.hydos.lint.entity.passive.BeeTaterEntity;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.EntityRenderDispatcher;
import net.minecraft.client.render.entity.MobEntityRenderer;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.Identifier;

public class BeeTaterEntityRenderer extends MobEntityRenderer<BeeTaterEntity, BeeTaterEntityModel> {

    public static final Identifier FRIENDLY_TATER = Lint.id("textures/entity/tiny_potato.png");

    public BeeTaterEntityRenderer(EntityRenderDispatcher dispatcher) {
        super(dispatcher, new BeeTaterEntityModel(), 0.4f);
    }

    @Override
    public void render(BeeTaterEntity mobEntity, float f, float g, MatrixStack matrixStack, VertexConsumerProvider vertexConsumerProvider, int i) {
        matrixStack.push();
        matrixStack.scale(1, 1, 1);
        super.render(mobEntity, f, g, matrixStack, vertexConsumerProvider, i);
        matrixStack.pop();
    }

    @Override
    public Identifier getTexture(BeeTaterEntity entity) {
        return FRIENDLY_TATER;
    }
}
