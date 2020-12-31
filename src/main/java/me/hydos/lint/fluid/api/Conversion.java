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

package me.hydos.lint.fluid.api;

/**
 * Why did i make lint an fluid api :concern:
 * Used to help convert between different measurements (blocks -> litres cubed, litres cubed -> fluid height)
 *
 * @author hydos
 */
public class Conversion {

	public static final double NUGGET = 1d / 81d;
	public static final double INGOT = 1d / 9d;
	public static final double BLOCK = 1D;
}
