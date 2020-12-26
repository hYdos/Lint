package me.hydos.lint.network;

import me.hydos.modernbossbars.ClientModernBossBar;
import me.hydos.modernbossbars.ModernBossBar;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.minecraft.text.Text;

public class ClientNetworking {

    public static void register() {
        ClientPlayNetworking.registerGlobalReceiver(Networking.SEND_BOSSBAR_INFO, (minecraftClient, clientPlayNetworkHandler, buf, packetSender) -> {
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
