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

package me.hydos.lint.world.structure;

import com.mojang.datafixers.util.Either;
import me.hydos.lint.Lint;
import me.hydos.lint.world.feature.DungeonFeature;
import net.fabricmc.fabric.api.structure.v1.FabricStructureBuilder;
import net.minecraft.structure.Structure;
import net.minecraft.structure.pool.LegacySinglePoolElement;
import net.minecraft.structure.pool.SinglePoolElement;
import net.minecraft.structure.pool.StructurePool;
import net.minecraft.structure.pool.StructurePool.Projection;
import net.minecraft.structure.processor.StructureProcessorList;
import net.minecraft.util.Identifier;
import net.minecraft.world.gen.GenerationStep;
import net.minecraft.world.gen.feature.StructureFeature;
import net.minecraft.world.gen.feature.StructurePoolFeatureConfig;

import java.util.function.Function;
import java.util.function.Supplier;

public class Structures {

	/**
	 * Lint Dungeon
	 */
	public static final StructureFeature<StructurePoolFeatureConfig> DUNGEON = FabricStructureBuilder.create(
			Lint.id("dungeon"),
			new DungeonFeature(StructurePoolFeatureConfig.CODEC))
			.step(GenerationStep.Feature.SURFACE_STRUCTURES)
			.defaultConfig(32, 8, 6969420)
			.register();

	public static void initialize() {
	}

	public static Function<StructurePool.Projection, LegacySinglePoolElement> createLegacySinglePoolElement(String path, StructureProcessorList structureProcessorList) {
		return (projection) -> new LegacySinglePoolElement(Either.left(Lint.id(path)), () -> structureProcessorList, projection);
	}

	public static Function<StructurePool.Projection, SinglePoolElement> createSinglePoolElement(String path, StructureProcessorList structureProcessorList) {
		return (projection) -> new SinglePoolElement(Either.left(Lint.id(path)), () -> structureProcessorList, projection){};
	}

}