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

package me.hydos.lint.item;

import me.hydos.lint.Lint;
import me.hydos.lint.item.group.ItemGroups;
import me.hydos.lint.item.materialset.MaterialSet;
import me.hydos.lint.sound.Sounds;
import net.fabricmc.fabric.api.registry.FuelRegistry;
import net.minecraft.item.Item;
import net.minecraft.item.Items;
import net.minecraft.util.Rarity;
import net.minecraft.util.registry.Registry;

import static me.hydos.lint.item.ItemData.registerGenerated;
import static me.hydos.lint.item.ItemData.registerHandheld;

public class LintItems {
	/**
	 * Boss Materials
	 */
	public static final Item TATER_ESSENCE = new TaterEssenceItem(new Item.Settings().group(ItemGroups.ITEMS).rarity(Rarity.EPIC).maxCount(1));
	public static final Item HERB_MIX = new Item(new Item.Settings().group(ItemGroups.ITEMS));

	/**
	 * Armor & Tool Sets
	 */
	public static final MaterialSet SICIERON_SET = new MaterialSet("sicieron", ArmourMaterials.SICIERON, ToolMaterials.SICIERON, ItemGroups.TOOLS);

	// actually a jurel-sicieron alloy (explained below)
	public static final MaterialSet JUREL_SET = new MaterialSet("jurel", ArmourMaterials.JUREL, ToolMaterials.JUREL, ItemGroups.TOOLS);
	public static final MaterialSet STONE_SET = new MaterialSet("stone", Items.STONE_PICKAXE, Items.STONE_AXE, Items.STONE_SHOVEL, Items.STONE_HOE, Items.STONE_SWORD, null, null, null, null).builtin();

	/**
	 * Materials
	 */
	public static final Item SICIERON_INGOT = new Item(new Item.Settings().group(ItemGroups.ITEMS).maxCount(64));
	public static final Item TARSCAN_SHARD = new Item(new Item.Settings().group(ItemGroups.ITEMS).maxCount(64));
	public static final Item JUREL_POWDER = new Item(new Item.Settings().group(ItemGroups.ITEMS).rarity(Rarity.UNCOMMON).maxCount(64));
	public static final Item MAGNETITE_POWDER = new Item(new Item.Settings().group(ItemGroups.ITEMS).maxCount(64));
	public static final Item MINT_GEL = new Item(new Item.Settings().group(ItemGroups.ITEMS).maxCount(64));
	public static final Item TARSCAN_GEL = new Item(new Item.Settings().group(ItemGroups.ITEMS).maxCount(64));
	public static final Item ALLOS_SHARD = new Item(new Item.Settings().group(ItemGroups.ITEMS).maxCount(16).rarity(Rarity.RARE));
	public static final Item MANOS_SHARD = new Item(new Item.Settings().group(ItemGroups.ITEMS).maxCount(16).rarity(Rarity.RARE));

	// Alloy of Jurel and Sicieron (1 jurel : 2 sicieron ratio)
	// Regular Jurel is fragile and becomes a powder readily. Combining it in the 1:2 ratio with regular sicieron allows its powerful properties to be fully utilised in the creation of items.
	public static final Item HARDENED_JUREL_INGOT = new Item(new Item.Settings().group(ItemGroups.ITEMS).rarity(Rarity.RARE).maxCount(64));

	/**
	 * Music Discs
	 */
	// Found in Dungeon (todo: actually finalise which discs are in dungeon)
	public static final Item GRIMACE_OBOE_DISC = new LintMusicDiscItem(14, Sounds.GRIMACE_OBOE, new Item.Settings().group(ItemGroups.ITEMS).maxCount(1).rarity(Rarity.UNCOMMON));
	public static final Item GRIMACE_VIOLINS_DISC = new LintMusicDiscItem(14, Sounds.GRIMACE_VIOLINS, new Item.Settings().group(ItemGroups.ITEMS).maxCount(1).rarity(Rarity.UNCOMMON));
	// Special
	public static final Item SUSPICIOUS_LOOKING_DISC = new Item(new Item.Settings().group(ItemGroups.ITEMS).maxCount(1).rarity(Rarity.RARE));
	public static final Item OCEAN_DISC = new LintMusicDiscItem(15, Sounds.VOYAGE, new Item.Settings().group(ItemGroups.ITEMS).maxCount(1).rarity(Rarity.RARE));
	public static final Item KING_TATER_LINT1_DISC = new LintMusicDiscItem(15, Sounds.KING_TATER_DISC, new Item.Settings().group(ItemGroups.ITEMS).maxCount(1).rarity(Rarity.RARE));
	public static final Item MYSTICAL_FOREST_DISC = new LintMusicDiscItem(16, Sounds.MYSTICAL_FOREST_DISC, new Item.Settings().group(ItemGroups.ITEMS).maxCount(1).rarity(Rarity.RARE));
	public static final Item CORRUPT_FOREST_DISC = new LintMusicDiscItem(17, Sounds.CORRUPT_FOREST_DISC, new Item.Settings().group(ItemGroups.ITEMS).maxCount(1).rarity(Rarity.RARE));

	/**
	 * yes
	 */
	public static final Item DUSKBLADE = new Item(new Item.Settings().group(ItemGroups.TOOLS).maxCount(1).rarity(Rarity.EPIC));
	public static final Item ATTUNER = new WaypointTeleportItem(new Item.Settings().group(ItemGroups.TOOLS).maxCount(1));
	public static final Item QUESTBOOK = new Item(new Item.Settings().group(ItemGroups.ITEMS).maxCount(1).rarity(Rarity.RARE));

	public static void initialize() {
		ItemGroups.register();
		registerMaterials();
		registerMaterialSets();
		registerDiscs();
		Registry.register(Registry.ITEM, Lint.id("duskblade"), DUSKBLADE);
		registerGenerated("questbook", QUESTBOOK);
		registerHandheld("attuner", ATTUNER);

		FuelRegistry.INSTANCE.add(MINT_GEL, 150);
	}

	private static void registerDiscs() {
		registerGenerated("suspicious_looking_disc", SUSPICIOUS_LOOKING_DISC);
		registerGenerated("oboe_disc", GRIMACE_OBOE_DISC);
		registerGenerated("violins_disc", GRIMACE_VIOLINS_DISC);
		registerGenerated("ocean_disc", OCEAN_DISC);
		registerGenerated("mystical_forest_disc", MYSTICAL_FOREST_DISC);
		registerGenerated("corrupt_forest_disc", CORRUPT_FOREST_DISC);
		registerGenerated("king_tater_disc", KING_TATER_LINT1_DISC);
	}

	private static void registerMaterialSets() {
		SICIERON_SET.registerItems().createRecipes(Lint.id("sicieron_ingot"));
		JUREL_SET.registerItems().createRecipes(Lint.id("hardened_jurel_ingot"));
		STONE_SET.createRecipes(Lint.id("fused_cobblestone")); // todo pearlescent cobblestone
	}

	private static void registerMaterials() {
		registerGenerated("tater_essence", TATER_ESSENCE);
		registerGenerated("herb_mix", HERB_MIX);
		registerGenerated("sicieron_ingot", SICIERON_INGOT);
		registerGenerated("jurel_powder", JUREL_POWDER);
		registerGenerated("magnetite_powder", MAGNETITE_POWDER);
		registerGenerated("tarscan_shard", TARSCAN_SHARD);
		registerGenerated("hardened_jurel_ingot", HARDENED_JUREL_INGOT);
		registerGenerated("mint_gel", MINT_GEL);
		registerGenerated("tarscan_gel", TARSCAN_GEL);
		registerGenerated("allos_shard", ALLOS_SHARD);
		registerGenerated("manos_shard", MANOS_SHARD);
	}
}
