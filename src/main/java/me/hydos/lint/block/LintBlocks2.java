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

import me.hydos.lint.item.group.LintItemGroups;
import me.hydos.lint.util.Power;
import net.devtech.arrp.json.blockstate.JBlockModel;
import net.devtech.arrp.json.blockstate.JState;
import net.devtech.arrp.json.loot.JCondition;
import net.devtech.arrp.json.loot.JLootTable;
import net.devtech.arrp.json.models.JModel;
import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.minecraft.block.*;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.sound.BlockSoundGroup;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;
import org.jetbrains.annotations.Nullable;

import static me.hydos.lint.Lint.RESOURCE_PACK;
import static me.hydos.lint.Lint.id;

public class LintBlocks2 {
	/**
	 * Block Settings
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
			LintItemGroups.BLOCKS);
	public static final Block CRACKED_BASIC_CASING = registerGeneric("cracked_basic_casing",
			new Block(FabricBlockSettings.of(Material.STONE)
					.hardness(0.5f)
					.sounds(BlockSoundGroup.STONE)),
			LintItemGroups.BLOCKS);

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
			LintItemGroups.DECORATIONS);
	public static final Block GREEN_BUTTON = registerGeneric(
			"green_button",
			new KingTaterButton(FabricBlockSettings.of(Material.SOIL)
					.hardness(-0.1f)
					.sounds(BlockSoundGroup.WET_GRASS)),
			LintItemGroups.DECORATIONS);

	public static final Block INDIGO_STONE = registerGeneric(
			"indigo_stone",
			new Block(FabricBlockSettings.copyOf(Blocks.STONE)
					.materialColor(MaterialColor.PURPLE_TERRACOTTA)),
			LintItemGroups.BLOCKS);
	public static final Block FUSED_STONE = registerGeneric(
			"fused_stone",
			new Block(FabricBlockSettings.of(Material.STONE)
					.hardness(1f)
					.sounds(BlockSoundGroup.STONE)),
			LintItemGroups.BLOCKS);
	public static final Block TARSCAN_ORE = registerGeneric(
			"tarscan_ore",
			new Block(FabricBlockSettings.of(Material.STONE)
					.hardness(1f)
					.sounds(BlockSoundGroup.STONE)),
			LintItemGroups.BLOCKS);
	public static final Block SICIERON_ORE = registerGeneric(
			"sicieron_ore",
			new Block(FabricBlockSettings.of(Material.STONE)
					.hardness(1f)
					.sounds(BlockSoundGroup.STONE)),
			LintItemGroups.BLOCKS);
	public static final Block JUREL_ORE = registerGeneric(
			"jurel_ore",
			new Block(FabricBlockSettings.of(Material.STONE)
					.hardness(1f)
					.sounds(BlockSoundGroup.STONE)),
			LintItemGroups.BLOCKS);
	public static final Block ASPHALT = registerGeneric(
			"asphalt",
			new Block(FabricBlockSettings.copyOf(Blocks.STONE)),
			LintItemGroups.BLOCKS);

	public static final Block CORRUPT_PLANKS = registerGeneric(
			"corrupt_planks",
			new Block(PLANK_SETTINGS
					.materialColor(MaterialColor.PURPLE)),
			LintItemGroups.BLOCKS);
	public static final Block CORRUPT_LEAVES = registerGeneric(
			"corrupt_leaves",
			new LintLeavesBlock(FabricBlockSettings.copyOf(Blocks.OAK_LEAVES)
					.materialColor(MaterialColor.PURPLE)),
			LintItemGroups.BLOCKS);
	public static final Block CORRUPT_SAND = registerGeneric(
			"corrupt_sand",
			new FallingBlock(SAND_SETTINGS
					.materialColor(MaterialColor.PURPLE)),
			LintItemGroups.BLOCKS);

	public static final Block ALLOS_INFUSED_ASPHALT = registerGeneric(
			"allos_infused_asphalt",
			new InfusedBlock(FabricBlockSettings.copyOf(Blocks.STONE), Power.ALLOS),
			LintItemGroups.BLOCKS);
	public static final Block MANOS_INFUSED_ASPHALT = registerGeneric(
			"manos_infused_asphalt",
			new InfusedBlock(FabricBlockSettings.copyOf(Blocks.STONE), Power.MANOS),
			LintItemGroups.BLOCKS);

	public static final Block ASH = registerGeneric(
			"ash",
			new FallingBlock(SAND_SETTINGS),
			LintItemGroups.BLOCKS);

	public static final Block DUNGEON_BRICKS = registerGeneric(
			"dungeon_bricks",
			new Block(FabricBlockSettings.of(Material.STONE)
					.hardness(4)
					.sounds(BlockSoundGroup.STONE)),
			LintItemGroups.BLOCKS);
	public static final Block MOSSY_DUNGEON_BRICKS = registerGeneric(
			"mossy_dungeon_bricks",
			new Block(FabricBlockSettings.of(Material.STONE)
					.hardness(4)
					.sounds(BlockSoundGroup.STONE)),
			LintItemGroups.BLOCKS);

	public static final Block MYSTICAL_PLANKS = registerGeneric(
			"mystical_planks",
			new Block(PLANK_SETTINGS
					.materialColor(MaterialColor.DIAMOND)),
			LintItemGroups.BLOCKS);
	public static final Block MYSTICAL_LEAVES = registerGeneric(
			"mystical_leaves",
			new LintLeavesBlock(FabricBlockSettings.copyOf(Blocks.OAK_LEAVES)
					.materialColor(MaterialColor.DIAMOND)),
			LintItemGroups.BLOCKS);
	public static final Block MYSTICAL_SAND = registerGeneric(
			"mystical_sand",
			new FallingBlock(SAND_SETTINGS
					.materialColor(MaterialColor.MAGENTA)),
			LintItemGroups.BLOCKS);

	public static final Block RICH_DIRT = registerGeneric(
			"rich_dirt",
			new Block(FabricBlockSettings.of(Material.SOIL)
					.hardness(0.5f)
					.sounds(BlockSoundGroup.WET_GRASS)),
			LintItemGroups.BLOCKS);
	public static final Block WHITE_SAND = registerGeneric(
			"white_sand",
			new FallingBlock(SAND_SETTINGS),
			LintItemGroups.BLOCKS);


	/**
	 * Registers a generic cube all block with an associated dropped item
	 *
	 * @param id        The block ID
	 * @param block     The block
	 * @param itemGroup The item group for the dropped item
	 * @return The registered block
	 */
	protected static Block registerGeneric(String id, Block block, @Nullable ItemGroup itemGroup) {
		Identifier identifier = id(id);
		Identifier modelIdentifier = id("block/" + id);

		RESOURCE_PACK.addBlockState(JState.state(JState.variant().put("", new JBlockModel(modelIdentifier))), identifier);
		RESOURCE_PACK.addModel(JModel.model().parent("block/cube_all").textures(JModel.textures().var("all", modelIdentifier.toString())), modelIdentifier);
		RESOURCE_PACK.addModel(JModel.model().parent(modelIdentifier.toString()), id("item/" + id));
		Registry.register(Registry.BLOCK, identifier, block);
		registerBlockItem(block, itemGroup);

		return block;
	}

	/**
	 * Registers a BlockItem for an already registered block
	 *
	 * @param block     A block which has already been registered
	 * @param itemGroup The item group to place the item in
	 */
	protected static void registerBlockItem(Block block, @Nullable ItemGroup itemGroup) {
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

	LintBlocks2() {
	}
}
