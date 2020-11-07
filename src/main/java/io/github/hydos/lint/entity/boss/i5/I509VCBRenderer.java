package io.github.hydos.lint.entity.boss.i5;

import io.github.hydos.lint.Lint;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.EnderDragonEntityRenderer;
import net.minecraft.client.render.entity.EntityRenderDispatcher;
import net.minecraft.client.render.entity.MobEntityRenderer;
import net.minecraft.client.render.model.json.ModelTransformation;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.Box;
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
    public void render(I509VCB cone, float f, float g, MatrixStack matrixStack, VertexConsumerProvider vertexConsumerProvider, int i) {
        matrixStack.push();
        float l = MathHelper.sin(((float) cone.age + g) / 10.0F + 3) * 0.5f;
        matrixStack.translate(0.0D, l + 0.25F, 0.0D);
        for (PlayerEntity player : cone.world.getEntitiesByClass(PlayerEntity.class, new Box(cone.getX() - 10, cone.getY() - 10, cone.getZ() - 10, cone.getX() + 10, cone.getY() + 10, cone.getZ() + 10), LivingEntity::isAlive)) {
            float x = (float) (player.getX() - cone.getX());
            float y = (float) (player.getY() - cone.getY()) - l - 0.5f;
            float z = (float) (player.getZ() - cone.getZ());
            EnderDragonEntityRenderer.renderCrystalBeam(x, y, z, f, cone.age, matrixStack, vertexConsumerProvider, i);
        }
        super.render(cone, f, g, matrixStack, vertexConsumerProvider, i);
        matrixStack.pop();
    }
}
