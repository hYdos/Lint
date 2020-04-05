package me.hydos.lint.core;

import com.chocohead.mm.api.ClassTinkerers;
import net.fabricmc.loader.api.FabricLoader;
import net.fabricmc.loader.api.MappingResolver;

public interface PreInitialize {

    static void onInitialize() {
        MappingResolver remapper = FabricLoader.getInstance().getMappingResolver();

        ClassTinkerers.enumBuilder(
                remapper.mapClassName("intermediary", "net.minecraft.class_1142$class_1143"),
                "L" + remapper.mapClassName("intermediary", "net.minecraft.class_3414") + ";",
                int.class, int.class)
                .addEnum("KING_TATER", () -> new Object[] {Sounds.KING_TATER, 0, 0})
                .build();
    }
}
