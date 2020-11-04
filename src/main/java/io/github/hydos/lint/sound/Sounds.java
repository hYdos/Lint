package io.github.hydos.lint.sound;

import io.github.hydos.lint.Lint;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.sound.MusicType;
import net.minecraft.sound.MusicSound;
import net.minecraft.sound.SoundEvent;
import net.minecraft.util.registry.Registry;

public class Sounds {

    public static final SoundEvent KING_TATER = new SoundEvent(Lint.id("king_tater_boss_theme"));
    public static final SoundEvent I509 = new SoundEvent(Lint.id("i509_boss_theme"));
    public static final SoundEvent LEX_MANOS = new SoundEvent(Lint.id("lex_manos_boss_theme"));

    public static final SoundEvent MYSTICAL_FOREST = new SoundEvent(Lint.id("mystical_forest"));

    public static final MusicSound KING_TATER_LOOP = MusicType.createIngameMusic(KING_TATER);
    public static final MusicSound I509_LOOP = MusicType.createIngameMusic(I509);
    public static final MusicSound LEX_MANOS_LOOP = MusicType.createIngameMusic(LEX_MANOS);

    public static void register(){
        Registry.register(Registry.SOUND_EVENT, KING_TATER.getId(), KING_TATER);
        Registry.register(Registry.SOUND_EVENT, I509.getId(), I509);
        Registry.register(Registry.SOUND_EVENT, LEX_MANOS.getId(), LEX_MANOS);
        Registry.register(Registry.SOUND_EVENT, MYSTICAL_FOREST.getId(), MYSTICAL_FOREST);
    }
}
