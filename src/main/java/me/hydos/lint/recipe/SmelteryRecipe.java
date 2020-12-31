package me.hydos.lint.recipe;

import net.minecraft.item.ItemStack;
import net.minecraft.recipe.AbstractCookingRecipe;
import net.minecraft.recipe.Ingredient;
import net.minecraft.recipe.RecipeSerializer;
import net.minecraft.recipe.RecipeType;
import net.minecraft.util.Identifier;

public class SmelteryRecipe extends AbstractCookingRecipe {

	public SmelteryRecipe(RecipeType<?> type, Identifier id, String group, Ingredient input, ItemStack output, float experience, int cookTime) {
		super(type, id, group, input, output, experience, cookTime);
	}

//	public SmelteryRecipe(Identifier id, String group, Ingredient input, ItemStack output, float experience, int cookTime) {
//		super(Recipes.SMELTERY_RECIPE_TYPE, id, group, input, output, experience, cookTime);
//	}

	@Override
	public RecipeSerializer<?> getSerializer() {
		return null;
	}
}
