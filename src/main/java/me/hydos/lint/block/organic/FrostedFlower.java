package me.hydos.lint.block.organic;

import me.hydos.lint.block.LintBlocks;
import net.minecraft.block.BlockState;
import net.minecraft.entity.effect.StatusEffect;

public class FrostedFlower extends LintFlowerBlock {
	public FrostedFlower(StatusEffect effect, Settings settings) {
		super(effect, settings);
	}

	@Override
	public BlockState getGrowsOn() {
		return LintBlocks.FROSTED_GRASS.getDefaultState();
	}
}
