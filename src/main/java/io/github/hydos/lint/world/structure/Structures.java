package io.github.hydos.lint.world.structure;

import com.mojang.datafixers.util.Either;
import io.github.hydos.lint.Lint;
import io.github.hydos.lint.world.feature.DungeonFeature;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.structure.v1.FabricStructureBuilder;
import net.minecraft.structure.pool.LegacySinglePoolElement;
import net.minecraft.structure.pool.StructurePool;
import net.minecraft.structure.processor.StructureProcessorList;
import net.minecraft.world.gen.GenerationStep;
import net.minecraft.world.gen.feature.StructureFeature;
import net.minecraft.world.gen.feature.StructurePoolFeatureConfig;

import java.util.function.Function;

public class Structures implements ModInitializer {

    public static final StructureFeature<StructurePoolFeatureConfig> DUNGEON = FabricStructureBuilder.create(Lint.id("dungeon"), new DungeonFeature(StructurePoolFeatureConfig.CODEC)).step(GenerationStep.Feature.SURFACE_STRUCTURES).defaultConfig(32, 8, 6969420).register();

    @Override
    public void onInitialize() {
    }

    public static Function<StructurePool.Projection, LegacySinglePoolElement> createLegacySinglePoolElement(String path, StructureProcessorList structureProcessorList) {
        return (projection) -> new LegacySinglePoolElement(Either.left(Lint.id(path)), () -> structureProcessorList, projection);
    }
}
