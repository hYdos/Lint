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

import net.devtech.arrp.json.blockstate.JBlockModel;
import net.devtech.arrp.json.blockstate.JState;
import net.devtech.arrp.json.loot.JCondition;
import net.devtech.arrp.json.loot.JLootTable;
import net.devtech.arrp.json.models.JModel;
import net.minecraft.block.*;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;
import org.jetbrains.annotations.Nullable;

import static me.hydos.lint.Lint.RESOURCE_PACK;
import static me.hydos.lint.Lint.id;

public class LintBlocks2 {

	/**
	 * Registers a generic "cube all" block with an associated dropped item
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
	 * Registers a generic slab block with an associated dropped item
	 *
	 * @param id        The block ID
	 * @param block     The block
	 * @param itemGroup The item group for the dropped item
	 * @return The registered block
	 */
	protected static Block registerSlab(String id, String planksId, Block block, @Nullable ItemGroup itemGroup) {
		Identifier identifier = id(id);
		Identifier plankIdentifier = id(planksId);
		Identifier lowerSlabIdentifier = id("block/" + id);
		Identifier topSlabIdentifier = id("block/" + id);
		Identifier plankModelIdentifier = id("block/" + id);

		RESOURCE_PACK.addBlockState(JState.state(JState.variant().put("type=bottom", new JBlockModel(modelIdentifier))), identifier);
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
