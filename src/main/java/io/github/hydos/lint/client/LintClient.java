package io.github.hydos.lint.client;

import io.github.hydos.lint.client.entity.render.*;
import io.github.hydos.lint.client.particle.ClientParticles;
import io.github.hydos.lint.entity.Entities;
import me.hydos.lint.fluid.Fluids;
import io.github.hydos.lint.screenhandler.Containers;
import io.github.hydos.lint.screenhandler.LilTaterInteractScreenHandler;
import io.github.hydos.lint.screenhandler.ScreenHandlers;
import io.github.hydos.lint.screenhandler.client.LilTaterContainerScreen;
import io.github.hydos.lint.screenhandler.client.SmelteryScreen;
import me.hydos.lint.world.biome.Biomes;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.blockrenderlayer.v1.BlockRenderLayerMap;
import net.fabricmc.fabric.api.client.render.fluid.v1.FluidRenderHandler;
import net.fabricmc.fabric.api.client.render.fluid.v1.FluidRenderHandlerRegistry;
import net.fabricmc.fabric.api.client.rendereregistry.v1.EntityRendererRegistry;
import net.fabricmc.fabric.api.client.screen.ScreenProviderRegistry;
import net.fabricmc.fabric.api.client.screenhandler.v1.ScreenRegistry;
import net.fabricmc.fabric.api.event.client.ClientSpriteRegistryCallback;
import net.fabricmc.fabric.api.resource.ResourceManagerHelper;
import net.fabricmc.fabric.api.resource.SimpleSynchronousResourceReloadListener;
import net.minecraft.block.Block;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.texture.Sprite;
import net.minecraft.client.texture.SpriteAtlasTexture;
import net.minecraft.fluid.FlowableFluid;
import net.minecraft.fluid.FluidState;
import net.minecraft.resource.ResourceManager;
import net.minecraft.resource.ResourceType;
import net.minecraft.text.LiteralText;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.registry.Registry;
import net.minecraft.util.registry.RegistryKey;
import net.minecraft.world.BlockRenderView;
import net.minecraft.world.World;
import net.minecraft.world.biome.Biome;

import java.util.Optional;
import java.util.function.Function;

@Environment(EnvType.CLIENT)
public class LintClient implements ClientModInitializer {

	public static void putBlock(Block block, ServerCompatibleRenderLayer layer) {
		RenderLayer renderLayer = null;
		if (layer == ServerCompatibleRenderLayer.CUTOUT) {
			renderLayer = RenderLayer.getCutout();
		}
		BlockRenderLayerMap.INSTANCE.putBlock(block, renderLayer);
	}

	@Override
	public void onInitializeClient() {
		ClientParticles.register();

		EntityRendererRegistry.INSTANCE.register(Entities.TINY_POTATO, (entityRenderDispatcher, context) -> new TinyPotatoEntityRenderer(entityRenderDispatcher));
		EntityRendererRegistry.INSTANCE.register(Entities.BEE_TATER, (entityRenderDispatcher, context) -> new BeeTaterEntityRenderer(entityRenderDispatcher));
		EntityRendererRegistry.INSTANCE.register(Entities.GHOST, (entityRenderDispatcher, context) -> new GhostEntityRenderer(entityRenderDispatcher));
		EntityRendererRegistry.INSTANCE.register(Entities.MINION, (entityRenderDispatcher, context) -> new TinyPotatoEntityRenderer(entityRenderDispatcher));
		EntityRendererRegistry.INSTANCE.register(Entities.KING_TATER, (entityRenderDispatcher, context) -> new KingTaterRenderer(entityRenderDispatcher));
		EntityRendererRegistry.INSTANCE.register(Entities.I5, (entityRenderDispatcher, context) -> new I509VCBRenderer(entityRenderDispatcher));

		ScreenProviderRegistry.INSTANCE.registerFactory(Containers.TATER_CONTAINER_ID, (syncId, identifier, playerEntity, buf) -> new LilTaterContainerScreen(new LilTaterInteractScreenHandler(null, syncId, buf.readInt(), playerEntity.inventory), playerEntity.inventory, new LiteralText("Lil Tater UI")));
		ScreenRegistry.register(ScreenHandlers.SMELTERY, SmelteryScreen::new);

		registerFluidRenderer(Fluids.STILL_MOLTEN_METAL, Fluids.FLOWING_MOLTEN_METAL, new Identifier("water"), 0x44FFFFFF);

		/*ClientTickEvents.START_CLIENT_TICK.register(lexmanos -> {
			long currentTime = System.currentTimeMillis();

			if (currentTime > nextUpdateTime) {
				nextUpdateTime = currentTime + 4000L;
				ClientWorld asie = lexmanos.world;

				if (asie != null) {
					if (asie.getDimension() == Dimensions.HAYKAM) {
						lexmanos.getSoundManager().stopSounds(SoundEvents.MUSIC_CREATIVE.getId(), SoundCategory.MUSIC);
						lexmanos.getSoundManager().stopSounds(SoundEvents.MUSIC_GAME.getId(), SoundCategory.MUSIC);
						lexmanos.getSoundManager().stopSounds(SoundEvents.MUSIC_UNDER_WATER.getId(), SoundCategory.MUSIC);
					}
				}
			}
		});*/
	}

	private void registerFluidRenderer(FlowableFluid still, FlowableFluid flowing, Identifier textureId, int colour) {
		final Identifier stillSpriteId = new Identifier(textureId.getNamespace(), "block/" + textureId.getPath() + "_still");
		final Identifier flowingSpriteId = new Identifier(textureId.getNamespace(), "block/" + textureId.getPath() + "_flow");
		// If they're not already present, add the sprites to the block atlas
		ClientSpriteRegistryCallback.event(SpriteAtlasTexture.BLOCK_ATLAS_TEXTURE).register((atlasTexture, registry) ->
		{
			registry.register(stillSpriteId);
			registry.register(flowingSpriteId);
		});

		final Identifier fluidId = Registry.FLUID.getId(still);
		final Identifier listenerId = new Identifier(fluidId.getNamespace(), fluidId.getPath() + "_reload_listener");
		final Sprite[] fluidSprites = {null, null};

		ResourceManagerHelper.get(ResourceType.CLIENT_RESOURCES).registerReloadListener(new SimpleSynchronousResourceReloadListener() {
			@Override
			public Identifier getFabricId() {
				return listenerId;
			}

			/**
			 * Get the sprites from the block atlas when resources are reloaded
			 */
			@Override
			public void apply(ResourceManager resourceManager) {
				final Function<Identifier, Sprite> atlas = MinecraftClient.getInstance().getSpriteAtlas(SpriteAtlasTexture.BLOCK_ATLAS_TEXTURE);
				fluidSprites[0] = atlas.apply(stillSpriteId);
				fluidSprites[1] = atlas.apply(flowingSpriteId);
			}
		});

		// The FluidRenderer gets the sprites and color from a FluidRenderHandler during rendering
		final FluidRenderHandler renderHandler = new FluidRenderHandler() {
			@Override
			public Sprite[] getFluidSprites(BlockRenderView view, BlockPos pos, FluidState state) {
				return fluidSprites;
			}

			@Override
			public int getFluidColor(BlockRenderView view, BlockPos pos, FluidState state) {
				return colour;
			}
		};

		FluidRenderHandlerRegistry.INSTANCE.register(still, renderHandler);
		FluidRenderHandlerRegistry.INSTANCE.register(flowing, renderHandler);

		// Transparency is cool
		BlockRenderLayerMap.INSTANCE.putFluids(RenderLayer.getTranslucent(), still, flowing);
	}

	public enum ServerCompatibleRenderLayer {
		CUTOUT
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
		if (true) return originalResultChunks;
		Optional<RegistryKey<Biome>> biome = world.getRegistryManager().get(Registry.BIOME_KEY).getKey(world.getBiome(new BlockPos(x, 64, z))); // get biome
		float distChunks = originalResultChunks;

		if (biome.isPresent()) {
			RegistryKey<Biome> aBiome = biome.get();
			// Want an equal experience for all players, so control it directly.
			if (aBiome == Biomes.CORRUPT_FOREST_KEY) {
				distChunks = 3f;
			} else if (aBiome == Biomes.MYSTICAL_FOREST_KEY) {
				distChunks = Math.min(6.2f, 0.43f * originalResultChunks);
			}
		}

		if (distChunks > originalResultChunks) {
			distChunks = originalResultChunks;
		}

		return distChunks;
	}
}
