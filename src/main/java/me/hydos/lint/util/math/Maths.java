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

package me.hydos.lint.util.math;

import java.util.Optional;

import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import me.hydos.lint.mixinimpl.LintSky;
import me.hydos.lint.world.biome.Biomes;
import me.hydos.lint.world.dimension.Dimensions;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.registry.Registry;
import net.minecraft.util.registry.RegistryKey;
import net.minecraft.world.World;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.source.BiomeAccess;

public class Maths {
	public static float calculateFogDistanceChunks(World world, double x, double z, float originalResultChunks) {
		int xi = MathHelper.floor(x);
		int zi = MathHelper.floor(z);
		int xl = (xi >> 4) << 4;
		int zl = (zi >> 4) << 4;
		int xh = xl + 16;
		int zh = zl + 16;

		float xProgress = (float) (x - (double) xl);
		xProgress /= 16.0f;
		float zProgress = (float) (z - (double) zl);
		zProgress /= 16.0f;

		return MathHelper.lerp(xProgress,
				MathHelper.lerp(zProgress, getFDC(world, xl, zl, originalResultChunks), getFDC(world, xl, zh, originalResultChunks)),
				MathHelper.lerp(zProgress, getFDC(world, xh, zl, originalResultChunks), getFDC(world, xh, zh, originalResultChunks)));
	}

	private static float getFDC(World world, int x, int z, float originalResultChunks) {
		//if (true) return originalResultChunks;
		Optional<RegistryKey<Biome>> biome = world.getRegistryManager().get(Registry.BIOME_KEY).getKey(world.getBiome(new BlockPos(x, 64, z))); // get biome
		float distChunks = originalResultChunks;

		if (biome.isPresent()) {
			RegistryKey<Biome> aBiome = biome.get();
			// Want an equal experience for all players, so control it directly.
			if (aBiome == Biomes.CORRUPT_FOREST_KEY) {
				distChunks = 3f;
			} else if (aBiome == Biomes.MYSTICAL_FOREST_KEY || aBiome == Biomes.INDIGO_RIDGES_KEY || aBiome == Biomes.ETHEREAL_WOODLAND_KEY) {
				distChunks = 0.5f * (3f + 9.2f);
			} else if (aBiome == Biomes.MYSTICAL_GROVE_KEY || aBiome == Biomes.DAWN_SHARDLANDS_KEY) {
				distChunks = 9.2f;
			} else if (aBiome == Biomes.DAWN_SHARDLANDS_EDGE_KEY) {
				distChunks = 0.69f * originalResultChunks;
			}
		}

		if (distChunks > originalResultChunks) {
			distChunks = originalResultChunks;
		}

		return distChunks;
	}

	public static int manhattan(int x, int y, int x1, int y1) {
		int dx = MathHelper.abs(x1 - x);
		int dy = MathHelper.abs(y1 - y);
		return dx + dy;
	}

	public static void onRetrieveModifiedFogColour(ClientWorld world, BiomeAccess access, float skyAngleThing,
			int noiseGenX, int noiseGenY, int noiseGenZ, CallbackInfoReturnable<Vec3d> info) {
		if (world.getRegistryKey() == Dimensions.FRAIYA_WORLD) {
			if (noiseGenY < (50 >> 2)) {
				info.setReturnValue(Vec3d.unpackRgb(Biomes.CAVERN_FOG_COLOUR));
			} else {
				Vec3d fogColour = Vec3d.unpackRgb(access.getBiomeForNoiseGen(noiseGenX, noiseGenY, noiseGenZ).getFogColor());
				fogColour = LintSky.adjustFogColor(fogColour, skyAngleThing);
				info.setReturnValue(fogColour);
			}
		}
	}
}
