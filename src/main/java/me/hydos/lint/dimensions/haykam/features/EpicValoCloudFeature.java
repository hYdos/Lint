package me.hydos.lint.dimensions.haykam.features;

import me.hydos.lint.util.OpenSimplexNoise;
import net.minecraft.block.Blocks;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IWorld;
import net.minecraft.world.gen.chunk.ChunkGenerator;
import net.minecraft.world.gen.chunk.ChunkGeneratorConfig;
import net.minecraft.world.gen.feature.DefaultFeatureConfig;
import net.minecraft.world.gen.feature.Feature;

import java.util.Random;

public class EpicValoCloudFeature extends Feature<DefaultFeatureConfig> {

    private OpenSimplexNoise noise;

    private long seedCache = 0;

    public EpicValoCloudFeature() {
        super(DefaultFeatureConfig::deserialize);
    }

    /*
        TODO: e e e e e e e e e e e e e e e e e e e e e e e e e e e e e e e e e e e e e e e e e e e e e e e e e e e e e
        TODO: e e e e e e e e e e e e e e e e e e e e e e e e e e e e e e e e e e e e e e e e e e e e e e e e e e e e e
        TODO: e e e e e e e e e e e e e e e e e e e e e e e e e e e e e e e e e e e e e e e e e e e e e e e e e e e e e
        TODO: e e e e e e e e e e e e e e e e e e e e e e V A L O e e e e e e e e e e e e e e e e e e e e e e e e e e e
        TODO: e e e e e e e e e e e e e e e e e e e e e e e e e e e e e e e e e e e e e e e e e e e e e e e e e e e e e
        TODO: e e e e e e e e e e e e e e e e e e e e e e e e e e e e e e e e e e e e e e e e e e e e e e e e e e e e e
        TODO: e e e e e e e e e e e e e e e e e e e e e e e e e e e e e e e e e e e e e e e e e e e e e e e e e e e e e
        TODO: e e e e e e e e e e e e e e e e e e e e e e e e e e e e e e e e e e e e e e e e e e e e e e e e e e e e e
     */

    @Override
    public boolean generate(IWorld world, ChunkGenerator<? extends ChunkGeneratorConfig> generator, Random random, BlockPos pos, DefaultFeatureConfig config) {
        long seed = world.getSeed();
        if (seed != this.seedCache || this.noise == null) {
            this.noise = new OpenSimplexNoise(new Random(seed));
            this.seedCache = seed;
        }

        int averageY = 130;

        int x = pos.getX();
        int z = pos.getZ();
        BlockPos.Mutable setPos = new BlockPos.Mutable();

        final double frequency = 0.06;
        final double rarity = 0.2;
        final int amplitude = 5;

        for (int xo = 0; xo < 16; ++xo) {
            int totalX = xo + x;
            setPos.setX(totalX);
            for (int zo = 0; zo < 16; ++zo) {
                int totalZ = zo + z;
                setPos.setZ(totalZ);

                int sample = (int) (amplitude * (this.noise.sample(totalX * frequency, totalZ * frequency) - rarity));

                if (sample > 0) {
                    for (int yo = -sample + 1; yo < sample; ++yo) {
                        setPos.setY(averageY + yo);
                        this.setBlockState(world, setPos, Blocks.WHITE_WOOL.getDefaultState());
                    }
                }
            }
        }

        return true;
    }
}
