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

package me.hydos.lint.world.feature.modifier;

import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import java.util.OptionalInt;
import java.util.Random;
import java.util.Set;

import com.google.common.collect.Lists;
import com.google.common.collect.Sets;

import me.hydos.lint.block.DirtLikeBlock;
import me.hydos.lint.world.feature.util.WorldModifier;
import net.minecraft.block.BlockState;
import net.minecraft.state.property.Properties;
import net.minecraft.structure.Structure;
import net.minecraft.util.math.BlockBox;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.Vec3i;
import net.minecraft.util.shape.BitSetVoxelSet;
import net.minecraft.util.shape.VoxelSet;
import net.minecraft.world.Heightmap;
import net.minecraft.world.ModifiableTestableWorld;
import net.minecraft.world.ModifiableWorld;
import net.minecraft.world.TestableWorld;
import net.minecraft.world.WorldAccess;
import net.minecraft.world.gen.feature.TreeFeature;
import net.minecraft.world.gen.feature.TreeFeatureConfig;
import net.minecraft.world.gen.foliage.FoliagePlacer;

// reason: bad minecraft hardcoding for a few things
public enum Tree implements WorldModifier<TreeFeatureConfig> {
	WORLD_MODIFIER;

	private static boolean isDirtOrGrass(TestableWorld world, BlockPos pos) {
		return world.testBlockState(pos, DirtLikeBlock::isLintGrass);
	}

	private boolean generate(ModifiableTestableWorld world, Random random, BlockPos pos, Set<BlockPos> logPositions, Set<BlockPos> leavesPositions, BlockBox box, TreeFeatureConfig config) {
		pos = new BlockPos(((pos.getX() >> 4) << 4) + random.nextInt(16), pos.getY(), ((pos.getZ() >> 4) << 4) + random.nextInt(16));
		int trunkPlacerHeight = config.trunkPlacer.getHeight(random);
		int j = config.foliagePlacer.getRandomHeight(random, trunkPlacerHeight, config);
		int k = trunkPlacerHeight - j;
		int l = config.foliagePlacer.getRandomRadius(random, k);
		BlockPos blockPos2;
		int r;

		if (!config.skipFluidCheck) {
			int m = world.getTopPosition(Heightmap.Type.OCEAN_FLOOR, pos).getY();
			r = world.getTopPosition(Heightmap.Type.WORLD_SURFACE, pos).getY();
			if (r - m > config.maxWaterDepth) {
				return false;
			}

			int q;
			if (config.heightmap == Heightmap.Type.OCEAN_FLOOR) {
				q = m;
			} else if (config.heightmap == Heightmap.Type.WORLD_SURFACE) {
				q = r;
			} else {
				q = world.getTopPosition(config.heightmap, pos).getY();
			}

			blockPos2 = new BlockPos(pos.getX(), q, pos.getZ());
		} else {
			blockPos2 = pos;
		}

		if (blockPos2.getY() >= 1 && blockPos2.getY() + trunkPlacerHeight + 1 <= 256) {
			if (!isDirtOrGrass(world, blockPos2.down())) {
				return false;
			} else {
				OptionalInt optionalInt = config.minimumSize.getMinClippedHeight();
				r = this.computeR(world, trunkPlacerHeight, blockPos2, config);
				if (r >= trunkPlacerHeight || optionalInt.isPresent() && r >= optionalInt.getAsInt()) {
					List<FoliagePlacer.TreeNode> list = config.trunkPlacer.generate(world, random, r, blockPos2, logPositions, box, config);
					int finalR = r;
					list.forEach((treeNode) -> config.foliagePlacer.generate(world, random, config, finalR, treeNode, j, l, leavesPositions, box));
					return true;
				} else {
					return false;
				}
			}
		} else {
			return false;
		}
	}

	private int computeR(TestableWorld testableWorld, int i, BlockPos blockPos, TreeFeatureConfig treeFeatureConfig) {
		BlockPos.Mutable mutable = new BlockPos.Mutable();

		for (int j = 0; j <= i + 1; ++j) {
			int k = treeFeatureConfig.minimumSize.method_27378(i, j);

			for (int l = -k; l <= k; ++l) {
				for (int m = -k; m <= k; ++m) {
					mutable.set(blockPos, l, j, m);
					if (!TreeFeature.canTreeReplace(testableWorld, mutable) || !treeFeatureConfig.ignoreVines && TreeFeature.isVine(testableWorld, mutable)) {
						return j - 2;
					}
				}
			}
		}

		return i;
	}

	protected void setBlockState(ModifiableWorld world, BlockPos pos, BlockState state) {
		TreeFeature.setBlockStateWithoutUpdatingNeighbors(world, pos, state);
	}

	@Override
	public final boolean place(GenerationSettings<TreeFeatureConfig> settings) {
		Set<BlockPos> set = Sets.newHashSet();
		Set<BlockPos> set2 = Sets.newHashSet();
		Set<BlockPos> set3 = Sets.newHashSet();
		BlockBox blockBox = BlockBox.empty();
		boolean bl = this.generate(settings.world, settings.random, settings.origin, set, set2, blockBox, settings.config);

		if (blockBox.minX <= blockBox.maxX && bl && !set.isEmpty()) {
			if (!settings.config.decorators.isEmpty()) {
				List<BlockPos> list = Lists.newArrayList(set);
				List<BlockPos> list2 = Lists.newArrayList(set2);
				list.sort(Comparator.comparingInt(Vec3i::getY));
				list2.sort(Comparator.comparingInt(Vec3i::getY));
				settings.config.decorators.forEach((decorator) -> decorator.generate(settings.world, settings.random, list, list2, set3, blockBox));
			}

			VoxelSet voxelSet = this.placeLogsAndLeaves(settings.world, blockBox, set, set3);
			Structure.updateCorner(settings.world, 3, voxelSet, blockBox.minX, blockBox.minY, blockBox.minZ);
			return true;
		} else {
			return false;
		}
	}

	private VoxelSet placeLogsAndLeaves(WorldAccess world, BlockBox box, Set<BlockPos> logs, Set<BlockPos> leaves) {
		List<Set<BlockPos>> list = Lists.newArrayList();
		VoxelSet voxelSet = new BitSetVoxelSet(box.getBlockCountX(), box.getBlockCountY(), box.getBlockCountZ());

		for (int j = 0; j < 6; ++j) {
			list.add(Sets.newHashSet());
		}

		BlockPos.Mutable mutable = new BlockPos.Mutable();
		Iterator<BlockPos> var9 = Lists.newArrayList(leaves).iterator();

		BlockPos blockPos2;

		while (var9.hasNext()) {
			blockPos2 = var9.next();
			if (box.contains(blockPos2)) {
				voxelSet.set(blockPos2.getX() - box.minX, blockPos2.getY() - box.minY, blockPos2.getZ() - box.minZ, true, true);
			}
		}

		var9 = Lists.newArrayList(logs).iterator();

		while (var9.hasNext()) {
			blockPos2 = var9.next();
			if (box.contains(blockPos2)) {
				voxelSet.set(blockPos2.getX() - box.minX, blockPos2.getY() - box.minY, blockPos2.getZ() - box.minZ, true, true);
			}

			Direction[] var11 = Direction.values();
			for (Direction direction : var11) {
				mutable.set(blockPos2, direction);
				if (!logs.contains(mutable)) {
					BlockState blockState = world.getBlockState(mutable);
					if (blockState.contains(Properties.DISTANCE_1_7)) {
						list.get(0).add(mutable.toImmutable());
						TreeFeature.setBlockStateWithoutUpdatingNeighbors(world, mutable, blockState.with(Properties.DISTANCE_1_7, 1));
						if (box.contains(mutable)) {
							voxelSet.set(mutable.getX() - box.minX, mutable.getY() - box.minY, mutable.getZ() - box.minZ, true, true);
						}
					}
				}
			}
		}

		for (int k = 1; k < 6; ++k) {
			Set<BlockPos> set = list.get(k - 1);
			Set<BlockPos> set2 = list.get(k);

			for (BlockPos o : set) {
				if (box.contains(o)) {
					voxelSet.set(o.getX() - box.minX, o.getY() - box.minY, o.getZ() - box.minZ, true, true);
				}

				Direction[] var27 = Direction.values();

				for (Direction direction2 : var27) {
					mutable.set(o, direction2);
					if (!set.contains(mutable) && !set2.contains(mutable)) {
						BlockState blockState2 = world.getBlockState(mutable);
						if (blockState2.contains(Properties.DISTANCE_1_7)) {
							int l = blockState2.get(Properties.DISTANCE_1_7);
							if (l > k + 1) {
								BlockState blockState3 = blockState2.with(Properties.DISTANCE_1_7, k + 1);
								TreeFeature.setBlockStateWithoutUpdatingNeighbors(world, mutable, blockState3);
								if (box.contains(mutable)) {
									voxelSet.set(mutable.getX() - box.minX, mutable.getY() - box.minY, mutable.getZ() - box.minZ, true, true);
								}

								set2.add(mutable.toImmutable());
							}
						}
					}
				}
			}
		}
		return voxelSet;
	}
	
	@Override
	public String id() {
		return "tree";
	}
}