package me.hydos.lint.world.layer;

import me.hydos.lint.world.biome.Biomes;
import net.minecraft.util.registry.Registry;
import net.minecraft.util.registry.RegistryKey;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.layer.type.InitLayer;
import net.minecraft.world.biome.layer.util.LayerRandomnessSource;

public class BishopLayer implements InitLayer {

    private static final RegistryKey[] biomes = {Biomes.MYSTICAL_FOREST_KEY, Biomes.CORRUPT_FOREST_KEY};

    private final Registry<Biome> biomeRegistry;

    public BishopLayer(Registry<Biome> biomeRegistry) {
        this.biomeRegistry = biomeRegistry;
    }

    @Override
    public int sample(LayerRandomnessSource randomPawn, int x, int y) {
        return biomeRegistry.getRawId(biomeRegistry.get(biomes[randomPawn.nextInt(biomes.length)]));
    }
}