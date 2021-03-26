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
import me.hydos.lint.core.block.BlockMaterial;
import me.hydos.lint.core.block.Model;
import me.hydos.lint.item.group.ItemGroups;
import me.hydos.lint.refactord.block.organic.LintSpreadableBlock;
import me.hydos.lint.refactord.block.organic.LintTallFlowerBlock;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.block.Material;
import net.minecraft.block.MaterialColor;
import net.minecraft.sound.BlockSoundGroup;

/**
 * All of lint's core blocks.
 */
public class LintBlocks {
	// Plants and Stuff

	public static final Block GENERIC_BLUE_FLOWER = BlockBuilder.create()
			.material(LintMaterials.TALL_FLOWER)
			.model(Model.TALL_PLANT)
			.itemGroup(ItemGroups.DECORATIONS)
			.register("generic_blue_flower", LintTallFlowerBlock.CONSTRUCTOR);

	// Soil

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

	public static final Block RICH_SOIL = BlockBuilder.create()
			.material(LintMaterials.FARMLAND)
			.model(Model.SIMPLE_BLOCKSTATE_ONLY)
			.register("rich_soil", FarmlandBlock::new);

	// Underground

	// Smeltery and Similar

	public static final Block BASIC_CASING = BlockBuilder.create()
			.material(LintMaterials.SMELTERY)
			.model(Model.SIMPLE_CUBE_ALL)
			.register("basic_casing");

	public static final Block CRACKED_BASIC_CASING = BlockBuilder.create()
			.material(LintMaterials.SMELTERY)
			.model(Model.SIMPLE_CUBE_ALL)
			.register("cracked_basic_casing");

	public static final Block CERAMIC = BlockBuilder.create()
			.material(BlockMaterial.builder()
					.material(Material.STONE)
					.colour(MaterialColor.ORANGE)
					.hardness(1.25f)
					.resistance(4.2f)
					.requiresTool())
			.model(Model.SIMPLE_CUBE_ALL)
			.register("ceramic");

	// The Portal

	public static final Block HAYKAMIUM_PORTAL = BlockBuilder.create()
			.material(BlockMaterial.builder()
					.strength(-1.0f)
					.collidable(false)
					.sounds(BlockSoundGroup.STONE))
			.model(Model.SIMPLE_CUBE_ALL)
			.itemGroup(null)
			.register("haykamium_portal");
	
	// Misc
	
	public static final Block COOKIE = BlockBuilder.create()
			.material(BlockMaterial.copy(Blocks.CAKE))
			.model(Model.SIMPLE_CUBE_ALL)
			.itemGroup(ItemGroups.FOOD)
			.register("cookie");

	private static final class FarmlandBlock extends net.minecraft.block.FarmlandBlock {
		protected FarmlandBlock(Settings settings) {
			super(settings);
		}
	}
}
