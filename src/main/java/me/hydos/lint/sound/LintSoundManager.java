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

import it.unimi.dsi.fastutil.objects.Object2ObjectArrayMap;
import me.hydos.lint.block.LintBlocks;
import me.hydos.lint.entity.Entities;
import me.hydos.lint.util.math.Vec2i;
import me.hydos.lint.world.biome.Biomes;
import me.hydos.lint.world.dimension.Dimensions;
import me.hydos.lint.world.feature.TownFeature;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.client.render.WorldRenderer;
import net.minecraft.client.sound.BiomeEffectSoundPlayer;
import net.minecraft.client.sound.BiomeEffectSoundPlayer.MusicLoop;
import net.minecraft.client.sound.MovingSoundInstance;
import net.minecraft.client.sound.SoundManager;
import net.minecraft.sound.SoundEvent;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Box;
import net.minecraft.world.Heightmap;
import net.minecraft.world.World;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.source.BiomeAccess;
import net.minecraft.world.chunk.Chunk;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

public class LintSoundManager {
	private static final Set<Identifier> BOSS_MUSIC = new HashSet<>();
	private static final Set<Identifier> RECORD_MUSIC = new HashSet<>();
	private static boolean isRecordPlaying = false;
	private static Optional<SoundEvent> prev = Optional.empty();
	private static Optional<SoundEvent> next = Optional.empty();

	public static boolean isPlayingRecordMusic(ClientPlayerEntity player, SoundManager soundManager, WorldRenderer worldRenderer) {
		Box box = player.getBoundingBox().expand(55.0);
		return worldRenderer.playingSongs.entrySet().stream().anyMatch(entry -> box.contains(entry.getKey().getX(), entry.getKey().getY(), entry.getKey().getZ()) && soundManager.isPlaying(entry.getValue()));
	}

	public static boolean isCachedAsRecordPlaying() {
		return isRecordPlaying;
	}

	public static SoundEvent registerBossMusic(SoundEvent event) {
		BOSS_MUSIC.add(event.getId());
		return event;
	}

	public static SoundEvent registerRecordMusic(SoundEvent event) {
		RECORD_MUSIC.add(event.getId());
		return event;
	}

	public static void checkFade(CallbackInfo info) {
		if (prev.isPresent() && next.isPresent()) {
			if (prev.get().getId().equals(next.get().getId())) {
				info.cancel();
			}
		}
	}

	public static void stopSounds(Optional<SoundEvent> event, Object2ObjectArrayMap<Biome, MusicLoop> soundLoops) {
		next = event;
		isRecordPlaying = true;
		soundLoops.values().removeIf(MovingSoundInstance::isDone);
		soundLoops.values().forEach(BiomeEffectSoundPlayer.MusicLoop::fadeOut);
		prev = next;
	}

	public static void markPrev(Biome biome) {
		prev = biome.getLoopSound();
	}

	public static void markNext(Biome biome) {
		next = biome.getLoopSound();
	}

	public static void restartSounds(SoundManager manager, Biome biome, Object2ObjectArrayMap<Biome, MusicLoop> soundLoops) {
		isRecordPlaying = false;
		biome.getLoopSound().ifPresent(soundEvent -> soundLoops.compute(biome, (biomex, musicLoop) -> {
			if (musicLoop == null) {
				musicLoop = new BiomeEffectSoundPlayer.MusicLoop(soundEvent);
				manager.play(musicLoop);
			}

			musicLoop.fadeIn();
			return musicLoop;
		}));
	}

	public static void markClear() {
		prev = Optional.empty();
		next = Optional.empty();
	}

	public static Biome injectBiomeSoundDummies(ClientPlayerEntity player, BiomeAccess access, double x, double y, double z) {
		World world = player.getEntityWorld();

		if (world.getRegistryKey() == Dimensions.FRAIYA_WORLD) {

			if (world != null && !world.getEntitiesByType(Entities.KING_TATER, player.getBoundingBox().expand(40), $ -> true).isEmpty()) {
				return DummyBiomes.DUMMY_KING_TATER;
			}

			if (world != null && !world.getEntitiesByType(Entities.I509VCB, player.getBoundingBox().expand(40), $ -> true).isEmpty()) {
				return DummyBiomes.DUMMY_I509;
			}

			BlockPos playerPos = player.getBlockPos();
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
							return DummyBiomes.DUMMY_DUNGEON;
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

			Chunk chunk = world.getChunk(new BlockPos(x, y, z));
			int targetWS = chunk.sampleHeightmap(Heightmap.Type.WORLD_SURFACE, (int) x, (int) z) - 15;
			int targetMB = chunk.sampleHeightmap(Heightmap.Type.MOTION_BLOCKING, (int) x, (int) z) - 2;
			int checky = (int) y;

			if (checky < 40 || checky < player.getEntityWorld().getSeaLevel() && checky < targetWS && checky < targetMB) {
				return DummyBiomes.DUMMY_CAVERNS;
			}

			synchronized (SecurityProblemCauser.lock) {
				if (SecurityProblemCauser.townLocs != null) {
					int i = 0;

					for (Vec2i townLoc : SecurityProblemCauser.townLocs) {
						double dx = x - townLoc.getX();
						double dz = z - townLoc.getY();

						if (dx * dx + dz * dz < TownFeature.SUBURB_DIST) {
							return DummyBiomes.TOWNS[i];
						}

						i++;
					}
				}
			}

		}

		Biome biome = access.getBiomeForNoiseGen(x, y, z);

		int fg = biome.getEffects().getFogColor();

		if (fg == Biomes.MYSTICAL_FOG_COLOUR) { // Good ol' performance hax. This is probably faster than getting the registry and checking the key.
			if ((System.currentTimeMillis() & 0x80000) == 0) { // about an 8-9 minute delay
				return DummyBiomes.DUMMY_MYSTICAL_FOREST_ALTER;
			}
		} else if (fg == Biomes.CORRUPT_FOG_COLOUR) {
			if (((System.currentTimeMillis() + 0x40000) & 0x80000) == 0) { // about an 8-9 minute delay, offset by half of the mystical one
				return DummyBiomes.DUMMY_CORRUPT_FOREST_ALTER;
			}
		}

		return biome;
	}
}
