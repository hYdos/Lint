package io.github.hydos.lint.biome;

import io.github.hydos.lint.feature.Features;
import me.hydos.lint.core.Entities;
import me.hydos.lint.core.Lint;
import net.fabricmc.fabric.mixin.biome.BuiltinBiomesAccessor;
import net.minecraft.block.Blocks;
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

public class Biomes {

    private static final ConfiguredSurfaceBuilder<TernarySurfaceConfig> TESTING = SurfaceBuilder.DEFAULT
            .withConfig(new TernarySurfaceConfig(
                    Blocks.OBSIDIAN.getDefaultState(),
                    Blocks.DIRT.getDefaultState(),
                    Blocks.GRAVEL.getDefaultState()));

    public static final Biome CORRUPT_FOREST;
    public static final Biome MYSTICAL_FOREST;

    public static final RegistryKey<Biome> MYSTICAL_FOREST_KEY = RegistryKey.of(Registry.BIOME_KEY, Lint.id("mystical_forest"));
    public static final RegistryKey<Biome> CORRUPT_FOREST_KEY = RegistryKey.of(Registry.BIOME_KEY, Lint.id("corrupt_forest"));

    public static boolean hasSpecialBlocks(Biome biome) {
        return false;
    }

    public static void register() {
        Registry.register(BuiltinRegistries.BIOME, MYSTICAL_FOREST_KEY.getValue(), MYSTICAL_FOREST);
        Registry.register(BuiltinRegistries.BIOME, CORRUPT_FOREST_KEY.getValue(), CORRUPT_FOREST);

        BuiltinBiomesAccessor.getBY_RAW_ID().put(BuiltinRegistries.BIOME.getRawId(CORRUPT_FOREST), CORRUPT_FOREST_KEY);
        BuiltinBiomesAccessor.getBY_RAW_ID().put(BuiltinRegistries.BIOME.getRawId(MYSTICAL_FOREST), MYSTICAL_FOREST_KEY);

        Registry.register(BuiltinRegistries.CONFIGURED_SURFACE_BUILDER, Lint.id("test_surface"), TESTING);
    }

    static {
        SpawnSettings.Builder spawningSettings = new SpawnSettings.Builder();
        spawningSettings.spawn(SpawnGroup.CREATURE, new SpawnSettings.SpawnEntry(Entities.LIL_TATER, 12, 4, 4));
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
                        .skyColor(0x77adff)
                        .build())
                .spawnSettings(spawningSettings.build())
                .generationSettings(new GenerationSettings.Builder()
                        .surfaceBuilder(TESTING)
                        .feature(GenerationStep.Feature.VEGETAL_DECORATION, Features.CORRUPT)
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
                        .fogColor(0xc0d8ff)
                        .skyColor(0x77adff)
                        .build())
                .spawnSettings(spawningSettings.build())
                .generationSettings(new GenerationSettings.Builder()
                        .surfaceBuilder(TESTING)
                        .feature(GenerationStep.Feature.VEGETAL_DECORATION, Features.CORRUPT)
                        .build())
                .build();
    }
}
