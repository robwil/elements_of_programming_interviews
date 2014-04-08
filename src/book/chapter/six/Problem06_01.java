package book.chapter.six;

import java.util.Arrays;

/**
 * NOTE: The book solution is slightly better as it only requires one pass through the array,
 *       but I like my answer better - easier to understand IMO.
 * 
 * Partition a given array A into a three-way partition (aka Dutch Flag partition)
 * such that the first section is less than a given pivot value (at index i),
 * the second section is equal to, and the third section is greater than.
 * 
 * The desires space complexity is O(1) and time complexity is O(|A|).
 *
 */
public class Problem06_01 {
	public static void partition3way(int[] A, int i, int left, int right) {
		int pivotVal = A[i];
		i = left; int j = right;
		// Tradition two-way partition.
		for(; i < j; ) {
			if (A[i] > pivotVal) {
				int tmp = A[i];
				A[i] = A[j];
				A[j] = tmp;
				j--;
			} else {
				i++;
			}
		}
		// Second pass takes the left partition ( <= pivotVal ) and splits
		// into two partitions: less than and equal to separated.
		for (i = left; i < j; ) {
			if (A[i] == pivotVal) {
				int tmp = A[i];
				A[i] = A[j];
				A[j] = tmp;
				j--;
			} else {
				i++;
			}
		}
	}
	public static void main(String[] args) {
		int[] A = {6, 1, 3, 4, 0, 3, 7};
		System.out.println("BEFORE: " + Arrays.toString(A));
		partition3way(A, 2, 0, A.length - 1);
		System.out.println("AFTER: " + Arrays.toString(A));
	}
}
