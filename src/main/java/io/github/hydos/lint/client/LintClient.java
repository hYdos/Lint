package io.github.hydos.lint.client;

import io.github.hydos.lint.block.Blocks;
import io.github.hydos.lint.client.particle.ClientParticles;
import io.github.hydos.lint.container.Containers;
import io.github.hydos.lint.container.LilTaterInteractContainer;
import io.github.hydos.lint.container.client.LilTaterContainerScreen;
import io.github.hydos.lint.entity.Entities;
import io.github.hydos.lint.entity.beetater.BeeTaterEntityRenderer;
import io.github.hydos.lint.entity.boss.i5.I509VCBRenderer;
import io.github.hydos.lint.entity.boss.kingtater.KingTaterRenderer;
import io.github.hydos.lint.entity.tater.LilTaterEntityRenderer;
import io.github.hydos.lint.sound.Sounds;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.blockrenderlayer.v1.BlockRenderLayerMap;
import net.fabricmc.fabric.api.client.rendereregistry.v1.EntityRendererRegistry;
import net.fabricmc.fabric.api.client.screen.ScreenProviderRegistry;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.text.LiteralText;

@Environment(EnvType.CLIENT)
public class LintClient implements ClientModInitializer {

    @Override
    public void onInitializeClient() {
        ClientParticles.register();

        EntityRendererRegistry.INSTANCE.register(Entities.LIL_TATER, (entityRenderDispatcher, context) -> new LilTaterEntityRenderer(entityRenderDispatcher));
        EntityRendererRegistry.INSTANCE.register(Entities.BEE_TATER, (entityRenderDispatcher, context) -> new BeeTaterEntityRenderer(entityRenderDispatcher));
        EntityRendererRegistry.INSTANCE.register(Entities.MINION, (entityRenderDispatcher, context) -> new LilTaterEntityRenderer(entityRenderDispatcher));
        EntityRendererRegistry.INSTANCE.register(Entities.KING_TATER, (entityRenderDispatcher, context) -> new KingTaterRenderer(entityRenderDispatcher));
        EntityRendererRegistry.INSTANCE.register(Entities.I5, (entityRenderDispatcher, context) -> new I509VCBRenderer(entityRenderDispatcher));

        ScreenProviderRegistry.INSTANCE.registerFactory(Containers.TATER_CONTAINER_ID, (syncId, identifier, playerEntity, buf) -> new LilTaterContainerScreen(new LilTaterInteractContainer(null, syncId, buf.readInt(), playerEntity.inventory), playerEntity.inventory, new LiteralText("Lil Tater UI")));

        BlockRenderLayerMap.INSTANCE.putBlock(Blocks.CORRUPT_STEM, RenderLayer.getCutout());
        BlockRenderLayerMap.INSTANCE.putBlock(Blocks.WILTED_FLOWER, RenderLayer.getCutout());
        BlockRenderLayerMap.INSTANCE.putBlock(Blocks.MYSTICAL_STEM, RenderLayer.getCutout());
        BlockRenderLayerMap.INSTANCE.putBlock(Blocks.MYSTICAL_DAISY, RenderLayer.getCutout());
        BlockRenderLayerMap.INSTANCE.putBlock(Blocks.MYSTICAL_GRASS, RenderLayer.getCutout());
    }
}
