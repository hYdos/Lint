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

package me.hydos.lint.world.feature;

import com.mojang.serialization.Codec;
import net.minecraft.structure.StructureManager;
import net.minecraft.structure.StructurePiece;
import net.minecraft.util.math.ChunkPos;
import net.minecraft.util.registry.DynamicRegistryManager;
import net.minecraft.world.HeightLimitView;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.gen.chunk.ChunkGenerator;
import net.minecraft.world.gen.feature.JigsawFeature;
import net.minecraft.world.gen.feature.StructureFeature;
import net.minecraft.world.gen.feature.StructurePoolFeatureConfig;

public class DungeonFeature extends JigsawFeature {
	public DungeonFeature(Codec<StructurePoolFeatureConfig> codec) {
		super(codec, 0, true, true);
	}

	//TODO: make the entry the 1st object
	@Override
	public StructureStartFactory<StructurePoolFeatureConfig> getStructureStartFactory() {
		return Start::new;
	}

	public class Start extends JigsawFeature.Start {

		public Start(StructureFeature<StructurePoolFeatureConfig> feature, ChunkPos chunkPos, int i, long l) {
			super(DungeonFeature.this, chunkPos, i, l);
		}

		@Override
		public void init(DynamicRegistryManager dynamicRegistryManager, ChunkGenerator chunkGenerator, StructureManager structureManager, ChunkPos chunkPos, Biome biome, StructurePoolFeatureConfig structurePoolFeatureConfig, HeightLimitView heightLimitView) {
			super.init(dynamicRegistryManager, chunkGenerator, structureManager, chunkPos, biome, structurePoolFeatureConfig, heightLimitView);

			for (StructurePiece child : children) {
				child.translate(0, -16, 0);
			}
		}
	}
}
