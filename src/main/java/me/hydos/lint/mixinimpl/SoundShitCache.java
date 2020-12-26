package me.hydos.lint.mixinimpl;

import java.util.Optional;

import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import net.minecraft.sound.SoundEvent;

public class SoundShitCache {
	public static Optional<SoundEvent> prev = Optional.empty();
	public static Optional<SoundEvent> next = Optional.empty();

	public static void checkFade(CallbackInfo info) {
		if (prev.isPresent() && next.isPresent()) {
			if (prev.get().getId().equals(next.get().getId())) {
				info.cancel();
			}
		}
	}
}
