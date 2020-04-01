package me.hydos.lint;

import me.hydos.lint.blocks.AdventureTransformerBlockEntity;
import me.hydos.lint.blocks.AdventureTransformerGui;
import me.hydos.lint.blocks.AdventureTransformerRecipe;
import me.hydos.lint.registers.BiomeRegister;
import me.hydos.lint.registers.DimensionRegister;
import me.hydos.lint.entities.liltaterbattery.LilTaterBattery;
import me.hydos.techrebornApi.TechRebornApi;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.entity.FabricEntityTypeBuilder;
import net.minecraft.entity.EntityCategory;
import net.minecraft.entity.EntityDimensions;
import net.minecraft.entity.EntityType;
import net.minecraft.item.ItemGroup;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;
import techreborn.client.GuiType;


public class Lint implements ModInitializer {

	public static final GuiType<AdventureTransformerBlockEntity> ADVENTURE_TRANSFORMER_GUI = GuiType.register(new Identifier("adventure_transformer"), () -> () -> (sync, player, be) -> new AdventureTransformerGui(sync, player, be));

	public static final EntityType<LilTaterBattery> LIL_TATER =
			Registry.register(
					Registry.ENTITY_TYPE,
					new Identifier("lint", "lil_tater"),
					FabricEntityTypeBuilder.<LilTaterBattery>create(EntityCategory.AMBIENT, ((type, world) -> new LilTaterBattery(world)))
							.size(EntityDimensions.fixed(0.3f, 0.4f))
							.build()
			);

    @Override
	public void onInitialize() {

		TechRebornApi.registerBlock("lint", "adventure_transformer", AdventureTransformerRecipe.class, ItemGroup.TOOLS, AdventureTransformerBlockEntity::new, ADVENTURE_TRANSFORMER_GUI);

		BiomeRegister.registerBiomes();
		DimensionRegister.register();

	}
}
