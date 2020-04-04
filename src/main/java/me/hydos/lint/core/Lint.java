package me.hydos.lint.core;

import me.hydos.lint.blocks.AdventureTransformerBlockEntity;
import me.hydos.lint.blocks.AdventureTransformerGui;
import me.hydos.lint.blocks.AdventureTransformerRecipe;
import me.hydos.lint.boss.BossManager;
import me.hydos.techrebornApi.TechRebornApi;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.sound.ListenerSoundInstance;
import net.minecraft.client.sound.SoundInstance;
import net.minecraft.client.sound.WeightedSoundSet;
import net.minecraft.item.ItemGroup;
import net.minecraft.util.Identifier;
import techreborn.client.GuiType;

public interface Lint {

    GuiType<AdventureTransformerBlockEntity> ADVENTURE_TRANSFORMER_GUI = GuiType.register(new Identifier("adventure_transformer"), () -> () -> AdventureTransformerGui::new);
    me.hydos.lint.boss.BossManager BossManager = new BossManager();

    static void onInitialize() {
        TechRebornApi.registerBlock("lint", "adventure_transformer", AdventureTransformerRecipe.class, ItemGroup.TOOLS, AdventureTransformerBlockEntity::new, ADVENTURE_TRANSFORMER_GUI);
    }
}
