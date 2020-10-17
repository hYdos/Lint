package io.github.hydos.lint.layer;

import io.github.hydos.lint.biome.Biomes;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.layer.type.InitLayer;
import net.minecraft.world.biome.layer.util.LayerRandomnessSource;

public class BishopLayer implements InitLayer {

    private static final Biome[] biomes = {Biomes.MYSTICAL_FOREST, Biomes.CORRUPT_FOREST};

    private final Registry<Biome> biomeRegistry;

    public BishopLayer(Registry<Biome> biomeRegistry) {
        this.biomeRegistry = biomeRegistry;
    }

    @Override
    public int sample(LayerRandomnessSource randomPawn, int x, int y) {
        return biomeRegistry.getRawId(biomes[randomPawn.nextInt(biomes.length)]);
    }
}