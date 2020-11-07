package io.github.hydos.mbb;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.text.Text;

@Environment(EnvType.CLIENT)
public class ClientModernBossBar {
    private static ClientModernBossBar instance;

    public Text title;
    public int colour;

    public int endX;

    public ClientModernBossBar(Text title, int colour, int endX) {
        this.title = title;
        this.colour = colour;
        instance = this;
        this.endX = endX;
    }

    public static ClientModernBossBar getInstance() {
        return instance;
    }
}
