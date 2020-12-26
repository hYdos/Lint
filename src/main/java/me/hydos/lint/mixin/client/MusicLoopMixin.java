package me.hydos.lint.mixin.client;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import me.hydos.lint.mixinimpl.SoundShitCache;
import net.minecraft.client.sound.BiomeEffectSoundPlayer;

@Mixin(BiomeEffectSoundPlayer.MusicLoop.class)
public class MusicLoopMixin {
	@Inject(
			at = @At(value = "HEAD"),
			method = "fadeIn",
			cancellable = true)
	private void onFadeIn(CallbackInfo info) {
		SoundShitCache.checkFade(info);
	}

	@Inject(
			at = @At(value = "HEAD"),
			method = "fadeOut",
			cancellable = true)
	private void onFadeOut(CallbackInfo info) {
		SoundShitCache.checkFade(info);
	}
}
