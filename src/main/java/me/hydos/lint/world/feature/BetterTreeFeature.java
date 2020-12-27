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

import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import me.hydos.lint.block.LintBlocks;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Material;
import net.minecraft.state.property.Properties;
import net.minecraft.structure.Structure;
import net.minecraft.tag.BlockTags;
import net.minecraft.util.math.BlockBox;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.Vec3i;
import net.minecraft.util.shape.BitSetVoxelSet;
import net.minecraft.util.shape.VoxelSet;
import net.minecraft.world.*;
import net.minecraft.world.gen.chunk.ChunkGenerator;
import net.minecraft.world.gen.feature.Feature;
import net.minecraft.world.gen.feature.TreeFeatureConfig;
import net.minecraft.world.gen.feature.size.FeatureSize;
import net.minecraft.world.gen.foliage.FoliagePlacer;
import net.minecraft.world.gen.stateprovider.BlockStateProvider;
import net.minecraft.world.gen.tree.TreeDecorator;
import net.minecraft.world.gen.trunk.TrunkPlacer;

import java.util.*;

public class BetterTreeFeature extends Feature<TreeFeatureConfig> {

	public static final Codec<TreeFeatureConfig> CODEC = RecordCodecBuilder.create((instance) -> instance.group(BlockStateProvider.TYPE_CODEC
					.fieldOf("trunk_provider")
					.forGetter((treeFeatureConfig) -> treeFeatureConfig.trunkProvider),
			BlockStateProvider.TYPE_CODEC.fieldOf("leaves_provider").forGetter((treeFeatureConfig) -> treeFeatureConfig.leavesProvider),
			FoliagePlacer.TYPE_CODEC.fieldOf("foliage_placer").forGetter((treeFeatureConfig) -> treeFeatureConfig.foliagePlacer),
			TrunkPlacer.CODEC.fieldOf("trunk_placer").forGetter((treeFeatureConfig) -> treeFeatureConfig.trunkPlacer),
			FeatureSize.TYPE_CODEC.fieldOf("minimum_size").forGetter((treeFeatureConfig) -> treeFeatureConfig.minimumSize),
			TreeDecorator.TYPE_CODEC.listOf().fieldOf("decorators").forGetter((treeFeatureConfig) -> treeFeatureConfig.decorators),
			Codec.INT.fieldOf("max_water_depth").orElse(0).forGetter((treeFeatureConfig) -> treeFeatureConfig.maxWaterDepth),
			Codec.BOOL.fieldOf("ignore_vines").orElse(false).forGetter((treeFeatureConfig) -> treeFeatureConfig.ignoreVines),
			Heightmap.Type.CODEC.fieldOf("heightmap").forGetter((treeFeatureConfig) -> treeFeatureConfig.heightmap)).apply(instance, LintTreeFeatureConfig::new));

	public BetterTreeFeature(Codec<TreeFeatureConfig> codec) {
		super(codec);
	}

	public static boolean canTreeReplace(TestableWorld world, BlockPos pos) {
		return canReplace(world, pos) || world.testBlockState(pos, (state) -> state.isIn(BlockTags.LOGS));
	}

	private static boolean isVine(TestableWorld world, BlockPos pos) {
		return world.testBlockState(pos, (state) -> state.isOf(net.minecraft.block.Blocks.VINE));
	}

	private static boolean isWater(TestableWorld world, BlockPos pos) {
		return world.testBlockState(pos, (state) -> state.isOf(net.minecraft.block.Blocks.WATER));
	}

	public static boolean isAirOrLeaves(TestableWorld world, BlockPos pos) {
		return world.testBlockState(pos, (state) -> state.isAir() || state.isIn(BlockTags.LEAVES));
	}

	private static boolean isDirtOrGrass(TestableWorld world, BlockPos pos) {
		return world.testBlockState(pos, (state) -> {
			Block block = state.getBlock();
			return block == LintBlocks.CORRUPT_GRASS || block == LintBlocks.LIVELY_GRASS;
		});
	}

	private static boolean isReplaceablePlant(TestableWorld world, BlockPos pos) {
		return world.testBlockState(pos, (state) -> {
			Material material = state.getMaterial();
			return material == Material.REPLACEABLE_PLANT;
		});
	}

	public static void setBlockStateWithoutUpdatingNeighbors(ModifiableWorld world, BlockPos pos, BlockState state) {
		world.setBlockState(pos, state, 19);
	}

	public static boolean canReplace(TestableWorld world, BlockPos pos) {
		return isAirOrLeaves(world, pos) || isReplaceablePlant(world, pos) || isWater(world, pos);
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
				r = this.method_29963(world, trunkPlacerHeight, blockPos2, config);
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

	private int method_29963(TestableWorld testableWorld, int i, BlockPos blockPos, TreeFeatureConfig treeFeatureConfig) {
		BlockPos.Mutable mutable = new BlockPos.Mutable();

		for (int j = 0; j <= i + 1; ++j) {
			int k = treeFeatureConfig.minimumSize.method_27378(i, j);

			for (int l = -k; l <= k; ++l) {
				for (int m = -k; m <= k; ++m) {
					mutable.set(blockPos, l, j, m);
					if (!canTreeReplace(testableWorld, mutable) || !treeFeatureConfig.ignoreVines && isVine(testableWorld, mutable)) {
						return j - 2;
					}
				}
			}
		}

		return i;
	}

	protected void setBlockState(ModifiableWorld world, BlockPos pos, BlockState state) {
		setBlockStateWithoutUpdatingNeighbors(world, pos, state);
	}

	public final boolean generate(StructureWorldAccess structureWorldAccess, ChunkGenerator chunkGenerator, Random random, BlockPos blockPos, TreeFeatureConfig treeFeatureConfig) {
		Set<BlockPos> set = Sets.newHashSet();
		Set<BlockPos> set2 = Sets.newHashSet();
		Set<BlockPos> set3 = Sets.newHashSet();
		BlockBox blockBox = BlockBox.empty();
		boolean bl = this.generate(structureWorldAccess, random, blockPos, set, set2, blockBox, treeFeatureConfig);

		if (blockBox.minX <= blockBox.maxX && bl && !set.isEmpty()) {
			if (!treeFeatureConfig.decorators.isEmpty()) {
				List<BlockPos> list = Lists.newArrayList(set);
				List<BlockPos> list2 = Lists.newArrayList(set2);
				list.sort(Comparator.comparingInt(Vec3i::getY));
				list2.sort(Comparator.comparingInt(Vec3i::getY));
				treeFeatureConfig.decorators.forEach((decorator) -> decorator.generate(structureWorldAccess, random, list, list2, set3, blockBox));
			}

			VoxelSet voxelSet = this.placeLogsAndLeaves(structureWorldAccess, blockBox, set, set3);
			Structure.updateCorner(structureWorldAccess, 3, voxelSet, blockBox.minX, blockBox.minY, blockBox.minZ);
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
						setBlockStateWithoutUpdatingNeighbors(world, mutable, blockState.with(Properties.DISTANCE_1_7, 1));
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
								setBlockStateWithoutUpdatingNeighbors(world, mutable, blockState3);
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

	public static class LintTreeFeatureConfig extends TreeFeatureConfig {
		public LintTreeFeatureConfig(BlockStateProvider trunkProvider, BlockStateProvider leavesProvider, FoliagePlacer foliagePlacer, TrunkPlacer trunkPlacer, FeatureSize minimumSize, List<TreeDecorator> decorators, int maxWaterDepth, boolean ignoreVines, Heightmap.Type heightmap) {
			super(trunkProvider, leavesProvider, foliagePlacer, trunkPlacer, minimumSize, decorators, maxWaterDepth, ignoreVines, heightmap);
		}
	}
}