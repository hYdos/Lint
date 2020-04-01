package me.hydos.lint;

import me.hydos.lint.blocks.AdventureTransformerBlockEntity;
import me.hydos.lint.blocks.AdventureTransformerGui;
import me.hydos.lint.blocks.AdventureTransformerRecipe;
import me.hydos.lint.registers.BiomeRegister;
import me.hydos.lint.registers.BlockRegister;
import me.hydos.lint.registers.DimensionRegister;
import me.hydos.lint.entities.liltaterbattery.LilTaterBattery;
import me.hydos.techrebornApi.TechRebornApi;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.block.FabricBlockSettings;
import net.fabricmc.fabric.api.entity.FabricEntityTypeBuilder;
import net.fabricmc.fabric.api.network.ServerSidePacketRegistry;
import net.minecraft.block.Block;
import net.minecraft.block.Material;
import net.minecraft.entity.EntityCategory;
import net.minecraft.entity.EntityDimensions;
import net.minecraft.entity.EntityType;
import net.minecraft.item.ItemGroup;
import net.minecraft.sound.BlockSoundGroup;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;
import techreborn.client.GuiType;


public class Lint implements ModInitializer {

	public static final GuiType<AdventureTransformerBlockEntity> ADVENTURE_TRANSFORMER_GUI = GuiType.register(new Identifier("adventure_transformer"), () -> () -> AdventureTransformerGui::new);

	public static final Identifier PLAY_DIMENSION_CHANGE_PACKET_ID = new Identifier("lint", "change_dimension");

	public static final EntityType<LilTaterBattery> LIL_TATER =
			Registry.register(
					Registry.ENTITY_TYPE,
					new Identifier("lint", "lil_tater"),
					FabricEntityTypeBuilder.<LilTaterBattery>create(EntityCategory.AMBIENT, ((type, world) -> new LilTaterBattery(world)))
							.size(EntityDimensions.fixed(0.3f, 0.4f))
							.build()
			);

    @Override
	public void onInitialize() {

		TechRebornApi.registerBlock("lint", "adventure_transformer", AdventureTransformerRecipe.class, ItemGroup.TOOLS, AdventureTransformerBlockEntity::new, ADVENTURE_TRANSFORMER_GUI);

		BlockRegister.registerBlocks(ItemGroup.BUILDING_BLOCKS, new Block(FabricBlockSettings.of(Material.EARTH).hardness(0.5f).sounds(BlockSoundGroup.GRASS).build()), "lively_grass");
		BlockRegister.registerBlocks(ItemGroup.BUILDING_BLOCKS, new Block(FabricBlockSettings.of(Material.EARTH).hardness(0.5f).sounds(BlockSoundGroup.GRASS).build()), "rich_dirt");
		BiomeRegister.registerBiomes();
		DimensionRegister.register();



		ServerSidePacketRegistry.INSTANCE.register(PLAY_DIMENSION_CHANGE_PACKET_ID, (packetContext, packetByteBuf) ->{

			String dimension = packetByteBuf.readString();
			if(dimension.equalsIgnoreCase("HAYKAM")){
				packetContext.getTaskQueue().execute(() -> {
					packetContext.getPlayer().changeDimension(DimensionRegister.HAYKAM);
					packetContext.getPlayer().openContainer(null);

				});
			}

		});

	}
}
