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
import me.hydos.lint.item.group.LintItemGroups;
import me.hydos.lint.mixin.FireBlockAccessor;
import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.minecraft.block.*;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.fluid.Fluid;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.sound.BlockSoundGroup;
import net.minecraft.util.math.Direction;
import net.minecraft.util.registry.Registry;

import java.util.HashMap;

public final class LintBlocks extends LintBlocks2 {
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
	public static final Block MYSTICAL_LOG = createLog(MaterialColor.LIME_TERRACOTTA, MaterialColor.LIME_TERRACOTTA);
	public static final FlowerBlock MYSTICAL_GRASS = new LintGrassBlock(StatusEffects.BAD_OMEN, FabricBlockSettings.of(Material.PLANT)
			.noCollision()
			.breakInstantly()
			.hardness(0)
			.sounds(BlockSoundGroup.GRASS)
			.nonOpaque()
	);
	public static final Block MYSTICAL_SLAB = new SlabBlock(AbstractBlock.Settings.of(Material.WOOD));
	/**
	 * Misc Building Blocks
	 */
	public static final Block DUNGEON_BRICK_SLAB = new SlabBlock(AbstractBlock.Settings.of(Material.WOOD));
	public static final Block LIVELY_GRASS = new Block(FabricBlockSettings.of(Material.SOIL)
			.hardness(0.5f)
			.sounds(BlockSoundGroup.GRASS));
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

		registerBlock(LintItemGroups.DECORATIONS, RETURN_HOME, "return_home");

		registerGeneric("mystical_fallen_leaves", MYSTICAL_FALLEN_LEAVES, LintItemGroups.DECORATIONS);
		registerGeneric("corrupt_fallen_leaves", CORRUPT_FALLEN_LEAVES, LintItemGroups.DECORATIONS);
	}

	public static void registerBuildingBlocks() {
		registerBlock(LintItemGroups.BLOCKS, SMELTERY, "smeltery");

		registerBlock(LintItemGroups.BLOCKS, CORRUPT_GRASS, "corrupt_grass");
		registerBlock(LintItemGroups.BLOCKS, LIVELY_GRASS, "lively_grass");

		registerBlock(LintItemGroups.BLOCKS, MYSTICAL_LOG, "mystical_log");

		registerBlock(LintItemGroups.BLOCKS, CORRUPT_LOG, "corrupt_log");

		registerBlock(LintItemGroups.BLOCKS, MYSTICAL_SLAB, "mystical_slab");
		registerBlock(LintItemGroups.BLOCKS, CORRUPT_SLAB, "corrupt_slab");
		registerBlock(LintItemGroups.BLOCKS, DUNGEON_BRICK_SLAB, "dungeon_brick_slab");
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
		Registry.register(Registry.ITEM, Lint.id(path), new BlockItem(block, new Item.Settings().group(itemGroup)));
	}

	private static void registerFlower(FlowerBlock flower, String path) {
		registerGeneric(path, flower, LintItemGroups.DECORATIONS);
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
