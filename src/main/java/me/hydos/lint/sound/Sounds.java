package me.hydos.lint.sound;

import io.github.hydos.lint.mixin.SoundEventAccessor;
import me.hydos.lint.Lint;
import net.minecraft.client.sound.MusicType;
import net.minecraft.sound.MusicSound;
import net.minecraft.sound.SoundEvent;
import net.minecraft.util.registry.Registry;

public class Sounds {

	/**
	 * Boss Music
	 */
	public static final SoundEvent KING_TATER = new SoundEvent(Lint.id("music.king_tater_boss_theme"));
	public static final SoundEvent I509 = new SoundEvent(Lint.id("music.i509_boss_theme"));
	public static final SoundEvent LEX_MANOS = new SoundEvent(Lint.id("music.lex_manos_boss_theme"));

	/**
	 * Misc Music
	 */
	public static final SoundEvent DUNGEON = new SoundEvent(Lint.id("music.clandestine"));
	public static final SoundEvent OBOE = new SoundEvent(Lint.id("music.oboe"));

	/**
	 * Biome Music
	 */
	public static final SoundEvent MYSTICAL_FOREST = new SoundEvent(Lint.id("music.mystical_forest"));
	public static final SoundEvent CORRUPT_FOREST = new SoundEvent(Lint.id("music.corrupt_forest"));

	/**
	 * Misc Sounds
	 */
	public static final SoundEvent ADVANCEMENT = new SoundEvent(Lint.id("player.advancement"));

	/**
	 * Boss Music Loops
	 */
	public static final MusicSound KING_TATER_LOOP = MusicType.createIngameMusic(KING_TATER);
	public static final MusicSound I509_LOOP = MusicType.createIngameMusic(I509);
	public static final MusicSound LEX_MANOS_LOOP = MusicType.createIngameMusic(LEX_MANOS);

	public static void register() {
		register(KING_TATER);
		register(I509);
		register(LEX_MANOS);
		register(MYSTICAL_FOREST);
		register(CORRUPT_FOREST);
	}

	private static void register(SoundEvent event) {
		Registry.register(Registry.SOUND_EVENT, ((SoundEventAccessor) event).getId(), event);
	}
}
