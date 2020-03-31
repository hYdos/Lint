package me.hydos.lint.entities.liltaterBattery;

import net.minecraft.client.render.entity.EntityRenderDispatcher;
import net.minecraft.client.render.entity.EntityRenderer;
import net.minecraft.client.render.entity.MobEntityRenderer;
import net.minecraft.util.Identifier;

public class LilTaterBatteryRenderer extends MobEntityRenderer<LilTaterBattery, LilTaterBatteryModel> {

    public LilTaterBatteryRenderer(EntityRenderDispatcher dispatcher) {
        super(dispatcher, new LilTaterBatteryModel(), 0.4f);
    }

    @Override
    public Identifier getTexture(LilTaterBattery entity) {
        return new Identifier("lint:textures/block/lil_tater.png");
    }

}
