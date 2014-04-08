package book.chapter.thirteen;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Arrays;

/**
 * Team Photo Shoot.
 * You are a photographer taking pictures of two opposing teams.
 * The photo is valid if and only if everyone in the back row is taller than everyone in the front row.
 * Each row must have players from the same team (teams of 10).
 * 
 * I realized the solution involves sorting the two arrays and comparing them to see if one array
 * is totally less than the other array (that is, all elements of array 1 are less than array 2, or vice versa).
 * 
 * I implemented dumbPhotoShoot() which is the naive solution; sorting both arrays and then comparing them.
 * This is what the book does.
 * 
 * I also implemented photoShoot() which I believe is a better solution because:
 * 1) The given size N = 10 is small enough where insertion sort is likely more efficient than quicksort or mergesort.
 * 2) From the problem we know we can stop as soon as the condition fails. With insertion sort's slow
 *    buildup of the sorted array, we can actually find the answer before fully sorting the array.
 *    Therefore in the best case this would be O(N) instead of O(N log N).
 *    
 * However, upon benchmarking the two of them, it turns out the dumbPhotoShoot() is faster
 * in both best and worst case scenarios.
 * I guess N == 10 is not enough for quicksort to lose to insertion sort?
 * (It could also be caused by my method call to swap() in insertion sort. If only Java had inline...)
 *    
 * @author rob
 *
 */
public class Problem13_06 {
	// Best, average, worst case are all O(n log n) Time, O(1) memory, but destroy order of input array
	public static boolean dumbPhotoShoot(int[] team1, int[] team2) {
		// Note sorting will destroy the input array ordering
		Arrays.sort(team1);
		Arrays.sort(team2);
		
		// Swap arrays so that team1[0] is the min-most element.
		if (team1[0] > team2[0]) {
			int[] tmp = team1;
			team1 = team2;
			team2 = tmp;
		}
		
		for (int i = 1; i < team1.length; i++) {
			if (team1[i] > team2[i])
				return false;
		}
		
		return true;
	}
	
	private static void swap(int[] arr, int i, int j) {
		if (i == j) return;
		int tmp = arr[i];
		arr[i] = arr[j];
		arr[j] = tmp;
	}
	
	public static boolean photoShoot(int[] team1, int[] team2) {
		// Determine min of each array in O(N) time
		int mini1 = 0, mini2 = 0;
		int min1 = team1[0], min2 = team2[0];
		for (int i = 1; i < team1.length; i++) {
			if (team1[i] < min1) {
				min1 = team1[i];
				mini1 = i;
			}
			if (team2[i] < min2) {
				min2 = team2[i];
				mini2 = i;
			}
		}
		swap(team1, 0, mini1);
		swap(team2, 0, mini2);
		// Swap arrays so that team1[0] is the min-most element.
		if (team1[0] > team2[0]) {
			int[] tmp = team1;
			team1 = team2;
			team2 = tmp;
		}
		// Perform insertion sort of both arrays, stopping if problem constraint is violated.
		for (int i = 1; i < team1.length; i++) {
			min1 = team1[i]; mini1 = i;
			min2 = team2[i]; mini2 = i;
			for (int j = i + 1; j < team1.length; j++) {
				if (team1[j] < min1) {
					min1 = team1[j];
					mini1 = j;
				}
				if (team2[j] < min2) {
					min2 = team2[j];
					mini2 = j;
				}
			}
			swap(team1, i, mini1);
			swap(team2, i, mini2);
			// Check problem constraint now that team1[i] and team2[i] are the correct sorted values
			if (team1[i] > team2[i]) {
				return false;
			}
		}
		return true;
	}
	
	public static void main(String[] args) throws Exception {
		int[] team1 = {7, 2, 3};
		int[] team2 = {1, 6, 4};
		
		System.out.println("dumbPhotoShoot = " + dumbPhotoShoot(team1, team2));
		System.out.println("photoShoot = " + photoShoot(team1, team2));
		
		// Benchmark
		int[] team3 = new int[10];
		int[] team4 = new int[10];
		for (int i = 0; i < 10; i++) {
			team3[i] = (int) (Math.floor(Math.random() * 100) + 1);
			team4[i] = team3[i] + 3; // guarantee a true result, which is worst-case scenario
		}
		int N = 1000000;
		String[] methodsToCompare = {
				"dumbPhotoShoot",
				"photoShoot"
		};
		for (String methodName : methodsToCompare) {
			Class clazz = Class.forName("book.chapter.thirteen.Problem13_06");
			Method method = clazz.getDeclaredMethod(methodName, int[].class, int[].class);

			// Time execution of the reflected method.
			long startTime = System.nanoTime();
			for (int i = 0; i < N; i++) {
				Boolean retVal = (Boolean) method.invoke(null, team3, team4);
				if (retVal == false)
					throw new Exception("wrong result!");
			}
			long endTime = System.nanoTime();
			System.out.println("Elapsed (worst case) time for " + methodName + ": " + (endTime-startTime)/1000000000.0 + " seconds");
		}
		
		
		for (int i = 0; i < 10; i++) {
			team3[i] = (int) (Math.floor(Math.random() * 100) + 1);
			team4[i] = team3[i] + 3;
		}
		team4[2] = -1; // guarantee a false result on element 2, which is best-case
		for (String methodName : methodsToCompare) {
			Class clazz = Class.forName("book.chapter.thirteen.Problem13_06");
			Method method = clazz.getDeclaredMethod(methodName, int[].class, int[].class);

			// Time execution of the reflected method.
			long startTime = System.nanoTime();
			for (int i = 0; i < N; i++) {
				Boolean retVal = (Boolean) method.invoke(null, team3, team4);
				if (retVal == true)
					throw new Exception("wrong result!");
			}
			long endTime = System.nanoTime();
			System.out.println("Elapsed (best case) time for " + methodName + ": " + (endTime-startTime)/1000000000.0 + " seconds");
		}
		
	}
}
