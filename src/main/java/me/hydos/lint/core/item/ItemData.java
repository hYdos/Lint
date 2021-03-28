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

package me.hydos.lint.core.item;

import static me.hydos.lint.Lint.RESOURCE_PACK;
import static me.hydos.lint.Lint.id;

import org.jetbrains.annotations.Nullable;

import net.devtech.arrp.json.blockstate.JBlockModel;
import net.devtech.arrp.json.blockstate.JState;
import net.devtech.arrp.json.models.JModel;
import net.devtech.arrp.json.recipe.JIngredient;
import net.devtech.arrp.json.recipe.JKeys;
import net.devtech.arrp.json.recipe.JPattern;
import net.devtech.arrp.json.recipe.JRecipe;
import net.devtech.arrp.json.recipe.JResult;
import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

/**
 * ARRP for item data.
 */
public class ItemData {
	public static Item registerGenerated(String id, Item item) {
		Identifier modelIdentifier = id("item/" + id);
		RESOURCE_PACK.addModel(generatedModel(modelIdentifier), modelIdentifier);
		return Registry.register(Registry.ITEM, id(id), item);
	}

	public static Item registerHandheld(String id, Item item) {
		Identifier modelIdentifier = id("item/" + id);
		RESOURCE_PACK.addModel(JModel.model().parent("item/handheld").textures(JModel.textures().var("layer0", modelIdentifier.toString())), modelIdentifier);
		return Registry.register(Registry.ITEM, id(id), item);
	}

	public static JModel generatedModel(Identifier textureIdentifier) {
		return JModel.model().parent("item/generated").textures(JModel.textures().var("layer0", textureIdentifier.toString()));
	}

	// Recipe

	public static void registerRecipe(String id, JRecipe recipe) {
		RESOURCE_PACK.addRecipe(id(id), recipe);
	}
}
