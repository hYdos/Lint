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

import me.hydos.lint.item.LintItems;
import me.hydos.lint.tag.LintItemTags;
import net.minecraft.inventory.CraftingInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.recipe.RecipeSerializer;
import net.minecraft.recipe.SpecialCraftingRecipe;
import net.minecraft.tag.ItemTags;
import net.minecraft.util.Identifier;
import net.minecraft.world.World;

public class HerbMixRecipe extends SpecialCraftingRecipe {

	public HerbMixRecipe(Identifier id) {
		super(id);
	}

	@Override
	public boolean matches(CraftingInventory inv, World world) {
		boolean bowl = false;
		boolean stick = false;
		int special = 0;

		for (int i = 0; i < inv.size(); i++) {
			ItemStack stack = inv.getStack(i);

			if (stack.getItem() == Items.BOWL) {
				bowl = true;
			} else if (stack.getItem() == Items.STICK) {
				stick = true;
			} else if (stack.getItem().isIn(LintItemTags.HERB_MIX_SPECIAL)) {
				special++;
			} else if (!stack.getItem().isIn(ItemTags.FLOWERS)) {
				return false;
			}
		}

		return bowl && stick && special >= 3;
	}

	@Override
	public ItemStack craft(CraftingInventory inv) {
		return new ItemStack(LintItems.HERB_MIX);
	}

	@Override
	public boolean fits(int width, int height) {
		return width * height == 9;
	}

	@Override
	public RecipeSerializer<?> getSerializer() {
		return Recipes.HERB_MIX;
	}
}
