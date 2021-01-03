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

package me.hydos.lint.multiblock;

import me.hydos.lint.tag.LintBlockTags;

public class Multiblocks {
	public static final SimpleMultiblockType SMELTERY = new SimpleMultiblockType(LintBlockTags.BASIC_CASING)
			.addPosition(0, 1, 0) // block above smeltery
			.addPosition(0, 0, 2) // far column
			.addPosition(0, 1, 2)
			.addPosition(-1, 0, 1) // left column
			.addPosition(-1, 1, 1)
			.addPosition(1, 0, 1) // right column
			.addPosition(1, 1, 1)
			.addAir(0, 0, 1) // air in centre
			.addAir(0, 1, 1);
}
