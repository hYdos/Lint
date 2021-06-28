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
import net.devtech.arrp.json.loot.JCondition;
import net.devtech.arrp.json.loot.JLootTable;
import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.fabricmc.fabric.api.tool.attribute.v1.FabricToolTags;
import net.minecraft.block.*;
import net.minecraft.fluid.Fluid;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.sound.BlockSoundGroup;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;
import org.jetbrains.annotations.Nullable;

import java.util.HashMap;

import static me.hydos.lint.Lint.RESOURCE_PACK;

public final class LintBlocksOld {
	/**
	 * Smeltery Related
	 */
	public static final Block SMELTERY = new SmelteryBlock(FabricBlockSettings.of(Material.METAL)
			.hardness(2)
			.sounds(BlockSoundGroup.STONE)
			.breakByTool(FabricToolTags.PICKAXES, 0));

	/**
	 * Fluid blockstate cache
	 */
	private static final HashMap<MoltenMetalFluid, FluidBlock> FLUID_BLOCKSTATE_MAP = new HashMap<>();

	public static void initialize() {
		registerBuildingBlocks();
		registerFluidBlocks();
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


	/**
	 * Registers a BlockItem for an already registered block
	 *
	 * @param block     A block which has already been registered
	 * @param itemGroup The item group to place the item in
	 */
	private static void registerBlockItem(Block block, @Nullable ItemGroup itemGroup) {
		Identifier id = Registry.BLOCK.getId(block);
		RESOURCE_PACK.addLootTable(new Identifier(id.getNamespace(), "blocks/" + id.getPath()),
				JLootTable.loot("minecraft:block")
						.pool(JLootTable.pool()
								.rolls(1)
								.entry(JLootTable.entry()
										.type("minecraft:item")
										.name(id.toString()))
								.condition(new JCondition("minecraft:survives_explosion"))));

		{
			Item.Settings settings = new Item.Settings();

			if (itemGroup != null) {
				settings.group(itemGroup);
			}

			Registry.register(Registry.ITEM, id, new BlockItem(block, settings));
		}
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
