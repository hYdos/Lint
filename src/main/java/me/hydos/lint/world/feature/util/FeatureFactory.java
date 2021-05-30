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

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import com.mojang.serialization.Codec;

import me.hydos.lint.Lint;
import me.hydos.lint.world.feature.util.WorldModifier.GenerationSettings;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.registry.BuiltinRegistries;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.StructureWorldAccess;
import net.minecraft.world.gen.chunk.ChunkGenerator;
import net.minecraft.world.gen.feature.ConfiguredFeature;
import net.minecraft.world.gen.feature.Feature;
import net.minecraft.world.gen.feature.FeatureConfig;

/**
 * @reason Mojang loves refactoring so if we abstract it that's 10x less patches we have to make and our code stays nice looking.
 */
public class FeatureFactory {
	private FeatureFactory() {
		// NO-OP
	}

	private static final Map<WorldModifier<?>, Feature<?>> REGISTERED_FEATURES = new HashMap<>();

	@SuppressWarnings("unchecked")
	public static <C extends FeatureConfig> ConfiguredFeature<C, ?> register(String cfId, WorldModifier<C> modifier, C config, Codec<C> configCodec) {
		// The most based line of code I have ever written
		Feature<C> feature = (Feature<C>) REGISTERED_FEATURES.computeIfAbsent(modifier,
				m -> Registry.register(Registry.FEATURE, Lint.id(m.id()), new LintFeature<>(modifier, configCodec))
				);

		return Registry.register(BuiltinRegistries.CONFIGURED_FEATURE, Lint.id(cfId), feature.configure(config)); // easy enough, at least for now. Just register it to the builtin regsitry and return it
	}

	private static class LintFeature<C extends FeatureConfig> extends Feature<C> {
		public LintFeature(WorldModifier<C> modifier, Codec<C> configCodec) {
			super(configCodec);
			this.modifier = modifier;
		}

		private final WorldModifier<C> modifier;

		@Override
		public boolean generate(StructureWorldAccess world,
				ChunkGenerator generator, Random random, BlockPos origin,
				C config) {
			// Stop mojang messing with parameters by making a class to do the dirty work
			return modifier.modify(new GenerationSettings<>(world, generator, random, origin, config));
		}

	}
}
