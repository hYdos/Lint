package me.hydos.lint.dimensions.haykam.features;

import com.mojang.datafixers.Dynamic;
import me.hydos.lint.core.Blocks;
import net.minecraft.block.Block;
import net.minecraft.util.math.BlockBox;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.Heightmap;
import net.minecraft.world.ModifiableTestableWorld;
import net.minecraft.world.TestableWorld;
import net.minecraft.world.gen.feature.BranchedTreeFeatureConfig;
import net.minecraft.world.gen.feature.OakTreeFeature;

import java.util.Optional;
import java.util.Random;
import java.util.Set;
import java.util.function.Function;

public class CommonMysticalTreeFeature extends OakTreeFeature {

    public CommonMysticalTreeFeature(Function<Dynamic<?>, ? extends BranchedTreeFeatureConfig> function) {
        super(function);
    }

    @Override
    public boolean generate(ModifiableTestableWorld modifiableTestableWorld, Random random, BlockPos blockPos, Set<BlockPos> set, Set<BlockPos> set2, BlockBox blockBox, BranchedTreeFeatureConfig branchedTreeFeatureConfig) {
        int i = branchedTreeFeatureConfig.baseHeight + random.nextInt(branchedTreeFeatureConfig.heightRandA + 1) + random.nextInt(branchedTreeFeatureConfig.heightRandB + 1);
        int j = branchedTreeFeatureConfig.trunkHeight >= 0 ? branchedTreeFeatureConfig.trunkHeight + random.nextInt(branchedTreeFeatureConfig.trunkHeightRandom + 1) : i - (branchedTreeFeatureConfig.foliageHeight + random.nextInt(branchedTreeFeatureConfig.foliageHeightRandom + 1));
        int k = branchedTreeFeatureConfig.foliagePlacer.getRadius(random, j, i, branchedTreeFeatureConfig);
        Optional<BlockPos> optional = this.findPositionToGenerate(modifiableTestableWorld, i, j, k, blockPos, branchedTreeFeatureConfig);
        if (!optional.isPresent()) {
            return false;
        } else {
            BlockPos blockPos2 = optional.get();
            this.setToDirt(modifiableTestableWorld, blockPos2.down());
            branchedTreeFeatureConfig.foliagePlacer.generate(modifiableTestableWorld, random, branchedTreeFeatureConfig, i, j, k, blockPos2, set2);
            this.generate(modifiableTestableWorld, random, i, blockPos2, branchedTreeFeatureConfig.trunkTopOffset + random.nextInt(branchedTreeFeatureConfig.trunkTopOffsetRandom + 1), set, blockBox, branchedTreeFeatureConfig);
            return true;
        }
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
            for(m = 0; m <= height + 1; ++m) {
                n = config.foliagePlacer.method_23447(i, height, j, m);
                BlockPos.Mutable mutable = new BlockPos.Mutable();

                for(int o = -n; o <= n; ++o) {
                    int p = -n;

                    while(p <= n) {
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

            if (canSpawnTree(world, blockPos2.down()) && blockPos2.getY() < 256 - height - 1) {
                return Optional.of(blockPos2);
            } else {
                return Optional.empty();
            }
        } else {
            return Optional.empty();
        }
    }

    protected void setToDirt(ModifiableTestableWorld world, BlockPos pos) {
        if (!isNaturalDirt(world, pos)) {
            this.setBlockState(world, pos, Blocks.LIVELY_GRASS.getDefaultState());
        }

    }

    protected static boolean canSpawnTree(TestableWorld world, BlockPos pos) {
        return world.testBlockState(pos, (blockState) -> {
            Block block = blockState.getBlock();
            return Blocks.LIVELY_GRASS.getDefaultState() == block.getDefaultState();
        });
    }

}
