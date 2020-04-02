package me.hydos.lint.entities.liltaterbattery;

import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.EntityRenderDispatcher;
import net.minecraft.client.render.entity.MobEntityRenderer;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.Identifier;

public class LilTaterBatteryRenderer extends MobEntityRenderer<LilTaterBattery, LilTaterBatteryModel> {


    public static final Identifier FRIENDLY_TATER = new Identifier("lint:textures/block/lil_tater.png");

    @Override
    public void render(LilTaterBattery mobEntity, float f, float g, MatrixStack matrixStack, VertexConsumerProvider vertexConsumerProvider, int i) {
        matrixStack.push();
        matrixStack.scale(1, 1, 1);
        super.render(mobEntity, f, g, matrixStack, vertexConsumerProvider, i);
        matrixStack.pop();
    }

    public LilTaterBatteryRenderer(EntityRenderDispatcher dispatcher) {
        super(dispatcher, new LilTaterBatteryModel(), 0.4f);
    }

    @Override
    public Identifier getTexture(LilTaterBattery entity) {
        if (!entity.isTamed()) {
            return new Identifier("lint:textures/block/lil_irritated.png");
        }
        return FRIENDLY_TATER;
    }

}
