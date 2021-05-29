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

package me.hydos.lint.npc.quest;

import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.text.Text;
import net.minecraft.text.TranslatableText;
import net.minecraft.util.Identifier;

import java.util.Random;

public abstract class Quest {
    public Quest(Identifier id, QuestCategory category) {
        this.category = category;
        this.id = id;
        this.title = "quest." + id.getNamespace() + "." + id.getPath() + ".title";
        this.description = "quest." + id.getNamespace() + "." + id.getPath() + ".description";
    }

    private final String title;
    private final String description;
    private final Identifier id;
    private final QuestCategory category;

    public final Identifier getId() {
        return this.id;
    }

    public final QuestCategory getCategory() {
        return this.category;
    }

    public Text getTitle() {
        return new TranslatableText(this.title);
    }

    public Text getDescription() {
        return new TranslatableText(this.description);
    }

    public void loadNBT(NbtCompound tag) {
    }

    public void writeNBT(NbtCompound tag) {
    }

    public abstract ItemStack[] getRewards(Random rand);
}
