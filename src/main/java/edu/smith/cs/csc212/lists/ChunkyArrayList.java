package edu.smith.cs.csc212.lists;

import me.jjfoley.adt.ListADT;
import me.jjfoley.adt.errors.BadIndexError;
import me.jjfoley.adt.errors.EmptyListError;
import me.jjfoley.adt.errors.TODOErr;

/**
 * This is a data structure that has an array inside each node of an ArrayList.
 * Therefore, we only make new nodes when they are full. Some remove operations
 * may be easier if you allow "chunks" to be partially filled.
 * 
 * @author jfoley
 * @param <T> - the type of item stored in the list.
 */
public class ChunkyArrayList<T> extends ListADT<T> {
	/**
	 * How big is each chunk?
	 */
	private int chunkSize;
	/**
	 * Where do the chunks go?
	 */
	private GrowableList<FixedSizeList<T>> chunks;

	/**
	 * Create a ChunkedArrayList with a specific chunk-size.
	 * @param chunkSize - how many items to store per node in this list.
	 */
	public ChunkyArrayList(int chunkSize) {
		this.chunkSize = chunkSize;
		chunks = new GrowableList<>();
	}

	private FixedSizeList<T> makeChunk() {
		return new FixedSizeList<>(chunkSize);
	}

	@Override
	public T removeFront() {

		this.checkNotEmpty();

		FixedSizeList<T> front = chunks.getFront();
		T value = front.removeFront();
		if (front.isEmpty()) {
			chunks.removeFront();
		}
		return value;
	}

	@Override
	public T removeBack() {

		this.checkNotEmpty();

		FixedSizeList<T> back = chunks.getBack();
		T value = back.removeBack();
		if (back.isEmpty()) {
			chunks.removeBack();
		}
		return value;
	}

	@Override
	public T removeIndex(int index) {

		this.checkNotEmpty();

		T value = null; 
		int counter = -1; 
		int chunkIndex = -1;
		for (FixedSizeList<T> chunk : this.chunks) {
			chunkIndex++;
			for( int i = 0; i < chunkSize; i++) {
				if ( i < chunk.size()) { 
					counter++; 
					if (index == counter ) {
						value = chunk.removeIndex(i);
						if (chunk.isEmpty()) {
							chunks.removeIndex(chunkIndex);
						}
					}

				}
			}
		}
		return value;
	}

	@Override
	public void addFront(T item) {

		if (chunks.isEmpty()) {
			chunks.addBack(makeChunk());
		}
		// get first chunk 
		FixedSizeList<T> front = chunks.getFront();
		// no space
		if (front.isFull()) {
			front = makeChunk();
			chunks.addFront(front);
		}
		// is space
		front.addFront(item);
	}

	@Override
	public void addBack(T item) {

		if (chunks.isEmpty()) {
			chunks.addBack(makeChunk());
		}
		// get first chunk 
		FixedSizeList<T> back = chunks.getBack();
		// no space
		if (back.isFull()) {
			back = makeChunk();
			chunks.addBack(back);
		}
		// is space
		back.addBack(item);
	}

	@Override
	public void addIndex(int index, T item) {

		this.checkInclusiveIndex(index);

		int chunkIndex = 0;
		int start = 0;

		for (FixedSizeList<T> chunk : this.chunks) {

			chunkIndex++;
			// calculate bounds of this chunk.
			int end = start + chunk.size();

			// Check whether the index should be in this chunk:
			if (start <= index && index <= end) {
				if (chunk.isFull()) {
					// check can roll to next
					// or need a new chunk	
					if (index == end) { 
						this.chunks.getIndex(chunkIndex).addFront(item); 
					} else {
						chunks.addIndex(chunkIndex, makeChunk()); 
						T roll = chunk.removeBack();
						this.chunks.getIndex(chunkIndex).addFront(roll);	
						chunk.addIndex(index-start, item);
					}

				} else {
					// put right in this chunk, there's space.
					chunk.addIndex(index, item);
				}	
				// upon adding, return.
				return;
			}

			// update bounds of next chunk.
			start = end;
			chunkIndex++;
		}
		throw new BadIndexError(index);

	}

	@Override
	public T getFront() {
		return this.chunks.getFront().getFront();
	}

	@Override
	public T getBack() {
		return this.chunks.getBack().getBack();
	}


	@Override
	public T getIndex(int index) {
		if (this.isEmpty()) {
			throw new EmptyListError();
		}
		int start = 0;
		for (FixedSizeList<T> chunk : this.chunks) {
			// calculate bounds of this chunk.
			int end = start + chunk.size();
			// Check whether the index should be in this chunk:
			if (start <= index && index < end) {
				return chunk.getIndex(index - start);
			}
			// update bounds of next chunk.
			start = end;
		}
		throw new BadIndexError(index);
	}

	@Override
	/**
	 * There is the external index (what gets passed in), the chunk number,
	 * and the chunk element number. We have to translate the external
	 * index to the chunk element number. We can use chunk.size() 
	 * because it is the number of elements in each chunk that is filled 
	 * (not null). If we use a counter variable that increments for each
	 * filled element in a chunk, we can compare that number with the 
	 * external index.  
	 */
	public void setIndex(int index, T value) {

		this.checkNotEmpty();
		this.checkExclusiveIndex(index);

		int counter = -1; 
		for (FixedSizeList<T> chunk : this.chunks) {
			for( int i = 0; i < chunkSize; i++) {
				if ( i < chunk.size()) { 
					counter++; 

					if (index == counter ) {
						chunk.setIndex(i, value);  
					} 
				}
			}
		}
	}

	@Override
	public int size() {
		int total = 0;
		for (FixedSizeList<T> chunk : this.chunks) {
			total += chunk.size();
		}
		return total;
	}

	@Override
	public boolean isEmpty() {
		return this.chunks.isEmpty();
	}
}