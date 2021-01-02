package me.hydos.lint.mixin;

import me.hydos.lint.mixinimpl.LintSoundEvent;
import net.minecraft.sound.SoundEvent;
import net.minecraft.util.Identifier;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;

@Mixin(SoundEvent.class)
public class SoundEventMixin implements LintSoundEvent {
	@Shadow @Final private Identifier id;

	@Override
	public Identifier getCommonId() {
		return this.id;
	}
}
