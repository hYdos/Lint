package io.github.hydos.lint.biome;

import com.google.common.collect.ImmutableList;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import io.github.hydos.lint.layer.BishopLayer;
import io.github.hydos.lint.mixin.DimensionTypeAccessor;
import net.minecraft.util.registry.Registry;
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

import java.util.function.LongFunction;

public class HaykamBiomeSource extends BiomeSource {

    private static BishopLayer bishopLayer;
    private final Registry<Biome> biomeRegistry;
    private final long seed;

    public static final Codec<HaykamBiomeSource> CODEC = RecordCodecBuilder.create(instance -> instance.group(
            RegistryLookupCodec.of(Registry.BIOME_KEY).forGetter(source -> source.biomeRegistry),
            Codec.LONG.fieldOf("seed").stable().forGetter(source -> source.seed))
            .apply(instance, instance.stable(HaykamBiomeSource::new)));

    BiomeLayerSampler sampler;

    public HaykamBiomeSource(Registry<Biome> biomeRegistry, long seed) {
        super(ImmutableList.of());
        this.seed = seed;
        bishopLayer = new BishopLayer(biomeRegistry);
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
        return this.sampler.sample(biomeRegistry, biomeX, biomeY);
    }

    @Override
    protected Codec<? extends BiomeSource> getCodec() {
        return CODEC;
    }

    @Override
    public BiomeSource withSeed(long seed) {
        sampler = createBiomeLayerSampler(seed);
        return this;
    }
}