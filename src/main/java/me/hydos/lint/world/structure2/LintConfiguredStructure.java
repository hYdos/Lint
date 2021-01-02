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

package me.hydos.lint.world.structure2;

class LintConfiguredStructure {
	LintConfiguredStructure(LintStructure structure, int maxIterDepth, int prepareChunkDistance, int gridSize) {
		this.structure = structure;
		this.gridSize = gridSize;
		this.maxIterDepth = maxIterDepth;
		this.prepareChunkDistance = prepareChunkDistance;
	}

	final LintStructure structure;
	private final int gridSize;
	private final int maxIterDepth;
	private final int prepareChunkDistance;

	public int getMaxIterDepth() {
		return this.maxIterDepth;
	}

	public int getPrepareChunkDistance() {
		return this.prepareChunkDistance;
	}

	public int getGridSize() {
		return this.gridSize;
	}
}
