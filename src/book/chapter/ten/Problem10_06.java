package book.chapter.ten;

import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.PriorityQueue;

/**
 * Problem 10.6, Approximate Sort
 * Given a long list of numbers that are "approximately" sorted, return a sorted list.
 * Approximate is defined as an element being at most k positions away from its sorted location.
 * The problem description asks for a solution using O(k) space.
 * 
 * My initial thinking, ignoring the fact that this is in the Heaps chapter of the book,
 * is given as approxSort1(..) below. This solution uses O(1) space, less than asked for
 * in the problem. The time complexity is somewhat confusing to determine. However, in the worst case
 * we will execute the outer for loop k times for each i, and the inner for loop k times. This leads
 * to an O(k^2 n) solution in worst case. Best/average case is O(kn).
 * 
 * I then developed approxSort2(..) which uses a min heap (part of Java Collections PriorityQueue).
 * Here we use O(k) space and O(logk n) time complexity, so time wise is definitely preferable.
 * 
 * Even if k is much less than n, k^2 compared to logk is a huge difference, so approxSort2 is likely much better.
 * 
 *  In practice, they seem to be much closer than expected:
 *  
 *  output of benchmark() method :
 *  Elapsed time for approxSort1: 1.5351239229999492 seconds
 *  Elapsed time for approxSort2: 1.3909773149999496 seconds
 *  
 * @author rob
 *
 */
public class Problem10_06 {
	
	private static void swap(int[] arr, int i, int j) {
		int tmp = arr[i];
		arr[i] = arr[j];
		arr[j] = tmp;
	}
	
	public static void approxSort1(int[] arr, int k) {
		// Invariant: elements at A[0..i-1] are always correctly sorted.
		for (int i = 0; i < arr.length; ) {
			boolean swapped = false;
			int indexOfOrigElem = i; // used so we can track with arr[i] even as we swap it to new positions
			for (int j = i + 1; j <= i+k && j < arr.length; j++) {
				if (arr[indexOfOrigElem] > arr[j]) {
					swap(arr, indexOfOrigElem, j);
					indexOfOrigElem = j;
					swapped = true;
				}
			}
			// If we haven't swapped in the inner loop,
			// it means arr[i] is correctly placed, so we move on.
			if (!swapped)
				i++;
		}
	}
	
	public static void approxSort2(int[] arr, int k) {
		// Load k+1 elements into priority queue to start
		PriorityQueue<Integer> minHeap = new PriorityQueue<Integer>(k);
		for (int i = 0; i <= k; i++) {
			minHeap.add(arr[i]);
		}
		// Loop through array, inserting correct element taken from minHeap.
		// We don't care if we override elements because this is just integers and they are copied to minHeap.
		// In a non-integer scenario we might have to be more careful with overriding stuff.
		for (int i = 0; i < arr.length; i++) {
			arr[i] = minHeap.remove();
			if (i + k + 1 < arr.length) {
				minHeap.add(arr[i + k + 1]); // we're always k+1 elements ahead in the heap, compared to our position in arr
			}
		}
		// No need to "flush" heap as the above handles it.
	}
	
	public static void benchmark() throws Exception {
		int N = 10000;
		String[] methodsToCompare = {
				"approxSort1",
				"approxSort2"
		};
		
		// Create test array of 1000 elements.
		int k = 10;
		int[] arr = new int[1000];
		for (int i = 0; i < 1000; i++) {
			arr[i] = (int) (i * 1000 + Math.floor(Math.random() * 100)); // generate random number between i*1000 and i*1000+99
			// randomly swap elements so that we don't have a perfectly sorted array
			int offset = (int) (Math.floor(Math.random() * 2 * k) - k); // generate random offset between [- k, + k]
			if (i + offset > 0 && i + offset < arr.length) {
				swap(arr, i, i+offset);
			}
		}
		
		
		for (String methodName : methodsToCompare) {
			Class clazz = Class.forName("book.chapter.ten.Problem10_06");
			Method method = clazz.getDeclaredMethod(methodName, int[].class, int.class);

			// Time execution of the reflected method.
			double totalTime = 0.0;
			for (int i = 0; i < N; i++) {
				int[] arrClone = arr.clone(); // clone array so we start with same one each time. Don't count this in timer.
				long startTime = System.nanoTime();
				method.invoke(null, arrClone, k);
				long endTime = System.nanoTime();
				totalTime += (endTime-startTime)/1000000000.0;
			}
			System.out.println("Elapsed time for " + methodName + ": " + totalTime + " seconds");
		}
	}
	
	public static void main(String[] args) throws Exception {
		int[] arr = {10, 14, 1, 7, 42};
		System.out.println("Original = " + Arrays.toString(arr));
		approxSort1(arr, 2);
		System.out.println("After    = " + Arrays.toString(arr));
		
		System.out.println("\n========\n");
		int[] arr2 = {10, 14, 1, 7, 42};
		System.out.println("Original = " + Arrays.toString(arr2));
		approxSort1(arr2, 2);
		System.out.println("After    = " + Arrays.toString(arr2));
		
		benchmark();
	}
}
