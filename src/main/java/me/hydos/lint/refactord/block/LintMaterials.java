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
}
