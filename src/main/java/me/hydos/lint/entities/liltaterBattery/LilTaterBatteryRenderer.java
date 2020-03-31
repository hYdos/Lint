package me.hydos.lint.entities.liltaterBattery;

import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.EntityRenderDispatcher;
import net.minecraft.client.render.entity.EntityRenderer;
import net.minecraft.client.render.entity.MobEntityRenderer;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.Identifier;

public class LilTaterBatteryRenderer extends MobEntityRenderer<LilTaterBattery, LilTaterBatteryModel> {


    @Override
    public void render(LilTaterBattery mobEntity, float f, float g, MatrixStack matrixStack, VertexConsumerProvider vertexConsumerProvider, int i) {
        matrixStack.push();
        matrixStack.scale(6.9f,42.0f,6.9f);
        super.render(mobEntity, f, g, matrixStack, vertexConsumerProvider, i);
        matrixStack.pop();
    }

    public LilTaterBatteryRenderer(EntityRenderDispatcher dispatcher) {
        super(dispatcher, new LilTaterBatteryModel(), 0.4f);
    }

    @Override
    public Identifier getTexture(LilTaterBattery entity) {
        return new Identifier("lint:textures/block/lil_tater.png");
    }

}
