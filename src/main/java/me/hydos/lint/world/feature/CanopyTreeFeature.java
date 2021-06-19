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

import com.google.common.collect.Iterables;
import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import me.hydos.lint.block.DirtLikeBlock;
import me.hydos.lint.block.LintBlocks;
import me.hydos.lint.block.organic.DistantLeavesBlock;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.PillarBlock;
import net.minecraft.structure.Structure;
import net.minecraft.util.math.*;
import net.minecraft.util.shape.VoxelSet;
import net.minecraft.world.ModifiableTestableWorld;
import net.minecraft.world.StructureWorldAccess;
import net.minecraft.world.TestableWorld;
import net.minecraft.world.gen.feature.Feature;
import net.minecraft.world.gen.feature.TreeFeature;
import net.minecraft.world.gen.feature.TreeFeatureConfig;
import net.minecraft.world.gen.feature.util.FeatureContext;

import java.util.Comparator;
import java.util.List;
import java.util.Random;
import java.util.Set;
import java.util.function.BiConsumer;

// Should be default feature config bc I ignore every parameter but i have to use this
public class CanopyTreeFeature extends Feature<TreeFeatureConfig> {
	public CanopyTreeFeature() {
		super(TreeFeatureConfig.CODEC);
	}

	private int getTopPosition(TestableWorld world, int height, BlockPos pos, TreeFeatureConfig config) {
		BlockPos.Mutable mutable = new BlockPos.Mutable();

		for(int i = 0; i <= height + 1; ++i) {
			int j = config.minimumSize.getRadius(height, i);

			for(int k = -j; k <= j; ++k) {
				for(int l = -j; l <= j; ++l) {
					mutable.set(pos, k, i, l);
					if (!TreeFeature.canTreeReplace(world, mutable) || !config.ignoreVines && TreeFeature.isVine(world, mutable)) {
						return i - 2;
					}
				}
			}
		}

		return height;
	}

	private boolean generate(StructureWorldAccess world, Random random, BlockPos start, BiConsumer<BlockPos, BlockState> trunkReplacer, BiConsumer<BlockPos, BlockState> foliageReplacer, TreeFeatureConfig config) {
		// TODO
		// - taller
		// - cooler angles
		// - cool spread out shape at top
		// the leaves don't decay easily like they do
		// larger decay distance for leaves (I think terrestria does this already)
		// - switch to trunk placer and leaf placer that does the equivalent. Will need a custom config bc I use multiple leaf placement methods.

		int startX = start.getX();
		int startY = start.getY();
		int startZ = start.getZ();
		BlockPos.Mutable pos = new BlockPos.Mutable().set(start);

		if (DirtLikeBlock.isUntaintedGrass(world.getBlockState(start.down()))) {
			int trunkHeight = 12 + random.nextInt(15);
			int trueHeight = trunkHeight + 3; // 3 blocks above trunk height

			if (startY + trueHeight < world.getHeight()) {
				for (int y = 0; y < trunkHeight; ++y) {
					pos.setY(startY + y);

					if (!TreeFeature.canTreeReplace(world, pos)) {
						return false;
					}
				}

				// 1. Canopy Leaves
				for (int dy = -5; dy < 0; ++dy) {
					float dymod = dy + 3;
					float r = dy == 0 ? 1.01f : 4 - 0.5f * dymod * (1 + 0.1f * dymod * dymod); // radius
					int max = MathHelper.ceil(r);
					pos.setY(startY + trueHeight + dy);

					for (int dx = -max; dx <= max; ++dx) {
						for (int dz = -max; dz <= max; ++dz) {
							if (dx * dx + dz * dz <= r * r) {
								pos.setX(startX + dx);
								pos.setZ(startZ + dz);

								this.setLeaves(world, pos);
							}
						}
					}
				}

				// 2. Branches
				int dy = 3;

				while (++dy < trunkHeight - 2) {
					if (random.nextInt(4) == 0) {
						Direction dir = Direction.fromHorizontal(random.nextInt(4));
						// todo secondary direction 1/3 of the time

						int length = 3 + random.nextInt(3);
						pos.setY(startY + dy);
						BlockState logState = LOG.with(PillarBlock.AXIS, dir.getAxis());

						for (int dist = 0; dist < length; ++dist) {
							pos.setX(dist * dir.getOffsetX() + startX);
							pos.setZ(dist * dir.getOffsetZ() + startZ);

							if (TreeFeature.canTreeReplace(world, pos)) {
								this.setBlockState(world, pos, logState);
							}
						}

						// Leaves Blob Node
						int leavesBlobX = startX + (length - 1) * dir.getOffsetX();
						int leavesBlobZ = startZ + (length - 1) * dir.getOffsetZ();

						for (int ddy = -2; ddy <= 3; ++ddy) {
							pos.setY(startY + dy + ddy);

							for (int ddx = -2; ddx <= 3; ++ddx) {
								pos.setX(leavesBlobX + ddx);

								for (int ddz = -2; ddz <= 3; ++ddz) {
									pos.setZ(leavesBlobZ + ddz);

									if (ddy * ddy + ddx * ddx + ddz * ddz < 3) {
										this.setLeaves(world, pos);
									}
								}
							}
						}
					}
				}

				pos.setX(startX);
				pos.setZ(startZ);

				// 3. Trunk
				for (dy = 0; dy < trunkHeight; ++dy) {
					pos.setY(startY + dy);

					if (TreeFeature.canTreeReplace(world, pos)) {
						this.setBlockState(world, pos, LOG);
					}
				}

				// Cross at top TODO probably change into branching out

				pos.setY(startY + trunkHeight - 1);
				pos.setX(startX + 1);

				if (TreeFeature.canTreeReplace(world, pos)) {
					this.setBlockState(world, pos, LOG);
				}

				pos.setX(startX - 1);

				if (TreeFeature.canTreeReplace(world, pos)) {
					this.setBlockState(world, pos, LOG);
				}

				pos.setX(startX);
				pos.setZ(startZ + 1);

				if (TreeFeature.canTreeReplace(world, pos)) {
					this.setBlockState(world, pos, LOG);
				}

				pos.setZ(startZ - 1);

				if (TreeFeature.canTreeReplace(world, pos)) {
					this.setBlockState(world, pos, LOG);
				}

				return true;
			}
		}

		return false;
	}

	// hydos why don't you just extend TreeFeature
	@Override
	public final boolean generate(FeatureContext<TreeFeatureConfig> context) {
		StructureWorldAccess structureWorldAccess = context.getWorld();
		Random random = context.getRandom();
		BlockPos blockPos = context.getOrigin();
		TreeFeatureConfig treeFeatureConfig = (TreeFeatureConfig)context.getConfig();
		Set<BlockPos> set = Sets.newHashSet();
		Set<BlockPos> set2 = Sets.newHashSet();
		Set<BlockPos> set3 = Sets.newHashSet();
		BiConsumer<BlockPos, BlockState> biConsumer = (pos, state) -> {
			set.add(pos.toImmutable());
			structureWorldAccess.setBlockState(pos, state, Block.NOTIFY_ALL | Block.FORCE_STATE);
		};
		BiConsumer<BlockPos, BlockState> biConsumer2 = (pos, state) -> {
			set2.add(pos.toImmutable());
			structureWorldAccess.setBlockState(pos, state, Block.NOTIFY_ALL | Block.FORCE_STATE);
		};
		BiConsumer<BlockPos, BlockState> biConsumer3 = (pos, state) -> {
			set3.add(pos.toImmutable());
			structureWorldAccess.setBlockState(pos, state, Block.NOTIFY_ALL | Block.FORCE_STATE);
		};
		boolean bl = this.generate(structureWorldAccess, random, blockPos, biConsumer, biConsumer2, treeFeatureConfig);
		if (bl && (!set.isEmpty() || !set2.isEmpty())) {
			if (!treeFeatureConfig.decorators.isEmpty()) {
				List<BlockPos> list = Lists.newArrayList(set);
				List<BlockPos> list2 = Lists.newArrayList(set2);
				list.sort(Comparator.comparingInt(Vec3i::getY));
				list2.sort(Comparator.comparingInt(Vec3i::getY));
				treeFeatureConfig.decorators.forEach((treeDecorator) -> {
					treeDecorator.generate(structureWorldAccess, biConsumer3, random, list, list2);
				});
			}

			return BlockBox.encompassPositions(Iterables.concat(set, set2, set3)).map((box) -> {
				VoxelSet voxelSet = TreeFeature.placeLogsAndLeaves(structureWorldAccess, box, set, set3);
				Structure.updateCorner(structureWorldAccess, Block.NOTIFY_ALL, voxelSet, box.getMinX(), box.getMinY(), box.getMinZ());
				return true;
			}).orElse(false);
		} else {
			return false;
		}
	}

	private void setLeaves(ModifiableTestableWorld world, BlockPos pos) {
		if (TreeFeature.canReplace(world, pos)) {
			world.setBlockState(pos, LEAVES, 19);
		}
	}

	public static final BlockState LEAVES = LintBlocks.CANOPY_LEAVES.getDefaultState().with(DistantLeavesBlock.distance(LintBlocks.CANOPY_LEAVES), 1);
	public static final BlockState LOG = LintBlocks.MYSTICAL_LOG.getDefaultState();
}