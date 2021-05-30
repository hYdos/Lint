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
import me.hydos.lint.util.TriFunction;
import net.minecraft.util.registry.BuiltinRegistries;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.gen.feature.ConfiguredFeature;
import net.minecraft.world.gen.feature.ConfiguredFeatures;

public enum Placement implements TriFunction<String, ConfiguredFeature<?, ?>, Object, ConfiguredFeature<?, ?>> {
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
	};

	private Placement() {
	}

	/**
	 * Sets up the placement for the configured feature provided.
	 * @return a new feature with the given placement algorithm and settings.
	 */
	@Override
	public abstract ConfiguredFeature<?, ?> apply(String id, ConfiguredFeature<?, ?> feature, Object config);

	private static ConfiguredFeature<?, ?> register(String id, ConfiguredFeature<?, ?> result) {
		return Registry.register(BuiltinRegistries.CONFIGURED_FEATURE, Lint.id(id), result);
	}
}
