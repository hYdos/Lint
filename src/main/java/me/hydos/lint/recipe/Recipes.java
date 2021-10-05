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

package me.hydos.lint.recipe;

import lint.mana.alchemy.AlchemyRecipe;
import me.hydos.lint.Lint;
import net.minecraft.recipe.RecipeSerializer;
import net.minecraft.recipe.RecipeType;
import net.minecraft.recipe.SpecialRecipeSerializer;
import net.minecraft.util.registry.Registry;

public class Recipes {

	public static final SpecialRecipeSerializer<HerbMixRecipe> HERB_MIX = Registry.register(Registry.RECIPE_SERIALIZER, Lint.id("herb_mix"), new SpecialRecipeSerializer<>(HerbMixRecipe::new));

	public static final RecipeType<AlchemyRecipe> ALCHEMY = Registry.register(Registry.RECIPE_TYPE, Lint.id("alchemy"), new LintRecipeType<>());
	public static final RecipeSerializer<AlchemyRecipe> ALCHEMY_SERIALIZER = Registry.register(Registry.RECIPE_SERIALIZER, Lint.id("alchemy"), new AlchemyRecipe.Serializer());

//	public static final RecipeType<SmelteryRecipe> SMELTERY_RECIPE_TYPE = Registry.register(Registry.RECIPE_TYPE,
//			Lint.id("smeltery_recipe"),
//			new LintRecipeType<>("smeltery_recipe"));
//
//	public static final RecipeSerializer<SmelteryRecipe> SMELTERY_RECIPE_SERIALIZER = Registry.register(Registry.RECIPE_SERIALIZER,
//			Lint.id("smeltery_recipe"),
//			new CookingRecipeSerializer<>(SmelteryRecipe::new, 200));

	public static void initialize() {
	}
}
