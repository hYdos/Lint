package me.hydos.lint.client.entity.model;

import me.hydos.lint.entity.passive.BeeTaterEntity;
import net.minecraft.client.model.ModelPart;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.entity.model.EntityModel;
import net.minecraft.client.util.math.MatrixStack;

public class BeeTaterEntityModel extends EntityModel<BeeTaterEntity> {

    private final ModelPart body;
    private final ModelPart left_wing;
    private final ModelPart right_wing;
    private final ModelPart leg1;
    private final ModelPart leg2;
    private final ModelPart leg3;

    public BeeTaterEntityModel() {
        textureWidth = 64;
        textureHeight = 64;

        body = new ModelPart(this);
        body.setPivot(0.0F, 0.0F, 0.0F);
        body.setTextureOffset(0, 0).addCuboid(-4.0F, -2.0F, -8.0F, 7.0F, 7.0F, 10.0F, 0.0F, false);
        body.setTextureOffset(3, 3).addCuboid(2.0F, -2.0F, -11.0F, 0.0F, 2.0F, 3.0F, 0.0F, false);
        body.setTextureOffset(2, 0).addCuboid(-3.0F, -2.0F, -11.0F, 0.0F, 2.0F, 3.0F, 0.0F, false);
        body.setTextureOffset(3, 1).addCuboid(-0.5F, 1.0F, 2.0F, 0.0F, 1.0F, 2.0F, 0.0F, false);

        left_wing = new ModelPart(this);
        left_wing.setPivot(-2.0F, -2.0F, -8.0F);
        left_wing.setTextureOffset(9, 24).addCuboid(3.0F, 0.0F, 2.0F, 7.0F, 0.0F, 6.0F, 0.0F, false);

        right_wing = new ModelPart(this);
        right_wing.setPivot(3.0F, -2.0F, -8.0F);
        right_wing.setTextureOffset(9, 24).addCuboid(-13.0F, 0.0F, 2.0F, 7.0F, 0.0F, 6.0F, 0.0F, true);

        leg1 = new ModelPart(this);
        leg1.setPivot(0.0F, 24.0F, 0.0F);
        leg1.setTextureOffset(28, 1).addCuboid(-2.0F, -19.0F, -5.0F, 3.0F, 2.0F, 0.0F, 0.0F, false);

        leg2 = new ModelPart(this);
        leg2.setPivot(0.0F, 24.0F, 0.0F);
        leg2.setTextureOffset(27, 3).addCuboid(-3.0F, -19.0F, -3.0F, 5.0F, 2.0F, 0.0F, 0.0F, false);

        leg3 = new ModelPart(this);
        leg3.setPivot(0.0F, 24.0F, 0.0F);
        leg3.setTextureOffset(27, 5).addCuboid(-3.0F, -19.0F, -1.0F, 5.0F, 2.0F, 0.0F, 0.0F, false);
    }

    @Override
    public void setAngles(BeeTaterEntity entity, float limbAngle, float limbDistance, float customAngle, float headYaw, float headPitch) {

    }


    @Override
    public void render(MatrixStack matrixStack, VertexConsumer buffer, int packedLight, int packedOverlay, float red, float green, float blue, float alpha) {
        body.render(matrixStack, buffer, packedLight, packedOverlay);
        left_wing.render(matrixStack, buffer, packedLight, packedOverlay);
        right_wing.render(matrixStack, buffer, packedLight, packedOverlay);
        leg1.render(matrixStack, buffer, packedLight, packedOverlay);
        leg2.render(matrixStack, buffer, packedLight, packedOverlay);
        leg3.render(matrixStack, buffer, packedLight, packedOverlay);
    }


    public void setRotationAngle(ModelPart modelRenderer, float x, float y, float z) {
        modelRenderer.pitch = x;
        modelRenderer.yaw = y;
        modelRenderer.roll = z;
    }
}