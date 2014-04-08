package book.chapter.eleven;

import java.util.PriorityQueue;

/**
 * Problem 11.13, p. 92
 * Find the kth largest element in an array, in O(N) time.
 * 
 * I could not think of a solution using strictly O(N) time.
 * However, I came up with a solution using O(KN) time and O(1) space,
 * and a second solution using O(logK N) time and O(K) space,
 * implemented as klargest1 and klargest2 respectively.
 * 
 * The book solution is of course strictly O(N), using a decrease and conquer method.
 * It uses the insight that performing a quicksort-style pivot and looking at the ending index of the pivot value
 * allows us to determine the kth largest element by recursion. The recurrence is solved to O(N) time using the Master Theorem.
 * 
 * I implemented this without consulting the book's code as klargest3. However, my paper solution had several bugs in it.
 * Some I solved on paper, but I had to use the debugger to solve all the bugs. This is certainly not a trivial
 * implementation, even when you know the basic idea behind it.
 * 
 * @author rob
 *
 */
public class Problem11_13 {
	
	/**
	 * O(KN) time and O(1) space.
	 * Basically finds the max in an array k different times, each time finding a max less than the previous max.
	 * This allows it to find the kth largest element.
	 */
	public static int klargest1(int[] arr, int k) {
		int currentMax = Integer.MAX_VALUE;
		for (int j = 0; j < k; j++) {
			int max = Integer.MIN_VALUE;
			for (int i = 0; i < arr.length; i++) {
				if (arr[i] > max && arr[i] < currentMax) {
					max = arr[i];
				}
			}
			currentMax = max;
		}
		return currentMax;
	}
	
	/**
	 * O(logK N) time and O(K) space
	 * Starts by inserting K elements into a MIN heap. Then for all elements from A[k .. N-1],
	 * it inserts that element into the heap and removes the min. The k largest elements will survive,
	 * at which point we return the min which is the kth largest element itself.
	 */
	public static int klargest2(int[] arr, int k) {
		PriorityQueue<Integer> minHeap = new PriorityQueue<Integer>(k);
		for (int i = 0; i < k; i++) {
			minHeap.add(arr[i]);
		}
		for (int i = k; i < arr.length; i++) {
			minHeap.add(arr[i]);
			minHeap.remove();
		}
		return minHeap.remove();
	}
	
	public static int klargest3(int[] arr, int k) {
		return klargest3(arr, k, 0, arr.length - 1);
	}
	private static void swap(int[] arr, int i, int j) {
		int tmp = arr[i];
		arr[i] = arr[j];
		arr[j] = tmp;
	}
	private static int klargest3(int[] arr, int k, int left, int right) {
		// Choose a random index, to find a pivot value.
		int random = (int) (Math.floor(Math.random() * (right-left + 1))) + left;
		int pivotValue = arr[random];
		// Swap values around in A[left..right] such that all elements
		// greater than or equal to pivotValue are left of i, while elements
		// less than pivotValue are right of j. At end, i==j and A[i]==pivotValue, with
		// array such that A[left..i] is >= pivotValue and A[i+1..right] is less than.
		int i = left, j = right;
		while (i < j) {
			if (arr[i] < pivotValue) {
				swap(arr, i, j);
				j--;
			} else if (arr[i] > pivotValue) {
				i++;
			} else { // arr[i] == pivotValue
				if (arr[j] > pivotValue) {
					swap(arr, i, j);
					i++;
				} else {
					j--;
				}
			}
		}
		// i == j is our p-value
		int p = i - left; // p is the index of the pivot value, expressed relative to the current subarray (starting at left).
		if (p == k - 1) {
			// this means that there are k-1 elements greater than A[i],
			// and A[i+1..N-1] are less than A[i]. Therefore A[i] must be kth largest element.
			return arr[i];
		} else if (p > k-1) {
			// This means there are at least k elements in left subarray, all of which are greater than right subarray.
			// Therefore we just need to find kth largest element in left subarray, recursively.
			return klargest3(arr, k, left, i-1);
		} else { // p < k-1 case
			// This means that we have found the first p+1 largest elements, but p+1 < k.
			// So the kth largest element is in right subarray, but in that context it is the (k - (p+1))th element.
			return klargest3(arr, k - (p + 1), i + 1, right);
		}
	}
	
	public static void main(String[] args) {
		int[] arr = {9, 8, 7, 6, 5, 4, 3, 2, 1};
		int[] arr2 = {1, 7, 3, 5, 8, 6, 2, 9, 4};
		
		System.out.println("klargest1, k=3\t\t"+klargest1(arr,3));
		System.out.println("klargest2, k=3\t\t"+klargest2(arr,3));
		System.out.println("klargest3, k=3\t\t"+klargest3(arr,3));
		
		System.out.println("klargest1, k=5\t\t"+klargest1(arr2,5));
		System.out.println("klargest2, k=5\t\t"+klargest2(arr2,5));
		System.out.println("klargest3, k=5\t\t"+klargest3(arr2,5));
	}
}
