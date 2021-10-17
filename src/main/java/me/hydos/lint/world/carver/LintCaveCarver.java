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

package me.hydos.lint.world.carver;

import com.mojang.serialization.Codec;
import me.hydos.lint.Lint;
import me.hydos.lint.tag.block.LintBlockTags;
import net.minecraft.block.BlockState;
import net.minecraft.tag.FluidTags;
import net.minecraft.util.math.*;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.chunk.Chunk;
import net.minecraft.world.gen.carver.Carver;
import net.minecraft.world.gen.carver.CarverContext;
import net.minecraft.world.gen.carver.CaveCarver;
import net.minecraft.world.gen.chunk.AquiferSampler;
import org.apache.commons.lang3.mutable.MutableBoolean;

import java.util.BitSet;
import java.util.Random;
import java.util.function.Function;

public class LintCaveCarver extends Carver<LintCaveCarverConfig> {
	public static final LintCaveCarver CAVE = Registry.register(Registry.CARVER, Lint.id("cave"), new LintCaveCarver(LintCaveCarverConfig.CAVE_CODEC));

	public LintCaveCarver(Codec<LintCaveCarverConfig> codec) {
		super(codec);
	}

	protected int getMaxCaveCount() {
		return 15;
	}

	protected void carveCave(CarverContext context, LintCaveCarverConfig config, Chunk chunk, Function<BlockPos, Biome> posToBiome, long seed, AquiferSampler aquiferSampler, double x, double y, double z, float yaw, double yawPitchRatio, BitSet carvingMask, Carver.SkipPredicate skipPredicate) {
		double d = 1.5D + (double) (MathHelper.sin(1.5707964F) * yaw);
		double e = d * yawPitchRatio;
		this.carveRegion(context, config, chunk, posToBiome, seed, aquiferSampler, x + 1.0D, y, z, d, e, carvingMask, skipPredicate);
	}

	protected void carveTunnels(CarverContext context, LintCaveCarverConfig config, Chunk chunk, Function<BlockPos, Biome> posToBiome, long seed, AquiferSampler aquiferSampler, double x, double y, double z, double horizontalScale, double verticalScale, float width, float yaw, float pitch, int branchStartIndex, int branchCount, double yawPitchRatio, BitSet carvingMask, Carver.SkipPredicate skipPredicate) {
		Random random = new Random(seed);
		int i = random.nextInt(branchCount / 2) + branchCount / 4;
		boolean bl = random.nextInt(6) == 0;
		float f = 0.0F;
		float g = 0.0F;

		for (int j = branchStartIndex; j < branchCount; ++j) {
			double d = 1.5D + (double) (MathHelper.sin(3.1415927F * (float) j / (float) branchCount) * width);
			double e = d * yawPitchRatio;
			float h = MathHelper.cos(pitch);
			x += MathHelper.cos(yaw) * h;
			y += MathHelper.sin(pitch);
			z += MathHelper.sin(yaw) * h;
			pitch *= bl ? 0.92F : 0.7F;
			pitch += g * 0.1F;
			yaw += f * 0.1F;
			g *= 0.9F;
			f *= 0.75F;
			g += (random.nextFloat() - random.nextFloat()) * random.nextFloat() * 2.0F;
			f += (random.nextFloat() - random.nextFloat()) * random.nextFloat() * 4.0F;
			if (j == i && width > 1.0F) {
				this.carveTunnels(context, config, chunk, posToBiome, random.nextLong(), aquiferSampler, x, y, z, horizontalScale, verticalScale, random.nextFloat() * 0.5F + 0.5F, yaw - 1.5707964F, pitch / 3.0F, j, branchCount, 1.0D, carvingMask, skipPredicate);
				this.carveTunnels(context, config, chunk, posToBiome, random.nextLong(), aquiferSampler, x, y, z, horizontalScale, verticalScale, random.nextFloat() * 0.5F + 0.5F, yaw + 1.5707964F, pitch / 3.0F, j, branchCount, 1.0D, carvingMask, skipPredicate);
				return;
			}

			if (random.nextInt(4) != 0) {
				if (!canCarveBranch(chunk.getPos(), x, z, j, branchCount, width)) {
					return;
				}

				this.carveRegion(context, config, chunk, posToBiome, seed, aquiferSampler, x, y, z, d * horizontalScale, e * verticalScale, carvingMask, skipPredicate);
			}
		}
	}

	protected double getTunnelSystemHeightWidthRatio() {
		return 1.0D;
	}

	@Override
	public boolean carve(CarverContext carverContext, LintCaveCarverConfig caveCarverConfig, Chunk chunk, Function<BlockPos, Biome> function, Random random, AquiferSampler aquiferSampler, ChunkPos chunkPos, BitSet bitSet) {
		int i = ChunkSectionPos.getBlockCoord(this.getBranchFactor() * 2 - 1);
		int j = random.nextInt(random.nextInt(random.nextInt(this.getMaxCaveCount()) + 1) + 1);

		for (int k = 0; k < j; ++k) {
			double d = chunkPos.getOffsetX(random.nextInt(16));
			double e = caveCarverConfig.y.get(random, carverContext);
			double f = chunkPos.getOffsetZ(random.nextInt(16));
			double g = caveCarverConfig.horizontalRadiusMultiplier.get(random);
			double h = caveCarverConfig.verticalRadiusMultiplier.get(random);
			double l = caveCarverConfig.floorLevel.get(random);
			Carver.SkipPredicate skipPredicate = (context, scaledRelativeX, scaledRelativeY, scaledRelativeZ, y) -> CaveCarver.isPositionExcluded(scaledRelativeX, scaledRelativeY, scaledRelativeZ, l);
			int m = 1;
			float r;
			if (random.nextInt(4) == 0) {
				double n = caveCarverConfig.yScale.get(random);
				r = 1.0F + random.nextFloat() * 6.0F;
				this.carveCave(carverContext, caveCarverConfig, chunk, function, random.nextLong(), aquiferSampler, d, e, f, r, n, bitSet, skipPredicate);
				m += random.nextInt(4);
			}

			for (int p = 0; p < m; ++p) {
				float q = random.nextFloat() * 6.2831855F;
				r = (random.nextFloat() - 0.5F) / 4.0F;
				float s = this.getTunnelSystemWidth(random);
				int t = i - random.nextInt(i / 4);
				this.carveTunnels(carverContext, caveCarverConfig, chunk, function, random.nextLong(), aquiferSampler, d, e, f, g, h, s, q, r, 0, t, this.getTunnelSystemHeightWidthRatio(), bitSet, skipPredicate);
			}
		}

		return true;
	}

	@Override
	public boolean shouldCarve(LintCaveCarverConfig config, Random random) {
		return random.nextFloat() <= config.probability;
	}

	@Override
	protected boolean canCarveBlock(BlockState state, BlockState stateAbove) {
		return (state.isIn(LintBlockTags.STONE) || state.isIn(LintBlockTags.DIRT) || state.isIn(LintBlockTags.GRASS)) && !stateAbove.getFluidState().isIn(FluidTags.WATER);
	}

	protected float getTunnelSystemWidth(Random random) {
		return 7.0f + random.nextFloat() + random.nextFloat();
	}

	@Override
	protected boolean carveAtPoint(CarverContext context, LintCaveCarverConfig config, Chunk chunk, Function<BlockPos, Biome> posToBiome, BitSet carvingMask, Random random, BlockPos.Mutable pos, BlockPos.Mutable downPos, AquiferSampler sampler, MutableBoolean foundSurface) {
		BlockState state = chunk.getBlockState(pos);
		BlockState state2 = chunk.getBlockState(downPos.set(pos, Direction.UP));
		if (state.isIn(LintBlockTags.GRASS)) {
			foundSurface.setTrue();
		}

		if (!this.canCarveBlock(state, state2) && !isDebug(config)) {
			return false;
		} else {
			BlockState state3 = this.getState(context, config, pos, sampler);
			if (state3 == null) {
				return false;
			} else {
				chunk.setBlockState(pos, state3, false);
				if (foundSurface.isTrue()) {
					downPos.set(pos, Direction.DOWN);
					if (chunk.getBlockState(downPos).isIn(LintBlockTags.DIRT)) {
						chunk.setBlockState(downPos, posToBiome.apply(pos).getGenerationSettings().getSurfaceConfig().getTopMaterial(), false);
					}
				}

				return true;
			}
		}
	}

//	@Override
//	protected boolean carveAtPoint(CarverContext context, CaveCarverConfig config, Chunk chunk, Function<BlockPos, Biome> posToBiome, BitSet carvingMask, Random random, Mutable pos, Mutable downPos, AquiferSampler sampler, MutableBoolean foundSurface) {
//		int i = relativeX | relativeZ << 4 | y << 8;
//		if (carvingMask.get(i)) {
//			return false;
//		} else {
//			carvingMask.set(i);
//			pos1.set(x, y, z);
//			BlockState state = chunk.getBlockState(pos1);
//			BlockState upState = chunk.getBlockState(mutable2.set(pos1, Direction.UP));
//			if (state.isOf(LintBlocks.CORRUPT_GRASS) || state.isOf(LintBlocks.LIVELY_GRASS)) {
//				grassCheckerThing.setTrue();
//			}
//
//			if (!this.canCarveBlock(state, upState)) {
//				return false;
//			} else {
//				if (y < 11) {
//					chunk.setBlockState(pos1, LAVA.getBlockState(), false);
//				} else {
//					chunk.setBlockState(pos1, CAVE_AIR, false);
//					if (grassCheckerThing.isTrue()) {
//						pos3.set(pos1, Direction.DOWN);
//						if (chunk.getBlockState(pos3).isOf(LintBlocks.RICH_DIRT)) {
//							chunk.setBlockState(pos3, posToBiome.apply(pos1).getGenerationSettings().getSurfaceConfig().getTopMaterial(), false);
//						}
//					}
//				}
//
//				return true;
//			}
//		}
//	}
}
