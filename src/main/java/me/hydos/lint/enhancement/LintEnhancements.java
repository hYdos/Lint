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

package me.hydos.lint.enhancement;

import me.hydos.lint.util.Power;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundTag;

import java.util.Set;
import java.util.stream.Collectors;

public final class LintEnhancements {
	/**
	 * @return the level of enhancement for the given power, with default return value of 0.
	 */
	public static float getEnhancement(ItemStack stack, Power.Broad power) {
		CompoundTag tag = stack.getOrCreateSubTag("lint_enhancements");
		String key = power.name();
		return tag.contains(key) ? tag.getFloat(key) : 0;
	}

	public static Set<Power.Broad> getEnhancements(ItemStack stack) {
		CompoundTag tag = stack.getOrCreateSubTag("lint_enhancements");
		return tag.getKeys().stream().map(Power.Broad::valueOf).collect(Collectors.toSet());
	}

	public static boolean isModified(ItemStack stack) {
		return stack.getOrCreateSubTag("lint_enhancements").getKeys().isEmpty();
	}

	/**
	 * Pls call on the server.
	 * @return the new power level of the power if successful. Otherwise, returns 0.
	 */
	public static float enhance(ItemStack stack, Power.Broad power, float increaseAmount) {
		CompoundTag tag = stack.getOrCreateSubTag("lint_enhancements");
		final int powers = tag.getKeys().size();

		if (powers > 0) { // if already has powers.
			if (!tag.getKeys().contains(power.name())) {
				switch (Power.Broad.valueOf(tag.getKeys().iterator().next())) {
					case ALLOS:
					case MANOS:
						return 0; // either one major power
					default:
						if (powers > 1 || power == Power.Broad.ALLOS || power == Power.Broad.MANOS) { // or 2 minor powers
							return 0;
						}
						break;
				}
			}
		}

		Item item = stack.getItem();

		// update data (i.e. mess with attribute tags if necessary)
		if (item instanceof Enhanceable) {
			((Enhanceable) item).update(stack, power, increaseAmount, powers == 0);
		}

		String key = power.name();

		// put new value
		float newValue = (tag.contains(key) ? tag.getFloat(key) : 0) + increaseAmount;
		tag.putFloat(key, newValue);

		return newValue;
	}
}
