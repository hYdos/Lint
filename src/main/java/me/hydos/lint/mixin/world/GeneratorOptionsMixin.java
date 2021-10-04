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

import java.util.Optional;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import me.hydos.lint.world.gen.terrain.TerrainChunkGenerator;
import net.minecraft.util.registry.SimpleRegistry;
import net.minecraft.world.dimension.DimensionOptions;
import net.minecraft.world.gen.GeneratorOptions;

@Mixin(GeneratorOptions.class)
public class GeneratorOptionsMixin {
	/**
	 * World seed for worldgen when not specified by JSON by Haven King
	 * https://github.com/Hephaestus-Dev/seedy-behavior/blob/master/src/main/java/dev/hephaestus/seedy/mixin/world/gen/GeneratorOptionsMixin.java
	 * 
	 * tele jigsaw grunt pathic telepathic st'ructure processorjigsaw worldgenjson 
	 */
	@Inject(method = "<init>(JZZLnet/minecraft/util/registry/SimpleRegistry;Ljava/util/Optional;)V",
			at = @At(value = "RETURN"))
	private void lint_giveUsTrueWorldSeed(long seed, boolean generateStructures, boolean bonusChest, SimpleRegistry<DimensionOptions> simpleRegistry, Optional<String> legacyCustomOptions, CallbackInfo ci) {
		TerrainChunkGenerator.trueWorldSeed = seed;
	}
}
