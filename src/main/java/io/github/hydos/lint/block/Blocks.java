package io.github.hydos.lint.block;

import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.minecraft.block.Block;
import net.minecraft.block.FallingBlock;
import net.minecraft.block.LeavesBlock;
import net.minecraft.block.Material;
import net.minecraft.block.MaterialColor;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.sound.BlockSoundGroup;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

public class Blocks implements ModInitializer {
	public static final Block CORRUPT_STEM = new LintCorruptGrassBlock(StatusEffects.NAUSEA, FabricBlockSettings.of(Material.PLANT)
			.noCollision()
			.breakInstantly()
			.hardness(0)
			.sounds(BlockSoundGroup.GRASS)
			.nonOpaque()
			);

	public static final Block WILTED_FLOWER = new LintCorruptGrassBlock(StatusEffects.POISON, FabricBlockSettings.of(Material.PLANT)
			.noCollision()
			.breakInstantly()
			.hardness(0)
			.sounds(BlockSoundGroup.GRASS)
			.nonOpaque()
			);

	public static final Block MYSTICAL_STEM = new LintGrassBlock(StatusEffects.RESISTANCE, FabricBlockSettings.of(Material.PLANT)
			.noCollision()
			.breakInstantly()
			.hardness(0)
			.sounds(BlockSoundGroup.GRASS)
			.nonOpaque()
			);

	public static final Block YELLOW_DAISY = new LintGrassBlock(StatusEffects.BAD_OMEN, net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings.of(Material.PLANT)
			.noCollision()
			.breakInstantly()
			.hardness(0)
			.sounds(BlockSoundGroup.GRASS)
			.nonOpaque());
	public static final Block RETURN_HOME = new ReturnHomeBlock(FabricBlockSettings.of(Material.STONE).hardness(-1.0f).sounds(BlockSoundGroup.METAL));

	public static final Block RED_BUTTON = new KingTaterButton(FabricBlockSettings.of(Material.SOIL).hardness(-0.1f).sounds(BlockSoundGroup.WET_GRASS));

	public static final Block GREEN_BUTTON = new KingTaterButton(FabricBlockSettings.of(Material.SOIL).hardness(-0.1f).sounds(BlockSoundGroup.WET_GRASS));

	public static final Block MYSTICAL_TRAPDOOR = new TrapdoorBlock(FabricBlockSettings.of(Material.WOOD).hardness(2).sounds(BlockSoundGroup.WOOD));

	public static final Block DUNGEON_BRICKS = new Block(FabricBlockSettings.of(Material.STONE).hardness(4).sounds(BlockSoundGroup.STONE));

	public static final Block.Settings PLANK_SETTINGS = FabricBlockSettings.of(Material.WOOD, MaterialColor.WOOD).strength(2.0F, 3.0F).sounds(BlockSoundGroup.WOOD);
	public static final Block.Settings SAND_SETTINGS = FabricBlockSettings.of(Material.AGGREGATE).hardness(0.5f).sounds(BlockSoundGroup.SAND);

	public static final Block WHITE_SAND = new FallingBlock(SAND_SETTINGS);

	public static final Block CORRUPT_PLANKS = new Block(PLANK_SETTINGS);

	public static final Block MYSTICAL_PLANKS = new Block(PLANK_SETTINGS);

	public static final Block RICH_DIRT = new Block(FabricBlockSettings.of(Material.SOIL).hardness(0.5f).sounds(BlockSoundGroup.WET_GRASS));
	public static final Block LIVELY_GRASS = new Block(FabricBlockSettings.of(Material.SOIL).hardness(0.5f).sounds(BlockSoundGroup.GRASS));

	public static final Block MYSTICAL_LEAVES = new LeavesBlock(FabricBlockSettings.of(Material.SOLID_ORGANIC).hardness(0.5f).sounds(BlockSoundGroup.SWEET_BERRY_BUSH).nonOpaque());
	public static final Block MYSTICAL_LOG = new Block(FabricBlockSettings.of(Material.WOOD).hardness(2).sounds(BlockSoundGroup.WOOD));
	public static final Block MYSTICAL_GRASS = new LintGrassBlock(StatusEffects.BAD_OMEN, FabricBlockSettings.of(Material.PLANT)
			.noCollision()
			.breakInstantly()
			.hardness(0)
			.sounds(BlockSoundGroup.GRASS)
			.nonOpaque()
			);
	public static final Block MYSTICAL_SAND = new FallingBlock(SAND_SETTINGS);

	public static final Block CORRUPT_LEAVES = new LeavesBlock(FabricBlockSettings.of(Material.SOLID_ORGANIC).hardness(0.5f).sounds(BlockSoundGroup.SWEET_BERRY_BUSH).nonOpaque());
	public static final Block CORRUPT_LOG = new Block(FabricBlockSettings.of(Material.WOOD).hardness(2).sounds(BlockSoundGroup.WOOD));
	public static final Block CORRUPT_GRASS = new Block(FabricBlockSettings.of(Material.SOLID_ORGANIC).hardness(00.5f).sounds(BlockSoundGroup.GRASS));
	public static final Block CORRUPT_SAND = new FallingBlock(SAND_SETTINGS);
	
	@Override
	public void onInitialize() {
		registerBlock(ItemGroup.BUILDING_BLOCKS, CORRUPT_STEM, "corrupt_stem");
		registerBlock(ItemGroup.BUILDING_BLOCKS, WILTED_FLOWER, "wilted_flower");

		registerBlock(ItemGroup.BUILDING_BLOCKS, MYSTICAL_STEM, "mystical_stem");

		registerBlock(ItemGroup.BUILDING_BLOCKS, YELLOW_DAISY, "yellow_daisy");
		registerBlock(ItemGroup.BUILDING_BLOCKS, RED_BUTTON, "red_button");

		registerBlock(ItemGroup.BUILDING_BLOCKS, GREEN_BUTTON, "green_button");

		registerBlock(ItemGroup.BUILDING_BLOCKS, MYSTICAL_TRAPDOOR, "mystical_trapdoor");

		registerBlock(ItemGroup.BUILDING_BLOCKS, DUNGEON_BRICKS, "dungeon_bricks");

		registerBlock(ItemGroup.BUILDING_BLOCKS, WHITE_SAND, "white_sand");

		registerBlock(ItemGroup.BUILDING_BLOCKS, CORRUPT_PLANKS, "corrupt_planks");

		registerBlock(ItemGroup.BUILDING_BLOCKS, MYSTICAL_PLANKS, "mystical_planks");
		registerBlock(ItemGroup.BUILDING_BLOCKS, RICH_DIRT, "rich_dirt");
		registerBlock(ItemGroup.BUILDING_BLOCKS, LIVELY_GRASS, "lively_grass");

		registerBlock(ItemGroup.BUILDING_BLOCKS, MYSTICAL_LEAVES, "mystical_leaves");
		registerBlock(ItemGroup.BUILDING_BLOCKS, MYSTICAL_LOG, "mystical_log");
		registerBlock(ItemGroup.DECORATIONS, MYSTICAL_GRASS, "mystical_grass");
		registerBlock(ItemGroup.BUILDING_BLOCKS, MYSTICAL_SAND, "mystical_sand");

		registerBlock(ItemGroup.BUILDING_BLOCKS, CORRUPT_LEAVES, "corrupt_leaves");
		registerBlock(ItemGroup.BUILDING_BLOCKS, CORRUPT_LOG, "corrupt_log");
		registerBlock(ItemGroup.BUILDING_BLOCKS, CORRUPT_GRASS, "corrupt_grass");
		registerBlock(ItemGroup.BUILDING_BLOCKS, CORRUPT_SAND, "corrupt_sand");

		registerBlock(ItemGroup.BUILDING_BLOCKS, RETURN_HOME, "return_home");
	}

	static void registerBlock(ItemGroup itemGroup, Block block, String identifier) {
		Registry.register(Registry.BLOCK, new Identifier("lint", identifier), block);
		Registry.register(Registry.ITEM, new Identifier("lint", identifier), new BlockItem(block, new Item.Settings().group(itemGroup)));
	}
}
