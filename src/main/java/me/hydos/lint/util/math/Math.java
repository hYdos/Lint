package me.hydos.lint.util.math;

import me.hydos.lint.world.biome.Biomes;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.registry.Registry;
import net.minecraft.util.registry.RegistryKey;
import net.minecraft.world.World;
import net.minecraft.world.biome.Biome;

import java.util.Optional;

public class Math {
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
			} else if (aBiome == Biomes.THICK_MYSTICAL_FOREST_KEY || aBiome == Biomes.INDIGO_RIDGES_KEY) {
				distChunks = 0.5f * (3f + java.lang.Math.min(6.2f, 0.43f * originalResultChunks));
			} else if (aBiome == Biomes.MYSTICAL_FOREST_KEY || aBiome == Biomes.DAWN_SHARDLANDS_KEY) {
				distChunks = java.lang.Math.min(6.2f, 0.43f * originalResultChunks);
			} else if (aBiome == Biomes.DAWN_SHARDLANDS_EDGE_KEY) {
				distChunks = 0.69f * originalResultChunks;
			}
		}

		if (distChunks > originalResultChunks) {
			distChunks = originalResultChunks;
		}

		return distChunks;
	}
}
