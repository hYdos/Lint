package me.hydos.lint.client.entity.model;

import com.google.common.collect.ImmutableList;
import me.hydos.lint.entity.passive.TinyPotatoEntity;
import net.minecraft.client.model.ModelPart;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.entity.model.CompositeEntityModel;
import net.minecraft.client.util.math.MatrixStack;

public class TinyPotatoEntityModel extends CompositeEntityModel<TinyPotatoEntity> {

    private final ModelPart model;

    public TinyPotatoEntityModel() {
        textureWidth = 32;
        textureHeight = 32;
        model = new ModelPart(this);
        model.setPivot(0.0F, 24.0F, 0.0F);
        model.setTextureOffset(0, 0).addCuboid(-4.0F, -12.0F, -4.0F, 8.0F, 12.0F, 8.0F, 0.0F, false);
    }

    @Override
    public void setAngles(TinyPotatoEntity entity, float limbAngle, float limbDistance, float customAngle, float headYaw, float headPitch) {
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