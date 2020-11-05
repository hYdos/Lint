package io.github.hydos.lint.mixin;

import io.github.hydos.lint.sound.Sounds;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.sound.MusicTracker;
import net.minecraft.client.sound.SoundInstance;
import net.minecraft.sound.MusicSound;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(MusicTracker.class)
public abstract class SoundManagerMixin {

    @Shadow private @Nullable SoundInstance current;

    @Shadow @Final private MinecraftClient client;

    @Shadow public abstract void play(MusicSound type);

    @Shadow public abstract void stop();

    @Inject(method = "tick", at = @At("HEAD"), cancellable = true)
    private void tickButGood(CallbackInfo ci){
        MusicSound musicSound = this.client.getMusicType();
        if(this.current == null){
            if(musicSound == Sounds.KING_TATER_LOOP || musicSound == Sounds.I509_LOOP || musicSound == Sounds.LEX_MANOS_LOOP){
                this.stop();
                this.play(musicSound);
            }
        }else {
            if(current.getId().getNamespace().equals("lint")){
                ci.cancel();
            }
        }
    }

}
