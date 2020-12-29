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

package me.hydos.lint.item.group;

import me.hydos.lint.Lint;
import me.hydos.lint.block.LintBlocks;
import me.hydos.lint.item.LintItems;
import net.fabricmc.fabric.api.client.itemgroup.FabricItemGroupBuilder;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;

public class ItemGroups {

	public static final ItemGroup BLOCKS = FabricItemGroupBuilder.create(
			Lint.id("lint_blocks"))
			.icon(() -> new ItemStack(LintBlocks.LIVELY_GRASS))
			.build();

	public static final ItemGroup DECORATIONS = FabricItemGroupBuilder.create(
			Lint.id("lint_decorations"))
			.icon(() -> new ItemStack(LintBlocks.MYSTICAL_DAISY))
			.build();

	public static final ItemGroup TOOLS = FabricItemGroupBuilder.create(
			Lint.id("lint_tools"))
			.icon(() -> new ItemStack(LintItems.SICIERON_AXE))
			.build();

	public static final ItemGroup ITEMS = FabricItemGroupBuilder.create(
			Lint.id("lint_items"))
			.icon(() -> new ItemStack(LintItems.TATER_ESSENCE))
			.build();

	public static void register() {
	}
}
