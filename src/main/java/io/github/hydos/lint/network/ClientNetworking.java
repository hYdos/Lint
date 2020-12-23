package io.github.hydos.lint.network;

import io.github.hydos.mbb.ClientModernBossBar;
import io.github.hydos.mbb.ModernBossBar;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.network.ClientSidePacketRegistry;
import net.minecraft.text.Text;

public class ClientNetworking implements ClientModInitializer {

    @Override
    public void onInitializeClient() {
        ClientSidePacketRegistry.INSTANCE.register(Networking.SEND_BOSSBAR_INFO, (packetContext, buf) -> {
            ModernBossBar.PacketType type = buf.readEnumConstant(ModernBossBar.PacketType.class);
            switch (type) {
                case NEW:
                    Text title = buf.readText();
                    int colour = buf.readInt();
                    int endX = buf.readInt();
                    new ClientModernBossBar(title, colour, endX);
                    break;
                case SET_VALUE:
                    ClientModernBossBar.getInstance().endX = buf.readInt();
                    break;
                case SET_TITLE:
                    ClientModernBossBar.getInstance().title = buf.readText();
            }
        });
    }
}
