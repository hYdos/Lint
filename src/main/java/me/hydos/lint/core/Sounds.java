package me.hydos.lint.core;

import net.minecraft.sound.SoundEvent;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

public interface Sounds {


    Identifier KING_TATER_SOUND_ID = new Identifier("lint:music.king_tater");

    SoundEvent KING_TATER = new SoundEvent(KING_TATER_SOUND_ID);

    static void onInitialize() {
        Registry.register(Registry.SOUND_EVENT, KING_TATER_SOUND_ID, KING_TATER);
    }
}
