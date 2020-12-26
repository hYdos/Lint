package me.hydos.lint.mixinimpl;

import java.util.Optional;

import net.minecraft.sound.SoundEvent;

public class SoundShitCache {
	public static Optional<SoundEvent> prev = Optional.empty();
	public static Optional<SoundEvent> next = Optional.empty();
}
