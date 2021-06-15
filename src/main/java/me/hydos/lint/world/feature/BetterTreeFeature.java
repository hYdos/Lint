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

import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import java.util.OptionalInt;
import java.util.Random;
import java.util.Set;
import java.util.function.BiConsumer;

import com.google.common.collect.Iterables;
import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import com.mojang.serialization.Codec;

import me.hydos.lint.block.DirtLikeBlock;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.state.property.Properties;
import net.minecraft.structure.Structure;
import net.minecraft.util.math.BlockBox;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.Vec3i;
import net.minecraft.util.shape.BitSetVoxelSet;
import net.minecraft.util.shape.VoxelSet;
import net.minecraft.world.ModifiableWorld;
import net.minecraft.world.StructureWorldAccess;
import net.minecraft.world.TestableWorld;
import net.minecraft.world.WorldAccess;
import net.minecraft.world.gen.feature.Feature;
import net.minecraft.world.gen.feature.TreeFeature;
import net.minecraft.world.gen.feature.TreeFeatureConfig;
import net.minecraft.world.gen.feature.size.FeatureSize;
import net.minecraft.world.gen.feature.util.FeatureContext;
import net.minecraft.world.gen.foliage.FoliagePlacer;
import net.minecraft.world.gen.stateprovider.BlockStateProvider;
import net.minecraft.world.gen.treedecorator.TreeDecorator;
import net.minecraft.world.gen.trunk.TrunkPlacer;

// reason: bad minecraft hardcoding for a few things
public class BetterTreeFeature extends Feature<TreeFeatureConfig> {

	public BetterTreeFeature(Codec<TreeFeatureConfig> codec) {
		super(codec);
	}

	private static boolean isDirtOrGrass(TestableWorld world, BlockPos pos) {
		return world.testBlockState(pos, DirtLikeBlock::isLintGrass);
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
	
	private int getTopPosition(TestableWorld world, int height, BlockPos pos, TreeFeatureConfig config) {
		BlockPos.Mutable mutable = new BlockPos.Mutable();
		
		for(int i = 0; i <= height + 1; ++i) {
			int j = config.minimumSize.getRadius(height, i);
			
			for(int k = -j; k <= j; ++k) {
				for(int l = -j; l <= j; ++l) {
					mutable.set((Vec3i)pos, k, i, l);
					if (!TreeFeature.canTreeReplace(world, mutable) || !config.ignoreVines && TreeFeature.isVine(world, mutable)) {
						return i - 2;
					}
				}
			}
		}
		
		return height;
	}

	protected void setBlockState(ModifiableWorld world, BlockPos pos, BlockState state) {
		TreeFeature.setBlockStateWithoutUpdatingNeighbors(world, pos, state);
	}
	
	@Override
	public final boolean generate(FeatureContext<TreeFeatureConfig> context) {
		StructureWorldAccess structureWorldAccess = context.getWorld();
		Random random = context.getRandom();
		BlockPos blockPos = context.getOrigin();
		TreeFeatureConfig treeFeatureConfig = context.getConfig();
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

	public static class LintTreeFeatureConfig extends TreeFeatureConfig {
		protected LintTreeFeatureConfig(BlockStateProvider trunkProvider, TrunkPlacer trunkPlacer, BlockStateProvider foliageProvider, BlockStateProvider saplingProvider, FoliagePlacer foliagePlacer, BlockStateProvider dirtProvider, FeatureSize minimumSize, List<TreeDecorator> decorators, boolean ignoreVines, boolean forceDirt) {
			super(trunkProvider, trunkPlacer, foliageProvider, saplingProvider, foliagePlacer, dirtProvider, minimumSize, decorators, ignoreVines, forceDirt);
		}
	}
}