/*
 * Lint
 * Copyright (C) 2020 hYdos, Valoeghese, ramidzkh
 *
 * This program is free software; you can redistribute it and/or
 * modify it under the terms of the GNU Lesser General Public
 * License as published by the Free Software Foundation; either
 * version 3 of the License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 * Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with this program; if not, write to the Free Software Foundation,
 * Inc., 51 Franklin Street, Fifth Floor, Boston, MA  02110-1301, USA.
 */

package me.hydos.lint.world.biome;

import me.hydos.lint.Lint;
import me.hydos.lint.block.LintBlocks;
import me.hydos.lint.entity.Birds;
import me.hydos.lint.entity.Entities;
import me.hydos.lint.sound.Sounds;
import me.hydos.lint.world.biome.surface.DawnShardlandsSurfaceBuilder;
import me.hydos.lint.world.biome.surface.OceanSurfaceBuilder;
import me.hydos.lint.world.carver.LintConfiguredCarvers;
import me.hydos.lint.world.feature.Features;
import me.hydos.lint.world.gen.HaykamChunkGenerator;
import me.hydos.lint.world.structure.ConfiguredStructureFeatures;
import net.fabricmc.fabric.mixin.biome.BuiltinBiomesAccessor;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.SpawnGroup;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.util.registry.BuiltinRegistries;
import net.minecraft.util.registry.Registry;
import net.minecraft.util.registry.RegistryKey;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.BiomeEffects;
import net.minecraft.world.biome.BiomeParticleConfig;
import net.minecraft.world.biome.GenerationSettings;
import net.minecraft.world.biome.SpawnSettings;
import net.minecraft.world.gen.GenerationStep;
import net.minecraft.world.gen.surfacebuilder.ConfiguredSurfaceBuilder;
import net.minecraft.world.gen.surfacebuilder.SurfaceBuilder;
import net.minecraft.world.gen.surfacebuilder.TernarySurfaceConfig;

public class Biomes {

	/**
	 * Fog Colours
	 */
	public static final int MYSTICAL_FOG_COLOUR = 0x8cfff5;
	public static final int CORRUPT_FOG_COLOUR = 0x916ec1;
	public static final int DAWN_FOG_COLOUR = 0xe5c14b;

	/**
	 * Biome Surface Builders
	 */
	public static final SurfaceBuilder<TernarySurfaceConfig> OCEAN_RAW_SB = new OceanSurfaceBuilder(LintBlocks.MYSTICAL_SAND.getDefaultState(), LintBlocks.WHITE_SAND.getDefaultState());
	public static final SurfaceBuilder<TernarySurfaceConfig> CORRUPT_OCEAN_RAW_SB = new OceanSurfaceBuilder(LintBlocks.CORRUPT_SAND.getDefaultState(), LintBlocks.WHITE_SAND.getDefaultState());
	public static final SurfaceBuilder<TernarySurfaceConfig> DAWN_SHARDLANDS_RAW_SB = new DawnShardlandsSurfaceBuilder();

	private static final ConfiguredSurfaceBuilder<TernarySurfaceConfig> MF_SB = SurfaceBuilder.DEFAULT.withConfig(
			new TernarySurfaceConfig(
					LintBlocks.LIVELY_GRASS.getDefaultState(),
					LintBlocks.RICH_DIRT.getDefaultState(),
					LintBlocks.RICH_DIRT.getDefaultState()));
	private static final ConfiguredSurfaceBuilder<TernarySurfaceConfig> CF_SB = SurfaceBuilder.DEFAULT.withConfig(
			new TernarySurfaceConfig(LintBlocks.CORRUPT_GRASS.getDefaultState(),
					LintBlocks.RICH_DIRT.getDefaultState(),
					LintBlocks.RICH_DIRT.getDefaultState()));
	private static final ConfiguredSurfaceBuilder<TernarySurfaceConfig> OC_SB = OCEAN_RAW_SB.withConfig(
			new TernarySurfaceConfig(
					LintBlocks.LIVELY_GRASS.getDefaultState(),
					LintBlocks.RICH_DIRT.getDefaultState(),
					LintBlocks.RICH_DIRT.getDefaultState()));
	private static final ConfiguredSurfaceBuilder<TernarySurfaceConfig> CB_SB = CORRUPT_OCEAN_RAW_SB.withConfig(
			new TernarySurfaceConfig(LintBlocks.CORRUPT_GRASS.getDefaultState(),
					LintBlocks.RICH_DIRT.getDefaultState(),
					LintBlocks.RICH_DIRT.getDefaultState()));
	private static final ConfiguredSurfaceBuilder<TernarySurfaceConfig> IN_SB = SurfaceBuilder.DEFAULT.withConfig(
			new TernarySurfaceConfig(LintBlocks.INDIGO_STONE.getDefaultState(),
					LintBlocks.INDIGO_STONE.getDefaultState(),
					LintBlocks.INDIGO_STONE.getDefaultState()));
	private static final ConfiguredSurfaceBuilder<TernarySurfaceConfig> DS_SB = DAWN_SHARDLANDS_RAW_SB.withConfig(
			new TernarySurfaceConfig(LintBlocks.ASPHALT.getDefaultState(),
					LintBlocks.ASPHALT.getDefaultState(),
					LintBlocks.ASPHALT.getDefaultState()));

	/**
	 * Spawn Configurations
	 */
	public static final SpawnSettings.Builder DEFAULT_SPAWN_SETTINGS = new SpawnSettings.Builder()
			.spawn(SpawnGroup.CREATURE, new SpawnSettings.SpawnEntry(Entities.TINY_POTATO, 2, 1, 3))
			.spawn(SpawnGroup.AMBIENT, new SpawnSettings.SpawnEntry(Birds.EASTERN_ROSELLA, 10, 1, 1))
//			.spawn(SpawnGroup.MONSTER, new SpawnSettings.SpawnEntry(Entities.GHOST, 4, 1, 1))
			.spawn(SpawnGroup.MONSTER, new SpawnSettings.SpawnEntry(EntityType.SPIDER, 1, 1, 1))
			.spawn(SpawnGroup.MONSTER, new SpawnSettings.SpawnEntry(EntityType.CREEPER, 1, 1, 1));

	public static final SpawnSettings.Builder FOREST_SPAWN_SETTINGS = new SpawnSettings.Builder()
			.spawn(SpawnGroup.CREATURE, new SpawnSettings.SpawnEntry(Entities.TINY_POTATO, 2, 1, 3))
			.spawn(SpawnGroup.AMBIENT, new SpawnSettings.SpawnEntry(Birds.EASTERN_ROSELLA, 1, 1, 4))
//			.spawn(SpawnGroup.MONSTER, new SpawnSettings.SpawnEntry(Entities.GHOST, 4, 1, 1))
			.spawn(SpawnGroup.MONSTER, new SpawnSettings.SpawnEntry(EntityType.SPIDER, 1, 1, 1))
			.spawn(SpawnGroup.MONSTER, new SpawnSettings.SpawnEntry(EntityType.CREEPER, 1, 1, 1));

	/**
	 * Biomes
	 */
	public static final Biome CORRUPT_FOREST = new Biome.Builder()
			.precipitation(Biome.Precipitation.NONE)
			.category(Biome.Category.FOREST)
			.depth(0.125f)
			.scale(1)
			.temperature(0.8f)
			.downfall(1)
			.effects(new BiomeEffects.Builder()
					.waterColor(0x916ec1)
					.waterFogColor(0x916ec1)
					.fogColor(CORRUPT_FOG_COLOUR)
					.loopSound(Sounds.CORRUPT_FOREST)
					.skyColor(0x9c76c1)
					.build()
					)
			.spawnSettings(DEFAULT_SPAWN_SETTINGS.build())
			.generationSettings(new GenerationSettings.Builder()
					.surfaceBuilder(CF_SB)
					.carver(GenerationStep.Carver.AIR, LintConfiguredCarvers.CAVE)
					.feature(GenerationStep.Feature.RAW_GENERATION, Features.CONFIGURED_VERTICAL_SHAFT)
					.feature(GenerationStep.Feature.SURFACE_STRUCTURES, Features.CONFIGURED_RETURN_PORTAL)
					.feature(GenerationStep.Feature.VEGETAL_DECORATION, Features.CORRUPT_TREES)
					.feature(GenerationStep.Feature.VEGETAL_DECORATION, Features.CORRUPT_STEMS)
					.feature(GenerationStep.Feature.VEGETAL_DECORATION, Features.WILTED_FLOWERS)
					.feature(GenerationStep.Feature.UNDERGROUND_ORES, Features.TARSCAN_ORE)
					.feature(GenerationStep.Feature.UNDERGROUND_ORES, Features.SICIERON_ORE)
					.feature(GenerationStep.Feature.UNDERGROUND_ORES, Features.JUREL_ORE)
					.structureFeature(ConfiguredStructureFeatures.DUNGEON)
					.build()
					).build();

	public static final Biome MYSTICAL_FOREST = new Biome.Builder()
			.precipitation(Biome.Precipitation.NONE)
			.category(Biome.Category.FOREST)
			.depth(0.125f)
			.scale(1)
			.temperature(0.8f)
			.downfall(0)
			.effects(new BiomeEffects.Builder()
					.waterColor(0x32e686)
					.waterFogColor(0x32e686)
					.fogColor(MYSTICAL_FOG_COLOUR)
					.loopSound(Sounds.MYSTICAL_FOREST)
					.skyColor(0x88dfea)
					.build())
			.spawnSettings(DEFAULT_SPAWN_SETTINGS.build())
			.generationSettings(new GenerationSettings.Builder()
					.surfaceBuilder(MF_SB)
					.carver(GenerationStep.Carver.AIR, LintConfiguredCarvers.CAVE)
					.feature(GenerationStep.Feature.SURFACE_STRUCTURES, Features.CONFIGURED_RETURN_PORTAL)
					.feature(GenerationStep.Feature.VEGETAL_DECORATION, Features.MYSTICAL_TREES)
					.feature(GenerationStep.Feature.VEGETAL_DECORATION, Features.MYSTICAL_FLOWERS)
					.feature(GenerationStep.Feature.VEGETAL_DECORATION, Features.MYSTICAL_STEMS)
					.feature(GenerationStep.Feature.UNDERGROUND_ORES, Features.TARSCAN_ORE)
					.feature(GenerationStep.Feature.UNDERGROUND_ORES, Features.SICIERON_ORE)
					.feature(GenerationStep.Feature.UNDERGROUND_ORES, Features.JUREL_ORE)
					.structureFeature(ConfiguredStructureFeatures.DUNGEON)
					.build())
			.build();

	public static final Biome DEEP_MYSTICAL_FOREST = new Biome.Builder()
			.precipitation(Biome.Precipitation.NONE)
			.category(Biome.Category.FOREST)
			.depth(0.125f)
			.scale(1)
			.temperature(0.8f)
			.downfall(0)
			.effects(new BiomeEffects.Builder()
					.waterColor(0x32e686)
					.waterFogColor(0x32e686)
					.fogColor(MYSTICAL_FOG_COLOUR)
					.loopSound(Sounds.MYSTICAL_FOREST)
					.skyColor(0x88dfea)
					.build())
			.spawnSettings(FOREST_SPAWN_SETTINGS.build())
			.generationSettings(new GenerationSettings.Builder()
					.surfaceBuilder(MF_SB)
					.carver(GenerationStep.Carver.AIR, LintConfiguredCarvers.CAVE)
					.feature(GenerationStep.Feature.SURFACE_STRUCTURES, Features.CONFIGURED_RETURN_PORTAL)
					.feature(GenerationStep.Feature.VEGETAL_DECORATION, Features.THICK_MYSTICAL_TREES)
					.feature(GenerationStep.Feature.VEGETAL_DECORATION, Features.MYSTICAL_FLOWERS)
					.feature(GenerationStep.Feature.VEGETAL_DECORATION, Features.MYSTICAL_STEMS)
					.feature(GenerationStep.Feature.UNDERGROUND_ORES, Features.TARSCAN_ORE)
					.feature(GenerationStep.Feature.UNDERGROUND_ORES, Features.SICIERON_ORE)
					.feature(GenerationStep.Feature.UNDERGROUND_ORES, Features.JUREL_ORE)
					.structureFeature(ConfiguredStructureFeatures.DUNGEON)
					.build())
			.build();

	public static final Biome OCEAN = new Biome.Builder()
			.precipitation(Biome.Precipitation.NONE)
			.category(Biome.Category.OCEAN)
			.depth(-0.5f)
			.scale(1)
			.temperature(0.8f)
			.downfall(0)
			.effects(new BiomeEffects.Builder()
					.waterColor(0x4faad1)
					.waterFogColor(0x4faad1)
					.fogColor(0xC0D8FF)
					.loopSound(Sounds.OCEAN)
					.skyColor(0x88dfea)
					.build())
			.spawnSettings(new SpawnSettings.Builder().build())
			.generationSettings(new GenerationSettings.Builder()
					.surfaceBuilder(OC_SB)
					.carver(GenerationStep.Carver.AIR, LintConfiguredCarvers.CAVE)
					.feature(GenerationStep.Feature.UNDERGROUND_ORES, Features.TARSCAN_ORE)
					.feature(GenerationStep.Feature.UNDERGROUND_ORES, Features.SICIERON_ORE)
					.feature(GenerationStep.Feature.UNDERGROUND_ORES, Features.JUREL_ORE)
					.structureFeature(ConfiguredStructureFeatures.DUNGEON)
					.build())
			.build();

	public static final Biome CORRUPT_BEACH = new Biome.Builder()
			.precipitation(Biome.Precipitation.NONE)
			.category(Biome.Category.BEACH)
			.depth(-0.5f)
			.scale(1)
			.temperature(0.8f)
			.downfall(0)
			.effects(new BiomeEffects.Builder()
					.waterColor(0)
					.waterColor(0x4faad1)
					.waterFogColor(0x4faad1)
					.fogColor(CORRUPT_FOG_COLOUR)
					.loopSound(Sounds.CORRUPT_FOREST)
					.skyColor(0x9c76c1)
					.build())
			.spawnSettings(DEFAULT_SPAWN_SETTINGS.build())
			.generationSettings(new GenerationSettings.Builder()
					.surfaceBuilder(CB_SB)
					.carver(GenerationStep.Carver.AIR, LintConfiguredCarvers.CAVE)
					.feature(GenerationStep.Feature.UNDERGROUND_ORES, Features.TARSCAN_ORE)
					.feature(GenerationStep.Feature.UNDERGROUND_ORES, Features.SICIERON_ORE)
					.feature(GenerationStep.Feature.UNDERGROUND_ORES, Features.JUREL_ORE)
					.structureFeature(ConfiguredStructureFeatures.DUNGEON)
					.build())
			.build();
	public static final Biome INDIGO_RIDGES = new Biome.Builder()
			.precipitation(Biome.Precipitation.NONE)
			.category(Biome.Category.EXTREME_HILLS)
			.depth(1.5f)
			.scale(0.5f)
			.temperature(0.6f)
			.downfall(1)
			.effects(new BiomeEffects.Builder()
					.waterColor(0x3f76e4)
					.waterFogColor(0x050533)
					.fogColor(CORRUPT_FOG_COLOUR)
					.loopSound(Sounds.CORRUPT_FOREST)
					.skyColor(0x9c76c1)
					.build()
					)
			.spawnSettings(new SpawnSettings.Builder().build())
			.generationSettings(new GenerationSettings.Builder()
					.surfaceBuilder(IN_SB)
					.carver(GenerationStep.Carver.AIR, LintConfiguredCarvers.CAVE)
					.feature(GenerationStep.Feature.SURFACE_STRUCTURES, Features.CONFIGURED_RETURN_PORTAL)
					.feature(GenerationStep.Feature.UNDERGROUND_ORES, Features.TARSCAN_ORE)
					.feature(GenerationStep.Feature.UNDERGROUND_ORES, Features.SICIERON_ORE)
					.feature(GenerationStep.Feature.UNDERGROUND_ORES, Features.JUREL_ORE)
					.build())
			.build();

	public static final Biome DAWN_SHARDLANDS = new Biome.Builder()
			.precipitation(Biome.Precipitation.NONE)
			.category(Biome.Category.NONE)
			.depth(0.125f)
			.scale(1)
			.temperature(0.8f)
			.downfall(0)
			.effects(new BiomeEffects.Builder()
					.particleConfig(new BiomeParticleConfig(ParticleTypes.ASH, 0.05F))
					.waterColor(0xfccb07)
					.waterFogColor(0xfcf807)
					.fogColor(DAWN_FOG_COLOUR)
					.loopSound(Sounds.DAWN_SHARDLANDS)
					.skyColor(0xffd30f)
					.build())
			.spawnSettings(new SpawnSettings.Builder().build())
			.generationSettings(new GenerationSettings.Builder()
					.surfaceBuilder(DS_SB)
					// TODO lexmanos boss structure.
					// TODO allos and manos shards (powerful crystals of the two forces that made the world) as "ores" that spawn hanging from the bottom of the floating islands
					.build())
			.build();

	/**
	 * Biome Keys
	 */
	public static final RegistryKey<Biome> MYSTICAL_FOREST_KEY = RegistryKey.of(Registry.BIOME_KEY, Lint.id("mystical_forest"));
	public static final RegistryKey<Biome> THICK_MYSTICAL_FOREST_KEY = RegistryKey.of(Registry.BIOME_KEY, Lint.id("thick_mystical_forest"));
	public static final RegistryKey<Biome> CORRUPT_FOREST_KEY = RegistryKey.of(Registry.BIOME_KEY, Lint.id("corrupt_forest"));
	public static final RegistryKey<Biome> OCEAN_KEY = RegistryKey.of(Registry.BIOME_KEY, Lint.id("ocean"));
	public static final RegistryKey<Biome> CORRUPT_BEACH_KEY = RegistryKey.of(Registry.BIOME_KEY, Lint.id("corrupt_beach"));
	public static final RegistryKey<Biome> INDIGO_RIDGES_KEY = RegistryKey.of(Registry.BIOME_KEY, Lint.id("indigo_ridges"));
	public static final RegistryKey<Biome> DAWN_SHARDLANDS_KEY = RegistryKey.of(Registry.BIOME_KEY, Lint.id("dawn_shardlands"));

	public static void register() {
		Registry.register(Registry.SURFACE_BUILDER, Lint.id("ocean"), OCEAN_RAW_SB);
		Registry.register(Registry.SURFACE_BUILDER, Lint.id("corrupt_ocean"), CORRUPT_OCEAN_RAW_SB);
		Registry.register(Registry.SURFACE_BUILDER, Lint.id("dawn_shardlands"), DAWN_SHARDLANDS_RAW_SB);

		Registry.register(Registry.CHUNK_GENERATOR, Lint.id("haykam_chunk_gen"), HaykamChunkGenerator.CODEC);

		registerBiome(MYSTICAL_FOREST_KEY, MYSTICAL_FOREST);
		registerBiome(THICK_MYSTICAL_FOREST_KEY, DEEP_MYSTICAL_FOREST);
		registerBiome(CORRUPT_FOREST_KEY, CORRUPT_FOREST);
		registerBiome(OCEAN_KEY, OCEAN);
		registerBiome(CORRUPT_BEACH_KEY, CORRUPT_BEACH);
		registerBiome(INDIGO_RIDGES_KEY, INDIGO_RIDGES);
		registerBiome(DAWN_SHARDLANDS_KEY, DAWN_SHARDLANDS);
	}

	private static void registerBiome(RegistryKey<Biome> key, Biome biome) {
		Registry.register(BuiltinRegistries.BIOME, key.getValue(), biome);
		BuiltinBiomesAccessor.getBY_RAW_ID().put(BuiltinRegistries.BIOME.getRawId(biome), key);
	}
}
