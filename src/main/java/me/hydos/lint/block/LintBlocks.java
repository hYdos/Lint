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

package me.hydos.lint.block;

import java.util.HashMap;

import me.hydos.lint.Lint;
import me.hydos.lint.block.organic.DistantLeavesBlock;
import me.hydos.lint.block.organic.FallenLeavesBlock;
import me.hydos.lint.block.organic.LintCorruptGrassBlock;
import me.hydos.lint.block.organic.LintFlowerBlock;
import me.hydos.lint.block.organic.LintLeavesBlock;
import me.hydos.lint.block.organic.LintSaplingBlock;
import me.hydos.lint.block.organic.StrippablePillarBlock;
import me.hydos.lint.block.organic.TaterbaneBlock;
import me.hydos.lint.fluid.LintFluids;
import me.hydos.lint.fluid.MoltenMetalFluid;
import me.hydos.lint.item.group.ItemGroups;
import me.hydos.lint.mixin.FireBlockAccessor;
import me.hydos.lint.refactord.block.organic.LintSpreadableBlock;
import me.hydos.lint.refactord.block.organic.LintTallFlowerBlock;
import me.hydos.lint.util.Power;
import me.hydos.lint.world.tree.CanopyTree;
import me.hydos.lint.world.tree.CorruptTree;
import me.hydos.lint.world.tree.MysticalTree;
import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.fabricmc.fabric.api.tool.attribute.v1.FabricToolTags;
import net.minecraft.block.AbstractBlock;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.FallingBlock;
import net.minecraft.block.FarmlandBlock;
import net.minecraft.block.FlowerBlock;
import net.minecraft.block.FluidBlock;
import net.minecraft.block.Material;
import net.minecraft.block.MaterialColor;
import net.minecraft.block.PillarBlock;
import net.minecraft.block.PlantBlock;
import net.minecraft.block.SaplingBlock;
import net.minecraft.block.SlabBlock;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.fluid.Fluid;
import net.minecraft.item.ItemGroup;
import net.minecraft.sound.BlockSoundGroup;
import net.minecraft.util.math.Direction;
import net.minecraft.util.registry.Registry;
import net.minecraft.util.shape.VoxelShapes;

public final class LintBlocks extends LintDataRegistry {
	/**
	 * Automatically generated
	 */
	public static final FabricBlockSettings PLANK_SETTINGS = FabricBlockSettings.of(Material.WOOD, MaterialColor.WOOD)
			.strength(2.0F, 3.0F)
			.breakByTool(FabricToolTags.AXES, 0)
			.sounds(BlockSoundGroup.WOOD);

	public static final FabricBlockSettings SAND_SETTINGS = FabricBlockSettings.of(Material.AGGREGATE)
			.hardness(0.5f)
			.breakByTool(FabricToolTags.SHOVELS, 0)
			.sounds(BlockSoundGroup.SAND);

	/**
	 * Soils
	 */
	public static final Block CORRUPT_GRASS = new LintSpreadableBlock(FabricBlockSettings.copy(Blocks.MYCELIUM)) {
	}; // DONE
	public static final Block LIVELY_GRASS = new LintSpreadableBlock(FabricBlockSettings.copy(Blocks.GRASS_BLOCK)) {
	}; // DONE
	public static final Block FROSTED_GRASS = new LintSpreadableBlock(FabricBlockSettings.copy(Blocks.GRASS_BLOCK)) {
	}; // DONE

	public static final Block RICH_SOIL = registerSimpleBlockState("rich_soil",
			new FarmlandBlock(FabricBlockSettings.copyOf(Blocks.FARMLAND)) {
			},
			ItemGroups.BLOCKS); // DONE

	/**
	 * Yes
	 */

	public static final Block BASIC_CASING = registerCubeAll("basic_casing",
			new Block(FabricBlockSettings.of(Material.STONE)
					.hardness(0.5f)
					.breakByTool(FabricToolTags.PICKAXES, 0)
					.requiresTool()
					.sounds(BlockSoundGroup.STONE)),
			ItemGroups.BLOCKS); // DONE

	public static final Block CRACKED_BASIC_CASING = registerCubeAll("cracked_basic_casing",
			new Block(FabricBlockSettings.of(Material.STONE)
					.hardness(0.5f)
					.breakByTool(FabricToolTags.PICKAXES, 0)
					.requiresTool()
					.sounds(BlockSoundGroup.STONE)),
			ItemGroups.BLOCKS); // DONE

	public static final Block HAYKAMIUM_PORTAL = registerCubeAll(
			"haykamium_portal",
			new HaykamiumPortalBlock(FabricBlockSettings.of(Material.STONE)
					.strength(-1.0f)
					.sounds(BlockSoundGroup.STONE)
					.collidable(false)),
			null); // DONE

	public static final Block CERAMIC = registerCubeAll(
			"ceramic",
			new Block(FabricBlockSettings.copyOf(Blocks.TERRACOTTA)
					.breakByTool(FabricToolTags.PICKAXES, 0)
					.requiresTool()),
			ItemGroups.BLOCKS); // Done

	public static final Block COOKIE = registerCubeAll(
			"cookie",
			new Block(FabricBlockSettings.copyOf(Blocks.CAKE)),
			ItemGroups.FOOD); // DONE

	public static final Block GENERIC_BLUE_FLOWER = registerTallCross(
			"generic_blue_flower",
			new LintTallFlowerBlock(FabricBlockSettings.copyOf(Blocks.SUNFLOWER).nonOpaque()),
			ItemGroups.DECORATIONS); // doing

	public static final Block INDIGO_STONE = registerCubeAll(
			"indigo_stone",
			new Block(FabricBlockSettings.copyOf(Blocks.STONE)
					.materialColor(MaterialColor.PURPLE_TERRACOTTA)
					.breakByTool(FabricToolTags.PICKAXES, 0)
					.requiresTool()),
			ItemGroups.BLOCKS);

	public static final Block FUSED_STONE = registerCubeAll(
			"fused_stone",
			new Block(FabricBlockSettings.of(Material.STONE)
					.hardness(1.25f)
					.sounds(BlockSoundGroup.STONE)
					.breakByTool(FabricToolTags.PICKAXES, 0)
					.requiresTool()),
			ItemGroups.BLOCKS);
	
	public static final Block FUSED_COBBLESTONE = registerCubeAll(
			"fused_cobblestone",
			new Block(FabricBlockSettings.of(Material.STONE)
					.hardness(1f)
					.sounds(BlockSoundGroup.STONE)
					.breakByTool(FabricToolTags.PICKAXES, 0)
					.requiresTool()),
			ItemGroups.BLOCKS);
	
	public static final Block PEARLESCENT_STONE = registerCubeAll( // This will be used with specific underground sub-biome-like stuff.
			"pearlescent_stone",
			new Block(FabricBlockSettings.of(Material.STONE)
					.hardness(1f)
					.sounds(BlockSoundGroup.STONE)
					.breakByTool(FabricToolTags.PICKAXES, 0)
					.requiresTool()),
			ItemGroups.BLOCKS);

	public static final Block PEARLESCENT_COBBLESTONE = registerCubeAll(
			"pearlescent_cobblestone",
			new Block(FabricBlockSettings.of(Material.STONE)
					.hardness(1f)
					.sounds(BlockSoundGroup.STONE)
					.breakByTool(FabricToolTags.PICKAXES, 0)
					.requiresTool()),
			ItemGroups.BLOCKS);

	public static final Block TARSCAN_ORE = registerCubeAll(
			"tarscan_ore",
			new Block(FabricBlockSettings.of(Material.STONE)
					.hardness(2.0f)
					.sounds(BlockSoundGroup.STONE)
					.breakByTool(FabricToolTags.PICKAXES, 0)
					.requiresTool()),
			ItemGroups.BLOCKS);

	public static final Block MAGNETITE_DEPOSIT = registerCubeAll(
			"magnetite_deposit",
			new Block(FabricBlockSettings.of(Material.STONE)
					.hardness(2.75f)
					.sounds(BlockSoundGroup.METAL)
					.breakByTool(FabricToolTags.PICKAXES, 0)
					.requiresTool()),
			ItemGroups.BLOCKS);

	public static final Block SICIERON_ORE = registerCubeAll(
			"sicieron_ore",
			new Block(FabricBlockSettings.of(Material.STONE)
					.hardness(3.0f)
					.sounds(BlockSoundGroup.STONE)
					.breakByTool(FabricToolTags.PICKAXES, 1)
					.requiresTool()),
			ItemGroups.BLOCKS);

	public static final Block JUREL_ORE = registerCubeAll(
			"jurel_ore",
			new Block(FabricBlockSettings.of(Material.STONE)
					.hardness(3.0f)
					.sounds(BlockSoundGroup.STONE)
					.breakByTool(FabricToolTags.PICKAXES, 2)
					.requiresTool()),
			ItemGroups.BLOCKS);

	public static final Block ASPHALT = registerCubeAll(
			"asphalt",
			new Block(FabricBlockSettings.copyOf(Blocks.STONE)
					.breakByTool(FabricToolTags.PICKAXES, 0)
					.requiresTool()),
			ItemGroups.BLOCKS);

	public static final Block CORRUPT_PLANKS = registerCubeAll(
			"corrupt_planks",
			new Block(PLANK_SETTINGS
					.materialColor(MaterialColor.PURPLE)),
			ItemGroups.BLOCKS);

	public static final Block CORRUPT_LEAVES = registerCubeAll(
			"corrupt_leaves",
			new LintLeavesBlock(FabricBlockSettings.copyOf(Blocks.OAK_LEAVES)
					.materialColor(MaterialColor.PURPLE)
					.breakByTool(FabricToolTags.HOES, 0)),
			ItemGroups.BLOCKS);

	public static final Block CORRUPT_SAND = registerCubeAll(
			"corrupt_sand",
			new FallingBlock(SAND_SETTINGS
					.materialColor(MaterialColor.PURPLE)),
			ItemGroups.BLOCKS);

	public static final Block ALLOS_INFUSED_ASPHALT = registerCubeAll(
			"allos_infused_asphalt",
			new InfusedBlock(FabricBlockSettings.copyOf(Blocks.STONE)
					.breakByTool(FabricToolTags.PICKAXES, 0)
					.requiresTool(), Power.ALLOS),
			ItemGroups.BLOCKS);

	public static final Block MANOS_INFUSED_ASPHALT = registerCubeAll(
			"manos_infused_asphalt",
			new InfusedBlock(FabricBlockSettings.copyOf(Blocks.STONE)
					.breakByTool(FabricToolTags.PICKAXES, 0)
					.requiresTool(), Power.MANOS),
			ItemGroups.BLOCKS);

	public static final Block ASH = registerCubeAll(
			"ash",
			new FallingBlock(SAND_SETTINGS),
			ItemGroups.BLOCKS);

	public static final Block DUNGEON_BRICKS = registerCubeAll(
			"dungeon_bricks",
			new Block(FabricBlockSettings.of(Material.STONE)
					.hardness(6)
					.sounds(BlockSoundGroup.STONE)
					.breakByTool(FabricToolTags.PICKAXES, 2) // yes this means require iron
					.requiresTool()),
			ItemGroups.BLOCKS);

	public static final Block MOSSY_DUNGEON_BRICKS = registerCubeAll(
			"mossy_dungeon_bricks",
			new Block(FabricBlockSettings.of(Material.STONE)
					.hardness(4)
					.sounds(BlockSoundGroup.STONE)
					.breakByTool(FabricToolTags.PICKAXES, 2)
					.requiresTool()),
			ItemGroups.BLOCKS);

	public static final Block MYSTICAL_PLANKS = registerCubeAll(
			"mystical_planks",
			new Block(PLANK_SETTINGS
					.materialColor(MaterialColor.DIAMOND)),
			ItemGroups.BLOCKS);

	public static final Block MYSTICAL_LEAVES = registerCubeAll(
			"mystical_leaves",
			new LintLeavesBlock(FabricBlockSettings.copyOf(Blocks.OAK_LEAVES)
					.materialColor(MaterialColor.DIAMOND)
					.breakByTool(FabricToolTags.HOES, 0)),
			ItemGroups.BLOCKS);

	public static final DistantLeavesBlock CANOPY_LEAVES = registerCubeAll(
			"canopy_leaves",
			new DistantLeavesBlock(9, FabricBlockSettings.copyOf(Blocks.OAK_LEAVES)
					.materialColor(MaterialColor.DIAMOND)
					.breakByTool(FabricToolTags.HOES, 0)),
			ItemGroups.BLOCKS);

	public static final Block FROZEN_LEAVES = registerCubeAll(
			"frozen_leaves",
			new LintLeavesBlock(FabricBlockSettings.copyOf(Blocks.SPRUCE_LEAVES)
					.materialColor(MaterialColor.WHITE)
					.breakByTool(FabricToolTags.HOES, 0)),
			ItemGroups.BLOCKS);

	public static final Block MYSTICAL_SAND = registerCubeAll(
			"mystical_sand",
			new FallingBlock(SAND_SETTINGS
					.materialColor(MaterialColor.MAGENTA)),
			ItemGroups.BLOCKS);

	public static final Block RICH_DIRT = registerCubeAll(
			"rich_dirt",
			new DirtLikeBlock(FabricBlockSettings.of(Material.SOIL)
					.hardness(0.5f)
					.sounds(BlockSoundGroup.WET_GRASS)
					.breakByTool(FabricToolTags.SHOVELS, 0)),
			ItemGroups.BLOCKS);

	public static final Block WHITE_SAND = registerCubeAll(
			"white_sand",
			new FallingBlock(SAND_SETTINGS),
			ItemGroups.BLOCKS);

	/**
	 * Smeltery Related
	 */
	public static final Block SMELTERY = new SmelteryBlock(FabricBlockSettings.of(Material.METAL)
			.hardness(2)
			.sounds(BlockSoundGroup.STONE)
			.breakByTool(FabricToolTags.PICKAXES, 0));
	/**
	 * Misc
	 */
	public static final Block RETURN_HOME = new ReturnHomeBlock(FabricBlockSettings.of(Material.STONE)
			.hardness(-1.0f)
			.sounds(BlockSoundGroup.METAL));

	public static final Block ALLOS_CRYSTAL = new PowerCrystalBlock(FabricBlockSettings.copy(Blocks.GLASS).strength(18.0F, 1200.0F).luminance(state -> 7),
			StatusEffects.GLOWING);
	public static final Block MANOS_CRYSTAL = new PowerCrystalBlock(FabricBlockSettings.copy(Blocks.GLASS).strength(18.0F, 1200.0F).luminance(state -> 4),
			StatusEffects.NAUSEA);

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
	public static final FlowerBlock KUREI = new LintCorruptGrassBlock(StatusEffects.NAUSEA, FabricBlockSettings.of(Material.PLANT)
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
	 * Saplings
	 */
	public static final SaplingBlock MYSTICAL_SAPLING = new LintSaplingBlock(new MysticalTree(), FabricBlockSettings.copyOf(Blocks.ACACIA_SAPLING), LintBlocks.LIVELY_GRASS.getDefaultState());

	public static final SaplingBlock CORRUPT_SAPLING = new LintSaplingBlock(new CorruptTree(), FabricBlockSettings.copyOf(Blocks.ACACIA_SAPLING), LintBlocks.CORRUPT_GRASS.getDefaultState());

	public static final SaplingBlock CANOPY_SAPLING = new LintSaplingBlock(new CanopyTree(), FabricBlockSettings.copyOf(Blocks.ACACIA_SAPLING), LintBlocks.LIVELY_GRASS.getDefaultState());

	/**
	 * Corrupt Building Blocks
	 */
	public static final Block CORRUPT_FALLEN_LEAVES = new FallenLeavesBlock(FabricBlockSettings.of(Material.LEAVES)
			.hardness(0.5f)
			.sounds(BlockSoundGroup.SWEET_BERRY_BUSH).nonOpaque());
	
	public static final Block STRIPPED_CORRUPT_LOG = createLog(MaterialColor.PURPLE, MaterialColor.PURPLE);
	public static final Block CORRUPT_LOG = createLog(MaterialColor.BLACK_TERRACOTTA, MaterialColor.PURPLE, STRIPPED_CORRUPT_LOG);

	public static final Block CORRUPT_SLAB = registerSlab("corrupt_slab", "corrupt_planks", new SlabBlock(FabricBlockSettings.copy(Blocks.OAK_SLAB)), ItemGroups.BLOCKS);

	/**
	 * Mystical Decorations
	 */

	public static final FlowerBlock MYSTICAL_STEM = new LintFlowerBlock(StatusEffects.JUMP_BOOST, FabricBlockSettings.of(Material.PLANT)
			.noCollision()
			.breakInstantly()
			.hardness(0)
			.sounds(BlockSoundGroup.GRASS));

	// Resistance because tussock is somewhat "hard"
	public static final FlowerBlock TUSSOCK = new LintFlowerBlock(StatusEffects.RESISTANCE, FabricBlockSettings.of(Material.PLANT)
			.noCollision()
			.breakInstantly()
			.hardness(0.1f)
			.sounds(BlockSoundGroup.GRASS));

	public static final FlowerBlock RED_TUSSOCK = new LintFlowerBlock(StatusEffects.FIRE_RESISTANCE, FabricBlockSettings.of(Material.PLANT)
			.noCollision()
			.breakInstantly()
			.hardness(0.1f)
			.sounds(BlockSoundGroup.GRASS));

	public static final FlowerBlock MYSTICAL_DAISY = new LintFlowerBlock(StatusEffects.BAD_OMEN, FabricBlockSettings.of(Material.PLANT)
			.noCollision()
			.breakInstantly()
			.hardness(0)
			.sounds(BlockSoundGroup.GRASS));

	public static final Block TATERBANE = new TaterbaneBlock(StatusEffects.NAUSEA, FabricBlockSettings.of(Material.PLANT)
			.noCollision()
			.hardness(0.5f)
			.sounds(BlockSoundGroup.GRASS));

	public static final FlowerBlock SPEARMINT = new LintFlowerBlock(StatusEffects.HASTE, FabricBlockSettings.of(Material.PLANT)
			.noCollision()
			.breakInstantly()
			.hardness(0)
			.sounds(BlockSoundGroup.GRASS));

	public static final FlowerBlock WATERMINT = new LintFlowerBlock(StatusEffects.HASTE, FabricBlockSettings.of(Material.PLANT)
			.noCollision()
			.breakInstantly()
			.hardness(0)
			.sounds(BlockSoundGroup.GRASS));

	public static final FlowerBlock DILL = new LintFlowerBlock(StatusEffects.LUCK, FabricBlockSettings.of(Material.PLANT)
			.noCollision()
			.breakInstantly()
			.hardness(0)
			.sounds(BlockSoundGroup.GRASS));

	/**
	 * Mystical Building Blocks
	 */
	public static final Block MYSTICAL_FALLEN_LEAVES = new FallenLeavesBlock(FabricBlockSettings.of(Material.LEAVES)
			.hardness(0.5f)
			.sounds(BlockSoundGroup.SWEET_BERRY_BUSH).nonOpaque());

	public static final Block STRIPPED_MYSTICAL_LOG = createLog(MaterialColor.DIAMOND, MaterialColor.DIAMOND);
	public static final Block MYSTICAL_LOG = createLog(MaterialColor.WOOD, MaterialColor.DIAMOND, STRIPPED_MYSTICAL_LOG);

	public static final FlowerBlock MYSTICAL_GRASS_PLANT = new LintFlowerBlock(StatusEffects.BAD_OMEN, FabricBlockSettings.of(Material.PLANT)
			.noCollision()
			.breakInstantly()
			.hardness(0)
			.sounds(BlockSoundGroup.GRASS)
			.nonOpaque(),
			VoxelShapes.cuboid(0.125, 0.0, 0.125, 0.9375, 0.5, 0.875)
	);
	public static final Block MYSTICAL_SLAB = registerSlab("mystical_slab", "mystical_planks", new SlabBlock(AbstractBlock.Settings.copy(Blocks.DARK_OAK_SLAB)), ItemGroups.BLOCKS);
	/**
	 * Misc Building Blocks
	 */
	public static final Block DUNGEON_BRICK_SLAB = registerSlab("dungeon_brick_slab", "dungeon_bricks", new SlabBlock(AbstractBlock.Settings.copy(DUNGEON_BRICKS)), ItemGroups.BLOCKS);

	public static final Block MYSTICAL_DOOR = Blocks.OAK_DOOR; // TODO
	/**
	 * Fluid blockstate cache
	 */
	private static final HashMap<MoltenMetalFluid, FluidBlock> FLUID_BLOCKSTATE_MAP = new HashMap<>();

	public static void initialize() {
		registerBuildingBlocks();
		registerDecorations();
		registerFluidBlocks();
		registerFlammableBlocks();
	}

	private static void registerFlammableBlocks() {
		FireBlockAccessor fire = (FireBlockAccessor) Blocks.FIRE;
		fire.callRegisterFlammableBlock(MYSTICAL_LOG, 5, 5);
		fire.callRegisterFlammableBlock(STRIPPED_MYSTICAL_LOG, 5, 5);
		fire.callRegisterFlammableBlock(STRIPPED_CORRUPT_LOG, 5, 5);
		fire.callRegisterFlammableBlock(CORRUPT_LOG, 5, 5);
		fire.callRegisterFlammableBlock(MYSTICAL_PLANKS, 5, 20);
		fire.callRegisterFlammableBlock(CORRUPT_PLANKS, 5, 20);
		fire.callRegisterFlammableBlock(MYSTICAL_SLAB, 5, 20);
		fire.callRegisterFlammableBlock(CORRUPT_SLAB, 5, 20);
		fire.callRegisterFlammableBlock(MYSTICAL_LEAVES, 30, 60);
		fire.callRegisterFlammableBlock(CANOPY_LEAVES, 30, 60);
		fire.callRegisterFlammableBlock(FROZEN_LEAVES, 30, 60);
		fire.callRegisterFlammableBlock(CORRUPT_LEAVES, 30, 60);
	}

	public static void registerDecorations() {
		registerCrossPlant(CORRUPT_STEM, "corrupt_stem");
		registerCrossPlant(WILTED_FLOWER, "wilted_flower");
		registerCrossPlant(MYSTICAL_GRASS_PLANT, "mystical_grass");
		registerCrossPlant(MYSTICAL_STEM, "mystical_stem");
		registerCrossPlant(TUSSOCK, "tussock");
		registerCrossPlant(RED_TUSSOCK, "red_tussock");
		registerCrossPlant(MYSTICAL_DAISY, "yellow_daisy");
		registerCrossPlant(SPEARMINT, "spearmint");
		registerCrossPlant(WATERMINT, "watermint");
		registerCrossPlant(DILL, "dill");
		registerCrossPlant(KUREI, "kurei");
		registerSimpleBlockState("taterbane", TATERBANE, ItemGroups.DECORATIONS);

		registerCrossPlant(MYSTICAL_SAPLING, "mystical_sapling");
		registerCrossPlant(CORRUPT_SAPLING, "corrupt_sapling");
		registerCrossPlant(CANOPY_SAPLING, "canopy_sapling");

		registerBlock(ItemGroups.DECORATIONS, RETURN_HOME, "return_home");

		registerCubeAll("mystical_fallen_leaves", MYSTICAL_FALLEN_LEAVES, ItemGroups.DECORATIONS);
		registerCubeAll("corrupt_fallen_leaves", CORRUPT_FALLEN_LEAVES, ItemGroups.DECORATIONS);
		registerSimpleBlockState("allos_crystal", ALLOS_CRYSTAL, ItemGroups.DECORATIONS);
		registerSimpleBlockState("manos_crystal", MANOS_CRYSTAL, ItemGroups.DECORATIONS);
	}

	public static void registerBuildingBlocks() {
		registerBlock(ItemGroups.BLOCKS, SMELTERY, "smeltery");

		registerBlock(ItemGroups.BLOCKS, CORRUPT_GRASS, "corrupt_grass");
		registerBlock(ItemGroups.BLOCKS, LIVELY_GRASS, "lively_grass");
		registerBlock(ItemGroups.BLOCKS, FROSTED_GRASS, "frosted_grass");

		registerBlock(ItemGroups.BLOCKS, MYSTICAL_LOG, "mystical_log");
		registerBlock(ItemGroups.BLOCKS, STRIPPED_MYSTICAL_LOG, "stripped_mystical_log");

		registerBlock(ItemGroups.BLOCKS, CORRUPT_LOG, "corrupt_log");
		registerBlock(ItemGroups.BLOCKS, STRIPPED_CORRUPT_LOG, "stripped_corrupt_log");
	}

	public static void registerFluidBlocks() {
		for (LintFluids.FluidEntry entry : LintFluids.MOLTEN_FLUID_MAP.values()) {
			LintFluidBlock block = new LintFluidBlock(entry.getStill(), FabricBlockSettings.copy(Blocks.LAVA));
			registerHiddenBlock(block, "molten_" + entry.getMetalName());
			FLUID_BLOCKSTATE_MAP.put((MoltenMetalFluid) entry.getStill(), block);
		}
	}

	private static void registerHiddenBlock(Block block, String path) {
		Registry.register(Registry.BLOCK, Lint.id(path), block);
	}

	private static PillarBlock createLog(MaterialColor topMaterialColor, MaterialColor sideMaterialColor, Block stripped) {
		return new StrippablePillarBlock(AbstractBlock.Settings.of(Material.WOOD, (blockState) -> blockState.get(PillarBlock.AXIS) == Direction.Axis.Y ? topMaterialColor : sideMaterialColor)
				.strength(2.0F)
				.sounds(BlockSoundGroup.WOOD),
				stripped);
	}

	private static PillarBlock createLog(MaterialColor topMaterialColor, MaterialColor sideMaterialColor) {
		return new PillarBlock(AbstractBlock.Settings.of(Material.WOOD, (blockState) -> blockState.get(PillarBlock.AXIS) == Direction.Axis.Y ? topMaterialColor : sideMaterialColor)
				.strength(2.0F)
				.sounds(BlockSoundGroup.WOOD));
	}

	private static void registerBlock(ItemGroup itemGroup, Block block, String path) {
		registerHiddenBlock(block, path);
		registerBlockItem(block, itemGroup);
	}

	private static void registerCrossPlant(PlantBlock flower, String path) {
		registerCross(path, flower, ItemGroups.DECORATIONS);
	}

	public static BlockState getFluid(Fluid still) {
		for (MoltenMetalFluid fluid : FLUID_BLOCKSTATE_MAP.keySet()) {
			if (still.equals(fluid)) {
				return FLUID_BLOCKSTATE_MAP.get(fluid).getDefaultState();
			}
		}
		throw new RuntimeException("Cannot find fluid!");
	}
}
