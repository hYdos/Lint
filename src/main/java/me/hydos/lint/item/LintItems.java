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
import me.hydos.lint.item.group.LintItemGroups;
import me.hydos.lint.sound.Sounds;
import net.minecraft.item.Item;
import net.minecraft.item.MusicDiscItem;
import net.minecraft.util.Rarity;
import net.minecraft.util.registry.Registry;

public class LintItems {

	/**
	 * Boss Materials
	 */
	public static final Item TATER_ESSENCE = new TaterEssenceItem(new Item.Settings().group(LintItemGroups.ITEMS).rarity(Rarity.EPIC).maxCount(1));

	/**
	 * Armor & Tool Sets
	 */
	public static final MaterialSet SICIERON_SET = new MaterialSet("sicieron", ArmourMaterials.SICIERON, ToolMaterials.SICIERON, LintItemGroups.TOOLS);

	/**
	 * Ore Materials
	 */
	public static final Item SICIERON_INGOT = new Item(new Item.Settings().group(LintItemGroups.ITEMS).maxCount(64));
	public static final Item TARSCAN_SHARD = new Item(new Item.Settings().group(LintItemGroups.ITEMS).maxCount(64));
	public static final Item JUREL_POWDER = new Item(new Item.Settings().group(LintItemGroups.ITEMS).rarity(Rarity.RARE).maxCount(64));

	/**
	 * Music Discs
	 */
	public static final Item SUSPICOUS_LOOKING_DISC = new Item(new Item.Settings().group(LintItemGroups.ITEMS).maxCount(1).rarity(Rarity.RARE));
	public static final Item OBOE_DISC = new MusicDiscItem(14, Sounds.GRIMACE, new Item.Settings().group(LintItemGroups.ITEMS).maxCount(1).rarity(Rarity.RARE)) {
	};
	public static final Item OCEAN_DISC = new MusicDiscItem(15, Sounds.OCEAN, new Item.Settings().group(LintItemGroups.ITEMS).maxCount(1).rarity(Rarity.RARE)) {
	};
	public static final Item MYSTICAL_FOREST_DISC = new MusicDiscItem(16, Sounds.MYSTICAL_FOREST, new Item.Settings().group(LintItemGroups.ITEMS).maxCount(1).rarity(Rarity.RARE)) {
	};
	public static final Item CORRUPT_FOREST_DISC = new MusicDiscItem(17, Sounds.CORRUPT_FOREST, new Item.Settings().group(LintItemGroups.ITEMS).maxCount(1).rarity(Rarity.RARE)) {
	};

	public static void register() {
		LintItemGroups.register();
		registerOreMaterials();
		registerMaterialSets();
		registerDiscs();
	}

	private static void registerDiscs() {
		Registry.register(Registry.ITEM, Lint.id("suspicous_looking_disc"), SUSPICOUS_LOOKING_DISC);
		Registry.register(Registry.ITEM, Lint.id("oboe_disc"), OBOE_DISC);
		Registry.register(Registry.ITEM, Lint.id("ocean_disc"), OCEAN_DISC);
		Registry.register(Registry.ITEM, Lint.id("mystical_forest_disc"), MYSTICAL_FOREST_DISC);
		Registry.register(Registry.ITEM, Lint.id("corrupt_forest_disc"), CORRUPT_FOREST_DISC);
	}

	private static void registerMaterialSets() {
		SICIERON_SET.registerItems();
	}

	private static void registerOreMaterials() {
		Registry.register(Registry.ITEM, Lint.id("tater_essence"), TATER_ESSENCE);
		Registry.register(Registry.ITEM, Lint.id("sicieron_ingot"), SICIERON_INGOT);
		Registry.register(Registry.ITEM, Lint.id("jurel_powder"), JUREL_POWDER);
		Registry.register(Registry.ITEM, Lint.id("tarscan_shard"), TARSCAN_SHARD);
	}
}
