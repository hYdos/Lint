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

import net.minecraft.block.Blocks;
import net.minecraft.entity.Entity;
import net.minecraft.entity.mob.EndermanEntity;
import net.minecraft.fluid.Fluid;
import net.minecraft.fluid.FluidState;
import net.minecraft.fluid.Fluids;
import net.minecraft.tag.FluidTags;
import net.minecraft.tag.Tag;
import net.minecraft.text.Text;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(value = Entity.class, priority = 69696969)
public abstract class EntityMixin {

    @Shadow
    public World world;

    @Shadow
    public abstract Vec3d getPos();

    @Shadow
    public abstract BlockPos getBlockPos();

    @Shadow
    public abstract Text getName();

    boolean doNormalCheck;

    @Redirect(method = "updateMovementInFluid", at = @At(value = "INVOKE", target = "Lnet/minecraft/fluid/FluidState;isIn(Lnet/minecraft/tag/Tag;)Z"))
    private boolean isIn(FluidState state, Tag<Fluid> tag) {
        if (doNormalCheck) {
            return state.isIn(tag);
        }
        if (!state.getFluid().isIn(tag) && !tag.equals(FluidTags.LAVA) && state.getFluid() != Fluids.EMPTY) {
            return !state.isEmpty();
        } else {
            return state.isIn(tag);
        }
    }

    @Inject(method = "isWet", at = @At("HEAD"), cancellable = true)
    private void isRaining(CallbackInfoReturnable<Boolean> cir) {
        cir.setReturnValue(world.isRaining() || this.world.getFluidState(getBlockPos()).isIn(FluidTags.WATER) && world.getBlockState(getBlockPos()).getBlock() != Blocks.AIR);
    }
}
