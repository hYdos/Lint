package me.hydos.lint.block;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;

public class StoneLikeBlock extends Block {
	StoneLikeBlock(Settings settings) {
		super(settings);
	}

	public static boolean isLintStone(Block block) {
		return block instanceof StoneLikeBlock;
	}

	public static boolean isLintStone(BlockState state) {
		return isLintStone(state.getBlock());
	}
}
