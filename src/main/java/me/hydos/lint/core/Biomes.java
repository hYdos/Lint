package me.hydos.lint.core;

import me.hydos.lint.dimensions.haykam.biomes.CorruptForest;
import me.hydos.lint.dimensions.haykam.biomes.MysticalForest;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.biome.Biome;

public interface Biomes {

    Biome MYSTICAL_FOREST = Registry.register(Registry.BIOME, new Identifier("lint", "mystical_forest"), new MysticalForest());
    Biome CORRUPT_FOREST = Registry.register(Registry.BIOME, new Identifier("lint", "corrupt_forest"), new CorruptForest());

    static void onInitialize() {
    }
}
