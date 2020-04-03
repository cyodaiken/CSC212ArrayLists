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
		
		throw new TODOErr();
		/*
		 * System.out.println("TEST");
		 * 
		 * this.checkNotEmpty();
		 * 
		 * for (FixedSizeList<T> chunk1 : this.chunks) {
		 * 
		 * System.out.println("removeINDEXStart1: "); System.out.println(chunk1); }
		 * 
		 * System.out.println(chunks.getIndex(index));
		 * 
		 * FixedSizeList<T> remove = chunks.getIndex(index);
		 * 
		 * T value = remove.removeIndex(index); System.out.println("VALUE" + value);
		 * 
		 * if (remove.isEmpty()) { chunks.removeIndex(index); }
		 * 
		 * for (FixedSizeList<T> chunk1 : this.chunks) {
		 * 
		 * System.out.println("removeINDEX: "); System.out.println(chunk1); }
		 * 
		 * return value;
		 */
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
		// THIS IS THE HARDEST METHOD IN CHUNKY-ARRAY-LIST.
		// DO IT LAST.

		int chunkIndex = 0;
		int start = 0;
		for (FixedSizeList<T> chunk : this.chunks) {
			// calculate bounds of this chunk.
			int end = start + chunk.size();

			// Check whether the index should be in this chunk:
			if (start <= index && index <= end) {
				if (chunk.isFull()) {
					// check can roll to next
					// or need a new chunk
					throw new TODOErr();
				} else {
					// put right in this chunk, there's space.
					throw new TODOErr();
				}	
				// upon adding, return.
				// return;
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
	public void setIndex(int index, T value) {

		this.checkNotEmpty();
		this.checkExclusiveIndex(index);
		
		
		  int counter = -1; 
		  for (FixedSizeList<T> chunk : this.chunks) {
			  System.out.println("new chunk");
			  for( int i = 0; i < chunkSize; i++) {
		  
				  if ( i < chunk.size()) { 
					  counter++; 
					  System.out.println("COUNTER: " + counter);
					  if (index == counter ) {
						  chunk.setIndex(i, value);
						  System.out.println("set");
						  
					  } else {
						  
						  System.out.println("notset" + "index" + index + "counter" + counter); 
					  }
					  
					  
				  
				  }
				  
				  
				  
		 
				//if (index == i ) {

					//System.out.println("i: " + i);

				//	chunk.setIndex(index, value);
				//	a = true; 

					//for (FixedSizeList<T> chunk1 : this.chunks) {

						//System.out.println("INSIDE LOOP: "); 
						//System.out.println(chunk1);
					//}	
				//}
			}

		}
		System.out.println("COUNTER: " + counter + " SIZE: " + size());
		
		
	}

	/*			if (index < chunk.size()) {

				chunk.setIndex(index, value);

				break;
			}
			index -= chunk.size();	
		}

		chunk.setIndex(index, value);*/

	// calculate bounds of this chunk.
	/*
	 * int end = start + chunk.size();
	 * 
	 * System.out.println("NEW CHUNK:"); System.out.println("SIZE: " +
	 * chunk.size());
	 * 
	 * System.out.println("end: " + end);
	 */

	// Check whether the index should be in this chunk:
	/*
	 * if (start <= index && index < end) { chunk.setIndex(index - start, value); }
	 */



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