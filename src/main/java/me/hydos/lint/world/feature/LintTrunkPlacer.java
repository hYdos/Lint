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

import java.util.List;
import java.util.Random;
import java.util.Set;

import com.google.common.collect.ImmutableList;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;

import me.hydos.lint.Lint;
import me.hydos.lint.block.LintBlocks;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.util.math.BlockBox;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.ModifiableTestableWorld;
import net.minecraft.world.TestableWorld;
import net.minecraft.world.gen.feature.Feature;
import net.minecraft.world.gen.feature.TreeFeature;
import net.minecraft.world.gen.feature.TreeFeatureConfig;
import net.minecraft.world.gen.foliage.FoliagePlacer;
import net.minecraft.world.gen.trunk.TrunkPlacer;
import net.minecraft.world.gen.trunk.TrunkPlacerType;

public class LintTrunkPlacer extends TrunkPlacer {

	public static final Codec<LintTrunkPlacer> CODEC = RecordCodecBuilder.create(instance -> method_28904(instance)
			.apply(instance, LintTrunkPlacer::new));

	public static final TrunkPlacerType<LintTrunkPlacer> STRAIGHT_TRUNK_PLACER = Registry.register(Registry.TRUNK_PLACER_TYPE, Lint.id("trunk_placer"), new TrunkPlacerType<>(CODEC));

	public LintTrunkPlacer(int i, int j, int k) {
		super(i, j, k);
	}

	private static boolean canGenerate(TestableWorld world, BlockPos pos) {
		return world.testBlockState(pos, (state) -> {
			Block block = state.getBlock(); // wtf is this not checking for lint blocks i mean it does't seem to be changing anyhting so ok
			return Feature.isSoil(block) && !state.isOf(Blocks.GRASS_BLOCK) && !state.isOf(Blocks.MYCELIUM);
		});
	}

	protected static void setToLintDirt(ModifiableTestableWorld world, BlockPos pos) {
		if (!canGenerate(world, pos)) {
			TreeFeature.setBlockStateWithoutUpdatingNeighbors(world, pos, LintBlocks.RICH_DIRT.getDefaultState());
		}
	}

	protected TrunkPlacerType<?> getType() {
		return STRAIGHT_TRUNK_PLACER;
	}

	public List<FoliagePlacer.TreeNode> generate(ModifiableTestableWorld world, Random random, int trunkHeight, BlockPos pos, Set<BlockPos> placedStates, BlockBox box, TreeFeatureConfig config) {
		setToLintDirt(world, pos.down());

		for (int i = 0; i < trunkHeight; ++i) {
			getAndSetState(world, random, pos.up(i), placedStates, box, config);
		}

		return ImmutableList.of(new FoliagePlacer.TreeNode(pos.up(trunkHeight), 0, false));
	}
}
