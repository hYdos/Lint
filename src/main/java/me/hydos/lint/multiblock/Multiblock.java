package me.hydos.lint.multiblock;

import net.minecraft.client.util.math.Vector3f;
import net.minecraft.util.math.BlockPos;

import java.util.List;

public class Multiblock {

	public Vector3f minDimensions, maxDimensions;
	public List<BlockPos> blocks;

	public Multiblock(Vector3f minDimensions, Vector3f maxDimensions, List<BlockPos> blocks) {
		this.minDimensions = minDimensions;
		this.maxDimensions = maxDimensions;
		this.blocks = blocks;
	}
}
