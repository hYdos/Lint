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

package me.hydos.lint.block.organic;

import it.unimi.dsi.fastutil.ints.Int2ObjectArrayMap;
import it.unimi.dsi.fastutil.ints.Int2ObjectMap;
import me.hydos.lint.particle.Particles;
import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.LeavesBlock;
import net.minecraft.item.ItemPlacementContext;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.BooleanProperty;
import net.minecraft.state.property.IntProperty;
import net.minecraft.state.property.Properties;
import net.minecraft.tag.BlockTags;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.util.shape.VoxelShapes;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;
import net.minecraft.world.WorldAccess;

import java.util.Random;

/**
 * Based off the code in terraform because I don't wanna figure out every override myself.
 * Not using terraform because I plan on updating to snapshot soon and they're not on latest.
 * Plus I needed to make a few modifications.
 */

public class DistantLeavesBlock extends Block {
	public DistantLeavesBlock(int distanceMax, Settings settings) {
		super(new Fuck(settings).fuck(distanceMax));
		this.distanceMax = distanceMax;
		this.setDefaultState(this.getStateManager().getDefaultState().with(PERSISTENT, false).with(distance(this), distanceMax));
	}

	// Valoeghese: Replace distanceMax with a field.
	private final int distanceMax;

	@Override
	public boolean hasRandomTicks(BlockState state) {
		return state.get(distance(this)) == this.distanceMax && !state.get(PERSISTENT);
	}

	@Override
	public void randomTick(BlockState state, ServerWorld world, BlockPos pos, Random random) {
		if (!state.get(PERSISTENT) && state.get(distance(this)) == this.distanceMax) {
			dropStacks(state, world, pos);
			world.removeBlock(pos, false);
		}
	}

	@Override
	public void scheduledTick(BlockState state, ServerWorld world, BlockPos pos, Random random) {
		world.setBlockState(pos, this.calculateAndUpdateDistance(state, world, pos)); // Flags are 3 in the original however this is default so optimise by removing that
	}

	// Valoeghese: Remove Optileaves from original terrestria code. Reason: No longer overrides isSideInvisible

	// Valoeghese: Match getOpacity to original LeavesBlock method
	@Override
	public int getOpacity(BlockState state, BlockView world, BlockPos pos) {
		return 1;
	}

	@Override
	public BlockState getStateForNeighborUpdate(BlockState state, Direction direction, BlockState neighborState, WorldAccess world, BlockPos pos, BlockPos neighborPos) {
		int distance = getDistance(neighborState) + 1;
		if (distance != 1 || state.get(distance(this)) != distance) {
			world.getBlockTickScheduler().schedule(pos, this, 1);
		}

		return state;
	}

	private BlockState calculateAndUpdateDistance(BlockState state, WorldAccess world, BlockPos start) {
		int result = this.distanceMax;
		BlockPos.Mutable pos = new BlockPos.Mutable();

		for (Direction direction : Direction.values()) {
			pos.set(start, direction);
			result = Math.min(result, this.getDistance(world.getBlockState(pos)) + 1);
			if (result == 1) {
				break;
			}
		}

		return state.with(distance(this), result);
	}

	// Valoeghese: Edit check to work with all leaf blocks. This might cause some bugs but I prefer compatibility.
	private int getDistance(BlockState state) {
		if (BlockTags.LOGS.contains(state.getBlock())) {
			return 0;
		} else {
			return state.getBlock() instanceof DistantLeavesBlock ? state.get(distance(this)) : (state.getBlock() instanceof LeavesBlock ? state.get(LeavesBlock.DISTANCE) : this.distanceMax);
		}
	}

	@Override
	public void randomDisplayTick(BlockState state, World world, BlockPos pos, Random random) {
		Blocks.BIRCH_LEAVES.randomDisplayTick(state, world, pos, random);

		// If I remove this I get a 5fps boost (not scientifically tested I only did one test probably inaccurate measurement)
		if (random.nextInt(15) == 1) {
			double x = (double) pos.getX() + random.nextDouble();
			double y = (double) pos.getY() + random.nextInt(1);
			double z = (double) pos.getZ() + random.nextDouble();
			if (world.isAir(pos.offset(Direction.DOWN))) {
				world.addParticle(Particles.FALLEN_MYSTICAL_LEAF, x, y, z, 0.0D, -0.2D, 0.0D);
			}
		}
	}

	@Override
	protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
		builder.add(distance(((Fuck) this.settings).distanceMax), PERSISTENT);
	}

	@Override
	public BlockState getPlacementState(ItemPlacementContext context) {
		return this.calculateAndUpdateDistance(this.getDefaultState().with(PERSISTENT, true), context.getWorld(), context.getBlockPos());
	}

	@Override
	public VoxelShape getSidesShape(BlockState state, BlockView world, BlockPos pos) {
		return VoxelShapes.empty();
	}

	public static final IntProperty distance(DistantLeavesBlock block) {
		try {
			return distance(block.distanceMax);
		} catch (IllegalArgumentException e) {
			throw e;
		}
	}

	private static final IntProperty distance(int dist) {
		return DISTANCE.computeIfAbsent(dist, val -> IntProperty.of("distance", 1, val));
	}

	public static final Int2ObjectMap<IntProperty> DISTANCE = new Int2ObjectArrayMap<>();
	public static final BooleanProperty PERSISTENT = Properties.PERSISTENT;

	private static class Fuck extends FabricBlockSettings {
		public Fuck(Settings parent) {
			super(parent);
		}

		private int distanceMax;

		public Fuck fuck(int distanceMax) {
			this.distanceMax = distanceMax;
			return this;
		}
	}
}
