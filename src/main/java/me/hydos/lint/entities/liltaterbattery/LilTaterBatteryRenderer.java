package me.hydos.lint.entities.liltaterbattery;

import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.EntityRenderDispatcher;
import net.minecraft.client.render.entity.MobEntityRenderer;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.Identifier;

public class LilTaterBatteryRenderer extends MobEntityRenderer<LilTaterBattery, LilTaterBatteryModel> {

    public static final Identifier FRIENDLY_TATER = new Identifier("lint:textures/entity/lil_tater.png");
    public static final Identifier IRRITATER = new Identifier("lint:textures/entity/lil_irritated.png");

    public LilTaterBatteryRenderer(EntityRenderDispatcher dispatcher) {
        super(dispatcher, new LilTaterBatteryModel(), 0.4f);
    }

    @Override
    public void render(LilTaterBattery mobEntity, float f, float g, MatrixStack matrixStack, VertexConsumerProvider vertexConsumerProvider, int i) {
        matrixStack.push();
        matrixStack.scale(1, 1, 1);
        super.render(mobEntity, f, g, matrixStack, vertexConsumerProvider, i);
        matrixStack.pop();
    }

    @Override
    public Identifier getTexture(LilTaterBattery entity) {
        return entity.isTamed() ? FRIENDLY_TATER : IRRITATER;
    }
}
