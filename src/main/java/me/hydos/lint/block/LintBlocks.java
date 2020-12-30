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
import me.hydos.lint.fluid.LintFluids;
import me.hydos.lint.fluid.MoltenMetalFluid;
import me.hydos.lint.item.group.ItemGroups;
import me.hydos.lint.mixin.FireBlockAccessor;
import me.hydos.lint.util.Power;
import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.minecraft.block.*;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.fluid.Fluid;
import net.minecraft.item.ItemGroup;
import net.minecraft.sound.BlockSoundGroup;
import net.minecraft.util.math.Direction;
import net.minecraft.util.registry.Registry;

import java.util.HashMap;

public final class LintBlocks extends LintAutoBlockRegistry {

	/**
	 * Automatically generated
	 */
	public static final FabricBlockSettings PLANK_SETTINGS = FabricBlockSettings.of(Material.WOOD, MaterialColor.WOOD)
			.strength(2.0F, 3.0F)
			.sounds(BlockSoundGroup.WOOD);
	public static final FabricBlockSettings SAND_SETTINGS = FabricBlockSettings.of(Material.AGGREGATE)
			.hardness(0.5f)
			.sounds(BlockSoundGroup.SAND);

	public static final Block BASIC_CASING = registerGeneric("basic_casing",
			new Block(FabricBlockSettings.of(Material.STONE)
					.hardness(0.5f)
					.sounds(BlockSoundGroup.STONE)),
			ItemGroups.BLOCKS);
	public static final Block CRACKED_BASIC_CASING = registerGeneric("cracked_basic_casing",
			new Block(FabricBlockSettings.of(Material.STONE)
					.hardness(0.5f)
					.sounds(BlockSoundGroup.STONE)),
			ItemGroups.BLOCKS);

	public static final Block HAYKAMIUM_PORTAL = registerGeneric(
			"haykamium_portal",
			new HaykamiumPortalBlock(FabricBlockSettings.of(Material.STONE)
					.hardness(1f)
					.sounds(BlockSoundGroup.STONE)
					.collidable(false)),
			null);
	public static final Block RED_BUTTON = registerGeneric(
			"red_button",
			new KingTaterButton(FabricBlockSettings.of(Material.SOIL)
					.hardness(-0.1f)
					.sounds(BlockSoundGroup.WET_GRASS)),
			ItemGroups.DECORATIONS);
	public static final Block GREEN_BUTTON = registerGeneric(
			"green_button",
			new KingTaterButton(FabricBlockSettings.of(Material.SOIL)
					.hardness(-0.1f)
					.sounds(BlockSoundGroup.WET_GRASS)),
			ItemGroups.DECORATIONS);

	public static final Block INDIGO_STONE = registerGeneric(
			"indigo_stone",
			new Block(FabricBlockSettings.copyOf(Blocks.STONE)
					.materialColor(MaterialColor.PURPLE_TERRACOTTA)),
			ItemGroups.BLOCKS);
	public static final Block FUSED_STONE = registerGeneric(
			"fused_stone",
			new Block(FabricBlockSettings.of(Material.STONE)
					.hardness(1f)
					.sounds(BlockSoundGroup.STONE)),
			ItemGroups.BLOCKS);
	public static final Block TARSCAN_ORE = registerGeneric(
			"tarscan_ore",
			new Block(FabricBlockSettings.of(Material.STONE)
					.hardness(1f)
					.sounds(BlockSoundGroup.STONE)),
			ItemGroups.BLOCKS);
	public static final Block SICIERON_ORE = registerGeneric(
			"sicieron_ore",
			new Block(FabricBlockSettings.of(Material.STONE)
					.hardness(1f)
					.sounds(BlockSoundGroup.STONE)),
			ItemGroups.BLOCKS);
	public static final Block JUREL_ORE = registerGeneric(
			"jurel_ore",
			new Block(FabricBlockSettings.of(Material.STONE)
					.hardness(1f)
					.sounds(BlockSoundGroup.STONE)),
			ItemGroups.BLOCKS);
	public static final Block ASPHALT = registerGeneric(
			"asphalt",
			new Block(FabricBlockSettings.copyOf(Blocks.STONE)),
			ItemGroups.BLOCKS);

	public static final Block CORRUPT_PLANKS = registerGeneric(
			"corrupt_planks",
			new Block(PLANK_SETTINGS
					.materialColor(MaterialColor.PURPLE)),
			ItemGroups.BLOCKS);
	public static final Block CORRUPT_LEAVES = registerGeneric(
			"corrupt_leaves",
			new LintLeavesBlock(FabricBlockSettings.copyOf(Blocks.OAK_LEAVES)
					.materialColor(MaterialColor.PURPLE)),
			ItemGroups.BLOCKS);
	public static final Block CORRUPT_SAND = registerGeneric(
			"corrupt_sand",
			new FallingBlock(SAND_SETTINGS
					.materialColor(MaterialColor.PURPLE)),
			ItemGroups.BLOCKS);

	public static final Block ALLOS_INFUSED_ASPHALT = registerGeneric(
			"allos_infused_asphalt",
			new InfusedBlock(FabricBlockSettings.copyOf(Blocks.STONE), Power.ALLOS),
			ItemGroups.BLOCKS);
	public static final Block MANOS_INFUSED_ASPHALT = registerGeneric(
			"manos_infused_asphalt",
			new InfusedBlock(FabricBlockSettings.copyOf(Blocks.STONE), Power.MANOS),
			ItemGroups.BLOCKS);

	public static final Block ASH = registerGeneric(
			"ash",
			new FallingBlock(SAND_SETTINGS),
			ItemGroups.BLOCKS);

	public static final Block DUNGEON_BRICKS = registerGeneric(
			"dungeon_bricks",
			new Block(FabricBlockSettings.of(Material.STONE)
					.hardness(4)
					.sounds(BlockSoundGroup.STONE)),
			ItemGroups.BLOCKS);
	public static final Block MOSSY_DUNGEON_BRICKS = registerGeneric(
			"mossy_dungeon_bricks",
			new Block(FabricBlockSettings.of(Material.STONE)
					.hardness(4)
					.sounds(BlockSoundGroup.STONE)),
			ItemGroups.BLOCKS);

	public static final Block MYSTICAL_PLANKS = registerGeneric(
			"mystical_planks",
			new Block(PLANK_SETTINGS
					.materialColor(MaterialColor.DIAMOND)),
			ItemGroups.BLOCKS);
	public static final Block MYSTICAL_LEAVES = registerGeneric(
			"mystical_leaves",
			new LintLeavesBlock(FabricBlockSettings.copyOf(Blocks.OAK_LEAVES)
					.materialColor(MaterialColor.DIAMOND)),
			ItemGroups.BLOCKS);
	public static final Block MYSTICAL_SAND = registerGeneric(
			"mystical_sand",
			new FallingBlock(SAND_SETTINGS
					.materialColor(MaterialColor.MAGENTA)),
			ItemGroups.BLOCKS);

	public static final Block RICH_DIRT = registerGeneric(
			"rich_dirt",
			new Block(FabricBlockSettings.of(Material.SOIL)
					.hardness(0.5f)
					.sounds(BlockSoundGroup.WET_GRASS)),
			ItemGroups.BLOCKS);
	public static final Block WHITE_SAND = registerGeneric(
			"white_sand",
			new FallingBlock(SAND_SETTINGS),
			ItemGroups.BLOCKS);

	/**
	 * Smeltery Related
	 */
	public static final Block SMELTERY = new SmelteryBlock(FabricBlockSettings.of(Material.METAL)
			.hardness(2)
			.sounds(BlockSoundGroup.STONE));
	/**
	 * Misc
	 */
	public static final Block RETURN_HOME = new ReturnHomeBlock(FabricBlockSettings.of(Material.STONE)
			.hardness(-1.0f)
			.sounds(BlockSoundGroup.METAL));
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
	public static final Block CORRUPT_FALLEN_LEAVES = new FallenLeavesBlock(FabricBlockSettings.of(Material.LEAVES)
			.hardness(0.5f)
			.sounds(BlockSoundGroup.SWEET_BERRY_BUSH).nonOpaque());
	public static final Block CORRUPT_LOG = createLog(MaterialColor.PURPLE, MaterialColor.PURPLE);
	public static final Block CORRUPT_GRASS = new Block(FabricBlockSettings.of(Material.SOLID_ORGANIC)
			.hardness(00.5f)
			.sounds(BlockSoundGroup.GRASS));
	public static final Block CORRUPT_SLAB = registerSlab("corrupt_slab", "corrupt_planks", new SlabBlock(FabricBlockSettings.copy(Blocks.OAK_SLAB)), ItemGroups.BLOCKS);

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
	public static final FlowerBlock MYSTICAL_DAISY = new LintGrassBlock(StatusEffects.BAD_OMEN, FabricBlockSettings.of(Material.PLANT)
			.noCollision()
			.breakInstantly()
			.hardness(0)
			.sounds(BlockSoundGroup.GRASS)
			.nonOpaque()
	);
	/**
	 * Mystical Building Blocks
	 */
	public static final Block MYSTICAL_FALLEN_LEAVES = new FallenLeavesBlock(FabricBlockSettings.of(Material.LEAVES)
			.hardness(0.5f)
			.sounds(BlockSoundGroup.SWEET_BERRY_BUSH).nonOpaque());
	public static final Block MYSTICAL_LOG = createLog(MaterialColor.DIAMOND, MaterialColor.DIAMOND);
	public static final FlowerBlock MYSTICAL_GRASS = new LintGrassBlock(StatusEffects.BAD_OMEN, FabricBlockSettings.of(Material.PLANT)
			.noCollision()
			.breakInstantly()
			.hardness(0)
			.sounds(BlockSoundGroup.GRASS)
			.nonOpaque()
	);
	public static final Block MYSTICAL_SLAB = registerSlab("mystical_slab", "mystical_planks", new SlabBlock(AbstractBlock.Settings.of(Material.WOOD)), ItemGroups.BLOCKS);
	/**
	 * Misc Building Blocks
	 */
	public static final Block DUNGEON_BRICK_SLAB = registerSlab("dungeon_brick_slab", "dungeon_bricks", new SlabBlock(AbstractBlock.Settings.of(Material.WOOD)), ItemGroups.BLOCKS);
	public static final Block LIVELY_GRASS = new Block(FabricBlockSettings.copy(Blocks.GRASS_BLOCK));
	/**
	 * Fluid blockstate cache
	 */
	private static final HashMap<MoltenMetalFluid, FluidBlock> FLUID_BLOCKSTATE_MAP = new HashMap<>();

	public static void register() {
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
		registerFlower(CORRUPT_STEM, "corrupt_stem");
		registerFlower(WILTED_FLOWER, "wilted_flower");
		registerFlower(MYSTICAL_GRASS, "mystical_grass");
		registerFlower(MYSTICAL_STEM, "mystical_stem");
		registerFlower(MYSTICAL_DAISY, "yellow_daisy");

		registerBlock(ItemGroups.DECORATIONS, RETURN_HOME, "return_home");

		registerGeneric("mystical_fallen_leaves", MYSTICAL_FALLEN_LEAVES, ItemGroups.DECORATIONS);
		registerGeneric("corrupt_fallen_leaves", CORRUPT_FALLEN_LEAVES, ItemGroups.DECORATIONS);
	}

	public static void registerBuildingBlocks() {
		registerBlock(ItemGroups.BLOCKS, SMELTERY, "smeltery");

		registerBlock(ItemGroups.BLOCKS, CORRUPT_GRASS, "corrupt_grass");
		registerBlock(ItemGroups.BLOCKS, LIVELY_GRASS, "lively_grass");

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

	private static void registerFlower(FlowerBlock flower, String path) {
		registerGeneric(path, flower, ItemGroups.DECORATIONS);
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
