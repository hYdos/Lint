package me.hydos.lint;

import me.hydos.lint.blocks.AdventureTransformerBlockEntity;
import me.hydos.lint.dimensions.DimensionManager;
//import me.hydos.lint.entities.liltaterBattery.LilTaterBattery;
import me.hydos.lint.entities.liltaterBattery.LilTaterBattery;
import me.hydos.techrebornApi.TechRebornApi;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.entity.FabricEntityTypeBuilder;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.entity.EntityCategory;
import net.minecraft.entity.EntityDimensions;
import net.minecraft.entity.EntityType;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;
import techreborn.blockentity.machine.multiblock.IndustrialGrinderBlockEntity;
import techreborn.blocks.GenericMachineBlock;

public class Lint implements ModInitializer {

	public static final GenericMachineBlock ADVENTURE_TRANSFORMER = new GenericMachineBlock(TechRebornApi.createGenericEGui(), IndustrialGrinderBlockEntity::new);

	public static final EntityType<LilTaterBattery> LIL_TATER =
			Registry.register(
					Registry.ENTITY_TYPE,
					new Identifier("lint", "lil_tater"),
					FabricEntityTypeBuilder.<LilTaterBattery>create(EntityCategory.AMBIENT, ((type, world) -> new LilTaterBattery(world)))
							.size(EntityDimensions.fixed(0.3f, 0.3f))
							.build()
			);

	public static BlockEntityType<AdventureTransformerBlockEntity> ADVENTURE_TRANSFORMER_BLOCK_ENTITY;

    @Override
	public void onInitialize() {

		Registry.register(Registry.BLOCK, new Identifier("lint", "adventure_transformer"), ADVENTURE_TRANSFORMER);
		Registry.register(Registry.ITEM, new Identifier("lint", "adventure_transformer"), new BlockItem(ADVENTURE_TRANSFORMER, new Item.Settings().group(ItemGroup.MISC)));


		DimensionManager.register();

	}
}
