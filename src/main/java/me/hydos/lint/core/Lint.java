package me.hydos.lint.core;

import me.hydos.lint.blocks.AdventureTransformerBlockEntity;
import me.hydos.lint.blocks.AdventureTransformerGui;
import me.hydos.lint.blocks.AdventureTransformerRecipe;
import me.hydos.techrebornApi.TechRebornApi;
import net.minecraft.item.ItemGroup;
import net.minecraft.util.Identifier;
import techreborn.client.GuiType;

public interface Lint {

    static void onInitializeServer() {
        TechRebornApi.registerBlock("lint", "adventure_transformer", AdventureTransformerRecipe.class, ItemGroup.TOOLS, AdventureTransformerBlockEntity::new, GuiType.register(new Identifier("adventure_transformer"), null));
    }
}
