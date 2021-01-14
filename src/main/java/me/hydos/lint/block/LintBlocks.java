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

import me.hydos.lint.Lint;
import me.hydos.lint.block.organic.*;
import me.hydos.lint.fluid.LintFluids;
import me.hydos.lint.fluid.MoltenMetalFluid;
import me.hydos.lint.item.group.ItemGroups;
import me.hydos.lint.mixin.FireBlockAccessor;
import me.hydos.lint.util.Power;
import me.hydos.lint.world.tree.CorruptTree;
import me.hydos.lint.world.tree.MysticalTree;
import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.fabricmc.fabric.api.tool.attribute.v1.FabricToolTags;
import net.minecraft.block.*;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.fluid.Fluid;
import net.minecraft.item.ItemGroup;
import net.minecraft.sound.BlockSoundGroup;
import net.minecraft.util.math.Direction;
import net.minecraft.util.registry.Registry;
import net.minecraft.util.shape.VoxelShapes;

import java.util.HashMap;

public final class LintBlocks extends LintAutoDataRegistry {
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
	public static final Block CORRUPT_GRASS = new SpreadableBlock(FabricBlockSettings.copy(Blocks.MYCELIUM)) {
	};
	public static final Block LIVELY_GRASS = new SpreadableBlock(FabricBlockSettings.copy(Blocks.GRASS_BLOCK)) {
	};
	public static final Block WASTELAND_GRASS = new Block(FabricBlockSettings.copy(Blocks.GRASS_PATH));

	public static final Block RICH_SOIL = registerSimpleBlockState("rich_soil",
			new FarmlandBlock(FabricBlockSettings.copyOf(Blocks.FARMLAND)) {
			},
			ItemGroups.BLOCKS);

	/**
	 * Yes
	 */

	public static final Block BASIC_CASING = registerCubeAll("basic_casing",
			new Block(FabricBlockSettings.of(Material.STONE)
					.hardness(0.5f)
					.breakByTool(FabricToolTags.PICKAXES, 0)
					.requiresTool()
					.sounds(BlockSoundGroup.STONE)),
			ItemGroups.BLOCKS);

	public static final Block CRACKED_BASIC_CASING = registerCubeAll("cracked_basic_casing",
			new Block(FabricBlockSettings.of(Material.STONE)
					.hardness(0.5f)
					.breakByTool(FabricToolTags.PICKAXES, 0)
					.requiresTool()
					.sounds(BlockSoundGroup.STONE)),
			ItemGroups.BLOCKS);

	public static final Block HAYKAMIUM_PORTAL = registerCubeAll(
			"haykamium_portal",
			new HaykamiumPortalBlock(FabricBlockSettings.of(Material.STONE)
					.strength(-1.0f)
					.sounds(BlockSoundGroup.STONE)
					.collidable(false)),
			null);

	public static final Block CERAMIC = registerCubeAll(
			"ceramic",
			new Block(FabricBlockSettings.copyOf(Blocks.TERRACOTTA)
					.breakByTool(FabricToolTags.PICKAXES, 0)
					.requiresTool()),
			ItemGroups.BLOCKS);

	public static final Block COOKIE = registerCubeAll(
			"cookie",
			new Block(FabricBlockSettings.copyOf(Blocks.CAKE)),
			ItemGroups.FOOD);

	public static final Block GENERIC_BLUE_FLOWER = registerTallCross(
			"generic_blue_flower",
			new LintTallFlowerBlock(FabricBlockSettings.copyOf(Blocks.SUNFLOWER)),
			ItemGroups.DECORATIONS);

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
					.hardness(1f)
					.sounds(BlockSoundGroup.STONE)
					.breakByTool(FabricToolTags.PICKAXES, 0)
					.requiresTool()),
			ItemGroups.BLOCKS);

	public static final Block TARSCAN_ORE = registerCubeAll(
			"tarscan_ore",
			new Block(FabricBlockSettings.of(Material.STONE)
					.hardness(1f)
					.sounds(BlockSoundGroup.STONE)
					.breakByTool(FabricToolTags.PICKAXES, 0)
					.requiresTool()),
			ItemGroups.BLOCKS);

	public static final Block MAGNETITE_DEPOSIT = registerCubeAll(
			"magnetite_deposit",
			new Block(FabricBlockSettings.of(Material.STONE)
					.hardness(1f)
					.sounds(BlockSoundGroup.METAL)
					.breakByTool(FabricToolTags.PICKAXES, 0)
					.requiresTool()),
			ItemGroups.BLOCKS);

	public static final Block SICIERON_ORE = registerCubeAll(
			"sicieron_ore",
			new Block(FabricBlockSettings.of(Material.STONE)
					.hardness(1f)
					.sounds(BlockSoundGroup.STONE)
					.breakByTool(FabricToolTags.PICKAXES, 1)
					.requiresTool()),
			ItemGroups.BLOCKS);

	public static final Block JUREL_ORE = registerCubeAll(
			"jurel_ore",
			new Block(FabricBlockSettings.of(Material.STONE)
					.hardness(1f)
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
					.hardness(4)
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

	/**
	 * Corrupt Building Blocks
	 */
	public static final Block CORRUPT_FALLEN_LEAVES = new FallenLeavesBlock(FabricBlockSettings.of(Material.LEAVES)
			.hardness(0.5f)
			.sounds(BlockSoundGroup.SWEET_BERRY_BUSH).nonOpaque());
	public static final Block CORRUPT_LOG = createLog(MaterialColor.PURPLE, MaterialColor.PURPLE);
	public static final Block CORRUPT_SLAB = registerSlab("corrupt_slab", "corrupt_planks", new SlabBlock(FabricBlockSettings.copy(Blocks.OAK_SLAB)), ItemGroups.BLOCKS);

	/**
	 * Mystical Decorations
	 */
	public static final FlowerBlock MYSTICAL_STEM = new LintFlowerBlock(StatusEffects.RESISTANCE, FabricBlockSettings.of(Material.PLANT)
			.noCollision()
			.breakInstantly()
			.hardness(0)
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
	public static final Block MYSTICAL_LOG = createLog(MaterialColor.DIAMOND, MaterialColor.DIAMOND);
	public static final FlowerBlock MYSTICAL_GRASS = new LintFlowerBlock(StatusEffects.BAD_OMEN, FabricBlockSettings.of(Material.PLANT)
			.noCollision()
			.breakInstantly()
			.hardness(0)
			.sounds(BlockSoundGroup.GRASS)
			.nonOpaque(),
			VoxelShapes.cuboid(0.125, 0.0, 0.125, 0.9375, 0.5, 0.875)
	);
	public static final Block MYSTICAL_SLAB = registerSlab("mystical_slab", "mystical_planks", new SlabBlock(AbstractBlock.Settings.of(Material.WOOD)), ItemGroups.BLOCKS);
	/**
	 * Misc Building Blocks
	 */
	public static final Block DUNGEON_BRICK_SLAB = registerSlab("dungeon_brick_slab", "dungeon_bricks", new SlabBlock(AbstractBlock.Settings.of(Material.WOOD)), ItemGroups.BLOCKS);

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
		fire.callRegisterFlammableBlock(CORRUPT_LOG, 5, 5);
		fire.callRegisterFlammableBlock(MYSTICAL_PLANKS, 5, 20);
		fire.callRegisterFlammableBlock(CORRUPT_PLANKS, 5, 20);
		fire.callRegisterFlammableBlock(MYSTICAL_SLAB, 5, 20);
		fire.callRegisterFlammableBlock(CORRUPT_SLAB, 5, 20);
		fire.callRegisterFlammableBlock(MYSTICAL_LEAVES, 30, 60);
		fire.callRegisterFlammableBlock(CORRUPT_LEAVES, 30, 60);
	}

	public static void registerDecorations() {
		registerCrossPlant(CORRUPT_STEM, "corrupt_stem");
		registerCrossPlant(WILTED_FLOWER, "wilted_flower");
		registerCrossPlant(MYSTICAL_GRASS, "mystical_grass");
		registerCrossPlant(MYSTICAL_STEM, "mystical_stem");
		registerCrossPlant(MYSTICAL_DAISY, "yellow_daisy");
		registerCrossPlant(SPEARMINT, "spearmint");
		registerCrossPlant(WATERMINT, "watermint");
		registerCrossPlant(DILL, "dill");
		registerCrossPlant(KUREI, "kurei");
		registerSimpleBlockState("taterbane", TATERBANE, ItemGroups.DECORATIONS);

		registerCrossPlant(MYSTICAL_SAPLING, "mystical_sapling");
		registerCrossPlant(CORRUPT_SAPLING, "corrupt_sapling");

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
		registerBlock(ItemGroups.BLOCKS, WASTELAND_GRASS, "frosted_grass");

		registerBlock(ItemGroups.BLOCKS, MYSTICAL_LOG, "mystical_log");

		registerBlock(ItemGroups.BLOCKS, CORRUPT_LOG, "corrupt_log");
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

	private static PillarBlock createLog(MaterialColor topMaterialColor, MaterialColor sideMaterialColor) {
		return new PillarBlock(AbstractBlock.Settings.of(Material.WOOD, (blockState) -> blockState.get(PillarBlock.AXIS) == Direction.Axis.Y ? topMaterialColor : sideMaterialColor).strength(2.0F).sounds(BlockSoundGroup.WOOD));
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
