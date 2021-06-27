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

import com.google.common.collect.ImmutableList;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import me.hydos.lint.Lint;
import me.hydos.lint.block.LintBlocks;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
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

import java.util.List;
import java.util.Random;
import java.util.function.BiConsumer;

public class LintTrunkPlacer extends TrunkPlacer {

	public static final Codec<LintTrunkPlacer> CODEC = RecordCodecBuilder.create(instance -> fillTrunkPlacerFields(instance)
			.apply(instance, LintTrunkPlacer::new));

	public static final TrunkPlacerType<LintTrunkPlacer> STRAIGHT_TRUNK_PLACER = Registry.register(Registry.TRUNK_PLACER_TYPE, Lint.id("trunk_placer"), new TrunkPlacerType<>(CODEC));

	public LintTrunkPlacer(int baseHeight, int firstRandomHeight, int secondRandomHeight) {
		super(baseHeight, firstRandomHeight, secondRandomHeight);
	}

	private static boolean canGenerate(TestableWorld world, BlockPos pos) {
		return world.testBlockState(pos, (state) -> {
			Block block = state.getBlock(); // wtf is this not checking for lint blocks i mean it does't seem to be changing anyhting so ok
			return Feature.isSoil(block.getDefaultState()) && !state.isOf(Blocks.GRASS_BLOCK) && !state.isOf(Blocks.MYCELIUM);
		});
	}

	protected static void setToLintDirt(ModifiableTestableWorld world, BlockPos pos) {
		if (!canGenerate(world, pos)) { // if cannot generate
			TreeFeature.setBlockStateWithoutUpdatingNeighbors(world, pos, LintBlocks.RICH_DIRT.getDefaultState());
		}
	}

	protected TrunkPlacerType<?> getType() {
		return STRAIGHT_TRUNK_PLACER;
	}
	
	@Override
	public List<FoliagePlacer.TreeNode> generate(TestableWorld world, BiConsumer<BlockPos, BlockState> replacer, Random random, int height, BlockPos startPos, TreeFeatureConfig config) {
		setToLintDirt((ModifiableTestableWorld) world, startPos.down());

		for (int i = 0; i < height; ++i) {
			getAndSetState(world, replacer, random, startPos.up(i), config);
		}

		return ImmutableList.of(new FoliagePlacer.TreeNode(startPos.up(height), 0, false));
	}
}
