package me.hydos.lint.mixin;

import me.hydos.lint.core.Lint;
import net.minecraft.client.sound.SoundInstance;
import net.minecraft.client.sound.SoundSystem;
import net.minecraft.sound.SoundCategory;
import net.minecraft.util.Identifier;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(SoundSystem.class)
public class SoundSystemMixin {

    @Inject(at = @At("HEAD"), method = "play(Lnet/minecraft/client/sound/SoundInstance;)V", cancellable = true)
    public void frickoffvanilla(SoundInstance soundInstance, CallbackInfo ci){
        Identifier soundID = soundInstance.getId();
        if("minecraft:music.creative".equals(soundID.toString()) && Lint.BossManager.isPlayingMusic){
            ci.cancel();
        }
    }

}
