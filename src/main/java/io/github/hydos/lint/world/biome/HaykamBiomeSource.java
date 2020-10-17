package io.github.hydos.lint.world.biome;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import io.github.hydos.lint.world.layer.BishopLayer;
import me.hydos.lint.util.OpenSimplexNoise;
import net.minecraft.util.registry.BuiltinRegistries;
import net.minecraft.util.registry.Registry;
import net.minecraft.util.registry.RegistryKey;
import net.minecraft.util.registry.RegistryLookupCodec;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.layer.ScaleLayer;
import net.minecraft.world.biome.layer.SmoothenShorelineLayer;
import net.minecraft.world.biome.layer.util.CachingLayerContext;
import net.minecraft.world.biome.layer.util.LayerFactory;
import net.minecraft.world.biome.layer.util.LayerSampleContext;
import net.minecraft.world.biome.layer.util.LayerSampler;
import net.minecraft.world.biome.source.BiomeLayerSampler;
import net.minecraft.world.biome.source.BiomeSource;

import java.util.Random;
import java.util.function.LongFunction;
import java.util.stream.Collectors;

public class HaykamBiomeSource extends BiomeSource {

    private static BishopLayer bishopLayer;
    private final Registry<Biome> biomeRegistry;
    private long seed;
    private OpenSimplexNoise noise;

    public static final Codec<HaykamBiomeSource> CODEC = RecordCodecBuilder.create(instance -> instance.group(
            RegistryLookupCodec.of(Registry.BIOME_KEY).forGetter(source -> source.biomeRegistry),
            Codec.LONG.fieldOf("seed").stable().forGetter(source -> source.seed))
            .apply(instance, instance.stable(HaykamBiomeSource::new)));

    BiomeLayerSampler sampler;

    public HaykamBiomeSource(Registry<Biome> biomeRegistry, long seed) {
        super(biomeRegistry.stream().collect(Collectors.toList()));
        this.seed = seed;
        bishopLayer = new BishopLayer(biomeRegistry);
        sampler = createBiomeLayerSampler(seed);
        this.noise = new OpenSimplexNoise(new Random(seed));
        this.biomeRegistry = biomeRegistry;
    }

    public static BiomeLayerSampler createBiomeLayerSampler(long seed) {
        LongFunction<CachingLayerContext> contextProvider = salt -> new CachingLayerContext(25, seed, salt);
        return new BiomeLayerSampler(stackLayers(contextProvider));

    }

    public static <T extends LayerSampler, C extends LayerSampleContext<T>> LayerFactory<T> stackLayers(LongFunction<C> contextProvider) {
        LayerFactory<T> result = bishopLayer.create(contextProvider.apply(1L));
        for (int i = 0; i < 6; i++) {
            result = ScaleLayer.NORMAL.create(contextProvider.apply(1000 + i), result);
        }
        result = SmoothenShorelineLayer.INSTANCE.create(contextProvider.apply(4L), result);
        return result;
    }

    @Override
    public Biome getBiomeForNoiseGen(int biomeX, int biomeY, int biomeZ) {
        if (this.noise.sample(biomeX * 0.05, biomeZ * 0.05) > 0.15) {
            return biomeRegistry.getOrThrow(RegistryKey.of(Registry.BIOME_KEY, BuiltinRegistries.BIOME.getKey(Biomes.CORRUPT_FOREST).get().getValue()));
        } else {
            return biomeRegistry.getOrThrow(RegistryKey.of(Registry.BIOME_KEY, BuiltinRegistries.BIOME.getKey(Biomes.MYSTICAL_FOREST).get().getValue()));
        }
    }

    @Override
    protected Codec<? extends BiomeSource> getCodec() {
        return CODEC;
    }

    @Override
    public BiomeSource withSeed(long seed) {
        return new HaykamBiomeSource(biomeRegistry, seed);
    }
}