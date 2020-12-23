package io.github.hydos.lint.block;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.block.BlockState;
import net.minecraft.block.LeavesBlock;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import java.util.Random;

public class LintLeavesBlock extends LeavesBlock {

    public LintLeavesBlock(Settings settings) {
        super(settings);
    }

    @Environment(EnvType.CLIENT)
    @Override
    public void randomDisplayTick(BlockState state, World world, BlockPos pos, Random random) {
        super.randomDisplayTick(state, world, pos, random);
        if (random.nextInt(15) == 1) {
            double d = (double) pos.getX() + random.nextInt(1);
            double e = (double) pos.getY() + random.nextInt(1);
            double f = (double) pos.getZ() + random.nextInt(1);
            world.addParticle(ParticleTypes.SMOKE, d, e, f, 0.0D, -0.2D, 0.0D);
        }
    }
}
