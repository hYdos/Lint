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

import me.hydos.lint.block.LintBlocks;
import net.minecraft.block.AbstractFireBlock;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(AbstractFireBlock.class)
public class AbstractFireBlockMixin {

	@Inject(method = "onBlockAdded", at = @At("HEAD"))
	private void checkForLintPortal(BlockState state, World world, BlockPos pos, BlockState oldState, boolean notify, CallbackInfo ci) {
		BlockPos frame = pos.down();
		Block frameBlock = Blocks.COAL_BLOCK;
		// Hardcoded for now yeef
		if(
				world.getBlockState(frame).getBlock() ==  frameBlock && //Bottom
				world.getBlockState(frame.up(3)).getBlock() ==  frameBlock && //Top
				world.getBlockState(frame.up(1).east()).getBlock() ==  frameBlock && //Left lower
				world.getBlockState(frame.up(2).east()).getBlock() ==  frameBlock && //Left higher
				world.getBlockState(frame.up(1).west()).getBlock() ==  frameBlock && //Right lower
				world.getBlockState(frame.up(2).west()).getBlock() ==  frameBlock //Right higher
		) {
			world.setBlockState(pos, LintBlocks.HAYKAMIUM_PORTAL.getDefaultState());
			world.setBlockState(pos.up(), LintBlocks.HAYKAMIUM_PORTAL.getDefaultState());
		}
	}
}
