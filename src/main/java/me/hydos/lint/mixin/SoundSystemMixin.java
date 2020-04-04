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

    @Inject(at = @At("HEAD"), method = "stop()V")
    public void a(CallbackInfo ci){
        System.out.println("A");
    }

    @Inject(at = @At("HEAD"), method = "stop(Lnet/minecraft/client/sound/SoundInstance;)V")
    public void b(SoundInstance soundInstance, CallbackInfo ci){
        System.out.println("B " + soundInstance.getId());
    }

    @Inject(at = @At("HEAD"), method = "stopSounds")
    public void c(Identifier identifier, SoundCategory soundCategory, CallbackInfo ci){
        System.out.println("C " + identifier + " " + soundCategory);
    }

}
