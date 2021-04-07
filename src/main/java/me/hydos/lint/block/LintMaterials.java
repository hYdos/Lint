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

import me.hydos.lint.block.util.BlockMaterial;
import net.fabricmc.fabric.api.tool.attribute.v1.FabricToolTags;
import net.minecraft.block.Blocks;
import net.minecraft.block.Material;
import net.minecraft.block.MaterialColor;
import net.minecraft.block.PillarBlock;
import net.minecraft.sound.BlockSoundGroup;
import net.minecraft.util.math.Direction;

/*
 * Please Follow this order for materials in this file for easy reading:
 * 1. builder() or copy()
 * 2. material
 * 3. colour
 * 4. hardness and resistance
 * 5. tool related stuff
 * 6. other
 * 7. sounds
 * 8. make it a template so modifications are redirected to making new instances.
 */
public class LintMaterials {
	public static final BlockMaterial log(MaterialColor top, MaterialColor side) {
		return BlockMaterial.builder()
				.material(Material.WOOD)
				.colour(state -> state.get(PillarBlock.AXIS) == Direction.Axis.Y ? top : side)
				.strength(2.0f)
				.miningLevel(FabricToolTags.AXES, -1)
				.flammability(5, 5)
				.sounds(BlockSoundGroup.WOOD); // Template not neccessary here because we make a new instance each call.
	}

	public static final BlockMaterial GRASS_BLOCK = BlockMaterial.copy(Blocks.GRASS_BLOCK).template();

	public static final BlockMaterial DIRT = BlockMaterial.builder()
			.material(Material.SOIL)
			.colour(MaterialColor.DIRT)
			.strength(0.5f)
			.sounds(BlockSoundGroup.GRAVEL)
			.miningLevel(FabricToolTags.SHOVELS, 1)
			.template();

	public static final BlockMaterial FARMLAND = BlockMaterial.copy(Blocks.FARMLAND).template();

	public static final BlockMaterial SMELTERY = BlockMaterial.builder()
			.material(Material.STONE)
			.colour(MaterialColor.STONE)
			.hardness(1.5f)
			.resistance(6.0f)
			.miningLevel(FabricToolTags.PICKAXES, 0)
			.sounds(BlockSoundGroup.STONE)
			.template();

	public static final BlockMaterial TALL_FLOWER = BlockMaterial.builder()
			.material(Material.REPLACEABLE_PLANT)
			.colour(MaterialColor.FOLIAGE)
			.collidable(false)
			.breaksInstantly()
			.sounds(BlockSoundGroup.GRASS)
			.template();

	public static final BlockMaterial STONE = BlockMaterial.builder()
			.material(Material.STONE)
			.colour(MaterialColor.STONE)
			.hardness(1.75f) // bumped by 1.25f compared to previously
			.resistance(6.0f)
			.miningLevel(FabricToolTags.PICKAXES, 0)
			.sounds(BlockSoundGroup.STONE)
			.template();
	
	public static final BlockMaterial COBBLESTONE = STONE.hardness(1.5f).template();

	public static final BlockMaterial PLANKS = BlockMaterial.builder()
			.material(Material.WOOD)
			.colour(MaterialColor.WOOD)
			.hardness(2.0f) // TODO ytf is this higher than stone. Is it just because of the whole effect of preferred tool on hardness?
			.resistance(3.0f)
			.miningLevel(FabricToolTags.AXES, -1)
			.flammability(5, 20)
			.sounds(BlockSoundGroup.SAND)
			.template();

	public static final BlockMaterial LEAVES = BlockMaterial.copy(Blocks.OAK_LEAVES)
			.colour(MaterialColor.DIAMOND)
			.miningLevel(FabricToolTags.HOES, -1)
			.flammability(30, 60)
			.template();

	public static final BlockMaterial SAND = BlockMaterial.builder()
			.material(Material.AGGREGATE)
			.colour(MaterialColor.SAND)
			.hardness(0.5f)
			.miningLevel(FabricToolTags.SHOVELS, -1)
			.sounds(BlockSoundGroup.SAND)
			.template();

	public static final BlockMaterial DUNGEON = BlockMaterial.builder()
			.material(Material.STONE)
			.colour(MaterialColor.STONE)
			.hardness(6.0f)
			.resistance(9.0f)
			.miningLevel(FabricToolTags.PICKAXES, 2)
			.sounds(BlockSoundGroup.STONE)
			.template();

	public static final BlockMaterial TUSSOCK = BlockMaterial.builder()
			.material(Material.PLANT)
			.colour(MaterialColor.FOLIAGE)
			.hardness(0.1f)
			.collidable(false)
			.sounds(BlockSoundGroup.GRASS)
			.template();

	public static final BlockMaterial PLANT = TUSSOCK
			.breaksInstantly()
			.hardness(0)
			.template();

	public static final BlockMaterial POWER_CRYSTAL = BlockMaterial.copy(Blocks.GLASS) // TODO add context predicate functionality to material
			.hardness(18.0F)
			.resistance(1200.0F)
			.template();

	public static final BlockMaterial SAPLING = BlockMaterial.copy(Blocks.ACACIA_SAPLING).template();
	
	public static final BlockMaterial FALLEN_LEAVES = BlockMaterial.builder()
			.material(Material.LEAVES)
			.colour(MaterialColor.FOLIAGE)
			.strength(0.5f)
			.flammability(30, 60)
			.template();
}
