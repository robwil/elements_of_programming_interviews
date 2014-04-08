package book.chapter.six;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * Problem 6.14, p. 60
 * Given a partially completed Sudoku board as a 9x9 2D array,
 * return true iff it is a valid board (i.e. there are no duplicates
 * in either rows, columns, or 3x3 sections). Else return false.
 * 
 * Assume input numbers are in range [0..9], with 0 meaning not filled in.
 * 
 * No time or space goals were given.
 * 
 * My first solution isValidPartialSolution1 is the "brute force" approach,
 * but with some code cleanliness by using an external class with exceptions.
 * It visits each item 3 times each. (3 * 9 * 9) where N = 9, is roughly O(N^2).
 * It also re-initializes the "seen" array 3 * 9 times, but this is still O(N^2).
 * The space used is roughly 9 bits/bytes (depending on boolean[] implementation),
 * plus any overhead for the object. So it's roughly O(N).
 * 
 * My second solution isValidPartialSolution2 trades space for time. It uses 3 * 81 bits/bytes
 * + object overhead of 18 objects. This is roughly O(N^2).
 * The time complexity is still O(N^2) but here we visit each element only once,
 * so the real world time will be less, especially given our small N here.
 * 
 * My benckmark at the end verifies that the second solution runs around 25% faster.
 * 
 * After some research, I changed the boolean[] in NumbersSeen to use BitSet which does use only
 * 1 bit per value. It internally uses long's, so our 9 bits can fit in one long which is 64 bits or 8 bytes,
 * so we actually don't get much saving (if at all, it's 8 bytes + overhead, compared to 9 bytes + overhead
 * for boolean[]).
 * 
 * The book solution is identical to my isValidPartialSolution1.
 * Actually his is slightly worse because he creates 9*3 different vectors while looping through
 * all 81 values 3 times!
 * 
 * @author rob
 *
 */
public class Problem06_14 {
	public static boolean isValidPartialSolution1(int[][] board) {
		try {
			NumbersSeen seen = new NumbersSeen();
			// Check columns
			for (int x = 0; x < 9; x++) {
				seen.init();
				for (int y = 0; y < 9; y++) {
					seen.sawNumber(board[x][y]);
				}
			}
			// Check rows
			for (int y = 0; y < 9; y++) {
				seen.init();
				for (int x = 0; x < 9; x++) {
					seen.sawNumber(board[x][y]);
				}
			}
			// Check 3x3 sections, I call them "trips"
			for (int tripi = 0; tripi < 3; tripi++) {
				for (int tripj = 0; tripj < 3; tripj++) {
					seen.init();
					for (int i = 0; i < 3; i++) {
						for (int j = 0; j < 3; j++) {
							seen.sawNumber(board[tripi*3 + i][tripj*3 + j]);
						}
					}
				}
			}
		} catch (DuplicateException ex) {
			System.err.println(ex.getMessage());
			return false;
		}
		return true;
	}
	
	public static boolean isValidPartialSolution2(int[][] board) {
		try {
			NumbersSeen[] rows = new NumbersSeen[9];
			NumbersSeen[] cols = new NumbersSeen[9];
			NumbersSeen[][] trips = new NumbersSeen[3][3];
			for (int i = 0; i < 9; i++) {
				rows[i] = new NumbersSeen();
				cols[i] = new NumbersSeen();
				trips[i/3][i%3] = new NumbersSeen();
			}
			for (int x = 0; x < 9; x++) {
				for (int y = 0; y < 9; y++) {
					int n = board[x][y];
					cols[x].sawNumber(n);
					rows[y].sawNumber(n);
					trips[x/3][y/3].sawNumber(n);
				}
			}
		} catch (DuplicateException ex) {
			System.err.println(ex.getMessage());
			return false;
		}
		return true;
	}
	
	public static void main(String[] args) throws Exception {
		int[][] blankBoard = {{0,0,0, 0,0,0, 0,0,0},
							  {0,0,0, 0,0,0, 0,0,0},
							  {0,0,0, 0,0,0, 0,0,0},
							  
							  {0,0,0, 0,0,0, 0,0,0},
							  {0,0,0, 0,0,0, 0,0,0},
							  {0,0,0, 0,0,0, 0,0,0},
							  
							  {0,0,0, 0,0,0, 0,0,0},
							  {0,0,0, 0,0,0, 0,0,0},
							  {0,0,0, 0,0,0, 0,0,0}};
		
		int[][] validBoard = {{1,2,3, 4,5,6, 7,8,9},
							  {6,5,4, 9,8,7, 3,2,1},
							  {7,8,9, 2,3,1, 4,5,6},
							  
							  {0,0,0, 0,0,0, 0,0,0},
							  {0,0,0, 0,0,0, 0,0,0},
							  {0,0,0, 0,0,0, 0,0,0},
							  
							  {0,0,0, 0,0,0, 0,0,0},
							  {0,0,0, 0,0,0, 0,0,0},
							  {0,0,0, 0,0,0, 0,0,0}};
		
		int[][] invalidBoard1 =  {{1,0,0, 0,0,0, 0,0,0},
								  {0,0,1, 0,0,0, 0,0,0},
								  {0,0,0, 0,0,0, 0,0,0},
								  
								  {0,0,0, 0,0,0, 0,0,0},
								  {0,0,0, 0,0,0, 0,0,0},
								  {0,0,0, 0,0,0, 0,0,0},
								  
								  {0,0,0, 0,0,0, 0,0,0},
								  {0,0,0, 0,0,0, 0,0,0},
								  {0,0,0, 0,0,0, 0,0,0}};
		
		int[][] invalidBoard2 =  {{2,0,0, 0,0,0, 0,0,0},
								  {0,0,0, 0,0,0, 0,0,0},
								  {0,0,0, 0,0,0, 0,0,0},
								  
								  {0,0,0, 0,0,0, 0,0,0},
								  {0,0,0, 0,0,0, 0,0,0},
								  {0,0,0, 0,0,0, 0,0,0},
								  
								  {0,0,0, 0,0,0, 0,0,0},
								  {0,0,0, 0,0,0, 0,0,0},
								  {2,0,0, 0,0,0, 0,0,0}};
		
		int[][] invalidBoard3 =  {{3,0,0, 3,0,0, 0,0,0},
								  {0,0,0, 0,0,0, 0,0,0},
								  {0,0,0, 0,0,0, 0,0,0},
								  
								  {0,0,0, 0,0,0, 0,0,0},
								  {0,0,0, 0,0,0, 0,0,0},
								  {0,0,0, 0,0,0, 0,0,0},
								  
								  {0,0,0, 0,0,0, 0,0,0},
								  {0,0,0, 0,0,0, 0,0,0},
								  {0,0,0, 0,0,0, 0,0,0}};
		
		System.out.println("isValidPartialSolution1:");
		System.out.println("blankBoard = " + isValidPartialSolution1(blankBoard));
		System.out.println("validBoard = " + isValidPartialSolution1(validBoard));
		System.out.println("invalidBoard1 = " + isValidPartialSolution1(invalidBoard1));
		System.out.println("invalidBoard2 = " + isValidPartialSolution1(invalidBoard2));
		System.out.println("invalidBoard3 = " + isValidPartialSolution1(invalidBoard3));
		System.out.println();
		System.out.println("isValidPartialSolution2:");
		System.out.println("blankBoard = " + isValidPartialSolution2(blankBoard));
		System.out.println("validBoard = " + isValidPartialSolution2(validBoard));
		System.out.println("invalidBoard1 = " + isValidPartialSolution2(invalidBoard1));
		System.out.println("invalidBoard2 = " + isValidPartialSolution2(invalidBoard2));
		System.out.println("invalidBoard3 = " + isValidPartialSolution2(invalidBoard3));
		
		// Benchmarks
		int N = 100000;
		String[] methodsToCompare = {
				"isValidPartialSolution1",
				"isValidPartialSolution2"
		};
		
		for (String methodName : methodsToCompare) {
			Class clazz = Class.forName("book.chapter.six.Problem06_14");
			Method method = clazz.getDeclaredMethod(methodName, int[][].class);

			// Time execution of the reflected method.
			long startTime = System.nanoTime();
			for (int i = 0; i < N; i++) {
				Boolean retVal = (Boolean) method.invoke(null, validBoard);
				if (retVal == false)
					throw new Exception("wrong result!");
			}
			long endTime = System.nanoTime();
			System.out.println("Elapsed time for " + methodName + ": " + (endTime-startTime)/1000000000.0 + " seconds");
		}
	}
}
