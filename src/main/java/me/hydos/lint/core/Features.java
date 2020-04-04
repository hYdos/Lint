package me.hydos.lint.core;

import me.hydos.lint.dimensions.haykam.features.CommonCorruptTreeFeature;
import me.hydos.lint.dimensions.haykam.features.CommonMysticalTreeFeature;
import me.hydos.lint.structurefeatures.TaterKingdungeonFeature;
import me.hydos.lint.structurefeatures.TaterKingDungeonGenerator;
import net.minecraft.structure.StructurePieceType;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.gen.feature.*;

public interface Features {

    Feature<BranchedTreeFeatureConfig> MYSTICAL_TREE = Registry.register(
            Registry.FEATURE,
            new Identifier("lint", "mystical_tree"),
            new CommonMysticalTreeFeature(BranchedTreeFeatureConfig::deserialize)
    );

    Feature<BranchedTreeFeatureConfig> CORRUPT_TREE = Registry.register(
            Registry.FEATURE,
            new Identifier("lint", "corrupt_tree"),
            new CommonCorruptTreeFeature(BranchedTreeFeatureConfig::deserialize)
    );

    StructurePieceType TATER_VILLAGE_PIECE_TYPE = Registry.register(Registry.STRUCTURE_PIECE, "tater_king_dungeon_piece", TaterKingDungeonGenerator.Piece::new);

    StructureFeature<DefaultFeatureConfig> TATER_KING_DUNGEON_FEATURE = register("tater_king_dungeon_feature", new TaterKingdungeonFeature(DefaultFeatureConfig::deserialize));

    StructureFeature<DefaultFeatureConfig> TATER_VILLAGE_STRUCTURE = Registry.register(Registry.STRUCTURE_FEATURE, "tater_king_dungeon_structure", TATER_KING_DUNGEON_FEATURE);

    static void onInitialize(){
        Feature.STRUCTURES.put("Dungeon Enterance", TATER_KING_DUNGEON_FEATURE);
    }

    static <F extends Feature<? extends FeatureConfig>> F register(String name, F feature) {
        return Registry.register(Registry.FEATURE, name, feature);
    }
}
