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

import me.hydos.lint.Lint;
import me.hydos.lint.block.LintBlocksOld;
import net.fabricmc.api.ModInitializer;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.util.registry.Registry;

public class BlockEntities implements ModInitializer {

	public static final BlockEntityType<SmelteryBlockEntity> SMELTERY = Registry.register(Registry.BLOCK_ENTITY_TYPE,
			Lint.id("smeltery"),
			BlockEntityType.Builder.create(
					SmelteryBlockEntity::new,
					LintBlocksOld.SMELTERY
			).build(null));

	@Override
	public void onInitialize() {
	}
}
