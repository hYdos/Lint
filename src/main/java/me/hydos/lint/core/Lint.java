package me.hydos.lint.core;

import me.hydos.lint.blocks.AdventureTransformerBlockEntity;
import me.hydos.lint.blocks.AdventureTransformerGui;
import me.hydos.lint.blocks.AdventureTransformerRecipe;
import me.hydos.techrebornApi.TechRebornApi;
import net.fabricmc.api.ModInitializer;
import net.minecraft.item.ItemGroup;
import net.minecraft.util.Identifier;
import techreborn.client.GuiType;


public class Lint implements ModInitializer {

	public static final GuiType<AdventureTransformerBlockEntity> ADVENTURE_TRANSFORMER_GUI = GuiType.register(new Identifier("adventure_transformer"), () -> () -> AdventureTransformerGui::new);

    @Override
	public void onInitialize() {
		TechRebornApi.registerBlock("lint", "adventure_transformer", AdventureTransformerRecipe.class, ItemGroup.TOOLS, AdventureTransformerBlockEntity::new, ADVENTURE_TRANSFORMER_GUI);
	}
}
