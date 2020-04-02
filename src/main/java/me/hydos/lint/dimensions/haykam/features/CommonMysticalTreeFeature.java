package me.hydos.lint.dimensions.haykam.features;

import com.mojang.datafixers.Dynamic;
import net.minecraft.util.math.BlockBox;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.ModifiableTestableWorld;
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

}
