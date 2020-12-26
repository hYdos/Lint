package me.hydos.lint.world.feature;

import com.google.common.collect.ImmutableList;
import me.hydos.lint.block.Blocks;
import net.minecraft.block.Block;
import net.minecraft.util.math.BlockBox;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.ModifiableTestableWorld;
import net.minecraft.world.TestableWorld;
import net.minecraft.world.gen.feature.Feature;
import net.minecraft.world.gen.feature.TreeFeature;
import net.minecraft.world.gen.feature.TreeFeatureConfig;
import net.minecraft.world.gen.foliage.FoliagePlacer;
import net.minecraft.world.gen.trunk.TrunkPlacer;
import net.minecraft.world.gen.trunk.TrunkPlacerType;

import java.util.List;
import java.util.Random;
import java.util.Set;

public class LintTrunkPlacer extends TrunkPlacer {

    public LintTrunkPlacer(int i, int j, int k) {
        super(i, j, k);
    }

    protected TrunkPlacerType<?> getType() {
        return TrunkPlacerType.STRAIGHT_TRUNK_PLACER;
    }

    public List<FoliagePlacer.TreeNode> generate(ModifiableTestableWorld world, Random random, int trunkHeight, BlockPos pos, Set<BlockPos> set, BlockBox blockBox, TreeFeatureConfig treeFeatureConfig) {
        setToDirt(world, pos.down());

        for (int i = 0; i < trunkHeight; ++i) {
            getAndSetState(world, random, pos.up(i), set, blockBox, treeFeatureConfig);
        }

        return ImmutableList.of(new FoliagePlacer.TreeNode(pos.up(trunkHeight), 0, false));
    }

    private static boolean canGenerate(TestableWorld world, BlockPos pos) {
        return world.testBlockState(pos, (state) -> {
            Block block = state.getBlock();
            return Feature.isSoil(block) && !state.isOf(net.minecraft.block.Blocks.GRASS_BLOCK) && !state.isOf(net.minecraft.block.Blocks.MYCELIUM);
        });
    }

    protected static void setToDirt(ModifiableTestableWorld world, BlockPos pos) {
        if (canGenerate(world, pos)) {
            TreeFeature.setBlockStateWithoutUpdatingNeighbors(world, pos, Blocks.RICH_DIRT.getDefaultState());
        }
    }
}
