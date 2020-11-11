package io.github.hydos.lint.client;

import java.util.Optional;

import io.github.hydos.lint.client.particle.ClientParticles;
import io.github.hydos.lint.container.Containers;
import io.github.hydos.lint.container.LilTaterInteractContainer;
import io.github.hydos.lint.container.client.LilTaterContainerScreen;
import io.github.hydos.lint.entity.Entities;
import io.github.hydos.lint.entity.beetater.BeeTaterEntityRenderer;
import io.github.hydos.lint.entity.boss.i5.I509VCBRenderer;
import io.github.hydos.lint.entity.boss.kingtater.KingTaterRenderer;
import io.github.hydos.lint.entity.tater.TinyPotatoEntityRenderer;
import io.github.hydos.lint.world.biome.Biomes;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.blockrenderlayer.v1.BlockRenderLayerMap;
import net.fabricmc.fabric.api.client.rendereregistry.v1.EntityRendererRegistry;
import net.fabricmc.fabric.api.client.screen.ScreenProviderRegistry;
import net.minecraft.block.FlowerBlock;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.text.LiteralText;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.registry.Registry;
import net.minecraft.util.registry.RegistryKey;
import net.minecraft.world.World;
import net.minecraft.world.biome.Biome;

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

	public static float calculateFogDistanceChunks(World world, double x, double z, float originalResultChunks) {
		int xi = MathHelper.floor(x);
		int zi = MathHelper.floor(z);
		int xl = (xi >> 4) << 4;
		int zl = (zi >> 4) << 4;
		int xh = xl + 16;
		int zh = zl + 16;

		float xProgress = (float) (x - (double) xl);
		xProgress /= 16.0f;
		float zProgress = (float) (z - (double) zl);
		zProgress /= 16.0f;

		return MathHelper.lerp(xProgress, 
				MathHelper.lerp(zProgress, getFDC(world, xl, zl, originalResultChunks), getFDC(world, xl, zh, originalResultChunks)),
				MathHelper.lerp(zProgress, getFDC(world, xh, zl, originalResultChunks), getFDC(world, xh, zh, originalResultChunks)));
	}

	private static float getFDC(World world, int x, int z, float originalResultChunks) {
		Optional<RegistryKey<Biome>> biome = world.getRegistryManager().get(Registry.BIOME_KEY).getKey(world.getBiome(new BlockPos(x, 64, z))); // get biome
		float distChunks = originalResultChunks;

		if (biome.isPresent()) {
			RegistryKey<Biome> aBiome = biome.get();
			// Want an equal experience for all players, so control it directly.
			if (aBiome == Biomes.CORRUPT_FOREST_KEY) {
				distChunks = 3f;
			} else if (aBiome == Biomes.MYSTICAL_FOREST_KEY) {
				distChunks = 0.9f * originalResultChunks;
			}
		}

		if (distChunks > originalResultChunks) {
			distChunks = originalResultChunks;
		}

		return distChunks;
	}
}
