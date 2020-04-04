package me.hydos.lint.core;

import me.hydos.lint.containers.LilTaterInteractContainer;
import net.fabricmc.fabric.api.container.ContainerProviderRegistry;
import net.minecraft.container.ContainerType;
import net.minecraft.util.Identifier;

public interface Containers {

    Identifier TATER_CONTAINER_ID = new Identifier("lint", "liltater");

    static void onInitialize() {
        ContainerProviderRegistry.INSTANCE.registerFactory(Containers.TATER_CONTAINER_ID, (syncId, id, player, buf) -> new LilTaterInteractContainer(ContainerType.ANVIL, syncId, buf.readInt(), player.inventory));
    }
}
