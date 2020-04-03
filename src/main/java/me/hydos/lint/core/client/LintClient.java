package me.hydos.lint.core.client;

import me.hydos.lint.containers.client.LilTaterContainerScreen;
import me.hydos.lint.containers.LilTaterInteractContainer;
import me.hydos.lint.core.Containers;
import me.hydos.lint.core.Entities;
import me.hydos.lint.entities.liltaterbattery.LilTaterBatteryRenderer;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.client.rendereregistry.v1.EntityRendererRegistry;
import net.fabricmc.fabric.api.client.screen.ScreenProviderRegistry;
import net.minecraft.text.LiteralText;

@Environment(EnvType.CLIENT)
public class LintClient implements ClientModInitializer {



    @Override
    public void onInitializeClient() {
        EntityRendererRegistry.INSTANCE.register(Entities.LIL_TATER, (entityRenderDispatcher, context) -> new LilTaterBatteryRenderer(entityRenderDispatcher));

        ScreenProviderRegistry.INSTANCE.registerFactory(Containers.TATER_CONTAINER_ID, (syncId, identifier, playerEntity, buf) -> new LilTaterContainerScreen(new LilTaterInteractContainer(null, syncId, buf.readInt(), playerEntity.inventory), playerEntity.inventory, new LiteralText("Lil Tater UI")));

    }
}
