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

import java.util.Random;

import me.hydos.lint.block.DirtLikeBlock;
import me.hydos.lint.block.LintBlocks;
import net.minecraft.block.BlockState;
import net.minecraft.block.LeavesBlock;
import net.minecraft.block.PillarBlock;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.ModifiableTestableWorld;
import net.minecraft.world.StructureWorldAccess;
import net.minecraft.world.gen.chunk.ChunkGenerator;
import net.minecraft.world.gen.feature.Feature;
import net.minecraft.world.gen.feature.TreeFeature;
import net.minecraft.world.gen.feature.TreeFeatureConfig;

// Should be default feature config bc I ignore every parameter but i have to use this
public class CanopyTreeFeature extends Feature<TreeFeatureConfig> {
	public CanopyTreeFeature() {
		super(TreeFeatureConfig.CODEC);
	}

	@Override
	public boolean generate(StructureWorldAccess world, ChunkGenerator chunkGenerator, Random random, BlockPos start, TreeFeatureConfig config) {
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
				for (int dy = -4; dy < 0; ++dy) {
					float dymod = dy + 3;
					float r = 4 - 0.5f * dymod * (1 + 0.1f * dymod * dymod); // radius
					int max = MathHelper.ceil(r);
					pos.setY(startY + trueHeight + dy);

					for (int dx = -max; dx <= max; ++dx) {
						for (int dz = -max; dz <= max; ++dz) {
							if (dx * dx + dz * dz <= r) {
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

				pos.setY(trunkHeight - 1);
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

	private void setLeaves(ModifiableTestableWorld world, BlockPos pos) {
		if (TreeFeature.canReplace(world, pos)) {
			world.setBlockState(pos, LEAVES, 19);
		}
	}

	public static final BlockState LEAVES = LintBlocks.CANOPY_LEAVES.getDefaultState().with(LeavesBlock.DISTANCE, 1);
	public static final BlockState LOG = LintBlocks.MYSTICAL_LOG.getDefaultState();
}
