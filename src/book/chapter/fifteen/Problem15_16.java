package book.chapter.fifteen;

/**
 * Problem 15.16, p. 125
 * 
 * Number of ways. Given an NxM 2D array, compute how many ways one can
 * process from the top-left corner (0,0) to the bottom right corner (n-1,m-1)
 * if at each step you can only go right or down. Also consider how to
 * avoid obstacles presented as a 2D boolean array (true if obstacle present).
 * 
 * -- Before looking at book solution --
 * This is a fairly simple recursive calculation. I commented my solution inline. 
 * 
 * @author rob
 *
 */
public class Problem15_16 {
	/**
	 * 
	 * @param N - x-boundary of the 2D array we are traversing
	 * @param M - y-boundary of the 2D array we are traversing
	 * @param B - tells us where obstacles are
	 * @param x - current x position
	 * @param y - current y position
	 * @return 1 if our current path was valid, 0 otherwise
	 */
	private static int countWays(int N, int M, boolean[][] B, int x, int y) {
		// check for invalid cases
		// x >= N exceeded right boundary of array
		// y >= M exceeded down boundary of array
		// B[x][y] means there we ran into an obstacle
		if (x >= N || y >= M || B[x][y])
			return 0;
		// check for finished case, bottom-right corner
		if (x == N-1 && y == M-1)
			return 1;
		// Otherwise, we recurse in right and down directions
		int rightWays = countWays(N, M, B, x+1, y);
		int downWays = countWays(N, M, B, x, y+1);
		return rightWays + downWays;
	}
	
	public static int countWays(int N, int M, boolean[][] B) {
		if (N <= 0 || M <= 0) return 0;
		return countWays(N, M, B, 0, 0);
	}
	
	public static void main(String[] args) {
		int N = 5;
		int M = 5;
		boolean[][] B = new boolean[5][5];
		System.out.println("No boundaries = " + countWays(5, 5, B));
		for (int i = 0; i < 5; i++) {
			B[2][i] = true;
		}
		System.out.println("impenetrable boundary = " + countWays(5, 5, B));
	}
}
