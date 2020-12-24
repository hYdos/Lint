package me.hydos.lint.world.biome;

import java.util.function.LongFunction;
import java.util.stream.Collectors;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;

import me.hydos.lint.world.layer.GenericBiomes;
import me.hydos.lint.world.layer.MountainBiomes;
import net.minecraft.util.registry.Registry;
import net.minecraft.util.registry.RegistryLookupCodec;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.layer.ScaleLayer;
import net.minecraft.world.biome.layer.SmoothLayer;
import net.minecraft.world.biome.layer.type.InitLayer;
import net.minecraft.world.biome.layer.util.CachingLayerContext;
import net.minecraft.world.biome.layer.util.LayerFactory;
import net.minecraft.world.biome.layer.util.LayerSampleContext;
import net.minecraft.world.biome.layer.util.LayerSampler;
import net.minecraft.world.biome.source.BiomeLayerSampler;
import net.minecraft.world.biome.source.BiomeSource;

public class HaykamBiomeSource extends BiomeSource {
    public static final Codec<HaykamBiomeSource> CODEC = RecordCodecBuilder.create(instance -> instance.group(
            RegistryLookupCodec.of(Registry.BIOME_KEY).forGetter(source -> source.biomeRegistry),
            Codec.LONG.fieldOf("seed").stable().forGetter(source -> source.seed))
            .apply(instance, instance.stable(HaykamBiomeSource::new)));

    private final Registry<Biome> biomeRegistry;
    private final long seed;

    private final GenericBiomes genericBiomes;
    private final MountainBiomes mountainBiomes;

    private final BiomeLayerSampler genericSampler;
    private final BiomeLayerSampler mountainSampler;
    
    private TerrainData data;

    public HaykamBiomeSource(Registry<Biome> biomeRegistry, long seed) {
        super(biomeRegistry.stream().collect(Collectors.toList()));
        this.seed = seed;
        this.biomeRegistry = biomeRegistry;

        this.genericBiomes = new GenericBiomes(biomeRegistry);
        this.genericSampler = createBiomeLayerSampler(this.genericBiomes, seed);

        this.mountainBiomes = new MountainBiomes(biomeRegistry);
        this.mountainSampler = createBiomeLayerSampler(this.mountainBiomes, seed);
    }

    public void setTerrainData(TerrainData data) {
        this.data = data;
    }

    public static BiomeLayerSampler createBiomeLayerSampler(InitLayer biomes, long seed) {
        LongFunction<CachingLayerContext> contextProvider = salt -> new CachingLayerContext(25, seed, salt);
        return new BiomeLayerSampler(stackLayers(biomes, contextProvider));

    }

    public static <T extends LayerSampler, C extends LayerSampleContext<T>> LayerFactory<T> stackLayers(InitLayer biomes, LongFunction<C> contextProvider) {
        LayerFactory<T> result = biomes.create(contextProvider.apply(1L));
        for (int i = 0; i < 6; i++) {
            result = ScaleLayer.NORMAL.create(contextProvider.apply(1000 + i), result);
        }
        result = SmoothLayer.INSTANCE.create(contextProvider.apply(4L), result);
        return result;
    }

    @Override
    public Biome getBiomeForNoiseGen(int biomeX, int biomeY, int biomeZ) {
        int x = (biomeX << 2);
        int z = (biomeZ << 2);
        double scale = this.data.sampleTerrainScale(x, z);

        return (scale > 40.0 ? this.mountainSampler : this.genericSampler).sample(this.biomeRegistry, biomeX, biomeZ);
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