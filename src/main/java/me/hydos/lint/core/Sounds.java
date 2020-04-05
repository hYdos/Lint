package me.hydos.lint.core;

import net.minecraft.sound.SoundEvent;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

public interface Sounds {


    Identifier MY_SOUND_ID = new Identifier("lint:music.king_tater");

    SoundEvent KING_TATER = new SoundEvent(MY_SOUND_ID);

    static void onInitialize() {
        Registry.register(Registry.SOUND_EVENT, MY_SOUND_ID, KING_TATER);
    }
}
