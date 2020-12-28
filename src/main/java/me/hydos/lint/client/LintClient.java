/*
 * Lint
 * Copyright (C) 2020 hYdos, Valoeghese, ramidzkh
 *
 * This program is free software; you can redistribute it and/or
 * modify it under the terms of the GNU Lesser General Public
 * License as published by the Free Software Foundation; either
 * version 3 of the License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 * Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with this program; if not, write to the Free Software Foundation,
 * Inc., 51 Franklin Street, Fifth Floor, Boston, MA  02110-1301, USA.
 */

package me.hydos.lint.client;

import me.hydos.lint.Lint;
import me.hydos.lint.block.LintBlocks;
import me.hydos.lint.client.entity.model.EasternRosellaModel;
import me.hydos.lint.client.entity.render.*;
import me.hydos.lint.client.particle.ClientParticles;
import me.hydos.lint.entity.Birds;
import me.hydos.lint.entity.Entities;
import me.hydos.lint.fluid.LintFluids;
import me.hydos.lint.network.ClientNetworking;
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
		//if (true) return originalResultChunks;
		Optional<RegistryKey<Biome>> biome = world.getRegistryManager().get(Registry.BIOME_KEY).getKey(world.getBiome(new BlockPos(x, 64, z))); // get biome
		float distChunks = originalResultChunks;

		if (biome.isPresent()) {
			RegistryKey<Biome> aBiome = biome.get();
			// Want an equal experience for all players, so control it directly.
			if (aBiome == Biomes.CORRUPT_FOREST_KEY) {
				distChunks = 3f;
			} else if (aBiome == Biomes.THICK_MYSTICAL_FOREST_KEY || aBiome == Biomes.INDIGO_RIDGES_KEY) {
				distChunks = 0.5f * (3f + Math.min(6.2f, 0.43f * originalResultChunks));
			} else if (aBiome == Biomes.MYSTICAL_FOREST_KEY || aBiome == Biomes.DAWN_SHARDLANDS_KEY) {
				distChunks = Math.min(6.2f, 0.43f * originalResultChunks);
			} else if (aBiome == Biomes.DAWN_SHARDLANDS_EDGE_KEY) {
				distChunks = 0.69f * originalResultChunks;
			}
		}

		if (distChunks > originalResultChunks) {
			distChunks = originalResultChunks;
		}

		return distChunks;
	}

	@Override
	public void onInitializeClient() {
		registerMiscRenderers();
		registerEntityRenderers();
		registerBlockRenderers();
		registerFluidRenderers();
		registerHandledScreens();
	}

	private void registerFluidRenderers() {
		for (LintFluids.FluidEntry entry : LintFluids.MOLTEN_FLUID_MAP.values()) {
			registerFluidRenderer(entry.getStill(), entry.getFlowing(), new Identifier("water"), entry.getColour());
		}
	}

	private void registerBlockRenderers() {
		BlockRenderLayerMap.INSTANCE.putBlock(LintBlocks.CORRUPT_STEM, RenderLayer.getCutout());
		BlockRenderLayerMap.INSTANCE.putBlock(LintBlocks.WILTED_FLOWER, RenderLayer.getCutout());
		BlockRenderLayerMap.INSTANCE.putBlock(LintBlocks.MYSTICAL_GRASS, RenderLayer.getCutout());
		BlockRenderLayerMap.INSTANCE.putBlock(LintBlocks.MYSTICAL_STEM, RenderLayer.getCutout());
		BlockRenderLayerMap.INSTANCE.putBlock(LintBlocks.MYSTICAL_DAISY, RenderLayer.getCutout());
	}

	private void registerHandledScreens() {
		ScreenProviderRegistry.INSTANCE.registerFactory(Lint.id("tater_inv"), (syncId, identifier, playerEntity, buf) -> new LilTaterContainerScreen(new LilTaterInteractScreenHandler(null, syncId, buf.readInt(), playerEntity.inventory), playerEntity.inventory, new LiteralText("")));
		ScreenRegistry.register(ScreenHandlers.SMELTERY, SmelteryScreen::new);
	}

	private void registerEntityRenderers() {
		EntityRendererRegistry.INSTANCE.register(Birds.EASTERN_ROSELLA, (entityRenderDispatcher, context) -> new BirdEntityRenderer(entityRenderDispatcher, new EasternRosellaModel()));

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
}
