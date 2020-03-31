package me.hydos.lint;

import me.hydos.lint.blocks.AdventureTransformerBlockEntity;
import me.hydos.lint.blocks.AdventureTransformerGui;
import me.hydos.lint.dimensions.DimensionManager;
import me.hydos.lint.entities.liltaterbattery.LilTaterBattery;
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
import techreborn.blocks.GenericMachineBlock;
import techreborn.client.GuiType;

public class Lint implements ModInitializer {

	public static final GuiType<AdventureTransformerBlockEntity> GUI = GuiType.register(new Identifier("adventure_transformer"), () -> () -> (sync, player, be) -> new AdventureTransformerGui(sync, player, (AdventureTransformerBlockEntity)be));
	public static final GenericMachineBlock ADVENTURE_TRANSFORMER = new GenericMachineBlock(GUI, AdventureTransformerBlockEntity::new);

	public static final EntityType<LilTaterBattery> LIL_TATER =
			Registry.register(
					Registry.ENTITY_TYPE,
					new Identifier("lint", "lil_tater"),
					FabricEntityTypeBuilder.<LilTaterBattery>create(EntityCategory.AMBIENT, ((type, world) -> new LilTaterBattery(world)))
							.size(EntityDimensions.fixed(0.3f, 0.3f))
							.build()
			);

	public static BlockEntityType<AdventureTransformerBlockEntity> ADVENTURE_TRANSFORMER_BLOCK_ENTITY = Registry.register(Registry.BLOCK_ENTITY_TYPE, "modid:demo", BlockEntityType.Builder.create(AdventureTransformerBlockEntity::new, ADVENTURE_TRANSFORMER).build(null));;

    @Override
	public void onInitialize() {

		Registry.register(Registry.BLOCK, new Identifier("lint", "adventure_transformer"), ADVENTURE_TRANSFORMER);
		Registry.register(Registry.ITEM, new Identifier("lint", "adventure_transformer"), new BlockItem(ADVENTURE_TRANSFORMER, new Item.Settings().group(ItemGroup.MISC)));


		DimensionManager.register();

	}
}
