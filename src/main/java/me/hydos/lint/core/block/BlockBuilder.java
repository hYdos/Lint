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

import org.jetbrains.annotations.Nullable;

import me.hydos.lint.Lint;
import me.hydos.lint.item.group.ItemGroups;
import me.hydos.lint.mixin.FireBlockAccessor;
import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.minecraft.block.AbstractBlock;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.util.registry.Registry;

/**
 * A material oriented builder pipeline for the efficient and easily readable construction of blocks.
 * Almost identical to vanilla with some tweaks.
 *
 * @reason Insurance against mojang's refactors bc we have so many blocks.
 */
public class BlockBuilder {
	private Model model = null;
	private ItemGroup itemGroup = ItemGroups.BLOCKS;
	private BlockMaterial material;

	public BlockBuilder material(BlockMaterial material) throws IllegalStateException {
		if (this.material.material == null || this.material.materialColour == null) {
			throw new IllegalStateException("Material and Material colour must both be non-null.");
		}

		this.material = material;
		return this;
	}

	public BlockBuilder model(Model model) {
		this.model = model;
		return this;
	}

	public BlockBuilder itemGroup(@Nullable ItemGroup itemGroup) {
		this.itemGroup = itemGroup;
		return this;
	}

	public Block register(String id) {
		return this.register(id, DEFAULT_CONSTRUCTOR);
	}

	public <T extends Block> T register(String id, BlockConstructor<T> constructor) {
		FabricBlockSettings settings = FabricBlockSettings.copyOf(AbstractBlock.Settings.of(material.material, material.materialColour))
				.sounds(material.sounds)
				.luminance(material.luminosity)
				.slipperiness(material.slipperiness)
				.strength(material.hardness, material.resistance)
				.collidable(material.collidable);

		if (this.material.ticksRandomly) {
			settings.ticksRandomly();
		}

		if (this.material.dropsNothing) {
			settings.dropsNothing();
		}

		if (this.material.toolRequired) {
			settings.requiresTool();
		}

		if (this.material.toolType != null) {
			settings.breakByTool(this.material.toolType, this.material.miningLevel);
		}

		// Model gets priority over the material since material can only gain this property by inheriting in a "copy" call
		if (this.model.opaque != null) {
			if (!this.model.opaque) {
				settings.nonOpaque();
			}
		} else if (this.material.opaque != null) {
			if (!this.material.opaque) {
				settings.nonOpaque();
			}
		}

		T result = register(id, constructor.create(settings)); // Yeah using a material is required dummy
		this.model.createFor(result, id);

		if (this.material.burnChance > -1) {
			((FireBlockAccessor) Blocks.FIRE).callRegisterFlammableBlock(result, this.material.burnChance, this.material.spreadChance);
		}

		// Block Item
		Item.Settings blockItemSettings = new Item.Settings().group(this.itemGroup);
		Registry.register(Registry.ITEM, Lint.id(id), new BlockItem(result, blockItemSettings));

		return result;
	}

	public static BlockBuilder create() {
		return new BlockBuilder();
	}

	private static <T extends Block> T register(String id, T block) {
		Registry.register(Registry.BLOCK, Lint.id(id), block);
		return block;
	}

	private static final BlockConstructor<Block> DEFAULT_CONSTRUCTOR = Block::new;

	/**
	 * Functional interface for constructing a block instance.
	 */
	@FunctionalInterface
	public interface BlockConstructor<T extends Block> {
		T create(FabricBlockSettings settings);
	}
}
