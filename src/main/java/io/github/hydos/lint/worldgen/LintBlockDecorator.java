package io.github.hydos.lint.worldgen;

import net.minecraft.block.BlockState;

public interface LintBlockDecorator {
    BlockState getGrass();
    BlockState getUnderDirt();
    BlockState getSand();
    BlockState getGravel();
}
