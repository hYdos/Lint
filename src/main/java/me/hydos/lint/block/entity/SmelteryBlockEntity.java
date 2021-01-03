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

import me.hydos.lint.fluid.FluidStack;
import me.hydos.lint.multiblock.Multiblock;
import me.hydos.lint.multiblock.Multiblocks;
import me.hydos.lint.screenhandler.SmelteryScreenHandler;
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
import net.minecraft.text.LiteralText;
import net.minecraft.text.Text;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;

public class SmelteryBlockEntity extends BlockEntity implements ExtendedScreenHandlerFactory, NamedScreenHandlerFactory, BlockEntityClientSerializable {

	public final List<FluidStack> fluidData = new ArrayList<>(5);
	public LintInventory inventory = new LintInventory(9);
	public Multiblock multiblock;

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
		Inventories.toTag(tag, inventory.getRawList());
		return super.toTag(tag);
	}

	@Override
	public void fromTag(BlockState state, CompoundTag tag) {
		super.fromTag(state, tag);
		updateFluids(tag.getCompound("fluidData"));
		inventory = new LintInventory(9);
		Inventories.fromTag(tag, inventory.getRawList());
	}

	public boolean isActive() {
		return true;
	}

	public void updateMultiblock() {
		multiblock = Multiblocks.SMELTERY.find(world, getPos());
	}

	@Override
	public void writeScreenOpeningData(ServerPlayerEntity serverPlayerEntity, PacketByteBuf packetByteBuf) {
		packetByteBuf.writeBlockPos(getPos());
	}

	public void updateFluids(CompoundTag fluidInformation) {
		if (fluidInformation != null) {
			for (int i = 0; i < fluidInformation.getInt("size"); i++) {
				fluidData.add(FluidStack.fromTag((CompoundTag) fluidInformation.get(String.valueOf(i))));
			}
		}
	}

	public List<FluidStack> getFluidData() {
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
