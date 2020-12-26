package me.hydos.lint.mixinimpl;

import java.util.Optional;

import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import net.minecraft.sound.SoundEvent;

public class SoundShitCache {
	public static Optional<SoundEvent> prev = Optional.empty();
	public static Optional<SoundEvent> next = Optional.empty();

	public static void checkFade(CallbackInfo info) {
		if (SoundShitCache.prev.isPresent() && SoundShitCache.next.isPresent()) {
			if (SoundShitCache.prev.get().getId().equals(SoundShitCache.next.get().getId())) {
				info.cancel();
			}
		}
	}
}
