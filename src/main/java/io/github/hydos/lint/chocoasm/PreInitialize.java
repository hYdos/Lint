package io.github.hydos.lint.chocoasm;

import com.chocohead.mm.api.ClassTinkerers;
import io.github.hydos.lint.sound.Sounds;
import net.fabricmc.api.EnvType;
import net.fabricmc.loader.api.FabricLoader;
import net.fabricmc.loader.api.MappingResolver;

public interface PreInitialize {

    static void initialize() {
        if (FabricLoader.getInstance().getEnvironmentType() == EnvType.CLIENT) {
            initializeClient();
        }
    }

    static void initializeClient() {
        MappingResolver remapper = FabricLoader.getInstance().getMappingResolver();

        String musicType = remapper.mapClassName("intermediary", "net.minecraft.class_1142$class_1143");
        String soundEvent = remapper.mapClassName("intermediary", "net.minecraft.class_3414");

        ClassTinkerers.enumBuilder(musicType, 'L' + soundEvent + ';', "I", "I")
                .addEnum("KING_TATER", () -> new Object[]{Sounds.KING_TATER, 0, 0})
                .build();
    }
}
