package book.chapter.eight;

public class CircularQueue<T> {
	/*
	 * Invariants
	 * first = points to first element in queue, if one exists
	 * last = where to insert next item. Always an available/empty slot.
	 * 
	 * When first == last, queue is either full or empty. If it's full,
	 * the capacity will be doubled automatically and only enqueue() knows about that,
	 * so all other methods can treat first == last as empty queue.
	 */
	int first, last;
	T[] arr;

	public CircularQueue (int capacity) {
		last = 0;
		first = 0;
		arr = (T[]) new Object[capacity]; // had to look up this syntax. I expected new T[capacity] to work.
	}
	
	public int size() {
		if (first > last)
			return (arr.length - first) + last;
		// else
		return last - first;
	}
	
	public void enqueue(T item) {
		arr[last++] = item;
		if (last >= arr.length) // implement circular looping
			last = 0;
		if (last == first) { // array is full, so resize
			resize();
		}
	}
	
	/**
	 * 
	 * @return element that was dequeued, or null if queue is empty
	 */
	public T dequeue() {
		// Handle empty Q case
		if (first == last)
			return null;
		// Normal case
		T retVal = arr[first++];
		if (first >= arr.length) { // implement circular looping
			first = 0;
		}
		return retVal;
	}
	
	/**
	 * Double size of internal array
	 */
	private void resize() {
		int oldLength = arr.length;
		T[] newArr = (T[]) new Object[2 * oldLength];
		for (int i = 0; i < oldLength; i++) {
			newArr[i] = arr[first++]; // could use first or last here, since they are equal.
			if (first >= oldLength) // implement circular looping
				first = 0;
		}
		arr = newArr;
		first = 0;
		last = oldLength;
	}
	
	public String toString() {
		StringBuilder sb = new StringBuilder();
		int current = first;
		while (current != last) {
			sb.append(arr[current].toString() + " ");
			if (++current >= arr.length) {
				current = 0;
			}
		}
		return sb.toString();
	}
}
