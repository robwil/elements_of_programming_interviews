package book.chapter.eleven;

/**
 * Problem 11.2, p. 89
 * 
 * Design an algorithm that takes a sorted array A
 * and finds the _first_ occurrence of an element greater than k.
 * It returns the index of this element, or -1 if all elements are <= k.
 * 
 * No time or space complexity goals were given.
 * 
 * My solution below was not correct on first entry. I had a bug with the N == 1 case (which
 * was originally just returning minI), and also with not considering the arr[mid]==minGreaterK case.
 * 
 * @author rob
 *
 */
public class Problem11_02 {
	public static int firstGreaterK(int[] arr, int k) {
		return firstGreaterK(arr, 0, arr.length - 1, k, Integer.MAX_VALUE, -1);
	}
	
	/**
	 * 
	 * @param arr - input array A, guaranteed to be sorted
	 * @param left - left bound of current sub-search
	 * @param right - right bound of current sub-search
	 * @param k - element we are searching for elements greater than
	 * @param minGreaterK - smallest item greater than K seen so far
	 * @param minI - index of minGreaterK
	 * @return index of first element greater than k in this sub-search
	 */
	public static int firstGreaterK(int[] arr, int left, int right, int k, int minGreaterK, int minI) {
		int N = right - left + 1;
		if (N <= 0) {
			return minI;
		}
		if (N == 1) {
			if (arr[left] <= minGreaterK && left < minI) {
				return left;
			}
			return minI;
		}
		int mid = left + (right - left)/2;
		if (arr[mid] <= k) {
			return firstGreaterK(arr, mid + 1, right, k, minGreaterK, minI);
		}
		// arr[mid] > k case
		if (arr[mid] < minGreaterK) {
			minGreaterK = arr[mid];
			minI = mid;
		} else if (arr[mid] == minGreaterK && mid < minI) {
			minI = mid;
		}
		return firstGreaterK(arr, left, mid-1, k, minGreaterK, minI);
	}
	
	public static void main(String[] args) {
		int[] arr = {-14, -10, 2, 108, 108, 243, 285, 285, 285, 401};
		//System.out.println("firstGreaterK(arr, 500) = " + firstGreaterK(arr, 500));
		System.out.println("firstGreaterK(arr, 101) = " + firstGreaterK(arr, 101));
	}
}
