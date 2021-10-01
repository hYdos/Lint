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

package me.hydos.lint.world.feature.util;

import com.google.common.base.Preconditions;

import me.hydos.lint.Lint;
import net.minecraft.util.registry.BuiltinRegistries;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.gen.YOffset;
import net.minecraft.world.gen.decorator.CountExtraDecoratorConfig;
import net.minecraft.world.gen.decorator.Decorator;
import net.minecraft.world.gen.decorator.RangeDecoratorConfig;
import net.minecraft.world.gen.feature.ConfiguredFeature;
import net.minecraft.world.gen.feature.ConfiguredFeatures;
import net.minecraft.world.gen.heightprovider.UniformHeightProvider;

// TODO: use this
public enum Placement {
	/**
	 * Count config for Random Patch Features.
	 */
	COUNT_PATCH {
		@Override
		public ConfiguredFeature<?, ?> apply(String id, ConfiguredFeature<?, ?> feature,
		                                     Object config) {
			Preconditions.checkArgument(config instanceof Integer, "config must be of type integer in COUNT_PATCH");
			return register(id, feature.decorate(ConfiguredFeatures.Decorators.SQUARE_HEIGHTMAP_SPREAD_DOUBLE).repeat((Integer) config));
		}
	},

	/**
	 * Chance config for Random Patch Features.
	 */
	CHANCE_PATCH {
		@Override
		public ConfiguredFeature<?, ?> apply(String id, ConfiguredFeature<?, ?> feature,
		                                     Object config) {
			Preconditions.checkArgument(config instanceof Integer, "config must be of type integer in CHANCE_PATCH");
			return register(id, feature.decorate(ConfiguredFeatures.Decorators.SQUARE_HEIGHTMAP_SPREAD_DOUBLE).applyChance((Integer) config));
		}
	},

	/**
	 * Count config for general features.
	 */
	COUNT_WORLD_SURFACE {
		@Override
		public ConfiguredFeature<?, ?> apply(String id, ConfiguredFeature<?, ?> feature,
		                                     Object config) {
			Preconditions.checkArgument(config instanceof Integer, "config must be of type integer in COUNT_WORLD_SURFACE");
			return register(id, feature.decorate(ConfiguredFeatures.Decorators.HEIGHTMAP_WORLD_SURFACE).spreadHorizontally().repeat((Integer) config));
		}
	},

	/**
	 * Count/Chance config for tree features.
	 */
	TREE_WORLD_SURFACE {
		@Override
		public ConfiguredFeature<?, ?> apply(String id, ConfiguredFeature<?, ?> feature,
				Object config) {
			Preconditions.checkArgument(config instanceof CountExtraDecoratorConfig, "config must be of type CountExtraDecoratorConfig in TREE_WORLD_SURFACE");
			return register(id, feature.decorate(ConfiguredFeatures.Decorators.SQUARE_HEIGHTMAP_OCEAN_FLOOR_NO_WATER).decorate(Decorator.COUNT_EXTRA.configure((CountExtraDecoratorConfig) config)));
		}
	},

	/**
	 * Chance config for general features.
	 */
	CHANCE_WORLD_SURFACE {
		@Override
		public ConfiguredFeature<?, ?> apply(String id, ConfiguredFeature<?, ?> feature,
		                                     Object config) {
			Preconditions.checkArgument(config instanceof Integer, "config must be of type integer in CHANCE_WORLD_SURFACE");
			return register(id, feature.decorate(ConfiguredFeatures.Decorators.HEIGHTMAP_WORLD_SURFACE).spreadHorizontally().applyChance((Integer) config));
		}
	},

	/**
	 * Config for Special bunnies.
	 */
	UNIFORM_WORLD_SURFACE {
		@Override
		public ConfiguredFeature<?, ?> apply(String id, ConfiguredFeature<?, ?> feature,
		                                     Object config) {
			Preconditions.checkArgument(config instanceof Integer, "config must be of type integer in UNIFORM_WORLD_SURFACE");
			return register(id, feature.decorate(ConfiguredFeatures.Decorators.HEIGHTMAP_WORLD_SURFACE));
		}
	},

	/**
	 * Chance config for general non-heightmap based features.
	 */
	CHANCE_SIMPLE {
		@Override
		public ConfiguredFeature<?, ?> apply(String id, ConfiguredFeature<?, ?> feature,
				Object config) {
			Preconditions.checkArgument(config instanceof Integer, "config must be of type integer in CHANCE_SIMPLE");
			return register(id, feature.spreadHorizontally().applyChance((Integer) config));
		}
	},

	/**
	 * Chance config for range features. Config is an int[3]: {CHANCE, MIN, MAX}.
	 */
	CHANCE_RANGE {
		@Override
		public ConfiguredFeature<?, ?> apply(String id, ConfiguredFeature<?, ?> feature,
				Object config) {
			Preconditions.checkArgument(config instanceof int[], "config must be an int[3] in CHANCE_RANGE (incorrect type)");
			int[] configuration = (int[]) config;
			Preconditions.checkArgument(configuration.length == 3, "config must be an int[3] in CHANCE_RANGE (incorrect size)");
			return register(id, feature
					.range(new RangeDecoratorConfig(UniformHeightProvider.create(
							YOffset.fixed(configuration[1]),
							YOffset.fixed(configuration[2])
							)))
					.spreadHorizontally()
					.applyChance(configuration[0]));
		}
	};

	Placement() {
	}

	/**
	 * Sets up the placement for the configured feature provided.
	 *
	 * @return a new feature with the given placement algorithm and settings.
	 */
	public abstract ConfiguredFeature<?, ?> apply(String id, ConfiguredFeature<?, ?> feature, Object config);

	private static ConfiguredFeature<?, ?> register(String id, ConfiguredFeature<?, ?> result) {
		return Registry.register(BuiltinRegistries.CONFIGURED_FEATURE, Lint.id(id), result);
	}
}
