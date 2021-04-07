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

import me.hydos.lint.sound.LintSoundManager;
import net.minecraft.client.sound.AbstractSoundInstance;
import net.minecraft.client.sound.BiomeEffectSoundPlayer;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvent;
import net.minecraft.sound.SoundEvents;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(BiomeEffectSoundPlayer.MusicLoop.class)
public class MusicLoopMixin extends AbstractSoundInstance {
    public MusicLoopMixin() {
        super(SoundEvents.AMBIENT_CAVE, SoundCategory.MUSIC); // doesnt get run. shut up the compiler.
    }

    @Inject(at = @At("RETURN"), method = "<init>")
    private void onConstruction(SoundEvent sound, CallbackInfo info) {
        if (sound.getId().toString().startsWith("lint:music")) {
            this.category = SoundCategory.MUSIC;
        }
    }

    @Inject(
            at = @At(value = "HEAD"),
            method = "fadeIn",
            cancellable = true)
    private void onFadeIn(CallbackInfo info) {
        LintSoundManager.checkFade(info);
    }

    @Inject(
            at = @At(value = "HEAD"),
            method = "fadeOut",
            cancellable = true)
    private void onFadeOut(CallbackInfo info) {
        LintSoundManager.checkFade(info);
    }
}
