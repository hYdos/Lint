package me.hydos.lint;

import me.hydos.lint.blocks.NetherReactorCoreBlock;
import me.hydos.lint.dimensions.DimensionManager;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.block.FabricBlockSettings;
import net.minecraft.block.Block;
import net.minecraft.block.Material;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

public class LintClient implements ModInitializer {

	public static final Block ADVENTURE_TRANSFORMER = new NetherReactorCoreBlock(FabricBlockSettings.of(Material.METAL).build());

	@Override
	public void onInitialize() {

		Registry.register(Registry.BLOCK, new Identifier("lint", "adventure_transformer"), ADVENTURE_TRANSFORMER);

		DimensionManager.register();

	}
}
