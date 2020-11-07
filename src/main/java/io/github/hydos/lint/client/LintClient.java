package io.github.hydos.lint.client;

import io.github.hydos.lint.client.particle.ClientParticles;
import io.github.hydos.lint.container.Containers;
import io.github.hydos.lint.container.LilTaterInteractContainer;
import io.github.hydos.lint.container.client.LilTaterContainerScreen;
import io.github.hydos.lint.entity.Entities;
import io.github.hydos.lint.entity.beetater.BeeTaterEntityRenderer;
import io.github.hydos.lint.entity.boss.i5.I509VCBRenderer;
import io.github.hydos.lint.entity.boss.kingtater.KingTaterRenderer;
import io.github.hydos.lint.entity.tater.TinyPotatoEntityRenderer;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.blockrenderlayer.v1.BlockRenderLayerMap;
import net.fabricmc.fabric.api.client.rendereregistry.v1.EntityRendererRegistry;
import net.fabricmc.fabric.api.client.screen.ScreenProviderRegistry;
import net.minecraft.block.FlowerBlock;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.text.LiteralText;

@Environment(EnvType.CLIENT)
public class LintClient implements ClientModInitializer {

    public static void putBlock(FlowerBlock flower, ServerRenderLayer layer) {
        RenderLayer renderLayer = null;
        if (layer == ServerRenderLayer.cutout) {
            renderLayer = RenderLayer.getCutout();
        }
        BlockRenderLayerMap.INSTANCE.putBlock(flower, renderLayer);
    }

    @Override
    public void onInitializeClient() {
        ClientParticles.register();

        EntityRendererRegistry.INSTANCE.register(Entities.TINY_POTATO, (entityRenderDispatcher, context) -> new TinyPotatoEntityRenderer(entityRenderDispatcher));
        EntityRendererRegistry.INSTANCE.register(Entities.BEE_TATER, (entityRenderDispatcher, context) -> new BeeTaterEntityRenderer(entityRenderDispatcher));
        EntityRendererRegistry.INSTANCE.register(Entities.MINION, (entityRenderDispatcher, context) -> new TinyPotatoEntityRenderer(entityRenderDispatcher));
        EntityRendererRegistry.INSTANCE.register(Entities.KING_TATER, (entityRenderDispatcher, context) -> new KingTaterRenderer(entityRenderDispatcher));
        EntityRendererRegistry.INSTANCE.register(Entities.I5, (entityRenderDispatcher, context) -> new I509VCBRenderer(entityRenderDispatcher));

        ScreenProviderRegistry.INSTANCE.registerFactory(Containers.TATER_CONTAINER_ID, (syncId, identifier, playerEntity, buf) -> new LilTaterContainerScreen(new LilTaterInteractContainer(null, syncId, buf.readInt(), playerEntity.inventory), playerEntity.inventory, new LiteralText("Lil Tater UI")));
    }

    public enum ServerRenderLayer {
        cutout
    }
}
