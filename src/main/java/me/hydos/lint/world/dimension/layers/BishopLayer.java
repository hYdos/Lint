package me.hydos.lint.world.dimension.layers;

import net.minecraft.util.registry.Registry;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.layer.type.InitLayer;
import net.minecraft.world.biome.layer.util.LayerRandomnessSource;

import static me.hydos.lint.core.Biomes.CORRUPT_FOREST;
import static me.hydos.lint.core.Biomes.MYSTICAL_FOREST;

public enum BishopLayer implements InitLayer {
    INSTANCE;

    private static final Biome[] biomes = {MYSTICAL_FOREST, CORRUPT_FOREST};

    @Override
    public int sample(LayerRandomnessSource randomPawn, int x, int y) {
        return Registry.BIOME.getRawId(biomes[randomPawn.nextInt(biomes.length)]);
    }
}
