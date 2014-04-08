package book.chapter.seventeen;

/**
 * Problem 17.2
 * Knapsack problem, thief stealing clocks of weights w_i with values v_i.
 * The problem description gives a counter-example of greedy algorithm's optimality.
 * 
 * Since we aren't using greedy, we must focus on global optima only. This excludes
 * all but brute force method. This means we must compute all possible subsets,
 * test they meet the weight constraint, and then track the maximum value we see.
 * 
 * Since we must enumerate all subsets, which is the Power Set, I am using the trick from Problem 5.5
 * that we can increment through the integers from 0 .. 2^n-1 to get the power set.
 * 
 * My solution, knapsackBits is O(2^n) time and O(1) space complexity. It has the limitation of N <= 64,
 * due to use of LONG to compute the power set.
 * 
 * The book solution uses Dynamic Programming and is O(nw) time and O(w) space. Being dependent on the weight
 * is totally weird and I never would have thought of this solution, but it is pretty cool.
 * However, my criticism of the book solution is twofold:
 * 1) The question clearly asks for the subset itself and they return only the max value possible.
 * 2) They already use O(w) space and to return the subset I believe they would need O(nw) space since
 *    they'd have to have the full 2D computation and work backward to reconstruct the solution.
 * 
 * @author rob
 *
 */
public class Problem17_02 {
	// Assumption: n <= 64, fitting inside LONG value type.
	// Anything bigger will cause some serious issues. Perhaps BigInteger, etc. could help?
	public static void knapsackBits(int[] weights, int[] values, long maxWeight) {
		int n = weights.length;
		long numSets = (long)Math.pow(2, n);
		// Initialize global maxima values with empty set's information.
		long bestSet = 0;
		long bestValue = 0;
		long bestWeight = 0;
		// Loop through Power Set (all subsets) and test their values/weights
		for (long set = 1; set < numSets; set++) {
			long weight = 0;
			long value = 0;
			// Test bits of current "set"
			for (int i = 0; i < 64; i++) { // 64 because we are using LONG
				if (((set >> i) & 1) == 1) {
					weight += weights[i];
					value += values[i];
				}
			}
			// Test against constraint and global maximum
			if (weight <= maxWeight && value > bestValue) {
				bestValue = value;
				bestWeight = weight;
				bestSet = set;
			}
		}
		// At this point, we have found global maximum.
		// Just need to Print the set.
		for (int i = 0; i < 64; i++) {
			if (((bestSet >> i) & 1) == 1) {
				System.out.print("[i=" + i + ", w=" + weights[i] + ", v=" + values[i] + "] ");
			}
		}
		System.out.println();
		System.out.println("Weight = " + bestWeight + ", Value = " + bestValue);
	}
	
	// Book solution (copy-paste, adapted to my types)
	public static int knapsackDP(int[] weights, int[] values, int maxWeight) {
        int V[] = new int[maxWeight + 1];
        for (int i = 0; i < weights.length; i++) {
            for (int j = maxWeight; j >= weights[i]; --j) {
                V[j] = Math.max(V[j], V[j - weights[i]] + values[i]);
            }
        }
        return V[maxWeight];
    }
	
	public static void main(String[] args) {
		int[] weights = {20, 8, 60, 55, 40, 70, 85, 25, 30, 65, 75, 10, 95, 50, 40, 10};
		int[] values = {65, 35, 245, 195, 65, 150, 275, 155, 120, 320, 75, 40, 200, 100, 220, 99};
		
		knapsackBits(weights, values, 130);
		
		System.out.println("DP solution = " + knapsackDP(weights, values, 130));
	}
}
