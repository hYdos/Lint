package me.hydos.lint.recipe;

import net.minecraft.recipe.Recipe;
import net.minecraft.recipe.RecipeType;

public class LintRecipeType<T extends Recipe<?>> implements RecipeType<T> {

	private final String name;

	public LintRecipeType(String name) {
		this.name = name;
	}

	@Override
	public String toString() {
		return name;
	}
}
