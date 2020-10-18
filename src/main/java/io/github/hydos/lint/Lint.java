package io.github.hydos.lint;

import io.github.hydos.lint.world.biome.Biomes;
import net.fabricmc.api.ModInitializer;
import net.minecraft.util.Identifier;

public class Lint implements ModInitializer {
    public static final String MODID = "lint";

    public static Identifier id(String path) {
        return new Identifier(MODID, path);
    }

    public void onInitialize() {
    }
}
