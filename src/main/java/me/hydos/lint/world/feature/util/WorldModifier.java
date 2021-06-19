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

import net.minecraft.util.math.BlockPos;
import net.minecraft.world.StructureWorldAccess;
import net.minecraft.world.gen.chunk.ChunkGenerator;
import net.minecraft.world.gen.feature.FeatureConfig;

import java.util.Random;

public interface WorldModifier<T extends FeatureConfig> {
    /**
     * Method to modify the contents of a world.
     * @param settings the generation settings.
     * @return whether generation was successful.
     */
    boolean place(GenerationSettings<T> settings);

    /**
     * @return the ID of this world modifier.
     */
    String id();

    class GenerationSettings<C extends FeatureConfig> {
        /**
         * Construct a settings instance for the world modifier.
         * @param world the world.
         * @param generator the chunk generator.
         * @param rand the random instance.
         * @param origin the position at which to generate.
         * @param config the feature config.
         */
        public GenerationSettings(StructureWorldAccess world, ChunkGenerator generator,
                                  Random rand, BlockPos origin, C config) {
            this.world = world;
            this.generator = generator;
            this.random = rand;
            this.origin = origin;
            this.config = config;
        }

        public final StructureWorldAccess world;
        public final ChunkGenerator generator;
        public final Random random;
        public final BlockPos origin;
        public final C config;
    }
}
