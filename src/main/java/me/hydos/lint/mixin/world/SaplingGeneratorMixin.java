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

import java.util.Random;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

import me.hydos.lint.world.dimension.LintDimensions;
import me.hydos.lint.world.feature.Features;
import me.hydos.lint.world.gen.FraiyaTerrainGenerator;
import net.minecraft.block.sapling.SaplingGenerator;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.gen.chunk.ChunkGenerator;
import net.minecraft.world.gen.feature.ConfiguredFeature;
import net.minecraft.world.gen.feature.TreeFeatureConfig;

@Mixin(SaplingGenerator.class)
public abstract class SaplingGeneratorMixin {
	/**
	 * @reason saplings that try to grow in the dawn shardlands become withered trees instead.
	 * TODO maybe make the sapling itself wither instead? Decide whether to keep this or do that.
	 */
	@Redirect(method = "generate", at = @At(value= "INVOKE", target = "Lnet/minecraft/block/sapling/SaplingGenerator;getTreeFeature(Ljava/util/Random;Z)Lnet/minecraft/world/gen/feature/ConfiguredFeature;"))
	private ConfiguredFeature<TreeFeatureConfig, ?> witherShardlandsTrees(SaplingGenerator generator, Random rand, boolean bees, ServerWorld world, ChunkGenerator cgenerator, BlockPos pos) {
		if (world.getRegistryKey().equals(LintDimensions.FRAIYA_WORLD) && (pos.getX() * pos.getX() + pos.getZ() * pos.getZ()) > FraiyaTerrainGenerator.SHARDLANDS_START) {
			return Features.WITHERED_TREE;
		}

		return this.getTreeFeature(rand, bees);
	}

	@Shadow
	protected abstract ConfiguredFeature<TreeFeatureConfig, ?> getTreeFeature(Random random, boolean bees);
}
