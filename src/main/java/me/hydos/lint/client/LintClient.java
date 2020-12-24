package me.hydos.lint.client;

import me.hydos.lint.block.Blocks;
import me.hydos.lint.client.entity.render.*;
import me.hydos.lint.client.particle.ClientParticles;
import me.hydos.lint.entity.Entities;
import me.hydos.lint.network.ClientNetworking;
import me.hydos.lint.Lint;
import me.hydos.lint.fluid.Fluids;
import me.hydos.lint.screenhandler.LilTaterInteractScreenHandler;
import me.hydos.lint.screenhandler.ScreenHandlers;
import me.hydos.lint.screenhandler.client.LilTaterContainerScreen;
import me.hydos.lint.screenhandler.client.SmelteryScreen;
import me.hydos.lint.world.biome.Biomes;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.blockrenderlayer.v1.BlockRenderLayerMap;
import net.fabricmc.fabric.api.client.render.fluid.v1.FluidRenderHandler;
import net.fabricmc.fabric.api.client.render.fluid.v1.FluidRenderHandlerRegistry;
import net.fabricmc.fabric.api.client.rendereregistry.v1.EntityRendererRegistry;
import net.fabricmc.fabric.api.client.screen.ScreenProviderRegistry;
import net.fabricmc.fabric.api.client.screenhandler.v1.ScreenRegistry;
import net.fabricmc.fabric.api.event.client.ClientSpriteRegistryCallback;
import net.fabricmc.fabric.api.resource.ResourceManagerHelper;
import net.fabricmc.fabric.api.resource.SimpleSynchronousResourceReloadListener;
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

public class LintClient implements ClientModInitializer {

	@Override
	public void onInitializeClient() {
		registerMiscRenderers();
		registerEntityRenderers();
		registerBlockRenderers();
		registerFluidRenderers();
		registerHandledScreens();
	}

	private void registerFluidRenderers() {
		registerFluidRenderer(Fluids.STILL_MOLTEN_METAL, Fluids.FLOWING_MOLTEN_METAL, new Identifier("water"), 0x44FFFFFF);
	}

	private void registerBlockRenderers() {
		BlockRenderLayerMap.INSTANCE.putBlock(Blocks.CORRUPT_STEM, RenderLayer.getCutout());
		BlockRenderLayerMap.INSTANCE.putBlock(Blocks.WILTED_FLOWER, RenderLayer.getCutout());
		BlockRenderLayerMap.INSTANCE.putBlock(Blocks.MYSTICAL_GRASS, RenderLayer.getCutout());
		BlockRenderLayerMap.INSTANCE.putBlock(Blocks.MYSTICAL_STEM, RenderLayer.getCutout());
		BlockRenderLayerMap.INSTANCE.putBlock(Blocks.MYSTICAL_DAISY, RenderLayer.getCutout());
	}

	private void registerHandledScreens() {
		ScreenProviderRegistry.INSTANCE.registerFactory(Lint.id("tater_inv"), (syncId, identifier, playerEntity, buf) -> new LilTaterContainerScreen(new LilTaterInteractScreenHandler(null, syncId, buf.readInt(), playerEntity.inventory), playerEntity.inventory, new LiteralText("Lil Tater UI")));
		ScreenRegistry.register(ScreenHandlers.SMELTERY, SmelteryScreen::new);
	}

	private void registerEntityRenderers() {
		EntityRendererRegistry.INSTANCE.register(Entities.TINY_POTATO, (entityRenderDispatcher, context) -> new TinyPotatoEntityRenderer(entityRenderDispatcher));
		EntityRendererRegistry.INSTANCE.register(Entities.BEE_TATER, (entityRenderDispatcher, context) -> new BeeTaterEntityRenderer(entityRenderDispatcher));
		EntityRendererRegistry.INSTANCE.register(Entities.GHOST, (entityRenderDispatcher, context) -> new GhostEntityRenderer(entityRenderDispatcher));
		EntityRendererRegistry.INSTANCE.register(Entities.MINION, (entityRenderDispatcher, context) -> new TinyPotatoEntityRenderer(entityRenderDispatcher));
		EntityRendererRegistry.INSTANCE.register(Entities.KING_TATER, (entityRenderDispatcher, context) -> new KingTaterRenderer(entityRenderDispatcher));
		EntityRendererRegistry.INSTANCE.register(Entities.I5, (entityRenderDispatcher, context) -> new I509VCBRenderer(entityRenderDispatcher));
	}

	private void registerMiscRenderers() {
		ClientParticles.register();
		ClientNetworking.register();
	}

	private void registerFluidRenderer(FlowableFluid still, FlowableFluid flowing, Identifier textureId, int colour) {
		final Identifier stillSpriteId = new Identifier(textureId.getNamespace(), "block/" + textureId.getPath() + "_still");
		final Identifier flowingSpriteId = new Identifier(textureId.getNamespace(), "block/" + textureId.getPath() + "_flow");
		// If they're not already present, add the sprites to the block atlas
		ClientSpriteRegistryCallback.event(SpriteAtlasTexture.BLOCK_ATLAS_TEXTURE).register((atlasTexture, registry) -> {
			registry.register(stillSpriteId);
			registry.register(flowingSpriteId);
		});

		Identifier fluidId = Registry.FLUID.getId(still);
		Identifier listenerId = new Identifier(fluidId.getNamespace(), fluidId.getPath() + "_reload_listener");
		Sprite[] fluidSprites = {null, null};

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
