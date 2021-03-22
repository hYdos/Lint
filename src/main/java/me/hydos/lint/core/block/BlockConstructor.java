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

package me.hydos.lint.core.block;

import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.minecraft.block.Block;

/**
 * A material oriented builder pipeline for the efficient and easily readable construction of blocks.
 */
public class BlockConstructor {
	private FabricBlockSettings settings = null;
	private Model model = null;

	public BlockConstructor material(BlockMaterial material) {
		if (material.material == null || material.materialColour == null) {
			throw new IllegalStateException("Material and Material colour must both be non-null.");
		}

		this.settings = FabricBlockSettings.of(material.material, material.materialColour)
				.luminance(material.luminosity);

		if (material.hardness != Float.NaN) {
			this.settings.hardness(material.hardness);
		}

		if (material.resistance != Float.NaN) {
			this.settings.resistance(material.resistance);
		}

		return this;
	}

	public Block register(String id) {
		return new Block(this.settings);
	}

	public static BlockConstructor create() {
		return new BlockConstructor();
	}
}
