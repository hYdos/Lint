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

package me.hydos.lint.mixin.world;

import net.minecraft.util.Identifier;
import net.minecraft.world.dimension.DimensionType;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Invoker;

import java.util.OptionalLong;

// TODO: crab accessors
@Mixin(DimensionType.class)
public interface DimensionTypeAccessor {
	@Invoker("<init>")
	static DimensionType create(
			OptionalLong fixedTime,
			boolean hasSkylight,
			boolean hasCeiling,
			boolean ultrawarm,
			boolean natural,
			double coordinateScale,
			boolean piglinSafe,
			boolean bedWorks,
			boolean respawnAnchorWorks,
			boolean hasRaids,
			int minimumY,
			int height,
			int logicalHeight,
			Identifier infiniburn,
			Identifier skyProperties,
			float ambientLight) {
		throw new AssertionError("Someone corbed mixin"); // TODO: more detailed error message and better error handling
	}
}