package io.github.hydos.lint.mixin;

import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import me.hydos.lint.sound.Sounds;
import me.hydos.lint.world.dimension.Dimensions;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.sound.MusicTracker;
import net.minecraft.client.sound.MusicType;
import net.minecraft.client.sound.SoundInstance;
import net.minecraft.sound.MusicSound;

@Mixin(MusicTracker.class)
public abstract class SoundManagerMixin {

	@Shadow private @Nullable SoundInstance current;

	@Shadow @Final private MinecraftClient client;

	@Shadow public abstract void play(MusicSound type);

	@Shadow public abstract void stop();

	@Inject(method = "tick", at = @At("HEAD"), cancellable = true)
	private void tickButGood(CallbackInfo ci){
		MusicSound musicSound = this.client.getMusicType();

		if (this.current == null) {
			if (musicSound == Sounds.KING_TATER_LOOP || musicSound == Sounds.I509_LOOP || musicSound == Sounds.LEX_MANOS_LOOP) {
				this.stop();
				this.play(musicSound);
			}
		} else {
			if(current.getId().getNamespace().equals("lint")){
				ci.cancel();
			}
		}
	}
	
	@Inject(at = @At("HEAD"), method = "play", cancellable = true)	
	private void onPlay(MusicSound type, CallbackInfo info) {	
		if (type == MusicType.UNDERWATER || type == MusicType.GAME || type == MusicType.CREATIVE) {	
			if (this.client.world.getDimension() == Dimensions.HAYKAM) {	
				info.cancel();	
			}	
		}	
	}
}
