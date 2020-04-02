package me.hydos.lint.core.client;

import me.hydos.lint.containers.LilTaterContainerScreen;
import me.hydos.lint.containers.LilTaterInteractContainer;
import me.hydos.lint.core.Lint;
import me.hydos.lint.entities.liltaterbattery.LilTaterBatteryRenderer;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.client.rendereregistry.v1.EntityRendererRegistry;
import net.fabricmc.fabric.api.client.screen.ScreenProviderRegistry;
import net.minecraft.text.LiteralText;
import net.minecraft.util.Identifier;

@Environment(EnvType.CLIENT)
public class LintClient implements ClientModInitializer {



    @Override
    public void onInitializeClient() {
        EntityRendererRegistry.INSTANCE.register(Lint.LIL_TATER, (entityRenderDispatcher, context) -> new LilTaterBatteryRenderer(entityRenderDispatcher));

        ScreenProviderRegistry.INSTANCE.registerFactory(new Identifier("lint", "liltater"), (syncId, identifier, playerEntity, buf) -> new LilTaterContainerScreen(new LilTaterInteractContainer(null, syncId, buf.readInt(), playerEntity.inventory), playerEntity.inventory, new LiteralText("Lil Tater UI")));

    }
}
