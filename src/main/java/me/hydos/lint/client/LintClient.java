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

import lint.mana.alchemy.AlchemyPedestalBlockEntityRenderer;
import me.hydos.lint.block.entity.BlockEntities;
import me.hydos.lint.block.util.BlockBuilder;
import me.hydos.lint.block.util.Layer;
import me.hydos.lint.client.entity.model.EasternRosellaModel;
import me.hydos.lint.client.entity.model.NightClawModel;
import me.hydos.lint.client.entity.model.RedTailedTropicBirdModel;
import me.hydos.lint.client.entity.render.*;
import me.hydos.lint.client.render.block.SmelteryBlockEntityRenderer;
import me.hydos.lint.client.sound.LintSoundManager;
import me.hydos.lint.client.sound.SecurityProblemCauser;
import me.hydos.lint.entity.Birds;
import me.hydos.lint.entity.Entities;
import me.hydos.lint.fluid.LintFluids;
import me.hydos.lint.network.Networking;
import me.hydos.lint.particle.FallenMysticalLeaf;
import me.hydos.lint.particle.Particles;
import me.hydos.lint.screenhandler.ScreenHandlers;
import me.hydos.lint.screenhandler.client.LilTaterScreen;
import me.hydos.lint.screenhandler.client.SmelteryScreen;
import me.hydos.lint.sound.Sounds;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.blockrenderlayer.v1.BlockRenderLayerMap;
import net.fabricmc.fabric.api.client.particle.v1.ParticleFactoryRegistry;
import net.fabricmc.fabric.api.client.render.fluid.v1.FluidRenderHandler;
import net.fabricmc.fabric.api.client.render.fluid.v1.FluidRenderHandlerRegistry;
import net.fabricmc.fabric.api.client.rendereregistry.v1.BlockEntityRendererRegistry;
import net.fabricmc.fabric.api.client.rendereregistry.v1.EntityRendererRegistry;
import net.fabricmc.fabric.api.client.screenhandler.v1.ScreenRegistry;
import net.fabricmc.fabric.api.event.client.ClientSpriteRegistryCallback;
import net.fabricmc.fabric.api.network.ClientSidePacketRegistry;
import net.fabricmc.fabric.api.resource.ResourceManagerHelper;
import net.fabricmc.fabric.api.resource.SimpleSynchronousResourceReloadListener;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.block.entity.BlockEntityRendererFactory;
import net.minecraft.client.texture.Sprite;
import net.minecraft.client.texture.SpriteAtlasTexture;
import net.minecraft.fluid.FlowableFluid;
import net.minecraft.fluid.FluidState;
import net.minecraft.resource.ResourceManager;
import net.minecraft.resource.ResourceType;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.BlockRenderView;

import java.util.function.Function;

public class LintClient implements ClientModInitializer {
	@Override
	@SuppressWarnings("deprecation")
	public void onInitializeClient() {
		registerMiscRenderers();
		registerEntityRenderers();
		registerBlockEntityRenderers();
		registerBlockRendererLayers();
		registerFluidRenderers();
		registerHandledScreens();
		registerParticles();
		registerBossMusic();

		ClientSidePacketRegistry.INSTANCE.register(Networking.TOWN_LOCATIONS, (context, data) -> {
			SecurityProblemCauser.deserialiseLocations(data);
		});

		BlockEntityRendererRegistry.INSTANCE.register(BlockEntities.ALCHEMY_PEDESTAL, AlchemyPedestalBlockEntityRenderer::new);
	}

	/**
	 * Set the boss music in the lint sound manager for the specific entities.
	 */
	private void registerBossMusic() {
		LintSoundManager.setBossMusic(Entities.KING_TATER, Sounds.KING_TATER);
		LintSoundManager.setBossMusic(Entities.I509VCB, Sounds.I509);
	}

	private void registerParticles() {
		ParticleFactoryRegistry.getInstance().register(Particles.FALLEN_MYSTICAL_LEAF, FallenMysticalLeaf.Factory::new);
	}

	private void registerBlockEntityRenderers() {
		BlockEntityRendererRegistry.INSTANCE.register(BlockEntities.SMELTERY, (BlockEntityRendererFactory.Context dispatcher) -> new SmelteryBlockEntityRenderer());
	}

	private void registerFluidRenderers() {
		for (LintFluids.FluidEntry entry : LintFluids.MOLTEN_FLUID_MAP.values()) {
			registerFluidRenderer(entry.getStill(), entry.getFlowing(), new Identifier("lava"), entry.getColour());
		}
	}

	private void registerBlockRendererLayers() {
		BlockBuilder.CUSTOM_BLOCK_RENDER_LAYERS.forEach((block, layer) -> {
			BlockRenderLayerMap.INSTANCE.putBlock(block, getRenderLayer(layer));
		});
	}

	private RenderLayer getRenderLayer(Layer layer) {
		return switch (layer) {
			case CUTOUT_MIPPED -> RenderLayer.getCutoutMipped();
			case TRANSLUCENT -> RenderLayer.getTranslucent();
			default -> RenderLayer.getSolid();
		};
	}

	private void registerHandledScreens() {
		ScreenRegistry.register(ScreenHandlers.TATER_INVENTORY, LilTaterScreen::new);
		ScreenRegistry.register(ScreenHandlers.SMELTERY, SmelteryScreen::new);
	}

	private void registerEntityRenderers() {
		EntityRendererRegistry.INSTANCE.register(Birds.EASTERN_ROSELLA, (context) -> new BirdEntityRenderer(context, new EasternRosellaModel()));
		EntityRendererRegistry.INSTANCE.register(Birds.NIGHTCLAW, (context) -> new BirdEntityRenderer(context, new NightClawModel()));
		EntityRendererRegistry.INSTANCE.register(Birds.RED_TAILED_TROPICBIRD, (context) -> new BirdEntityRenderer(context, new RedTailedTropicBirdModel()));

		EntityRendererRegistry.INSTANCE.register(Entities.TINY_POTATO, TinyPotatoEntityRenderer::new);
		EntityRendererRegistry.INSTANCE.register(Entities.NPC_TINY_POTATO, TinyPotatoEntityRenderer::new);
		EntityRendererRegistry.INSTANCE.register(Entities.BEE_TATER, BeeTaterEntityRenderer::new);
		EntityRendererRegistry.INSTANCE.register(Entities.GHOST, GhostEntityRenderer::new);
		EntityRendererRegistry.INSTANCE.register(Entities.CRAB, CrabEntityRenderer::new);
		EntityRendererRegistry.INSTANCE.register(Entities.MINION, TinyPotatoEntityRenderer::new);
		EntityRendererRegistry.INSTANCE.register(Entities.KING_TATER, KingTaterRenderer::new);
		EntityRendererRegistry.INSTANCE.register(Entities.I509VCB, I509VCBRenderer::new);
		EntityRendererRegistry.INSTANCE.register(Entities.NPC_HUMAN, NPCHumanEntityRenderer::new);
	}

	private void registerMiscRenderers() {
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
			public void reload(ResourceManager resourceManager) {
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
