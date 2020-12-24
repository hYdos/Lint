package me.hydos.lint.item;

import me.hydos.lint.Lint;
import me.hydos.lint.item.group.ItemGroups;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.util.Rarity;
import net.minecraft.util.registry.Registry;

public class Items {

	/**
	 * Boss Materials
	 */
	public static final Item TATER_ESSENCE = new TaterEssenceItem(new Item.Settings().group(ItemGroup.MATERIALS).rarity(Rarity.EPIC).maxCount(1));

	/**
	 * Armor
	 */


	/**
	 * Ore Materials
	 */
	public static final Item SICIERON_INGOT = new Item(new Item.Settings().group(ItemGroup.MATERIALS).maxCount(64));
	public static final Item TARSCAN_SHARD = new Item(new Item.Settings().group(ItemGroup.MATERIALS).maxCount(64));
	public static final Item JUREL_POWDER = new Item(new Item.Settings().group(ItemGroup.MATERIALS).rarity(Rarity.RARE).maxCount(32));

	public static void register() {
		ItemGroups.register();
		Registry.register(Registry.ITEM, Lint.id("tater_essence"), TATER_ESSENCE);
		Registry.register(Registry.ITEM, Lint.id("sicieron_ingot"), SICIERON_INGOT);
		Registry.register(Registry.ITEM, Lint.id("jurel_powder"), JUREL_POWDER);
		Registry.register(Registry.ITEM, Lint.id("tarscan_shard"), TARSCAN_SHARD);
	}
}
