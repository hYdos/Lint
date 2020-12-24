package me.hydos.lint.block;

import me.hydos.lint.block.Blocks;
import net.minecraft.block.BlockState;
import net.minecraft.block.FlowerBlock;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.BlockView;

public class LintGrassBlock extends FlowerBlock {

    public LintGrassBlock(StatusEffect effect, Settings settings) {
        super(effect, 7, settings);
    }

    @Override
    protected boolean canPlantOnTop(BlockState floor, BlockView view, BlockPos pos) {
        return floor == Blocks.LIVELY_GRASS.getDefaultState();
    }
}