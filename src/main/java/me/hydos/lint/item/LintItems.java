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

import static me.hydos.lint.block.LintAutoDataRegistry.registerGenerated;

import me.hydos.lint.item.group.ItemGroups;
import me.hydos.lint.item.materialset.MaterialSet;
import me.hydos.lint.sound.Sounds;
import net.minecraft.item.Item;
import net.minecraft.item.MusicDiscItem;
import net.minecraft.util.Rarity;

public class LintItems {

	/**
	 * Boss Materials
	 */
	public static final Item TATER_ESSENCE = new TaterEssenceItem(new Item.Settings().group(ItemGroups.ITEMS).rarity(Rarity.EPIC).maxCount(1));

	/**
	 * Armor & Tool Sets
	 */
	public static final MaterialSet SICIERON_SET = new MaterialSet("sicieron", ArmourMaterials.SICIERON, ToolMaterials.SICIERON, ItemGroups.TOOLS);

	// actually a jurel-sicieron alloy (explained below)
	public static final MaterialSet JUREL_SET = new MaterialSet("jurel", ArmourMaterials.JUREL, ToolMaterials.JUREL, ItemGroups.TOOLS);

	/**
	 * Ore Materials
	 */
	public static final Item SICIERON_INGOT = new Item(new Item.Settings().group(ItemGroups.ITEMS).maxCount(64));
	public static final Item TARSCAN_SHARD = new Item(new Item.Settings().group(ItemGroups.ITEMS).maxCount(64));
	public static final Item JUREL_POWDER = new Item(new Item.Settings().group(ItemGroups.ITEMS).rarity(Rarity.RARE).maxCount(64));
	public static final Item MAGNETITE_POWDER = new Item(new Item.Settings().group(ItemGroups.ITEMS).rarity(Rarity.RARE).maxCount(64));

	// Alloy of Jurel and Sicieron (1 jurel : 2 sicieron ratio)
	// Regular Jurel is fragile and becomes a powder readily. Combining it in the 1:2 ratio with regular sicieron allows its powerful properties to be fully utilised in the creation of items.
	public static final Item HARDENED_JUREL_INGOT = new Item(new Item.Settings().group(ItemGroups.ITEMS).rarity(Rarity.RARE).maxCount(64));

	/**
	 * Music Discs
	 */
	public static final Item SUSPICOUS_LOOKING_DISC = new Item(new Item.Settings().group(ItemGroups.ITEMS).maxCount(1).rarity(Rarity.RARE));
	public static final Item OBOE_DISC = new LintMusicDiscItem(14, Sounds.GRIMACE, new Item.Settings().group(ItemGroups.ITEMS).maxCount(1).rarity(Rarity.RARE));
	public static final Item OCEAN_DISC = new LintMusicDiscItem(15, Sounds.OCEAN, new Item.Settings().group(ItemGroups.ITEMS).maxCount(1).rarity(Rarity.RARE));
	public static final Item MYSTICAL_FOREST_DISC = new LintMusicDiscItem(16, Sounds.MYSTICAL_FOREST, new Item.Settings().group(ItemGroups.ITEMS).maxCount(1).rarity(Rarity.RARE));
	public static final Item CORRUPT_FOREST_DISC = new LintMusicDiscItem(17, Sounds.CORRUPT_FOREST, new Item.Settings().group(ItemGroups.ITEMS).maxCount(1).rarity(Rarity.RARE));

	public static void initialize() {
		ItemGroups.register();
		registerOreMaterials();
		registerMaterialSets();
		registerDiscs();
	}

	private static void registerDiscs() {
		registerGenerated("suspicious_looking_disc", SUSPICOUS_LOOKING_DISC);
		registerGenerated("oboe_disc", OBOE_DISC);
		registerGenerated("ocean_disc", OCEAN_DISC);
		registerGenerated("mystical_forest_disc", MYSTICAL_FOREST_DISC);
		registerGenerated("corrupt_forest_disc", CORRUPT_FOREST_DISC);
	}

	private static void registerMaterialSets() {
		SICIERON_SET.registerItems();
		JUREL_SET.registerItems();
	}

	private static void registerOreMaterials() {
		registerGenerated("tater_essence", TATER_ESSENCE);
		registerGenerated("sicieron_ingot", SICIERON_INGOT);
		registerGenerated("jurel_powder", JUREL_POWDER);
		registerGenerated("magnetite_powder", MAGNETITE_POWDER);
		registerGenerated("tarscan_shard", TARSCAN_SHARD);
		registerGenerated("hardened_jurel_ingot", HARDENED_JUREL_INGOT);
	}
}
