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

import me.hydos.lint.world.dimension.LintDimensions;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

/**
 * @reason prevent weather in Fraiya. TODO probably allow weather in fraiya but just make it less common?
 */
@Mixin(World.class)
public class WorldMixin {
	@Inject(at = @At("HEAD"), method = "isRaining", cancellable = true)
	private void onIsRaining(CallbackInfoReturnable<Boolean> cir) {
		@SuppressWarnings("resource")
		World self = (World) (Object) this;

		if (self.getRegistryKey().equals(LintDimensions.FRAIYA_WORLD)) {
			cir.setReturnValue(false);
		}
	}

	@Inject(at = @At("HEAD"), method = "isThundering", cancellable = true)
	private void onIsThundering(CallbackInfoReturnable<Boolean> cir) {
		@SuppressWarnings("resource")
		World self = (World) (Object) this;

		if (self.getRegistryKey().equals(LintDimensions.FRAIYA_WORLD)) {
			cir.setReturnValue(false);
		}
	}
}
