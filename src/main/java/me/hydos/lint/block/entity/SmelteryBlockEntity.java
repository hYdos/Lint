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
import net.minecraft.nbt.NbtCompound;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.screen.NamedScreenHandlerFactory;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.text.LiteralText;
import net.minecraft.text.Text;
import net.minecraft.util.math.BlockPos;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;

public class SmelteryBlockEntity extends BlockEntity implements ExtendedScreenHandlerFactory, NamedScreenHandlerFactory, BlockEntityClientSerializable {

	public final List<FluidStack> fluidData = new ArrayList<>(5);
	public LintInventory inventory = new LintInventory(9);
	public Multiblock multiblock;

	public SmelteryBlockEntity(BlockPos pos, BlockState state) {
		super(BlockEntities.SMELTERY, pos, state);
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
	public NbtCompound writeNbt(NbtCompound tag) {
		NbtCompound serialisedFluidData = new NbtCompound();
		writeFluidsToTag(serialisedFluidData);
		tag.put("fluidData", serialisedFluidData);
		Inventories.writeNbt(tag, inventory.getRawList());
		return super.writeNbt(tag);
	}

	@Override
	public void readNbt(NbtCompound nbt) {
		super.readNbt(nbt);
		updateFluids(nbt.getCompound("fluidData"));
		inventory = new LintInventory(9);
		Inventories.readNbt(nbt, inventory.getRawList());
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

	public void updateFluids(NbtCompound fluidInformation) {
		if (fluidInformation != null) {
			for (int i = 0; i < fluidInformation.getInt("size"); i++) {
				fluidData.add(FluidStack.fromTag((NbtCompound) fluidInformation.get(String.valueOf(i))));
			}
		}
	}

	public List<FluidStack> getFluidData() {
		return fluidData;
	}

	public void writeFluidsToTag(NbtCompound tag) {
		tag.putInt("size", fluidData.size());
		for (int i = 0; i < fluidData.size(); i++) {
			tag.put(String.valueOf(i), fluidData.get(i).toTag());
		}
	}

	@Override
	public void fromClientTag(NbtCompound compoundTag) {
		readNbt(compoundTag);
	}

	@Override
	public NbtCompound toClientTag(NbtCompound compoundTag) {
		return writeNbt(compoundTag);
	}
}
