package me.hydos.lint.mixinimpl;

import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class LintPortal {
	public static void resolve(World world, BlockPos firePos) {
		final int size = 3;
		
		final int fireX = firePos.getX();
		final int fireZ = firePos.getZ();
		BlockPos.Mutable pos = new BlockPos.Mutable().set(firePos);

		int widthP = 0; // the amount of "gap" on +x
		int widthN = 0; // the amount of "gap" on -x
		int breadthP = 0; // the amount of "gap" on +z
		int breadthN = 0; // the amount of "gap" on -z

		// find encapsulation of portal.
		for (int xo = 1; xo <= size; ++xo) {
			if (xo == size) {
				return;
			}

			pos.setX(fireX + xo);

			if (world.getBlockState(pos) == FRAME) {
				break;
			}

			widthP++;
		}
		
		for (int xo = 1; xo <= size; ++xo) {
			if (xo == size) {
				return;
			}

			pos.setX(fireX - xo);

			if (world.getBlockState(pos) == FRAME) {
				break;
			}

			widthN++;
		}
		
		if (1 + widthP + widthN != size) {
			return;
		}

		pos.setX(fireX);

		for (int zo = 1; zo <= size; ++zo) {
			if (zo == size) {
				return;
			}
			
			pos.setZ(fireZ + zo);

			if (world.getBlockState(pos) == FRAME) {
				break;
			}
			
			breadthP++;
		}
		
		for (int zo = 1; zo <= size; ++zo) {
			if (zo == size) {
				return;
			}
			
			pos.setZ(fireZ - zo);

			if (world.getBlockState(pos) == FRAME) {
				break;
			}
			
			breadthN++;
		}
		
		if (1 + breadthP + breadthN != size) {
			return;
		}
		
		world.setBlockState(firePos, Blocks.ACTIVATOR_RAIL.getDefaultState());
	}

	public static final BlockState FRAME = Blocks.COAL_BLOCK.getDefaultState();
}
