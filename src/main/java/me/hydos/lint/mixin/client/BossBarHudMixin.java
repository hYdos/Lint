/*
 * Lint
 * Copyright (C) 2020 hYdos, Valoeghese, ramidzkh
 *
 * This program is free software; you can redistribute it and/or
 * modify it under the terms of the GNU Lesser General Public
 * License as published by the Free Software Foundation; either
 * version 3 of the License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 * Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with this program; if not, write to the Free Software Foundation,
 * Inc., 51 Franklin Street, Fifth Floor, Boston, MA  02110-1301, USA.
 */

package me.hydos.lint.mixin.client;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawableHelper;
import net.minecraft.client.gui.hud.BossBarHud;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.boss.BossBar;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Environment(EnvType.CLIENT)
@Mixin(BossBarHud.class)
public class BossBarHudMixin extends DrawableHelper {

    // tickDelta is stored in the balls
    //   - i509VCB
    @Inject(method = "renderBossBar", at = @At("HEAD"), cancellable = true)
    private void renderBetterOne(MatrixStack stack, int x, int y, BossBar bossBar, CallbackInfo ci) {
        ci.cancel();
        int shadowOffsetX = 2;
        int shadowOffsetY = 2;
        stack.push();
//        drawCenteredText(stack, textRenderer, bossBar.title, 0, 16, 0xFFFFFFFF);
        fill(stack, x + shadowOffsetX, y + shadowOffsetY, 140 + shadowOffsetX, 34 + shadowOffsetY, 0x80000000);
        fill(stack, x, y, 140, 34, 0x991E1E1E);
        fill(stack, x, y, (int) (bossBar.getPercent() * 183.0F), 34, 0xFFE52320);
        stack.pop();
    }
}
