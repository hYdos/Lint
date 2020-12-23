package io.github.hydos.lint.util;

import java.util.Arrays;

public class LossyDoubleCache implements DoubleGridOperator {
	public LossyDoubleCache(int size, DoubleGridOperator operator) {
		int arrSize = 1; // 2^n = 2 * (2^(n-1))
		int nextArrSize;

		while (true) {
			if ((nextArrSize = (arrSize << 1)) > size) {
				break;
			}

			arrSize = nextArrSize;
		}

		if (arrSize > size) {
			throw new RuntimeException("LossyCache " + arrSize + " must be smaller or equal to given Cache size! (" + size + ")");
		}

		this.mask = arrSize - 1;
		this.positions = new long[arrSize];
		this.data = new double[arrSize];
		this.operator = operator;

		Arrays.fill(this.positions, Long.MAX_VALUE);
	}

	private final int mask;
	private final long[] positions;
	private final double[] data;
	private final DoubleGridOperator operator;

	@Override
	public double get(int x, int y) {
		try {
			long pos = asLong(x, y);
			int loc = mix5(x, y) & this.mask;

			if (this.positions[loc] != pos) {
				this.positions[loc] = pos;
				return this.data[loc] = this.operator.get(x, y);
			} else {
				return this.data[loc];
			}
		} catch (Exception e) {
			e.printStackTrace();
			System.err.println("LossyCache broke! You'll need to restart your game (or perhaps just reload the world), sadly. If this issue persists, let me (Valoeghese) know!");
			throw new RuntimeException(e);
		}
	}

	private static int mix5(int a, int b) {
		return (((a >> 4) & 1) << 9) |
				(((b >> 4) & 1) << 8) |
				(((a >> 3) & 1) << 7) |
				(((b >> 3) & 1) << 6) |
				(((a >> 2) & 1) << 5) |
				(((b >> 2) & 1) << 4) |
				(((a >> 1) & 1) << 3) |
				(((b >> 1) & 1) << 2) |
				((a & 1) << 1) |
				(b & 1);
	}

	// from minecraft
	public static long asLong(int chunkX, int chunkZ) {
		return (long)chunkX & 4294967295L | ((long)chunkZ & 4294967295L) << 32;
	}
}
