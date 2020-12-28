package me.hydos.lint.mixinimpl;

import me.hydos.lint.block.LintBlocks;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.tag.BlockTags;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class LintPortal {
	public static void resolve(World world, BlockPos firePos) {
		if (world.getRegistryKey() != World.OVERWORLD) {
			return;
		}

		final int size = 3;
		final int searchSize = 4;

		final int fireX = firePos.getX();
		final int fireZ = firePos.getZ();
		BlockPos.Mutable pos = new BlockPos.Mutable().set(firePos);

		int widthP = 0; // the amount of "gap" on +x
		int widthN = 0; // the amount of "gap" on -x
		int breadthP = 0; // the amount of "gap" on +z
		int breadthN = 0; // the amount of "gap" on -z

		// find encapsulation of portal.
		for (int xo = 1; xo <= searchSize; ++xo) {
			if (xo == searchSize) {
				return;
			}

			pos.setX(fireX + xo);

			if (world.getBlockState(pos) == FRAME) {
				break;
			}

			widthP++;
		}

		for (int xo = 1; xo <= searchSize; ++xo) {
			if (xo == searchSize) {
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

		for (int zo = 1; zo <= searchSize; ++zo) {
			if (zo == searchSize) {
				return;
			}

			pos.setZ(fireZ + zo);

			if (world.getBlockState(pos) == FRAME) {
				break;
			}

			breadthP++;
		}

		for (int zo = 1; zo <= searchSize; ++zo) {
			if (zo == searchSize) {
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

		// Check Edges
		pos.setZ(fireZ + breadthP + 1);

		for (int xo = -widthN; xo <= widthP; ++xo) {
			pos.setX(fireX + xo);

			if (world.getBlockState(pos) != FRAME) {
				return;
			}
		}

		pos.setZ(fireZ - breadthN - 1);

		for (int xo = -widthN; xo <= widthP; ++xo) {
			pos.setX(fireX + xo);

			if (world.getBlockState(pos) != FRAME) {
				return;
			}
		}

		pos.setX(fireX + widthP + 1);

		for (int zo = -breadthN; zo <= breadthP; ++zo) {
			pos.setZ(fireZ + zo);

			if (world.getBlockState(pos) != FRAME) {
				return;
			}
		}

		pos.setX(fireX - widthN - 1);

		for (int zo = -breadthN; zo <= breadthP; ++zo) {
			pos.setZ(fireZ + zo);

			if (world.getBlockState(pos) != FRAME) {
				return;
			}
		}

		// check air space for portal
		for (int xo = -widthN; xo <= widthP; ++xo) {
			pos.setX(fireX + xo);

			for (int zo = -breadthN; zo <= breadthP; ++zo) {
				pos.setZ(fireZ + zo);

				if (xo != 0 || zo != 0) {
					if (!world.getBlockState(pos).isAir()) {
						return;
					}
				}
			}
		}

		final int fireY = pos.getY();
		pos.setY(fireY - 1);

		// test floor is logs
		for (int xo = -widthN; xo <= widthP; ++xo) {
			pos.setX(fireX + xo);

			for (int zo = -breadthN; zo <= breadthP; ++zo) {
				pos.setZ(fireZ + zo);

				if (!BlockTags.LOGS.contains(world.getBlockState(pos).getBlock())) {
					return;
				}
			}
		}

		pos.setY(fireY);

		// place portal
		for (int xo = -widthN; xo <= widthP; ++xo) {
			pos.setX(fireX + xo);

			for (int zo = -breadthN; zo <= breadthP; ++zo) {
				pos.setZ(fireZ + zo);

				world.setBlockState(pos, LintBlocks.HAYKAMIUM_PORTAL.getDefaultState());
			}
		}
	}

	public static final BlockState FRAME = Blocks.COAL_BLOCK.getDefaultState();
}
