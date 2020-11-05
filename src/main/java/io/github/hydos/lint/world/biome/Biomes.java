package io.github.hydos.lint.world.biome;

import io.github.hydos.lint.Lint;
import io.github.hydos.lint.resource.block.Blocks;
import io.github.hydos.lint.entity.Entities;
import io.github.hydos.lint.sound.Sounds;
import io.github.hydos.lint.world.feature.Features;
import io.github.hydos.lint.world.gen.HaykamChunkGenerator;
import io.github.hydos.lint.world.structure.ConfiguredStructureFeatures;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.mixin.biome.BuiltinBiomesAccessor;
import net.minecraft.entity.SpawnGroup;
import net.minecraft.util.registry.BuiltinRegistries;
import net.minecraft.util.registry.Registry;
import net.minecraft.util.registry.RegistryKey;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.BiomeEffects;
import net.minecraft.world.biome.GenerationSettings;
import net.minecraft.world.biome.SpawnSettings;
import net.minecraft.world.gen.GenerationStep;
import net.minecraft.world.gen.surfacebuilder.ConfiguredSurfaceBuilder;
import net.minecraft.world.gen.surfacebuilder.SurfaceBuilder;
import net.minecraft.world.gen.surfacebuilder.TernarySurfaceConfig;

public class Biomes implements ModInitializer {

    public static final int CORRUPT_FOG_COLOUR = 0x916ec1;
    public static final Biome CORRUPT_FOREST;
    public static final Biome MYSTICAL_FOREST;
    public static final RegistryKey<Biome> MYSTICAL_FOREST_KEY = RegistryKey.of(Registry.BIOME_KEY, Lint.id("mystical_forest"));
    public static final RegistryKey<Biome> CORRUPT_FOREST_KEY = RegistryKey.of(Registry.BIOME_KEY, Lint.id("corrupt_forest"));
    private static final ConfiguredSurfaceBuilder<TernarySurfaceConfig> TESTING = SurfaceBuilder.DEFAULT.withConfig(new TernarySurfaceConfig(Blocks.CORRUPT_LEAVES.getDefaultState(), Blocks.CORRUPT_LEAVES.getDefaultState(), Blocks.CORRUPT_LEAVES.getDefaultState()));

    static {
        SpawnSettings.Builder spawningSettings = new SpawnSettings.Builder();
        spawningSettings.spawn(SpawnGroup.CREATURE, new SpawnSettings.SpawnEntry(Entities.LIL_TATER, 12, 1, 3));
        MYSTICAL_FOREST = new Biome.Builder()
                .precipitation(Biome.Precipitation.RAIN)
                .category(Biome.Category.NONE)
                .depth(0.125f)
                .scale(1)
                .temperature(0.8f)
                .downfall(1)
                .effects(new BiomeEffects.Builder()
                        .waterColor(0)
                        .waterColor(0x3f76e4)
                        .waterFogColor(0x050533)
                        .fogColor(0xc0d8ff)
                        .loopSound(Sounds.MYSTICAL_FOREST)
                        .skyColor(0x77adff)
                        .build())
                .spawnSettings(spawningSettings.build())
                .generationSettings(new GenerationSettings.Builder()
                        .surfaceBuilder(TESTING)
                        .feature(GenerationStep.Feature.VEGETAL_DECORATION, Features.MYSTICAL_TREES)
                        .feature(GenerationStep.Feature.VEGETAL_DECORATION, Features.MYSTICAL_FLOWERS)
                        .feature(GenerationStep.Feature.VEGETAL_DECORATION, Features.MYSTICAL_STEMS)
                        .structureFeature(ConfiguredStructureFeatures.DUNGEON)
                        .build())
                .build();

        CORRUPT_FOREST = new Biome.Builder()
                .precipitation(Biome.Precipitation.RAIN)
                .category(Biome.Category.NONE)
                .depth(0.125f)
                .scale(1)
                .temperature(0.8f)
                .downfall(1)
                .effects(new BiomeEffects.Builder()
                        .waterColor(0)
                        .waterColor(0x3f76e4)
                        .waterFogColor(0x050533)
                        .fogColor(CORRUPT_FOG_COLOUR)
                        .skyColor(0x77adff)
                        .build()
                )
                .spawnSettings(spawningSettings.build())
                .generationSettings(new GenerationSettings.Builder()
                        .surfaceBuilder(TESTING)
                        .feature(GenerationStep.Feature.VEGETAL_DECORATION, Features.CORRUPT_TREES)
                        .feature(GenerationStep.Feature.SURFACE_STRUCTURES, Features.CONFIGURED_RETURN_PORTAL)
                        .feature(GenerationStep.Feature.VEGETAL_DECORATION, Features.CORRUPT_STEMS)
                        .feature(GenerationStep.Feature.VEGETAL_DECORATION, Features.WILTED_FLOWERS)
                        .structureFeature(ConfiguredStructureFeatures.DUNGEON)
                        .build()
                ).build();
    }

    public void onInitialize() {
        Registry.register(Registry.CHUNK_GENERATOR, Lint.id("haykam_generator"), HaykamChunkGenerator.CODEC);

        Registry.register(BuiltinRegistries.BIOME, MYSTICAL_FOREST_KEY.getValue(), MYSTICAL_FOREST);
        Registry.register(BuiltinRegistries.BIOME, CORRUPT_FOREST_KEY.getValue(), CORRUPT_FOREST);

        BuiltinBiomesAccessor.getBY_RAW_ID().put(BuiltinRegistries.BIOME.getRawId(CORRUPT_FOREST), CORRUPT_FOREST_KEY);
        BuiltinBiomesAccessor.getBY_RAW_ID().put(BuiltinRegistries.BIOME.getRawId(MYSTICAL_FOREST), MYSTICAL_FOREST_KEY);
    }
}
