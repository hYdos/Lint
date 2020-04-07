package me.hydos.lint.world.dimension.biomes;

import net.minecraft.block.BlockState;

public interface IBiomeHasLex {

    BlockState getGrass();
    BlockState getUnderDirt();
    BlockState getSand();
    BlockState getGravel();
}
