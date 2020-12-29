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

package me.hydos.lint.components;

import dev.onyxstudios.cca.api.v3.component.sync.AutoSyncedComponent;
import it.unimi.dsi.fastutil.objects.Object2FloatArrayMap;
import it.unimi.dsi.fastutil.objects.Object2FloatMap;
import me.hydos.lint.util.Power;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.server.network.ServerPlayerEntity;

public class LintEnhancementComponent implements AutoSyncedComponent {
	public LintEnhancementComponent(ItemStack stack) {
		this.stack = stack;
	}

	private final ItemStack stack;
	private final Object2FloatMap<Power.Broad> enhancements = new Object2FloatArrayMap<>();

	@Override
	public boolean shouldSyncWith(ServerPlayerEntity player) {
		return player.inventory.contains(this.stack);
	}

	@Override
	public void readFromNbt(CompoundTag tag) {
		for (Power.Broad power : Power.Broad.values()) {
			String powerName = power.name();

			if (tag.contains(powerName)) {
				this.enhancements.put(power, tag.getFloat(powerName));
			}
		}
	}

	@Override
	public void writeToNbt(CompoundTag tag) {
		this.enhancements.forEach((power, value) -> {
			tag.putFloat(power.name(), value);
		});
	}
}
