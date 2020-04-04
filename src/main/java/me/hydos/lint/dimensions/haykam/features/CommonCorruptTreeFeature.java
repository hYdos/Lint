package me.hydos.lint.dimensions.haykam.features;

import com.mojang.datafixers.Dynamic;
import me.hydos.lint.core.Blocks;
import net.minecraft.block.Block;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.Heightmap;
import net.minecraft.world.ModifiableTestableWorld;
import net.minecraft.world.TestableWorld;
import net.minecraft.world.gen.feature.BranchedTreeFeatureConfig;
import net.minecraft.world.gen.feature.OakTreeFeature;

import java.util.Optional;
import java.util.function.Function;

public class CommonCorruptTreeFeature extends OakTreeFeature {

    public CommonCorruptTreeFeature(Function<Dynamic<?>, ? extends BranchedTreeFeatureConfig> function) {
        super(function);
    }

    public static boolean isDirtOrGrass(TestableWorld world, BlockPos pos) {
        return world.testBlockState(pos, (blockState) -> {
            Block block = blockState.getBlock();
            return isDirt(block) || block == net.minecraft.block.Blocks.FARMLAND;
        });
    }

    protected static boolean isDirt(Block block) {
        return block == Blocks.RICH_DIRT || block == Blocks.CORRUPT_GRASS;
    }

    @Override
    protected void setToDirt(ModifiableTestableWorld world, BlockPos pos) {
        this.setBlockState(world, pos, Blocks.RICH_DIRT.getDefaultState());
    }

    @Override
    public Optional<BlockPos> findPositionToGenerate(ModifiableTestableWorld world, int height, int i, int j, BlockPos pos, BranchedTreeFeatureConfig config) {
        BlockPos blockPos2;
        int m;
        int n;
        if (!config.field_21593) {
            m = world.getTopPosition(Heightmap.Type.OCEAN_FLOOR, pos).getY();
            n = world.getTopPosition(Heightmap.Type.WORLD_SURFACE, pos).getY();
            blockPos2 = new BlockPos(pos.getX(), m, pos.getZ());
            if (n - m > config.maxWaterDepth) {
                return Optional.empty();
            }
        } else {
            blockPos2 = pos;
        }

        if (blockPos2.getY() >= 1 && blockPos2.getY() + height + 1 <= 256) {
            for (m = 0; m <= height + 1; ++m) {
                n = config.foliagePlacer.method_23447(i, height, j, m);
                BlockPos.Mutable mutable = new BlockPos.Mutable();

                for (int o = -n; o <= n; ++o) {
                    int p = -n;

                    while (p <= n) {
                        if (m + blockPos2.getY() >= 0 && m + blockPos2.getY() < 256) {
                            mutable.set(o + blockPos2.getX(), m + blockPos2.getY(), p + blockPos2.getZ());
                            if (canTreeReplace(world, mutable) && (config.noVines || !isLeaves(world, mutable))) {
                                ++p;
                                continue;
                            }

                            return Optional.empty();
                        }

                        return Optional.empty();
                    }
                }
            }

            if (isDirtOrGrass(world, blockPos2.down()) && blockPos2.getY() < 256 - height - 1) {
                return Optional.of(blockPos2);
            } else {
                return Optional.empty();
            }
        } else {
            return Optional.empty();
        }
    }
}
