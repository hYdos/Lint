package me.hydos.lint.blocks;

import net.minecraft.block.entity.BlockEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.DefaultedList;
import net.minecraft.util.Identifier;
import reborncore.common.crafting.RebornRecipe;
import reborncore.common.crafting.RebornRecipeType;
import reborncore.common.crafting.ingredient.RebornIngredient;

public class AdventureTransformerRecipe extends RebornRecipe {

    public AdventureTransformerRecipe(RebornRecipeType<?> type, Identifier name) {
        super(type, name);
    }

    public AdventureTransformerRecipe(RebornRecipeType<?> type, Identifier name, DefaultedList<RebornIngredient> ingredients, DefaultedList<ItemStack> outputs, int power, int time) {
        super(type, name, ingredients, outputs, power, time);
    }

    @Override
    public boolean canCraft(BlockEntity be) {
        return super.canCraft(be);
    }
}
