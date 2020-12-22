package io.github.hydos.lint.world.biome;

import io.github.hydos.lint.Lint;
import io.github.hydos.lint.entity.Entities;
import io.github.hydos.lint.block.LintBlocks;
import io.github.hydos.lint.sound.Sounds;
import io.github.hydos.lint.world.carver.LintConfiguredCarvers;
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
	public static final int MYSTICAL_FOG_COLOUR = 0xc0d8ff;
	public static final int CORRUPT_FOG_COLOUR = 0x916ec1;
	public static final int DAWN_FOG_COLOUR = 0xe5c14b;
	public static final Biome CORRUPT_FOREST;
	public static final Biome MYSTICAL_FOREST;
	public static final RegistryKey<Biome> MYSTICAL_FOREST_KEY = RegistryKey.of(Registry.BIOME_KEY, Lint.id("mystical_forest"));
	public static final RegistryKey<Biome> CORRUPT_FOREST_KEY = RegistryKey.of(Registry.BIOME_KEY, Lint.id("corrupt_forest"));
	private static final ConfiguredSurfaceBuilder<TernarySurfaceConfig> MF_SB = SurfaceBuilder.DEFAULT.withConfig(new TernarySurfaceConfig(LintBlocks.LIVELY_GRASS.getDefaultState(), LintBlocks.RICH_DIRT.getDefaultState(), LintBlocks.RICH_DIRT.getDefaultState()));
	private static final ConfiguredSurfaceBuilder<TernarySurfaceConfig> CF_SB = SurfaceBuilder.DEFAULT.withConfig(new TernarySurfaceConfig(LintBlocks.CORRUPT_GRASS.getDefaultState(), LintBlocks.RICH_DIRT.getDefaultState(), LintBlocks.RICH_DIRT.getDefaultState()));

	static {
		SpawnSettings.Builder spawningSettings = new SpawnSettings.Builder();
		spawningSettings.spawn(SpawnGroup.CREATURE, new SpawnSettings.SpawnEntry(Entities.TINY_POTATO, 1, 1, 3));
		MYSTICAL_FOREST = new Biome.Builder()
				.precipitation(Biome.Precipitation.NONE)
				.category(Biome.Category.FOREST)
				.depth(0.125f)
				.scale(1)
				.temperature(0.8f)
				.downfall(0)
				.effects(new BiomeEffects.Builder()
						.waterColor(0)
						.waterColor(0x3f76e4)
						.waterFogColor(0x050533)
						.fogColor(MYSTICAL_FOG_COLOUR)//.fogColor(CORRUPT_FOG_COLOUR)
						.loopSound(Sounds.MYSTICAL_FOREST)
						.skyColor(0x88dfea)
						.build())
				.spawnSettings(spawningSettings.build())
				.generationSettings(new GenerationSettings.Builder()
						.surfaceBuilder(MF_SB)
						.carver(GenerationStep.Carver.AIR, LintConfiguredCarvers.CAVE)
						.feature(GenerationStep.Feature.SURFACE_STRUCTURES, Features.CONFIGURED_RETURN_PORTAL)
						.feature(GenerationStep.Feature.VEGETAL_DECORATION, Features.MYSTICAL_TREES)
						.feature(GenerationStep.Feature.VEGETAL_DECORATION, Features.MYSTICAL_FLOWERS)
						.feature(GenerationStep.Feature.VEGETAL_DECORATION, Features.MYSTICAL_STEMS)
						.structureFeature(ConfiguredStructureFeatures.DUNGEON)
						.build())
				.build();

		CORRUPT_FOREST = new Biome.Builder()
				.precipitation(Biome.Precipitation.NONE)
				.category(Biome.Category.FOREST)
				.depth(0.125f)
				.scale(1)
				.temperature(0.8f)
				.downfall(1)
				.effects(new BiomeEffects.Builder()
						.waterColor(0)
						.waterColor(0x3f76e4)
						.waterFogColor(0x050533)
						.fogColor(CORRUPT_FOG_COLOUR)
						.loopSound(Sounds.CORRUPT_FOREST)
						.skyColor(0x9c76c1)
						.build()
						)
				.spawnSettings(spawningSettings.build())
				.generationSettings(new GenerationSettings.Builder()
						.surfaceBuilder(CF_SB)
						.carver(GenerationStep.Carver.AIR, LintConfiguredCarvers.CAVE)
						.feature(GenerationStep.Feature.SURFACE_STRUCTURES, Features.CONFIGURED_RETURN_PORTAL)
						.feature(GenerationStep.Feature.VEGETAL_DECORATION, Features.CORRUPT_TREES)
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
