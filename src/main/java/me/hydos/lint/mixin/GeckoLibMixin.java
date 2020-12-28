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

package me.hydos.lint.mixin;

import net.fabricmc.fabric.api.resource.ResourceManagerHelper;
import net.fabricmc.fabric.impl.resource.loader.ResourceManagerHelperImpl;
import net.minecraft.resource.ResourceType;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;
import software.bernie.geckolib3.GeckoLib;

@Mixin(GeckoLib.class)
public class GeckoLibMixin {

	@Redirect(method = "initialize", at = @At(value = "INVOKE", target = "Lnet/fabricmc/fabric/impl/resource/loader/ResourceManagerHelperImpl;get(Lnet/minecraft/resource/ResourceType;)Lnet/fabricmc/fabric/api/resource/ResourceManagerHelper;"), require = 0)
	private static ResourceManagerHelper get(ResourceType type) {
		return ResourceManagerHelperImpl.get(type);
	}
}
