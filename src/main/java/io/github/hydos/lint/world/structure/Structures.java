package io.github.hydos.lint.world.structure;

import com.mojang.datafixers.util.Either;
import io.github.hydos.lint.Lint;
import io.github.hydos.lint.world.feature.DungeonFeature;
import net.fabricmc.api.ModInitializer;
import net.minecraft.structure.pool.LegacySinglePoolElement;
import net.minecraft.structure.pool.StructurePool;
import net.minecraft.structure.processor.StructureProcessorList;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.gen.GenerationStep;
import net.minecraft.world.gen.feature.StructureFeature;
import net.minecraft.world.gen.feature.StructurePoolFeatureConfig;

import java.util.Locale;
import java.util.function.Function;

public class Structures implements ModInitializer {

    public static final StructureFeature<StructurePoolFeatureConfig> DUNGEON = register("dungeon", new DungeonFeature(StructurePoolFeatureConfig.CODEC), GenerationStep.Feature.SURFACE_STRUCTURES);

    @Override
    public void onInitialize() {
    }

    public static <F extends StructureFeature<?>> F register(String name, F structureFeature, GenerationStep.Feature step) {
        return Registry.register(Registry.STRUCTURE_FEATURE, Lint.id(name.toLowerCase(Locale.ROOT)), structureFeature);
    }


    public static Function<StructurePool.Projection, LegacySinglePoolElement> createLegacySinglePoolElement(String path, StructureProcessorList structureProcessorList) {
        return (projection) -> new LegacySinglePoolElement(Either.left(Lint.id(path)), () -> structureProcessorList, projection);
    }
}
