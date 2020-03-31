package me.hydos.lint;

import me.hydos.lint.blocks.AdventureTransformerBlock;
import me.hydos.lint.blocks.AdventureTransformerBlockEntity;
import me.hydos.lint.dimensions.DimensionManager;
import me.hydos.techrebornApi.TechRebornApi;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.block.FabricBlockSettings;
import net.fabricmc.fabric.api.blockrenderlayer.v1.BlockRenderLayerMap;
import net.minecraft.block.Block;
import net.minecraft.block.Material;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;
import techreborn.blockentity.machine.multiblock.IndustrialGrinderBlockEntity;
import techreborn.blocks.GenericMachineBlock;
import techreborn.client.EGui;

public class LintClient implements ModInitializer {

	public static final GenericMachineBlock ADVENTURE_TRANSFORMER = new GenericMachineBlock(TechRebornApi.createGenericEGui(), IndustrialGrinderBlockEntity::new);

	public static BlockEntityType<AdventureTransformerBlockEntity> ADVENTURE_TRANSFORMER_BLOCK_ENTITY;

    @Override
	public void onInitialize() {

		Registry.register(Registry.BLOCK, new Identifier("lint", "adventure_transformer"), ADVENTURE_TRANSFORMER);
		Registry.register(Registry.ITEM, new Identifier("lint", "adventure_transformer"), new BlockItem(ADVENTURE_TRANSFORMER, new Item.Settings().group(ItemGroup.MISC)));

		DimensionManager.register();

	}
}
