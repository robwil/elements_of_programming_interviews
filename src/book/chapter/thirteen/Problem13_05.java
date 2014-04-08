package book.chapter.thirteen;

import java.util.ArrayList;
import java.util.Arrays;

/**
 * Given two sorted arrays (of sizes m and n), compute the intersection and return as a new array.
 * Must not have duplicate arrays in the answer.
 * 
 * The problem asked how to do it when m ~~ n, vs. m << n.
 * I implemented m ~~ n as intersect1 and m << n as intersect2.
 * 
 * My solution concept was correct, but my implementation of intersect1 was wrong.
 * I mixed up the branch. For A[i] > B[j], it should be j = advance(B, j, A[i]).
 * I originally had i = advance(A, i, B[j]) here, and vice versa for next case. Which caused the method to immediate end.
 *
 */
public class Problem13_05 {
	/**
	 * @param A input array
	 * @param i current position in A
	 * @return the first index j such that A[j] != A[i] and j > i.<br/>
	 * Note that j may == A.length, so caller should not do A[j] without checking bounds.
	 */
	private static int advance(int[] A, int i) {
		int current = A[i++];
		for(; i < A.length; i++) {
			if (A[i] != current)
				break;
		}
		return i;
	}
	/**
	 * @param A input array
	 * @param i current position in A
	 * @param current value from another array we seek to surpass
	 * @return the first index j such that A[j] >= current and j > i.<br/>
	 * Note that j may == A.length, so caller should not do A[j] without checking bounds.
	 */
	private static int advance(int[] A, int i, int current) {
		for(i++; i < A.length; i++) {
			if (A[i] >= current)
				break;
		}
		return i;
	}
	
	public static ArrayList<Integer> intersect1(int[] A, int[] B) {
		ArrayList<Integer> output = new ArrayList<Integer>();
		for (int i = 0, j = 0; i < A.length && j < B.length; ) {
			if (A[i] == B[j]) {
				output.add(A[i]); // found intersection so add to output
				i = advance(A, i); // advance A and B to distinct values so we don't get duplicates
				j = advance(B, j);
			} else if (A[i] > B[j]) { // since A is ahead of B, B needs to catch up so we don't miss any potential intersect
				j = advance(B, j, A[i]);
			} else { // since B is ahead of A, A needs to catch up
				i = advance(A, i, B[j]);
			}
		}
		return output;
	}
	
	public static ArrayList<Integer> intersect2(int[] A, int[] B) {
		ArrayList<Integer> output = new ArrayList<Integer>();
		for (int i = 0; i < B.length; i++) {
			if (Arrays.binarySearch(A, B[i]) >= 0) {
				output.add(B[i]);
				i = advance(B, i);
			}
		}
		return output;
	}
	
	public static void main(String[] args) {
		int[] A = {1, 1, 1, 1, 2, 5, 19, 19, 19, 2000, 10000, 10001, 10002, 10003, 10004, 10005};
		int[] B = {1, 4, 19, 19, 51, 2000, 9001};
		
		System.out.println("expect result = {1, 19, 2000}");
		System.out.println(intersect1(A, B));
		System.out.println(intersect2(A, B));
	}
}
