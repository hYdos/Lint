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

import me.hydos.lint.core.block.BlockMaterial;
import net.fabricmc.fabric.api.tool.attribute.v1.FabricToolTags;
import net.minecraft.block.Blocks;
import net.minecraft.block.Material;
import net.minecraft.block.MaterialColor;
import net.minecraft.sound.BlockSoundGroup;

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
	public static final BlockMaterial GRASS_BLOCK = BlockMaterial.copy(Blocks.GRASS_BLOCK).template();

	public static final BlockMaterial FARMLAND = BlockMaterial.copy(Blocks.FARMLAND).template();

	public static final BlockMaterial SMELTERY = BlockMaterial.builder()
			.material(Material.STONE)
			.colour(MaterialColor.STONE)
			.hardness(1.5f)
			.resistance(6.0f)
			.requiresTool()
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
			.requiresTool()
			.miningLevel(FabricToolTags.PICKAXES, 0)
			.sounds(BlockSoundGroup.STONE)
			.template();
	
	public static final BlockMaterial COBBLESTONE = STONE.hardness(1.5f).template();

	public static final BlockMaterial PLANKS = BlockMaterial.builder()
			.material(Material.WOOD)
			.colour(MaterialColor.WOOD)
			.hardness(2.0f) // TODO ytf is this higher than stone. Is it just because of the whole effect of preferred tool on hardness?
			.resistance(3.0f)
			.miningLevel(FabricToolTags.AXES, 0)
			.flammability(5, 20)
			.sounds(BlockSoundGroup.SAND);
}
