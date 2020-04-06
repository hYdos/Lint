package me.hydos.lint.core;

import com.chocohead.mm.api.ClassTinkerers;
import net.fabricmc.api.EnvType;
import net.fabricmc.loader.api.FabricLoader;
import net.fabricmc.loader.api.MappingResolver;

public interface PreInitialize {

    static void onInitialize() {
        if (FabricLoader.getInstance().getEnvironmentType() == EnvType.CLIENT) {
            MappingResolver remapper = FabricLoader.getInstance().getMappingResolver();

            System.out.println(remapper.mapClassName("intermediary", "net.minecraft.class_1142$class_1143"));
            ClassTinkerers.enumBuilder(
                    remapper.mapClassName("intermediary", "net.minecraft.class_1142$class_1143"),
                    "L" + remapper.mapClassName("intermediary", "net.minecraft.class_3414") + ";",
                    "I", "I")
                    .addEnum("KING_TATER", () -> new Object[] {Sounds.KING_TATER, 0, 0})
                    .build();
        }
    }
}
