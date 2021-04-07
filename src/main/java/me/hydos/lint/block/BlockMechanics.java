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

package me.hydos.lint.block;

import me.hydos.lint.block.BlockBuilder.BlockConstructor;
import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.minecraft.block.AbstractBlock.OffsetType;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

/**
 * Class for constructing blocks with particular special properties.
 *
 * @reason insurance against mojank updates.
 */
public class BlockMechanics implements BlockConstructor<Block> {
    @Nullable
    private OnUse onUse;
    @Nullable
    private EntityCollision collision;
    @Nullable
    private NeighbourUpdate horse; // Neigh.

    private OffsetType offset = OffsetType.NONE;

    public BlockMechanics onUse(OnUse onUse) {
        this.onUse = onUse;
        return this;
    }

    public BlockMechanics onEntityCollision(EntityCollision collision) {
        this.collision = collision;
        return this;
    }

    public BlockMechanics onNeighbourUpdate(NeighbourUpdate horse) {
        this.horse = horse;
        return this;
    }

    public BlockMechanics offsetType(OffsetType type) {
        this.offset = type;
        return this;
    }

    @Override
    public Block create(FabricBlockSettings settings) {
        return new MechanicalBlock(settings, this.onUse, this.offset, this.collision, this.horse);
    }

    // Block Class and Functional Interfaces

    private static class MechanicalBlock extends Block {
        public MechanicalBlock(Settings settings, @Nullable OnUse onUse, OffsetType offset,
                               @Nullable EntityCollision collision, @Nullable NeighbourUpdate horse) {
            super(settings);
            this.onUse = onUse == null ? super::onUse : onUse;
            this.horse = horse == null ? super::neighborUpdate : horse;
            this.collision = collision == null ? super::onEntityCollision : collision;
            this.offset = offset;
        }

        private final OnUse onUse;
        private final OffsetType offset;
        private final EntityCollision collision;
        private final NeighbourUpdate horse;

        @Override
        public ActionResult onUse(BlockState state, World world, BlockPos pos, PlayerEntity player, Hand hand, BlockHitResult hit) {
            return this.onUse.onUse(state, world, pos, player, hand, hit);
        }

        @Override
        public OffsetType getOffsetType() {
            return this.offset;
        }

        @Override
        public void onEntityCollision(BlockState state, World world, BlockPos pos, Entity entity) {
            this.collision.onCollision(state, world, pos, entity);
        }

        @Override
        public void neighborUpdate(BlockState state, World world, BlockPos pos, Block block, BlockPos fromPos, boolean notify) {
            this.horse.neighborUpdate(state, world, pos, block, fromPos, notify);
        }
    }

    @FunctionalInterface
    public interface OnUse {
        ActionResult onUse(BlockState state, World world, BlockPos pos, PlayerEntity player, Hand hand, BlockHitResult hit);
    }

    @FunctionalInterface
    public interface EntityCollision {
        void onCollision(BlockState state, World world, BlockPos pos, Entity entity);
    }

    @FunctionalInterface
    public interface NeighbourUpdate {
        void neighborUpdate(BlockState state, World world, BlockPos pos, Block block, BlockPos fromPos, boolean notify);
    }
}
