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

package me.hydos.lint.screenhandler;

import me.hydos.lint.Lint;
import net.fabricmc.fabric.api.screenhandler.v1.ScreenHandlerRegistry;
import net.minecraft.screen.ScreenHandlerType;

public class ScreenHandlers {

	public static final ScreenHandlerType<SmelteryScreenHandler> SMELTERY = ScreenHandlerRegistry.registerExtended(Lint.id("smeltery"), SmelteryScreenHandler::new);
	public static final ScreenHandlerType<LilTaterInteractScreenHandler> TATER_INVENTORY = ScreenHandlerRegistry.registerExtended(Lint.id("tater_inv"), LilTaterInteractScreenHandler::new);

	public static void initialize() {
	}
}
