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

import net.minecraft.block.Block;
import net.minecraft.client.MinecraftClient;
import net.minecraft.tag.Tag;
import net.minecraft.text.LiteralText;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.World;

import java.util.ArrayList;
import java.util.List;

public class MultiblockManager {

	public static final int MAX_DEPTH = 100;

	public static Multiblock findCuboid(World world, BlockPos startPos, Tag<Block> validTag) {
		List<BlockPos> blocks = new ArrayList<>();
		BlockPos min = new BlockPos(0, 0, 0);
//		BlockPos max = new BlockPos(0, 0, 0);
		//TODO: oh god oh fuck
		for (int x = 0; x > -MAX_DEPTH; x--) {
			for (int z = 0; z > -MAX_DEPTH; z--) {
				BlockPos pos = startPos.offset(Direction.Axis.X, x).offset(Direction.Axis.Z, z);
				if (world.getBlockState(pos).isIn(validTag)) {
					if(min.getX() > pos.getX()){
						min = min.add(pos.getX(), 0, 0);
					}
					if(min.getY() > pos.getY()){
						min = min.add(0, pos.getY(), 0);
					}
					if(min.getZ() > pos.getZ()){
						min = min.add(0, 0, pos.getZ());
					}
				} else {
					break;
				}
			}
		}
		MinecraftClient.getInstance().player.sendMessage(new LiteralText(min.toString()), false);
//		MinecraftClient.getInstance().player.sendMessage(new LiteralText(max.toString()), false);
		return new Multiblock(null, null, blocks);
	}
}
