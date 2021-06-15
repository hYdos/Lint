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

import java.util.*;
import java.util.function.BiConsumer;

import com.google.common.collect.Iterables;
import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import me.hydos.lint.block.DirtLikeBlock;
import me.hydos.lint.block.LintBlocks;
import me.hydos.lint.block.LintBlocksOld;
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
import net.minecraft.world.gen.chunk.ChunkGenerator;
import net.minecraft.world.gen.feature.Feature;
import net.minecraft.world.gen.feature.TreeFeature;
import net.minecraft.world.gen.feature.TreeFeatureConfig;
import net.minecraft.world.gen.feature.util.FeatureContext;
import net.minecraft.world.gen.foliage.FoliagePlacer;

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
	
	private boolean generate(StructureWorldAccess world, Random random, BlockPos pos, BiConsumer<BlockPos, BlockState> trunkReplacer, BiConsumer<BlockPos, BlockState> foliageReplacer, TreeFeatureConfig config) {
		int i = config.trunkPlacer.getHeight(random);
		int j = config.foliagePlacer.getRandomHeight(random, i, config);
		int k = i - j;
		int l = config.foliagePlacer.getRandomRadius(random, k);
		if (pos.getY() >= world.getBottomY() + 1 && pos.getY() + i + 1 <= world.getTopY()) {
			if (!config.saplingProvider.getBlockState(random, pos).canPlaceAt(world, pos)) {
				return false;
			} else {
				OptionalInt optionalInt = config.minimumSize.getMinClippedHeight();
				int m = this.getTopPosition(world, i, pos, config);
				if (m >= i || optionalInt.isPresent() && m >= optionalInt.getAsInt()) {
					List<FoliagePlacer.TreeNode> list = config.trunkPlacer.generate(world, trunkReplacer, random, m, pos, config);
					list.forEach((treeNode) -> {
						config.foliagePlacer.generate(world, foliageReplacer, random, config, m, treeNode, j, l);
					});
					return true;
				} else {
					return false;
				}
			}
		} else {
			return false;
		}
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
