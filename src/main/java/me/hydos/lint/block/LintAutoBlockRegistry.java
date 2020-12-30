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

import static me.hydos.lint.Lint.RESOURCE_PACK;
import static me.hydos.lint.Lint.id;

import org.jetbrains.annotations.Nullable;

import net.devtech.arrp.json.blockstate.JBlockModel;
import net.devtech.arrp.json.blockstate.JState;
import net.devtech.arrp.json.loot.JCondition;
import net.devtech.arrp.json.loot.JLootTable;
import net.devtech.arrp.json.models.JModel;
import net.minecraft.block.Block;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

public class LintAutoBlockRegistry {
	/**
	 * Only registers the block state and the block item, as a simple block state referencing the model, and a basic BlockItem.
	 */
	protected static Block registerSimpleBlockState(String id, Block block, @Nullable ItemGroup itemGroup) {
		Identifier identifier = id(id);
		Identifier modelIdentifier = id("block/" + id);

		RESOURCE_PACK.addBlockState(JState.state(JState.variant().put("", new JBlockModel(modelIdentifier))), identifier);
		RESOURCE_PACK.addModel(JModel.model().parent(modelIdentifier.toString()), id("item/" + id));
		Registry.register(Registry.BLOCK, identifier, block);
		registerBlockItem(block, itemGroup);

		return block;
	}

	/**
	 * Registers a generic "cube all" block with an associated dropped item
	 *
	 * @param id        The block ID
	 * @param block     The block
	 * @param itemGroup The item group for the dropped item
	 * @return The registered block
	 */
	protected static Block registerCubeAll(String id, Block block, @Nullable ItemGroup itemGroup) {
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
	 * Registers a generic slab block with an associated dropped item
	 *
	 * @param id        The block ID
	 * @param block     The block
	 * @param itemGroup The item group for the dropped item
	 * @return The registered block
	 */
	protected static Block registerSlab(String id, String planksId, Block block, @Nullable ItemGroup itemGroup) {
		Identifier identifier = id(id);
		Identifier lowerSlabIdentifier = id("block/" + id);
		Identifier topSlabIdentifier = id("block/" + id + "_top");
		Identifier plankModelIdentifier = id("block/" + planksId);

		RESOURCE_PACK.addBlockState(JState.state(JState.variant()
				.put("type=bottom", new JBlockModel(lowerSlabIdentifier))
				.put("type=double", new JBlockModel(plankModelIdentifier))
				.put("type=top", new JBlockModel(topSlabIdentifier))
				), identifier);
		RESOURCE_PACK.addModel(JModel.model()
				.parent("block/slab")
				.textures(JModel.textures()
						.var("bottom", plankModelIdentifier.toString())
						.var("top", plankModelIdentifier.toString())
						.var("side", plankModelIdentifier.toString())
						), lowerSlabIdentifier);
		RESOURCE_PACK.addModel(JModel.model()
				.parent("block/slab_top")
				.textures(JModel.textures()
						.var("bottom", plankModelIdentifier.toString())
						.var("top", plankModelIdentifier.toString())
						.var("side", plankModelIdentifier.toString())
						), topSlabIdentifier);
		RESOURCE_PACK.addModel(JModel.model().parent(lowerSlabIdentifier.toString()), id("item/" + id));
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

	LintAutoBlockRegistry() {
	}
}