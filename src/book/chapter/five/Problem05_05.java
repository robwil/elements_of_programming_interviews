package book.chapter.five;

import java.util.Arrays;

/**
 * Problem 5.5, page 51
 * Print out the power set of a given set S.
 * The power set is a set containing all possible subsets of S, including empty set and S itself.
 * 
 * I implemented printPSet1 which works in O(n * 2^n) time and uses O(n * 2^n) space.
 * My paper implementation had an off-by-one error with using S[i] instead of S[i-1] around line 41, 43.
 * I also had a syntax error with multi-dimensional arrays, trying to do new String[][x] instead of String[x][]
 * 
 * I then asked myself, can I do this with less space? The answer is certainly yes. We only need the prior row.
 * This is the usual dynamic programming paradigm of keeping only what you need.
 * In fact, we don't even need this 2D array concept of prior row, but can build the entire answer as one 1D String array.
 * printPSet2 is that result, which is same time complexity but uses only O(2^n) space.
 * 
 * I guess theoretically if we only were printing it, we could get away with O(2^(n-1)) space.
 * 
 * The Book answer was much more clever and clean. They used the insight that the binary representation
 * of all integers 0 .. 2^n represent one of the subsets in the power set. Each bit set means to include
 * that item from S in the current subset.
 * 
 * E.g. for N = 3, S = ABC, we enumerate the eight integers from 0 .. 2^3
 *     ABC
 * 0 = 000 = empty set
 * 1 = 001 = C
 * 2 = 010 = B
 * 3 = 011 = BC
 * 4 = 100 = A
 * 5 = 101 = AC
 * 6 = 110 = AB
 * 7 = 111 = ABC
 * 
 * After this insight, I implemented my last version printPSet3 which uses this.
 * 
 * @author rob
 *
 */
public class Problem05_05 {

	// Note: I am using char[] for Set S for convenience
	public static void printPSet1(char[] S) {
		if (S == null || S.length == 0) {
			System.out.println("[[EMPTY]]");
		} else {
			// We iterate S.length times (extra 1 for empty set initialization step)
			// Each time we append the current value S[i] to all items found in previous step,
			// while also copying those items.
			// This progressively builds the power set until output[S.length] contains the finished result.
			String[][] output = new String[S.length+1][];
			output[0] = new String[1];
			output[0][0] = "EMPTY";
			for (int i = 1; i <= S.length; i++) {
				int powerI = (int) Math.pow(2, i); // Yes, this can lose precision. But if it does, we're already out of RAM.
				int powerI_1 = (int) Math.pow(2, i-1);
				output[i] = new String[powerI];
				for (int j = 0; j < powerI_1; j++) {
					output[i][j] = output[i-1][j];
				}
				for (int j = 0; j < powerI_1; j++) {
					// Handle empty case so we don't get stuff like "EMPTYA" but rather "A"
					if (output[i-1][j].equals("EMPTY"))
						output[i][j + powerI_1] = Character.toString(S[i-1]);
					else
						output[i][j + powerI_1] = output[i-1][j] + S[i-1];
				}
			}
			System.out.println(Arrays.deepToString(output[S.length]));
			System.out.println("PSET length = " + output[S.length].length);
		}
	}
	
	public static void printPSet2(char[] S) {
		if (S == null || S.length == 0) {
			System.out.println("[[EMPTY]]");
		} else {
			// We iterate S.length times
			// Each time output[0.. 2^(i-1)] contains the results found so far.
			// We then build up [2^(i-1) .. 2^i] by appending S[i] to all previous values.
			// This progressively builds the power set until output[] contains the finished result.
			String[] output = new String[(int)Math.pow(2, S.length)];
			output[0] = "EMPTY";
			for (int i = 0; i < S.length; i++) {
				int powerI = (int)Math.pow(2, i);
				for (int j = 0; j < powerI; j++) {
					if (j == 0)
						output[j + powerI] = Character.toString(S[i]);
					else
						output[j + powerI] = output[j] + S[i];
				}
			}
			System.out.println(Arrays.toString(output));
			System.out.println("PSET length = " + output.length);
		}
	}
	
	public static void printPSet3(char[] S) {
		if (S == null || S.length == 0) {
			System.out.println("[EMPTY]");
		} else {
			System.out.print("[");
			// Explanation of this madness is in my top-of-file commentary.
			int powerI = (int)Math.pow(2, S.length);
			for (int i = 0; i < powerI; i++) {
				int bits = (int)Math.floor(Math.log(i)/Math.log(2)) + 1;
				if (i == 0)
					System.out.print("EMPTY");
				for (int j = 0; j < bits; j++) {
					if ((i & (1 << j)) > 0) {
						System.out.print(S[j]);
					}
				}
				if (i < powerI-1)
					System.out.print(", ");
			}
			System.out.println("]");
			System.out.println("PSET length = " + powerI);
		}
	}
	
	public static void main(String[] args) {
		char[] S = {'A', 'B', 'C', 'D'};
		printPSet1(S);
		printPSet2(S);
		printPSet3(S);
	}
}
