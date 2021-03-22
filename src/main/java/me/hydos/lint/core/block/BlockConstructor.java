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

import me.hydos.lint.Lint;
import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.minecraft.block.AbstractBlock;
import net.minecraft.block.Block;
import net.minecraft.util.registry.Registry;

/**
 * A material oriented builder pipeline for the efficient and easily readable construction of blocks.
 * Almost identical to vanilla with some tweaks.
 *
 * @reason Insurance against mojang's refactors bc we have so many blocks.
 */
public class BlockConstructor {
	private FabricBlockSettings settings = null;
	private Model model = null;
	private Boolean opaque = null;

	public BlockConstructor material(BlockMaterial material) {
		if (material.material == null || material.materialColour == null) {
			throw new IllegalStateException("Material and Material colour must both be non-null.");
		}

		this.settings = FabricBlockSettings.copyOf(AbstractBlock.Settings.of(material.material, material.materialColour))
				.sounds(material.sounds)
				.luminance(material.luminosity)
				.slipperiness(material.slipperiness)
				.strength(material.hardness, material.resistance)
				.collidable(material.collidable);


		if (material.ticksRandomly) {
			this.settings.ticksRandomly();
		}

		if (material.dropsNothing) {
			this.settings.dropsNothing();
		}

		if (material.toolRequired) {
			this.settings.requiresTool();
		}

		if (material.toolType != null) {
			this.settings.breakByTool(material.toolType, material.miningLevel);
		}

		if (material.opaque != null && this.opaque != null) {
			this.opaque = material.opaque;
		}

		return this;
	}

	public BlockConstructor model(Model model) {
		this.model = model;
		
		if (model.opaque != null) {
			this.opaque = model.opaque;
		}

		return this;
	}

	// TODO functionality stuff and spreadable configs and stuff. Maybe another class for manufacturing the block itself or something idk

	public Block register(String id) {
		if (this.opaque != null) {
			if (!this.opaque) {
				this.settings.nonOpaque();
			}
		}

		Block result = register(id, new Block(this.settings)); // Yeah using a material is required dummy
		this.model.createFor(result, id);
		return result;
	}

	public static BlockConstructor create() {
		return new BlockConstructor();
	}

	private static <T extends Block> T register(String id, T block) {
		Registry.register(Registry.BLOCK, Lint.id(id), block);
		return block;
	}
}
