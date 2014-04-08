package book.chapter.seven;

import java.util.HashMap;
import java.util.Map;

import book.common.List;
import book.common.Node;

/**
 * Problem 7.2, p. 66
 * Checking for cycles in linked list
 * Return null if list has no cycle, otherwise return first element of cycle.
 * No mention on space goals.
 * 
 * I implemented the canonical solution, using Hash Table to store already seen items, findCycle below.
 * 
 * The book talks about the hash table solution, but mentions a solution which is O(N) time and O(1) space.
 * The idea is to use a fast (+2 each iteration) and slow (+1 each iteration) pointer. If they intersect
 * before the loop terminates, there is a cycle. Then the cycle length is calculated by freezing one pointer
 * and counter with the second one until they overlap again. Finally, the cycle beginning is found by starting
 * one pointer at head, one at head + cycle length, and incrementing each by 1 until they overlap again.
 * 
 * I implemented the book solution as findCycle2.
 * 
 * @author rob
 *
 */
public class Problem07_02 {
	public static Node findCycle(List list) {
		if (list == null) return null;
		Node current = list.getHead();
		Map<Node, Boolean> seen = new HashMap<Node, Boolean>();
		while (current != null) {
			if (seen.get(current) != null) {
				return current;
			}
			seen.put(current, true);
			current = current.getNext();
		}
		return null;
	}
	
	public static Node findCycle2(List list) {
		if (list == null) return null;
		Node slow = list.getHead();
		Node fast = slow;
		while(slow != null && fast != null && slow.getNext() != null && fast.getNext() != null && fast.getNext().getNext() != null) {
			slow = slow.getNext();
			fast = fast.getNext().getNext();
			if (slow == fast) {
				// Cycle found, must determine its length
				int cycleLength = 0;
				do {
					fast = fast.getNext();
					cycleLength++;
				} while (fast != slow);
				// Start slow at head, fast at head + cycle length.
				// Move each by +1 each iteration until they are equal.
				// That will be start of cycle.
				slow = list.getHead();
				fast = list.getHead();
				while (cycleLength > 0) {
					fast = fast.getNext();
					cycleLength--;
				}
				while (slow != fast) {
					slow = slow.getNext();
					fast = fast.getNext();
				}
				return slow;
			}
		}
		return null;
	}
	
	public static void main(String[] args) {
		int[] arr = {1, 8, -2, 14};
		List noCycles = List.fromArray(arr);
		System.out.println("Nocycles = " + findCycle(noCycles));
		System.out.println("Nocycles 2 = " + findCycle2(noCycles));
		
		int[] arr2 = {8, 4, 2, 9};
		List cycles = List.fromArray(arr2);
		// create cycle (point 9 -> 4)
		cycles.getHead().getNext().getNext().getNext().setNext(cycles.getHead().getNext());
		System.out.println("Cycles = " + findCycle(cycles).getData());
		System.out.println("Cycles 2 = " + findCycle2(cycles).getData());
	}
}
