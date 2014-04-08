package book.chapter.eleven;

/**
 * Problem 11.3, p. 86
 * 
 * Given a sorted array of distinct integers, find an index i such that A[i] == i.
 * The problem does not specify a goal time or space complexity, but asks for an "efficient algorithm."
 * 
 * My initial reading of the problem ignored the important word "distinct". In the case of non-distinct
 * sorted integer array, there is no way to do better than O(N). You can skip some elements, which will
 * give better than an actual N iterations, but it's still O(N). I implemented this as findIndex1(..)
 * 
 * Once realizing the problem said distinct integers, it became clear that a binary search variation
 * was possible, which is O(log N). The basic idea is that you look at the median element, and if that
 * element is greater than i, you then recurse on the left half (because if A[i] > i then it will be
 * impossible for A[j] == j where j > i, due to pigeon hole principle). If element is less than i, you
 * recurse on the right half. This is implemented as findIndex2(..)
 * 
 * The book answer is identical to my findIndex2, although they do it iteratively rather than recursively.
 * 
 * @author rob
 *
 */
public class Problem11_03 {
	public static int findIndex1(int[] A) {
		for (int i = 0; i < A.length; ) {
			if (A[i] == i) return i;
			if (A[i] > i)
				i = A[i];
			else
				i++;
		}
		return -1;
	}
	
	public static int findIndex2(int[] A) {
		return findIndex2(A, 0, A.length - 1);
	}
	private static int findIndex2(int[] A, int left, int right) {
		if (left > right) return -1;
		int median = (right - left)/2 + left;
		if (A[median] == median) return median;
		if (A[median] > median) // A[i] > i, so recurse on left half of A
			return findIndex2(A, left, median - 1);
		// else, A[i] < i so recurse on right half of A
		return findIndex2(A, median + 1, right);
	}
	
	public static void main(String[] args) {
		int[] A1 = {0, 2, 3, 4, 5};
		int[] A2 = {-10, -8, -5, 3, 100};
		int[] A3 = {-10, -9, -8, -7, 4, 100, 101, 102, 103, 104, 105, 106};
		int[] A4 = {-10, -9, -8, -7, -6, -5, 99, 100, 101};
		
		System.out.println("A1, findIndex1 = " + findIndex1(A1));
		System.out.println("A1, findIndex2 = " + findIndex2(A1));
		System.out.println();
		
		System.out.println("A2, findIndex1 = " + findIndex1(A2));
		System.out.println("A2, findIndex2 = " + findIndex2(A2));
		System.out.println();
		
		System.out.println("A3, findIndex1 = " + findIndex1(A3));
		System.out.println("A3, findIndex2 = " + findIndex2(A3));
		System.out.println();
		
		System.out.println("A4, findIndex1 = " + findIndex1(A4));
		System.out.println("A4, findIndex2 = " + findIndex2(A4));
		System.out.println();
	}
}
