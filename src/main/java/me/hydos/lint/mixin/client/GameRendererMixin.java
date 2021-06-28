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

// TODO: make fog distance less dramatic (and scale it according to the user's max chunks)
//@Mixin(GameRenderer.class)
//public class GameRendererMixin {
//    @Shadow
//    @Final
//    private MinecraftClient client;
//
//    @Inject(at = @At("RETURN"), method = "getViewDistance", cancellable = true)
//    private void strengthenFog(CallbackInfoReturnable<Float> cir) {
//        final float originalResult = cir.getReturnValueF();
//        final float originalResultChunks = originalResult * 0.0625f; // originalResult / 16.0f
//
//        final World world = this.client.world;
//
//        if (world.getRegistryKey().equals(Dimensions.FRAIYA_WORLD)) { // Add dimension checks here for fog distance there
//            final Vec3d playerPos = this.client.player.getPos();
//            double x = playerPos.getX();
//            double z = playerPos.getZ();
//
//            final float modifiedResultChunks = Maths.calculateFogDistanceChunks(world, x, z, originalResultChunks);
//
//            float modifiedResult = modifiedResultChunks * 16.0f;
//
//            if (modifiedResult < originalResult) { // we don't want to mess with the player's max render distance
//                cir.setReturnValue(modifiedResult);
//            }
//        }
//    }
//}
