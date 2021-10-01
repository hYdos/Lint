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

package me.hydos.lint.mixin.world;

import com.mojang.serialization.Lifecycle;
import me.hydos.lint.Lint;
import me.hydos.lint.world.dimension.Dimensions;
import me.hydos.lint.world.gen.terrain.TerrainChunkGenerator;
import net.minecraft.util.registry.DynamicRegistryManager;
import net.minecraft.util.registry.Registry;
import net.minecraft.util.registry.SimpleRegistry;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.dimension.DimensionOptions;
import net.minecraft.world.dimension.DimensionType;
import net.minecraft.world.gen.chunk.ChunkGeneratorSettings;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(DimensionType.class)
public class DimensionTypeMixin {
	@Inject(method = "addRegistryDefaults", at = @At("TAIL"))
	private static void addAdditionalDefaults(DynamicRegistryManager.Impl registryManager, CallbackInfoReturnable<DynamicRegistryManager.Impl> cir) {
		Registry.register(registryManager.getMutable(Registry.DIMENSION_TYPE_KEY), Dimensions.FRAIYA_DIM.getValue(), Dimensions.FRAIYA);
	}

	@Inject(method = "createDefaultDimensionOptions", at = @At("TAIL"))
	private static void addAdditionalDefaultDimensionOptions(Registry<DimensionType> dimensionRegistry, Registry<Biome> biomeRegistry, Registry<ChunkGeneratorSettings> settingsRegistry, long seed, CallbackInfoReturnable<SimpleRegistry<DimensionOptions>> cir) {
		cir.getReturnValue().add(Dimensions.FRAIYA_DIM_OPTIONS, new DimensionOptions(() -> dimensionRegistry.getOrThrow(Dimensions.FRAIYA_DIM), new TerrainChunkGenerator(seed, Lint.id("fraiya"), biomeRegistry)), Lifecycle.stable());
	}
}
