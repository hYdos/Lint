package io.github.hydos.lint.sound;

import io.github.hydos.lint.Lint;
import net.minecraft.client.sound.MusicType;
import net.minecraft.sound.MusicSound;
import net.minecraft.sound.SoundEvent;

public class Sounds {

    public static final SoundEvent KING_TATER = new SoundEvent(Lint.id("king_tater_boss_theme"));
    public static final SoundEvent I509 = new SoundEvent(Lint.id("i509_boss_theme"));
    public static final SoundEvent LEX_MANOS = new SoundEvent(Lint.id("lex_manos_boss_theme"));


    public static final MusicSound KING_TATER_LOOP = MusicType.createIngameMusic(KING_TATER);
    public static final MusicSound I509_LOOP = MusicType.createIngameMusic(I509);
    public static final MusicSound LEX_MANOS_LOOP = MusicType.createIngameMusic(LEX_MANOS);
}
