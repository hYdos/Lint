package me.hydos.lint;

import me.hydos.lint.entities.liltaterbattery.LilTaterBatteryRenderer;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.client.rendereregistry.v1.EntityRendererRegistry;

@Environment(EnvType.CLIENT)
public class LintClient implements ClientModInitializer {



    @Override
    public void onInitializeClient() {
        EntityRendererRegistry.INSTANCE.register(Lint.LIL_TATER, (entityRenderDispatcher, context) -> new LilTaterBatteryRenderer(entityRenderDispatcher));
    }
}
