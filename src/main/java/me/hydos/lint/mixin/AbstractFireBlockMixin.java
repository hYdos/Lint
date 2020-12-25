package me.hydos.lint.mixin;

import net.minecraft.block.AbstractFireBlock;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(AbstractFireBlock.class)
public class AbstractFireBlockMixin {

	@Inject(method = "onBlockAdded", at = @At("HEAD"))
	private void checkForLintPortal(BlockState state, World world, BlockPos pos, BlockState oldState, boolean notify, CallbackInfo ci) {
		BlockPos frame = pos.down();
		Block frameBlock = Blocks.COAL_BLOCK;
		// Hardcoded for now yeef
		if(
				world.getBlockState(frame).getBlock() ==  frameBlock && //Bottom
				world.getBlockState(frame.up(3)).getBlock() ==  frameBlock && //Top
				world.getBlockState(frame.up(1).east()).getBlock() ==  frameBlock && //Left lower
				world.getBlockState(frame.up(2).east()).getBlock() ==  frameBlock && //Left higher
				world.getBlockState(frame.up(1).west()).getBlock() ==  frameBlock && //Right lower
				world.getBlockState(frame.up(2).west()).getBlock() ==  frameBlock //Right higher
		) {
			world.setBlockState(pos, me.hydos.lint.block.Blocks.HAYKAMIUM_PORTAL.getDefaultState());
			world.setBlockState(pos.up(), me.hydos.lint.block.Blocks.HAYKAMIUM_PORTAL.getDefaultState());
		}
	}
}
