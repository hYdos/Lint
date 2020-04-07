package me.hydos.lint.entities.tater;

import com.google.common.collect.ImmutableList;
import net.minecraft.client.model.ModelPart;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.entity.model.CompositeEntityModel;
import net.minecraft.client.util.math.MatrixStack;

public class LilTaterEntityModel extends CompositeEntityModel<LilTaterEntity> {

    private final ModelPart model;

    public LilTaterEntityModel() {
        textureWidth = 32;
        textureHeight = 32;

        model = new ModelPart(this);
        model.setPivot(0.0F, 24.0F, 0.0F);
        model.addCuboid("model", -3.0F, -7.0F, -2.0F, 5, 7, 5, 0.0F, 0, 0);
    }

    @Override
    public void setAngles(LilTaterEntity entity, float limbAngle, float limbDistance, float customAngle, float headYaw, float headPitch) {
        model.yaw = headYaw / 100;
        model.pitch = headPitch / 100;
    }

    @Override
    public Iterable<ModelPart> getParts() {
        return ImmutableList.of(model);
    }

    public void renderOnShoulder(MatrixStack matrixStack, VertexConsumer vertexConsumer, int i, int j) {
        this.getParts().forEach((modelPart) -> modelPart.render(matrixStack, vertexConsumer, i, j));
    }
}