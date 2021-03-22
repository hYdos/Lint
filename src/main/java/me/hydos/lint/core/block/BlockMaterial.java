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

import java.util.function.Function;
import java.util.function.ToIntFunction;

import net.fabricmc.fabric.mixin.object.builder.AbstractBlockAccessor;
import net.fabricmc.fabric.mixin.object.builder.AbstractBlockSettingsAccessor;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Material;
import net.minecraft.block.MaterialColor;
import net.minecraft.item.Item;
import net.minecraft.sound.BlockSoundGroup;
import net.minecraft.tag.Tag;

/**
 * Represents the properties of a block material.
 */
public class BlockMaterial {
	private BlockMaterial(BlockMaterial builder) {
		this.material = builder.material;
		this.materialColour = builder.materialColour;
		this.hardness = builder.hardness;
		this.luminosity = builder.luminosity;
		this.resistance = builder.resistance;

		this.sounds = builder.sounds;
		this.ticksRandomly = builder.ticksRandomly;
		this.dropsNothing = builder.dropsNothing;
		this.slipperiness = builder.slipperiness;
		this.toolRequired = builder.toolRequired;
		this.collidable = builder.collidable;
		this.opaque = builder.opaque;
		
		this.toolType = builder.toolType;
		this.miningLevel = builder.miningLevel;
		
		this.burnChance = builder.burnChance;
		this.spreadChance = builder.spreadChance;
	}

	private BlockMaterial() {
	}

	// Based Properties
	Material material;
	Function<BlockState, MaterialColor> materialColour;
	float hardness = 0.0f;
	float resistance = 0.0f;
	ToIntFunction<BlockState> luminosity = ignored -> 0;

	// Cringe Properties
	BlockSoundGroup sounds = BlockSoundGroup.STONE;
	boolean ticksRandomly = false;
	boolean dropsNothing = false;
	float slipperiness = Float.NaN;
	boolean toolRequired = false;
	boolean collidable = true;
	Boolean opaque = null;
	
	// Blessed Fabric Stuff
	Tag<Item> toolType;
	int miningLevel;
	
	// Nice Lint Properties
	int burnChance = -1;
	int spreadChance;

	// Builder methods

	public Builder material(Material material) {
		return new Builder(this).material(material);
	}

	public Builder colour(MaterialColor colour) {
		return new Builder(this).colour(colour);
	}

	public Builder hardness(float hardness) {
		return new Builder(this).hardness(hardness);
	}

	public Builder resistance(float resistance) {
		return new Builder(this).resistance(resistance);
	}

	/**
	 * Sets both hardness and resistance to the same value.
	 */
	public Builder strength(float strength) {
		return new Builder(this).strength(strength);
	}

	public Builder luminosity(int luminosity) {
		return new Builder(this).luminosity(luminosity);
	}
	
	public Builder luminosity(ToIntFunction<BlockState> luminosity) {
		return new Builder(this).luminosity(luminosity);
	}

	public Builder sounds(BlockSoundGroup sounds) {
		return new Builder(this).sounds(sounds);
	}

	/**
	 * Sets hardness and resistance to 0.
	 */
	public Builder breaksInstantly() {
		return new Builder(this).breaksInstantly();
	}

	public Builder ticksRandomly() {
		return new Builder(this).ticksRandomly();
	}

	public Builder dropsNothing() {
		return new Builder(this).dropsNothing();
	}

	public Builder slipperiness(float slipperiness) {
		return new Builder(this).slipperiness(slipperiness);
	}

	public Builder requiresTool() {
		return new Builder(this).requiresTool();
	}

	public Builder collidable(boolean collidable) {
		return new Builder(this).collidable(this.collidable);
	}

	public Builder miningLevel(Tag<Item> toolType, int miningLevel) {
		return new Builder(this).miningLevel(toolType, miningLevel);
	}
	
	public Builder flammability(int burnChance, int spreadChance) {
		return new Builder(this).flammability(burnChance, spreadChance);
	}

	// Non Builder Stuff

	public static Builder builder() {
		return new Builder();
	}

	public static Builder copy(Block existing) {
		return new Builder(existing);
	}

	public static class Builder extends BlockMaterial {
		private Builder(BlockMaterial builder) {
			super(builder);
		}

		private Builder(Block existing) {
			AbstractBlockSettingsAccessor settings = (AbstractBlockSettingsAccessor) ((AbstractBlockAccessor) existing).getSettings();
			
			this.material = settings.getMaterial();
			this.materialColour = settings.getMaterialColorFactory();
			this.hardness = settings.getHardness();
			this.luminosity = settings.getLuminance();
			this.resistance = settings.getResistance();
			
			this.sounds = settings.getSoundGroup();
			this.ticksRandomly = settings.getRandomTicks();
			this.dropsNothing = false; // TODO how
			this.slipperiness = settings.getSlipperiness();
			this.toolRequired = settings.isToolRequired();
			this.collidable = settings.getCollidable();
			this.opaque = settings.getOpaque();
		}

		private Builder() {
		}

		@Override
		public Builder material(Material material) {
			this.material = material;
			return this;
		}

		@Override
		public Builder colour(MaterialColor colour) {
			this.materialColour = ignored -> colour;
			return this;
		}

		@Override
		public Builder hardness(float hardness) {
			this.hardness = hardness;
			return this;
		}

		@Override
		public Builder strength(float strength) {
			this.hardness = strength;
			this.resistance = strength;
			return this;
		}

		@Override
		public Builder resistance(float resistance) {
			this.resistance = resistance;
			return this;
		}

		@Override
		public Builder luminosity(int luminosity) {
			this.luminosity = ignored -> luminosity;
			return this;
		}

		@Override
		public Builder luminosity(ToIntFunction<BlockState> luminosity) {
			this.luminosity = luminosity;
			return this;
		}

		@Override
		public Builder sounds(BlockSoundGroup sounds) {
			this.sounds = sounds;
			return this;
		}

		@Override
		public Builder breaksInstantly() {
			return this.strength(0.0F);
		}

		@Override
		public Builder ticksRandomly() {
			this.ticksRandomly = true;
			return this;
		}

		@Override
		public Builder dropsNothing() {
			this.dropsNothing = true;
			return this;
		}
		
		@Override
		public Builder slipperiness(float slipperiness) {
			this.slipperiness = slipperiness;
			return this;
		}
		
		@Override
		public Builder requiresTool() {
			this.toolRequired = true;
			return this;
		}
		
		@Override
		public Builder collidable(boolean collidable) {
			this.collidable = collidable;
			return this;
		}
		
		@Override
		public Builder miningLevel(Tag<Item> toolType, int miningLevel) {
			this.toolType = toolType;
			this.miningLevel = miningLevel;
			return this;
		}

		@Override
		public Builder flammability(int burnChance, int spreadChance) {
			this.burnChance = burnChance;
			this.spreadChance = spreadChance;
			return this;
		}

		public BlockMaterial immutable() {
			return new BlockMaterial(this);
		}
	}
}
