/*
 * Lint
 * Copyright (C) 2020 hYdos, Valoeghese, ramidzkh
 *
 * This program is free software; you can redistribute it and/or
 * modify it under the terms of the GNU Lesser General Public
 * License as published by the Free Software Foundation; either
 * version 3 of the License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 * Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with this program; if not, write to the Free Software Foundation,
 * Inc., 51 Franklin Street, Fifth Floor, Boston, MA  02110-1301, USA.
 */

package me.hydos.lint.util;

import me.hydos.lint.util.math.IntGridOperator;

import java.util.Arrays;

public class LossyIntCache implements IntGridOperator {
    private final int mask;
    private final long[] positions;
    private final int[] data;
    private final IntGridOperator operator;

    public LossyIntCache(int size, IntGridOperator operator) {
        int arrSize = 1; // 2^n = 2 * (2^(n-1))
        int nextArrSize;

        while ((nextArrSize = (arrSize << 1)) <= size) {
            arrSize = nextArrSize;
        }

        if (arrSize > size) {
            throw new RuntimeException("LossyCache " + arrSize + " must be smaller or equal to given Cache size! (" + size + ")");
        }

        this.mask = arrSize - 1;
        this.positions = new long[arrSize];
        this.data = new int[arrSize];
        this.operator = operator;

        Arrays.fill(this.positions, Long.MAX_VALUE);
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
        return (long) chunkX & 4294967295L | ((long) chunkZ & 4294967295L) << 32;
    }

    @Override
    public int get(int x, int y) {
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
}
