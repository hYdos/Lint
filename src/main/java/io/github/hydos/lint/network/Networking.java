package io.github.hydos.lint.network;

import io.github.hydos.lint.Lint;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.network.ServerSidePacketRegistry;
import net.minecraft.util.Identifier;

public class Networking implements ModInitializer {

    public static final Identifier SEND_BOSSBAR_INFO = Lint.id("send_bossbar_info");

    @Override
    public void onInitialize() {

    }
}
