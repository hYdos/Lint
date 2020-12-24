package me.hydos.lint.world.layer;

import me.hydos.lint.world.biome.Biomes;
import net.minecraft.util.registry.Registry;
import net.minecraft.util.registry.RegistryKey;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.layer.type.InitLayer;
import net.minecraft.world.biome.layer.util.LayerRandomnessSource;

public class GenericBiomes implements InitLayer {
    @SuppressWarnings("rawtypes")
    private static final RegistryKey[] biomes = {Biomes.MYSTICAL_FOREST_KEY, Biomes.CORRUPT_FOREST_KEY};

    private final Registry<Biome> biomeRegistry;

    public GenericBiomes(Registry<Biome> biomeRegistry) {
        this.biomeRegistry = biomeRegistry;
    }

    @Override
    public int sample(LayerRandomnessSource randomPawn, int x, int y) {
        if (x == 0 && y == 0) return id(Biomes.MYSTICAL_FOREST_KEY);
        return id(biomes[randomPawn.nextInt(biomes.length)]);
    }

    @SuppressWarnings({ "rawtypes", "unchecked" })
    private int id(RegistryKey key) {
        return biomeRegistry.getRawId(biomeRegistry.get(key));
    }
}