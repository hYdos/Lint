package me.hydos.lint.entities.liltaterBattery;

import com.google.common.collect.ImmutableList;
import net.minecraft.client.model.ModelPart;
import net.minecraft.client.render.entity.model.CompositeEntityModel;

public class LilTaterBatteryModel extends CompositeEntityModel<LilTaterBattery> {
    private final ModelPart ramidzk;

    public LilTaterBatteryModel() {
        textureWidth = 32;
        textureHeight = 32;

        ramidzk = new ModelPart(this);
        ramidzk.setPivot(0.0F, 24.0F, 0.0F);
        ramidzk.addCuboid("ramidzk", -3.0F, -7.0F, -2.0F, 5, 7, 5, 0.0F, 0, 0);
    }

    @Override
    public void setAngles(LilTaterBattery entity, float limbAngle, float limbDistance, float customAngle, float headYaw, float headPitch) {
        ramidzk.yaw = headYaw;
    }

    @Override
    public Iterable<ModelPart> getParts() {
        return ImmutableList.of(ramidzk);
    }
}