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

package me.hydos.lint.mixin.gecko;

import net.fabricmc.loader.FabricLoader;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;
import software.bernie.example.registry.RegistryUtils;

@Mixin(value = RegistryUtils.class, remap = false)
public class RegistryUtilsMixin {

	@Redirect(method = {
			"register(Lnet/minecraft/block/Block;Lnet/minecraft/util/Identifier;Lnet/minecraft/item/ItemGroup;)Lnet/minecraft/block/Block;",
			"registerBlockWithoutItem(Lnet/minecraft/block/Block;Lnet/minecraft/util/Identifier;)Lnet/minecraft/block/Block;",
			"registerBlockWithoutItem(Ljava/lang/String;Lnet/minecraft/block/Block;)Lnet/minecraft/block/Block;",
			"registerItem(Lnet/minecraft/item/Item;Lnet/minecraft/util/Identifier;)Lnet/minecraft/item/Item;",
			"registerItem(Ljava/lang/String;Lnet/minecraft/item/Item;)Lnet/minecraft/item/Item;",
			"registerBlockEntity"
	}, at = @At(value = "INVOKE", target = "Lnet/fabricmc/loader/FabricLoader;isDevelopmentEnvironment()Z"), require = 0)
	private static boolean isDevelopmentEnvironment(FabricLoader fabricLoader) {
		return false;
	}
}
