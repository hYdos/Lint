package io.github.hydos.lint.client.entity.model;

import io.github.hydos.lint.entity.aggressive.I509VCBEntity;
import net.minecraft.client.model.ModelPart;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.entity.model.EntityModel;
import net.minecraft.client.util.math.MatrixStack;

public class I509VCBEntityModel extends EntityModel<I509VCBEntity> {
	private final ModelPart cone;

	public I509VCBEntityModel() {
		textureWidth = 64;
		textureHeight = 64;

		cone = new ModelPart(this);
		cone.setPivot(0.0F, 24.0F, 0.0F);
		cone.setTextureOffset(0, 0).addCuboid(-8.0F, -1.0F, -8.0F, 16.0F, 1.0F, 16.0F, 0.0F, false);
		cone.setTextureOffset(0, 17).addCuboid(-7.0F, -5.0F, -7.0F, 14.0F, 4.0F, 14.0F, 0.0F, false);
		cone.setTextureOffset(0, 36).addCuboid(-6.0F, -9.0F, -6.0F, 12.0F, 4.0F, 12.0F, 0.0F, false);
		cone.setTextureOffset(4, 6).addCuboid(-5.0F, -13.0F, -5.0F, 10.0F, 4.0F, 10.0F, 0.0F, false);
		cone.setTextureOffset(32, 50).addCuboid(-4.0F, -18.0F, -4.0F, 8.0F, 6.0F, 8.0F, 0.0F, false);
		cone.setTextureOffset(0, 0).addCuboid(-3.0F, -15.0F, -3.0F, 6.0F, 3.0F, 6.0F, 0.0F, false);
		cone.setTextureOffset(40, 0).addCuboid(-3.0F, -22.0F, -3.0F, 6.0F, 6.0F, 6.0F, 0.0F, false);
		cone.setTextureOffset(24, 8).addCuboid(-2.0F, -27.0F, -2.0F, 4.0F, 7.0F, 4.0F, 0.0F, false);
		cone.setTextureOffset(56, 11).addCuboid(-1.0F, -31.0F, -1.0F, 2.0F, 7.0F, 2.0F, 0.0F, false);
	}

	@Override
	public void setAngles(I509VCBEntity entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch){
		cone.roll = (float) (Math.PI);
	}

    @Override
    public void render(MatrixStack matrices, VertexConsumer vertices, int light, int overlay, float red, float green, float blue, float alpha) {
		matrices.translate(0, -3, 0);
		cone.render(matrices, vertices, light, overlay, red, green, blue, alpha);
    }
}