package me.hydos.lint.boss;

import net.fabricmc.api.EnvType;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.client.MinecraftClient;

public class BossManager {
    public boolean isPlayingMusic = true;

    public void tick(){
        if(FabricLoader.getInstance().getEnvironmentType() == EnvType.CLIENT){
//            MinecraftClient.getInstance().getSoundManager().
        }
    }

}
