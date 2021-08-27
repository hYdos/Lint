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

package me.hydos.lint.sound;

import java.util.Optional;

import me.hydos.lint.block.LintBlocks;
import me.hydos.lint.entity.Entities;
import me.hydos.lint.util.math.Vec2i;
import me.hydos.lint.world.dimension.Dimensions;
import me.hydos.lint.world.feature.TownFeature;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.client.render.WorldRenderer;
import net.minecraft.client.sound.SoundManager;
import net.minecraft.sound.MusicSound;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Box;
import net.minecraft.world.Heightmap;
import net.minecraft.world.World;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.source.BiomeAccess;
import net.minecraft.world.chunk.Chunk;

public class LintSoundManager {
	public static boolean isRecordPlaying(ClientPlayerEntity player, WorldRenderer renderer, SoundManager soundManager) {
		Box box = player.getBoundingBox().expand(55.0);
		return renderer.playingSongs.entrySet().stream().anyMatch(entry -> box.contains(entry.getKey().getX(), entry.getKey().getY(), entry.getKey().getZ()) && soundManager.isPlaying(entry.getValue()));
	}

	public static Optional<MusicSound> injectSpecialMusic(World world, BlockPos playerPos, double x, double y, double z) {
		int playerY = playerPos.getY();
		int passthroughs = 2;

		// test blocks below for dungeon
		// you can't get dungeon boxes on the client ok structures are SERVER side this is a good compromise
		if (playerY > 0 && playerY < 256) {
			for (int i = 1; i < 7; ++i) {
				if (playerY < i) {
					break;
				}

				BlockPos pos = playerPos.down(i);
				BlockState bs = world.getBlockState(pos);

				if (bs.isFullCube(world, pos)) {
					Block b = bs.getBlock();

					if (b == LintBlocks.DUNGEON_BRICKS || b == LintBlocks.DUNGEON_BRICK_SLAB || b == LintBlocks.DUNGEON_BRICK_SLAB) {
						return Optional.of(MusicSounds.DUNGEON);
					} else {
						if (passthroughs-- == 0) {
							break;
						}
					}
				} else if (!bs.isAir()) {
					++i;
				}
			}
		}

		Chunk chunk = world.getChunk(playerPos);
		int targetWS = chunk.sampleHeightmap(Heightmap.Type.WORLD_SURFACE, (int) x, (int) z) - 15;
		int targetMB = chunk.sampleHeightmap(Heightmap.Type.MOTION_BLOCKING, (int) x, (int) z) - 2;
		int checky = playerPos.getY();

		if (checky < 40 || checky < world.getSeaLevel() && checky < targetWS && checky < targetMB) {
			return Optional.of(MusicSounds.CAVERNS);
		}

		synchronized (SecurityProblemCauser.lock) {
			if (SecurityProblemCauser.townLocs != null) {
				int i = 0;

				for (Vec2i townLoc : SecurityProblemCauser.townLocs) {
					double dx = x - townLoc.getX();
					double dz = z - townLoc.getY();

					if (dx * dx + dz * dz < TownFeature.SUBURB_DIST) {
						return Optional.of(MusicSounds.TOWNS[i]);
					}

					i++;
				}
			}
		}

		return Optional.empty();
	}

	public static Biome injectBiomeLoopDummies(ClientPlayerEntity player, BiomeAccess access, double x, double y, double z) {
		World world = player.getEntityWorld();

		if (world.getRegistryKey() == Dimensions.FRAIYA_WORLD) {
			if (world != null && !world.getEntitiesByType(Entities.KING_TATER, player.getBoundingBox().expand(40), $ -> true).isEmpty()) {
				return MusicSounds.DUMMY_KING_TATER;
			}

			if (world != null && !world.getEntitiesByType(Entities.I509VCB, player.getBoundingBox().expand(40), $ -> true).isEmpty()) {
				return MusicSounds.DUMMY_I509;
			}
		}

		return access.getBiomeForNoiseGen(x, y, z);
	}
}
