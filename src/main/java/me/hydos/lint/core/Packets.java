package me.hydos.lint.core;

import net.fabricmc.fabric.api.network.ServerSidePacketRegistry;
import net.minecraft.util.Identifier;

public interface Packets {

    Identifier PLAY_DIMENSION_CHANGE_PACKET_ID = new Identifier("lint", "change_dimension");

    static void onInitialize(){
        ServerSidePacketRegistry.INSTANCE.register(PLAY_DIMENSION_CHANGE_PACKET_ID, (packetContext, packetByteBuf) ->{
            String dimension = packetByteBuf.readString(65535);
            if(dimension.equalsIgnoreCase("HAYKAM")){
                packetContext.getTaskQueue().execute(() -> {
                    packetContext.getPlayer().changeDimension(Dimensions.HAYKAM);
                    packetContext.getPlayer().openContainer(null);

                });
            }
        });
    }

}
