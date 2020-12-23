package io.github.hydos.lint.screenhandler;

import io.github.hydos.lint.Lint;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.container.ContainerProviderRegistry;
import net.minecraft.screen.ScreenHandlerType;
import net.minecraft.util.Identifier;

//TODO update api
public class Containers implements ModInitializer {

    public static final Identifier TATER_CONTAINER_ID = Lint.id("liltater");

    @Override
    public void onInitialize() {
        ContainerProviderRegistry.INSTANCE.registerFactory(Containers.TATER_CONTAINER_ID, (syncId, id, player, buf) -> new LilTaterInteractScreenHandler(ScreenHandlerType.ANVIL, syncId, buf.readInt(), player.inventory));
    }
}
