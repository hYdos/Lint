package me.hydos.lint.block;

import java.util.HashMap;

import me.hydos.lint.Lint;
import me.hydos.lint.fluid.Fluids;
import me.hydos.lint.fluid.MoltenMetalFluid;
import me.hydos.lint.item.group.ItemGroups;
import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.minecraft.block.AbstractBlock;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.FallingBlock;
import net.minecraft.block.FlowerBlock;
import net.minecraft.block.FluidBlock;
import net.minecraft.block.Material;
import net.minecraft.block.MaterialColor;
import net.minecraft.block.PillarBlock;
import net.minecraft.block.SlabBlock;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.fluid.Fluid;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.sound.BlockSoundGroup;
import net.minecraft.util.math.Direction;
import net.minecraft.util.registry.Registry;

public final class Blocks {

	/**
	 * Fluid blockstate cache
	 */
	private static final HashMap<MoltenMetalFluid, FluidBlock> FLUID_BLOCKSTATE_MAP = new HashMap<>();

	/**
	 * Bindings for easy access to minecraft blocks
	 */
	public static final Block WATER = net.minecraft.block.Blocks.WATER;
	public static final Block AIR = net.minecraft.block.Blocks.AIR;
	public static final Block BEDROCK = net.minecraft.block.Blocks.BEDROCK;

	/**
	 * Block Settings
	 */
	public static final Block.Settings PLANK_SETTINGS = FabricBlockSettings.of(Material.WOOD, MaterialColor.WOOD)
			.strength(2.0F, 3.0F)
			.sounds(BlockSoundGroup.WOOD);
	public static final Block.Settings SAND_SETTINGS = FabricBlockSettings.of(Material.AGGREGATE)
			.hardness(0.5f)
			.sounds(BlockSoundGroup.SAND);

	/**
	 * Smeltery Related
	 */
	public static final Block SMELTERY = new SmelteryBlock(FabricBlockSettings.of(Material.METAL)
			.hardness(2)
			.sounds(BlockSoundGroup.STONE));
	public static final Block CRACKED_BASIC_CASTING = new Block(FabricBlockSettings.of(Material.STONE)
			.hardness(0.5f)
			.sounds(BlockSoundGroup.STONE));
	public static final Block BASIC_CASTING = new Block(FabricBlockSettings.of(Material.STONE)
			.hardness(0.5f)
			.sounds(BlockSoundGroup.STONE));

	/**
	 * Misc
	 */
	public static final Block HAYKAMIUM_PORTAL = new HaykamiumPortalBlock(FabricBlockSettings.of(Material.STONE)
			.hardness(1f)
			.sounds(BlockSoundGroup.STONE)
			.collidable(false));
	public static final Block RETURN_HOME = new ReturnHomeBlock(FabricBlockSettings.of(Material.STONE)
			.hardness(-1.0f)
			.sounds(BlockSoundGroup.METAL));
	public static final Block RED_BUTTON = new KingTaterButton(FabricBlockSettings.of(Material.SOIL)
			.hardness(-0.1f)
			.sounds(BlockSoundGroup.WET_GRASS));
	public static final Block GREEN_BUTTON = new KingTaterButton(FabricBlockSettings.of(Material.SOIL)
			.hardness(-0.1f)
			.sounds(BlockSoundGroup.WET_GRASS));

	/**
	 * Stone & Ores
	 */
	public static final Block FUSED_STONE = new Block(FabricBlockSettings.of(Material.STONE)
			.hardness(1f)
			.sounds(BlockSoundGroup.STONE));
	public static final Block TARSCAN = new Block(FabricBlockSettings.of(Material.STONE)
			.hardness(1f)
			.sounds(BlockSoundGroup.STONE));
	public static final Block SICIERON = new Block(FabricBlockSettings.of(Material.STONE)
			.hardness(1f)
			.sounds(BlockSoundGroup.STONE));
	public static final Block JUREL = new Block(FabricBlockSettings.of(Material.STONE)
			.hardness(1f)
			.sounds(BlockSoundGroup.STONE));


	/**
	 * Corrupt Decorations
	 */
	public static final FlowerBlock CORRUPT_STEM = new LintCorruptGrassBlock(StatusEffects.NAUSEA, FabricBlockSettings.of(Material.PLANT)
			.noCollision()
			.breakInstantly()
			.hardness(0)
			.sounds(BlockSoundGroup.GRASS)
			.nonOpaque()
	);
	public static final FlowerBlock WILTED_FLOWER = new LintCorruptGrassBlock(StatusEffects.POISON, FabricBlockSettings.of(Material.PLANT)
			.noCollision()
			.breakInstantly()
			.hardness(0)
			.sounds(BlockSoundGroup.GRASS)
			.nonOpaque()
	);

	/**
	 * Corrupt Building Blocks
	 */
	public static final Block CORRUPT_PLANKS = new Block(PLANK_SETTINGS);
	public static final Block CORRUPT_LEAVES = new LintLeavesBlock(FabricBlockSettings.of(Material.SOLID_ORGANIC)
			.hardness(0.5f)
			.sounds(BlockSoundGroup.SWEET_BERRY_BUSH).nonOpaque());
	public static final Block CORRUPT_FALLEN_LEAVES = new FallenLeavesBlock(FabricBlockSettings.of(Material.SOLID_ORGANIC)
			.hardness(0.5f)
			.sounds(BlockSoundGroup.SWEET_BERRY_BUSH).nonOpaque());
	public static final Block CORRUPT_LOG = createLog(MaterialColor.PURPLE, MaterialColor.PURPLE);
	public static final Block CORRUPT_GRASS = new Block(FabricBlockSettings.of(Material.SOLID_ORGANIC)
			.hardness(00.5f)
			.sounds(BlockSoundGroup.GRASS));
	public static final Block CORRUPT_SAND = new FallingBlock(SAND_SETTINGS);
	public static final Block CORRUPT_SLAB = new SlabBlock(AbstractBlock.Settings.of(Material.WOOD));

	/**
	 * Mystical Decorations
	 */
	public static final FlowerBlock MYSTICAL_STEM = new LintGrassBlock(StatusEffects.RESISTANCE, FabricBlockSettings.of(Material.PLANT)
			.noCollision()
			.breakInstantly()
			.hardness(0)
			.sounds(BlockSoundGroup.GRASS)
			.nonOpaque()
	);

	public static final FlowerBlock MYSTICAL_DAISY = new LintGrassBlock(StatusEffects.BAD_OMEN, net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings.of(Material.PLANT)
			.noCollision()
			.breakInstantly()
			.hardness(0)
			.sounds(BlockSoundGroup.GRASS)
			.nonOpaque()
	);

	/**
	 * Mystical Building Blocks
	 */
	public static final Block MYSTICAL_TRAPDOOR = new TrapdoorBlock(FabricBlockSettings.of(Material.WOOD)
			.hardness(2)
			.sounds(BlockSoundGroup.WOOD));
	public static final Block MYSTICAL_LEAVES = new LintLeavesBlock(FabricBlockSettings.of(Material.SOLID_ORGANIC)
			.hardness(0.5f)
			.sounds(BlockSoundGroup.SWEET_BERRY_BUSH).nonOpaque());
	public static final Block MYSTICAL_FALLEN_LEAVES = new FallenLeavesBlock(FabricBlockSettings.of(Material.SOLID_ORGANIC)
			.hardness(0.5f)
			.sounds(BlockSoundGroup.SWEET_BERRY_BUSH).nonOpaque());
	public static final Block MYSTICAL_LOG = createLog(MaterialColor.LIME_TERRACOTTA, MaterialColor.LIME_TERRACOTTA);
	public static final Block MYSTICAL_PLANKS = new Block(PLANK_SETTINGS);
	public static final FlowerBlock MYSTICAL_GRASS = new LintGrassBlock(StatusEffects.BAD_OMEN, FabricBlockSettings.of(Material.PLANT)
			.noCollision()
			.breakInstantly()
			.hardness(0)
			.sounds(BlockSoundGroup.GRASS)
			.nonOpaque()
	);
	public static final Block MYSTICAL_SAND = new FallingBlock(SAND_SETTINGS);
	public static final Block MYSTICAL_SLAB = new SlabBlock(AbstractBlock.Settings.of(Material.WOOD));
	public static final Block WHITE_SAND = new FallingBlock(SAND_SETTINGS);

	/**
	 * Misc Building Blocks
	 */
	public static final Block DUNGEON_BRICK_SLAB = new SlabBlock(AbstractBlock.Settings.of(Material.WOOD));
	public static final Block DUNGEON_BRICKS = new Block(FabricBlockSettings.of(Material.STONE)
			.hardness(4)
			.sounds(BlockSoundGroup.STONE));
	public static final Block MOSSY_DUNGEON_BRICKS = new Block(FabricBlockSettings.of(Material.STONE)
			.hardness(4)
			.sounds(BlockSoundGroup.STONE));
	public static final Block RICH_DIRT = new Block(FabricBlockSettings.of(Material.SOIL)
			.hardness(0.5f)
			.sounds(BlockSoundGroup.WET_GRASS));
	public static final Block LIVELY_GRASS = new Block(FabricBlockSettings.of(Material.SOIL)
			.hardness(0.5f)
			.sounds(BlockSoundGroup.GRASS));
	public static final Block INDIGO_STONE = new Block(FabricBlockSettings.copyOf(net.minecraft.block.Blocks.STONE).materialColor(MaterialColor.PURPLE_TERRACOTTA));

	public static void register() {
		registerBuildingBlocks();
		registerDecorations();
		registerFluidBlocks();
	}

	public static void registerDecorations() {
		registerHiddenBlock(HAYKAMIUM_PORTAL, "haykamium_portal");

		registerFlower(CORRUPT_STEM, "corrupt_stem");
		registerFlower(WILTED_FLOWER, "wilted_flower");
		registerFlower(MYSTICAL_GRASS, "mystical_grass");
		registerFlower(MYSTICAL_STEM, "mystical_stem");
		registerFlower(MYSTICAL_DAISY, "yellow_daisy");

		registerBlock(ItemGroups.DECORATIONS, RED_BUTTON, "red_button");
		registerBlock(ItemGroups.DECORATIONS, GREEN_BUTTON, "green_button");

		registerBlock(ItemGroups.DECORATIONS, RETURN_HOME, "return_home");

		registerBlock(ItemGroups.DECORATIONS, MYSTICAL_FALLEN_LEAVES, "mystical_fallen_leaves");
		registerBlock(ItemGroups.DECORATIONS, CORRUPT_FALLEN_LEAVES, "corrupt_fallen_leaves");
	}

	public static void registerBuildingBlocks() {
		registerBlock(ItemGroups.BLOCKS, SMELTERY, "smeltery");
		registerBlock(ItemGroups.BLOCKS, BASIC_CASTING, "basic_casting");
		registerBlock(ItemGroups.BLOCKS, CRACKED_BASIC_CASTING, "cracked_basic_casting");

		registerBlock(ItemGroups.BLOCKS, FUSED_STONE, "fused_stone");
		registerBlock(ItemGroups.BLOCKS, TARSCAN, "tarscan_ore");
		registerBlock(ItemGroups.BLOCKS, SICIERON, "sicieron_ore");
		registerBlock(ItemGroups.BLOCKS, JUREL, "jurel_ore");

		registerBlock(ItemGroups.BLOCKS, MYSTICAL_TRAPDOOR, "mystical_trapdoor");

		registerBlock(ItemGroups.BLOCKS, DUNGEON_BRICKS, "dungeon_bricks");
		registerBlock(ItemGroups.BLOCKS, MOSSY_DUNGEON_BRICKS, "mossy_dungeon_bricks");

		registerBlock(ItemGroups.BLOCKS, WHITE_SAND, "white_sand");
		registerBlock(ItemGroups.BLOCKS, CORRUPT_SAND, "corrupt_sand");

		registerBlock(ItemGroups.BLOCKS, RICH_DIRT, "rich_dirt");
		registerBlock(ItemGroups.BLOCKS, CORRUPT_GRASS, "corrupt_grass");
		registerBlock(ItemGroups.BLOCKS, LIVELY_GRASS, "lively_grass");

		registerBlock(ItemGroups.BLOCKS, MYSTICAL_PLANKS, "mystical_planks");
		registerBlock(ItemGroups.BLOCKS, MYSTICAL_LEAVES, "mystical_leaves");
		registerBlock(ItemGroups.BLOCKS, MYSTICAL_LOG, "mystical_log");
		registerBlock(ItemGroups.BLOCKS, MYSTICAL_SAND, "mystical_sand");

		registerBlock(ItemGroups.BLOCKS, CORRUPT_PLANKS, "corrupt_planks");
		registerBlock(ItemGroups.BLOCKS, CORRUPT_LEAVES, "corrupt_leaves");
		registerBlock(ItemGroups.BLOCKS, CORRUPT_LOG, "corrupt_log");

		registerBlock(ItemGroups.BLOCKS, MYSTICAL_SLAB, "mystical_slab");
		registerBlock(ItemGroups.BLOCKS, CORRUPT_SLAB, "corrupt_slab");
		registerBlock(ItemGroups.BLOCKS, DUNGEON_BRICK_SLAB, "dungeon_brick_slab");
	}

	public static void registerFluidBlocks() {
		for (Fluids.FluidEntry entry : Fluids.MOLTEN_FLUID_MAP.values()) {
			LintFluidBlock block = new LintFluidBlock(entry.getStill(), FabricBlockSettings.copy(net.minecraft.block.Blocks.LAVA));
			registerHiddenBlock(block, "molten_" + entry.getMetalName());
			FLUID_BLOCKSTATE_MAP.put((MoltenMetalFluid) entry.getStill(), block);
		}
	}

	private static void registerHiddenBlock(Block block, String path) {
		Registry.register(Registry.BLOCK, Lint.id(path), block);
	}

	private static PillarBlock createLog(MaterialColor topMaterialColor, MaterialColor sideMaterialColor) {
		return new PillarBlock(AbstractBlock.Settings.of(Material.WOOD, (blockState) -> blockState.get(PillarBlock.AXIS) == Direction.Axis.Y ? topMaterialColor : sideMaterialColor).strength(2.0F).sounds(BlockSoundGroup.WOOD));
	}

	private static void registerBlock(ItemGroup itemGroup, Block block, String path) {
		registerHiddenBlock(block, path);
		Registry.register(Registry.ITEM, Lint.id(path), new BlockItem(block, new Item.Settings().group(itemGroup)));
	}

	private static void registerFlower(FlowerBlock flower, String path) {
		registerBlock(ItemGroups.DECORATIONS, flower, path);
	}

	public static BlockState getFluid(Fluid still) {
		for (MoltenMetalFluid fluid : FLUID_BLOCKSTATE_MAP.keySet()) {
			if(still.equals(fluid)) {
				return FLUID_BLOCKSTATE_MAP.get(fluid).getDefaultState();
			}
		}
		throw new RuntimeException("Cannot find fluid!");
	}
}
