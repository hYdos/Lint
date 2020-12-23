package io.github.hydos.mbb;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.font.TextRenderer;
import net.minecraft.client.gui.DrawableHelper;
import net.minecraft.client.util.math.MatrixStack;

@Environment(EnvType.CLIENT)
public class BossBarClientRenderer extends DrawableHelper {

    public static void render(MatrixStack stack, TextRenderer textRenderer, float tickDelta, boolean renderShadow){
        ClientModernBossBar bossBar = ClientModernBossBar.getInstance();
        if(bossBar == null || bossBar.endX == 0){
            return;
        }
        float scale = (float) MinecraftClient.getInstance().getWindow().getScaleFactor() / 4;
        int shadowOffsetX = 2;
        int shadowOffsetY = 2;
        stack.push();
        stack.scale(scale, scale, scale);
        stack.translate(240, 0, 0);
        drawCenteredText(stack, textRenderer, bossBar.title, 0, 16, 0xFFFFFFFF);
        if(renderShadow){
            fill(stack, -140 + shadowOffsetX, 30 + shadowOffsetY, 140 + shadowOffsetX, 34 + shadowOffsetY, 0x80000000);
        }
        fill(stack, -140, 30, 140, 34, 0x991E1E1E);
        fill(stack, -140, 30, bossBar.endX, 34, 0xFFE52320);
        stack.pop();
    }

}
