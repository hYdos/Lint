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

import me.hydos.lint.util.math.Maths;
import net.minecraft.client.render.BackgroundRenderer;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.biome.source.BiomeAccess;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(BackgroundRenderer.class)
public class BackgroundRendererMixin {
    @Inject(
            at = @At("HEAD"),
            method = "method_24873(Lnet/minecraft/client/world/ClientWorld;Lnet/minecraft/world/biome/source/BiomeAccess;FIII)Lnet/minecraft/util/math/Vec3d;",
            cancellable = true
    )
    private static void onRetrieveModifiedFogColour(ClientWorld world, BiomeAccess access, float skyAngleThing,
                                                    int noiseGenX, int noiseGenY, int noiseGenZ, CallbackInfoReturnable<Vec3d> info) {
        Maths.onRetrieveModifiedFogColor(world, access, skyAngleThing, noiseGenX, noiseGenY, noiseGenZ, info);
    }
}
