package book.chapter.six;

/**
 * Not an explicit problem, but alluded to in 6.3
 * For a given array, find the max(A[i] - A[j]) where i > j.
 * Also know as the "Max difference" problem.
 * 
 * This has a rather straight-forward implementation, O(N) time and O(1) space.
 * 
 * @author rob
 *
 */
public class MaxDiff {
	/**
	 * If arr is null or has <= 1 elements, return Integer.MIN_VALUE since
	 * there is no valid difference operation with 1 or fewer elements.
	 */
	public static int maxDiff(int[] arr) {
		int maxDiff = Integer.MIN_VALUE;
		// Input validation
		if (arr == null || arr.length <= 1)
			return maxDiff;
		// Compute max difference by subtracting current value from minimum value seen so far.
		int minSoFar = arr[0];
		for (int i = 1; i < arr.length; i++) {
			int diff = arr[i] - minSoFar;
			if (diff > maxDiff)
				maxDiff = diff;
			if (arr[i] < minSoFar)
				minSoFar = arr[i];
		}
		return maxDiff;
	}
	
	public static void main(String[] args) {
		int[] arr1 = {0, 1, 2, 3, 4};
		int[] arr2 = {4, 3, 2, 1, 0};
		int[] arr3 = {4, 3, 3, 1, 0};
		int[] arr4 = {5, -2, 3, -1, 7};
		
		System.out.println("maxDiff(arr1) = " + maxDiff(arr1) + ", expected = 4");
		System.out.println("maxDiff(arr2) = " + maxDiff(arr2) + ", expected = -1");
		System.out.println("maxDiff(arr3) = " + maxDiff(arr3) + ", expected = 0");
		System.out.println("maxDiff(arr4) = " + maxDiff(arr4) + ", expected = 9");
	}
}
