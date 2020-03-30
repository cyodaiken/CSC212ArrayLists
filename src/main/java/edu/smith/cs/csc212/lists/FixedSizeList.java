package edu.smith.cs.csc212.lists;

import me.jjfoley.adt.ArrayWrapper;
import me.jjfoley.adt.ListADT;
import me.jjfoley.adt.errors.RanOutOfSpaceError;
import me.jjfoley.adt.errors.TODOErr;

/**
 * FixedSizeList is a List with a maximum size.
 * @author jfoley
 *
 * @param <T>
 */
public class FixedSizeList<T> extends ListADT<T> {
	/**
	 * This is the array of fixed size.
	 */
	private ArrayWrapper<T> array;
	/**
	 * This keeps track of what we have used and what is left.
	 */
	private int fill;

	/**
	 * Construct a new FixedSizeList with a given maximum size.
	 * @param maximumSize - the size of the array to use.
	 */
	public FixedSizeList(int maximumSize) {
		this.array = new ArrayWrapper<>(maximumSize);
		this.fill = 0;

	}

	@Override
	public boolean isEmpty() {
		return this.fill == 0;
	}

	@Override
	public int size() {
		return this.fill;
	}

	@Override
	public void setIndex(int index, T value) {
		checkNotEmpty();
		this.checkExclusiveIndex(index);
		this.array.setIndex(index, value);
	}

	@Override
	public T getIndex(int index) {
		checkNotEmpty();
		this.checkExclusiveIndex(index);
		return this.array.getIndex(index);
	}

	@Override
	public T getFront() {

		this.checkNotEmpty();

		array.getIndex(0);
		return array.getIndex(0);
	}

	@Override
	public T getBack() {

		this.checkNotEmpty();

		array.getIndex(array.size()-1);
		return array.getIndex(array.size()-1);
	}

	@Override
	public void addIndex(int index, T value) {
		
		this.checkInclusiveIndex(index);

		if(this.fill + 1 > this.array.size()) {	
			throw new RanOutOfSpaceError();
		} else {
			// slide to the right
			for (int i = this.fill; i > index; i--) {
				//System.out.println("getindex i-1="+(i-1));
				//System.out.println("getindex="+getIndex((i-1)));
				array.setIndex(i, array.getIndex(i-1));
				
				//System.out.println("ARRAY:" + this.array);
			}
			array.setIndex(index, value);
			fill += 1;
			System.out.println("FINAL ARRAY: " + this.array);
		}
	}

	@Override
	public void addFront(T value) {
		this.addIndex(0, value);
	}

	@Override
	public void addBack(T value) {
		if (fill < array.size()) {
			array.setIndex(fill++, value);
		} else {
			throw new RanOutOfSpaceError();
		}
	}

	@Override
	public T removeIndex(int index) {
		// slide to the left
		this.checkNotEmpty();
		T item = array.getIndex(index);
		for (int i = index; i < array.size()-1; i++) {
			array.setIndex(i, array.getIndex(i+1));
		}
		array.setIndex(array.size()-1, null);
		fill -= 1;
		return item;
	}

	@Override
	public T removeBack() {
		this.checkNotEmpty();
		return removeIndex(this.size() - 1);
	}

	@Override
	public T removeFront() {
		this.checkNotEmpty();
		return removeIndex(0);
	}

	/**
	 * Is this data structure full? Used in challenge: {@linkplain ChunkyArrayList}.
	 * 
	 * @return if true this FixedSizeList is full.
	 */
	public boolean isFull() {
		return this.fill == this.array.size();
	}

}
