package me.hydos.lint.taterkingdungeon;

import net.minecraft.structure.StructurePieceType;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.gen.GenerationStep;
import net.minecraft.world.gen.decorator.Decorator;
import net.minecraft.world.gen.decorator.DecoratorConfig;
import net.minecraft.world.gen.feature.DefaultFeatureConfig;
import net.minecraft.world.gen.feature.StructureFeature;

public interface KingTaterDungeonFeatures {

    StructureFeature<DefaultFeatureConfig> DUNGEON_FEATURE = Registry.register(
            Registry.FEATURE,
            new Identifier("lint", "dungeon_feature"),
            new TaterKingDungeonFeature(DefaultFeatureConfig::deserialize)
    );

    StructureFeature<DefaultFeatureConfig> DUNGEON_STRUCTURE_FEATURE = Registry.register(
            Registry.STRUCTURE_FEATURE,
            new Identifier("lint", "dungeon_structure_feature"),
            DUNGEON_FEATURE
    );

    StructurePieceType BASIC_PIECE = Registry.register(
            Registry.STRUCTURE_PIECE,
            new Identifier("lint", "basic_piece"),
            BasicPiece::new
    );

    static void onInitialize(){
        Registry.BIOME.forEach(biome -> {
            biome.addFeature(GenerationStep.Feature.RAW_GENERATION, DUNGEON_FEATURE.configure(new DefaultFeatureConfig()).createDecoratedFeature(Decorator.NOPE.configure(DecoratorConfig.DEFAULT)));
            biome.addStructureFeature(DUNGEON_FEATURE.configure(new DefaultFeatureConfig()));
        });
    }

}
