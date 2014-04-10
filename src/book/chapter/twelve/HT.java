package book.chapter.twelve;

import java.util.HashSet;
import java.util.Set;

public class HT {
	private double maxLoadFactor;
	private Node[] arr;
	private int size;
	
	// Constructors
	
	// default capacity = 16, maxLoadFactor = 0.75
	public HT() {
		this(16, 0.75);
	}
	
	public HT(int capacity) {
		this(capacity, 0.75);
	}
	
	public HT(int capacity, double maxLoadFactor) {
		this.arr = new Node[capacity];
		this.maxLoadFactor = maxLoadFactor;
		this.size = 0;
	}
	
	// Public methods
	
	public int size() {
		return size;
	}

	public boolean isEmpty() {
		return size == 0;
	}
	
	public void put(Object key, Object value) {
		size += addToArray(arr, key, value);
		// check the hash table invariant for load factor is not broken.
		if ( (size/(double)arr.length) > maxLoadFactor ) {
			// Our current load is larger than desired loadFactor, so call resize.
			resize();
		}
	}
	
	public Object get(Object key) {
		Object value = null;
		int hashcode = getHashIndex(key, arr.length);
		// If something is stored at hash index, loop through the list until we find entry with a matching key.
		if (arr[hashcode] != null) {
			Node current = arr[hashcode];
			while (current != null) {
				if (current.getData().getKey().equals(key)) {
					value = current.getData().getValue();
					break;
				}
				current = current.getNext();
			}
		}
		// If the key wasn't found, this will return null.
		return value;
	}
	
	// Cheating and using HashSet for this method.
	// I am only making this method because my test code wants to use it.
	public Set<HTEntry> entrySet() {
		Set<HTEntry> entries = new HashSet<HTEntry>();
		for (int i = 0; i < arr.length; i++) {
			Node current = arr[i];
			while (current != null) {
				entries.add(current.getData());
				current = current.getNext();
			}
		}
		return entries;
	}
	
	// Private methods
	/**
	 * Insert a value, using key, into theArray.
	 * This implements the full collision/hash table business logic.
	 * This is extracted as a method because we want to use it from both put(..) and resize().
	 * 
	 * Returns 1 if value was inserted, or 0 if it overwrote old value for key.
	 */
	private int addToArray(Node[] theArray, Object key, Object value) {
		int hashcode = getHashIndex(key, theArray.length);
		// No collision case, so insert right away
		if (theArray[hashcode] == null) {
			theArray[hashcode] = new Node(new HTEntry(key, value));
			return 1;
		} else {
			// Collision detected, so we must loop through the linked list
			// If we find an HTEntry with same key, then overwrite the value
			Node current = theArray[hashcode];
			Node prev = null;
			while (current != null) {
				if (current.getData().getKey().equals(key)) {
					current.getData().setValue(value);
					// don't increment size since we just overwrote a value
					return 0;
				}
				prev = current;
				current = current.getNext();
			}
			// Otherwise, add it as a new Node to end of the linked list.
			prev.setNext(new Node(new HTEntry(key, value)));
			return 1;
		}
	}
	
	private void resize() {
		// Make array that is double the length of old array
		Node[] newArr = new Node[arr.length * 2];
		// Loop through all key/value pairs in current array, and add to new array.
		// Note that this will move everything around since the capacity increases, which changes the modulo hash index.
		for (int i = 0; i < arr.length; i++) {
			Node current = arr[i];
			while (current != null) {
				addToArray(newArr, current.getData().getKey(), current.getData().getValue());
				current = current.getNext();
			}
		}
		this.arr = newArr;
	}
	
	private int getHashIndex(Object key, int arrayLength) {
		int hashcode = key.hashCode() % arrayLength;
		if (hashcode < 0) // since Java modulo will return negative if dividend is negative
			hashcode += arrayLength;
		return hashcode;
	}
}
