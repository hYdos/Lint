package me.hydos.lint.item;

import net.minecraft.item.Item;
import net.minecraft.item.MusicDiscItem;
import net.minecraft.sound.SoundEvent;

public class LintMusicDiscItem extends MusicDiscItem {

	public LintMusicDiscItem(int comparatorOutput, SoundEvent sound, Settings settings) {
		super(comparatorOutput, sound, settings);
	}
}
