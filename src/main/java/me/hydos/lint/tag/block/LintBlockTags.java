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

package me.hydos.lint.tag.block;

import me.hydos.lint.Lint;
import net.fabricmc.fabric.api.tag.TagFactory;
import net.minecraft.block.Block;
import net.minecraft.tag.Tag;

public final class LintBlockTags {
	public static final Tag<Block> BASIC_CASING = TagFactory.BLOCK.create(Lint.id("basic_smeltery_casing"));
	public static final Tag<Block> DIRT = TagFactory.BLOCK.create(Lint.id("dirt"));
	public static final Tag<Block> GRASS = TagFactory.BLOCK.create(Lint.id("grass"));
	public static final Tag<Block> SAND = TagFactory.BLOCK.create(Lint.id("sand"));
	public static final Tag<Block> STONE = TagFactory.BLOCK.create(Lint.id("stone"));

	private LintBlockTags() {}

	public static void initialize() {
		LintGrassTags.initialize();
	}
}
