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
import net.fabricmc.fabric.api.tool.attribute.v1.FabricToolTags;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.block.Material;
import net.minecraft.block.MaterialColor;
import net.minecraft.sound.BlockSoundGroup;

/**
 * All of lint's core blocks.
 */
public class LintBlocks2 {
	// Plants and Stuff

	public static final Block THAISA = BlockBuilder.create()
			.material(LintMaterials.TALL_FLOWER)
			.model(Model.TALL_PLANT)
			.itemGroup(ItemGroups.DECORATIONS)
			.customLootTable()
			.register("generic_blue_flower", LintTallFlowerBlock.CONSTRUCTOR);

	// Soil

	public static final Block CORRUPT_GRASS = BlockBuilder.create()
			.material(LintMaterials.GRASS_BLOCK
					.material(Material.SOLID_ORGANIC)
					.colour(MaterialColor.PURPLE))
			.model(Model.NONE)
			.customLootTable()
			.register("corrupt_grass", LintSpreadableBlock.CONSTRUCTOR);

	public static final Block LIVELY_GRASS = BlockBuilder.create()
			.material(LintMaterials.GRASS_BLOCK)
			.model(Model.NONE)
			.customLootTable()
			.register("lively_grass", LintSpreadableBlock.CONSTRUCTOR);

	public static final Block FROSTED_GRASS = BlockBuilder.create()
			.material(LintMaterials.GRASS_BLOCK)
			.model(Model.NONE)
			.customLootTable()
			.register("frosted_grass", LintSpreadableBlock.CONSTRUCTOR);

	public static final Block RICH_SOIL = BlockBuilder.create()
			.material(LintMaterials.FARMLAND)
			.model(Model.SIMPLE_BLOCKSTATE_ONLY)
			.register("rich_soil", FarmlandBlock::new);

	// Underground

	public static final Block INDIGO_STONE = BlockBuilder.create()
			.material(LintMaterials.STONE
					.colour(MaterialColor.PURPLE_TERRACOTTA))
			.model(Model.SIMPLE_CUBE_ALL)
			.register("indigo_stone");
	
	public static final Block FUSED_STONE = BlockBuilder.create()
			.material(LintMaterials.STONE)
			.model(Model.SIMPLE_CUBE_ALL)
			.customLootTable()
			.register("fused_stone");
	
	public static final Block FUSED_COBBLESTONE = BlockBuilder.create()
			.material(LintMaterials.COBBLESTONE)
			.model(Model.SIMPLE_CUBE_ALL)
			.register("fused_cobblestone");
	
	public static final Block PEARLESCENT_STONE = BlockBuilder.create()
			.material(LintMaterials.STONE
					.colour(MaterialColor.WHITE_TERRACOTTA))
			.model(Model.SIMPLE_CUBE_ALL)
			.customLootTable()
			.register("pearlescent_stone");
	
	public static final Block PEARLESCENT_COBBLESTONE = BlockBuilder.create()
			.material(LintMaterials.COBBLESTONE
					.colour(MaterialColor.WHITE_TERRACOTTA))
			.model(Model.SIMPLE_CUBE_ALL)
			.register("pearlescent_cobblestone");

	public static final Block TARSCAN_ORE = BlockBuilder.create()
			.material(LintMaterials.STONE
					.hardness(2.0f))
			.model(Model.SIMPLE_CUBE_ALL)
			.register("tarscan_ore");

	public static final Block MAGNETITE_DEPOSIT = BlockBuilder.create() // Used to use sound group metal but seeing as it is mineral form I think I should keep it as stone.
			.material(LintMaterials.STONE
					.hardness(2.75f))
			.model(Model.SIMPLE_CUBE_ALL)
			.register("magnetite_deposit");

	public static final Block SICIERON_ORE = BlockBuilder.create()
			.material(LintMaterials.STONE
					.hardness(3.0f)
					.miningLevel(FabricToolTags.PICKAXES, 1))
			.model(Model.SIMPLE_CUBE_ALL)
			.register("sicieron_ore");

	public static final Block JUREL_ORE = BlockBuilder.create()
			.material(LintMaterials.STONE
					.hardness(3.0f)
					.miningLevel(FabricToolTags.PICKAXES, 2))
			.model(Model.SIMPLE_CUBE_ALL)
			.register("jurel_ore");

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
					.material(Material.PORTAL)
					.colour(MaterialColor.CYAN)
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

	public static final Block initialise() {
		return COOKIE;
	}

	private static final class FarmlandBlock extends net.minecraft.block.FarmlandBlock {
		protected FarmlandBlock(Settings settings) {
			super(settings);
		}
	}
}
