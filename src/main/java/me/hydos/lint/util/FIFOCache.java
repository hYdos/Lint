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
