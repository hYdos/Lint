package me.hydos.lint.registers;

import me.hydos.lint.dimensions.haykam.biomes.MysticalForest;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.biome.Biome;

public class BiomeRegister {

    public static Biome MYSTICAL_FOREST;

    public static void registerBiomes(){
        MYSTICAL_FOREST = Registry.register(Registry.BIOME, new Identifier("lint", "mystical_forest"), new MysticalForest());
    }

}
