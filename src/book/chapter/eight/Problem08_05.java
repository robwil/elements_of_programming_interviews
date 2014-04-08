package book.chapter.eight;

import java.util.LinkedList;

/**
 * Problem 8.5
 * Towers of Hanoi
 * 
 * I could not think of the solution to this to save my life.
 * I remembered it had to do with recursion but still couldn't solve it.
 * 
 * Looking at the book's solution, I understood the concepts and was able to implement hanoi().
 * 
 * @author rob
 *
 */
public class Problem08_05 {
	public static void hanoi(int n, LinkedList<Integer>[] pegs, int initial, int destination, int interim) {
		// Base case N == 0 do nothing
		if (n == 0) return;
		// Basic steps
		// 1) Move n-1 disks from initial to interim, through destination
		hanoi(n-1, pegs, initial, interim, destination);
		// 2) Move initial's remaining one to destination directly
		pegs[destination].push(pegs[initial].pop());
		System.out.println("Move from " + initial + " to " + destination);
		printPegs(pegs);
		// 3) Move the n-1 from interim into destination, through initial.
		hanoi(n-1, pegs, interim, destination, initial);
	}
	
	public static void printPegs(LinkedList<Integer>[] pegs) {
		System.out.println("----");
		for (int i = 0; i < pegs.length; i++) {
			System.out.println(pegs[i]);
		}
		System.out.println("----\n");
	}
	
	public static void main(String[] args) {
		LinkedList<Integer> initial = new LinkedList<Integer>();
		initial.push(6); initial.push(5); initial.push(4); initial.push(3); initial.push(2); initial.push(1);
		LinkedList<Integer> destination = new LinkedList<Integer>();
		LinkedList<Integer> interim = new LinkedList<Integer>();
		LinkedList<Integer>[] pegs = new LinkedList[3];
		pegs[0] = initial; pegs[1] = destination; pegs[2] = interim;
		
		printPegs(pegs);
		hanoi(initial.size(), pegs, 0, 1, 2);
	}
}
