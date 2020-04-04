package me.hydos.lint.core;

import me.hydos.lint.dimensions.haykam.features.CommonCorruptTreeFeature;
import me.hydos.lint.dimensions.haykam.features.CommonMysticalTreeFeature;
import me.hydos.lint.structurefeatures.TaterVillageFeature;
import me.hydos.lint.structurefeatures.TaterVillageGenerator;
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

    StructurePieceType TATER_VILLAGE_PIECE_TYPE = Registry.register(Registry.STRUCTURE_PIECE, "tater_village_piece", TaterVillageGenerator.Piece::new);

    StructureFeature<DefaultFeatureConfig> TATER_VILLAGE_FEATURE = register("tater_village_feature", new TaterVillageFeature(DefaultFeatureConfig::deserialize));

    StructureFeature<DefaultFeatureConfig> TATER_VILLAGE_STRUCTURE = Registry.register(Registry.STRUCTURE_FEATURE, "tater_village_structure", TATER_VILLAGE_FEATURE);

//    Feature<DefaultFeatureConfig> EPIC_VALO_CLOUD_FEATURE_FEATURE = Registry.register(
//            Registry.FEATURE,
//            new Identifier("lint", "valo_cool_epic_e"),
//            new EpicValoCloudFeature()
//    );

    static void onInitialize(){
        Feature.STRUCTURES.put("Tater Village", TATER_VILLAGE_FEATURE);
    }

    static <F extends Feature<? extends FeatureConfig>> F register(String name, F feature) {
        return Registry.register(Registry.FEATURE, name, feature);
    }
}
