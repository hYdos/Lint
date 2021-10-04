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

package me.hydos.lint.world.feature.trunkplacer;

import java.util.List;
import java.util.Random;
import java.util.function.BiConsumer;

import com.google.common.collect.Lists;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;

import me.hydos.lint.Lint;
import net.minecraft.block.BlockState;
import net.minecraft.util.dynamic.Codecs;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.intprovider.IntProvider;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.TestableWorld;
import net.minecraft.world.gen.feature.TreeFeature;
import net.minecraft.world.gen.feature.TreeFeatureConfig;
import net.minecraft.world.gen.foliage.FoliagePlacer;
import net.minecraft.world.gen.foliage.FoliagePlacer.TreeNode;
import net.minecraft.world.gen.trunk.BendingTrunkPlacer;
import net.minecraft.world.gen.trunk.TrunkPlacerType;

public class LintBendingTrunkPlacer extends BendingTrunkPlacer {
	public static final Codec<LintBendingTrunkPlacer> CODEC = RecordCodecBuilder.create(instance -> {
		return fillTrunkPlacerFields(instance)
				.and(instance.group(
						Codecs.POSITIVE_INT.optionalFieldOf("min_height_for_leaves", 1).forGetter(placer -> placer.minHeightForLeaves),
						IntProvider.createValidatingCodec(1, 64).fieldOf("bend_length").forGetter(placer -> placer.bendLength)
						)).apply(instance, LintBendingTrunkPlacer::new);
	});

	public static final TrunkPlacerType<LintBendingTrunkPlacer> BENDING_TRUNK_PLACER = Registry.register(Registry.TRUNK_PLACER_TYPE, Lint.id("bending_trunk_placer"), new TrunkPlacerType<>(CODEC));

	public LintBendingTrunkPlacer(int baseHeight, int firstRandomHeight, int secondRandomHeight, int minHeightForLeaves, IntProvider bendLength) {
		super(baseHeight, firstRandomHeight, secondRandomHeight, minHeightForLeaves, bendLength);

		this.minHeightForLeaves = minHeightForLeaves;
		this.bendLength = bendLength;
	}

	private final int minHeightForLeaves;
	private final IntProvider bendLength;

	@Override
	public List<TreeNode> generate(TestableWorld world, BiConsumer<BlockPos, BlockState> replacer, Random random,
			int height, BlockPos startPos, TreeFeatureConfig config) {
		Direction direction = Direction.Type.HORIZONTAL.random(random);
		int i = height - 1;
		BlockPos.Mutable mutable = startPos.mutableCopy();

		// DONT SET TO DIRT
		// BlockPos blockPos = mutable.down();
		// setToDirt(world, replacer, random, blockPos, config);

		List<FoliagePlacer.TreeNode> list = Lists.newArrayList();

		int j;
		for(j = 0; j <= i; ++j) {
			if (j + 1 >= i + random.nextInt(2)) {
				mutable.move(direction);
			}

			if (TreeFeature.canReplace(world, mutable)) {
				getAndSetState(world, replacer, random, mutable, config);
			}

			if (j >= this.minHeightForLeaves) {
				list.add(new FoliagePlacer.TreeNode(mutable.toImmutable(), 0, false));
			}

			mutable.move(Direction.UP);
		}

		j = this.bendLength.get(random);

		for(int l = 0; l <= j; ++l) {
			if (TreeFeature.canReplace(world, mutable)) {
				getAndSetState(world, replacer, random, mutable, config);
			}

			list.add(new FoliagePlacer.TreeNode(mutable.toImmutable(), 0, false));
			mutable.move(direction);
		}

		return list;
	}

	@Override
	protected TrunkPlacerType<?> getType() {
		return BENDING_TRUNK_PLACER;
	}
}
