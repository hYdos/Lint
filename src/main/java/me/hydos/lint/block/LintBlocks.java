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
import me.hydos.lint.fluid.LintFluids;
import me.hydos.lint.fluid.MoltenMetalFluid;
import me.hydos.lint.item.group.ItemGroups;
import me.hydos.lint.refactord.block.organic.FallenLeavesBlock;
import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.fabricmc.fabric.api.tool.attribute.v1.FabricToolTags;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.FluidBlock;
import net.minecraft.block.Material;
import net.minecraft.fluid.Fluid;
import net.minecraft.item.ItemGroup;
import net.minecraft.sound.BlockSoundGroup;
import net.minecraft.util.registry.Registry;

public final class LintBlocks extends LintDataRegistry {
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

	/**
	 * Corrupt Building Blocks
	 */
	public static final Block CORRUPT_FALLEN_LEAVES = new FallenLeavesBlock(FabricBlockSettings.of(Material.LEAVES)
			.hardness(0.5f)
			.sounds(BlockSoundGroup.SWEET_BERRY_BUSH).nonOpaque());

	/**
	 * Mystical Decorations
	 */

	/**
	 * Mystical Building Blocks
	 */
	public static final Block MYSTICAL_FALLEN_LEAVES = new FallenLeavesBlock(FabricBlockSettings.of(Material.LEAVES)
			.hardness(0.5f)
			.sounds(BlockSoundGroup.SWEET_BERRY_BUSH).nonOpaque());

	/**
	 * Fluid blockstate cache
	 */
	private static final HashMap<MoltenMetalFluid, FluidBlock> FLUID_BLOCKSTATE_MAP = new HashMap<>();

	public static void initialize() {
		registerBuildingBlocks();
		registerDecorations();
		registerFluidBlocks();
	}

	public static void registerDecorations() {
		registerBlock(ItemGroups.DECORATIONS, RETURN_HOME, "return_home");

		registerCubeAll("mystical_fallen_leaves", MYSTICAL_FALLEN_LEAVES, ItemGroups.DECORATIONS);
		registerCubeAll("corrupt_fallen_leaves", CORRUPT_FALLEN_LEAVES, ItemGroups.DECORATIONS);
	}

	public static void registerBuildingBlocks() {
		registerBlock(ItemGroups.BLOCKS, SMELTERY, "smeltery");
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

	private static void registerBlock(ItemGroup itemGroup, Block block, String path) {
		registerHiddenBlock(block, path);
		registerBlockItem(block, itemGroup);
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
