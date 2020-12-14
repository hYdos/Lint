package io.github.hydos.lint.world.feature;

import java.util.Random;

import io.github.hydos.lint.resource.block.LintBlocks;
import io.github.hydos.lint.util.OpenSimplexNoise;
import io.github.hydos.lint.util.Voronoi;
import net.minecraft.block.BlockState;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.Heightmap;
import net.minecraft.world.StructureWorldAccess;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.chunk.Chunk;
import net.minecraft.world.gen.feature.DefaultBiomeFeatures;
import net.minecraft.world.gen.surfacebuilder.SurfaceConfig;

public class FloatingIslandModifier {
	public FloatingIslandModifier(long seed) {
		this.noise = new OpenSimplexNoise(new Random(seed + 3));
		// int seed
		int protoSeed = (int) (seed >> 32);
		this.seed = protoSeed == 0 ? 1 : protoSeed; // 0 bad and worst in game
	}

	private final OpenSimplexNoise noise;
	private final int seed;

	public void generate(StructureWorldAccess world, Random random, int startX, int startZ) {
		BlockPos.Mutable pos = new BlockPos.Mutable();
		Chunk chunk = world.getChunk(new BlockPos(startX, 0, startZ));
		Heightmap wsWG = chunk.getHeightmap(Heightmap.Type.WORLD_SURFACE_WG);
		Heightmap ofWG = chunk.getHeightmap(Heightmap.Type.OCEAN_FLOOR_WG);
		Heightmap mb = chunk.getHeightmap(Heightmap.Type.MOTION_BLOCKING);

		for (int xo = 0; xo < 16; ++xo) {
			int x = xo + startX;
			pos.setX(x);

			for (int zo = 0; zo < 16; ++zo) {
				int z = zo + startZ;
				double floatingIsland = Voronoi.sampleFloating(x * 0.04, z * 0.04, this.seed, this.noise);

				if (floatingIsland > 0.0) {
					pos.setZ(z);
					double height = 150 + 10 * this.noise.sample(x * 0.02, z * 0.02);
					double depth = height - 25 * (0.5 + Math.abs(this.noise.sample(5 + x * 0.012, z * 0.012))) * floatingIsland;

					int iHeight = (int) height;
					int finalY = iHeight - 1;
					int fillY = iHeight - (random.nextBoolean() ? 4 : 5);
					int iDepth = (int) depth;

					if (iDepth < iHeight) {
						Biome biome = world.getBiome(pos.up(64));// just in case 3d biomes in 1.17
						SurfaceConfig surface = biome.getGenerationSettings().getSurfaceConfig();
						BlockState filler = surface.getUnderMaterial();
						BlockState top = surface.getTopMaterial();

						for (int y = iDepth; y < iHeight; ++y) {
							pos.setY(y);
							
							if (y == finalY) {
								world.setBlockState(pos, top, 3);
								wsWG.trackUpdate(xo, y, zo, top); // the top block will never be air so always updates
								ofWG.trackUpdate(xo, y, zo, top); // I don't think any top blocks fail the check here either
								mb.trackUpdate(xo, y, zo, top);
							} else if (y > fillY) {
								world.setBlockState(pos, filler, 3);
							} else {
								world.setBlockState(pos, FUSED_STONE, 3);
							}
						}
					}
				}
			}
		}
	}

	private static final BlockState FUSED_STONE = LintBlocks.FUSED_STONE.getDefaultState();
}
