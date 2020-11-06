package io.github.hydos.lint.entity.tater;

import net.minecraft.client.model.ModelPart;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.entity.model.EntityModel;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.Entity;

public class ImpersataterModel extends EntityModel<Entity> {
    private final ModelPart bb_main;

    public ImpersataterModel() {
        textureWidth = 32;
        textureHeight = 32;
        bb_main = new ModelPart(this);
        bb_main.setPivot(0.0F, 24.0F, 0.0F);
        bb_main.setTextureOffset(0, 0).addCuboid(-4.0F, -12.0F, -4.0F, 8.0F, 12.0F, 8.0F, 0.0F, false);
        bb_main.setTextureOffset(32, 32).addCuboid(-5.0F, -8.0F, 1.0F, 1.0F, 1.0F, -6.0F, 0.0F, false);
        bb_main.setTextureOffset(32, 32).addCuboid(4.0F, -8.0F, 1.0F, 1.0F, 1.0F, -6.0F, 0.0F, false);
        bb_main.setTextureOffset(0, 23).addCuboid(-5.0F, -11.0F, -6.0F, 10.0F, 8.0F, 1.0F, 0.0F, false);
    }

    @Override
    public void setAngles(Entity entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
        //previously the render function, render code was moved to a method below
    }

    @Override
    public void render(MatrixStack matrixStack, VertexConsumer buffer, int packedLight, int packedOverlay, float red, float green, float blue, float alpha) {
        bb_main.render(matrixStack, buffer, packedLight, packedOverlay);
    }

    public void setRotationAngle(ModelPart bone, float x, float y, float z) {
        bone.pitch = x;
        bone.yaw = y;
        bone.roll = z;
    }
}