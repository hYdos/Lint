package io.github.hydos.lint.entity.tater;

import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.EntityRenderDispatcher;
import net.minecraft.client.render.entity.MobEntityRenderer;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.Identifier;

public class TinyPotatoEntityRenderer extends MobEntityRenderer<TinyPotatoEntity, TinyPotatoEntityModel> {

    public static final Identifier TINY_POTATO = new Identifier("lint:textures/entity/tiny_potato.png");
    public static final Identifier IRRITATER = new Identifier("lint:textures/entity/irritated.png");
    public static final Identifier CONCERN = new Identifier("lint:textures/entity/concern.png");

    public TinyPotatoEntityRenderer(EntityRenderDispatcher dispatcher) {
        super(dispatcher, new TinyPotatoEntityModel(), 0.4f);
    }

    @Override
    public void render(TinyPotatoEntity mobEntity, float f, float g, MatrixStack matrixStack, VertexConsumerProvider vertexConsumerProvider, int i) {
        matrixStack.push();
        matrixStack.scale(0.6f, 0.6f, 0.6f);
        super.render(mobEntity, f, g, matrixStack, vertexConsumerProvider, i);
        matrixStack.pop();
    }

    @Override
    public Identifier getTexture(TinyPotatoEntity entity) {
        return entity.isTamed() ? TINY_POTATO : IRRITATER;
    }
}
