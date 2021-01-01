package me.hydos.lint.sound;

import net.minecraft.client.sound.BiomeEffectSoundPlayer.MusicLoop;
import net.minecraft.sound.SoundEvent;

public class NotMusicLoop extends MusicLoop {
	public NotMusicLoop(SoundEvent sound) {
		super(sound);

		this.repeat = false;
	}
}
