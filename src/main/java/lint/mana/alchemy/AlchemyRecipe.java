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

package lint.mana.alchemy;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import lint.mana.ManaType;
import me.hydos.lint.recipe.Recipes;
import net.minecraft.inventory.Inventory;
import net.minecraft.item.ItemStack;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.recipe.*;
import net.minecraft.util.Identifier;
import net.minecraft.util.JsonHelper;
import net.minecraft.util.collection.DefaultedList;
import net.minecraft.world.World;

public record AlchemyRecipe(Identifier id, String group, ManaRequirements mana, ItemStack output,
							DefaultedList<Ingredient> ingredients) implements Recipe<Inventory> {

	@Override
	public DefaultedList<Ingredient> getIngredients() {
		return ingredients;
	}

	@Override
	public String getGroup() {
		return group;
	}

	@Override
	public ItemStack createIcon() {
		return Recipe.super.createIcon();
	}

	@Override
	public boolean matches(Inventory inventory, World world) {
		return false;
	}

	@Override
	public ItemStack craft(Inventory inventory) {
		return output.copy();
	}

	@Override
	public boolean fits(int width, int height) {
		return true;
	}

	@Override
	public ItemStack getOutput() {
		return output;
	}

	@Override
	public Identifier getId() {
		return id;
	}

	@Override
	public RecipeSerializer<?> getSerializer() {
		return Recipes.ALCHEMY_SERIALIZER;
	}

	@Override
	public RecipeType<?> getType() {
		return Recipes.ALCHEMY;
	}

	public record ManaRequirements(ManaType type, long amount) {
	}

	public static class Serializer implements RecipeSerializer<AlchemyRecipe> {

		private static DefaultedList<Ingredient> getIngredients(JsonArray json) {
			DefaultedList<Ingredient> list = DefaultedList.of();

			for (int i = 0; i < json.size(); i++) {
				Ingredient ingredient = Ingredient.fromJson(json.get(i));

				if (!ingredient.isEmpty()) {
					list.add(ingredient);
				}
			}

			return list;
		}

		@Override
		public AlchemyRecipe read(Identifier id, JsonObject json) {
			String group = JsonHelper.getString(json, "group", "");
			JsonObject manaObject = JsonHelper.getObject(json, "mana");

			ManaType type = ManaType.valueOf(JsonHelper.getString(manaObject, "type").toUpperCase());
			long amount = JsonHelper.getLong(manaObject, "amount");
			DefaultedList<Ingredient> defaultedList = getIngredients(JsonHelper.getArray(json, "ingredients"));
			ItemStack output = ShapedRecipe.outputFromJson(JsonHelper.getObject(json, "result"));
			return new AlchemyRecipe(id, group, new ManaRequirements(type, amount), output, defaultedList);
		}

		@Override
		public AlchemyRecipe read(Identifier id, PacketByteBuf buf) {
			String string = buf.readString();
			ManaType type = buf.readEnumConstant(ManaType.class);
			long amount = buf.readVarLong();

			int i = buf.readVarInt();
			DefaultedList<Ingredient> ingredients = DefaultedList.ofSize(i, Ingredient.EMPTY);

			for (int j = 0; j < ingredients.size(); ++j) {
				ingredients.set(j, Ingredient.fromPacket(buf));
			}

			ItemStack output = buf.readItemStack();
			return new AlchemyRecipe(id, string, new ManaRequirements(type, amount), output, ingredients);
		}

		@Override
		public void write(PacketByteBuf buf, AlchemyRecipe recipe) {
			buf.writeString(recipe.group);
			buf.writeEnumConstant(recipe.mana.type);
			buf.writeVarLong(recipe.mana.amount);
			buf.writeVarInt(recipe.ingredients.size());

			for (Ingredient ingredient : recipe.ingredients) {
				ingredient.write(buf);
			}

			buf.writeItemStack(recipe.output);
		}
	}
}
