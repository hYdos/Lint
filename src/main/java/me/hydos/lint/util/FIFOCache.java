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

import java.util.Iterator;
import java.util.function.LongFunction;
import java.util.function.Predicate;

import org.apache.logging.log4j.core.util.ObjectArrayIterator;

/**
 * Simple First-In First-Out cache of items.
 * @author Valoeghese
 */
public class FIFOCache<T> implements Iterable<T> {
	public FIFOCache(T[] array) {
		this.capacity = array.length;
		this.array = array;
		this.positions = new long[array.length];
	}

	private final int capacity;
	private int size = 0;
	protected int currentIndex = 0;
	private final T[] array;
	private final long[] positions;

	public T add(long position, T item) {
		if (this.capacity > this.size) {
			this.size++;
		}

		this.array[this.currentIndex++] = item;

		if (this.currentIndex >= this.capacity) {
			this.currentIndex = 0;
		}

		return item;
	}

	public T getOrAdd(long position, Predicate<T> requires, LongFunction<T> constructor) {
		if (this.size > 0) {
			for (int i = 0; i < this.size; ++i) {
				long l = this.positions[i]; // take the L

				if (l == position) {
					T maybeResult = this.array[i];

					if (requires.test(maybeResult)) {
						return maybeResult;
					} else {
						continue;
					}
				}
			}
		}

		// if not exist then we get here
		return this.add(position, constructor.apply(position));
	}

	public boolean contains(T item) {
		for (int i = 0; i < this.size; ++i) {
			if (this.array[i].equals(item)) {
				return true;
			}
		}

		return false;
	}

	public void reset() {
		this.currentIndex = 0;
		this.size = 0;
	}

	public int getSize() {
		return this.size;
	}

	public void clear() {
		this.reset();

		for (int i = 0; i <= this.capacity; ++i) {
			this.array[i] = null;
		}
	}

	@Override
	public Iterator<T> iterator() {
		return new ObjectArrayIterator<>(this.array);
	}
}
