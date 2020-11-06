package io.github.hydos.mbb;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.font.TextRenderer;
import net.minecraft.client.gui.DrawableHelper;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;

import java.util.ArrayList;
import java.util.List;

public class ModernBossBar extends DrawableHelper {
    public static final List<ModernBossBar> instancedBossBars = new ArrayList<>();
    public Text title;
    public int colour;
    private int percentage;

    public ModernBossBar(Text title, int colour) {
        this.title = title;
        this.colour = colour;
        instancedBossBars.add(this);
    }

    public void render(MatrixStack stack, TextRenderer textRenderer, float tickDelta, boolean renderShadow) {
        if(percentage == 0){
            return;
        }
        float scale = (float) MinecraftClient.getInstance().getWindow().getScaleFactor() / 4;
        int shadowOffsetX = 2;
        int shadowOffsetY = 2;
        stack.push();
        stack.scale(scale, scale, scale);
        stack.translate(240, 0, 0);
        drawCenteredText(stack, textRenderer, title, 0, 16, 0xFFFFFFFF);
        if(renderShadow){
            fill(stack, -140 + shadowOffsetX, 30 + shadowOffsetY, 140 + shadowOffsetX, 34 + shadowOffsetY, 0x80000000);
        }
        fill(stack, -140, 30, 140, 34, 0x991E1E1E);
        fill(stack, -140, 30, percentage, 34, 0xFFE52320);
        stack.pop();
    }

    public void render(MatrixStack stack, TextRenderer textRenderer, float tickDelta) {
        render(stack, textRenderer, tickDelta, true);
    }

    public void setPercent(float scaledHealth) {
        this.percentage = (int) (140 * scaledHealth);
    }
}
