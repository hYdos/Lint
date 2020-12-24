package me.hydos.lint.world.structure;

import me.hydos.lint.Lint;import net.minecraft.util.registry.BuiltinRegistries;
import net.minecraft.world.gen.feature.ConfiguredStructureFeature;
import net.minecraft.world.gen.feature.FeatureConfig;
import net.minecraft.world.gen.feature.StructureFeature;
import net.minecraft.world.gen.feature.StructurePoolFeatureConfig;

public interface ConfiguredStructureFeatures {

    ConfiguredStructureFeature<StructurePoolFeatureConfig, ? extends StructureFeature<StructurePoolFeatureConfig>> DUNGEON = register("dungeon", Structures.DUNGEON.configure(new StructurePoolFeatureConfig(() -> DungeonGenerator.structures, 7)));

    static <FC extends FeatureConfig, F extends StructureFeature<FC>> ConfiguredStructureFeature register(String id, ConfiguredStructureFeature<FC, F> configuredStructureFeature) {
        return BuiltinRegistries.add(BuiltinRegistries.CONFIGURED_STRUCTURE_FEATURE, Lint.id(id), configuredStructureFeature);
    }

}
