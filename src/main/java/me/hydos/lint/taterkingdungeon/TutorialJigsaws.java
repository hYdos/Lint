package me.hydos.lint.taterkingdungeon;

import net.fabricmc.api.ModInitializer;
import net.minecraft.structure.StructurePieceType;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.gen.feature.DefaultFeatureConfig;
import net.minecraft.world.gen.feature.Feature;
import net.minecraft.world.gen.feature.StructureFeature;

public class TutorialJigsaws implements ModInitializer
{
	public static final StructureFeature<DefaultFeatureConfig> FEATURE = Registry.register(
			Registry.FEATURE,
			new Identifier("lint", "example_feature"),
			new KingTaterDungeonFeature(DefaultFeatureConfig::deserialize)
	);

	public static final StructureFeature<DefaultFeatureConfig> STRUCTURE = Registry.register(
			Registry.STRUCTURE_FEATURE,
			new Identifier("lint", "example_structure_feature"),
			new KingTaterDungeonFeature(DefaultFeatureConfig::deserialize)
	);

	public static final StructurePieceType EXAMPLE_PIECE = Registry.register(
			Registry.STRUCTURE_PIECE,
			new Identifier("lint", "example_piece"),
			KingTaterDungeonPiece::new
	);

	static {
		Feature.STRUCTURES.put("tutorial_jigsaw", FEATURE);
	}

	@Override
	public void onInitialize()
	{
	}
}
