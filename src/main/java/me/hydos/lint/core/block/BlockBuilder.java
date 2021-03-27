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

import static me.hydos.lint.Lint.RESOURCE_PACK;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

import org.jetbrains.annotations.Nullable;

import me.hydos.lint.Lint;
import me.hydos.lint.item.group.ItemGroups;
import me.hydos.lint.mixin.FireBlockAccessor;
import net.devtech.arrp.json.loot.JCondition;
import net.devtech.arrp.json.loot.JLootTable;
import net.devtech.arrp.json.models.JModel;
import net.fabricmc.api.EnvType;
import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.block.AbstractBlock;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.util.Identifier;
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
	private boolean defaultLootTable = true;
	private Function<Identifier, JModel> itemModel = PARENTED;

	public BlockBuilder material(BlockMaterial material) throws IllegalStateException {
		if (material.material == null || material.materialColour == null) {
			throw new IllegalStateException("Material and Material colour must both be non-null.");
		}

		this.material = material;
		return this;
	}

	public BlockBuilder model(Model model) {
		this.model = model;
		return this;
	}

	/**
	 * Sets the item group for the block item. Default item group is Lint's BLOCKS group. Null is allowed.
	 */
	public BlockBuilder itemGroup(@Nullable ItemGroup itemGroup) {
		this.itemGroup = itemGroup;
		return this;
	}

	/**
	 * Tells the block builder not to add the default loot table.
	 */
	public BlockBuilder customLootTable() {
		this.defaultLootTable = false;
		return this;
	}

	// TODO make a method for other java loot tables.

	/**
	 * Replace the default item model. Function takes the item id (NOT the model id) and returns the item JModel.
	 */
	public BlockBuilder itemModel(Function<Identifier, JModel> modelCreator) {
		this.itemModel = modelCreator;
		return this;
	}

	public Block register(String id) {
		return this.register(id, DEFAULT_CONSTRUCTOR);
	}

	public <T extends Block> T register(String id, BlockConstructor<T> constructor) {
		if (this.model == null) {
			throw new IllegalStateException("Required Property: Model");
		}
		
		if (this.material == null) {
			throw new IllegalStateException("Required Property: Material");
		}

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

		// might be burny
		if (this.material.burnChance > -1) {
			((FireBlockAccessor) Blocks.FIRE).callRegisterFlammableBlock(result, this.material.burnChance, this.material.spreadChance);
		}

		Identifier idl = Lint.id(id);

		// Block Item
		Item.Settings blockItemSettings = new Item.Settings().group(this.itemGroup);
		Registry.register(Registry.ITEM, idl, new BlockItem(result, blockItemSettings));

		if (this.defaultLootTable) {
			RESOURCE_PACK.addLootTable(new Identifier(idl.getNamespace(), "blocks/" + idl.getPath()),
					JLootTable.loot("minecraft:block")
					.pool(JLootTable.pool()
							.rolls(1)
							.entry(JLootTable.entry()
									.type("minecraft:item")
									.name(id.toString()))
							.condition(new JCondition("minecraft:survives_explosion"))));
		}
		
		// Add the item model
		RESOURCE_PACK.addModel(this.itemModel.apply(idl), Lint.id("item/" + id));

		// Render Layer Shenanigans
		if (FabricLoader.getInstance().getEnvironmentType() == EnvType.CLIENT) {
			CUSTOM_BLOCK_RENDER_LAYERS.put(result, model.renderLayer);
		}

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
	private static final Function<Identifier, JModel> PARENTED = id -> JModel.model().parent(Lint.id("block/" + id.getPath()).toString());
	public static final Map<Block, Layer> CUSTOM_BLOCK_RENDER_LAYERS = new HashMap<>();

	/**
	 * Functional interface for constructing a block instance.
	 */
	@FunctionalInterface
	public interface BlockConstructor<T extends Block> {
		T create(FabricBlockSettings settings);
	}
}
