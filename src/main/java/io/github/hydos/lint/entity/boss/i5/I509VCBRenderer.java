package io.github.hydos.lint.entity.boss.i5;

import io.github.hydos.lint.Lint;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.EntityRenderDispatcher;
import net.minecraft.client.render.entity.MobEntityRenderer;
import net.minecraft.client.render.model.json.ModelTransformation;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.MathHelper;

public class I509VCBRenderer extends MobEntityRenderer<I509VCB, I509VCBModel> {

    public static final Identifier SKIN = Lint.id("textures/entity/i509vcb.png");

    public I509VCBRenderer(EntityRenderDispatcher dispatcher) {
        super(dispatcher, new I509VCBModel(), 1);
    }

    @Override
    public Identifier getTexture(I509VCB entity) {
        return SKIN;
    }

    @Override
    public void render(I509VCB mobEntity, float f, float g, MatrixStack matrixStack, VertexConsumerProvider vertexConsumerProvider, int i) {
        matrixStack.push();
        float l = MathHelper.sin(((float)mobEntity.age + g) / 10.0F + 3) * 0.5f;
        matrixStack.translate(0.0D, l + 0.25F, 0.0D);
        super.render(mobEntity, f, g, matrixStack, vertexConsumerProvider, i);
        matrixStack.pop();
    }
}
