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

package me.hydos.lint.refactord.block;

import me.hydos.lint.core.block.BlockBuilder;
import me.hydos.lint.core.block.Model;
import me.hydos.lint.refactord.block.organic.LintSpreadableBlock;
import net.minecraft.block.Block;
import net.minecraft.block.Material;
import net.minecraft.block.MaterialColor;

/**
 * All of lint's core blocks.
 */
public class LintBlocks {
	public static final Block CORRUPT_GRASS = BlockBuilder.create()
			.material(LintMaterials.GRASS_BLOCK
					.material(Material.SOLID_ORGANIC)
					.colour(MaterialColor.PURPLE))
			.model(Model.NONE)
			.register("corrupt_grass", LintSpreadableBlock.CONSTRUCTOR);

	public static final Block LIVELY_GRASS = BlockBuilder.create()
			.material(LintMaterials.GRASS_BLOCK)
			.model(Model.NONE)
			.register("lively_grass", LintSpreadableBlock.CONSTRUCTOR);

	public static final Block FROSTED_GRASS = BlockBuilder.create()
			.material(LintMaterials.GRASS_BLOCK)
			.model(Model.NONE)
			.register("frosted_grass", LintSpreadableBlock.CONSTRUCTOR);
}
