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

// TODO: add video option that changes the bossbar from modern to default
// why don't we just use a normal bossbar hydos
//@Mixin(InGameHud.class)
//public class InGameHudMixin {
//
//    @Shadow
//    @Final
//    private MinecraftClient client;
//
//    // tickDelta is stored in the balls
//    //   - i509VCB
//    @Inject(method = "render", at = @At(value = "TAIL"))
//    private void renderModernBossBar(MatrixStack matrices, float tickDelta, CallbackInfo ci) {
//        BossBarClientRenderer.render(matrices, client.textRenderer, tickDelta, true);
//    }
//}
