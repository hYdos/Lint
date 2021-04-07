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

package me.hydos.lint.item.materialset;

import me.hydos.lint.block.LintDataRegistry;
import net.devtech.arrp.json.recipe.*;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.item.*;
import net.minecraft.util.Identifier;
import net.minecraft.util.Rarity;

public final class MaterialSet {
    public final Item pickaxe;
    public final Item axe;
    public final Item shovel;
    public final Item hoe;
    public final Item sword;
    public final Item helmet;
    public final Item chestplate;
    public final Item leggings;
    public final Item boots;
    private final String registryName;
    private boolean registered = false;

    public MaterialSet(String registryName, ArmorMaterial armour, ToolMaterial tool, ItemGroup group) {
        this.pickaxe = new PickaxeItem(tool, 1, -2.8F, new Item.Settings().group(group)) {
        };
        this.axe = new LintAxeItem(tool, 6.0F, -3.1F, new Item.Settings().group(group));
        this.shovel = new ShovelItem(tool, 1.5F, -3.0F, new Item.Settings().group(group)) {
        };
        this.hoe = new HoeItem(tool, -2, -1.0f, new Item.Settings().group(group).rarity(Rarity.EPIC)) {
        };
        this.sword = new LintSwordItem(tool, 3, -2.4F, new Item.Settings().group(group));

        this.helmet = new ArmorItem(armour, EquipmentSlot.HEAD, new Item.Settings().group(group));
        this.chestplate = new ArmorItem(armour, EquipmentSlot.CHEST, new Item.Settings().group(group));
        this.leggings = new ArmorItem(armour, EquipmentSlot.LEGS, new Item.Settings().group(group));
        this.boots = new ArmorItem(armour, EquipmentSlot.FEET, new Item.Settings().group(group));

        this.registryName = registryName;
    }

    public MaterialSet(String registryName, Item pick, Item axe, Item shovel, Item hoe, Item sword, Item helmet, Item chestplate, Item leggings, Item boots) {
        this.pickaxe = pick;
        this.axe = axe;
        this.shovel = shovel;
        this.hoe = hoe;
        this.sword = sword;
        this.helmet = helmet;
        this.chestplate = chestplate;
        this.leggings = leggings;
        this.boots = boots;

        this.registryName = registryName;
    }

    public boolean contains(Item item) {
        return pickaxe == item || axe == item || shovel == item || hoe == item || sword == item || helmet == item || chestplate == item || leggings == item || boots == item;
    }

    public MaterialSet builtin() {
        this.registered = true;
        return this;
    }

    public MaterialSet registerItems() {
        LintDataRegistry.registerHandheld(this.registryName + "_sword", this.sword);
        LintDataRegistry.registerHandheld(this.registryName + "_axe", this.axe);
        LintDataRegistry.registerHandheld(this.registryName + "_pickaxe", this.pickaxe);
        LintDataRegistry.registerHandheld(this.registryName + "_shovel", this.shovel);
        LintDataRegistry.registerHandheld(this.registryName + "_hoe", this.hoe);

        LintDataRegistry.registerGenerated(this.registryName + "_helmet", this.helmet);
        LintDataRegistry.registerGenerated(this.registryName + "_chestplate", this.chestplate);
        LintDataRegistry.registerGenerated(this.registryName + "_leggings", this.leggings);
        LintDataRegistry.registerGenerated(this.registryName + "_boots", this.boots);
        this.registered = true;
        return this;
    }

    public void createRecipes(Identifier material) {
        if (this.registered) {
            final JIngredient stick = JIngredient.ingredient().item("minecraft:stick");
            final JIngredient mat = JIngredient.ingredient().item(material.toString());

            // Tools

            if (this.pickaxe != null) {
                LintDataRegistry.registerRecipe(this.registryName + "_sword", JRecipe.shaped(
                        JPattern.pattern("#", "#", "/"),
                        JKeys.keys().key("#", mat).key("/", stick),
                        JResult.item(this.sword)));

                LintDataRegistry.registerRecipe(this.registryName + "_axe", JRecipe.shaped(
                        JPattern.pattern("## ", "#/ ", " / "),
                        JKeys.keys().key("#", mat).key("/", stick),
                        JResult.item(this.axe)));

                LintDataRegistry.registerRecipe(this.registryName + "_axe2", JRecipe.shaped(
                        JPattern.pattern(" ##", " /#", " / "),
                        JKeys.keys().key("#", mat).key("/", stick),
                        JResult.item(this.axe)));

                LintDataRegistry.registerRecipe(this.registryName + "_shovel", JRecipe.shaped(
                        JPattern.pattern("#", "/", "/"),
                        JKeys.keys().key("#", mat).key("/", stick),
                        JResult.item(this.shovel)));

                LintDataRegistry.registerRecipe(this.registryName + "_hoe", JRecipe.shaped(
                        JPattern.pattern("##", " /", " /"),
                        JKeys.keys().key("#", mat).key("/", stick),
                        JResult.item(this.hoe)));

                LintDataRegistry.registerRecipe(this.registryName + "_hoe2", JRecipe.shaped(
                        JPattern.pattern("##", "/ ", "/ "),
                        JKeys.keys().key("#", mat).key("/", stick),
                        JResult.item(this.hoe)));

                LintDataRegistry.registerRecipe(this.registryName + "_pickaxe", JRecipe.shaped(
                        JPattern.pattern("###", " / ", " / "),
                        JKeys.keys().key("#", mat).key("/", stick),
                        JResult.item(this.pickaxe)));
            }

            // Armour

            if (this.helmet != null) {
                LintDataRegistry.registerRecipe(this.registryName + "_helmet", JRecipe.shaped(
                        JPattern.pattern("###", "# #"),
                        JKeys.keys().key("#", mat),
                        JResult.item(this.helmet)));

                LintDataRegistry.registerRecipe(this.registryName + "_chestplate", JRecipe.shaped(
                        JPattern.pattern("# #", "###", "###"),
                        JKeys.keys().key("#", mat),
                        JResult.item(this.chestplate)));

                LintDataRegistry.registerRecipe(this.registryName + "_leggings", JRecipe.shaped(
                        JPattern.pattern("###", "# #", "# #"),
                        JKeys.keys().key("#", mat),
                        JResult.item(this.leggings)));

                LintDataRegistry.registerRecipe(this.registryName + "_boots", JRecipe.shaped(
                        JPattern.pattern("# #", "# #"),
                        JKeys.keys().key("#", mat),
                        JResult.item(this.boots)));
            }
        } else {
            throw new IllegalStateException("Can only create recipes for registered or builtin material sets."); // thanks haykam
        }
    }
}
