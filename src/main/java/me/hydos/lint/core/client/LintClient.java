package me.hydos.lint.core.client;

import me.hydos.lint.blocks.AdventureTransformerBlockEntity;
import me.hydos.lint.blocks.AdventureTransformerGui;
import me.hydos.lint.blocks.AdventureTransformerRecipe;
import me.hydos.lint.containers.LilTaterInteractContainer;
import me.hydos.lint.containers.client.LilTaterContainerScreen;
import me.hydos.lint.core.Containers;
import me.hydos.lint.core.Entities;
import me.hydos.lint.entities.boss.KingTaterRenderer;
import me.hydos.lint.entities.liltaterbattery.LilTaterBatteryRenderer;
import me.hydos.techrebornApi.TechRebornApi;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.client.rendereregistry.v1.EntityRendererRegistry;
import net.fabricmc.fabric.api.client.screen.ScreenProviderRegistry;
import net.minecraft.item.ItemGroup;
import net.minecraft.text.LiteralText;
import net.minecraft.util.Identifier;
import techreborn.client.GuiType;

@Environment(EnvType.CLIENT)
public interface LintClient {

    static void onInitializeClient() {
        TechRebornApi.registerBlock("lint", "adventure_transformer", AdventureTransformerRecipe.class, ItemGroup.TOOLS, AdventureTransformerBlockEntity::new, GuiType.register(new Identifier("adventure_transformer"), () -> () -> AdventureTransformerGui::new));

        EntityRendererRegistry.INSTANCE.register(Entities.LIL_TATER, (entityRenderDispatcher, context) -> new LilTaterBatteryRenderer(entityRenderDispatcher));
        EntityRendererRegistry.INSTANCE.register(Entities.MINION, (entityRenderDispatcher, context) -> new LilTaterBatteryRenderer(entityRenderDispatcher));
        EntityRendererRegistry.INSTANCE.register(Entities.KING_TATER, (entityRenderDispatcher, context) -> new KingTaterRenderer(entityRenderDispatcher));

        ScreenProviderRegistry.INSTANCE.registerFactory(Containers.TATER_CONTAINER_ID, (syncId, identifier, playerEntity, buf) -> new LilTaterContainerScreen(new LilTaterInteractContainer(null, syncId, buf.readInt(), playerEntity.inventory), playerEntity.inventory, new LiteralText("Lil Tater UI")));
    }
}
