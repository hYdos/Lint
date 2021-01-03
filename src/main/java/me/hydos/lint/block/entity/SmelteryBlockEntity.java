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

package me.hydos.lint.block.entity;

import me.hydos.lint.block.SmelteryBlock;
import me.hydos.lint.fluid.SimpleFluidData;
import me.hydos.lint.multiblock.MultiblockManager;
import me.hydos.lint.screenhandler.SmelteryScreenHandler;
import me.hydos.lint.tag.LintBlockTags;
import me.hydos.lint.util.LintInventory;
import net.fabricmc.fabric.api.block.entity.BlockEntityClientSerializable;
import net.fabricmc.fabric.api.screenhandler.v1.ExtendedScreenHandlerFactory;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.Inventories;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.screen.NamedScreenHandlerFactory;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.state.property.Property;
import net.minecraft.text.LiteralText;
import net.minecraft.text.Text;
import net.minecraft.util.math.BlockPos;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;

public class SmelteryBlockEntity extends BlockEntity implements ExtendedScreenHandlerFactory, NamedScreenHandlerFactory, BlockEntityClientSerializable {

	private final List<SimpleFluidData> fluidData = new ArrayList<>(5);
	public BlockPos center;
	private boolean validMultiblock;
	public LintInventory inventory = new LintInventory(9);

	public SmelteryBlockEntity() {
		super(BlockEntities.SMELTERY);
	}

	@Override
	public Text getDisplayName() {
		return new LiteralText("Smeltery");
	}

	@Nullable
	@Override
	public ScreenHandler createMenu(int syncId, PlayerInventory inv, PlayerEntity player) {
		updateMultiblock();
		return new SmelteryScreenHandler(syncId, inv, this);
	}

	@Override
	public CompoundTag toTag(CompoundTag tag) {
		CompoundTag serialisedFluidData = new CompoundTag();
		writeFluidsToTag(serialisedFluidData);
		tag.put("fluidData", serialisedFluidData);
		tag.putBoolean("valid_multiblock", validMultiblock);
		Inventories.toTag(tag, inventory.getRawList());
		return super.toTag(tag);
	}

	@Override
	public void fromTag(BlockState state, CompoundTag tag) {
		super.fromTag(state, tag);
		center = pos.offset(state.get(SmelteryBlock.FACING).getOpposite());
		updateFluids(tag.getCompound("fluidData"));
		validMultiblock = tag.getBoolean("valid_multiblock");
		inventory = new LintInventory(9);
		Inventories.fromTag(tag, inventory.getRawList());
	}

	public <T extends Comparable<T>> void setBlockstateProperty(Property<T> property, T value) {
		if (world.getBlockState(pos).get(property) != value) {
			world.setBlockState(pos, world.getBlockState(pos).with(property, value));
		}
	}

	public boolean isActive() {
		return true;
	}

	public void updateMultiblock() {
		// TODO hydos use new multiblock system
		MultiblockManager.findCuboid(world, getPos(), LintBlockTags.BASIC_CASING);
//		center = pos.offset(world.getBlockState(pos).get(SmelteryBlock.FACING).getOpposite());
//		if (!world.isClient()) {
//			BlockPos topCenter = center.up(1);
//
//			int validDirections = 0;
//			// loop through all but up and down directions and check for the casting tag
//			for (Direction direction : Direction.values()) {
//				if (direction != Direction.DOWN && direction != Direction.UP) {
//					Block lowerWall = world.getBlockState(center.offset(direction)).getBlock();
//					Block upperWall = world.getBlockState(topCenter.offset(direction)).getBlock();
//					if (lowerWall.isIn(LintBlockTags.BASIC_CASING) && upperWall.isIn(LintBlockTags.BASIC_CASING)) {
//						validDirections++;
//					}
//				}
//			}
//			if (validDirections == 4) {
//				this.validMultiblock = true;
//				setBlockstateProperty(SmelteryBlock.LIT, true);
//			} else {
//				setBlockstateProperty(SmelteryBlock.LIT, false);
//				this.validMultiblock = false;
//			}
//		}
	}

	@Override
	public void writeScreenOpeningData(ServerPlayerEntity serverPlayerEntity, PacketByteBuf packetByteBuf) {
		packetByteBuf.writeBlockPos(getPos());
	}

	public void updateFluids(CompoundTag fluidInformation) {
		if(fluidInformation != null) {
			for (int i = 0; i < fluidInformation.getInt("size"); i++) {
				fluidData.add(SimpleFluidData.fromTag((CompoundTag) fluidInformation.get(String.valueOf(i))));
			}
		}
	}

	public List<SimpleFluidData> getFluidData() {
		return fluidData;
	}

	public void writeFluidsToTag(CompoundTag tag) {
		tag.putInt("size", fluidData.size());
		for (int i = 0; i < fluidData.size(); i++) {
			tag.put(String.valueOf(i), fluidData.get(i).toTag());
		}
	}

	@Override
	public void fromClientTag(CompoundTag compoundTag) {
		fromTag(world.getBlockState(pos), compoundTag);
	}

	@Override
	public CompoundTag toClientTag(CompoundTag compoundTag) {
		return toTag(compoundTag);
	}
}
