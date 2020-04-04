package me.hydos.lint.dimensions.haykam.biomes;

import com.google.common.collect.ImmutableSet;
import me.hydos.lint.dimensions.haykam.layers.BishopLayer;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.layer.ScaleLayer;
import net.minecraft.world.biome.layer.SmoothenShorelineLayer;
import net.minecraft.world.biome.layer.util.CachingLayerContext;
import net.minecraft.world.biome.layer.util.LayerFactory;
import net.minecraft.world.biome.layer.util.LayerSampleContext;
import net.minecraft.world.biome.layer.util.LayerSampler;
import net.minecraft.world.biome.source.BiomeLayerSampler;
import net.minecraft.world.biome.source.BiomeSource;

import java.util.function.LongFunction;

import static me.hydos.lint.core.Biomes.CORRUPT_FOREST;
import static me.hydos.lint.core.Biomes.MYSTICAL_FOREST;

public class HaykamBiomeSource extends BiomeSource {

    BiomeLayerSampler sampler;

    public HaykamBiomeSource(long seed) {
        super(ImmutableSet.of(MYSTICAL_FOREST, CORRUPT_FOREST));
        sampler = createBiomeLayerSampler(seed);
    }

    public static BiomeLayerSampler createBiomeLayerSampler(long seed) {
        LongFunction<CachingLayerContext> contextProvider = salt -> new CachingLayerContext(25, seed, salt);
        return new BiomeLayerSampler(stackLayers(contextProvider));

    }

    public static <T extends LayerSampler, C extends LayerSampleContext<T>> LayerFactory<T> stackLayers(LongFunction<C> contextProvider) {
        LayerFactory<T> result = BishopLayer.INSTANCE.create(contextProvider.apply(1L));
        for(int i = 0; i < 6; i++){
            result = ScaleLayer.NORMAL.create(contextProvider.apply(1000 + i), result);
        }
        result = SmoothenShorelineLayer.INSTANCE.create(contextProvider.apply(4L), result);
        return result;
    }

    @Override
    public Biome getBiomeForNoiseGen(int biomeX, int biomeY, int biomeZ) {
        return this.sampler.sample(biomeX, biomeZ);
    }
}
