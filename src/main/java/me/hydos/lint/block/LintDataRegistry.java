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

import net.devtech.arrp.json.loot.JCondition;
import net.devtech.arrp.json.loot.JLootTable;
import net.devtech.arrp.json.models.JModel;
import net.devtech.arrp.json.recipe.JRecipe;
import net.minecraft.block.Block;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;
import org.jetbrains.annotations.Nullable;

import static me.hydos.lint.Lint.RESOURCE_PACK;
import static me.hydos.lint.Lint.id;

public class LintDataRegistry {

    public static Item registerGenerated(String id, Item item) {
        Identifier modelIdentifier = id("item/" + id);
        _registerGenerated(modelIdentifier, modelIdentifier);
        return Registry.register(Registry.ITEM, id(id), item);
    }

    public static Item registerHandheld(String id, Item item) {
        Identifier modelIdentifier = id("item/" + id);
        RESOURCE_PACK.addModel(JModel.model().parent("item/handheld").textures(JModel.textures().var("layer0", modelIdentifier.toString())), modelIdentifier);
        return Registry.register(Registry.ITEM, id(id), item);
    }

    private static void _registerGenerated(Identifier modelIdentifier, Identifier textureIdentifier) {
        RESOURCE_PACK.addModel(JModel.model().parent("item/generated").textures(JModel.textures().var("layer0", textureIdentifier.toString())), modelIdentifier);
    }

    /**
     * Registers a BlockItem for an already registered block
     *
     * @param block     A block which has already been registered
     * @param itemGroup The item group to place the item in
     */
    public static void registerBlockItem(Block block, @Nullable ItemGroup itemGroup) {
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

    public static void registerRecipe(String id, JRecipe recipe) {
        RESOURCE_PACK.addRecipe(id(id), recipe);
    }
}
