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

import org.apache.logging.log4j.core.util.ObjectArrayIterator;

public class FIFOCache<T> implements Iterable<T> {
	public FIFOCache(T[] array) {
		this.capacity = array.length;
		this.array = array;
	}

	private final int capacity;
	private int size = 0;
	protected int currentIndex = 0;
	private final T[] array;
	
	public void add(T item) {
		if (this.capacity > this.size) {
			this.size++;
		}

		this.array[this.currentIndex++] = item;

		if (this.currentIndex >= this.capacity) {
			this.currentIndex = 0;
		}
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
