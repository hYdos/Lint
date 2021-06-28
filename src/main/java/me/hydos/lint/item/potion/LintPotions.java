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

package me.hydos.lint.item.potion;

import me.hydos.lint.Lint;
import me.hydos.lint.item.LintItems;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.potion.Potion;
import net.minecraft.potion.Potions;
import net.minecraft.recipe.BrewingRecipeRegistry;
import net.minecraft.util.registry.Registry;

public class LintPotions {
	public static final Potion MINT_TEA = new Potion(new StatusEffectInstance(StatusEffects.HASTE, 40));
	public static final Potion TRANSMUTATION = new Potion(new StatusEffectInstance(LintStatusEffects.TRANSMUTATION, 5 * 60 * 20)); // 5 minutes
	public static final Potion LONG_TRANSMUTATION = new Potion(new StatusEffectInstance(LintStatusEffects.TRANSMUTATION, 12 * 60 * 20)); // 12 minutes

	private static void register(String name, Potion potion) {
		Registry.register(Registry.POTION, Lint.id(name), potion);
	}

	public static void initialize() {
		register("mint_tea", MINT_TEA);
		register("transmutation", TRANSMUTATION);
		register("long_transmutation", LONG_TRANSMUTATION);

		BrewingRecipeRegistry.registerPotionRecipe(Potions.AWKWARD, LintItems.HERB_MIX, TRANSMUTATION);
		BrewingRecipeRegistry.registerPotionRecipe(TRANSMUTATION, LintItems.HERB_MIX, LONG_TRANSMUTATION);
	}
}
