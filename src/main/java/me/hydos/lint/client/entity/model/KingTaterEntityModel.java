package me.hydos.lint.client.entity.model;

import com.google.common.collect.ImmutableList;
import me.hydos.lint.entity.aggressive.KingTaterEntity;
import net.minecraft.client.model.ModelPart;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.entity.model.CompositeEntityModel;
import net.minecraft.client.util.math.MatrixStack;

public class KingTaterEntityModel extends CompositeEntityModel<KingTaterEntity> {

    private final ModelPart model;

    public KingTaterEntityModel() {
        textureWidth = 96;
        textureHeight = 82;

        model = new ModelPart(this);
        model.setPivot(0.0F, 24.0F, 0.0F);
        model.addCuboid("model", -12.0F, -32.0F, -12.0F, 24, 32, 24, 0.0F, 0, 0);

        ModelPart crown = new ModelPart(this);
        crown.setPivot(0.0F, 0.0F, 0.0F);
        model.addChild(crown);
        crown.addCuboid("crown", -12.0F, -34.0F, -12.0F, 24, 2, 24, 0.0F, 0, 56);

        ModelPart things = new ModelPart(this);
        things.setPivot(0.0F, 0.0F, 0.0F);
        crown.addChild(things);

        ModelPart front = new ModelPart(this);
        front.setPivot(0.0F, 0.0F, 0.0F);
        things.addChild(front);
        front.addCuboid("front", 10.5F, -35.0F, -12.0F, 1, 1, 0, 0.0F, 8, 77);
        front.addCuboid("front", 8.5F, -35.0F, -12.0F, 1, 1, 0, 0.0F, 8, 77);
        front.addCuboid("front", 6.5F, -35.0F, -12.0F, 1, 1, 0, 0.0F, 8, 77);
        front.addCuboid("front", 4.5F, -35.0F, -12.0F, 1, 1, 0, 0.0F, 8, 77);
        front.addCuboid("front", 2.5F, -35.0F, -12.0F, 1, 1, 0, 0.0F, 8, 77);
        front.addCuboid("front", 0.5F, -35.0F, -12.0F, 1, 1, 0, 0.0F, 8, 77);
        front.addCuboid("front", -1.5F, -35.0F, -12.0F, 1, 1, 0, 0.0F, 8, 77);
        front.addCuboid("front", -3.5F, -35.0F, -12.0F, 1, 1, 0, 0.0F, 8, 77);
        front.addCuboid("front", -5.5F, -35.0F, -12.0F, 1, 1, 0, 0.0F, 8, 77);
        front.addCuboid("front", -7.5F, -35.0F, -12.0F, 1, 1, 0, 0.0F, 8, 77);
        front.addCuboid("front", -9.5F, -35.0F, -12.0F, 1, 1, 0, 0.0F, 8, 77);
        front.addCuboid("front", -11.5F, -35.0F, -12.0F, 1, 1, 0, 0.0F, 8, 77);


        ModelPart back = new ModelPart(this);
        back.setPivot(0.0F, 0.0F, 1.0F);
        things.addChild(back);
        back.addCuboid("back", 10.5F, -35.0F, 11.0F, 1, 1, 0, 0.0F, 8, 77);
        back.addCuboid("back", 8.5F, -35.0F, 11.0F, 1, 1, 0, 0.0F, 8, 77);
        back.addCuboid("back", 6.5F, -35.0F, 11.0F, 1, 1, 0, 0.0F, 8, 77);
        back.addCuboid("back", 4.5F, -35.0F, 11.0F, 1, 1, 0, 0.0F, 8, 77);
        back.addCuboid("back", 2.5F, -35.0F, 11.0F, 1, 1, 0, 0.0F, 8, 77);
        back.addCuboid("back", 0.5F, -35.0F, 11.0F, 1, 1, 0, 0.0F, 8, 77);
        back.addCuboid("back", -1.5F, -35.0F, 11.0F, 1, 1, 0, 0.0F, 8, 77);
        back.addCuboid("back", -3.5F, -35.0F, 11.0F, 1, 1, 0, 0.0F, 8, 77);
        back.addCuboid("back", -5.5F, -35.0F, 11.0F, 1, 1, 0, 0.0F, 8, 77);
        back.addCuboid("back", -7.5F, -35.0F, 11.0F, 1, 1, 0, 0.0F, 8, 77);
        back.addCuboid("back", -9.5F, -35.0F, 11.0F, 1, 1, 0, 0.0F, 8, 77);
        back.addCuboid("back", -11.5F, -35.0F, 11.0F, 1, 1, 0, 0.0F, 8, 77);

        ModelPart right = new ModelPart(this);
        right.setPivot(0.0F, 0.0F, 0.0F);
        things.addChild(right);
        right.addCuboid("right", 12.0F, -35.0F, -11.5F, 0, 1, 1, 0.0F, 31, 68);
        right.addCuboid("right", 12.0F, -35.0F, -9.5F, 0, 1, 1, 0.0F, 31, 68);
        right.addCuboid("right", 12.0F, -35.0F, -7.5F, 0, 1, 1, 0.0F, 31, 68);
        right.addCuboid("right", 12.0F, -35.0F, -5.5F, 0, 1, 1, 0.0F, 31, 68);
        right.addCuboid("right", 12.0F, -35.0F, -3.5F, 0, 1, 1, 0.0F, 31, 68);
        right.addCuboid("right", 12.0F, -35.0F, -1.5F, 0, 1, 1, 0.0F, 31, 68);
        right.addCuboid("right", 12.0F, -35.0F, 0.5F, 0, 1, 1, 0.0F, 31, 68);
        right.addCuboid("right", 12.0F, -35.0F, 2.5F, 0, 1, 1, 0.0F, 31, 68);
        right.addCuboid("right", 12.0F, -35.0F, 4.5F, 0, 1, 1, 0.0F, 31, 68);
        right.addCuboid("right", 12.0F, -35.0F, 6.5F, 0, 1, 1, 0.0F, 31, 68);
        right.addCuboid("right", 12.0F, -35.0F, 8.5F, 0, 1, 1, 0.0F, 31, 68);
        right.addCuboid("right", 12.0F, -35.0F, 10.5F, 0, 1, 1, 0.0F, 31, 68);

        ModelPart left = new ModelPart(this);
        left.setPivot(0.0F, 0.0F, 0.0F);
        things.addChild(left);
        left.addCuboid("left", -12.0F, -35.0F, -11.5F, 0, 1, 1, 0.0F, 31, 68);
        left.addCuboid("left", -12.0F, -35.0F, -9.5F, 0, 1, 1, 0.0F, 31, 68);
        left.addCuboid("left", -12.0F, -35.0F, -7.5F, 0, 1, 1, 0.0F, 31, 68);
        left.addCuboid("left", -12.0F, -35.0F, -5.5F, 0, 1, 1, 0.0F, 31, 68);
        left.addCuboid("left", -12.0F, -35.0F, -3.5F, 0, 1, 1, 0.0F, 31, 68);
        left.addCuboid("left", -12.0F, -35.0F, -1.5F, 0, 1, 1, 0.0F, 31, 68);
        left.addCuboid("left", -12.0F, -35.0F, 0.5F, 0, 1, 1, 0.0F, 31, 68);
        left.addCuboid("left", -12.0F, -35.0F, 2.5F, 0, 1, 1, 0.0F, 31, 68);
        left.addCuboid("left", -12.0F, -35.0F, 4.5F, 0, 1, 1, 0.0F, 31, 68);
        left.addCuboid("left", -12.0F, -35.0F, 6.5F, 0, 1, 1, 0.0F, 31, 68);
        left.addCuboid("left", -12.0F, -35.0F, 8.5F, 0, 1, 1, 0.0F, 31, 68);
        left.addCuboid("left", -12.0F, -35.0F, 10.5F, 0, 1, 1, 0.0F, 31, 68);
    }

    @Override
    public void render(MatrixStack matrices, VertexConsumer vertexConsumer, int light, int overlay, float red, float green, float blue, float alpha) {
        model.render(matrices, vertexConsumer, light, overlay, red, green, blue, alpha);
    }

    @Override
    public Iterable<ModelPart> getParts() {
        return ImmutableList.of(model);
    }

    @Override
    public void setAngles(KingTaterEntity entity, float limbAngle, float limbDistance, float customAngle, float headYaw, float headPitch) {
    }
}