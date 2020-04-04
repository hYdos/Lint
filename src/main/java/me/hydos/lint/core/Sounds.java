package me.hydos.lint.core;

import net.minecraft.sound.SoundEvent;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

public interface Sounds {

    SoundEvent KING_TATER = new SoundEvent(new Identifier("lint", "king_tater_boss"));

    static void onInitialize() {
        Registry.register(Registry.SOUND_EVENT, KING_TATER.getId(), KING_TATER);
    }
}
